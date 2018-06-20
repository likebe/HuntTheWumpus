/**
 * File: Landscape.Java
 * Author: Li Kebing 
 * Date: 12/15/2016
 */

import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Iterator;

// the landscape of the game
public class Landscape{

	private LinkedList<Agent> foreground;
    private LinkedList<Agent> background;
    private int row;
    private int col;

	// constructor
	public Landscape(int r, int c){
	
		this.foreground = new LinkedList<Agent>();
		this.background = new LinkedList<Agent>();
		this.row = r;
		this.col = c;		
	}
	
	// get/set methods
	public int getRows(){ return this.row; }
	public void setRows( int r ){ this.row = r; }
	public int getCols(){ return this.col; }
	public void setCols( int c ){ this.col = c; }
	
	// add agents to the foreground
	public void addForeground( Agent a ){
		this.foreground.add(a);
	}
	
	// add agents to the background
	public void addBackground( Agent b ){
		this.background.add(b);
	}

	// draw method
	public void draw( Graphics g, int gridScale ){
		g.setColor( new Color(0.6f, 0.8f, 0.8f) );
		
		// draw the horizontal lines
		for( int i=1; i<this.row; i++){
			g.fillRect(0, gridScale*i, this.col*gridScale, 4);
		}		
		// draw the vertical lines
		for( int j=1; j<this.col; j++){
			g.fillRect(gridScale*j, 0, 3, this.row*gridScale);
		}	
		// draw background agents
		for( Agent b: background ){
			b.draw( g, gridScale);
		}
		//draw foreground agents	
		for( Agent f: foreground ){
			f.draw( g, gridScale);
		}	
	}

	public static void main(String[] args) {
		
	}
	
}