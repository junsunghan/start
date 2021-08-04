package com.mvc.msg.dto;

import java.sql.Date;

public class MsgDTO {
	private int msgNo;
	private String sender_email;
	private String receiver_email;
	private Date reg_date;
	private String msgContent;
	private String msgOpen;
	private String sendBlind;
	private String receiveBlind;
	
	public int getMsgNo() {
		return msgNo;
	}
	public void setMsgNo(int msgNo) {
		this.msgNo = msgNo;
	}
	public String getSender_email() {
		return sender_email;
	}
	public void setSender_email(String sender_email) {
		this.sender_email = sender_email;
	}
	public String getReceiver_email() {
		return receiver_email;
	}
	public void setReceiver_email(String receiver_email) {
		this.receiver_email = receiver_email;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getMsgOpen() {
		return msgOpen;
	}
	public void setMsgOpen(String msgOpen) {
		this.msgOpen = msgOpen;
	}
	public String getSendBlind() {
		return sendBlind;
	}
	public void setSendBlind(String sendBlind) {
		this.sendBlind = sendBlind;
	}
	public String getReceiveBlind() {
		return receiveBlind;
	}
	public void setReceiveBlind(String receiveBlind) {
		this.receiveBlind = receiveBlind;
	}

	
}
