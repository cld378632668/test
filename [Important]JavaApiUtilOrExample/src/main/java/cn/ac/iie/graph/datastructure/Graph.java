package cn.ac.iie.graph.datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.sf.json.JSONArray;

public class Graph {

	//边列表 按单向存
	private ArrayList<Edge> edge = null;
	//顶点集合
	private HashSet<Integer> vertex = null;

	//前台传回的index映射到从0开始自增的编号
	private HashMap<String, Integer> nodeIndexMap = null;

	//便于各种图的遍历
	private HashMap<Integer, HashMap<Integer, Double>> graphMap = null;

	//节点对应入边集合的Map
	private HashMap<Integer, HashSet<Edge>> inEdgeMap = null;

	//默认无向图
	private boolean isDirected = false;

	/**
	 * construction function
	 * no parameter
	 */
	public Graph() {
		this.edge = new ArrayList<Edge>();
		this.vertex = new HashSet<Integer>();
		this.graphMap = new HashMap<Integer, HashMap<Integer, Double>>();
		this.nodeIndexMap = new HashMap<String, Integer>();
	}

	/**
	 * construction
	 * @param edge
	 * @param vertex
	 */
	public Graph(ArrayList<Edge> edge, HashSet<Integer> vertex) {

		this.edge = edge;
		this.vertex = vertex;
		this.graphMap = new HashMap<Integer, HashMap<Integer, Double>>();
		this.nodeIndexMap = new HashMap<String, Integer>();

		//如果是有向图，只需要存src->dst 的边
		for(Edge e: edge) {
			addEdge2graphMap(e);
		}
	}

	/**
	 * 根据前台传来的json字符串构建图结构
	 * @param nodeArray
	 * @param linkArray
	 * @param graphStyle
	 */
	public Graph(JSONArray nodeArray,JSONArray linkArray, String graphStyle) {

		this();

		if(graphStyle.contains("have")) {
			this.isDirected = true;
		} else {
			this.isDirected = false;
		}


		for(int i=0; i<nodeArray.size(); i++){
			String nodeIndex = nodeArray.getJSONObject(i).getString("index");
			this.vertex.add(i);
			this.nodeIndexMap.put(nodeIndex, i);
		}

		for(int i=0;i<linkArray.size();i++) {
			int src =this.nodeIndexMap.get(linkArray.getJSONObject(i).getJSONObject("source")
					.getString("index"));
			int des =this.nodeIndexMap.get(linkArray.getJSONObject(i).getJSONObject("target")
					.getString("index"));
			Edge e = new Edge(src, des);
			addEdge(e);
		}
	}


	public HashMap<Integer, HashSet<Edge>> getInEdgeMap() {
		return inEdgeMap;
	}

	public void setInEdgeMap(HashMap<Integer, HashSet<Edge>> inEdgeMap) {
		this.inEdgeMap = inEdgeMap;
	}

	/**
	 * 获得指定vertexid的邻居节点
	 * @param vertex
	 * @return
	 */
	public HashMap<Integer, Double> getNeighbours(int vertex) {

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

		inEdgeMap = new HashMap<Integer, HashSet<Edge>>();
		for(Edge edge: this.edge) {
			if(this.isDirected()) {//有向图
				int node = edge.getDstId();
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
				int fromNode = edge.getSrcId();
				HashSet<Edge> set;
				if(inEdgeMap.containsKey(fromNode)) {
					set = inEdgeMap.get(fromNode);
					set.add(edge);

				} else {
					set = new HashSet<Edge>();
					set.add(edge);
				}
				inEdgeMap.put(fromNode, set);

				int toNode = edge.getDstId();
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
	public HashSet<Edge> getInEdges(int vertex) {

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

		int src = e.getSrcId();
		int dst = e.getDstId();
		double weight = e.getWeight();
		//将src对应的边加入graphMap
		if(this.graphMap.containsKey(src)) {
			this.graphMap.get(src).put(dst, weight);
		} else {
			HashMap<Integer, Double> map = new HashMap<Integer, Double>();
			map.put(dst, weight);
			this.graphMap.put(src, map);
		}

		//如果是无向图需要将dst->src的边也存起
		if(this.isDirected==false) {
			//将dst对应的边加入graphMap
			if(this.graphMap.containsKey(dst)) {
				this.graphMap.get(dst).put(src, weight);
			} else {
				HashMap<Integer, Double> map = new HashMap<Integer, Double>();
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
	public void addVertex(int id) {
		this.vertex.add(id);
	}

	/**
	 * compute degree for each node
	 * @return degree HashMap
	 */
	public HashMap<Integer, Integer> computeIndirectionDegree() {

		HashMap<Integer, Integer> degreeMap = new HashMap<Integer, Integer>();

		for(int v: vertex) {
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

	public HashSet<Integer> getVertex() {
		return vertex;
	}

	public void setVertex(HashSet<Integer> vertex) {
		this.vertex = vertex;
	}

	public HashMap<Integer, HashMap<Integer, Double>> getGraphMap() {
		return graphMap;
	}

	public void setGraphMap(HashMap<Integer, HashMap<Integer, Double>> graphMap) {
		this.graphMap = graphMap;
	}

	public boolean isDirected() {
		return isDirected;
	}

	public void setDirected(boolean isDirected) {
		this.isDirected = isDirected;
	}


	public HashMap<String, Integer> getNodeIndexMap() {
		return nodeIndexMap;
	}

	public void setNodeIndexMap(HashMap<String, Integer> nodeIndexMap) {
		this.nodeIndexMap = nodeIndexMap;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.graphMap.toString();
	}

}
