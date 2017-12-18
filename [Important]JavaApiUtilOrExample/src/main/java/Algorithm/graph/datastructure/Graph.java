package Algorithm.graph.datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Graph {

	// 边列表 按单向存
	private ArrayList<Edge> edge = null;
	// 顶点集合
	private HashSet<String> vertex = null;

	// 便于各种图的遍历
	private HashMap<String, HashMap<String, Double>> graphMap = null;

	// 节点对应入边集合的Map
	private HashMap<String, HashSet<Edge>> inEdgeMap = null;

	// 默认无向图
	private boolean isDirected = false;

	/**
	 * construction function
	 * no parameter
	 */
	public Graph() {
		this.edge = new ArrayList<Edge>();
		this.vertex = new HashSet<String>();
		this.graphMap = new HashMap<String, HashMap<String, Double>>();
	}

	/**
	 * construction
	 * @param edge
	 * @param vertex
	 */
	public Graph(ArrayList<Edge> edge, HashSet<String> vertex) {

		this.edge = edge;
		this.vertex = vertex;
		this.graphMap = new HashMap<String, HashMap<String, Double>>();

		//如果是有向图，只需要存src->dst 的边
		for(Edge e: edge) {
			addEdge2graphMap(e);
		}
	}


	public HashMap<String, HashSet<Edge>> getInEdgeMap() {
		return inEdgeMap;
	}

	public void setInEdgeMap(HashMap<String, HashSet<Edge>> inEdgeMap) {
		this.inEdgeMap = inEdgeMap;
	}

	/**
	 * 获得指定vertexid的邻居节点
	 * @param vertex
	 * @return
	 */
	public HashMap<String, Double> getNeighbours(String vertex) {

		if(graphMap.containsKey(vertex)) {
			return this.graphMap.get(vertex);
		} else {
			return null;
		}
	}

	/**
	 * 获得所有的节点对应的入边Set，组成HashMap
	 * @return
	 */
	public void computeAllNodeInEdges() {

		inEdgeMap = new HashMap<String, HashSet<Edge>>();
		for(Edge edge: this.edge) {
			if(this.isDirected()) {//有向图
				String node = edge.getDstId();
				HashSet<Edge> set;
				if(inEdgeMap.containsKey(node)) {
					set = inEdgeMap.get(node);
					set.add(edge);

				} else {
					set = new HashSet<Edge>();
					set.add(edge);
				}
				inEdgeMap.put(node, set);
			} else {//无向图
				String fromNode = edge.getSrcId();
				HashSet<Edge> set;
				if(inEdgeMap.containsKey(fromNode)) {
					set = inEdgeMap.get(fromNode);
					set.add(edge);

				} else {
					set = new HashSet<Edge>();
					set.add(edge);
				}
				inEdgeMap.put(fromNode, set);

				String toNode = edge.getDstId();
				set = new HashSet<Edge>();
				if(inEdgeMap.containsKey(toNode)) {
					set = inEdgeMap.get(toNode);
					set.add(edge);

				} else {
					set = new HashSet<Edge>();
					set.add(edge);
				}
				inEdgeMap.put(toNode, set);
			}
		}
	}

	/**
	 * 获得指定vertexid的入边集合
	 * @param vertex
	 * @return
	 */
	public HashSet<Edge> getInEdges(String vertex) {

		if(inEdgeMap.containsKey(vertex)) {
			return this.inEdgeMap.get(vertex);
		} else {
			return null;
		}
	}

	/**
	 * 添加一条边e到graphMap中
	 * @param e
	 */
	private void addEdge2graphMap(Edge e) {

		String src = e.getSrcId();
		String dst = e.getDstId();
		double weight = e.getWeight();
		//将src对应的边加入graphMap
		if(this.graphMap.containsKey(src)) {
			this.graphMap.get(src).put(dst, weight);
		} else {
			HashMap<String, Double> map = new HashMap<String, Double>();
			map.put(dst, weight);
			this.graphMap.put(src, map);
		}

		//如果是无向图需要将dst->src的边也存起
		if(this.isDirected==false) {
			//将dst对应的边加入graphMap
			if(this.graphMap.containsKey(dst)) {
				this.graphMap.get(dst).put(src, weight);
			} else {
				HashMap<String, Double> map = new HashMap<String, Double>();
				map.put(src, weight);
				this.graphMap.put(dst, map);
			}
		}
	}

	/**
	 * add an edge to graph
	 * @param e
	 */
	public void addEdge(Edge e) {
		this.edge.add(e);
		this.addEdge2graphMap(e);
		if(!this.vertex.contains(e.getSrcId())) {
			addVertex(e.getSrcId());
		}
		if(!this.vertex.contains(e.getDstId())) {
			addVertex(e.getDstId());
		}
	}

	/**
	 * add a vertex to the graph
	 * @param id
	 */
	public void addVertex(String id) {
		this.vertex.add(id);
	}

	/**
	 * compute degree for each node
	 * @return degree HashMap
	 */
	public HashMap<String, Integer> computeIndirectionDegree() {

		HashMap<String, Integer> degreeMap = new HashMap<String, Integer>();

		for(String v: vertex) {
			degreeMap.put(v, 0);
		}

		for(int i=0; i<edge.size(); i++) {
			Edge curEdge = edge.get(i);
			degreeMap.put(curEdge.getSrcId(), degreeMap.get(curEdge.getSrcId())+1);
			degreeMap.put(curEdge.getDstId(), degreeMap.get(curEdge.getDstId())+1);
		}

		return degreeMap;
	}

	public ArrayList<Edge> getEdge() {
		return this.edge;
	}

	public void setEdge(ArrayList<Edge> edge) {
		this.edge = edge;
	}

	public HashSet<String> getVertex() {
		return vertex;
	}

	public void setVertex(HashSet<String> vertex) {
		this.vertex = vertex;
	}

	public HashMap<String, HashMap<String, Double>> getGraphMap() {
		return graphMap;
	}

	public void setGraphMap(HashMap<String, HashMap<String, Double>> graphMap) {
		this.graphMap = graphMap;
	}

	public boolean isDirected() {
		return isDirected;
	}

	public void setDirected(boolean isDirected) {
		this.isDirected = isDirected;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.graphMap.toString();
	}

}
