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
    private HuffmanNode root;

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
            roots.add(node);
        }
        return sortRoots(roots);
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

    private void createTree() {
        List<HuffmanNode> priorityRootList = this.initializeTree(this.getCharFrequency(this.nonCryptedText));

        while (priorityRootList.size() > 1) {
            HuffmanNode firstNode = priorityRootList.get(0);
            HuffmanNode secondNode = priorityRootList.get(1);
            HuffmanNode newRoot = new HuffmanNode();
            newRoot.setLeft(firstNode);
            newRoot.setRight(secondNode);
            newRoot.setIsEmpty(true);
            newRoot.setFrequency(newRoot.getLeft().getFrequency() + newRoot.getRight().getFrequency());
            newRoot.setContent(666);
            priorityRootList.add(newRoot);
            priorityRootList.remove(firstNode);
            priorityRootList.remove(secondNode);
            this.sortRoots(priorityRootList);
        }
        this.root = priorityRootList.get(0);
    }

    public String crypt(String nonCryptedText) {
        this.nonCryptedText = nonCryptedText;
        this.createTree();
        StringBuilder cryptedTextAccumulator = new StringBuilder();
        final String SPACE = " ";
        for (char wantedChar : this.nonCryptedText.toCharArray()) {
            cryptedTextAccumulator.append(crypt(this.root, wantedChar)).append(SPACE);
        }
        return this.cryptedText = cryptedTextAccumulator.toString();
    }

    public String crypt(HuffmanNode root, char wantedChar) {
        if (root.getLeft().getContent() == wantedChar) {
            return "0";
        }
        if (root.getRight().getContent() == wantedChar) {
            return "1";
        }
        if (root.getLeft().getContent() == 666) {
            return "0".concat(crypt(root.getLeft(), wantedChar));
        }
        if (root.getRight().getContent() == 666) {
            return "1".concat(crypt(root.getRight(), wantedChar));
        }
        return "";
    }

    public String decrypt(String cryptedText) {
        this.cryptedText = cryptedText;
        return decrypt();
    }

    public String decrypt() {
        return null;
    }
}
