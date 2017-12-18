package cn.ac.iie.graph.algorithm;

import java.util.HashMap;

import cn.ac.iie.graph.datastructure.Edge;
import cn.ac.iie.graph.datastructure.Graph;

public class DegreeCentrality {

	private Graph graph = null;
	
	public DegreeCentrality() {
		this.graph = new Graph();
	}
	
	public DegreeCentrality(Graph graph) {
		this.graph = graph;
	}
	
	/**
	 * 不带权无向图 活跃程度计算
	 * compute vertex degree centrality
	 * @return HashMap<Integer, Double> 返回节点id，节点度中心性值。
	 */
	public HashMap<Integer, Double> computeDegreeCentrality() {
		
		HashMap<Integer, Double> centralityMap = new HashMap<Integer, Double>();
		HashMap<Integer, Integer> degreeMap = this.graph.computeIndirectionDegree();
		
		int vertexNum = this.graph.getVertex().size();
		
		if(vertexNum==1) {
			return null;
		}
		
		for(Integer node: degreeMap.keySet()) {
			int degree = degreeMap.get(node);
			centralityMap.put(node, (double)degree/(vertexNum-1));
		}
		
		return centralityMap;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph graph = new Graph();
		graph.addEdge(new Edge(1,2));
		graph.addEdge(new Edge(1,4));
		graph.addEdge(new Edge(2,3));
		graph.addEdge(new Edge(3,4));
		graph.addEdge(new Edge(2,5));
		graph.addVertex(1);
		graph.addVertex(2);
		graph.addVertex(3);
		graph.addVertex(4);
		graph.addVertex(5);
		
		DegreeCentrality s= new DegreeCentrality(graph);
		HashMap<Integer, Double> map = s.computeDegreeCentrality();
		for(Integer i: map.keySet()) {
			System.out.println(i + "\t"+ map.get(i));
		}
	}

}
