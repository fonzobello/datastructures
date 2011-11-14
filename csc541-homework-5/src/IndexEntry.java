import java.io.IOException;

import java.io.RandomAccessFile;

public class IndexEntry {

	private int _Key;

	private long _Record;

	private long _Next;
	
	public IndexEntry(RandomAccessFile file, long position) {
		
		read(file, position);
		
	}
	
	public IndexEntry(int key, long record, long next) {
		
		setKey(key);
		
		setRecord(record);
		
		setNext(next);
		
	}
	
	public int getKey() {
		
		return _Key;
		
	}
	
	public void setKey(int key) {
		
		_Key = key;
		
	}
	
	public long getRecord() {
		
		return _Record;
		
	}
	
	public void setRecord(long record) {
		
		_Record = record;
		
	}
	
	public long getNext() {
		
		return _Next;
		
	}
	
	public void setNext(long next) {
		
		_Next = next;
	
	}
	
	public void read(RandomAccessFile file, long position) {
		
		try {
			
			file.seek(position);
			
			setKey(file.readInt());
			
			setRecord(file.readLong());
			
			setNext(file.readLong());
			
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void write(RandomAccessFile file, long position) {
		
		try {
		
			file.seek(position);
			
			file.writeInt(getKey());
			
			file.writeLong(getRecord());
			
			file.writeLong(getNext());
			
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
	
	}
	
}
