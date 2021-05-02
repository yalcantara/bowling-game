package com.jobsity.bowling.model;

import java.io.File;
import java.io.Serializable;

import static com.jobsity.bowling.utils.AppUtils.*;

public class PlayerEntry implements Serializable {
	
	private final String player;
	private final String pinfall;
	
	public PlayerEntry(String player, String pinfall){
		checkParamNotNull("player", player);
		checkParamNotNull("pinfall", pinfall);
		this.player = player;
		this.pinfall = pinfall;
	}
	
	public String getPlayer() {
		return player;
	}
	
	public String getPinfall() {
		return pinfall;
	}
	
	@Override
	public String toString() {
		return "FileEntry{" +
				"player='" + player + '\'' +
				", pinfall='" + pinfall + '\'' +
				'}';
	}
}
