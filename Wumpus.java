/**
 * File: Wumpus.Java
 * Author: Li Kebing 
 * Date: 12/11/2016
 */

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

// this class creates a wumpus object that represultents the monster you are chasing, or is chasing you...
public class Wumpus extends Agent{
	
	private Vertex location;
	private boolean visible;
	private boolean result;
	
	// the constructor method
	public Wumpus( int r, int c ){
		
		super(r, c);
		this.location = null;
		this.visible = false;
		this.result = false;
	}
	
	// get/set method
	public Vertex getLocation(){ return this.location; }	
	public void setLocation( Vertex v){ this.location = v; }

	// the player wins
	public void Win(){
		
		this.result = true;
		this.visible = true;
	}
	
	// the player loses
	public void Lose(){
	
		this.result = false;
		this.visible = true;
	}
	
	// draw method
	public void draw( Graphics g, int gridScale ){
		if(this.visible==false){
			return;
		}	
		int x = this.location.getCol()*gridScale;
		int y = this.location.getRow()*gridScale;
		if(this.result!=false){			
			g.setColor( Color.blue );
			
       		g.setFont(new Font("MONOSPACED", Font.ITALIC, 100));
        	g.drawString( "GGWP!!!", 9*gridScale/4, 5*gridScale );
        				
			g.fillOval(x+gridScale/6+1, y+gridScale/2-1, gridScale*2/3, gridScale*2/5);
			g.fillOval(x+gridScale*5/9-1, y+gridScale/8-2, gridScale/6, gridScale/6);
			g.fillOval(x+gridScale/3-1, y+gridScale/8-2,  gridScale/6, gridScale/6);
		}
		else{
			g.setColor( Color.red );
			
       		g.setFont(new Font("MONOSPACED", Font.ITALIC, 40));
        	g.drawString( "Wumpus Eats Your Brain!!!", 3*gridScale/4, 5*gridScale );	
        			
        	g.setColor( Color.black );		
			g.fillOval(x+gridScale/6+1, y+gridScale/5, gridScale*2/3, gridScale*2/3);
			g.fillOval(x+gridScale*5/9-1, y+gridScale/8-2, gridScale/6, gridScale/6);
			g.fillOval(x+gridScale/3-1, y+gridScale/8-2,  gridScale/6, gridScale/6);
		}	
	}
}