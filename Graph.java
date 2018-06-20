/**
 * File: Graph.Java
 * Author: Li Kebing 
 * Date: 10/16/2016
 */
 
import java.util.ArrayList;
import java.util.Comparator;

// the new data structure: graph
public class Graph{

	ArrayList<Vertex> vertexList;
	
	// constructor method
	public Graph(){
	
		this.vertexList = new ArrayList<Vertex>();
	}
	
	// count the number of vertices
	public int vertexCount(){
	
		return this.vertexList.size();
	}
	
	// add an edge between two vertices with direction dir
	public void addEdge(Vertex v1, Direction dir, Vertex v2){
	
		this.vertexList.add(v1);
		this.vertexList.add(v2);
		v1.connect(v2, dir);
		v2.connect(v1, v2.opposite(dir));
	}
	
	// find the shortest path and use the cost field to trace it
	public void shortestPath(Vertex v0){
		
		for(int i=0; i<this.vertexCount(); i++){
		
			this.vertexList.get(i).setMarked(false);
			this.vertexList.get(i).setCost(100000000);
		}
		
		PQHeap<Vertex> q = new PQHeap<Vertex>(new VertexComparator());
		v0.setCost(0);
		q.add(v0);
		
		while (q.size()!=0){
		
			Vertex v = q.get();
			q.remove();
			v.setMarked(true);
			for(Vertex w: v.getNeighbors()){
				if (w.getMarked()==false && v.getCost()+1 < w.getCost()){
					w.setCost(v.getCost()+1);
					q.add(w);
				}	
			}
		}
	}
	
	// main method
	public static void main(String[] args){
		
//		Graph g = new Graph();
 //		Vertex v1 = new Vertex(1);
 	//	Vertex v2 = new Vertex(2);
	//	Vertex v3 = new Vertex(3);
	//	Vertex v4 = new Vertex(4);
	//	Vertex v5 = new Vertex(5);
 	//
 	//	g.addEdge(v1, Direction.SOUTH, v2);
 	//	g.addEdge(v2, Direction.SOUTH, v3);
 	//	g.addEdge(v2, Direction.WEST, v3);
 	//	g.addEdge(v3, Direction.NORTH, v4);
 	//	g.addEdge(v4, Direction.EAST, v5);
 	//	
 	//	g.shortestPath(v1);
 	//
 	//	System.out.println(v2.toString());
 	//	System.out.println(v3.toString());
	//	System.out.println(v4.toString());
	//	System.out.println(v5.toString());
	
	}
}

// comare the vertices
class VertexComparator implements Comparator<Vertex> {
    public int compare( Vertex i1, Vertex i2 ) {
        int diff = i1.getCost() - i2.getCost();
        if (diff == 0)
            return 0;
        if (diff < 0)
            return 1;
        else
            return -1;
    }
}
