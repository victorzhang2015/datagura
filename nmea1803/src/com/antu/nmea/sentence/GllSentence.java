package com.antu.nmea.sentence;

import java.util.Date;

import com.antu.nmea.annotation.*;

public class GllSentence extends ParametricSentence {

	public GllSentence() {
		super();
	}
	
	public GllSentence(Date date) {
		super(date);
	}
	
	public GllSentence(long currentTimeSinceEpochInSeconds) {
		super(currentTimeSinceEpochInSeconds);
	}
	
	@Override
	public String sentenceType() {
		return "gll";
	}


	@SentenceField(order = 1, fieldType="double", precision=2)
	public double latitude;
	
	@SentenceField(order = 2, fieldType="char")
	public char earth = 'N'; //Î³¶È°ëÇò
	
	@SentenceField(order = 3, fieldType="double", precision=2)
	public double longitude;
	
	@SentenceField(order = 4, fieldType="char")
	public char earth2 = 'E'; //¾«¶È°ëÇò
	
	@SentenceField(order = 5, fieldType="String")
	public String UTCtime;
	
}
