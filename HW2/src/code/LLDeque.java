package code;

/* 
 * ASSIGNMENT 2
 * AUTHOR:  <Insert Student Name>
 * Class : LLDeque
 *
 * You are not allowed to use Java containers!
 * You must implement the linked list yourself
 * Note that it should be a doubly linked list
 *
 * MODIFY 
 * 
 * */

import given.iDeque;
import java.util.Iterator;
import given.Util;

//If you have been following the class, it should be obvious by now how to implement a Deque wth a doubly linked list
public class LLDeque<E> implements iDeque<E> {
  
  //Use sentinel nodes. See slides if needed
  private Node<E> header;
  private Node<E> trailer;
  
  
  private int size;
  
  /*
   * ADD FIELDS IF NEEDED
   */

  // The nested node class, provided for your convenience. Feel free to modify
  private class Node<T> {
    private T element;
    private Node<T> next;
    private Node<T> prev;
    /*
     * ADD FIELDS IF NEEDED
     */
    
     Node(T d, Node<T> n, Node<T> p) {
      element = d;
      next = n;
      prev = p;
    }
    
    public T getElement() {
    	return element;
    }
    public void setElement(T element) {
		this.element = element;
	}
    public Node<T> getPrev() {
		return prev;
	}
	public void setPrev(Node<T> prev) {
		this.prev = prev;
	}
	public Node<T> getNext() {
		return next;
	}
	public void setNext(Node<T> next) {
		this.next = next;
	}
	
	

    
    
    /*
     * ADD METHODS IF NEEDED
     */
  }
  
  public LLDeque() {
    //Remember how we initialized the sentinel nodes
    header  = new Node<E>(null, null, header);
    trailer = new Node<E>(null, trailer, header);
    header.next = trailer;
    
    size=0;
    /*
     * ADD CODE IF NEEDED
     */
  }
  
  public String toString() {
    if(isEmpty())
      return "";
    StringBuilder sb = new StringBuilder(1000);
    sb.append("[");
    Node<E> tmp = header.next;
    while(tmp.next != trailer) {
      sb.append(tmp.element.toString());
      sb.append(", ");
      tmp = tmp.next;
    }
    sb.append(tmp.element.toString());
    sb.append("]");
    return sb.toString();
  }
  
  /*
   * ADD METHODS IF NEEDED
   */
  
  /*
   * Below are the interface methods to be overriden
   */

  @Override
  public int size() {
    // TODO Auto-generated method stub
    return size;
  }

  @Override
  public boolean isEmpty() {
    // TODO Auto-generated method stub
   
    	return size==0;
  }

  @Override
  public void addFront(E o) {
    // TODO Auto-generated method stub
	Node<E> newNode = new Node<E>(o,header.next,header); 
	if(isEmpty()) {
		header.next=newNode;
		trailer.prev=newNode;
	} else {
		header.next.prev= newNode;
		header.next = newNode;
	}
	
	size++;
   
  }

  @Override
  public E removeFront() {
    // TODO Auto-generated method stub
	 
	  if(isEmpty()) {
	    	return null;
	    } else if(size==1) {
	    	Node<E> temp = header.next;
	    	trailer.prev=header;
	    	header.next=trailer;
	    	size--;
	    	return temp.element;
	    	
	    }else {
	    	Node<E> temp = header.next;
	    	
	    	header.next.next.prev = header;
	    	header.next = header.next.next;
	    	
	   size--;
    	return temp.element;
	    	
	    }
	    	
    	
  }

  @Override
  public E front() {
    // TODO Auto-generated method stub
    if(isEmpty()) {
    	return null;
    }
    else {
    	return header.getNext().getElement();
    }
  }

  @Override
  public void addBehind(E o) {
    // TODO Auto-generated method stub
    Node<E> newNode = new Node<E>(o,trailer,trailer.prev); 
    if(isEmpty()) {
		header.next=newNode;
		trailer.prev=newNode;
	} else {
		trailer.prev.next=newNode;
		trailer.prev= newNode;
	}
    
	size++;
    
  }

  @Override
  public E removeBehind() {
    // TODO Auto-generated method stub
   
    if(isEmpty()) {
    	return null;
    } else if(size==1) {
    	Node<E> temp = trailer.prev;
    	trailer.prev=header;
    	header.next=trailer;
    	size--;
    	return temp.element;
    	
    }
    else {
    
    	Node<E> temp = trailer.prev;
    	trailer.prev.prev.next = trailer;
    	trailer.prev = trailer.prev.prev;
    	
    	size--;
    	return temp.element;
    }
    
 }
    	
    
  

  @Override
  public E behind() {
    // TODO Auto-generated method stub
    if(isEmpty()) {
    	return null;
    }
   else {
	    return trailer.prev.element;
   }
   
  }

  @Override
  public void clear() {
    // TODO Auto-generated method stub
    header.next=trailer;
    trailer.prev=header;
    size=0;
  }
  
  @Override
  public Iterator<E> iterator() {
    // TODO Auto-generated method stub
    //Hint: Fill in the LLDequeIterator given below and return a new instance of it
    return new LLDequeIterator();
  }
  
  private final class LLDequeIterator implements Iterator<E> {
    
    /*
     * 
     * ADD A CONSTRUCTOR IF NEEDED
     * Note that you can freely access everything about the outer class!
     * 
     */
	 
	  
	private Node<E> cursor;
	private int index = 0;
	private Node<E> lastAccessed;
	
	LLDequeIterator(){ 
		cursor = header.next; 
		lastAccessed = null; 
		index = 0;
	}
	@Override
	public boolean hasNext() {
		return index < size; }
	@Override
	
	public E next () {
		if (!hasNext()){
			return null;
		}
		lastAccessed = cursor; 
		E elt = cursor.element;
		cursor = cursor.next; 
		index++;
		return elt ;
	}
        
  }
  
}
