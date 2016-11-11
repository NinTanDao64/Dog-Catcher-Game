package com.mycompany.a3;

import java.util.ArrayList;

//Generalized 'Collection' class for GameObjects with an Iterator hard-coded into it
public class GameObjectsCollection implements ICollection {
	private ArrayList theCollection;
	
	public GameObjectsCollection() {
		theCollection = new ArrayList();
	}
	
	public void add(Object newObject) {
		theCollection.add(newObject);
	}
	
	public ArrayList getArrayList() {
		return theCollection;
	}
	
//-------------------------------- Iterator ---------------------------------------
	
	private class ArrayListIterator implements IIterator {
		private int currIndex;
		private boolean removeOK = false;
			
		public ArrayListIterator() {
				currIndex = -1;
		}
			
		public boolean hasNext() {
			if (theCollection.size() <= 0) return false;
			if (currIndex == theCollection.size() - 1) return false;
				removeOK = true;
				return true;
		}
			
		public Object getNext() {
			currIndex++;
			return(get(currIndex));
		}
			
		public void remove() {
			if (removeOK = true) {
				theCollection.remove(currIndex);
				currIndex--;
			}
		}
	}
		
//----------------------------END ITERATOR SECTION---------------------------------	
		
	public IIterator getIterator() {
		return new ArrayListIterator();
	}
	
	public void removeObject(int index) {
		theCollection.remove(index);
	}
	
	public Object get(int index) {
		return theCollection.get(index);
	}
	
	public int size() {
		return theCollection.size();
	}
}
