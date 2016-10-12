package com.cooksys.assessment;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListItems {
	
	// holds all the elements inside the JList
	@XmlElement
	ArrayList<String> items = new ArrayList<String>();
	
	// method to add to the ArrayList
	public void add(String item) {
		items.add(item);
	} // end add()
	
	// method to remove to the ArrayList
	public void remove(int index) {
		items.remove(index);
	} // end remove()
	
	// method to clear the ArrayList
	public void clear() {
		for(int i = 0; i < items.size(); i++)
			items.remove(i);
	} // end clear()
	
	// method to return the ArrayList
	public ArrayList<String> returnList() {
		return items;
	} // end returnList()
	
	// method to copy from an outside ArrayList
	public void copyList(ArrayList<String> items) {
		this.items = items;
	} // end copyList()
	
	// method to return the size of the ArrayList
	public int getSize() {
		return items.size();
	} // end getSize()
	
	// method to get a specific element from the ArrayList
	public String getElement(int index) {
		return items.get(index);
	} // end getSize()	
} // end class ListItems
