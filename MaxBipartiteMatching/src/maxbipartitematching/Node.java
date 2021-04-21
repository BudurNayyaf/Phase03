/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maxbipartitematching;

/**
 *
 * @author bodoralharbi
 */
//Node class with four attributes name, number and label of vertex and attribute to check its free vertex or not
public class Node {
    private String name ;
    private int label=-1;
    private boolean Free;
    private int number;
//-------------------------------------------------------------------------------------------------------------------------------------
    public Node(String name,int number) {
        this.name = name;
        this.number =number;
        Free=true;
    }
//-------------------------------------------------------------------------------------------------------------------------------------
    public void setLabel(int label) {
        this.label = label;
    }
//-------------------------------------------------------------------------------------------------------------------------------------
    public String getName() {
        return name;
    }
//-------------------------------------------------------------------------------------------------------------------------------------
    public int getLabel() {
        return label;
    }
//-------------------------------------------------------------------------------------------------------------------------------------
    public void setFree(boolean isFree) {
        this.Free = isFree;
    }
//-------------------------------------------------------------------------------------------------------------------------------------
    public boolean isFree() {
        return Free;
    }
//-------------------------------------------------------------------------------------------------------------------------------------
    public int getNumber() {
        return number;
    }
    
}
