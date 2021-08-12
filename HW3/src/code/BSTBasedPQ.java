package code;

import java.util.Comparator;
import java.util.List;

import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement a binary search tree based priority queue
 * Do not try to create heap behavior (e.g. no need for a last node)
 * Just use default binary search tree properties
 */

public class BSTBasedPQ<Key, Value> extends BinarySearchTree<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {


  private Comparator<Key> comparator;
	
  @Override
  public void insert(Key k, Value v) {
    put(k,v);
  }

  @Override
  public Entry<Key, Value> pop() {
   if(isEmpty()) return null;
   BinaryTreeNode<Key,Value> node = root;
  
   while(isInternal(getLeftChild(node))) {
	   node = getLeftChild(node);
   }
   Entry<Key, Value> entry = new Entry<Key, Value>(node.getKey(), node.getValue());
   remove(node.getKey());
   return entry;
  }

  @Override
  public Entry<Key, Value> top() {
	  if(isEmpty()) return null;
	   BinaryTreeNode<Key,Value> node = root;
	   while(isInternal(getLeftChild(node))) {
		   node = getLeftChild(node);
	   }
	   return node;
  }

  @Override
  public Key replaceKey(Entry<Key, Value> entry, Key k) {
	  if(isEmpty()|| isExternal(getNode(entry.getKey()))) {
		  return null;
	  }
	  Key oldKey = entry.getKey();
	  if(getNode(oldKey).getValue().equals(entry.getValue())) { 
		  remove(oldKey);
		  insert(k,entry.getValue());
		  return oldKey;
    }
	  return null;
  }

  @Override
  public Key replaceKey(Value v, Key k) {
	  if(this.isEmpty()) return null;
	  Key oldKey= null;
	  List<BinaryTreeNode<Key, Value>> nodes = getNodesInOrder();
	  for(BinaryTreeNode<Key, Value> entry : nodes) {
		  if(entry.getValue().equals(v)) {
			  oldKey=entry.getKey();
			  remove(oldKey);
			  insert(k,v);
			  return oldKey;
		  }
	  }
	  return null;
  }

  @Override
  public Value replaceValue(Entry<Key, Value> entry, Value v) {
	  if(isEmpty()||isExternal(getNode(entry.getKey()))) {
		  return null;
	  }
	      Value oldVal = entry.getValue(); 
		  getNode(entry.getKey()).setValue(v);
		  return oldVal;
  }
}
