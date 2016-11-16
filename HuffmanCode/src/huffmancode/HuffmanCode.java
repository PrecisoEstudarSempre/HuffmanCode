package huffmancode;

import java.util.Scanner;

/**
 *
 * @author joao.maida
 */
public class HuffmanCode {

    public static void main(String[] args) {
        HuffmanCode huffmanCode = new HuffmanCode();
        String text = huffmanCode.getText();
        HuffmanTree huffmanTree = new HuffmanTree();
        System.out.println(huffmanTree.crypt(text));
    }

    private String getText() {
        Scanner io = new Scanner(System.in);
        System.out.println("Enter the string: ");
        return io.nextLine();
    }    
}
