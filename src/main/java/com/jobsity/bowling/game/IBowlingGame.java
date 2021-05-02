package com.jobsity.bowling.game;

public interface IBowlingGame {
	
	public void append(String player, String val);
	public int getScore(String player, int frame);
	public void print();
}
