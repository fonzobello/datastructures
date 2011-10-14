package ncsu.csc.datastructures;

public class RedBlackTreeNode<K,V> extends LinkedBinaryTreeNode<Entry<K,V>> {

	protected boolean isRed;

	RedBlackTreeNode() {}

	RedBlackTreeNode(Entry<K,V> element, BinaryTreePosition<Entry<K,V>> parent, BinaryTreePosition<Entry<K,V>> left, BinaryTreePosition<Entry<K,V>> right) {

		super(element, parent, left, right);

		isRed = false;

	} 

	public boolean isRed() {
		
		return isRed;
		
	}

	public void makeRed() {
		
		isRed = true;
		
	}

	public void makeBlack() {
		
		isRed = false;
		
	}

	public void setColor(boolean color) {
		
		isRed = color;
		
	}

}
