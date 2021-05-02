package com.jobsity.bowling.game;


import com.jobsity.bowling.game.impl.BowlingGameImpl;
import com.jobsity.bowling.model.PlayerEntry;

import java.util.ArrayList;
import java.util.List;

import  static com.jobsity.bowling.utils.AppUtils.*;

public class BowlingProgram {
	
	private IGameParser parser;
	private final IBowlingGame game;
	
	public BowlingProgram(){
		super();
		game = new BowlingGameImpl();
	}
	
	public void setParser(IGameParser parser){
		checkParamNotNull("parser", parser);
		this.parser = parser;
	}
	
	public void start(){
		if(parser == null){
			throw new BowlingException("Could not start program without a parser.");
		}
		
		try(IGameParser p = parser){
			p.init();
			
			List<PlayerEntry> entries = new ArrayList<>();
			PlayerEntry entry;
			while((entry = p.next()) != null){
				entries.add(entry);
			}
			
			entries.forEach(e->{
				String player = e.getPlayer();
				String pinfall = e.getPinfall();
				
				game.append(player, pinfall);
			});
		}catch (Exception ex){
			//shouldn't happen, but in this case, we are not going to ignore it.
			throw new RuntimeException(ex);
		}
	}
	
	public void display(){
		game.print();
	}
	
	
	public int score(String player, int frame){
		//composite shortcut
		return game.getScore(player, frame);
	}
}
