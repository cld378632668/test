package cn.ac.iie.graph.algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import cn.ac.iie.graph.datastructure.Edge;
import cn.ac.iie.graph.datastructure.Graph;

/**
 * 
 * 无权图  介数中心性。
 * @author SJ
 *
 */
public class BetweennessCentrality {

	private Graph graph = null;
	
	public BetweennessCentrality() {
		this.graph = new Graph();
	}
	
	public BetweennessCentrality(Graph graph) {
		this.graph = graph;
	}
	
    public HashMap<Integer, Double> calculateBetweennessCentrality() {
    	
        HashMap<Integer, Double> nodeBetweenness = new HashMap<>();
        
        int nodeNum = graph.getVertex().size();
        
        for (Integer s : graph.getVertex()) {
        	
        	Stack<Integer> stack = new Stack<>(); //empty stack
			LinkedList<Integer>[] P = new LinkedList[nodeNum]; //empty list
            double[] theta = new double[nodeNum]; //theta
            int[] d = new int[nodeNum];

            setInitParametetrsForNode(s, P, theta, d, nodeNum);

            LinkedList<Integer> Q = new LinkedList<>();
            Q.addLast(s);
            
            while (!Q.isEmpty()) {
            	
            	//找出队首节点的所有邻接节点
                Integer v = Q.removeFirst();
                stack.push(v);
                
                HashMap<Integer, Double> neighborMap = graph.getNeighbours(v);
                
                if(neighborMap==null) {
                	nodeBetweenness.put(v, 0.0);
                	stack.pop();
                	continue;
                }
                
                //遍历所有当前节点v的邻接节点
                for (Integer anotherNode : neighborMap.keySet()) {
                    //第一次遍历到该节点anotherNode，d值变为出发节点v的d+1
                	if(d[anotherNode]<0) {
                    	Q.addLast(anotherNode);
                    	d[anotherNode]=d[v]+1;
                    }
                	//起始节点v到当前节点anotherNode是最短路径
                    if(d[anotherNode]==d[v]+1) {
                    	theta[anotherNode]=theta[anotherNode]+ theta[v];
                    	P[anotherNode].addLast(v);
                    }
                }
            }
            
            double[] delta = new double[nodeNum];
            
            for(int i=0; i<nodeNum; i++) {
            	delta[i]=0;
            }
            
            while (!stack.empty()) {
                Integer w = stack.pop();
                for (Integer v: P[w]) {
                    delta[v] += (theta[v] / theta[w]) * (1 + delta[w]);
                }
                if (w != s) {
                	if(!nodeBetweenness.containsKey(w)) {
                		nodeBetweenness.put(w, delta[w]);
                	} else{
                		nodeBetweenness.put(w, nodeBetweenness.get(w)+ delta[w]);
                	}
                }
            }
            
        }

        if(!graph.isDirected()) {
        	for(Integer node: nodeBetweenness.keySet()) {
        		nodeBetweenness.put(node, nodeBetweenness.get(node)/2);
        	}
        }
        return nodeBetweenness;
    }

    private void setInitParametetrsForNode(Integer node, LinkedList<Integer>[] P, 
    		double[] theta, int[] d, int nodeNum){
    	
    	for(int j=0; j<nodeNum; j++) {
    		P[j] = new LinkedList<>();
    		theta[j] = 0;
    		d[j] = -1;
    	}
    	theta[node]=1;
    	d[node]=0;
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph graph = new Graph();
		graph.addEdge(new Edge(0,1));
		graph.addEdge(new Edge(1,2));
		graph.addEdge(new Edge(1,3));
		graph.addEdge(new Edge(2,4));
		graph.addEdge(new Edge(3,4));
		graph.addVertex(0);
		graph.addVertex(1);
		graph.addVertex(2);
		graph.addVertex(3);
		graph.addVertex(4);
		graph.setDirected(false);
		
		BetweennessCentrality s= new BetweennessCentrality(graph);
		HashMap<Integer,Double> betweennessMap = s.calculateBetweennessCentrality();
		System.out.println(betweennessMap);
	}

}
