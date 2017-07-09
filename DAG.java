package CodeU_Assignment5;

import java.util.HashMap;
import java.util.HashSet;

/**Implemented DAG (directed acyclic tree) data structure with "character" nodes.
 * data member:
 *      + nodes_map (mapping each character to a corresponding object type Node)
 *      + sources (set of objects of type Node which do not have any incoming edges)
 */
public class DAG{
    HashMap<Character, Node> nodes_map;
	HashSet<Node> sources;
	
	public DAG(){
		this.nodes_map = new HashMap<Character, Node>();
		this.sources = new HashSet<Node>();
	}
	
	/** addNode(c) adds a new node to the DAG corresponding to "c" 
	 * (if not already present)
	 *  Also, it adds the new node to the set of sources.
	 * 
	 * @param c : character
	 */
	public void addNode(char c){
	    if(!this.nodes_map.containsKey(c)){
	        Node newNode = new Node(c);
	        nodes_map.put(c, newNode);
	        this.sources.add(newNode);
	    }
	}
	
	/** getNode(c):
	 *  if the node corresponding to "c" is in DAG, returns the node
	 *  otherwise returns null
	 * 
	 * @param c : character
	 * @return
	 */
	public Node getNode(char c){
        if(this.nodes_map.containsKey(c)){
            return this.nodes_map.get(c);
        }
        return null;
    }
	    
	/** removeSource(node) deletes all edges from source "node" to its children
	 *  and then deletes "node" from sources
	 *  
	 *  assumption: the node is a source node, otherwise do nothing.
	 * 
	 * @param node : type Node
	 */
    public void removeSource(Node node){
        if(!this.sources.contains(node)){
            return;
        }
        for(Node child : node.children){
            this.removeDirectedEdge(node.getData(), child.getData());
        }
        this.sources.remove(node);
    }
	    
    /** addDirectedEdge(a,b) adds a directed edge 
     *  from "a" (corresponding node) to "b" (corresponding node)
     * 
     * @param a : character corresponding to the source node of the directed edge
     * @param b : character corresponding to the sink node of the directed edge
     */
    public void addDirectedEdge(char a, char b){
        if(this.getNode(a)== null){
            this.addNode(a);
        }
        Node from_node = this.getNode(a);
        
        if(this.getNode(b)== null){
            this.addNode(b);
        }
        Node to_node = this.getNode(b);

        from_node.addChild(to_node);
        this.sources.remove(to_node);
    }
    
    /** removeDirectedEdge(a,b) removes the edge from "a" to "b"
     *  does nothing in the absence of "a" or "b" or the edge from "a" to "b".
     * 
     * @param a : character corresponding to the source node of the directed edge
     * @param b : character corresponding to the sink node of the directed edge
     */
    public void removeDirectedEdge(char a, char b){
        Node from_node = this.getNode(a);
        Node to_node = this.getNode(b);
        
        if(from_node == null || to_node == null){
            return;
        }
        
        from_node.removeChild(to_node);
        if(to_node.parents.size() == 0){
            sources.add(to_node);
        }
    }
    
    /** getSources() returns the set of sources
     * 
     * @return
     */
    public HashSet<Node> getSources(){
        return this.sources;
    }
    
	@Override
	public String toString(){
		StringBuilder str = new StringBuilder();
		for(char c: this.nodes_map.keySet()){
		        str.append(c+":"+this.nodes_map.get(c).toString()+"\n");
		}
		return str.toString();
	}
}
