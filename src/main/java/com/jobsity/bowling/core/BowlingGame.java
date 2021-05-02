package com.jobsity.bowling.core;

import com.jobsity.bowling.model.Sheet;
import com.jobsity.bowling.utils.Constants;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.jobsity.bowling.utils.AppUtils.*;
import static com.jobsity.bowling.utils.Constants.*;

public class BowlingGame {
	
	private final Map<String, Sheet> sheets;
	
	public BowlingGame(){
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
		
		for(int i =1; i <= TOTAL_FRAMES; i++){
			pw.print(i);
			
			if(i  + 1 <= TOTAL_FRAMES){
				pw.print("\t\t");
			}
		}
		pw.println();
		pw.println();
		for(Sheet sheet:sheets.values()){
			sheet.print(pw);
			pw.println();
			pw.println();
		}
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
