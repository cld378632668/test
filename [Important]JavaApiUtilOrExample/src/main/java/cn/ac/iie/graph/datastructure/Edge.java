package cn.ac.iie.graph.datastructure;

import java.util.ArrayList;

public class Edge {

	private int srcId = 0;
	private int dstId = 0;
	
	private double weight = 1.0;
	private ArrayList<Object> attrList;
	
	public Edge(int srcId, int dstId) {
		this.srcId = srcId;
		this.dstId = dstId;
	}
	
	public Edge(int srcId, int dstId, double weight) {
		this.srcId = srcId;
		this.dstId = dstId;
		this.weight = weight;
	}
	
	/**
	 * 已知一条边中某个顶点的id，求另外一个顶点的id
	 * @param id
	 * @return
	 */
	public int getAnotherVertexId(int id) {
		
		if(this.dstId == id) {
			return this.srcId;
		} else {
			return this.dstId;
		}
	}

	public int getSrcId() {
		return srcId;
	}

	public void setSrcId(int srcId) {
		this.srcId = srcId;
	}

	public int getDstId() {
		return dstId;
	}

	public void setDstId(int dstId) {
		this.dstId = dstId;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public ArrayList<Object> getAttrList() {
		return attrList;
	}

	public void setAttrList(ArrayList<Object> attrList) {
		this.attrList = attrList;
	}
	
}
