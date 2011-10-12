package ncsu.csc.datastructures;
import java.util.Comparator;
import java.util.Iterator;
public class BinarySearchTreeMap<K,V> extends LinkedBinaryTree<Entry<K,V>> implements Map<K,V> {
protected Comparator<K> C;
protected Position<Entry<K,V>> actionPos;
protected int numEntries = 0;
public BinarySearchTreeMap()  { 
C = new DefaultComparator<K>(); 
addRoot(null);
}
public BinarySearchTreeMap(Comparator<K> c)  { 
C = c; 
addRoot(null);
}
protected static class BSTEntry<K,V> implements Entry<K,V> {
protected K key;
protected V value;
protected Position<Entry<K,V>> pos;
BSTEntry() { /* default constructor */ }
BSTEntry(K k, V v, Position<Entry<K,V>> p) { 
key = k; value = v; pos = p;
}
public K getKey() { return key; }
public V getValue() { return value; }
public Position<Entry<K,V>> position() { return pos; }
}
protected K key(Position<Entry<K,V>> position)  {
return position.element().getKey();
}
protected V value(Position<Entry<K,V>> position)  { 
return position.element().getValue(); 
}
protected Entry<K,V> entry(Position<Entry<K,V>> position)  { 
return position.element();
}
protected V replaceEntry(Position <Entry<K,V>> pos, Entry<K,V> ent) {
((BSTEntry<K,V>) ent).pos = pos;
return replace(pos, ent).getValue();
}
protected void checkKey(K key) throws InvalidKeyException {
if(key == null) throw new InvalidKeyException("null key");
}
protected void checkEntry(Entry<K,V> ent) throws InvalidEntryException {
if(ent == null || !(ent instanceof BSTEntry)) throw new InvalidEntryException("invalid entry");
}
protected Entry<K,V> insertAtExternal(Position<Entry<K,V>> v, Entry<K,V> e) {
expandExternal(v,null,null);
replace(v, e);
numEntries++;
return e;
}
protected void removeExternal(Position<Entry<K,V>> v) {
removeAboveExternal(v);
numEntries--;
}
protected Position<Entry<K,V>> treeSearch(K key, Position<Entry<K,V>> pos) {
if (isExternal(pos)) return pos;
else {
K curKey = key(pos);
int comp = C.compare(key, curKey);
if (comp < 0) return treeSearch(key, left(pos));
else if (comp > 0)
return treeSearch(key, right(pos));
return pos;
}
}
public int size() { return numEntries; }
public boolean isEmpty() { return size() == 0; }
public V get(K key) throws InvalidKeyException {
checkKey(key);
Position<Entry<K,V>> curPos = treeSearch(key, root());
actionPos = curPos;
if (isInternal(curPos)) return value(curPos);
return null;
}
public V put(K k, V x) throws InvalidKeyException {
checkKey(k);
Position<Entry<K,V>> insPos = treeSearch(k, root());
BSTEntry<K,V> e = new BSTEntry<K,V>(k, x, insPos);
actionPos = insPos;
if (isExternal(insPos)) {
insertAtExternal(insPos, e).getValue();
return null;
}
return replaceEntry(insPos, e);
  }
public V remove(K k) throws InvalidKeyException  {
checkKey(k);
Position<Entry<K,V>> remPos = treeSearch(k, root());
if (isExternal(remPos)) return null;
Entry<K,V> toReturn = entry(remPos);
if (isExternal(left(remPos))) remPos = left(remPos);
else if (isExternal(right(remPos))) remPos = right(remPos);
else {
Position<Entry<K,V>> swapPos = remPos;
remPos = right(swapPos);
do
remPos = left(remPos);
while (isInternal(remPos));
replaceEntry(swapPos, (Entry<K,V>) parent(remPos).element());
}
actionPos = sibling(remPos);
removeExternal(remPos);
return toReturn.getValue();
}
public Iterable<K> keySet() {
PositionList<K> keys = new NodePositionList<K>();
Iterable<Position<Entry<K,V>>> positer = positions();
for (Position<Entry<K,V>> cur: positer)
if (isInternal(cur))
keys.addLast(key(cur));
return keys;
}
public Iterable<V> values() {
PositionList<V> vals = new NodePositionList<V>();
Iterable<Position<Entry<K,V>>> positer = positions();
for (Position<Entry<K,V>> cur: positer)
if (isInternal(cur))
vals.addLast(value(cur));
return vals;
}
public Iterable<Entry<K,V>> entrySet() {
PositionList<Entry<K,V>> entries = new NodePositionList<Entry<K,V>>();
Iterable<Position<Entry<K,V>>> positer = positions();
for (Position<Entry<K,V>> cur: positer)
if (isInternal(cur))
entries.addLast(cur.element());
return entries;
}
protected Position<Entry<K,V>> restructure(Position<Entry<K,V>> x) { 
BTPosition<Entry<K,V>> a, b, c, t1, t2, t3, t4;
Position<Entry<K,V>> y = parent(x);	// assumes x has a parent
Position<Entry<K,V>> z = parent(y);	// assumes y has a parent
boolean xLeft = (x == left(y));
boolean yLeft = (y == left(z));
BTPosition<Entry<K,V>> xx = (BTPosition<Entry<K,V>>)x, 
yy = (BTPosition<Entry<K,V>>)y, zz = (BTPosition<Entry<K,V>>)z;
if (xLeft && yLeft) { 
a = xx; b = yy; c = zz; 
t1 = a.getLeft(); t2 = a.getRight(); t3 = b.getRight(); t4 = c.getRight();
}
else if (!xLeft && yLeft) { 
a = yy; b = xx; c = zz; 
t1 = a.getLeft(); t2 = b.getLeft(); t3 = b.getRight(); t4 = c.getRight();
}
else if (xLeft && !yLeft) { 
a = zz; b = xx; c = yy; 
t1 = a.getLeft(); t2 = b.getLeft(); t3 = b.getRight(); t4 = c.getRight();
}
else {
a = zz; b = yy; c = xx; 
t1 = a.getLeft(); t2 = b.getLeft(); t3 = c.getLeft(); t4 = c.getRight();
}
if (isRoot(z)) {
root = b;
b.setParent(null);
}
else {
BTPosition<Entry<K,V>> zParent = (BTPosition<Entry<K,V>>)parent(z);
if (z == left(zParent)) {
b.setParent(zParent);
zParent.setLeft(b);
}
else {
b.setParent(zParent);
zParent.setRight(b);
}
}
b.setLeft(a);
a.setParent(b);
b.setRight(c);
c.setParent(b);
a.setLeft(t1);
t1.setParent(a);
a.setRight(t2);
t2.setParent(a);
c.setLeft(t3);
t3.setParent(c);
c.setRight(t4);
t4.setParent(c);
((BSTEntry<K,V>) a.element()).pos = a;
((BSTEntry<K,V>) b.element()).pos = b;
((BSTEntry<K,V>) c.element()).pos = c;
return b;
}
} 	
