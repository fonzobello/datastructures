public class LinkedBTreeNode {
	
	private Integer _Size;
	
	private Integer[] _Keys;
	
	private Integer[] _Pointers;
	
	public LinkedBTreeNode(Integer order) {
		
		setKeys(new Integer[order - 1]);
		
		setPointers(new Integer[order]);
		
	}
	
	public Integer[] keys() {
		
		return _Keys;
		
	}
	
	public Integer[] pointers() {
		
		return _Pointers;
		
	}
	
	public Integer size() {
		
		return _Size;
		
	}
	
	public void setSize(Integer size) {
		
		_Size = size;
		
	}
	
	public void setKeys(Integer[] keys) {
		
		_Keys = keys;
		
	}
	
	public void setKey(Integer index, Integer value) {
		
		_Keys[index] = value;
		
	}
	
	public void setPointers(Integer[] pointers) {
		
		_Pointers = pointers;
		
	}
	
	public void setPointer(Integer index, Integer value) {
		
		_Pointers[index] = value;
		
	}
	
	public Integer key(Integer index) {
		
		return _Keys[index];
		
	}
	
	public Integer pointer(Integer index) {
		
		return _Pointers[index];
		
	}
	
}
