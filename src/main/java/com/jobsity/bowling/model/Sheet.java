package com.jobsity.bowling.model;

import com.jobsity.bowling.game.BowlingException;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static com.jobsity.bowling.utils.Constants.*;
import static com.jobsity.bowling.utils.AppUtils.*;

public class Sheet implements Serializable {

	

	private String player;
	
	private final List<Frame> frames;
	private final Integer[] score;
	
	private int crt = 0;

	public Sheet(){
		frames = new ArrayList<>(TOTAL_FRAMES);
		for(int i =0; i < TOTAL_FRAMES; i++){
			frames.add(new Frame(i + 1));
		}
		
		score = new Integer[TOTAL_FRAMES];
	}
	
	public String getPlayer() {
		return player;
	}
	
	public void setPlayer(String player) {
		this.player = player;
	}
	
	public void append(String val){
		if(crt == frames.size()){
			throw new BowlingException("Maximun number of frames/shots reached.");
		}
		Frame f = frames.get(crt);
		f.append(val);
		if(f.isReady()){
			crt++;
		}
	}
	
	public boolean isReady(int frameNum){
		checkParamBetween("frameNum", 1, frames.size(), frameNum);
		return frames.get(frameNum - 1).isReady();
	}
	
	public int computeScore(int num){
		
		int score = 0;
		
		for(int i = 1; i <= num; i++){
			int frameScore = computeFrameScore(i);
			score += frameScore;
		}
		
		return score;
	}
	
	public int computeFrameScore(int num){
		int idx = num - 1;
		Frame f = frames.get(idx);
		f.checkIsReady();
		
		if(f.isLast()){
			int sum = f.getFirst();
			if(f.getFirstType() == PinfallType.STRIKE){
				sum += sumOfNext2(idx, 1);
				return sum;
			}
			
			sum += f.getSecond();
			if(f.getSecondType() == PinfallType.STRIKE ||
					f.getSecondType() == PinfallType.SPARE){
				sum += nextShotPinfall(idx, 2);
			}
			
			Integer last = f.getLast();
			if(last != null){
				sum += last;
			}
			return sum;
		}
		
		if(f.getFirstType() == PinfallType.STRIKE){
			int sum = sumOfNext2(idx, 1);
			int score = STRIKE_VALUE + sum;
			return score;
		}
		
		if(f.getSecondType() == PinfallType.SPARE){
			int nextOne = nextShotPinfall(idx, 2);
			int score = TOTAL_PIN + nextOne;
			return score;
		}
		
		int score = f.getFirst() + f.getSecond();
		return score;
	}
	
	private int nextShotPinfall(int frameIdx, int shot) {
		checkParamBetween("frameIdx", 0, frames.size() - 1, frameIdx);
		checkParamBetween("shot", 1, 2, shot);
		
		Frame f = frames.get(frameIdx);
		
		f.checkIsReady();
		
		if(f.isLast()){
			if(shot == 1){
				return f.checkAndGetSecond();
			}
			
			if(shot == 2){
				return f.checkAndGetLast();
			}
			//unreachable code
			//assert false
			throw new AssertionError("Unreachable code.");
		}
		
		Frame next = frames.get(frameIdx + 1);
		return next.checkAndGetFirst();
	}
	
	private int sumOfNext2(int frameIdx, int shot){
		checkParamBetween("frameIdx", 0, frames.size()-1, frameIdx);
		checkParamBetween("shot", 1, 2, shot);
		
		Frame f = frames.get(frameIdx);
		
		f.checkIsReady();
		
		if(f.isLast()){
			if(shot == 1){
				int sum = f.getSecond() + f.getLast();
				return sum;
			}
			
			if(shot == 2){
				return f.getLast();
			}
			//unreachable code
			//assert false
			throw new AssertionError("Unreachable code.");
		}
		
		Frame next = frames.get(frameIdx + 1);
		int nfirst = next.checkAndGetFirst();
		
		if(nfirst == STRIKE_VALUE){
			if(next.isLast()){
				int sum = nfirst + next.checkAndGetSecond();
				return sum;
			}
			
			Frame third = frames.get(frameIdx + 2);
			
			int sum = nfirst + third.checkAndGetFirst();
			return sum;
		}
		
		int sum = nfirst + next.checkAndGetSecond();
		
		return sum;
	}
	
	public void print(){
		PrintWriter pw = new PrintWriter(System.out);
		print(pw);
		pw.flush();
	}
	
	public void print(PrintWriter pw){
		String name = (player ==null)?"":player;
		pw.println(name);
		pw.print("Pinfalls\t");
		
		IntStream.range(0, frames.size()).forEach((i)->{
			Frame frame = frames.get(i);
			frame.print(pw);
			if(i + 1 < frames.size()) {
				pw.print("\t");
			}
		});
		
		
		pw.println();
		pw.print("Score\t\t");
		IntStream.range(0, frames.size()).forEach((i)->{
			int num = i + 1;
			int score = computeScore(num);
			pw.print(score);
			if(i + 1 < frames.size()) {
				pw.print("\t\t");
			}
		});
		
	}
	
	@Override
	public String toString() {
		return "Sheet{" +
				"player='" + player + '\'' +
				", frames=" + frames.toString() +
				", score=" + Arrays.toString(score) +
				", crt=" + crt +
				'}';
	}
}
