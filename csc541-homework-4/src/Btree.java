import java.io.Console;

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

		LinkedBTree myBTree =  new LinkedBTree(myFile, 33);

		Console c = System.console();
        
		if (c == null) {
           
			System.err.println("No console.");
            
			System.exit(1);
        
		}

		String command;
		
		while ((command = c.readLine()) != "end") {
			
			String[] arguments = command.split(" ");
			
			if (arguments[0].equals("add")) myBTree.insert(Integer.parseInt(arguments[1]));
			
			else if (arguments[0].equals("find")) myBTree.;
			
			else if (arguments[0].equals("print")) ;
			
		}
		
        
		
	}
	
}
