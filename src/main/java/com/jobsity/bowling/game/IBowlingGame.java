package com.jobsity.bowling.game;

/**
 * Defines the very basics of a streaming Bowling Game by accepting player's shot
 * value on the go. Since there are many types of Bowling Games, this interface is
 * simple enough to allow different implementations to set their own rules & strategies.
 */
public interface IBowlingGame {
	
	/**
	 * Saves the player shot's value in the current game, adding to the total list of shots.
	 * @param player the player's id or unique name.
	 * @param val the shot's value: 1-10, X, F or /.
	 */
	public void append(String player, String val);
	
	/**
	 * Returns the current score for the given player and frame.
	 * @param player the player's id or unique name.
	 * @param frame the frame to query.
	 * @return the accumulated score of the player at the given frame.
	 */
	public int getScore(String player, int frame);
	
	/**
	 * Prints to the output console the current state of the game, including the
	 * names of the players, frames, and score.
	 */
	public void print();
}
