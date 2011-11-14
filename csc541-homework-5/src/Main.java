import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class Main {

	public static void main(String[] args) throws IOException {
		
		File indexFile = new File(args[0]);
		
		File dataFile = new File(args[1]);
		
		if (indexFile.exists()) indexFile.delete();
		
		if (dataFile.exists()) dataFile.delete();
		
		indexFile.createNewFile();
		
		dataFile.createNewFile();
		
		HashTableMap myHashTableMap = new HashTableMap(new RandomAccessFile(indexFile, "rw"), new RandomAccessFile(dataFile, "rw"));
		
		BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

		String[] command;

		while (true) {
			
			command = cin.readLine().split("\\s");
			
			if (command[0].equals("add")) myHashTableMap.add(new DataEntry(
					
					Integer.parseInt(command[1]), 
					
					Float.parseFloat(command[2]), 
					
					command[3].charAt(0), 
					
					Integer.parseInt(command[4]), 
					
					Integer.parseInt(command[5]), 
					
					Integer.parseInt(command[6]), 
					
					Integer.parseInt(command[7]), 
					
					Integer.parseInt(command[8])));
			
			if (command[0].equals("find")) myHashTableMap.find(Integer.parseInt(command[1]));
			
			if (command[0].equals("delete")) myHashTableMap.delete(Integer.parseInt(command[1]));
			
			if (command[0].equals("print")) myHashTableMap.print();
			
			if (command[0].equals("end")) return;
			
		}
		
	}
	
}
