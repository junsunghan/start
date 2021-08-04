package com.mvc.serviceCenter.dto;

import java.sql.Date;

public class QnaDTO {

	private int qnano;
	private String email;
	private Date reg_date;
	private String title;
	private String content;
	private char blind;
	
	public int getQnano() {
		return qnano;
	}
	public void setQnano(int qnano) {
		this.qnano = qnano;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public char getBlind() {
		return blind;
	}
	public void setBlind(char blind) {
		this.blind = blind;
	}
	
	
}
