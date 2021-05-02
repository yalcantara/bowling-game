package com.jobsity.bowling;

import com.jobsity.bowling.model.Sheet;
import org.junit.Test;

import static org.junit.Assert.*;

public class SheetTest {
	
	
	@Test
	public void testFrameScore(){
		
		Sheet s = new Sheet();
		s.append("3"); // 1st frame
		assertEquals(false, s.isReady(1));
		s.append("2");
		assertEquals(true, s.isReady(1));
		int score = s.computeFrameScore(1);
		assertEquals(5, score);
		
		s.append("x"); //2nd frame
		assertEquals(true, s.isReady(2));
		//we need to set the next 2 balls
		s.append("x"); //3rd frame
		s.append("x"); //4th frame
		score = s.computeFrameScore(2);
		assertEquals(30, score);
		
		s.append("7"); //5th frame
		s.append("/");
		assertEquals(true, s.isReady(5));
		assertEquals(20, s.computeFrameScore(4));
		
		s.append("1"); //6th frame
		s.append("9");
		s.append("x"); //7th frame
		s.append("4"); //8th frame
		s.append("/");
		s.append("2"); //9th frame
		s.append("/");
		
		assertEquals(true, s.isReady(9));
		assertEquals(12, s.computeFrameScore(8));
		
		s.append("x");
		s.append("x");
		s.append("x");
		assertEquals(true, s.isReady(10));
		assertEquals(30, s.computeFrameScore(10));
	}
}
