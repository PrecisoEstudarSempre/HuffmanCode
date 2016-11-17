package huffmancode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author joao.maida
 */
public class HuffmanTree {

    private String nonCryptedText;
    private String cryptedText;
    private HuffmanNode root; //se comporta como uma cifra
    private Map<Character, String> binaryMap;

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

    private void initializeBinaryMap(Map<Character, Integer> frequencyMap){
        this.binaryMap = new HashMap<>(frequencyMap.size());
        for(Map.Entry<Character, Integer> entry : frequencyMap.entrySet()){
            this.binaryMap.put(entry.getKey(), null);
        }
    }
    
    private List<HuffmanNode> sortRoots(List<HuffmanNode> roots) {
        Collections.sort(roots, new Comparator<HuffmanNode>() {
            @Override
            public int compare(HuffmanNode o1, HuffmanNode o2) {
                return o1.getFrequency().compareTo(o2.getFrequency());
            }
        });
        return roots;
    }

    private HuffmanNode createTree() {
        Map<Character, Integer> charFrequencyMap = this.getCharFrequency(this.nonCryptedText);
        this.initializeBinaryMap(charFrequencyMap);
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

    public String crypt(String nonCryptedText) {
        this.nonCryptedText = nonCryptedText;
        final String SPACE = "-";
        StringBuilder accumulatorCryptedText = new StringBuilder();
        
        this.root = this.createTree();
        this.crypt();
        
        for(char nonCrypedLetter : this.nonCryptedText.toCharArray()){
            accumulatorCryptedText.append(binaryMap.get(nonCrypedLetter)).append(SPACE);
        }
        
        return accumulatorCryptedText.toString();
    }

    public void crypt() {
        this.crypt(this.root, "");
    }
    
    public void crypt(HuffmanNode root, String currentBinary) {
        if(root.isLeaf()){
            binaryMap.put((char)root.getContent(), currentBinary);
        } else {
            crypt(root.getLeft(), currentBinary.concat("0"));
            crypt(root.getRight(), currentBinary.concat("1"));
        }
    }

    public String decrypt(String cryptedText) {
        this.cryptedText = cryptedText;
        String[] arrayCryptedText = this.cryptedText.split("-");
        StringBuilder accumulatorDecryptedText = new StringBuilder();
        return decrypt();
    }

    public String decrypt() {
        return null;
    }
}
