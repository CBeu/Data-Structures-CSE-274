
/**
 * @author Craig Beuerlein
 *This class generate the Huffman Code, Codes and Decodes .txt files, and builds the Huffman tree
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class HuffmanCode {
	/**
	 * 
	 * @param freq The number of times a character occurs in the inputed .txt file
	 * @return a Huffman Tree
	 */
	public static HuffmanTree buildTree(int[] freq) {
		PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
		for (int i = 0; i < freq.length; i++)
			if (freq[i] > 0)
				trees.add(new HuffmanLeaf(freq[i], (char) i));
		while (trees.size() > 1) {
			HuffmanTree a = trees.poll();
			HuffmanTree b = trees.poll();
			trees.add(new HuffmanNode(a, b));
		}
		return trees.poll();
	}

	/**
	 * Generates the codes based off the Huffman Tree and using recursion
	 * 
	 * @param tree        The Huffman Tree to generate the codes
	 * @param huffmanCode A StringBuffer (which allows easier modification) that is
	 *                    the Huffman Code for a particular character
	 */
	public static void getCodes(HuffmanTree tree, StringBuffer huffmanCode) {
		if (tree instanceof HuffmanLeaf) {
			HuffmanLeaf leaf = (HuffmanLeaf) tree;
			HuffmanTree.charStore.add(leaf.value);
			HuffmanTree.keyStore.add(huffmanCode.toString());
			System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + huffmanCode);

		} else if (tree instanceof HuffmanNode) {
			HuffmanNode node = (HuffmanNode) tree;
			// traverse left
			huffmanCode.append('0');
			getCodes(node.left, huffmanCode);
			huffmanCode.deleteCharAt(huffmanCode.length() - 1);
			// traverse right
			huffmanCode.append('1');
			getCodes(node.right, huffmanCode);
			huffmanCode.deleteCharAt(huffmanCode.length() - 1);
		}
	}

	/**
	 * Scans the characters of an inputer .txt file and compares the charcter index
	 * from the Huffman Tree to the Huffman Code index from the Huffman Tree to
	 * generate the Huffman Code
	 * 
	 * @param tree  The Huffman Tree to search for the code
	 * @param input A string of the inputed .txt file
	 * @return a string of Huffman Code
	 */
	public static String encode(HuffmanTree tree, String input) {
		String encoded = "";
		for (char c : input.toCharArray()) {
			for (char i : HuffmanTree.charStore) {
				if (c == i) {
					encoded += (HuffmanTree.keyStore.get(HuffmanTree.charStore.indexOf(i)));
				}
			}
		}
		return encoded;
	}

	/**
	 * Scans the huffman code and the arrays in the huffman tree to match the code
	 * with the appropriate character
	 * 
	 * @param tree  The Huffman Tree to search for the code
	 * @param input A string of the inputed .txt file of huffman code
	 * @return A decoded string
	 */
	public static String decode(HuffmanTree tree, String input) {
		String decoded = "";
		String search = "";
		for (char c : input.toCharArray()) {
			search += c;
			for (String i : HuffmanTree.keyStore) {
				if (search.equals(i)) {
					decoded += HuffmanTree.charStore.get(HuffmanTree.keyStore.indexOf(i));
					search = "";
				}
			}
		}
		return decoded;
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		// Get Input
		String input = new Scanner(new File("input.txt")).useDelimiter("\\Z").next();
		int[] freq = new int[100000000];
		for (char c : input.toCharArray())
			freq[c]++;
		HuffmanTree tree = buildTree(freq);
		getCodes(tree, new StringBuffer());
		// Encode Input
		System.out.println("Encoding");
		String encoded = encode(tree, input);
		System.out.println(encoded);
		PrintWriter writer = new PrintWriter("huffman.txt");
		writer.print(encoded);
		writer.close();
		// Decode Huffman Code
		System.out.println("Decoding");
		String out = new Scanner(new File("huffman.txt")).next();
		String decoded = decode(tree, out);
		System.out.println(decoded);
		PrintWriter w = new PrintWriter("decoded.txt");
		w.print(decoded);
		w.close();
	}
}
