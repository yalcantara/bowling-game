package com.jobsity.bowling.game.impl;

import com.jobsity.bowling.game.BowlingException;
import com.jobsity.bowling.game.IBowlingGame;
import com.jobsity.bowling.model.Sheet;
import com.jobsity.bowling.utils.Constants;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static com.jobsity.bowling.utils.AppUtils.*;
import static com.jobsity.bowling.utils.Constants.*;

public class BowlingGameImpl implements IBowlingGame {
	
	private final Map<String, Sheet> sheets;
	
	public BowlingGameImpl(){
		sheets = new LinkedHashMap<>();
	}
	
	public void append(String player, String val){
		
		Sheet sheet = sheets.get(player);
		if(sheet == null){
			sheet = new Sheet();
			sheet.setPlayer(player);
			sheets.put(player, sheet);
		}
		
		sheet.append(val);
	}
	
	public void print(){
		PrintWriter pw = new PrintWriter(System.out);
		print(pw);
		pw.flush();
	}
	
	public void print(PrintWriter pw){
		pw.println();
		pw.print("Frame\t\t");
		
		IntStream.range(1, TOTAL_FRAMES + 1).forEach(i -> {
			
			pw.print(i);
			
			if (i + 1 <= TOTAL_FRAMES) {
				pw.print("\t\t");
			}
			
		});
		pw.println();
		pw.println();
		
		sheets.values().forEach((Sheet s)->{
			s.print(pw);
			pw.println();
			pw.println();
		});
	}
	
	
	public int getScore(String player, int frame){
		checkParamNotNull("player", player);
		checkParamBetween("frame", 1, Constants.TOTAL_FRAMES, frame);
		Sheet sheet = sheets.get(player);
		if(sheet == null){
			throw new BowlingException("The player '" + player +"' does not exist.");
		}
		return sheet.computeScore(frame);
	}
}
