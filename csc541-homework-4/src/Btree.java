import java.io.File;

import java.io.IOException;

import java.io.RandomAccessFile;

public class Btree {

	public static void main(String[] args) throws IOException {
		
		File newFile = new File(args[0]);
		
		if (newFile.exists()) {
			
			newFile.delete();
			
		}
		
		newFile.createNewFile();
		
		RandomAccessFile myFile = new RandomAccessFile(newFile, "rw");

		LinkedBTree myBTree =  new LinkedBTree(myFile, 3);
		
		myBTree.insert(25);
		
		myBTree.insert(10);
		
		myBTree.insert(15);
		
		myBTree.print();
		
	}
	
}
