package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import given.iMap;
import given.iBinarySearchTree;

/*
 * Implement a vanilla binary search tree using a linked tree representation
 * Use the BinaryTreeNode as your node class
 */

public class BinarySearchTree<Key, Value> implements iBinarySearchTree<Key, Value>, iMap<Key, Value> {

	private int size=0;
	protected BinaryTreeNode<Key,Value> root= new BinaryTreeNode<Key,Value>(null,null);
	private Comparator<Key> comparator;
	
	
	public BinarySearchTree() { }

	private BinaryTreeNode<Key,Value> treeSearch(Key k,BinaryTreeNode<Key,Value> node) {
		if(isExternal(node)) {
			return node;
		} 
		if(comparator.compare(k, node.getKey()) <0) {
			return treeSearch(k,getLeftChild(node));
		} else if(comparator.compare(k, node.getKey()) ==0) {
			return node;
		} else {
			return treeSearch(k,getRightChild(node));
		}
	}

	@Override
	public Value get(Key k) {
		return getValue(k);
	}

	@Override
	public Value put(Key k, Value v) {
		BinaryTreeNode<Key,Value>  node = treeSearch(k,root);

		if(isExternal(node)) {
			expandExternal(node);
			node.setKey(k);
			node.setValue(v);
			size++;
			return null;
		}

		Value oldValue = node.getValue();
		node.setValue(v);
		return oldValue;

	}


	private void expandExternal(BinaryTreeNode<Key,Value> node) {
		BinaryTreeNode<Key, Value> left = new BinaryTreeNode<Key, Value>(null, null);
		BinaryTreeNode<Key, Value> right = new BinaryTreeNode<Key, Value>(null, null);
		left.setParent(node);
		right.setParent(node);
		node.setLeftChild(left);
		node.setRightChild(right);
	}


	@Override
	public Value remove(Key k) {

		BinaryTreeNode<Key,Value> node = treeSearch(k,root);
		if(isEmpty()|| isExternal(node)) {
			return null;
		} 
		Value val = node.getValue();

		if(isExternal(getLeftChild(node)) && isExternal(getRightChild(node))) { 
			node.setKey(null);
			node.setValue(null);
			node.setLeftChild(null);
			node.setRightChild(null);

		}

		else if(isInternal(getLeftChild(node)) && isInternal(getRightChild(node))) {

			BinaryTreeNode<Key,Value> successor = getRightChild(node);
			while(isInternal(getLeftChild(successor))) {
				successor=getLeftChild(successor);
			}

			node.setKey(successor.getKey());
			node.setValue(successor.getValue());

			if(isLeftChild(successor)) {
				getRightChild(successor).setParent(getParent(successor));
				getParent(successor).setLeftChild(getRightChild(successor));

			} else {
				getRightChild(successor).setParent(getParent(successor));
				getParent(successor).setRightChild(getRightChild(successor));

			}


			successor = null;


		} else {

			if(isRoot(node)){
				if(isExternal(getLeftChild(node))){
					root=getRightChild(node);
					root.setParent(null);

				} else {
					root=getLeftChild(node);
					root.setParent(null);
				}
			}
			else if(isRightChild(node)) {

				if(isExternal(getLeftChild(node))) {
					getRightChild(node).setParent(getParent(node));
					getParent(node).setRightChild(getRightChild(node));	  
					node=null;

				}
				else {
					getLeftChild(node).setParent(getParent(node));
					getParent(node).setRightChild(getLeftChild(node));	  
					node=null;
				}
			}

			else {
				if(isExternal(getRightChild(node))) {
					getLeftChild(node).setParent(getParent(node));
					getParent(node).setLeftChild(getLeftChild(node));	  
					node=null;

				}
				else {
					getRightChild(node).setParent(getParent(node));
					getParent(node).setLeftChild(getRightChild(node));	  
					node=null;
				}
			}
		}
		size--;
		return val; 
	}

	@Override
	public Iterable<Key> keySet() {
		List<Key> keys = new ArrayList<Key>();
		List<BinaryTreeNode<Key, Value>> nodes =  getNodesInOrder();
		for(BinaryTreeNode<Key,Value> node :nodes) {
			keys.add(node.getKey());
		}
		return keys;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public BinaryTreeNode<Key, Value> getRoot() {
		return root;
	}

	@Override
	public BinaryTreeNode<Key, Value> getParent(BinaryTreeNode<Key, Value> node) {
		return node.getParent();
	}

	@Override
	public boolean isInternal(BinaryTreeNode<Key, Value> node) {
		return (!isExternal(node));
	}

	@Override
	public boolean isExternal(BinaryTreeNode<Key, Value> node) {
		return (node.getKey()==null);
	}

	@Override
	public boolean isRoot(BinaryTreeNode<Key, Value> node) {
		return node==root;
	}

	@Override
	public BinaryTreeNode<Key, Value> getNode(Key k) {
		BinaryTreeNode<Key, Value> node = treeSearch(k,root);
		return node; 
	}

	@Override
	public Value getValue(Key k) {
		BinaryTreeNode<Key, Value> node = treeSearch(k,root);
		if(isExternal(node)) return null; else 
			return node.getValue(); 
	}

	@Override
	public BinaryTreeNode<Key, Value> getLeftChild(BinaryTreeNode<Key, Value> node) {
		return node.getLeftChild();
	}

	@Override
	public BinaryTreeNode<Key, Value> getRightChild(BinaryTreeNode<Key, Value> node) {
		return node.getRightChild();
	}

	@Override
	public BinaryTreeNode<Key, Value> sibling(BinaryTreeNode<Key, Value> node) {
		if(isRoot(node)) {
			return null;
		}
		else if(isLeftChild(node)) {
			return getRightChild(getParent(node));
		} else {
			return getLeftChild(getParent(node));
		}
	}

	@Override
	public boolean isLeftChild(BinaryTreeNode<Key, Value> node) {
		if(isRoot(node)) return false; else 
			return(node.getParent().getLeftChild()==node);	

	}

	@Override
	public boolean isRightChild(BinaryTreeNode<Key, Value> node) {
		if(isRoot(node)) return false; else 
			return(node.getParent().getRightChild()==node);	

	}

	@Override
	public List<BinaryTreeNode<Key, Value>> getNodesInOrder() {
		List<BinaryTreeNode<Key, Value>> nodes = new ArrayList<BinaryTreeNode<Key, Value>>();

		if(!isEmpty()) {
			inorderTraversal(root,nodes);
		}
		return nodes;
	}

	private void inorderTraversal(BinaryTreeNode<Key, Value> node, List<BinaryTreeNode<Key, Value>> list) {
		if(!isExternal(getLeftChild(node))) {
			inorderTraversal(getLeftChild(node), list);
		} 
		list.add(node);
		if(!isExternal(getRightChild(node))) {
			inorderTraversal(getRightChild(node), list);
		}
	}

	@Override
	public void setComparator(Comparator<Key> C) {
		comparator = C;
	}

	@Override
	public Comparator<Key> getComparator() {
		return comparator;
	}

	@Override
	public BinaryTreeNode<Key, Value> ceiling(Key k) {
		BinaryTreeNode<Key, Value> node = treeSearch(k,root);
		BinaryTreeNode<Key, Value> ceiling =null; 

		if(isExternal(node)) {
			if(isLeftChild(node)) ceiling=node.getParent();   
			else {
				if(isRightChild(node.getParent())) {
					while(isRightChild(node.getParent())) {
						node= node.getParent();
					}
					if(!isRoot(node.getParent())) ceiling = node.getParent().getParent();
				}
				else {
					ceiling=node.getParent().getParent();
				}
			}
		}
		else {
			if(isInternal(getRightChild(node))) {
				node=getRightChild(node);
				while(isInternal(getLeftChild(node))) {
					node=getLeftChild(node);
				}
				ceiling=node;   
			}
			else {
				if(isRightChild(node)){
					while(isRightChild(node)) {
						node= node.getParent();
					}
					if(!isRoot(node)) ceiling = node.getParent(); 
					else {
						while(isInternal(getRightChild(node))) {
							node=node.getRightChild();
						}
						ceiling=node;
					}
				} else {
					ceiling = node.getParent();
				}
			}
		}
		return ceiling;


	}

	@Override
	public BinaryTreeNode<Key, Value> floor(Key k) {
		BinaryTreeNode<Key, Value> node = treeSearch(k,root);

		if(isExternal(node)) {

		} else {
			if(isInternal(getLeftChild(node))) {
				while(isInternal(getLeftChild(node))) {
					node=getLeftChild(node);
				}
				return node;
			}

		}
		if(isExternal(node) && isRightChild(node)) return node.getParent();
		else if(isExternal(node) && isLeftChild(node)) {

		} 
		while(!isRoot(node)) {
			if(isRightChild(node)) {
				return node.getParent();
			} else {
				node = node.getParent();
			}
		}
		return null;

	}
}
