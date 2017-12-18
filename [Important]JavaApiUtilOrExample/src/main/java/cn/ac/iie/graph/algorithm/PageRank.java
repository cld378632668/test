package cn.ac.iie.graph.algorithm;

import java.util.HashMap;
import java.util.HashSet;

import cn.ac.iie.graph.datastructure.Edge;
import cn.ac.iie.graph.datastructure.Graph;

public class PageRank {

	private double pagerank[];
	private Graph graph;
	
	private int nodeNum;
	private double dampingFactor = 0.85;
	private int maxIteration = 100;
	private double minEps = 0.001;
	
	public PageRank() {
		pagerank = new double[0];
	}
	
	public PageRank(Graph graph) {
		this.graph = graph;
	}
	
	public double[] getPagerank() {
		return pagerank;
	}

	public void setPagerank(double[] pagerank) {
		this.pagerank = pagerank;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	/**
	 * 初始化PageRank参数
	 */
	private void setInitialParams() {
		
		this.nodeNum = this.graph.getVertex().size();
		this.pagerank = new double[this.nodeNum];
		
		for(int i=0; i<this.nodeNum; i++) {
			this.pagerank[i] = 1.0/this.nodeNum;
		}
		
	}
	
	public void runPageRank() {
		
		setInitialParams();
		
		double dampingValue = (1-this.dampingFactor)/this.nodeNum;
		
		HashMap<Integer, HashSet<Edge>> allNodeInEdgeMap;
		
		graph.computeAllNodeInEdges();
		allNodeInEdgeMap = graph.getInEdgeMap();//内部已经对有向图和无向图做了处理
			
		System.out.println(allNodeInEdgeMap);
		
		for(int i=0; i< this.maxIteration; i++) {
			
			double delta = 0;
			System.out.println(">>>>>>>>>>>>>>>>>>>>iteration"+i);
			for(double pr: pagerank) {
				System.out.print(pr+" ");
			}
			System.out.println();
			
			for(Integer node: graph.getVertex()) {
				if(!allNodeInEdgeMap.containsKey(node)) {
					continue;
				}
				HashSet<Edge> inEdgeSet = allNodeInEdgeMap.get(node);
				
				double prSum = 0;
				
				for(Edge e: inEdgeSet) {
					prSum += this.pagerank[e.getSrcId()]/graph.getGraphMap().get(e.getSrcId()).size();
				}
				double temp = dampingValue + dampingFactor * prSum;
				delta += Math.abs(this.pagerank[node] - temp);
				this.pagerank[node] = temp;
			}

			if(delta < this.minEps) {
				System.out.println("PR Iteration stops at "+ (i+1) +"'s iteration!!");
				return;
			}
		}
		
		System.out.println("PR Iteration stops at " + this.maxIteration + "'s iteration...");
		
	}
	
	public int getNodeNum() {
		return nodeNum;
	}

	public void setNodeNum(int nodeNum) {
		this.nodeNum = nodeNum;
	}

	public double getDampingFactor() {
		return dampingFactor;
	}

	public void setDampingFactor(double dampingFactor) {
		this.dampingFactor = dampingFactor;
	}

	public int getMaxIteration() {
		return maxIteration;
	}

	public void setMaxIteration(int maxIteration) {
		this.maxIteration = maxIteration;
	}

	public double getMinEps() {
		return minEps;
	}

	public void setMinEps(double minEps) {
		this.minEps = minEps;
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
		
		PageRank s= new PageRank(graph);
		s.runPageRank();
		for(int i=0; i< s.getPagerank().length; i++) {
			double pr = s.getPagerank()[i];
			System.out.println( i + "\t"+ s.pagerank[i]);
		}
	}

}
