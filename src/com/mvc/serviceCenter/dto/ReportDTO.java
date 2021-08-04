package com.mvc.serviceCenter.dto;

import java.sql.Date;

public class ReportDTO {
	private int reportNo;
	private int categoryNo;
	private int contentNo;//변수명 소문자로 시작해야 되서 No앞에 contentNO붙임
	private int commentNo;
	private String email;
	private Date reportDate;
	private char state;
	private String rportText;
	private char blind;
	
	public int getReportNo() {
		return reportNo;
	}
	public void setReportNo(int reportNo) {
		this.reportNo = reportNo;
	}
	public int getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}
	public int getContentNo() {
		return contentNo;
	}
	public void setContentNo(int contentNo) {
		this.contentNo = contentNo;
	}
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public char getState() {
		return state;
	}
	public void setState(char state) {
		this.state = state;
	}
	public String getRportText() {
		return rportText;
	}
	public void setRportText(String rportText) {
		this.rportText = rportText;
	}
	public char getBlind() {
		return blind;
	}
	public void setBlind(char blind) {
		this.blind = blind;
	}
}
