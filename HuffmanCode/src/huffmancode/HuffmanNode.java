package huffmancode;

/**
 *
 * @author joao.maida
 */
public class HuffmanNode {

    private Integer frequency;
    private int content;
    private HuffmanNode left;
    private HuffmanNode right;
    private boolean isEmpty;

    public boolean isIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

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

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "[content="+this.content+"]";
    }
}
