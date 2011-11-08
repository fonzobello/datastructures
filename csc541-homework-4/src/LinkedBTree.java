/*
 * References:
 * 
 * Tharp, Alan L. File Organization and Processing. 1st. New York, Chichester, Brisbane, Toronto, Singapore: John Wiley & Sons, 1988. 221-232. Print.
 * 
 * Carlson, David. Software Design Using C . Web. <http://cis.stvincent.edu/carlsond/swdesign/swd.html>.
 */

import java.io.IOException;

import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;

public class LinkedBTree {
	
	private RandomAccessFile _File;
	
	private Integer _Order;
	
	private Integer _NodeSize;
	
	private Integer _NumItems;
	
	private Integer _NumNodes;
	
	private Integer _MinKeys;
	
	private Integer _MaxKeys;
	
	private MyInteger _Root;
	
	private LinkedBTreeNode _CurrentNode;
	
	private long _ElapsedTime = 0;
	
	private int _TotalFinds = 0;
	
	public LinkedBTree(RandomAccessFile file, Integer order) {

		_File = file;
		
		_Order = order;
		
		_MinKeys = (order - 1) / 2;
		
		_MaxKeys = (order - 1);
		
		_NodeSize = 4 + (4 * (order - 1)) + (4 * order);

		_NumItems = 0;

		_NumNodes = 0;

		_Root = new MyInteger(-1);

	}
	
	public void addItem(MyInteger newItem, int newRight, LinkedBTreeNode node, int location) {

		int j;

		for (j = node.size(); j > location; j--) {

			node.setKey(j, node.key(j - 1));

			node.setPointer(j + 1, node.pointer(j));

		}

		node.setKey(location, newItem.getValue());

		node.setPointer(location + 1, newRight);

		node.setSize(node.size() + 1);
		
	}
	
	public void retrieve(Integer key) {

		long lStartTime = new Date().getTime();
		
		long lEndTime;
		
		if (treeSearch(key, _Root.getValue())) {
			
			lEndTime = new Date().getTime();
			
			System.out.println("Record " + key + " exists.");
		}
		
		else {
			
			lEndTime = new Date().getTime();
			
			System.out.println("Record " + key + " does not exist.");
		}

		_ElapsedTime = _ElapsedTime + (lEndTime - lStartTime);
		
		_TotalFinds ++;
		
	}
	
	public boolean treeSearch(Integer key, Integer location) {
		
		_CurrentNode = readNode(location);
		
		Integer keys[] = _CurrentNode.keys();
		
		Integer pointers[] = _CurrentNode.pointers();
		
		if (searchNode(key, new MyInteger(0))) return true;
		
		for (int i = 0; i < keys.length; i ++) {
			
			if ((key < keys[i]) && (pointers[i] != -1)) return treeSearch(key, pointers[i]);
			
			else if (key == keys[i]) return true;
			
			else if ((key > keys[i]) && (i == _CurrentNode.size() - 1) && (pointers[i+1] != -1)) return treeSearch(key, pointers[i+1]);
			
		}
		
		return false;
		
	}
	
	private boolean searchNode(Integer key, MyInteger location) {

		boolean found = false;

		if (key < _CurrentNode.key(0)) location.setValue(-1);

		else {

			location.setValue(_CurrentNode.size() - 1);

			while ((key < _CurrentNode.key(location.getValue())) && (location.getValue() > 0)) location.setValue(location.getValue() - 1);
			
			if (key.equals(_CurrentNode.key(location.getValue()))) found = true;

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

			_Root.setValue(_NumNodes);

			writeNode(_NumNodes, _CurrentNode);

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

					addItem(newItem, newRight.getValue(), _CurrentNode, location.getValue() + 1);
					
					writeNode(currentRoot.getValue(), _CurrentNode);

				} else {

					moveUp.setValue(true);

					split(newItem, newRight.getValue(), currentRoot.getValue(), location.getValue(), newItem, newRight);

				}

			}

		}

	}

	public void split(MyInteger currentItem, int currentRight, int currentRoot, int location, MyInteger NewItem, MyInteger newRight) {

		int j, median;

		LinkedBTreeNode rightNode = new LinkedBTreeNode(_Order);

		if (location < _MinKeys) median = _MinKeys;

		else median = _MinKeys + 1;

		_CurrentNode = readNode(currentRoot);

		for (j = median; j < _MaxKeys; j++) {

			rightNode.setKey(j - median, _CurrentNode.key(j));

			rightNode.setPointer(j - median + 1, _CurrentNode.pointer(j + 1));

		}

		rightNode.setSize(_MaxKeys - median);

		_CurrentNode.setSize(median);

		if (location < _MinKeys) addItem(currentItem, currentRight, _CurrentNode, location + 1);

		else addItem(currentItem, currentRight, rightNode, location - median + 1);

		NewItem.setValue(_CurrentNode.key(_CurrentNode.size() - 1));

		rightNode.setPointer(0, _CurrentNode.pointer(_CurrentNode.size()));

		_CurrentNode.setSize(_CurrentNode.size() - 1);

		for (int i = 0; i < _CurrentNode.keys().length; i ++) {
			
			if (i >= _CurrentNode.size()) _CurrentNode.setKey(i, -1);
			
		}
		
		writeNode(currentRoot, _CurrentNode);

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
		
		System.out.println("");
		
		int level = 1;
		
		LinkedList<Integer> printList = new LinkedList<Integer>();
		
		LinkedList<Integer> nextList = new LinkedList<Integer>();
		
		printList.insertLast(_Root.getValue());
		
		while (!printList.isEmpty()) {
			
			System.out.print(level + ": ");
		
			while (!printList.isEmpty()) {
				
				int offset = printList.remove(printList.first());
				
				LinkedBTreeNode printNode = readNode(offset);
				
				for (int i = 0; i < printNode.keys().length; i ++) {
					
					if (!printNode.key(i).equals(-1)) {
						
						if (i != 0) System.out.print(",");
						
						System.out.print(printNode.key(i));
						
					}
					
				}
						
				System.out.print("/" + (offset -1) * _NodeSize + " ");
				
				for (int i = 0; i < printNode.size() + 1; i ++) {
					
					if (printNode.pointer(i) != -1) nextList.insertLast(printNode.pointer(i));
					
				}
				
			}
			
			System.out.println("");
			
			printList = nextList;
			
			nextList = new LinkedList<Integer>();
			
			level ++;
			
		}
		
		System.out.println("");
		
		DecimalFormat myFormatter = new DecimalFormat("0.000000");
		
		System.out.println("Sum: " + myFormatter.format(_ElapsedTime * 0.001) );
		
		System.out.println("Avg: " + myFormatter.format(((_ElapsedTime * 0.001) / _TotalFinds)));
		
	}
	
}
