import java.io.File;

import java.io.IOException;


import java.io.RandomAccessFile;

public class Bst {

	public static void main(String[] args) throws IOException {
		
		File newFile = new File(args[0]);
		
		if (newFile.exists()) {
			
			newFile.delete();
			
		}
		
		newFile.createNewFile();
		
		RandomAccessFile myFile = new RandomAccessFile(newFile, "rw");

		LinkedBinarySearchTree<Integer, Integer> myBinarySearchTree =  new LinkedBinarySearchTree<Integer, Integer>(myFile);
		
		myBinarySearchTree.insert(25, 25);
		
		myBinarySearchTree.insert(15, 15);
		
		myBinarySearchTree.insert(32, 32);
		
		myBinarySearchTree.insert(18, 18);
		
		myBinarySearchTree.print();

	}
	
}
