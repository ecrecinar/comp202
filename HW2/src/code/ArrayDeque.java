package code;

/* 
 * ASSIGNMENT 2
 * AUTHOR:  <Insert Student Name>
 * Class : ArrayDeque
 *
 * You are not allowed to use Java containers!
 * You must implement the Array Deque yourself
 *
 * MODIFY 
 * 
 * */

import given.iDeque;
import java.util.Arrays;
import java.util.Iterator;

import given.Util;


/*
 * You must have a circular implementation. 
 * 
 * WARNING: Modulo operator (%) might create issues with negative numbers.
 * Use Math.floorMod instead if you are having issues
 */
public class ArrayDeque<E> implements iDeque<E> {

  private E[] A; //Do not change this name!

  /*
   * ADD FIELDS IF NEEDED
   */
  int size=0;
  int first=-1;
  int last=-1;
  
	public ArrayDeque() {
		this(1000);
    /*
     * ADD CODE IF NEEDED
     */
	}
	
	public ArrayDeque(int initialCapacity) {
	   if(initialCapacity < 1)
	      throw new IllegalArgumentException();
		A = createNewArrayWithSize(initialCapacity);
		
		/*
		 * ADD CODE IF NEEDED
		 */
	}
	
	// This is given to you for your convenience since creating arrays of generics is not straightforward in Java
	@SuppressWarnings({"unchecked" })
  private E[] createNewArrayWithSize(int size) {
	  return (E[]) new Object[size];
	}
	
	//Modify this such that the dequeue prints from front to back!
	//Hint, after you implement the iterator, use that!
  public String toString() {
    
    /*
     * MODIFY THE BELOW CODE
     */
    
    if(isEmpty()) {
    	return "";
    } else {
    StringBuilder sb = new StringBuilder(1000);
    sb.append("[");
    Iterator<E> iter = this.iterator();
    while(iter.hasNext()) {
      E e = iter.next();
      if(e == null)
        continue;
      sb.append(e);
      if(!iter.hasNext())
        sb.append("]");
      else
        sb.append(", ");
    }
    return sb.toString();
    }
	
  }
	
  /*
   * ADD METHODS IF NEEDED
   */

  public void resize() {
	  E[] newArray = createNewArrayWithSize(A.length*2);
	  for(int i=0; i<= A.length-1;i++) {
		  int j= Math.floorMod(first+i,A.length);
		  newArray[i]= A[j];
		   
	  }
	  first=0;
	  A = newArray;
  }
  
  
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
   
   if(size==A.length) resize();

   if(isEmpty()) {
   		first=last=0;		
   } else if(first==0) {
   		first = A.length-1;
   } else {
   		first--;	
    }
   
   A[first]=o;
   size++;
  }

  @Override
  public E removeFront() {
    // TODO Auto-generated method stub
  
	  if(isEmpty())return null;
	  E temp = A[first];
	  if(size==1) {
		  first=-1;
		  last=-1;
	  }else {
		  first=Math.floorMod(first+1, A.length);
	  }
	  size--;
	 
	  return temp;

  }

  @Override
  public E front() {
    // TODO Auto-generated method stub
    if(isEmpty()) {
    	return null; 
    }	
    else {
    	return A[first];
    }
  }

  @Override
  public void addBehind(E o) {
    // TODO Auto-generated method stub
	  if(size==A.length) resize();
	   
	  if(isEmpty()) {
	   		first=last=0;		
	   } else if(last==A.length-1) {
	   		last = 0;
	   } else {
	   		last++;	
	    } 
	  
	  
	
	  A[last]= o;
	   size++;
	  
    
  }

  @Override
  public E removeBehind() {
    // TODO Auto-generated method stub

	  if(isEmpty()) return null;
	  
	  E temp = A[last];
	  if(size==1) {
		  first=-1;
		  last=-1;
	  } else {
		  last = Math.floorMod(last-1, A.length);
	  }
	   size--;
	  return temp;
	    
  }

  @Override
  public E behind() {
    // TODO Auto-generated method stub
	 if(isEmpty()) 
		 return null; 
	 else return A[last];
  }

  @Override
  public void clear() {
    // TODO Auto-generated method stub
    last=-1;
    first=-1;
    size=0;
  }
  
  //Must print from front to back
  @Override
  public Iterator<E> iterator() {
    // TODO Auto-generated method stub
    //Hint: Fill in the ArrayDequeIterator given below and return a new instance of it
    return new ArrayDequeIterator();
  }
  
  private final class ArrayDequeIterator implements Iterator<E> {
    
    /*
     * 
     * ADD A CONSTRUCTOR IF NEEDED
     * Note that you can freely access everything about the outer class!
     * 
     */
	 private int count=0; 
	 
	 private int index;
	 
	 public ArrayDequeIterator() {
		 index=first;
	 }
	 
    @Override
    public boolean hasNext() {
      // TODO Auto-generated method stub
      return  count<size;
    }

    @Override
    public E next() {
      // TODO Auto-generated method stub
    if(!hasNext()) {
    	return null;
    } else {
    	E elt = A[index];
    	index= Math.floorMod(index+1, A.length);
    	count++;
    	
    	return elt;
    }
    
    }        
  }
}
