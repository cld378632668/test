package cn.ac.iie.graph.path;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * This class include method Max-connection-subgraph,degree,cycle
 */
public class TF {
    Graph graph;//create graph

    // init graph
    public void init(Graph graph) {
        this.graph = graph;
    }

    //reward node map
    public HashMap<Integer, ArrayList<Integer>> getNodeMap() {
        HashMap<Integer, ArrayList<Integer>> nodeLink = new HashMap<>();
        TreeSet<Integer> matirxLabel = new TreeSet<>();
        for (int i = 0; i < graph.getVertexList().length; i++) {
            ArrayList<Integer> matirxValue = new ArrayList<>();
            for (int j = 0; j < graph.getVertexList().length; j++) {
                if (graph.getAdjMat()[i][j] == 1) {
                    matirxValue.add(j);
                    matirxLabel.add(i);
                }
                if (matirxLabel.contains(i)) {
                    nodeLink.put(i, matirxValue);
                }
            }
        }
        return nodeLink;
    }

////    reward node map for un-digraph cycle
//    public HashMap<Integer,ArrayList<Integer>> getUndigraphNodeMap(){
//        HashMap<Integer, ArrayList<Integer>> nodeLink = new HashMap<>();
//        for (int i = 0; i < graph.getVertexList().length; i++) {
//            ArrayList<Integer> matirxValue = new ArrayList<>();
//
//            for (int j = i+1; j < graph.getVertexList().length; j++) {
//                if (graph.getAdjMat()[i][j] == 1) {
//                    matirxValue.add(j);
//                }
//            }
//            nodeLink.put(i, matirxValue);
//        }
//        return nodeLink;
//    }

    //reward graph   pretreatment of deepness
    public HashMap<Integer, ArrayList<Integer>> getGraphDep(Integer node, Integer lay) {
        ArrayList<Integer> arrayList = null;//
        ArrayList<Integer> arrayList2 = new ArrayList<>();//remove repetition
        ArrayList<Integer> arrayList3 = null;
        ArrayList<Integer> arrayList4 = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> hashMap = new HashMap<>();
        if (getNodeMap().get(node) != null) {
            arrayList4.add(node);
            for (int i = 0; i < lay; i++) {
                arrayList = new ArrayList<>();
                if (i <= 1) {
                    hashMap.put(0, arrayList4);
                    hashMap.put(1, getNodeMap().get(node));
                    arrayList2.add(node);
                    arrayList2.addAll(getNodeMap().get(node));
                    arrayList3 = new ArrayList<>();
                    arrayList3.addAll(getNodeMap().get(node));
                } else {
                    for (int j = 0; j < arrayList3.size(); j++) {
                        for (int k = 0; k < getNodeMap().get(arrayList3.get(j)).size(); k++) {
                            if (!arrayList2.contains(getNodeMap().get(arrayList3.get(j)).get(k))) {
                                arrayList.add(getNodeMap().get(arrayList3.get(j)).get(k));
                                arrayList2.add(getNodeMap().get(arrayList3.get(j)).get(k));
                            }
                        }
                    }
                    hashMap.put(i, arrayList);
                    arrayList3 = new ArrayList<>();
                    arrayList3 = arrayList;
                }
                if (arrayList3.size() == 0) {
//                    System.out.print("maxNLayer:"+(i-1));
                    break;
                }
            }
        }

        return hashMap;
    }

//    method
    //reward choice node Connection SubGraph
    //BFS method
    //because of the method speed,but it need expend much memory
    public JSONArray bfs(String node1) {
        Integer node = -1;
        for(int i=0;i<graph.getVertexList().length;i++) {
            if (graph.getVertexList()[i].getLabel().equals(node1)) {
                node = i;
            }
        }
        Queue<Integer> queue = new LinkedList<>();
        HashMap<String, ArrayList<Integer>> bfsResult = new HashMap<>();
        queue.add(node);
        graph.getVertexList()[node].wasVisited = true;
        if (!queue.isEmpty()) {

            bfsResult.put(queue.peek().toString(), getAdjUnvisitedVertex(queue));

        }

        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArray1 = new JSONArray();
        for (int i = 0; i < bfsResult.get(node.toString()).size(); i++) {
            HashMap<String, Integer> midHash = new HashMap<>();
            midHash.put("index", Integer.parseInt(graph.getVertexMap().get(bfsResult.get(node.toString()).get(i))));
            jsonArray.add(midHash);
        }


        HashMap<String, Integer> selfNode = new HashMap<>();
        selfNode.put("index", Integer.parseInt(graph.getVertexMap().get(node)));
        jsonArray.add(selfNode);

        jsonArray1.add(jsonArray);
        return jsonArray1;
    }

    public ArrayList<Integer> getAdjUnvisitedVertex(Queue<Integer> queue) {
        ArrayList<Integer> markNode = new ArrayList<>();
        while (!queue.isEmpty()) {
            int v = queue.peek();
            for (int j = 0; j < graph.getN(); j++) {
                if (graph.getAdjMat()[v][j] == 1 && graph.getVertexList()[j].wasVisited == false) {
                    graph.getVertexList()[j].wasVisited = true;
                    queue.add(j);
                    markNode.add(j);
                }

            }

            queue.remove();
        }

        return markNode;
    }

//    method
    //reward link node of one node for un-digraph
//    un-digraph degree
    public int getUndigraphNodeLink(Integer node) {
        ArrayList<Integer> value = new ArrayList<>();//this node inexistence
        if (getNodeMap().get(node) != null) {
            value = getNodeMap().get(node);
            return value.size();
        } else {
            return 0;
        }

    }


//    method
    //reward link node of one digraph node in-degree and out-degree
    //out-degree
    public int getDigraphNodeLinkToOut(Integer node) {
        ArrayList<Integer> value = new ArrayList<>();//this node inexistence
        if (getNodeMap().get(node) != null) {
            value = getNodeMap().get(node);
            return value.size();
        } else {
            return 0;
        }
    }

//    method
    //in-degree
    public int getDigraphNodeLinkToIn(Integer node) {
        HashMap<Integer, ArrayList<Integer>> nodeLink = new HashMap<>();
        TreeSet<Integer> matirxLabel = new TreeSet<>();
        for (int i = 0; i < graph.getVertexList().length; i++) {
            ArrayList<Integer> matirxValue = new ArrayList<>();
            for (int j = 0; j < graph.getVertexList().length; j++) {
                if (graph.getAdjMat()[j][i] == 1) {
                    matirxValue.add(j);
                    matirxLabel.add(i);
                }
                if (matirxLabel.contains(i)) {
                    nodeLink.put(i, matirxValue);
                }
            }
        }
        if (nodeLink.get(node) != null) {
            return nodeLink.get(node).size();
        } else {
            return 0;
        }
    }

//method
    //reward N Max degree sub-graph
    public JSONArray getNMaxDegreeSubgraph(Integer n){
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArraySum=new JSONArray();
        for (Integer key:getNodeMap().keySet()) {
            HashMap<String,Integer> hashMap = new HashMap<>();
            JSONArray jsonArray1 = new JSONArray();
            if(getNodeMap().get(key).size()>=n&&n>0){
                jsonArraySum.add(key);
                hashMap.put("index",Integer.parseInt(graph.getVertexMap().get(key)));
                jsonArray1.add(hashMap);
                jsonArray.add(jsonArray1);
            }
        }
        HashMap<Object,ArrayList<Integer>> hashMap=new HashMap<>();
        if (n>=2){
            for (int i=0;i<jsonArraySum.size();i++) {
                ArrayList<Integer> arrayList;
                arrayList=getNodeMap().get(jsonArraySum.get(i));
                arrayList.retainAll(jsonArraySum);
                if(arrayList.size()>=n){
                    hashMap.put(jsonArraySum.get(i),arrayList);
                }
            }
           jsonArraySum.removeAll(hashMap.keySet());
            JSONArray hashRmKey = new JSONArray();
            for(Object obj:hashMap.keySet()){
                int count=0;
                for(int k=0;k<jsonArraySum.size();k++){

                    if(hashMap.get(obj).contains(jsonArraySum.get(k))){
                        count=count+1;
                    }

                }
                if((hashMap.get(obj).size()-count)<n){
                    hashRmKey.add(obj);
                }
            }
            for (Object ele:hashRmKey
                 ) {
                hashMap.remove(ele);
            }
        }
        if(n==1){
            return jsonArray;
        }else {
            JSONArray jsonArrayTwo= new JSONArray();
            for(Object ele:hashMap.keySet()){
                JSONObject jsb = new JSONObject();
                JSONArray midJS = new JSONArray();
                jsb.put("index",graph.getVertexMap().get(ele));
                midJS.add(jsb);
                jsonArrayTwo.add(midJS);
            }
            return jsonArrayTwo;
        }
    }

//method
    //reward choice node's all Cycle
    //  un-digraph and digraph
    public JSONArray getNodeCycle(String node1) {
        Integer node = -1;
        for(int i=0;i<graph.getVertexList().length;i++) {
            if (graph.getVertexList()[i].getLabel().equals(node1)) {
                node = i;
            }
        }
        HashMap<Integer, List<Integer>> queryNode = new HashMap<>();
        List<Integer> cycleKMP = new ArrayList<Integer>();
        List<Integer> arrayList = new ArrayList<Integer>();
        JSONArray jsonArray1 = new JSONArray();
        if (getNodeMap().get(node) != null) {
            int value = getNodeMap().get(node).size();
            for (int i = 0; i < value; i++) {
                arrayList = getNodeMap().get(node);
                int var = arrayList.get(i);
                if (getNodeMap().get(var) == null) {
                    continue;
                }
                for (int j = 0; j < getNodeMap().get(var).size(); j++) {
                    List<Integer> arrayListTwo = new ArrayList<Integer>();
                    arrayListTwo = getNodeMap().get(var);
                    int var2 = arrayListTwo.get(j);
//                    System.out.println(var2);
                    if (getNodeMap().get(var2) == null) {
                        continue;
                    }
                    for (int k = 0; k < getNodeMap().get(var2).size(); k++) {
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
                        if (getNodeMap().get(var3) == null) {
                            continue;
                        }

                        for (int e = 0; e < getNodeMap().get(var3).size(); e++) {
                            List<Integer> arrayListFour = new ArrayList<>();
                            arrayListFour = getNodeMap().get(var3);
                            if (var3 == var || var3 == node) {
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
                            if (getNodeMap().get(var4) == null) {
                                continue;
                            }
                            for (int f = 0; f < getNodeMap().get(var4).size(); f++) {
                                List<Integer> arrayListFive = new ArrayList<>();
                                arrayListFive = getNodeMap().get(var4);
                                if (var4 == var2 || var4 == var || var4 == node) {
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
                                    if (var5 == var3 || var5 == var2 || var5 == var) {
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

//                                queryNode.put(node, cycleKMP);
                            }


                        }
//                        queryNode.put(node, cycleKMP);
                    }
                }
            }
            queryNode.put(node, cycleKMP);
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject2;
            JSONArray jsonArray2 = new JSONArray();
//            System.out.println("+++++"+queryNode);
            if (queryNode.get(node) == null) {
                return null;
            } else {
                for (int i = 0; i < queryNode.get(node).size(); i++) {
                    jsonObject2 = new JSONObject();
                    jsonObject2.put("index", graph.getVertexMap().get(queryNode.get(node).get(i)));
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

            for (int i = 0; i < jsonArray2.size(); i++) {
                if (!jsonArray1.contains(jsonArray2.get(i))) {
//                    System.out.println(jsonArray2.get(i));
                    jsonArray1.add(jsonArray2.get(i));
                }
            }
        }
        return jsonArray1;
    }

//    method
    //this method in order to solve node N layer deepness
    public JSONArray getNLayerDep(String node1, Integer lay) {
        Integer node = -1;
        for(int i=0;i<graph.getVertexList().length;i++) {
            if (graph.getVertexList()[i].getLabel().equals(node1)) {
                node = i;
            }
        }
        HashMap<Integer, ArrayList<Integer>> hashMap = getGraphDep(node, lay);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < hashMap.size(); i++) {
            JSONArray jsonArray1 = new JSONArray();
            if (hashMap.get(i).size() != 0) {
                for (int j = 0; j < hashMap.get(i).size(); j++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("index", graph.getVertexMap().get(hashMap.get(i).get(j)));
                    jsonArray1.add(jsonObject);
                }
                jsonArray.add(jsonArray1);
            }
        }

        JSONArray jsa=new JSONArray();
        if(lay<=jsonArray.size()) {
            for (int i = 0; i < lay; i++) {
                if (jsonArray.get(i) != null) {
                    jsa.add(jsonArray.get(i));
                } else {
                    break;
                }
            }
            return jsa;
        }else {
            return jsa;
        }
    }

//    method
    //    divide community  triangle model
    public JSONArray getDivideRes() {
        //sort node degree form max to min
        List<HashMap.Entry<Integer, ArrayList<Integer>>> entryList = new ArrayList<>(getNodeMap().entrySet());
        Collections.sort(entryList, new Comparator<HashMap.Entry<Integer, ArrayList<Integer>>>() {
            @Override
            public int compare(HashMap.Entry<Integer, ArrayList<Integer>> o1, HashMap.Entry<Integer, ArrayList<Integer>> o2) {
                return (o2.getValue().size() - o1.getValue().size());
            }
        });

        //triangle model
        HashMap<Integer, ArrayList<Integer>> hashMap = new HashMap<>();
        Integer i = 1;
        ArrayList<Integer> arrayList1 = new ArrayList<>();
        for (int j = 0; j < entryList.size(); j++) {//reward key
            arrayList1.add(entryList.get(j).getKey());
        }
        System.out.println("********"+entryList);
//            System.out.println("++++"+entryList.get(0));
            while (entryList.get(0).getValue().size() != 1) {//
                ArrayList<Integer> arrayList = new ArrayList<>();
                arrayList = entryList.get(0).getValue();//0
//            arrayList.add(entryList.get(0).getKey());
                for (int j = 0; j < arrayList.size(); j++) {
                    Integer ele = arrayList.get(j);
                    for (int k = j + 1; k < arrayList.size(); k++) {
                        Integer exp = arrayList.get(k);
                        if (getNodeMap().get(exp) != null) {
                            for (Integer elem : getNodeMap().get(exp)) {

                                if (getNodeMap().get(elem) != null && ele != elem && getNodeMap().get(elem).contains(ele) && !arrayList.contains(elem)) {//triangle
                                    arrayList.add(elem);
                                }
                            }
                        }

                    }

                }

                hashMap.put(i, arrayList);
//            System.out.println(hashMap);
//            System.out.println("%%%%%%%%%%"+entryList);

                //remove was computed node
                int m=entryList.size();
                ArrayList<Integer> num = new ArrayList<>();
                for (int j = 0; j < entryList.size(); j++) {
                    for (int k = 0; k < arrayList.size(); k++) {
                        Integer arr = arrayList.get(k);
                        if (entryList.get(j).getKey() == arr) {
                            entryList.remove(j);
                            j = 0;
                        }
                    }
                }
                i++;
                if (entryList.size()==0){
                    System.out.println(entryList.size()+"************************");
                    break;
                }
                if(i>m){
                    break;
                }
            }
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        for (Integer key:hashMap.keySet()
             ) {
            arr.add(hashMap.get(key));
        }
//remove repetition set
        System.out.println("%%%%%%%%"+arr);
//        ArrayList<Integer> arrayListKey = new ArrayList<>();
//        HashMap<Integer,ArrayList<Integer>> hs = new HashMap<>();
//        for(int n=0;n<arr.size();n++){
//            ArrayList<Integer> arrayListKey1 = new ArrayList<>();
//                for (Integer ele:arr.get(n)
//                     ) {
//                    for (int m=n+1;m<arr.size();m++) {
//                        for (Integer elex:arr.get(m)) {
//                            if (ele==elex&&!arrayListKey.contains(m)){//
//                                arrayListKey.add(m);
//                                arrayListKey1.add(m);
//                                 break;
//                        }
//                    }
//
//                }
//            }
//            hs.put(n,arrayListKey1);
//        }
//            System.out.println("+++"+hs);
//
//        for(int n=0;n<arr.size();n++){
//            for (Integer ele:hs.get(n)
//                 ) {
//                arr.get(n).addAll(arr.get(ele));
//            }
//
//        }
//
//        for(int n=0;n<arr.size();n++){
//            for(int m=n+1;m<arr.size();m++){
//                arr.get(m).removeAll(arr.get(n));
//            }
//        }

        ArrayList<ArrayList<Integer>> lastArr = new ArrayList<>();
        ArrayList<Integer> markArr = new ArrayList<>();
        for(int n=0;n<arr.size();n++){
            ArrayList<Integer> midVar=new ArrayList<>();
            for (Integer ele:arr.get(n)
                 ) {
                if(!midVar.contains(ele)){
                    midVar.add(ele);
                    markArr.add(ele);
                }
            }
            lastArr.add(midVar);
        }

//        System.out.println(lastArr);

        JSONArray json1 = new JSONArray();
        for(int n=0;n<lastArr.size();n++){
            JSONArray json2 = new JSONArray();
            for (Integer ele:lastArr.get(n)) {
                JSONObject jsonobj=new JSONObject();
                jsonobj.put("index",graph.getVertexMap().get(ele));
                json2.add(jsonobj);
            }
            if(json2.size()!=0) {
                json1.add(json2);
            }
        }
        ArrayList<Integer> allNode = new ArrayList<>();
        for(int k=0;k<getNodeMap().size();k++){
            allNode.add(k);
        }
        allNode.removeAll(markArr);
        System.out.println("++++++:"+allNode);
        for(int g=0;g<allNode.size();g++){
            JSONArray markJS = new JSONArray();
            ArrayList<Integer> removeRP = new ArrayList<>();
            if(getNodeMap().get(allNode.get(g))!=null){
                removeRP.addAll(getNodeMap().get(allNode.get(g)));
                removeRP.removeAll(markArr);
            }
            removeRP.add(allNode.get(g));
            for (Integer ele:removeRP
                 ) {
                JSONObject job = new JSONObject();
                job.put("index",graph.getVertexMap().get(ele));
                markJS.add(job);
            }
            json1.add(markJS);
        }

        return json1;
    }

//    method
//    These are method in regard to spread networks
//    First method.reward source of a node
    public JSONArray getSourceNode(String node1){
        Integer node = -1;
        for(int i=0;i<graph.getVertexList().length;i++) {
            if (graph.getVertexList()[i].getLabel().equals(node1)) {
                node = i;
            }
        }
        TFMidNeedClass tfMidNeedClass=new TFMidNeedClass();
        JSONArray jsonArray = new JSONArray();
        tfMidNeedClass.init(graph);
       for(int i=0;i<graph.getVertexList().length;i++){
           if(getDigraphNodeLinkToIn(i)==0&&i!=node){ //compute in-degree equals zero
               tfMidNeedClass.init(i,node);
               jsonArray=tfMidNeedClass.getResult();
           }
       }
//        System.out.println("+++"+jsonArray);
        JSONArray lastJSONArray = new JSONArray();
        for (int i=0;i<jsonArray.size();i++) {
            JSONArray midJS = new JSONArray();
            JSONArray js = jsonArray.getJSONArray(i);
            for(int j=0;j<js.size();j++) {
                JSONObject job = new JSONObject();
                job.put("index", js.get(j));
                midJS.add(job);
            }
            lastJSONArray.add(midJS);
        }
//            System.out.println(">>>>>>>"+lastJSONArray);



//        System.out.println("&&&&&&&&&&&&&&&"+changeArrary);
        return  lastJSONArray;
    }


//    method
    //second method that find all path pass refer node  from src to desc in the spread networks
    public JSONArray getAllPathReferNode(String node1){
        Integer node = -1;
        for(int i=0;i<graph.getVertexList().length;i++) {
            if (graph.getVertexList()[i].getLabel().equals(node1)) {
                node = i;
            }
        }
        TFMidNeedClass tfMidNeedClass=new TFMidNeedClass();
        TFMidNeedClass tf2MidNeedClass=new TFMidNeedClass();
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArray1 = new JSONArray();
        JSONArray lastJSONArray = new JSONArray();

        for(int i=0;i<graph.getVertexList().length;i++){
            if(getDigraphNodeLinkToIn(i)==0&&i!=node){ //compute in-degree equals zero
                tfMidNeedClass.init(graph);
                tfMidNeedClass.init(i,node);
                jsonArray=tfMidNeedClass.getResult();
            }
            if(i!=node) {
                tf2MidNeedClass.init(graph);
                tf2MidNeedClass.init(node, i);
                jsonArray1 = tf2MidNeedClass.getResult();
            }
        }
        for(int g=0;g<jsonArray1.size();g++){
            jsonArray1.getJSONArray(g).remove(0);
        }
//        System.out.println("****************"+jsonArray);
//        System.out.println("****************"+jsonArray1);

        for (int i=0;i<jsonArray.size();i++){
            if(jsonArray1.size()!=0){
                for (int j=0;j<jsonArray1.size();j++){
                    JSONArray midJSONArray = new JSONArray();
                    midJSONArray.addAll(jsonArray.getJSONArray(i));
                    midJSONArray.addAll(jsonArray1.getJSONArray(j));
                    lastJSONArray.add(midJSONArray);
                }
            }else {
                lastJSONArray=jsonArray;
            }

        }
//        System.out.println("/////"+lastJSONArray);
        JSONArray result = new JSONArray();
        for (int i=0;i<lastJSONArray.size();i++){
            JSONArray midJson = new JSONArray();
            for (int j=0;j<lastJSONArray.getJSONArray(i).size();j++){
                JSONObject jsonObj= new JSONObject();
                jsonObj.put("index",lastJSONArray.getJSONArray(i).get(j));
                midJson.add(jsonObj);
            }
            result.add(midJson);
        }
//       System.out.println(">>>>>>>"+result);

        return result;
    }
}
