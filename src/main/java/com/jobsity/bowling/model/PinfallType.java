package com.jobsity.bowling.model;

import com.jobsity.bowling.core.InvalidScoreException;

import static com.jobsity.bowling.utils.Constants.*;
import static com.jobsity.bowling.utils.AppUtils.*;

public enum PinfallType {
	NUMBER,
	FOUL,
	STRIKE,
	SPARE;


	public static PinfallType parse(String str){
		checkParamNotNull("str", str);

		if("X".equalsIgnoreCase(str)){
			return STRIKE;
		}
		
		if("F".equalsIgnoreCase(str)){
			return FOUL;
		}

		if("/".equals(str)){
			return SPARE;
		}

		try{
			int val = Integer.parseInt(str);
			
			if(val == TOTAL_PIN){
				return STRIKE;
			}
			
			if(val < 0 || val > TOTAL_PIN){
				throw new InvalidScoreException("The input '" + str + "' is not a valid score number.");
			}
			return NUMBER;
		}catch(NumberFormatException ex){
			throw new InvalidScoreException("Could not parse the input '" + str +"' into a valid score.");
		}
	}
}
