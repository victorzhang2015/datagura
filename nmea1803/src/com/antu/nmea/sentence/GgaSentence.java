package com.antu.nmea.sentence;

import java.util.Date;

import com.antu.nmea.annotation.*;

public class GgaSentence extends ParametricSentence {

	public GgaSentence() {
		super();
	}
	
	public GgaSentence(Date date) {
		super(date);
	}
	
	public GgaSentence(long currentTimeSinceEpochInSeconds) {
		super(currentTimeSinceEpochInSeconds);
	}
	
	@Override
	public String sentenceType() {
		return "gga";
	}

	@SentenceField(order = 1, fieldType="String")
	public String UTCtime;
	
	@SentenceField(order = 2, fieldType="double", precision=2)
	public double latitude;
	
	@SentenceField(order = 3, fieldType="char")
	public char earth = 'N'; //Î³¶È°ëÇò
	
	@SentenceField(order = 4, fieldType="double", precision=2)
	public double longitude;
	
	@SentenceField(order = 5, fieldType="char")
	public char earth2 = 'E'; //¾«¶È°ëÇò

}
