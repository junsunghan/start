package com.mvc.board.dto;

import java.sql.Date;

public class CommentDTO {
	private int footPrintNo;
	private int commentNo;
	private String email;
	private Date reg_date;
	private String commentText;
	private char commentBlind;
	private int likeCnt;
	public int getFootPrintNo() {
		return footPrintNo;
	}
	public void setFootPrintNo(int footPrintNo) {
		this.footPrintNo = footPrintNo;
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
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public char getCommentBlind() {
		return commentBlind;
	}
	public void setCommentBlind(char commentBlind) {
		this.commentBlind = commentBlind;
	}
	public int getLikeCnt() {
		return likeCnt;
	}
	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}
}