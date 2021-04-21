/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maxbipartitematching;

import java.util.*;
/*
Section: KAR
Zuhra Othman Al-saadi            1780353
Bodor Nayaf Alamri               1805739
Shuroog Abdulmajed Alshaikh	 1812184
 */
/**
 *
 * @author bodoralharbi
 */
public class MaxBipartiteMatching {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Insert all applicants in array 
        Node[] applicants = {new Node("Ahmed ", 0), new Node("Mahmoud ", 1), new Node("Eman ", 2),
            new Node("Fatimah ", 3), new Node("Kamel ", 4), new Node("Nojood ", 5)};
        //Insert all hospitals in another array 
        Node[] hospitals = {new Node("King Abdelaziz University ", 0), new Node("King Fahd ", 1),
            new Node("East Jeddah ", 2), new Node("King Fahad Armed Forces ", 3), new Node("King Faisal Specialist ", 4),
            new Node("Ministry of National Guard ", 5)};

        //Create array for edges and insert all edges.
        Edge[] edges = new Edge[9];
        edges[0] = new Edge(0, 0);
        edges[1] = new Edge(0, 1);
        edges[2] = new Edge(1, 5);
        edges[3] = new Edge(2, 0);
        edges[4] = new Edge(2, 3);
        edges[5] = new Edge(3, 2);
        edges[6] = new Edge(4, 3);
        edges[7] = new Edge(4, 4);
        edges[8] = new Edge(5, 5);

        //Call Maximum Bipartite Matching algorithm 
        Edge[] M = MatchingALgorithm(applicants, hospitals, edges);
        //Print the result
        print(M, applicants, hospitals);

    }
    //-------------------------------------------------------------------------------------------------------------------------------------
    public static Edge[] MatchingALgorithm(Node[] V, Node[] U, Edge[] E) {
        //Set of edges(Maximum- cardinality matching in the input graph), initial equal empty
        Edge[] M = new Edge[E.length];
        int CounterM = 0;
        //Queue with free vertices in V 
        Queue<Node> Q = new LinkedList<>();
        //Insert free vertices in V 
        for (int i = 0; i < V.length; i++) {
            if (V[i].isFree()) {
                Q.add(V[i]);
            }
        }

        while (!Q.isEmpty()) {
            //Delete front vertex
            Node w = Q.remove();
            //Case 1: if w is in V
            if (SearchNode(w, V)) {
                //find every vertex adjacent to "w"
                for (int i = 0; i < E.length; i++) {
                    if (E[i].getSource() == w.getNumber()) {
                        //check if vertex "u" free or not 
                        if (U[E[i].getDestination()].isFree()) {
                            //insert edge that connect "v" and "u" in M list
                            M[CounterM] = E[i];
                            //Update free attribute in "u" and "v"
                            V[E[i].getSource()].setFree(false);
                            U[E[i].getDestination()].setFree(false);
                            CounterM++;

                            // Augment from v
                            Node v = w;

                            while (v.getLabel() != -1) {

                                //Remove edge with vertex indicated by v's label from M list
                                int j = 0;
                                while (U[E[j].getDestination()].getLabel() == -1) {
                                    j++;
                                }
                                U[E[j].getDestination()].setLabel(-1);
                                for (int k = 0; k < M.length; k++) {
                                    if (M[k] != null && j < E.length) {
                                        if (U[E[j].getDestination()].getLabel() == M[k].getSource() && E[j].getDestination() == M[k].getDestination()) {
                                            V[M[k].getSource()].setFree(true);
                                            U[M[k].getDestination()].setFree(true);
                                            M[k] = null;

                                        }
                                    }
                                }

                                //Remove null object from M list
                                CounterM = 0;
                                Edge[] M2 = new Edge[M.length];
                                for (int k = 0; k < M.length; k++) {
                                    if (M[k] != null) {
                                        M2[CounterM] = M[k];
                                        CounterM++;
                                    }
                                }
                                M = M2;

                                //Insert edge (v,u) with vertex indicated by u's label to M list
                                j = 0;
                                while (V[E[j].getSource()].getLabel() == -1) {
                                    j++;
                                }
                                for (int k = j; k < E.length; k++) {
                                    if (V[E[j].getSource()].getLabel() == E[k].getDestination()) {
                                        M[CounterM] = E[j];
                                        V[E[j].getSource()].setFree(false);
                                        U[E[j].getDestination()].setFree(false);
                                        break;
                                    }
                                }

                                //remove all vertex labels
                                for (int k = 0; k < V.length; k++) {
                                    V[k].setLabel(-1);
                                    U[k].setLabel(-1);
                                }
                                //reinitialize Q with all free vertices in V
                                Q.clear();
                                for (int h = 0; h < V.length; h++) {
                                    if (V[h].isFree()) {
                                        if (!Q.contains(V[h])) {
                                            Q.add(V[h]);
                                        }

                                    }
                                }
                            }
                            break;

                        } else {//u is matched
                            //check if u is unlabeled
                            if (!SearchEdge(w, U[E[i].getDestination()], M) && U[E[i].getDestination()].getLabel() == -1) {
                                //label u with w
                                U[E[i].getDestination()].setLabel(w.getNumber());
                                //enqueue "u" vertex if it doesn't exist in the queue.
                                if (!Q.contains(U[E[i].getDestination()])) {
                                    Q.add(U[E[i].getDestination()]);
                                }
                            }
                        }
                    }

                }

            } //Case 2: if w is in U
            else {
                //find the mate v of w then label it
                int MateV = Find(w, M);
                V[MateV].setLabel(w.getNumber());
                //enqueue "v" vertex if it doesn't exist in M list and in the queue.
                if (!Q.contains(V[MateV]) && !SearchEdge(w, V[MateV], M)) {
                    Q.add(V[MateV]);
                }
            }
        }
        return M;
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    //Function to search edge in specific list 
    public static boolean SearchEdge(Node w, Node u, Edge[] M) {
        for (int k = 0; k < M.length; k++) {
            if (M[k] != null) {
                if (w.getNumber() == M[k].getSource() && u.getNumber() == M[k].getDestination()) {
                    return true;
                }
            }

        }
        return false;
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    //Function to search node in specific list 
    public static boolean SearchNode(Node w, Node[] list) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].getName().contentEquals(w.getName())) {
                return true;
            }
        }
        return false;
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    //Function to find destination node in specific list and source
    public static int Find(Node w, Edge[] list) {

        for (int i = 0; i < list.length; i++) {
            if (list[i].getDestination() == w.getNumber()) {
                return list[i].getSource();
            }
        }
        return -1;
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    //Function to print Maximum Bipartite Matching 
    public static void print(Edge[] M, Node[] V, Node[] U) {
        System.out.println("------------------------ Maximum Bipartite Matching ------------------------\n");
        int counter = 0;
        for (int i = 0; i < M.length; i++) {
            if (M[i] != null) {
                counter++;
                System.out.printf("%-8s  -> %s\n", V[M[i].getSource()].getName(), U[M[i].getDestination()].getName());
            }
        }
        System.out.println("\nMaximum number of applicants that can get job is \"" + counter + "\"\n");

    }

}
