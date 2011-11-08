/*
 * This class was based off of the text from the CSC316 textbook (2004 Fall Term)
 * 
 * Goodrich, Michael T., and Roberto Tamassia. Data Structures and Algorithms in Java. 4th. Print.
 */

import java.io.IOException;

import java.io.RandomAccessFile;

public class LinkedBinaryTreeNode<E> implements BinaryTreePosition<E> {

	private E _Element;
	
	private int _Offset;
	
	private RandomAccessFile _File;

	@SuppressWarnings("unchecked")
	
	public LinkedBinaryTreeNode(int offset, RandomAccessFile file) {
		
		try {
			
			_File = file;
			
			_Offset = offset;
			
			_File.seek(_Offset);
			
			Integer element = new Integer(_File.readInt());
			
			setElement((E) new LinkedBinarySearchTreeEntry<Integer, Integer>(element, element));

		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}

	public LinkedBinaryTreeNode(E element, BinaryTreePosition<E> left, BinaryTreePosition<E> right, RandomAccessFile file) { 

		try {
		
			_File = file;
			
			_Offset = (int) _File.length();
		
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
		setElement(element);

		setLeft(left);

		setRight(right);

	}
	
	@Override
	
	public int getOffset() {

		return _Offset;
		
	}

	public E element() {
		
		return _Element;
		
	}

	@SuppressWarnings("unchecked")
	
	public void setElement(E element) {

		_Element = element;
		
		try {
			
			_File.seek(_Offset);
			
			if (element == null) _File.writeInt(-1);
			
			else _File.writeInt(((LinkedBinarySearchTreeEntry<Integer, Integer>) element).getKey());
		
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}

	public BinaryTreePosition<E> getLeft() {
		
		try {
		
			_File.seek(_Offset + 4);
			
			int left = _File.readInt();
			
			if (left == -1) return null;
			
			else return new LinkedBinaryTreeNode<E>(left, _File);
			
		} catch(IOException e) {
			
			e.printStackTrace();
			
			return null;
			
		}
		
	}

	public void setLeft(BinaryTreePosition<E> left) {
		
		try {
			
			_File.seek(_Offset + 4);
			
			if (left == null) _File.writeLong(-1);
			
			else _File.writeInt(left.getOffset());
		
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}

	public BinaryTreePosition<E> getRight() {
		
		try {
		
			_File.seek(_Offset + 8);
			
			int right = _File.readInt();
			
			if (right == -1) return null;
			
			else return new LinkedBinaryTreeNode<E>(right, _File);
		
		} catch(IOException e) {
			
			e.printStackTrace();
			
			return null;
			
		}
		
	}

	public void setRight(BinaryTreePosition<E> right) {
		
		try {
			
			_File.seek(_Offset + 8);
			
			if (right == null) _File.writeInt(-1);
			
			else _File.writeInt(right.getOffset());
		
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public String toString() {
		
		if (element() == null) return "null";
		
		return element().toString() + "/" + getOffset();
		
	}

}
