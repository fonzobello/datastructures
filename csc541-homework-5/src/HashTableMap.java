import java.io.IOException;
import java.io.RandomAccessFile;

public class HashTableMap {
	
	private static final int TBL_SIZE = 1001;
	
	private static final int SIZEOF_INDEX = 20;
	
	private static final int SIZEOF_DATA = 30;
	
	private RandomAccessFile _IndexFile;
	
	private RandomAccessFile _DataFile;
	
	public HashTableMap(RandomAccessFile indexFile, RandomAccessFile dataFile) {

		_IndexFile = indexFile;
		
		_DataFile = dataFile;
		
		for (int i = 0; i < TBL_SIZE; i++) {
			
			IndexEntry indexEntry = new IndexEntry(-1, -1, -1);
			
			try {
			
				indexEntry.write(_IndexFile, _IndexFile.length());

			} catch (IOException e) {

				e.printStackTrace();
			}
			
		}
		
	}
	
	private int hash(int key) {
		
		return (key % TBL_SIZE) * SIZEOF_INDEX;
		
	}
	
	public void add(DataEntry entry) {
		
		if (entry.getTransactionNumber() == 54502) {
			
			System.out.println("");
			
		}
		
		try {
			
			long record = _DataFile.length();
			
			entry.write(_DataFile, record);
			
			long currentIndex = hash(entry.getTransactionNumber());
			
			IndexEntry indexEntry = new IndexEntry(_IndexFile, currentIndex);
			
			while (true) {
						
				if (indexEntry.getKey() == entry.getTransactionNumber()) {
				
					System.out.println("Record " + entry.getTransactionNumber() + " is a duplicate.");
					
					return;
					
				} else {
						
					if (indexEntry.getNext() != -1) {
						
							currentIndex = indexEntry.getNext();
							
							indexEntry = new IndexEntry(_IndexFile, currentIndex);
							
					} else {
						
						if (indexEntry.getKey() == -1) {
							
							indexEntry.setKey(entry.getTransactionNumber());
							
							indexEntry.setRecord(record);
							
							indexEntry.setNext(-1);
							
							indexEntry.write(_IndexFile, currentIndex);
							
							return;
							
						} else {
							
							indexEntry.setNext(_IndexFile.length());
							
							indexEntry.write(_IndexFile, currentIndex);
							
							IndexEntry newEntry = new IndexEntry(entry.getTransactionNumber(), record, -1);
							
							newEntry.write(_IndexFile, _IndexFile.length());
							
							return;
							
						}
						

						
					}
					
				}
				
			}
					
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void find(int key) {
			
		IndexEntry indexEntry = new IndexEntry(_IndexFile, hash(key));
		
		if (indexEntry.getKey() == -1) {
			
			// Not Found
			
			System.out.println("Record " + key + " does not exist.");
			
			return;
			
		} else {
			
			// Search the Chain
			
			while (indexEntry.getNext() != -1) {

				if (indexEntry.getKey() == key) {
					
					DataEntry dataEntry= new DataEntry(_DataFile, indexEntry.getRecord());
					
					System.out.println(dataEntry);
					
					return;
					
				}
				
				indexEntry = new IndexEntry(_IndexFile, indexEntry.getNext());
				
			}
			
			if (indexEntry.getKey() == key) {
				
				DataEntry dataEntry= new DataEntry(_DataFile, indexEntry.getRecord());
				
				System.out.println(dataEntry);
				
				return;
				
			}
			
		}
		
		System.out.println("Record " + key + " does not exist.");
		
	}
	
	public void delete(int key) {
		
		long previousIndex = -1;
		
		long currentIndex = hash(key);
		
		IndexEntry indexEntry = new IndexEntry(_IndexFile, currentIndex);
		
		while (true) {
					
			if (indexEntry.getKey() == key) {
			
				if (previousIndex != -1) {
					
					IndexEntry previousEntry = new IndexEntry(_IndexFile, previousIndex);
					
					previousEntry.setNext(indexEntry.getNext());
					
					previousEntry.write(_IndexFile, previousIndex);
					
				} else {
				
					//TODO: move second record to front
					
					if (indexEntry.getNext() != -1) {
						
						IndexEntry nextEntry = new IndexEntry(_IndexFile, indexEntry.getNext());
						
						nextEntry.write(_IndexFile, currentIndex);
						
						return;
						
					}
					
				}
				
				indexEntry.setKey(-1);
				
				indexEntry.setRecord(-1);
				
				indexEntry.setNext(-1);
				
				indexEntry.write(_IndexFile, currentIndex);
				
				return;
				
			} else {
					
				if (indexEntry.getNext() != -1) {
					
						previousIndex = currentIndex;
					
						currentIndex = indexEntry.getNext();
						
						indexEntry = new IndexEntry(_IndexFile, currentIndex);
						
				} else {
					
					break;
					
				}
				
			}
			
		}
		
		System.out.println("Record " + key + " does not exist.");
		
	}
	
	public void print() {
		
		for (int i = 0; i < TBL_SIZE; i++) {
			
			System.out.print(i + ": ");
			
			IndexEntry indexEntry = new IndexEntry(-1, -1, -1);
		
			indexEntry.read(_IndexFile, i * SIZEOF_INDEX);
			
			if (indexEntry.getKey() != -1) System.out.print(indexEntry);
			
			while (indexEntry.getNext() != -1) {
				
				indexEntry = new IndexEntry(_IndexFile, indexEntry.getNext());

				if (indexEntry.getKey() != -1) System.out.print(" " + indexEntry);
				
			}
			
			System.out.println();
			
		}
	
	}
	
}
