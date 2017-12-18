package Algorithm.graph.datastructure;

import java.util.ArrayList;

public class Edge {

	private String srcId = null;
	private String dstId = null;
	
	private double weight = 1.0;
	private long num = 0;

	private ArrayList<Object> attrList;
	
	public Edge(String srcId, String dstId) {
		this.srcId = srcId;
		this.dstId = dstId;
	}
	
	public Edge(String srcId, String dstId, long num) {
		this.srcId = srcId;
		this.dstId = dstId;
		this.num = num;
	}
	
	/**
	 * 已知一条边中某个顶点的id，求另外一个顶点的id
	 * @param id
	 * @return
	 */
	public String getAnotherVertexId(String id) {
		
		if(this.dstId.equals(id)) {
			return this.srcId;
		} else {
			return this.dstId;
		}
	}

	public String getSrcId() {
		return srcId;
	}

	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}

	public String getDstId() {
		return dstId;
	}

	public void setDstId(String dstId) {
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

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "Edge{" +
				"srcId='" + srcId + '\'' +
				", dstId='" + dstId + '\'' +
				", weight=" + weight +
				", num=" + num +
				", attrList=" + attrList +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Edge edge = (Edge) o;

		if (Double.compare(edge.weight, weight) != 0) return false;
		if (num != edge.num) return false;
		if (srcId != null ? !srcId.equals(edge.srcId) : edge.srcId != null) return false;
		if (dstId != null ? !dstId.equals(edge.dstId) : edge.dstId != null) return false;
		return attrList != null ? attrList.equals(edge.attrList) : edge.attrList == null;
	}

	@Override
	public int hashCode() {
		int result;
		result = srcId != null ? srcId.hashCode() : 0;
		result = 31 * result + (dstId != null ? dstId.hashCode() : 0);
		return result;
	}
}
