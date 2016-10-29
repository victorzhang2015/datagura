package com.antu.nmea.sentence;

import java.util.Date;

public class MutiVdmSentence implements IGroupedSentence {
	
	private VdmSentence vdm;
	private Date date;//接收时间
	
	
	/**
	 * @return the vdm
	 */
	public VdmSentence getVdm() {
		return vdm;
	}
	/**
	 * @param vdm the vdm to set
	 */
	public void setVdm(VdmSentence vdm) {
		this.vdm = vdm;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	//构造有参参构造器
	public MutiVdmSentence(Date time1,VdmSentence vdmSentence) {
		date=time1;
		vdm=vdmSentence;
	}
	public MutiVdmSentence() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String sentenceType() {
		// TODO Auto-generated method stub
		return "vdm";
	}

	@Override
	public Date getReceiveDate() {
		// TODO Auto-generated method stub
		return date;
	}
	@Override
	public boolean isComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void concatenate(IGroupedSentence sentence) {
		// TODO Auto-generated method stub
		MutiVdmSentence m = (MutiVdmSentence) sentence;
		String message=vdm.getEncapsulatedData();
		message=message+m.getVdm().getEncapsulatedData();
		vdm.setEncapsulatedData(message);
	}
	@Override
	public int getSequentialMessageId() {
		// TODO Auto-generated method stub
		return vdm.getSequentialMessageId();
	}
	@Override
	public int getTotalNumberOfSentences() {
		// TODO Auto-generated method stub
		return vdm.getTotalNumberOfSentences();
	}
	@Override
	public int getSentenceNumber() {
		// TODO Auto-generated method stub
		return vdm.getSentenceNumber();
	}
}
