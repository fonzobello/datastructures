import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class Main {

	public static void main(String[] args) throws IOException {
		
		File indexFile = new File(args[0]);
		
		File dataFile = new File(args[0]);
		
		if (indexFile.exists()) indexFile.delete();
		
		if (dataFile.exists()) dataFile.delete();
		
		indexFile.createNewFile();
		
		dataFile.createNewFile();
		
		HashTableMap myHashTableMap = new HashTableMap(new RandomAccessFile(indexFile, "rw"), new RandomAccessFile(dataFile, "rw"));
		
		BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

		String[] command;

		while (true) {
			
			command = cin.readLine().split("\\s");
			
			if (command[0].equals("add")) ;
			
			if (command[0].equals("find")) ;
			
			if (command[0].equals("delete")) ;
			
			if (command[0].equals("print")) ;
			
		}
		
	}
	
}
