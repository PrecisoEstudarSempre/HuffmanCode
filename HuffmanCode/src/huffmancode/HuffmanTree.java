package huffmancode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Preciso Estudar Sempre
 */
public class HuffmanTree {

    private HuffmanNode root; //se comporta como uma cifra

    /**
     * Analisa a frequência de cada caractere e monta um map, onde a chave é o caractere e a frequência é o valor.
     */
    private Map<Character, Integer> getCharFrequency(String nonCryptedText) {
        char[] arrayOfChars = nonCryptedText.toCharArray();
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char letter : arrayOfChars) {
            if (frequencyMap.get(letter) == null) {
                frequencyMap.put(letter, 1);
            } else {
                frequencyMap.put(letter, frequencyMap.get(letter) + 1);
            }
        }
        return frequencyMap;
    }

    /**
     * Cria um mapa para os valores binários, onde a chave é o caractere e a string binária é o valor.
     */
    private Map<Character, String> createBinaryMap(Map<Character, Integer> frequencyMap) {
        Map<Character, String> binaryMap = new HashMap<>(frequencyMap.size());
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            binaryMap.put(entry.getKey(), null);
        }
        return binaryMap;
    }

    /**
     * Ordena as raízes na análise de Huffman.
     */
    private List<HuffmanNode> sortRoots(List<HuffmanNode> roots) {
        Collections.sort(roots, new Comparator<HuffmanNode>() {
            @Override
            public int compare(HuffmanNode o1, HuffmanNode o2) {
                return o1.getFrequency().compareTo(o2.getFrequency());
            }
        });
        return roots;
    }

    /**
     * Inicializa a árvore de Huffman.
     */
    private List<HuffmanNode> initializeTree(Map<Character, Integer> frequencyMap) {
        List<HuffmanNode> roots = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            HuffmanNode node = new HuffmanNode();
            node.setContent(entry.getKey());
            node.setFrequency(entry.getValue());
            node.setIsLeaf(true);
            roots.add(node);
        }
        return sortRoots(roots);
    }
    
    /**
     * Cria a árvore de Huffman.
     */
    private HuffmanNode createTree(Map<Character, Integer> charFrequencyMap) {
        List<HuffmanNode> priorityRootList = this.initializeTree(charFrequencyMap);

        while (priorityRootList.size() > 1) {
            HuffmanNode firstNode = priorityRootList.get(0);
            HuffmanNode secondNode = priorityRootList.get(1);
            HuffmanNode newRoot = new HuffmanNode();
            newRoot.setLeft(firstNode);
            newRoot.setRight(secondNode);
            newRoot.setFrequency(newRoot.getLeft().getFrequency() + newRoot.getRight().getFrequency());
            newRoot.setContent(666);
            priorityRootList.add(newRoot);
            priorityRootList.remove(firstNode);
            priorityRootList.remove(secondNode);
            this.sortRoots(priorityRootList);
        }
        return priorityRootList.get(0);
    }

    /**
     * Realiza a compressão.
     */
    public String crypt(String nonCryptedText) {
        char[] arrayCryptedText = nonCryptedText.toCharArray();
        final char SPACE = '-';
        StringBuilder accumulatorCryptedText = new StringBuilder();
        Map<Character, Integer> charFrequencyMap = this.getCharFrequency(nonCryptedText);
        Map<Character, String> binaryMap = this.createBinaryMap(charFrequencyMap);
        this.root = this.createTree(charFrequencyMap);
        this.crypt(this.root, "", binaryMap);

        for (int i = 0; i < arrayCryptedText.length; i++) {
            char nonCrypedLetter = arrayCryptedText[i];
            accumulatorCryptedText.append(binaryMap.get(nonCrypedLetter));
            if (i != arrayCryptedText.length - 1) {
                accumulatorCryptedText.append(SPACE);
            }
        }

        return accumulatorCryptedText.toString();
    }

    /**
     * Este método de fato realiza a compressão dos dados através de recursividade da árvore de Huffman.
     */
    private void crypt(HuffmanNode root, String currentBinary, Map<Character, String> binaryMap) {
        if (root.isLeaf()) {
            binaryMap.put((char) root.getContent(), currentBinary);
        } else {
            crypt(root.getLeft(), currentBinary.concat("0"), binaryMap);
            crypt(root.getRight(), currentBinary.concat("1"), binaryMap);
        }
    }

    /**
     * Realiza a descompressão.
     */
    public String decrypt(String cryptedText) {
        String[] arrayCryptedWords = cryptedText.split("-");
        StringBuilder accumulatorDecryptedText = new StringBuilder();
        for (String cryptedWord : arrayCryptedWords) {
            HuffmanNode currentNode = this.root;
            for (char cryptedLetter : cryptedWord.toCharArray()) {
                if (cryptedLetter == '0') {
                    currentNode = currentNode.getLeft();
                } else {
                    currentNode = currentNode.getRight();
                }
                if (currentNode.isLeaf()) {
                    accumulatorDecryptedText.append((char) currentNode.getContent());
                }
            }
        }
        return accumulatorDecryptedText.toString();
    }
}
