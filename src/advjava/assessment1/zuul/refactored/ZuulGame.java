package advjava.assessment1.zuul.refactored;

import advjava.assessment1.zuul.refactored.utils.Out;
import java.io.IOException;

/**
 * 
 * The bulk of the game happens here. Multiple fields handle management for
 * respective roles in the game. The class provides access to these managers
 * through getters and includes methods to interact with the game with limited
 * scope as to stop accidental breaking of links between references.
 * 
 * 
 * @author dja33
 *
 */
public class ZuulGame extends Game{

	/**
	 * Create new instance of the Game.
	 * 
	 * Creates all management systems.
	 * 
	 * Call .initialiseGame( prop ) to prepare the game
	 * 
	 * Call .play() to start the game
	 */
	public ZuulGame() {
		super();
	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void play() {
		ui.play(this);
	}

	/**
	 * Terminate the game
	 */
	public void terminate() {
		finished = true;
		try {
			Out.close();
		} catch (IOException ioe) {
			Out.out.loglnErr("Failed to close logger!");
		}
	}
	
	public boolean hasTerminated(){
		return finished;
	}
}
