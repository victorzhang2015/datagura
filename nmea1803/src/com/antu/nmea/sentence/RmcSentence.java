package com.antu.nmea.sentence;

import java.util.Date;

import com.antu.nmea.annotation.*;

public class RmcSentence extends ParametricSentence {

	public RmcSentence() {
		super();
	}
	
	public RmcSentence(Date date) {
		super(date);
	}
	
	public RmcSentence(long currentTimeSinceEpochInSeconds) {
		super(currentTimeSinceEpochInSeconds);
	}
	
	@Override
	public String sentenceType() {
		return "rmc";
	}

	@SentenceField(order = 1, fieldType="boolean")
	public boolean arrivalCircleEntered = false;
	
	@SentenceField(order = 2, fieldType="boolean")
	public boolean perpendicularPassedAtWaypoint = false;
	
	@SentenceField(order = 3, fieldType="double", precision=2)
	public double arrivalCircleRadius = 0.0;
	
	@SentenceField(order = 4, fieldType="char")
	public char unitOfRadius = 'N';
	
	@SentenceField(order = 5, fieldType="string")
	public String waypointId = "";
}
