/**
 *
 * @author Shuroog
 */

/*
source code for maximumFlow/: https://iq.opengenus.org/edmonds-karp-algorithm-for-maximum-flow/
source code for minCut: https://www.geeksforgeeks.org/minimum-cut-in-a-directed-graph/
 */

/*
Section: KAR
Zuhra Othman Al-saadi            1780353
Bodor Nayaf Alamri               1805739
Shuroog Abdulmajed Alshaikh	 1812184
 */

import java.util.LinkedList;
import java.lang.Exception;

public class Edmonds_Karp_Algorithm {

    // rGraph represent the residual networks
    static int rGraph[][];

    public static void main(String[] args) throws java.lang.Exception {

// initialize the graph
        int graph[][] = new int[][]{
            {0, 2, 7, 0, 0, 0},
            {0, 0, 0, 3, 4, 0},
            {0, 0, 0, 4, 2, 0},
            {0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 5},
            {0, 0, 0, 0, 0, 0}};

        rGraph = new int[graph.length][graph.length];

        /* 
        Call the functions: maxFlow and minCut
        The paramters: First one: the path graph.
                       Secend one: the source, vertex 1 represent by ZERO 
                       because the array counting start by ZERO.
                       Third one: the sink, vertex 6 represent by 5. 
         */
        System.out.println("The maximum flow is " + maxFlow(graph, 0, 5));
        System.out.println("------------------------------------------------------");
        System.out.println("The min-cut is " + minCut(graph, 0, 5));

    }

    /* ----------------------------------------------------------------------------------
        maximum flow function 
     ----------------------------------------------------------------------------------
     */
    // s is source and t is sink
    static int maxFlow(int graph[][], int s, int t) {
        int u, v;

        /*
          Residual graph where rGraph[i][j] indicates
          residual capacity of edge from i to j (if there
          is an edge. If rGraph[i][j] is 0, then there is
          not)
         */
        for (u = 0; u < graph.length; u++) {
            for (v = 0; v < graph.length; v++) {
                rGraph[u][v] = graph[u][v];
            }
        }

        // This array is filled by BFS later, to store the path
        int parent[] = new int[graph.length];

        // initilize the maximum flow with ZERO.
        int max_flow = 0;

        // use BFS to travel through graph. 
        while (bfs(rGraph, s, t, parent)) {

            //find the maximum flow through the path found from bfs.
            // t is the sink, s is the source 
            int pathFlow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                pathFlow = Math.min(pathFlow, rGraph[u][v]);

            }

            //update residual capacities of the edges and reverse edges along the path
            // initilize the path with empty. 
            String path = "";
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] -= pathFlow;
                rGraph[v][u] += pathFlow;
                /* varible add is the new vertix in the path
               then vertecis count in the code is starting from ZERO based in the array implementaion,
               but the graph counting start from 1, so sum 1 with the vertx number in the output. */

                /*--------------------------------------------------------------------
                printing part
                --------------------------------------------------------------------
                 */
                String add;

                /*if their is no direct path between v and u then (which it's flow is ZERO) 
                 then it must used a backward edge. 
                 add 1 to the vertices in printing to match the given counting in the graph, which is starts with 1.     
                */
                if (graph[u][v] == 0) {
                    add = "<-" + (v + 1);
                } else {
                    add = "->" + (v + 1);
                }
                // add the new vertix in the path
                path = add + path;
            }
            // add the source in the beginning of the path 
            path = "1" + path;
            // Add path flow to overall flow
            max_flow += pathFlow;

            // print the Flow augmentation path  and it's capacity
            System.out.println("Flow augmentation path : " + path);
            System.out.println("The capacity is: " + pathFlow);
        }
       
        // Return the total flow
        return max_flow;
    }

    // BNS to travel through the graph. 
    static boolean bfs(int rGraph[][], int s, int t, int parent[]) {
      
        // Create a visited array and mark all vertices as not  visited
       
        boolean visited[] = new boolean[rGraph.length];
        for (int i = 0; i < rGraph.length; ++i) {
            visited[i] = false;
        }
        
        // Create a queue, enqueue source vertex and mark source vertex as visited
    
        LinkedList<Integer> queue = new LinkedList<>();
        // add the source in the queue front 
        queue.add(s);
        // make it visited.
        visited[s] = true;
        parent[s] = -1;

        while (!queue.isEmpty()) {
           // remove the source, it's front the queue 
            int u = queue.poll();
            for (int v = 0; v < rGraph.length; v++) {
                // if the vertix v is unvisited and it's flow greater than ZERO then add it to the queue
                // this loop is costruct the augumented path 
                if (visited[v] == false && rGraph[u][v] > 0) {
                    queue.add(v);
                    // make the source as parent to the current vertex 
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
       //we reached sink in BFS starting from source, then return true. 
       
        return (visited[t] == true);
    }

    /* ----------------------------------------------------------------------------------
        minimum cut function 
     ----------------------------------------------------------------------------------
     */
    static int minCut(int graph[][], int s, int t) {
        int minCut = 0;
// Flow is maximum now, find vertices reachable from s     
        boolean[] isVisited = new boolean[graph.length];
        dfs(rGraph, s, isVisited);

        System.out.println("The min-cut is between the following vertices:");
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] > 0 && isVisited[i] && !isVisited[j]) {
                    /* add 1 to the vertix number because the counting in arrays starting from ZERO, 
                    while the graph start from 1.*/
                    System.out.println((i + 1) + " -> " + (j + 1));
                    // add the edge capacity to cut.  
                    minCut += graph[i][j];
                }
            }
        }
        return minCut;
    }

    /* A DFS based function to find all reachable 
       vertices from the source. The function marks visited[i] 
       as true if i is reachable from the source.*/
    
    static void dfs(int[][] rGraph, int s, boolean[] visited) {
        visited[s] = true;
        for (int i = 0; i < rGraph.length; i++) {
            if (rGraph[s][i] > 0 && !visited[i]) {
                dfs(rGraph, i, visited);
            }
        }
    }

}
