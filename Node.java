package CodeU_Assignment5;

import java.util.HashSet;

/** Implementing a node of a directed graph with the data members:
 *      + character data
 *      + set of children (of type Node)
 *      + set of parents (of type Node)
 */

public class Node{
	char data;
	HashSet<Node> children;
	HashSet<Node> parents;
	
	public Node(char data){
	    this.data = data;
		this.children = new HashSet<Node>();
		this.parents= new HashSet<Node>();
	}
	
	char getData(){
		return this.data;
	}	
	
	public void addChild(Node node){
	    this.children.add(node);
	    node.addParent(this);
	}
	
	private void addParent(Node node){	    
        this.parents.add(node);
    }
	
	public void removeChild(Node node){
	    node.parents.remove(this);
	    this.children.remove(node);	    
	}
	
	@Override
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("<"+this.data+"~{ ");
        for(Node node: children){
            str.append(Character.toString(node.getData())+",");
        }
        str.append("}>");
        return str.toString();
	}
	
	@Override
	public boolean equals(Object o){
	    Node obj = (Node) o;
	    if(this.data == obj.data){
	        return true;
	    }
	    return false;
	}
	
}
