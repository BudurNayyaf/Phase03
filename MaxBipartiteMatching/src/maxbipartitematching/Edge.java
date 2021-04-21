/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maxbipartitematching;

 /*
 * @author bodoralharbi
 */
//Edge class with three attribute 
public class Edge{

    //edge(u,v) , number of the vertex u 
    private int source;
    //edge(u,v) , number of the vertex v
    private int destination;


    public Edge(int source, int destination)  {
        this.source = source;
        this.destination = destination;
        //this.weight = weight;
    }
//-------------------------------------------------------------------------------------------------------------------------------------

    public int getSource() {
        return source;
    }
//-------------------------------------------------------------------------------------------------------------------------------------

    public int getDestination() {
        return destination;
    }
//-------------------------------------------------------------------------------------------------------------------------------------

    
}
