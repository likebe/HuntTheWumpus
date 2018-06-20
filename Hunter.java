/**
 * File: Hunter.Java
 * Author: Li Kebing 
 * Date: 12/12/2016
 */

import java.awt.Graphics;
import java.awt.Color;

// this class creates a HUNTER object that represents the player
public class Hunter extends Agent{
	
	private Vertex location;
	private boolean shoot;
	
	// constructor method
	public Hunter( int r, int c ){
		
		super(r, c);
		this.location = null;
		this.shoot = false;
	}

	// get the vertex HUNTER locates
	public Vertex getLocation(){
		return this.location;
	}
	
	// set the new location (a new vertex)
	public void setLocation( Vertex v ){
		
		this.location = v;
		this.setRow(v.getRow());
		this.setCol(v.getCol());
		v.setVisibility(true);
	}
	
	// get/set the status of shoot
	public boolean getShoot(){
		return this.shoot;
	}
	
	public void setShoot( boolean boo ){
		this.shoot = boo;
	}
	
	// draw method
	public void draw( Graphics g, int gridScale ){
		
		int x = this.location.getCol()*gridScale;
		int y = this.location.getRow()*gridScale;
		g.setColor( Color.green );
		g.fillOval( x+gridScale/5, y+gridScale/5, 5*gridScale/8, 5*gridScale/8 );	
		// change the color to red when you are armed
		if( this.getShoot() == true ){
			g.setColor( Color.red );
			g.fillOval( x+gridScale/5, y+gridScale/5, 5*gridScale/8, 5*gridScale/8 );	
		}
	}


}