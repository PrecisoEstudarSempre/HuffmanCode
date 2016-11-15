package huffmancode;

/**
 *
 * @author joao.maida
 */
public class HuffmanNode {

    private Integer frequency;
    private char content;
    private HuffmanNode left;
    private HuffmanNode right;

    public Integer getFrequency() {
        return frequency;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public char getContent() {
        return content;
    }

    public void setContent(char content) {
        this.content = content;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }
}
