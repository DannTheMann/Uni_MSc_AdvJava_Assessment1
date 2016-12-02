package advjava.assessment1.zuul.refactored;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import advjava.assessment1.zuul.refactored.utils.Out;
import advjava.assessment1.zuul.refactored.utils.PrintableList;
import advjava.assessment1.zuul.refactored.utils.Resource;

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
        @Override
	public void play() {
		ui.play(this);
	}

	/**
	 * Terminate the game
	 */
        @Override
	public void terminate() {
		finished = true;
		try {
			Out.close();
		} catch (IOException ioe) {
			Out.out.loglnErr("Failed to close logger!");
		}
	}
	
        @Override
	public boolean hasTerminated(){
		return finished;
	}

	public Collection<Resource> loadAllResources() {
			
			List<Resource> resources = new PrintableList<>();
			
			Stream.concat(characterManager.values().stream()
					, Stream.concat(roomManager.values().stream()
							, Stream.concat(getPlayer().getInventory().stream()
									, itemManager.values().stream()))).forEach(s->resources.add(s));
		
		
			return resources;
	}
}
