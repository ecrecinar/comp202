package code;

import given.iPrintable;
import given.iSet;

/*
 * A set class implemented with hashing. Note that there is no "value" here 
 * 
 * You are free to implement this however you want. Two potential ideas:
 * 
 * - Use a hashmap you have implemented with a dummy value class that does not take too much space
 * OR
 * - Re-implement the methods but tailor/optimize them for set operations
 * 
 * You are not allowed to use any existing java data structures
 * 
 */

public class HashSet<Key> implements iSet<Key>, iPrintable<Key>{
  
	private HashMapDH<Key,String> set;
  // A default public constructor is mandatory!
  public HashSet() {
	  set = new HashMapDH<Key,String>();
  }
  
  /*
   * 
   * Add whatever you want!
   * 
   */

  @Override
  public int size() {
    // TODO Auto-generated method stub
    return set.size();
  }

  @Override
  public boolean isEmpty() {
    // TODO Auto-generated method stub
    return set.isEmpty();
  }

  @Override
  public boolean contains(Key k) {
    // TODO Auto-generated method stub
	 if(set.get(k)==null) return false; else return true;
  }

  @Override
  public boolean put(Key k) {
    // TODO Auto-generated method stub
	  if(set.put(k,"dummy")==null) return false; else return true;
  }

  @Override
  public boolean remove(Key k) {
    // TODO Auto-generated method stub
	  if(set.get(k)==null) {
		  return false; } 
	  else {
			  set.remove(k);
			  return true;
	  }
  }

  @Override
  public Iterable<Key> keySet() {
    // TODO Auto-generated method stub
    return set.keySet();
  }

  @Override
  public Object get(Key key) {
    // Do not touch
    return null;
  }

}
