package cn.ac.iie.graph.allpathbetween2vertex;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Stack;

public class AF {

	boolean isAF = true;
	Graph graph;
	int n;
	int start, end;
	Stack<Integer> theStack;
	JSONArray pathArray = new JSONArray();

	private ArrayList<Integer> tempList;
	private String counterexample;

	public void init(Graph graph){
		this.graph = graph;
	}
	public void init(int start,int end){
		this.start = start;
		this.end = end;
	}

	public JSONArray getResult(int k) {   //k 0为所有路径  1为最短路径
		//graph.printGraph();
		n = graph.getN();
		theStack = new Stack<Integer>();

		if (!isConnectable(start, end)) {
			isAF = false;
			counterexample = "节点之间没有通路";
		} else {
			for (int j = 0; j < n; j++) {
				tempList = new ArrayList<Integer>();
				for (int i = 0; i < n; i++) {
					tempList.add(0);
				}
				graph.getVertexList()[j].setAllVisitedList(tempList);
			}

			isAF = af(start, end);
		}
		if(k == 0)
			return pathArray;
		else{
			if(pathArray.size()!=0){
				int min = pathArray.getJSONArray(0).size();
				JSONArray pathArray1 = new JSONArray();
				pathArray1.add(pathArray.getJSONArray(0));
				for(int i=1;i<pathArray.size();i++){
					if(pathArray.getJSONArray(i).size()< min)
					{
						for(int j=0;j<pathArray1.size();j++){
							pathArray1.remove(j);
						}
						pathArray1.add(pathArray.getJSONArray(i));
						min = pathArray.getJSONArray(i).size();

					}
					else if(pathArray.getJSONArray(i).size() == min){
						pathArray1.add(pathArray.getJSONArray(i));
					}
				}
				return pathArray1;
			}
			else
				return pathArray;
		}


	}

	private boolean af(int start, int end) {
		graph.getVertexList()[start].setWasVisited(true); // mark it
		theStack.push(start); // push it

		while (!theStack.isEmpty()) {
			int v = getAdjUnvisitedVertex(theStack.peek());
			if (v == -1) // if no such vertex,
			{
				tempList = new ArrayList<Integer>();
				for (int j = 0; j < n; j++) {
					tempList.add(0);
				}
				graph.getVertexList()[theStack.peek()]
						.setAllVisitedList(tempList);
				theStack.pop();
			} else // if it exists,
			{
				theStack.push(v); // push it
			}

			if (!theStack.isEmpty() && end == theStack.peek()) {
				graph.getVertexList()[end].setWasVisited(false); // mark it
				printTheStack(theStack);
				System.out.println();
				theStack.pop();
			}
		}

		return isAF;
	}


	private boolean isConnectable(int start, int end) {
		ArrayList<Integer> queue = new ArrayList<Integer>();
		ArrayList<Integer> visited = new ArrayList<Integer>();
		queue.add(start);
		while (!queue.isEmpty()) {
			for (int j = 0; j < n; j++) {
				if (graph.getAdjMat()[start][j] == 1 && !visited.contains(j)) {
					queue.add(j);
				}
			}
			if (queue.contains(end)) {
				return true;
			} else {
				visited.add(queue.get(0));
				queue.remove(0);
				if (!queue.isEmpty()) {
					start = queue.get(0);
				}
			}
		}
		return false;
	}

//	public String counterexample() {
//		for (Integer integer : theStack) {
//			counterexample += graph.displayVertex(integer);
//			if (integer != theStack.peek()) {
//				counterexample += "-->";
//			}
//		}
//
//		return counterexample;
//	}


	public int getAdjUnvisitedVertex(int v) {
		ArrayList<Integer> arrayList = graph.getVertexList()[v]
				.getAllVisitedList();
		for (int j = 0; j < n; j++) {
			if (graph.getAdjMat()[v][j] == 1 && arrayList.get(j) == 0
					&& !theStack.contains(j)) {
				graph.getVertexList()[v].setVisited(j);
				return j;
			}
		}
		return -1;
	} // end getAdjUnvisitedVertex()

	public void printTheStack(Stack<Integer> theStack2) {
		JSONArray pathArray1 = new JSONArray();
		//JSONObject path = new JSONObject();
		for (Integer integer : theStack2) {
			JSONObject path = new JSONObject();
			System.out.print(graph.displayVertex(integer));
			path.put("name",graph.displayVertex(integer));
			pathArray1.add(path);
			if (integer != theStack2.peek()) {
				System.out.print("-->");
			}
		}
		pathArray.add(pathArray1);
	}

}
