package ncsu.csc.datastructures;
public class LinkedAVLTreeNode<K,V> extends LinkedBinaryTreeNode<Entry<K,V>> {

	private int _Height;

	public LinkedAVLTreeNode() {}

	public LinkedAVLTreeNode(Entry<K,V> element, BinaryTreePosition<Entry<K,V>> parent, BinaryTreePosition<Entry<K,V>> left, BinaryTreePosition<Entry<K,V>> right) {

		super(element, parent, left, right);

		setHeight(0);

		if (left != null) setHeight(Math.max(_Height, 1 + ((LinkedAVLTreeNode<K,V>) left).getHeight()));
	
		if (right != null) setHeight(Math.max(_Height, 1 + ((LinkedAVLTreeNode<K,V>) right).getHeight()));
			
	}

	public void setHeight(int height) {
		
		_Height = height;
		
	}

	public int getHeight() {
		
		return _Height;
		
	}

}