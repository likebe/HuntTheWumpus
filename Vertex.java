/**
 * File: Vertex.Java
 * Author: Li Kebing 
 * Date: 12/11/2016
 */

import java.util.HashMap;
import java.util.Comparator;
import java.util.Collection;
import java.awt.Graphics;
import java.awt.Color;

// vertex is part of the graph
// it behaves like an advanced Agent object
public class Vertex extends Agent{

	private HashMap<Direction,Vertex> edge;
	private int cost;
	private boolean marked;	
	private int label=0;
	private Comparator<Vertex> comparator;
	private boolean visible = false;
	
	// constructor method
	public Vertex( int r, int c ){
	
		super(r, c);
		this.cost = 0;
		this.marked = false;
		this.edge = new HashMap<Direction,Vertex>();
		this.comparator = new VertexComparator();
	}
	
	// get/set method
	public int getCost(){ return this.cost; }	
	public void setCost( int c ){ this.cost = c; }
	public boolean getMarked(){ return this.marked; }
	public void setMarked( boolean boo ){ this.marked = boo; }
	public int getLabel(){ return this.label; }
	public void setLabel( int l ){ this.label = l; }
	public boolean getVisibility(){ return this.visible; }
	public void setVisibility(boolean boo){ this.visible = boo; }
		
	// the static opposite direction method that returns the opposite direction of a specific direction
	public static Direction opposite(Direction d){
		if( d == Direction.NORTH )
			return Direction.SOUTH;
		if( d == Direction.SOUTH )
			return Direction.NORTH;
		if( d == Direction.EAST )
			return Direction.WEST;
		if( d == Direction.WEST );
			return Direction.EAST;
	}

	// connect the vertex other with the direction d in the hash map
	public void connect(Vertex other, Direction d){
		this.edge.put( d, other );
	}
	
	// get the neighbor of a vertex in direction d
    public Vertex getNeighbor(Direction d){
    	return this.edge.get(d);
    }
 
	// get neighbors of a vertex, returns a collection of vertices
    public Collection<Vertex> getNeighbors(){
    	return this.edge.values();	
    }
    
    // get the edge of the vertex 
    public HashMap<Direction,Vertex> getEdge(){
    	return this.edge;
    }
 
	// draw method
    public void draw(Graphics g, int gridScale) {
    	
    	// draw nothing if it's not visibile
        if (this.visible==false){
            return;
        }
        
        int xpos = this.getCol()*gridScale;
        int ypos = this.getRow()*gridScale;
        int border = 2;
        int half = gridScale / 2;
        int eighth = gridScale / 8;
        int sixteenth = gridScale / 16;
        
        // if the wumpus is close enough
		if (this.cost <= 2){
			g.setColor( new Color(0.9f, 0.3f, 0.3f) );
        	g.fillRect(xpos + border, ypos + border, gridScale - 2*border, gridScale - 2 * border);
        }	
		else{
			g.setColor( Color.black );
       		g.drawRect(xpos + border, ypos + border, gridScale - 2*border, gridScale - 2 * border);
		}	
		        
        // draw doorways as boxes
        g.setColor( new Color(0.6f, 0.8f, 0.8f) );
        if (this.edge.containsKey(Direction.NORTH))
            g.fillRect(xpos + half - sixteenth, ypos, eighth, eighth + sixteenth);
        if (this.edge.containsKey(Direction.SOUTH))
            g.fillRect(xpos + half - sixteenth, ypos + gridScale - (eighth + sixteenth), eighth, eighth + sixteenth);
        if (this.edge.containsKey(Direction.WEST))
            g.fillRect(xpos, ypos + half - sixteenth, eighth + sixteenth, eighth);
        if (this.edge.containsKey(Direction.EAST))
            g.fillRect(xpos + gridScale - (eighth + sixteenth), ypos + half - sixteenth, eighth + sixteenth, eighth);
    }
 
 	// toString method
	public String toString(){
		String str = "";
		str += "number of neighbors is: " + this.getNeighbors().size() + "; the cost is: " + this.getCost() + "; the status is: " + this.getMarked(); 
		return str;
	}

	// main method
	public static void main( String[] args ){
	}
}

// enum 
enum Direction{ NORTH, EAST, SOUTH, WEST; }

