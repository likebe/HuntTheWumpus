/*
11/28/2016
Kebing li
PQHeap.java
*/

import java.util.Comparator;
import java.util.ArrayList;

public class PQHeap<T>{

	private ArrayList<T> heap;
	private int size;
	private Comparator<T> comparator;
	
	// constructor method
	public PQHeap( Comparator<T> comparator ){
	
		this.heap = new ArrayList<T>(); // array-based data structure
		this.size = 0;		
		this.comparator = comparator;
	}
	
	// return the size of the heap
	public int size(){ return this.size; }
	
	// add an object to the heap
	public void add( T obj ){
	
		this.heap.add(obj);
		this.reshapeBot();
		this.size++;
	}
	
	public T get(){
		return this.heap.get(0);
	}

	// remove the first one and reshape the heap
	public T remove(){		
	
		if (this.size == 0){
			return null;
		}
		
		else if (this.size == 1){
			T val = heap.get(0);
			heap.remove(this.size-1); // recursion
			size--;
			return val;
		}
		
		else{	
			T val = heap.get(0);
			this.swap(0, this.size - 1);
			this.reshapeTop();
			heap.remove(this.size - 1); // recursion
			size--;
			return val;
		}
	
	}
	
	// swap the objects at index1 and index2
	private void swap( int index1, int index2 ){
	
		T temp = this.heap.get(index1);
		this.heap.set( index1, this.heap.get(index2) );
		this.heap.set( index2, temp );
	}
	
	// reshape from bottom to top
	private void reshapeBot(){
		
		int index = this.size();
		int parent = (index - 1) / 2; // parent index
		if(index!=0){
			// reshaping from bottom
			while( comparator.compare( this.heap.get(index), this.heap.get(parent) ) > 0 ){
				this.swap( index, parent );
				index = parent;
				parent = (index - 1)/2;
			}
		}	
	}
	
	
	// reshape from top to bottom
	private void reshapeTop(){
		
		int parent = 0;
    	int left = parent * 2 + 1;
    	int right =parent * 2 + 2;
    	// reshaping from top
    	while(right < this.size){
    		
    		if ( comparator.compare(heap.get(left), heap.get(right)) > 0){
				this.swap(left, parent);
				parent = left;
				left = parent * 2 + 1;
				right = parent * 2 + 2;
			}
    		
    		else if ( comparator.compare(heap.get(right), heap.get(parent)) > 0){
    			this.swap(right, parent);
				parent = right;
				left = parent * 2 + 1;
				right = parent * 2 + 2;
    		}    		
    		
    		else{
    			break;
    		}	  	
    	}
	}
	
	// print out the heap
	public void print(){
		for( int i=0; i<this.size; i++ ){
			System.out.println( this.heap.get(i) );
		}
	}
	
	// print first ten in the heap
	public void printTen(){
	
		int time = 10;
		while(size>0 && time>0){
			System.out.println(remove());
			time--;
		}
	}
}