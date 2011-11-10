
public class ApplicationNode {
	
	private Dictionary<Integer, Server> _Tier1;
	
	private Dictionary<Integer, Server> _Tier2;
	
	private Dictionary<Integer, Server> _Tier3;
	
	public ApplicationNode() {
		
		setTier1(new LinkedBinarySearchTree<Integer, Server>());
		
		setTier2(new LinkedBinarySearchTree<Integer, Server>());
		
		setTier3(new LinkedBinarySearchTree<Integer, Server>());
		
	}
	
	private void setTier1(Dictionary<Integer, Server> dictionary) {
		
		_Tier1 = dictionary;
		
	}
	
	private void setTier2(Dictionary<Integer, Server> dictionary) {
		
		_Tier2 = dictionary;
		
	}
	
	private void setTier3(Dictionary<Integer, Server> dictionary) {
		
		_Tier3 = dictionary;
		
	}
	
	public Dictionary<Integer, Server> getTier1() {
		
		return _Tier1;
		
	}
	
	public Dictionary<Integer, Server> getTier2() {
		
		return _Tier2;
		
	}
	
	public Dictionary<Integer, Server> getTier3() {
		
		return _Tier3;
		
	}
	
	public void insert(Integer serverNumber, Integer tierNumber) {
		
		Dictionary<Integer, Server> tier;
		
		switch(tierNumber) {
		
		case 1:
			
			tier = getTier1();
			
			break;
			
		case 2:
			
			tier = getTier2();
			
			break;
			
		case 3:
			
			tier = getTier3();
			
			break;
			
		default:
		
			return;
			
		}
		
		Entry<Integer, Server> entry = tier.find(serverNumber);
		
		if (entry == null) tier.insert(serverNumber, new Server());
			
	}
	
	public String toString() {

		return "Tier 1: " + getTier1().size() + " Tier 2: " + getTier2().size() + " Tier 3: " + getTier3().size();
	
	}
	
}
