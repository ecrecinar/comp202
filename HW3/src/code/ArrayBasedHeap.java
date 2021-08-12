package code;

import java.util.ArrayList;
import java.util.Comparator;

import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement an array based heap
 * Note that you can just use Entry here!
 * 
 */

public class ArrayBasedHeap<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {

	// Use this arraylist to store the nodes of the heap. 
	// This is required for the autograder. 
	// It makes your implementation more verbose (e.g. nodes[i] vs nodes.get(i)) but then you do not have to deal with dynamic resizing
	protected ArrayList<Entry<Key,Value>> nodes = new ArrayList<Entry<Key,Value>>();
	
	private Comparator<Key> comparator;
	
	public ArrayBasedHeap() { }
	
	protected int parentOf(int i) {
		return (i-1)/2;
	}

	protected int leftChildOf(int i) {
		return (2*i + 1);
	}

	protected int rightChildOf(int i) {
		return (2*i + 2);
	}

	protected boolean hasLeftChild(int i) {
		return(leftChildOf(i) < size());
	}

	protected boolean hasRightChild(int i) {
		return (rightChildOf(i) < size());
	}
	
	protected void upheap(int i) {
		while(i>0) {
			int p = parentOf(i);
			if(comparator.compare(nodes.get(i).getKey(), nodes.get(p).getKey())>=0) {
				break;
			}
			swap(i,p);
			i = p;
		}
	}

	protected void downheap(int i) {
		while(hasLeftChild(i)) {
			int smallChild = leftChildOf(i);
			if(hasRightChild(i)) {
				int rc=rightChildOf(i);
				if(comparator.compare(nodes.get(rc).getKey(), nodes.get(smallChild).getKey())<0) {
					smallChild = rc;
				}
			}
			if(comparator.compare(nodes.get(smallChild).getKey(), nodes.get(i).getKey())>=0) {
				break;
			}
			swap(i,smallChild);
			i=smallChild;
		}
	}

	protected void recDownheap(ArrayList<Entry<Key,Value>> nodes,int i,int k) {
		int ind = i;
		int lc = 2*i+1;
		int rc = 2*i+2;
		if (lc <= k && (comparator.compare(nodes.get(ind).getKey(), nodes.get(lc).getKey())>0)){
			ind =lc;
		}
		if (rc <= k && (comparator.compare(nodes.get(ind).getKey(), nodes.get(rc).getKey())>0)){
			ind = rc;
		}
		if(ind != i) {
			swap(i, ind); 
			recDownheap(nodes, ind, k);
		}
	}

	protected void heapify(ArrayList<Entry<Key,Value>> nodes) {
		for(int i=nodes.size()/2 ; i>=0 ;i--) {
			recDownheap(nodes,i,nodes.size()-1);
		}
	}

	protected void swap(int i,int j) {
		Entry<Key,Value> temp = nodes.get(i);
		nodes.set(i, nodes.get(j));
		nodes.set(j, temp);
	}

	@Override
	public int size() {
		return nodes.size();
	}

	@Override
	public boolean isEmpty() {
		return size()==0;
	}

	@Override
	public void setComparator(Comparator<Key> C) {
		comparator =C;
	}

	@Override
	public Comparator<Key> getComparator() {
		return comparator;
	}

	@Override
	public void insert(Key k, Value v) {
		int index=searchKey(k);
		if(index==-1) {
			Entry<Key,Value> entry = new Entry<Key,Value>(k,v);    
			nodes.add(entry);
			upheap(size()-1);
		} else {
			nodes.get(index).setValue(v);
		}
	}

	@Override
	public Entry<Key, Value> pop() {
		if(isEmpty()) {
			return null;
		} else {
			Entry<Key,Value> min = nodes.get(0);
			swap(0,size()-1);
			nodes.remove(size()-1);  
			downheap(0);
			return min;
		}
	}

	@Override
	public Entry<Key, Value> top() {
		if(isEmpty()) return null; else return nodes.get(0);
	}

	@Override
	public Value remove(Key k) {
		int index = searchKey(k);
		if(index==-1) return null;
		else {
			Entry<Key,Value> entry = nodes.get(index);
			nodes.remove(entry);
			heapify(nodes);
			return entry.getValue();
		}
	}

	protected int searchKey(Key k) {
		for(Entry<Key, Value> entry : nodes) { 
			if(comparator.compare(entry.getKey(),k)==0) {
				return nodes.indexOf(entry);
			}
		}
		return -1;
	}

	protected int searchValue(Value v) {
		for(Entry<Key, Value> entry : nodes) {
			if(entry.getValue().equals(v)) {
				return nodes.indexOf(entry);
			}
		}
		return -1;
	}

	@Override
	public Key replaceKey(Entry<Key, Value> entry, Key k) {
		int index = searchKey(entry.getKey());
		if(index==-1) return null;
		Key oldKey = entry.getKey();
		if(nodes.get(index).getValue().equals(entry.getValue())){
			nodes.get(index).setKey(k);
			heapify(nodes);
			return oldKey;
		}
		return null;
	}

	@Override
	public Key replaceKey(Value v, Key k) {
		int index = searchValue(v);
		if(index==-1) return null;
		Key oldKey = nodes.get(index).getKey();
		nodes.get(searchValue(v)).setKey(k);   
		heapify(nodes);
		return oldKey;
	}

	@Override
	public Value replaceValue(Entry<Key, Value> entry, Value v) {
		int index = searchKey(entry.getKey());
		if(index==-1) return null;
		if(nodes.get(index).getValue().equals(entry.getValue())) {
			Value oldVal = entry.getValue();
			nodes.get(index).setValue(v);
			return oldVal;
		}
		return null;	
	}
}
