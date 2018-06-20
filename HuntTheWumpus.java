/**
 * File: HuntTheWumpus.Java
 * Author: Li Kebing 
 * Date: 12/16/2016
 */

import java.util.Random;
import java.util.ArrayList;

// the main class that create the game
public class HuntTheWumpus{

	private Game game;
	private Graph graph;
	private Random ran;
	private Landscape scape;
	private ArrayList<Vertex> list;
	private Hunter hunter;
	private Wumpus wumpus;
	private LandscapeDisplay display;
	private int win;
	private int lose;
	 
	// constructor method
	public HuntTheWumpus() {
	
		this.win = 0;
		this.lose = 0;
	}

	// set up the scene
	public void setUp() throws InterruptedException {
	
 		this.game = Game.ON;
 		this.graph = new Graph();
		this.ran = new Random();
		this.scape = new Landscape(10, 10);
		this.list = new ArrayList<Vertex>();  
		this.display = new LandscapeDisplay(scape, 64, win, lose);
 		
 		for( int i=0; i<scape.getRows(); i++ ){
 			for( int j=0; j<scape.getCols(); j++ ){
 				Vertex v = new Vertex(i, j);
 				list.add(v);
 				scape.addBackground(v);
 			}
 		}
 		
 		// create a map by linking different vertices
 		for( int k=0; k<scape.getRows()*scape.getCols(); k++ ){
 			// not the first row
 			if( k > scape.getCols()-1 && ran.nextDouble()<=0.6){ 
 				graph.addEdge( list.get(k), Direction.NORTH, list.get(k-10) );
 			}	
 			// not the last row
 			if( k < scape.getRows()*scape.getCols()-scape.getCols() && ran.nextDouble()<=0.6){
 				graph.addEdge( list.get(k), Direction.SOUTH, list.get(k+10) );
 			}	
 			// not the first column
 			if( k%scape.getCols() != 0 && ran.nextDouble()<=0.6){
 				graph.addEdge( list.get(k), Direction.WEST, list.get(k-1) );
 			}
 			// not the last column	
 			if( k%scape.getCols() != scape.getCols()-1 && ran.nextDouble()<=0.6){
 				graph.addEdge( list.get(k), Direction.EAST, list.get(k+1) );
 			}	
 		}
 		
 		// create a hunter object
		this.hunter = new Hunter( ran.nextInt(10), ran.nextInt(10) );
		// get the index of the vertex that hunter is currently locating
 		int hunterIndex = hunter.getRow()*scape.getCols() + hunter.getCol();
 		hunter.setLocation( list.get(hunterIndex) );
 		scape.addForeground(hunter);
 		
 		// create a wumpus object and put it into the landscape
		this.wumpus = new Wumpus( ran.nextInt(10), ran.nextInt(10) );
		// get the index of vertex that wumpus locates
 		int wumpusIndex = wumpus.getRow()*scape.getCols() + wumpus.getCol(); 
 		wumpus.setLocation( list.get(wumpusIndex) );
 		graph.shortestPath( list.get(wumpusIndex) );
 		scape.addForeground(wumpus);
 		display.repaint();
 	}
 	
	// reset the game (clear the current game and start a new one)
	public void reset() throws InterruptedException{
		this.display.dispose();
		this.Game();
	} 	

	// the main game method
 	public void Game() throws InterruptedException {
		
		this.setUp(); // set up the game
		
		// if the game is played
		while( display.getPlayState() != PlayState.STOP && this.game != Game.OFF){	
			
			// if wants to reset
			if(display.getReset() == Reset.RESET){
				this.lose++; 
				this.reset();
			}		
			
			// if the hunter doesn't shoot
			if(display.getShoot() == Shoot.NO){
			
				hunter.setShoot(false);
				if(display.getMove() == Move.UP){
					if( hunter.getLocation().getEdge().containsKey(Direction.NORTH)){
						// get the new index of vertex that hunter should go
						int hunterIndex = hunter.getRow()*scape.getCols() + hunter.getCol() - scape.getCols();
						hunter.setLocation( list.get(hunterIndex) );
					}
					display.endMove(); // set the move back to null
				}
				else if(display.getMove() == Move.DOWN){
					if( hunter.getLocation().getEdge().containsKey(Direction.SOUTH)){
						// get the new index of vertex that hunter should go
						int hunterIndex = hunter.getRow()*scape.getCols() + hunter.getCol() + scape.getCols();
						hunter.setLocation( list.get(hunterIndex) );
					}
					display.endMove();
				}
				else if(display.getMove() == Move.LEFT){
					if( hunter.getLocation().getEdge().containsKey(Direction.WEST)){
						// get the new index of vertex that hunter should go
						int hunterIndex = hunter.getRow()*scape.getCols() + hunter.getCol() - 1;
						hunter.setLocation( list.get(hunterIndex) );
					}
					display.endMove();			
				}
				else if(display.getMove() == Move.RIGHT){
					if( hunter.getLocation().getEdge().containsKey(Direction.EAST)){
						// get the new index of vertex that hunter should go
						int hunterIndex = hunter.getRow()*scape.getCols() + hunter.getCol() + 1;
						hunter.setLocation( list.get(hunterIndex) );
					}
					display.endMove();	
				}
				if( hunter.getRow() == wumpus.getRow() && hunter.getCol() == wumpus.getCol() ){
					this.lose++;
					wumpus.Lose();
					System.out.println("Wumpus Eats Your Brain!");
					this.game = Game.OFF;
				}
			}
			
			// if the hunter shoots
			else{
			
				hunter.setShoot(true);
				if(display.getShotDirection() == ShotDirection.NORTH){
					wumpus.getLocation().setVisibility(true);
					if( hunter.getRow()-1 == wumpus.getRow() && hunter.getCol() == wumpus.getCol() ){
						this.win++;
						wumpus.Win();
					}
					else{
						this.lose++;
						wumpus.Lose();
						System.out.println("Wumpus Eats Your Brain!");
						
					}
					this.game = Game.OFF;
				}
				else if(display.getShotDirection() == ShotDirection.SOUTH){
					wumpus.getLocation().setVisibility(true);
					if( hunter.getRow()+1 == wumpus.getRow() && hunter.getCol() == wumpus.getCol() ){
						this.win++;
						wumpus.Win();
					}
					else{
						this.lose++;
						wumpus.Lose();
						System.out.println("Wumpus Eats Your Brain!");
					}
					this.game = Game.OFF;				
				}
				else if(display.getShotDirection() == ShotDirection.WEST){
					wumpus.getLocation().setVisibility(true);
					if( hunter.getRow() == wumpus.getRow() && hunter.getCol()-1 == wumpus.getCol() ){
						this.win++;
						wumpus.Win();
					}
					else{
						this.lose++;
						wumpus.Lose();
						System.out.println("Wumpus Eats Your Brain!");
					}
					this.game = Game.OFF;				
				}
				else if(display.getShotDirection() == ShotDirection.EAST){
					wumpus.getLocation().setVisibility(true);
					if( hunter.getRow() == wumpus.getRow() && hunter.getCol()+1 == wumpus.getCol() ){
						this.win++;
						wumpus.Win();
					}
					else{
						this.lose++;
						wumpus.Lose();
						System.out.println("Wumpus Eats Your Brain!");
					}
					this.game = Game.OFF;
				}
			}
			
			display.repaint();
			Thread.sleep(25);
		}
		
		if( display.getPlayState() == PlayState.STOP ){
			System.exit(0);
		}
		
		// game ends
		while( this.game == Game.OFF ){
			if( display.getPlayState() == PlayState.STOP ){
				System.exit(0);
			}
			if(display.getReset() == Reset.RESET){
				this.reset();
			}	
			display.repaint();
			Thread.sleep(25);
		}
	}

	
	public static void main(String[] args) throws InterruptedException {
		HuntTheWumpus htw = new HuntTheWumpus();
		htw.Game();
	}
}

enum Game{ ON, OFF }