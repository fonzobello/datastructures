package ncsu.csc.datastructures;
public interface TreePosition<E> extends Position<E> {

	public void setElement(E element);

	public List<Position<E>> getChildren(); 

	public void setChildren(List<Position<E>> children);

	public TreePosition<E> getParent(); 

	public void setParent(TreePosition<E> parent);
	
}

