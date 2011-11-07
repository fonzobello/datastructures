import java.io.IOException;

import java.io.RandomAccessFile;

public class LinkedBinaryTreeNode<E> implements BinaryTreePosition<E> {

	private E _Element;
	
	private long _Offset;
	
	private RandomAccessFile _File;

	@SuppressWarnings("unchecked")
	
	public LinkedBinaryTreeNode(long offset, RandomAccessFile file) {
		
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
			
			_Offset = _File.length();
		
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
		setElement(element);

		setLeft(left);

		setRight(right);

	}
	
	@Override
	
	public long getOffset() {

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
			
			long left = _File.readLong();
			
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
			
			else _File.writeLong(left.getOffset());
		
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}

	public BinaryTreePosition<E> getRight() {
		
		try {
		
			_File.seek(_Offset + 12);
			
			long right = _File.readLong();
			
			if (right == -1) return null;
			
			else return new LinkedBinaryTreeNode<E>(right, _File);
		
		} catch(IOException e) {
			
			e.printStackTrace();
			
			return null;
			
		}
		
	}

	public void setRight(BinaryTreePosition<E> right) {
		
		try {
			
			_File.seek(_Offset + 12);
			
			if (right == null) _File.writeLong(-1);
			
			else _File.writeLong(right.getOffset());
		
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public String toString() {
		
		if (element() == null) return "null";
		
		return element().toString() + "/" + getOffset();
		
	}

}
