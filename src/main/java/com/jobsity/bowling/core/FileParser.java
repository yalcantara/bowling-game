package com.jobsity.bowling.core;

import com.jobsity.bowling.model.PinfallType;
import com.jobsity.bowling.model.PlayerEntry;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.jobsity.bowling.utils.AppUtils.*;

public class FileParser implements BowlingParser {
	
	private final Path path;
	
	private int num = 1;
	private BufferedReader br;
	
	public FileParser(String path){
		this.path = Paths.get(path);
	}
	
	private PlayerEntry parseLine(int num, String line){
		if(line == null){
			throw new ParseException("The line parameter can not be null.");
		}
		
		
		String s = strip(line);
		if(s == null){
			throw new ParseException("The line parameter can not be empty.");
		}
		
		if(s.contains("\t") == false){
			throw new ParseException("The line " + num +" does not contain the tab character.");
		}
		
		String[] arr = s.split("\t");
		
		if(arr.length != 2){
			throw new ParseException("Invalid line " + num +": '" + line +"'.");
		}
		
		String player = strip(arr[0]);
		String pinfall = strip(arr[1]);
		
		if(player == null){
			throw new ParseException("Invalid player at line " + num +": '" + line +"'.");
		}
		
		if(pinfall == null){
			throw new ParseException("Invalid pinfall at line " + num +": '" + line +"'.");
		}
		
		
		try{
			PinfallType.parse(pinfall);
		}catch (InvalidScoreException ex){
			throw new ParseException("Invalid pinfall '" + pinfall +"' at line " + num + ".");
		}
		
		return new PlayerEntry(player, pinfall);
	}
	
	public void init(){
		try {
			br = new BufferedReader(new FileReader(path.toFile()));
		} catch (FileNotFoundException e) {
			throw new ParseException(e);
		}
		
	}
	
	public PlayerEntry next(){
		
		try {
			if(br == null) {
				throw new ParseException("The init method has not been called.");
			}
			
			String line;
			for(;;){
				line = br.readLine();
				if(line == null){
					return null;
				}
				
				if(isNullOrEmpty(line) == false){
					break;
					
				}
				//empty line
				num++;
			}
			PlayerEntry	entry = parseLine(num, line);
			num++;
			return entry;
			
		} catch (IOException e) {
			//Modern frameworks does not throw checked exceptions. That is why we capture it and
			//then re-throw with our app exception. It can then be caught by the client code and
			//call the method getCause().
			throw new ParseException(e);
		}
	}
	
	@Override
	public void close() throws Exception {
		if(br != null){
			try{
				br.close();
			}catch(IOException ex){
				//ignored, no need to make noise here
			}
		}
	}
}
