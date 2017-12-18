package Algorithm.graph.algorithm;


import Algorithm.graph.datastructure.Edge;
import Algorithm.graph.datastructure.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ConnectSubGraph {

    // visitMap key为节点标识，value为该节点是否被访问过
    private HashMap<String, Boolean> visitMap = new HashMap<String, Boolean>();

    // List<Graph> 用于存储连通分量的列表
    private List<Graph> connectSubGraph = new ArrayList<>();

    // Graph 待计算的图
    private Graph graph = null;

    // 连通分量的个数
    private int subGraphCount;


    public ConnectSubGraph(Graph graph) {
        this.graph = graph;
        for(String ver : graph.getVertex()) {
            visitMap.put(ver, false);
        }
    }


    /**
     * 基于图，计算该图的连通分量
     * @return 连通分量list
     */
    public List<Graph> getConnectSubGraph() {

        int count = 0;
        HashSet<String> vertex = graph.getVertex();

        for(String ver: vertex) {

            Graph subGraph = new Graph();// 默认无向图

            if(!visitMap.get(ver)) {
                dfsTranverse(ver, subGraph);
                count++;
            }
            if(subGraph.getGraphMap().size()!=0) {
                connectSubGraph.add(subGraph);
            }
        }
        this.subGraphCount = count;
        return connectSubGraph;
    }


    /**
     * 在graph上，从ver 节点出发进行深度优先遍历，遍历过程中的边添加到subGraph中。
     * @param ver
     * @param subGraph
     */
    private void dfsTranverse(String ver, Graph subGraph) {

        visitMap.put(ver, true);

        HashMap<String, Double> neighborMap = graph.getNeighbours(ver);

        for(String nei: neighborMap.keySet()) {
            if(visitMap.get(nei)) {
                subGraph.addEdge(new Edge(ver, nei));
            }
            else if (!visitMap.get(nei)) {
                subGraph.addEdge(new Edge(ver, nei));
                dfsTranverse(nei, subGraph);
            }
        }
    }
}
