package ncsu.csc.datastructures;

public class TreeNode<E> implements TreePosition<E> {

	private E _Element;

	private TreePosition<E> _Parent;

	private List<Position<E>> _Children;

	public TreeNode() { }

	public TreeNode(E element, TreePosition<E> parent, List<Position<E>> children) { 

		setElement(element);

		setParent(parent);

		setChildren(children);

	}

	public E element() {
		
		return _Element;
		
	}

	public void setElement(E element) {
		
		_Element = element;
		
	}

	public List<Position<E>> getChildren() {
		
		return _Children;
		
	}

	public void setChildren(List<Position<E>> children) {
		
		_Children=children;
		
	}

	public TreePosition<E> getParent() {
		
		return _Parent;
		
	}

	public void setParent(TreePosition<E> parent) {
		
		_Parent = parent;
	
	}

}
