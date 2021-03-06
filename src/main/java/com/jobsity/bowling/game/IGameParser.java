package com.jobsity.bowling.game;

import com.jobsity.bowling.model.PlayerEntry;

/**
 * Defines some basic method for general parsing capabilities for the bowing program.
 * This simple design allows extending for multiple parsing sources, such as: stream,
 * web, output of another program, etc..
 */
public interface IGameParser extends AutoCloseable {
	
	/**
	 * This method is called when the bowling programs starts and before calling
	 * the {@link IGameParser#next()} method.
	 */
	public void init();
	
	/**
	 * Creates the player information containing the player's name and pinfalls.
	 * @return the parsed {@link PlayerEntry}.
	 */
	public PlayerEntry next();
}
