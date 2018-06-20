/**
 * File: LandscapeDisplay.Java
 * Author: Li Kebing 
 * Date: 12/16/2016
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.event.MouseInputAdapter;
import java.util.*;

/**
 * Displays a Landscape graphically using Swing.  The Landscape
 * can be displayed at any scale factor.
 * @author bsEASTwo
 */
public class LandscapeDisplay extends JFrame
{
    protected Landscape scape;
    private LandscapePanel canvas;
    private int gridScale;
	private PlayState state = PlayState.PLAY;
	private Move move = null;
	private Shoot shoot = Shoot.NO;
	private ShotDirection sd = null;
	private Reset reset = Reset.NO;
	private JLabel fieldX;
	private JLabel fieldY;
	
	
    /**
     * Initializes a display window for a Landscape.
     * @param scape the Landscape to display
     * @param scale controls the relative size of the display
     */
    public LandscapeDisplay(Landscape scape, int scale, int win, int lose){
       
        // setUP the window
        super("Hunt The Wumps");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.scape = scape;
        this.gridScale = scale;

        // create a panel in which to display the Landscape
        this.canvas = new LandscapePanel( (int) this.scape.getCols() * this.gridScale,
                                        (int) this.scape.getRows() * this.gridScale);

        // add the panel to the window, layout, and display
        this.add(this.canvas, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
        
        this.fieldX = new JLabel("win: " + Integer.toString(win) + "   | ");
		this.fieldY = new JLabel("lose: " + Integer.toString(lose) + " ");
        JButton reset = new JButton("Reset");
        JButton quit = new JButton("Quit");
        JPanel panel = new JPanel( new FlowLayout(FlowLayout.RIGHT));
        panel.add( this.fieldX );
		panel.add( this.fieldY );
		panel.add( reset );
		panel.add( quit );
		
		this.add( panel, BorderLayout.SOUTH);
		this.pack();

		Control control = new Control();
		this.addKeyListener(control);
		this.setFocusable(true);
		this.requestFocus();

		quit.addActionListener( control );
		reset.addActionListener( control );
    }


    /**
     * This inner class provides the panel on which Landscape elements
     * are drawn.
     */
    private class LandscapePanel extends JPanel
    {
        /**
         * Creates the panel.
         * @param width     the width of the panel in pixels
         * @param height        the height of the panel in pixels
         */
        public LandscapePanel(int width, int height)
        {
                super();
                this.setPreferredSize(new Dimension(width, height));
                this.setBackground(new Color(0.9f, 0.9f, 0.98f));
        }

        /**
         * Method overridden from JComponent that is responsible for
         * drawing components on the screen.  The sUPplied Graphics
         * object is used to draw.
         * 
         * @param g     the Graphics object used for drawing
         */
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            scape.draw( g, gridScale );    
        } // end paintComponent
        
    } // end LandscapePanel

    public void UPdate() {
        Graphics g = canvas.getGraphics();
        this.requestFocus();
        canvas.paintComponent( g );
    }
 
 	// get methods for different enums
    public PlayState getPlayState(){ return this.state; }
	public Move getMove(){ return this.move; } 
    public Shoot getShoot(){ return this.shoot; }
    public ShotDirection getShotDirection(){ return this.sd; }
    public Reset getReset(){ return this.reset; }
    
    // set method for move and reset
    public void endMove(){ this.move = null; }
    public void noReset(){ this.reset = Reset.NO; }

	/*
	private class MouseControl extends MouseInputAdapter{
    	
    	public void mouseMoved(MouseEvent e) {
        	fieldX.setText( "" + e.getPoint().x );
            fieldY.setText( "" + e.getPoint().y );
            if( e.getPoint().x > 0 && e.getPoint().x < width && 
            	e.getPoint().y > 0 && e.getPoint().y < height ) {
            	curPoint.x += (e.getPoint().x - curPoint.x) * 0.1;
                curPoint.y += (e.getPoint().y - curPoint.y) * 0.1;
        	}
        }
 
        public void mouseDragged(MouseEvent e) {
        	fieldX.setText( "" + e.getPoint().x );
            fieldY.setText( "" + e.getPoint().y );
        	if( e.getPoint().x > 0 && e.getPoint().x < width && 
            	e.getPoint().y > 0 && e.getPoint().y < height ) {
                	curPoint.x += (e.getPoint().x - curPoint.x) * 0.1;
                    curPoint.y += (e.getPoint().y - curPoint.y) * 0.1;
        	}
        }
         
        public void mousePressed(MouseEvent e) {
        	System.out.println( "Pressed: " + e.getClickCount() );
            prevColor = curColor;
            curColor = Color.red;
        }
 
        public void mouseReleased(MouseEvent e) {
    		System.out.println( "Released: " + e.getClickCount());
            curColor = prevColor;
        }
 
        public void mouseEntered(MouseEvent e) {
        	System.out.println( "Entered: " + e.getPoint() );
            curColor = prevColor;
        }
 
        public void mouseExited(MouseEvent e) {
        	System.out.println( "Exited: " + e.getPoint() );
            prevColor = curColor;
            curColor = Color.yellow;
        }
 
        public void mouseClicked(MouseEvent e) {
        	System.out.println( "Clicked: " + e.getClickCount() );
        }
        
	} // end class MouseControul
	*/

	// the controller of the keyboard
    private class Control extends KeyAdapter implements ActionListener {
		String str = "";
	
        public void keyTyped(KeyEvent e) {
			if( ("" + e.getKeyChar()).equalsIgnoreCase("q") ) {
				str = "Quit";
				state = PlayState.STOP;
			}
            else if( shoot == Shoot.NO ){
				if( ("" + e.getKeyChar()).equalsIgnoreCase("w") ) {
					str = "Move NORTH";
					move = Move.UP;
				}
				else if( ("" + e.getKeyChar()).equalsIgnoreCase("a") ) {
					str = "Move WEST";
					move = Move.LEFT;
				}
				else if( ("" + e.getKeyChar()).equalsIgnoreCase("s") ) {
					str = "Move SOUTH";
					move = Move.DOWN;
				}
				else if( ("" + e.getKeyChar()).equalsIgnoreCase("d") ) {
					str = "Move EAST";
					move = Move.RIGHT;
				}
				else if( ("" + e.getKeyChar()).equalsIgnoreCase(" ") ) {
					str = "Armed";
					shoot = Shoot.SHOOT;
				}
            }
            else{
            	if( ("" + e.getKeyChar()).equalsIgnoreCase(" ") ) {
					str = "UnArmed";
					shoot = Shoot.NO;
				}
				else if( ("" + e.getKeyChar()).equalsIgnoreCase("w") ) {
					str = "FIRE NORTH";
					sd = ShotDirection.NORTH;
				}
				else if( ("" + e.getKeyChar()).equalsIgnoreCase("a") ) {
					str = "FIRE WEST";
					sd = ShotDirection.WEST;
				}
				else if( ("" + e.getKeyChar()).equalsIgnoreCase("s") ) {
					str = "FIRE SOUTH";
					sd = ShotDirection.SOUTH;
				}
				else if( ("" + e.getKeyChar()).equalsIgnoreCase("d") ) {
					str = "FIRE EAST";
					sd = ShotDirection.EAST;
				}
            }
            
            System.out.println( "Press: " + e.getKeyChar() + " and " + str );
        }

        public void actionPerformed(ActionEvent event) {
            if( event.getActionCommand().equalsIgnoreCase("Reset") ) {
            	reset = Reset.RESET;
            	System.out.println( "Restart the game." );
            }
            else if( event.getActionCommand().equalsIgnoreCase("Quit") ) {
            	state = PlayState.STOP;
            	System.out.println( "Quit." );
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
	}

}

enum PlayState{ PLAY, STOP; }
enum Move{ UP, DOWN, LEFT, RIGHT; }
enum Shoot{ SHOOT, NO; }
enum ShotDirection{ NORTH, SOUTH, EAST, WEST; }
enum Reset{ RESET, NO; }