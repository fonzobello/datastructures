import java.io.IOException;
import java.io.RandomAccessFile;

public class HashTableMap {
	
	private static final int TBL_SIZE = 1001;
	
	private RandomAccessFile _IndexFile;
	
	private RandomAccessFile _DataFile;
	
	public HashTableMap(RandomAccessFile indexFile, RandomAccessFile dataFile) {

		_IndexFile = indexFile;
		
		_DataFile = dataFile;
		
	}
	
	private int hash(int key) {
		
		return (key % TBL_SIZE);
		
	}
	
	public void add(DataEntry entry) {
		
		try {
		
			int position = (int) _DataFile.length();
			
			entry.write(_DataFile, position);
			
			
					
		} catch(IOException e) {}
		
	}
	
	public DataEntry find(int key) {
		
		return null;
		
	}
	
	public void delete(int key) {}
	
	public void print() {}
	
}
