package cn.ac.iie.graph.path;

import net.sf.json.JSONArray;

import java.util.HashMap;

public class Graph {

	private Vertex vertexList[]; // list of vertices
	private int adjMat[][]; // adjacency matrix
	private HashMap<Integer,String> vertexMap; // list of vertices

	private int nVerts;
	private static int MAX_VERTS;

	int i = 0;
	int j = 0;

	public Vertex[] getVertexList() {
		return vertexList;
	}

	public HashMap<Integer,String>  getVertexMap() {
		return vertexMap;
	}

	public int[][] getAdjMat() {
		return adjMat;
	}

	public int getN() {
		return MAX_VERTS;
	}

	public void init(JSONArray nodeArray,JSONArray linkArray){

	}

	public Graph(JSONArray nodeArray,JSONArray linkArray,String graphStyle) {
		MAX_VERTS = nodeArray.size();
		adjMat = new int[MAX_VERTS][MAX_VERTS];
		vertexList = new Vertex[MAX_VERTS];
		vertexMap = new HashMap<Integer,String>();
		nVerts = 0;

		for (i = 0; i < MAX_VERTS; i++) {
			for (j = 0; j < MAX_VERTS; j++) {
				adjMat[i][j] = 0;
			}
		}


		for(int i=0;i<nodeArray.size();i++){
			String index = nodeArray.getJSONObject(i).getString("index");
			addVertex(index);
			//if(name.equals("卞然倌1")||name.equals("蔡沐壮1"))
			//System.out.println(i+"="+name);
		}

		for(int i=0;i<linkArray.size();i++) {
			String srcIndex =linkArray.getJSONObject(i).getJSONObject("source").getString("index");
			String desIndex =linkArray.getJSONObject(i).getJSONObject("target").getString("index");
			int src = -1;
			int des = -1;
			for(int j=0;j<nodeArray.size();j++){
				if(vertexList[j].getLabel().equals(srcIndex))
				{
					src = j;
				}
				else if(vertexList[j].getLabel().equals(desIndex))
				{
					des = j;
				}
			}
			System.out.println(srcIndex+":"+desIndex);
			System.out.println(src+":"+des);
			if(src!=-1&&des!=-1){
				vertexMap.put(src,srcIndex);
				vertexMap.put(des,desIndex);
				if(graphStyle.contains("have"))
					addEdge(src, des);
				else
				{
					addEdge(src, des);
					addEdge(des, src);
				}
			}
		}




	}



	private void delEdge(int start, int end) {
		adjMat[start][end] = 0;
	}

	private void addEdge(int start, int end) {
		adjMat[start][end] = 1;
		// adjMat[end][start] = 1;
	}

	public void addVertex(String lab) {
		vertexList[nVerts++] = new Vertex(lab);
	}

	public String displayVertex(int i) {
		return vertexList[i].getLabel();
	}

	public boolean displayVertexVisited(int i) {
		return vertexList[i].WasVisited();
	}

//	public void printGraph() {
//		for (i = 0; i < MAX_VERTS; i++) {
//			System.out.print("第" + displayVertex(i) + "个节点:" + " ");
//
//			for (j = 0; j < MAX_VERTS; j++) {
//				System.out.print(displayVertex(i) + "-" + displayVertex(j)
//						+ ":" + adjMat[i][j] + " ");
//			}
//			System.out.println();
//		}
//
//	}

}
