package com.jobsity.bowling;

import com.jobsity.bowling.model.Frame;
import org.junit.Test;

import static org.junit.Assert.*;

public class FrameTest {

	
	@Test
	public void testIsReady(){
		Frame f1 = new Frame(1);

		f1.setFirst("2");
		f1.setSecond("/");
		assertEquals(true, f1.isReady());
		assertEquals(10, f1.directSum());
		
		Frame f2 = new Frame(9);
		f2.setFirst("9");
		f2.setSecond("/");
		assertEquals(true, f2.isReady());
		assertEquals(10, f2.directSum());
		
		Frame f9 = new Frame(9);
		f9.setFirst("x");
		assertEquals(true, f9.isReady());
		assertEquals(10, f9.directSum());
		
		Frame f10 = new Frame(10);
		f10.setFirst("3");
		f10.setSecond("4");
		//on the 10th frame if the sum is less than 10, no extra shot
		assertEquals(true, f10.isReady());
		assertEquals(7, f10.directSum());
		
		f10.setFirst("0");
		f10.setSecond("0");
		assertEquals(true, f10.isReady());
		assertEquals(0, f10.directSum());
		
		f10.setFirst("8");
		f10.setSecond("2");
		assertEquals(false, f10.isReady());
		
		f10.setFirst("9");
		f10.setSecond("/");
		f10.setLast("2");
		assertEquals(true, f10.isReady());
		assertEquals(12, f10.directSum());
		
		f10.setFirst("x");
		f10.setSecond("x");
		f10.setLast("x");
		assertEquals(true, f10.isReady());
		assertEquals(30, f10.directSum());
	}
}
