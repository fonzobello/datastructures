import java.io.IOException;

import java.io.RandomAccessFile;

public class DataEntry {

	private int _TransactionNumber;

	private float _Amount;

	private char _TransactionType;

	private int _Account;

	private int _Hour;

	private int _Minute;

	private int _Day;

	private int _Year;
	
	public DataEntry(RandomAccessFile file, long position) {
		
		read(file, position);
		
	}
	
	public DataEntry(int transactionNumber, float amount, char transactionType, int account, int hour, int minute, int day, int year) {
	
		setTransactionNumber(transactionNumber);
		
		setAmount(amount);
		
		setTransactionType(transactionType);
		
		setAccount(account);
		
		setHour(hour);
		
		setMinute(minute);
		
		setDay(day);
		
		setYear(year);
		
	}
	
	public int getTransactionNumber() {
		
		return _TransactionNumber;
		
	}
	
	public void setTransactionNumber(int transactionNumber) {
		
		_TransactionNumber = transactionNumber;
		
	}
	
	public float getAmount() {
		
		return _Amount;
		
	}
	
	public void setAmount(float amount) {
		
		_Amount = amount;
		
	}
	
	public char getTransactionType() {
		
		return _TransactionType;
		
	}
	
	public void setTransactionType(char transactionType) {
		
		_TransactionType = transactionType;
		
	}
	
	public int getAccount() {
		
		return _Account;
		
	}
	
	public void setAccount(int account) {
		
		_Account = account;
		
	}
	
	public int getHour() {
		
		return _Hour;
		
	}
	
	public void setHour(int hour) {
		
		_Hour = hour;
		
	}
	
	public int getMinute() {
		
		return _Minute;
		
	}
	
	public void setMinute(int minute) {
		
		_Minute = minute;
		
	}
	
	public int getDay() {
		
		return _Day;
		
	}
	
	public void setDay(int day) {
		
		_Day = day;
		
	}
	
	public int getYear() {
		
		return _Year;
		
	}
	
	public void setYear(int year) {
		
		_Year = year;
	
	}
	
	public void read(RandomAccessFile file, long position) {
		
		try {
			
			file.seek(position);
			
			setTransactionNumber(file.readInt());
			
			setAmount(file.readFloat());
			
			setTransactionType(file.readChar());
			
			setAccount(file.readInt());
			
			setHour(file.readInt());
			
			setMinute(file.readInt());
			
			setDay(file.readInt());
			
			setYear(file.readInt());
			
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void write(RandomAccessFile file, long position) {
		
		try {
		
			file.seek(position);
			
			file.writeInt(getTransactionNumber());
			
			file.writeFloat(getAmount());
			
			file.writeChar(getTransactionType());
			
			file.writeInt(getAccount());
			
			file.writeInt(getHour());
			
			file.writeInt(getMinute());
			
			file.writeInt(getDay());
			
			file.writeInt(getYear());
			
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
	
	}
	
}