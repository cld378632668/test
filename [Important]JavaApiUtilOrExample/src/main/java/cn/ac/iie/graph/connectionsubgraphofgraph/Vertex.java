package cn.ac.iie.graph.connectionsubgraphofgraph;

import java.util.ArrayList;

public class Vertex {

	boolean wasVisited;
	public String label;
	ArrayList<Integer> allVisitedList;

	public void setAllVisitedList(ArrayList<Integer> allVisitedList) {
		this.allVisitedList = allVisitedList;
	}

	public ArrayList<Integer> getAllVisitedList() {
		return allVisitedList;
	}

	public boolean WasVisited() {
		return wasVisited;
	}

	public void setWasVisited(boolean wasVisited) {
		this.wasVisited = wasVisited;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Vertex(String lab) // constructor
	{
		label = lab;
		wasVisited = false;
	}

	public void setVisited(int j) {
		allVisitedList.set(j, 1);

	}

}
