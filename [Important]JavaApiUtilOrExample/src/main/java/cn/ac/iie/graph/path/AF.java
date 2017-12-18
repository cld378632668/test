package cn.ac.iie.graph.path;


import net.sf.json.*;

import java.util.*;

public class AF {

	boolean isAF = true;
	Graph graph;
	int n;
	int start, end;
	Stack<Integer> theStack;
	JSONArray pathArray = new JSONArray();
	AF af;


	private ArrayList<Integer> tempList;
	private String counterexample;

	public void init(Graph graph){
		this.graph = graph;
	}
	public void init(AF af){
		this.af=af;
	}
	public void init(String start,String end){
		for(int i=0;i<graph.getVertexList().length;i++){
			if(graph.getVertexList()[i].getLabel().equals(start)){
				this.start = i;
			}
			if(graph.getVertexList()[i].getLabel().equals(end)){
				this.end = i;
			}
		}

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
						pathArray1 = new JSONArray();
						pathArray1.add(pathArray.getJSONArray(i));
						min = pathArray.getJSONArray(i).size();

					}
					else if(pathArray.getJSONArray(i).size() == min){
						pathArray1.add(pathArray.getJSONArray(i));
					}
				}
				System.out.println(pathArray1.toString());
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
			System.out.print(integer+"-");
			System.out.print(graph.displayVertex(integer));
			path.put("index",graph.displayVertex(integer));
			pathArray1.add(path);
			if (integer != theStack2.peek()) {
				System.out.print("-->");
			}
		}
		pathArray.add(pathArray1);
	}
	//reward node map
	public HashMap<Integer,ArrayList<Integer>> getNodeMap() {
		HashMap<Integer, ArrayList<Integer>> nodeLink = new HashMap<>();
		TreeSet<Integer> matirxLabel = new TreeSet<>();
		for (int i = 0; i < graph.getVertexList().length; i++) {
			ArrayList<Integer> matirxValue = new ArrayList<>();
			for (int j = 0; j < graph.getVertexList().length;j++) {
				if (graph.getAdjMat()[i][j] == 1) {
					matirxValue.add(j);
					matirxLabel.add(i);
				}
				if (matirxLabel.contains(i)) {
					nodeLink.put(i, matirxValue);
				}
			}
		}
		return  nodeLink;
	}



	//reward choice node Connection SubGraph
	//BFS method
	//because of the method speed,but it need expend much memory
	public JSONObject bfs(Integer node){
		Queue<Integer> queue = new LinkedList<>();
		HashMap<String,ArrayList<Integer>> bfsResult = new HashMap<>();;
		queue.add(node);
		graph.getVertexList()[node].wasVisited=true;
		if (!queue.isEmpty()) {

			bfsResult.put(queue.peek().toString(), getAdjUnvisitedVertex(queue));

		}

		JSONObject jsonObject = JSONObject.fromObject(bfsResult);
		return jsonObject;
	}

	public  ArrayList<Integer> getAdjUnvisitedVertex(Queue<Integer> queue){
		ArrayList<Integer> markNode = new ArrayList<>();
		while (!queue.isEmpty()) {
			int v = queue.peek();
			for (int j=0;j<graph.getN();j++){
				if(graph.getAdjMat()[v][j]==1&&graph.getVertexList()[j].wasVisited==false){
					graph.getVertexList()[j].wasVisited=true;
					queue.add(j);
					markNode.add(j);
				}

			}

			queue.remove();
		}

		return markNode;
	}


	//reward link node of one node for un-digraph
	public int getUndigraphNodeLink(Integer node){
		ArrayList<Integer> value = new ArrayList<>();//this node inexistence
		value=getNodeMap().get(node);
		return value.size();
	}


	//reward link node of one digraph node in-degree and out-degree
	//out-degree
	public int getDigraphNodeLinkToOut(Integer node){
		ArrayList<Integer> value = new ArrayList<>();//this node inexistence
		value=getNodeMap().get(node);
		return value.size();
	}
	//in-degree
	public int getDigraphNodeLinkToIn(Integer node){
		HashMap<Integer, ArrayList<Integer>> nodeLink = new HashMap<>();
		TreeSet<Integer> matirxLabel = new TreeSet<>();
		for (int i = 0; i < graph.getVertexList().length; i++) {
			ArrayList<Integer> matirxValue = new ArrayList<>();
			for (int j = 0; j < graph.getVertexList().length;j++) {
				if (graph.getAdjMat()[j][i] == 1) {
					matirxValue.add(j);
					matirxLabel.add(i);
				}
				if (matirxLabel.contains(i)) {
					nodeLink.put(i, matirxValue);
				}
			}
		}
		return nodeLink.get(node).size();
	}



	//reward choice node's all Cycle
	// digraph  and un-digraph
	public JSONArray getNodeCycle(Integer node) {
		HashMap<Integer, List<Integer>> queryNode = new HashMap<>();
		List<Integer> cycleKMP = new ArrayList<Integer>();
		List<Integer> arrayList = new ArrayList<Integer>();
		if (getNodeMap().get(node) != null) {
			int value = getNodeMap().get(node).size();
			for (int i = 0; i < value; i++) {
				arrayList = getNodeMap().get(node);
				int var = arrayList.get(i);
				if(getNodeMap().get(var)==null){
					continue;
				}
				for (int j = 0; j < getNodeMap().get(var).size(); j++) {
					List<Integer> arrayListTwo = new ArrayList<Integer>();
					arrayListTwo = getNodeMap().get(var);
					int var2 = arrayListTwo.get(j);
//                    System.out.println(var2);
					if (getNodeMap().get(var2)==null){
						continue;
					}
					for (int k = 0; k < getNodeMap().get(var2).size() ; k++) {
						List<Integer> arrayListThree = new ArrayList<Integer>();
						arrayListThree = getNodeMap().get(var2);
						if (var2 == node) {
							continue;
						}

						int var3 = arrayListThree.get(k);
//	                        &&!cycleKMP.contains(var2)
						if (arrayListThree.contains(node)) {
							cycleKMP.add(node);
							cycleKMP.add(var);
							cycleKMP.add(var2);
							cycleKMP.add(node);
						}

//                        System.out.println("++++" + cycleKMP);
						if (getNodeMap().get(var3)==null){
							continue;
						}

						for (int e = 0; e < getNodeMap().get(var3).size(); e++) {
							List<Integer> arrayListFour = new ArrayList<>();
							arrayListFour = getNodeMap().get(var3);
							if (var3 == var||var3==node) {
								continue;
							}
							int var4 = arrayListFour.get(e);
//	                            &&!cycleKMP.contains(var3)
							if (arrayListFour.contains(node)) {
								cycleKMP.add(node);
								cycleKMP.add(var);
								cycleKMP.add(var2);
								cycleKMP.add(var3);
								cycleKMP.add(node);
							}
							if (getNodeMap().get(var4)==null){
								continue;
							}
							for (int f=0;f<getNodeMap().get(var4).size();f++){
								List<Integer> arrayListFive = new ArrayList<>();
								arrayListFive = getNodeMap().get(var4);
								if (var4 == var2||var4==var||var4==node) {
									continue;
								}

								int var5 = arrayListFive.get(f);
//                                && !cycleKMP.contains(var4)
								if (arrayListFive.contains(node)) {
									cycleKMP.add(node);
									cycleKMP.add(var);
									cycleKMP.add(var2);
									cycleKMP.add(var3);
									cycleKMP.add(var4);
									cycleKMP.add(node);
								}
								if (getNodeMap().get(var5) == null) {
									continue;
								}
								for (int g = 0; g < getNodeMap().get(var5).size(); g++) {
									List<Integer> arrayListSix = new ArrayList<>();
									arrayListSix = getNodeMap().get(var5);
									if (var5 == var3||var5==var2||var5==var) {
										continue;
									}
//                                        && !cycleKMP.contains(var5)
									if (arrayListSix.contains(node)) {
										cycleKMP.add(node);
										cycleKMP.add(var);
										cycleKMP.add(var2);
										cycleKMP.add(var3);
										cycleKMP.add(var4);
										cycleKMP.add(var5);
										cycleKMP.add(node);
									}
								}

								queryNode.put(node, cycleKMP);
							}

						}
					}
				}
			}
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject2;
			JSONArray jsonArray2 = new JSONArray();
			if (queryNode.get(node)==null){
				return null;
			}else {
				for (int i = 0; i < queryNode.get(node).size(); i++) {
					jsonObject2 = new JSONObject();
					jsonObject2.put("index", queryNode.get(node).get(i));
					jsonArray.add(jsonObject2);
					if (i == queryNode.get(node).size() - 1) {
						jsonArray2.add(jsonArray);
						break;
					} else if (queryNode.get(node).get(i) == queryNode.get(node).get(i + 1)) {

						jsonArray2.add(jsonArray);
						jsonArray = new JSONArray();
					}

				}
			}
			JSONArray jsonArray1 = new JSONArray();
			for(int i=0;i<jsonArray2.size();i++){
				if (!jsonArray1.contains(jsonArray2.get(i))){
					System.out.println(jsonArray2.get(i));
					jsonArray1.add(jsonArray2.get(i));
				}
			}


			return jsonArray1;
		}else{
			return null;
		}
	}

}
