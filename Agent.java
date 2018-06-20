/**
 * File: Agent.Java
 * Author: Li Kebing 
 * Date: 10/16/2016
 */

import java.awt.Graphics;

public class Agent{
	
	private int row = 0;
	private int col = 0;
	
	// constructor
	public Agent(int r, int c){
	
		this.row = r;
		this.col = c;
	
	}
	
	// get the number of rows
	public int getRow(){
	
		return this.row;
	
	}
	// get the number of columns
	public int getCol(){
	
		return this.col;
	
	}
	
	//set the number of rows 
	public void setRow(int newRow){
				
		this.row = newRow;
	
	}
	// set the number of columns
	public void setCol(int newCol){
	
		this.col = newCol;
	
	}
	
	// get a string 
	public String toString(){
	
		String str = "";
		str = "(" + this.row + ", " + this.col +")";
		return str;
		
	}

	// update 
	public void updateState(Landscape scape){}
	
	// get the categorCol
//	public int getCategorCol(){return 0;}
	// draw
	public void draw(Graphics g, int scale){}
	
	// main method
	public static void main(String[] args){
		
		Agent a = new Agent(3, 4);
		System.out.println(a.toString());
	
	}

}