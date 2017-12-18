package cn.ac.iie.graph.connectionsubgraphofgraph;

import java.util.*;
import net.sf.json.*;
/**
 * Created by AlexanderTan on 2017/4/8 0008.
 * this compute of graph in order to find MAX-subgraph,connectionNodeDegree,cycle
 * first method compute MAX connection sub-graph
 * then method compute connectionNodeDegree
 * last method compute  cycle
 *
 * If you have some questions,please you make contact with me,thank you very much.E-mail:Tan_gf@126.com
 */

public class AS {
    GraphC graph;//create graph
    boolean flag;
    Queue theQueue;
    int start, end;
    /**
     *
     * //judge one effective node
     public void judgeVertex(Vertex node){
     for(int i=0;i<graph.getVertexList().length;i++){
     if(node==(graph.getVertexList()[i])){
     flag = true;
     }else {flag = false;}
     }
     }
     */
// init graph
    public void init(GraphC graph){
        this.graph = graph;
    }
//init edge
    public void init(int start,int end){
        this.start = start;
        this.end = end;
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
    public JSONArray getNodeCycle(Integer node){
        int value = getNodeMap().get(node).size();
        HashMap<Integer,ArrayList<Integer>> queryNode = new HashMap<>();
        ArrayList<Integer> cycleKMP = new ArrayList<>();
        ArrayList<Integer> arrayList = new ArrayList<>();
            for (int i=0;i<value;i++){
                arrayList = getNodeMap().get(node);
                int var = arrayList.get(i);
                for(int j=0;j<getNodeMap().get(var).size();j++){
                    ArrayList<Integer> arrayListTwo = new ArrayList<>();
                    arrayListTwo=getNodeMap().get(var);
                    int var2 = arrayListTwo.get(j);
                    for(int k=0;k<getNodeMap().get(var2).size();k++){
                        ArrayList<Integer> arrayListThree = new ArrayList<>();
                        arrayListThree=getNodeMap().get(var2);
                        if (var2==node) {
                            continue;
                        }
                        int var3 = arrayListThree.get(k);
                        if(arrayListThree.contains(node)&&!cycleKMP.contains(var2)) {
                            cycleKMP.add(node);
                            cycleKMP.add(var);
                            cycleKMP.add(var2);
                            cycleKMP.add(node);
                        }else{
                            continue;
                        }
                        System.out.println("++++"+cycleKMP);
                        for (int e=0;e<getNodeMap().get(var3).size();e++){
                            ArrayList<Integer> arrayListFour = new ArrayList<>();
                            arrayListFour=getNodeMap().get(var3);
                            if (var3==var) {
                                continue;
                            }
                            if(arrayListFour.contains(node)&&!cycleKMP.contains(var3)){
                                cycleKMP.add(var3);
                                cycleKMP.add(var2);
                                cycleKMP.add(node);
                            }
                            queryNode.put(node, cycleKMP);
                        }
                    }
                }
            }
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject2 ;
        JSONArray jsonArray2 = new JSONArray();
        for(int i=0;queryNode.get(node)!=null&&i<queryNode.get(node).size();i++){
            jsonObject2=new JSONObject();
            jsonObject2.put("index", queryNode.get(node).get(i));
            jsonArray.add(jsonObject2);
            if(i==queryNode.get(node).size()-1){
                jsonArray2.add(jsonArray);
                break;
            }else if(queryNode.get(node).get(i)==queryNode.get(node).get(i+1)) {
                jsonArray2.add(jsonArray);
                jsonArray=new JSONArray();
            }

        }

        return jsonArray2;
    }

}