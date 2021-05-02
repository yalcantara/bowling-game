package com.jobsity.bowling.model;

import com.jobsity.bowling.core.InvalidScoreException;
import com.jobsity.bowling.core.InvalidStateException;

import java.io.PrintWriter;
import java.io.Serializable;

import static com.jobsity.bowling.utils.AppUtils.*;
import static com.jobsity.bowling.utils.Constants.*;

public class Frame implements Serializable {
	
	
	private final int num;
	private Integer first;
	private boolean firstFoul = false;
	
	private Integer second;
	private boolean secondFoul = false;
	
	private Integer last; //used only in the 10th frame.
	private boolean lastFoul = false;
	
	public Frame(int num) {
		checkParamBetween("num", 1, 10, num);
		this.num = num;
	}
	
	private void checkSum(int pinfalls) {
		int sum = first + pinfalls;

		if (isLast() == false && first != STRIKE_VALUE && sum > TOTAL_PIN) {
			throw new InvalidScoreException("The sum of the pin falls for the first and " +
					"second shots, can not exceed " + TOTAL_PIN + ". First: " + first + "," +
					" second: " + pinfalls + " in frame " + num +".");
		}
	}
	
	private void unsupported(PinfallType type) {
		throw new InvalidScoreException("Unsupported score type " + type + ".");
	}
	
	public int getNum() {
		return num;
	}
	
	public Integer getFirst() {
		return first;
	}
	
	public PinfallType getFirstType() {
		if (first == null) {
			return null;
		}
		
		if (first == 10) {
			return PinfallType.STRIKE;
		}
		
		return PinfallType.NUMBER;
	}
	
	public PinfallType getSecondType() {
		if (second == null) {
			return null;
		}
		
		if (second == STRIKE_VALUE) {
			return PinfallType.STRIKE;
		}
		
		int f = checkAndGetFirst();
		if (f + second == TOTAL_PIN) {
			return PinfallType.SPARE;
		}
		
		return PinfallType.NUMBER;
	}
	
	public void setFirst(String val) {
		PinfallType type = PinfallType.parse(val);
		switch (type) {
			case SPARE:
				throw new InvalidScoreException("The first shot of a frame can not be a spare " +
						"(/)" +
						".");
			case FOUL:
				first = 0;
				firstFoul = true;
				break;
			case NUMBER:
				int num = Integer.parseInt(val);
				first = num;
				break;
			case STRIKE:
				first = STRIKE_VALUE;
				break;
			default:
				unsupported(type);
		}
		
		second = null;
		last = null;
	}
	
	public Integer getSecond() {
		return second;
	}
	
	public int checkAndGetFirst() {
		checkFirst();
		return first;
	}
	
	public int checkAndGetSecond() {
		checkSecond();
		return second;
	}
	
	public void checkFirst() {
		if (first == null) {
			throw new InvalidStateException("The first pinfall for frame " + num + " has not been " +
					"assigned.");
		}
	}
	
	public void checkSecond() {
		if (second == null) {
			throw new InvalidStateException("The second pinfall for frame " + num + " has not been" +
					" assigned.");
		}
	}
	
	
	public int checkAndGetLast() {
		checkLast();
		return last;
	}
	
	public void checkLast() {
		if (last == null) {
			throw new InvalidStateException("The last (extra) pinfall for frame " + num + " has " +
					"not been assigned.");
		}
	}
	
	public void setSecond(String val) {
		
		if (first == null) {
			throw new InvalidScoreException("The first score must be set before assigning the " +
					"value for the second shot.");
		}
		PinfallType type = PinfallType.parse(val);
		switch (type) {
			case SPARE:
				second = TOTAL_PIN - first;
				break;
			case FOUL:
				second = 0;
				secondFoul = true;
				break;
			case NUMBER:
				int pinfalls = Integer.parseInt(val);
				checkSum(pinfalls);
				second = pinfalls;
				break;
			case STRIKE:
				if (num < 10) {
					throw new InvalidScoreException("For this frame (" + num + ") the strike can " +
							"only happen in the first shot.");
				}
				
				if (first < TOTAL_PIN) {
					throw new InvalidScoreException("The first shot was not a strike (" + first +
							").");
				}
				second = STRIKE_VALUE;
				break;
			default:
				unsupported(type);
		}
		last = null;
	}
	
	public Integer getLast() {
		return last;
	}
	
	public void setLast(String val) {
		if (first == null || second == null) {
			throw new InvalidScoreException("The first and second score must be set before " +
					"assigning the " +
					"value for the last shot.");
		}
		PinfallType type = PinfallType.parse(val);
		switch (type) {
			case SPARE:
				throw new InvalidScoreException("Can not set a spare for the extra shot in the " +
						"10th frame.");
			case FOUL:
				last = 0;
				lastFoul = true;
				break;
			case NUMBER:
				int num = Integer.parseInt(val);
				last = num;
				break;
			case STRIKE:
				last = STRIKE_VALUE;
				break;
			default:
				unsupported(type);
		}
	}
	
	public void checkIsReady() {
		if (isReady() == false) {
			throw new InvalidStateException("The frame " + num + " is not ready yet.");
		}
	}
	
	public boolean isReady() {
		if (num < 10) {
			if (first == null) {
				return false;
			}
			
			if (first == STRIKE_VALUE) {
				return true;
			}
			
			return second != null;
		}
		
		if (first == null || second == null) {
			return false;
		}
		
		
		//A strike on the 10'th frame gets 2 more shots
		if (first == STRIKE_VALUE) {
			return second != null && last != null;
		}
		
		//If the second is a spare, then the user get's one extra shot
		if (first + second == TOTAL_PIN) {
			return last != null;
		}
		
		return true;
	}
	
	public int directSum() {
		int ans = 0;
		
		ans += (first == null) ? 0 : first;
		ans += (second == null) ? 0 : second;
		if (num == 10) {
			//the 10th frame has the special case
			ans += (last == null) ? 0 : last;
		}
		
		return ans;
	}
	
	public void append(String val) {
		if (first == null) {
			setFirst(val);
			return;
		}
		
		if (second == null) {
			setSecond(val);
			return;
		}
		
		if (last == null) {
			setLast(val);
			return;
		}
		throw new InvalidStateException("Can not append another score to this frame.");
	}
	
	//Convenient method checking if it is the last frame (10th).
	public boolean isLast() {
		return num == TOTAL_FRAMES;
	}
	
	public boolean isSpare(){
		int f = checkAndGetFirst();
		if(f == STRIKE_VALUE){
			return false;
		}
		
		int s = checkAndGetSecond();
		if(f + s == TOTAL_PIN){
			return true;
		}
		
		return false;
	}
	
	
	public String getDisplay(int shot){
		checkParamBetween("shot", 1, 3, shot);
		
		if(shot == 1 && firstFoul) return "F";
		if(shot == 2 && secondFoul) return "F";
		if(shot == 3 && lastFoul) return "F";
		
		Integer val = (shot == 1)?first:(shot==2)?second:last;
	
		if(val == null){
			return "";
		}
		
		if(val == STRIKE_VALUE){
			return "X";
		}
		
		if(shot == 2 && isSpare()){
			return "/";
		}
		
		return String.valueOf(val);
	}
	
	public void print(PrintWriter pw){

		if(isLast()){
			
			String f = getDisplay(1);
			pw.write(f);
			pw.write('\t');
			String s = getDisplay(2);
			pw.write(s);
			
			PinfallType secondType = getSecondType();
			if(getFirstType() == PinfallType.STRIKE || secondType == PinfallType.SPARE){
				String l = getDisplay(3);
				pw.write('\t');
				pw.write(l);
				return;
			}
			return;
		}
		
		String f = getDisplay(1);
		if(getFirstType() == PinfallType.STRIKE){
			pw.write('\t'); // in case of a strike, it should be aligned to the right
			pw.write(f);
			return;
		}
		
		String s = getDisplay(2);
		
		pw.write(f);
		pw.write('\t');
		pw.write(s);
	}
	
	@Override
	public String toString() {
		return "Frame{" +
				"num=" + num +
				", first=" + first +
				", second=" + second +
				", last=" + last +
				'}';
	}
}
