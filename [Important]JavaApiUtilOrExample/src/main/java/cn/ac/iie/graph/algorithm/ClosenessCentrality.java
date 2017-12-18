package cn.ac.iie.graph.algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import cn.ac.iie.graph.datastructure.Edge;
import cn.ac.iie.graph.datastructure.Graph;

public class ClosenessCentrality {

	private double d[];
	private Graph graph;
	
	private double closeness[];
	
	private int nodeNum;
	
	public ClosenessCentrality() {
		
	}
	
	public ClosenessCentrality(Graph graph) {
		this.graph = graph;
		this.nodeNum = this.graph.getVertex().size();
		this.d = new double[this.nodeNum];
		this.closeness = new double[this.nodeNum];
	}
	
	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	/**
	 * 初始化d参数
	 */
	private void setInitialParams(int nodeIndex) {

		for(int i=0; i<this.nodeNum; i++) {
			this.d[i] = -1;
		}
		d[nodeIndex]=0;
	}
	
	public void calculateCloseness() {
		
		HashMap<Integer, HashMap<Integer, Double>> graphMap = this.graph.getGraphMap();
		
		for(Integer node: graphMap.keySet()) {
			setInitialParams(node);
			
			Queue<Integer> queue = new LinkedList<Integer>();
			queue.add(node);
			
			while(!queue.isEmpty()) {
				Integer cur = queue.remove();
				// XXX: 不连通的情况
				if(graphMap.containsKey(cur)) {
					HashMap<Integer, Double> toNodeMap = graphMap.get(cur);
					for (Integer toNode: toNodeMap.keySet()) {
						if (d[toNode]<=0) {
							d[toNode]=d[cur]+1;
							queue.add(toNode);
						}
					}
				}
			}
			
			for(int i=0; i<this.nodeNum; i++) {
				if (d[i]>0) {
					this.closeness[node] += d[i];
				}
			}
			this.closeness[node] = this.closeness[node]==0? 0: 
				(this.nodeNum-1)/this.closeness[node];
		}
		
	}
	
	public int getNodeNum() {
		return nodeNum;
	}

	public void setNodeNum(int nodeNum) {
		this.nodeNum = nodeNum;
	}
	
	public static void main(String[] args) {
		
		Graph graph = new Graph();
		graph.addEdge(new Edge(0,1));
		graph.addEdge(new Edge(0,2));
		graph.addEdge(new Edge(0,3));
		graph.addEdge(new Edge(0,5));
		graph.addEdge(new Edge(2,3));
		graph.addEdge(new Edge(3,4));
		graph.addEdge(new Edge(2,5));
		graph.addEdge(new Edge(5,0));
		
		ClosenessCentrality s= new ClosenessCentrality(graph);
		s.calculateCloseness();
		for(int i=0; i< s.closeness.length; i++) {
			double closeness = s.closeness[i];
			System.out.println( i + "\t"+ closeness);
		}
	}

	public double[] getD() {
		return d;
	}

	public void setD(double[] d) {
		this.d = d;
	}

	public double[] getCloseness() {
		return closeness;
	}

	public void setCloseness(double[] closeness) {
		this.closeness = closeness;
	}

}
