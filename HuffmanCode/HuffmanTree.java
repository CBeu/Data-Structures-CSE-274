/**
 * @author Craig Beuerlein
 * This provides the basic structure of the Huffman Tree(The Tree, The Leafs, and The Node). Leafs and Nodes extend the
 * T
 */
import java.util.*;

/**
 */
abstract class HuffmanTree implements Comparable<HuffmanTree> {
    public static ArrayList<String> keyStore;
    public static ArrayList<Character> charStore;
	public int frequency; 
/**
 * Generates a Huffman tree
 * @param freq frequency of each charcter
 */
	public HuffmanTree(int freq) {
		frequency = freq;
		keyStore = new ArrayList<String>();
		charStore = new ArrayList<Character>();
	}
/**
 * Compares two huffman trees
 */
	@Override
	public int compareTo(HuffmanTree o) {
		// TODO Auto-generated method stub
		// compares on the frequency
		return frequency - o.frequency;
	}
}
/**
 */
class HuffmanLeaf extends HuffmanTree {
	public char value; 
	/**
	 * Creates a huffman leaf, meaning no children
	 * @param freq frequency of a character in the text file
	 * @param val the character
	 */
	public HuffmanLeaf(int freq, char val) {
		super(freq);
		value = val;
	}

}

class HuffmanNode extends HuffmanTree {
	public  HuffmanTree left;
	public  HuffmanTree right; 
/**
 * Creates a huffman node
 * @param l The left child
 * @param r the right child
 */
	public HuffmanNode(HuffmanTree l, HuffmanTree r) {
		super(l.frequency + r.frequency);
		left = l;
		right = r;
	}
}
