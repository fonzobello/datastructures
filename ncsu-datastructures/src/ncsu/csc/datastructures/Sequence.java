package ncsu.csc.datastructures;
public interface Sequence<E> extends Vector<E>, List<E> {

	public Position<E> positionAtRank(int rank) throws BoundaryViolationException;

	public int rankOf(Position<E> position) throws InvalidPositionException;

}
