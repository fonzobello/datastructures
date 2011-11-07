// http://cis.stvincent.edu/carlsond/swdesign/btree/btree.cpp

import java.io.IOException;

import java.io.RandomAccessFile;

public class LinkedBTree {
	
	private RandomAccessFile _File;
	
	private Integer _Order;
	
	private Integer _NodeSize;
	
	private Integer _NumItems;
	
	private Integer _NumNodes;
	
	private MyInteger _Root;
	
	private LinkedBTreeNode _CurrentNode;
	
	public LinkedBTree(RandomAccessFile file, Integer order) {

		_File = file;
		
		_Order = order;
		
		_NodeSize = 4 + (4 * (order - 1)) + (4 * order);

		_NumItems = 0;

		_NumNodes = 0;

		_Root = new MyInteger(-1);

	}
	
	public void addItem(MyInteger newItem, MyInteger newRight, LinkedBTreeNode node, MyInteger location) {

		int j;

		for (j = node.size(); j > location.getValue(); j--) {

			node.setKey(j, node.key(j - 1));

			node.setPointer(j + 1, node.pointer(j));

		}

		node.setKey(location.getValue(), newItem.getValue());

		node.setPointer(location.getValue() + 1, newRight.getValue());

		node.setSize(node.size() + 1);
		
	}
	
	private boolean searchNode(Integer key, MyInteger location) {

		boolean found = false;

		if (key < _CurrentNode.key(0)) location.setValue(-1);

		else {

			location.setValue(_CurrentNode.size() - 1);

			while ((key < _CurrentNode.key(location.getValue())) && (location.getValue() > 0)) location.setValue(location.getValue() - 1);

			if (key == _CurrentNode.key(location.getValue())) found = true;

		}

		return found;

	}
	
	public boolean insert(Integer item) {

		MyBoolean moveUp = new MyBoolean(false);

		MyInteger newRight = new MyInteger(0);

		MyInteger newItem = new MyInteger(0);
		
		pushDown(item, _Root, moveUp, newItem, newRight);

		if (moveUp.getValue()) {

			_CurrentNode = new LinkedBTreeNode(_Order);
			
			_CurrentNode.setSize(1);

			_CurrentNode.setKey(0, newItem.getValue());

			_CurrentNode.setPointer(0, _Root.getValue());

			_CurrentNode.setPointer(1, newRight.getValue());

			_NumNodes++;

			_Root.setValue(_NumNodes - 1);

			writeNode(_NumNodes - 1, _CurrentNode);

		}

		_NumItems++;

		return true;
		
	}
	
	public void pushDown(Integer currentItem, MyInteger currentRoot, MyBoolean moveUp, MyInteger newItem, MyInteger newRight) {

		MyInteger location = new MyInteger(0);

		if (currentRoot.getValue() == -1) {

			moveUp.setValue(true);

			newItem.setValue(currentItem);

			newRight.setValue(-1);

		} else {

			_CurrentNode = readNode(currentRoot.getValue());

			if (searchNode(currentItem, location)) System.err.println("Error: attempt to put a duplicate into B-tree");

			pushDown(currentItem, new MyInteger(_CurrentNode.pointer(location.getValue() + 1)), moveUp, newItem, newRight);

			if (moveUp.getValue()) {

				_CurrentNode = readNode(currentRoot.getValue());
				
				if (_CurrentNode.size() < (_Order - 1)) {

					moveUp.setValue(false);

					addItem(newItem, newRight, _CurrentNode, new MyInteger(location.getValue() + 1));
					
					writeNode(currentRoot.getValue(), _CurrentNode);

				} else {

					moveUp.setValue(true);

					split(newItem, newRight, currentRoot, new MyInteger(location.getValue()), newItem, newRight);

				}

			}

		}

	}

	public void split(MyInteger currentItem, MyInteger currentRight, MyInteger currentRoot, MyInteger location, MyInteger newItem, MyInteger newRight) {

		Integer j, median;

		LinkedBTreeNode rightNode = new LinkedBTreeNode(_Order);

		median = (_Order - 1) / 2;

		_CurrentNode = readNode(currentRoot.getValue());

		for (j = median; j < (_Order - 1); j++) {

			rightNode.setKey(j - median, _CurrentNode.key(j));

			rightNode.setPointer(j - median + 1, _CurrentNode.pointer(j + 1));

		}

		rightNode.setSize((_Order - 1) - median);

		_CurrentNode.setSize(median);

		addItem(currentItem, currentRight, rightNode, new MyInteger(location.getValue() - median + 1));

		newItem.setValue(_CurrentNode.key(_CurrentNode.size() - 1));

		rightNode.setPointer(0, _CurrentNode.pointer(_CurrentNode.size()));

		_CurrentNode.setSize(_CurrentNode.size() - 1);

		writeNode(currentRoot.getValue(), _CurrentNode);

		_NumNodes++;

		newRight.setValue(_NumNodes);

		writeNode(newRight.getValue(), rightNode);

	}
	
	private LinkedBTreeNode readNode(Integer offset) {

		LinkedBTreeNode toReturn = new LinkedBTreeNode(_Order);
		
		try {
			
			Integer[] keys = new Integer[_Order - 1];
			
			Integer[] pointers = new Integer[_Order];
			
			_File.seek(offset * _NodeSize);
			
			toReturn.setSize(_File.readInt());
			
			for (int i = 0; i < _Order - 1; i ++) {
				
				pointers[i] = _File.readInt();
				
				keys[i] = _File.readInt();
				
			}
			
			pointers[pointers.length - 1] = _File.readInt();
		
			toReturn.setKeys(keys);
			
			toReturn.setPointers(pointers);
			
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
		return toReturn;
		
	}
	
	private void writeNode(Integer offset, LinkedBTreeNode newNode) {
		
		try {
		
			_File.seek(offset * _NodeSize);
			
			_File.writeInt(newNode.size());
			
			for (int i = 0; i < _Order - 1; i ++) {
				
				if (newNode.pointer(i) == null) _File.writeInt(-1);
				
				else _File.writeInt(newNode.pointer(i));
				
				if (newNode.key(i) == null) _File.writeInt(-1);
				
				else _File.writeInt(newNode.key(i));
				
			}
			
			if (newNode.pointer(_Order - 1) == null) _File.writeInt(-1);
			
			else _File.writeInt(newNode.pointer(_Order - 1));
		
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void print() {
		
		LinkedList<Integer> printList = new LinkedList<Integer>();
		
		printList.insertLast(_Root.getValue());
		
		while (!printList.isEmpty()) {
			
			LinkedBTreeNode printNode = readNode(printList.remove(printList.first()));
			
			for (int i = 0; i < printNode.keys().length; i ++) {
				
				System.out.print(printNode.key(i));
				
			}
			
			for (int i = 0; i < printNode.pointers().length; i ++) {
				
				if (printNode.pointer(i) != -1) printList.insertLast(printNode.pointer(i));
				
			}
			
		}
		
	}
	
}
