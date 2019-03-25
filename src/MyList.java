import interfaces.MyComparator;
import interfaces.MyConsumer;
import interfaces.MyIterator;
import interfaces.MyPredicate;

public class MyList<T> implements MyIterator<T>{
	private T[] arr;
	private int currentIndex = 0;
	
	@SuppressWarnings("unchecked")
	public MyList() {
		this.arr = (T[]) new Object[0];
	}
	
	public MyList(T[] arr) {
		this.arr = arr;
	}
	
	//Iterate the list and do the action specified by the user
	public void forEach(MyConsumer<T> consumer) {
		for (T t : this.arr) {
			consumer.action(t);
		}
	}
	
	//Add one more element in the last index of the array
	@SuppressWarnings("unchecked")
	public void push(T value) {
		T[] newArr = (T[]) new Object[arr.length + 1];		
		int i = 0;
		
		for (T v : this.arr) {
			newArr[i] = v; 
			i++;
		}
		newArr[newArr.length - 1] = value;
		this.arr = newArr;
	}
	
	//Add an element on a specified index, and push the other elements to the right (if its necessary)
	@SuppressWarnings("unchecked")
	public void add(int index, T value) {
		if(index == this.length()) {
			this.push(value);
			return;
		}
		if(index > this.length()) throw new ArrayIndexOutOfBoundsException();
		
		this.newSize(this.length()+1);
		T[] arrAux = (T[])new Object[this.length()];
		this.toArray(arrAux);
		
		for(int i = index; i < this.length() && i != this.length()-1; i++) {
			this.arr[i+1] = arrAux[i];
		}
		this.arr[index] = value;
	}
	
	@SuppressWarnings("unchecked")
	public void push(T... values) {
		this.merge(values);
	}
	
	@SuppressWarnings("unchecked")
	public void pop() {
		Object[] newArr = new Object[arr.length-1];	
		
		for (int i = 0; i < newArr.length; i++) {
			newArr[i] = this.arr[i]; 
		}
		this.arr = (T[]) newArr;
	}
	
	//Return a list containing all the elements of the intersection between two lists
	public MyList<T> intersection(MyList<T> list) {
		MyList<T> res = new MyList<T>();
		for (int i = 0; i < this.length(); i++) {
			for (int j = 0; j < list.length(); j++) {
				if(this.arr[i].equals(list.get(j)) && !res.contains(arr[i])) 
					res.push(arr[i]);	
			}
		}
		return res;
	}
	
	public T get(int i) {
		return this.arr[i];
	}
	
	public int length() {
		return this.arr.length;
	}
	
	public boolean contains(T value) {
		for (T i : this.arr) {
			if(value.equals(i)) return true;
		}
		return false;
	}
	
	//Replace the value of a specified index
	public void put(int i, T value) {
		if(i >= this.length())
			throw new ArrayIndexOutOfBoundsException();
		this.arr[i] = value;
	}
	
	//Returns a String containing all the elements of the list
	public String toString() {
		String statement = "[";
		int i = 0;
		for (T value : arr) {
			statement += value + (i == this.arr.length - 1 ? "" : ",");
			i++;
		}
		statement += "]";
		return statement;
	}
	
	//Both functions sort the list, either by default criterion or defined by the user (programer) 
	public void sort() {
		T aux;
		MyComparator<T> comparator = new MyComparator<T>() {};
		for (int i = 0; i < this.length(); i++) {
			for (int j = 0; j < this.length(); j++) {
				if(i == j) continue;
				else if(comparator.compare(this.arr[i], this.arr[j]) < 0){
					aux = this.arr[i];
					this.arr[i] = this.arr[j];
					arr[j] = aux;
				}
			}
		}
	}
	
	public void sort(MyComparator<T> comparator) {
		T aux;
		for (int i = 0; i < this.length(); i++) {
			for (int j = 0; j < this.length(); j++) {
				if(i == j) continue;
				else if(comparator.compare(this.arr[i], this.arr[j]) < 0){
					aux = this.arr[i];
					this.arr[i] = this.arr[j];
					this.arr[j] = aux;
				}
			}
		}
	}
	
	//Remove all the elements of the list
	@SuppressWarnings("unchecked")
	public void clear() {
		this.arr = (T[])new Object[0];
	}
	
	//Join elements of the list into a String
	public String implode(String glue) {
		String acm = "";
		for (int i = 0; i < this.length(); i++) {
			acm += this.arr[i] + (i == this.length() - 1 ? "" : glue);
		}
		return acm;
	}
	
	//Search for the first occurence of a value, and return its index.
	//If it wasnt found, returns -1 instead.
	public int indexOf(T t) {
		if(t.equals(null)) return -1;
		for (int i = 0; i < this.length(); i++) {
			if(this.get(i).equals(t)) return i;
		}
		return -1;
	}
	
	//Return a copy of the list as an Array of objects 
	public Object[] toArray() {
		Object[] newArr = new Object[this.length()];
		for(int i = 0; i < this.length(); i++) newArr[i]  = this.arr[i];
		return newArr;
	}
	
	//Copy the list to the provided array 
	public T[] toArray(T[] t) {
		for (int i = 0; i < t.length; i++) t[i] = this.arr[i];
		return t;
	}
	
	//Remove elements of the list depending on the filter defined by the user
	public void removeIf(MyPredicate<T> predicate) {
		for (int i = 0; i < this.length(); i++) {
			if(predicate.filter(this.get(i))) {
				this.remove(i);
				i--;
			}
		}
	}
	
	//Merge two lists
	public void merge(MyList<T> list) {
		for (int i = 0; i < list.length(); i++) {
			this.push(list.get(i));
		}
	}
	
	//Merge a list to an array
	public void merge(T[] list) {
		for (int i = 0; i < list.length; i++) {
			this.push(list[i]);
		}
	}
	
	//Removes the element at the specified position in this list. Shifts any subsequent elements to the left.
	@SuppressWarnings("unchecked")
	public void remove(int index) {
		if(index < 0 || index >= this.length()) {
			throw new IndexOutOfBoundsException();
		}
		
		int size = this.length();
		T[] newArr = (T[])new Object[size-1];
		
		for(int i = 0; i < size-1; ++i) {
			newArr[i] = this.arr[i];
			if(i >= index) {
				newArr[i] = this.arr[i+1];
			}
		}
		this.arr = newArr;
	}

	@Override
	public boolean hasNext() {
		return (this.currentIndex < this.length());
	}

	@Override
	public T next() {
		T statement = this.arr[this.currentIndex];
		this.currentIndex++;
		return statement;
	}
	
	public static MyList<Integer> asList(Integer... nums){
		return new MyList<Integer>(nums);
	}
	
	public static MyList<String> asList(String... strs){
		return new MyList<String>(strs);
	}
	
	public static MyList<Character> asList(Character... characs){
		return new MyList<Character>(characs);
	}
	
	public static MyList<Double> asList(Double... nums){
		return new MyList<Double>(nums);
	}
	
	public boolean isEmpty() {
		return (this.length() <= 0);
	}
	
	@SuppressWarnings("unchecked")
	private void newSize(int size) {
		T[] newArr = (T[]) new Object[size];
		for(int i = 0; i < (size > length() ? this.length() : size); i++) newArr[i] = this.arr[i];
		this.arr = newArr;
	}
}