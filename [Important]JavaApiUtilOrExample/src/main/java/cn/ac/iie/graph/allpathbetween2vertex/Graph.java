package cn.ac.iie.graph.allpathbetween2vertex;

import net.sf.json.JSONArray;

public class Graph {

	private Vertex vertexList[]; // list of vertices
	private int adjMat[][]; // adjacency matrix

	private int nVerts;
	private static int MAX_VERTS;

	int i = 0;
	int j = 0;

	public Vertex[] getVertexList() {
		return vertexList;
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
		nVerts = 0;

		for (i = 0; i < MAX_VERTS; i++) {
			for (j = 0; j < MAX_VERTS; j++) {
				adjMat[i][j] = 0;
			}
		}


		for(int i=0;i<nodeArray.size();i++){
			String name = nodeArray.getJSONObject(i).getString("column1");
			addVertex(name);
			//if(name.equals("卞然倌1")||name.equals("蔡沐壮1"))
			//System.out.println(i+"="+name);
		}

		for(int i=0;i<linkArray.size();i++) {
			int src =Integer.parseInt(linkArray.getJSONObject(i).getJSONObject("source").getString("index"));
			int des =Integer.parseInt(linkArray.getJSONObject(i).getJSONObject("target").getString("index"));
			//System.out.println(src+":"+des);
			if(graphStyle.equals("sylj_have")||graphStyle.equals("zdlj_have"))
					addEdge(src, des);
			else
			{
				addEdge(src, des);
				addEdge(des, src);
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
