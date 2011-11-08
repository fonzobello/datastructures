import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

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

		BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

		String command;

		while (!(command = cin.readLine()).equals("end")) {
			
			String[] arguments = command.split(" ");
			
			if (arguments[0].equals("add")) myBinarySearchTree.insert(Integer.parseInt(arguments[1]), Integer.parseInt(arguments[1]));
				
			else if (arguments[0].equals("find")) myBinarySearchTree.find(Integer.parseInt(arguments[1]));
				
			else if (arguments[0].equals("print")) myBinarySearchTree.print();
			
		}

	}
	
}
