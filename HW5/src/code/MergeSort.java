package code;

import java.util.Arrays;

import given.AbstractArraySort;

/*
 * Implement the merge-sort algorithm here. You can look at the slides for the pseudo-codes.
 * Make sure to use the swap and compare functions given in the AbstractArraySort!
 * 
 * You may need to create an Array of K (Hint: the auxiliary array). 
 * Look at the previous assignments on how we did this!
 * 
 */

public class MergeSort<K extends Comparable<K>> extends AbstractArraySort<K> {

  // Add any fields here
	
	public K[] auxA;
	
  public MergeSort() {
    name = "Mergesort";

    // Initialize anything else here
  }

  @Override
  public void sort(K[] inputArray) {
    // TODO: Implement the merge-sort algorithm
	  int N = inputArray.length;
	  if(N < 1)
	      throw new IllegalArgumentException();
	  auxA = createNewArrayWithSize(N);
	  mergesort(inputArray,0,inputArray.length-1);
  }
  
  public void mergesort(K[] array,int lo,int hi) {
	  if(lo < hi) {
		  int mid = (lo + hi)/2;
		  mergesort(array,lo,mid);
		  mergesort(array,mid+1,hi);
		  merge(array,lo,mid,hi);
	  }
  }

  // Public since we are going to check its output!
  public void merge(K[] inputArray, int lo, int mid, int hi) {
    // TODO: Implement the merging algorithm
	  deepCopy(inputArray,auxA,lo,hi);
	  int i = lo;
	  int k = lo;
	  int j = mid+1;
	  while(k <= hi) {
		  if(j>hi || (i<=mid && compare(auxA[i],auxA[j])<=0)) {
			  inputArray[k]= auxA[i];
			  i++; k++;
		  } else {
			  inputArray[k]= auxA[j];
			  j++; k++;
		  }
	  }
  }
  
  public void deepCopy(K[] input, K[] aux, int lo, int hi) {
	  for(int i=lo ; i<= hi ; i++) {
		  aux[i]=input[i];
	  }
  }
  
  @SuppressWarnings("unchecked")
private K[] createNewArrayWithSize(int size) {
	  return (K[]) new Comparable[size];
	}
  // Feel free to add more methods
}
