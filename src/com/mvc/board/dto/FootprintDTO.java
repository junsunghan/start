package com.mvc.board.dto;


import java.sql.Date;

public class FootprintDTO {
	private int footPrintNO;
	private int markerNO;
	private String email;
	private Date reg_date;
	private String footprintText;
	private char release;//공개 비공개 여부. public이 안됨
	private char postBlind;
	private int likeCnt;
	private String oriFileName;
	private String newFileName;
	private int boardNO;
	private String hashTag; 
	
	public String getHashTag() {
		return hashTag;
	}
	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}
	public int getBoardNO() {
		return boardNO;
	}
	public void setBoardNO(int boardNO) {
		this.boardNO = boardNO;
	}
	public int getFootPrintNO() {
		return footPrintNO;
	}
	public void setFootPrintNO(int footPrintNO) {
		this.footPrintNO = footPrintNO;
	}
	public int getMarkerNO() {
		return markerNO;
	}
	public void setMarkerNO(int markerNO) {
		this.markerNO = markerNO;
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
	public String getFootprintText() {
		return footprintText;
	}
	public void setFootprintText(String footprintText) {
		this.footprintText = footprintText;
	}
	public char getRelease() {
		return release;
	}
	public void setRelease(char release) {
		this.release = release;
	}
	public char getPostBlind() {
		return postBlind;
	}
	public void setPostBlind(char postBlind) {
		this.postBlind = postBlind;
	}
	public int getLikeCnt() {
		return likeCnt;
	}
	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}
	public String getOriFileName() {
		return oriFileName;
	}
	public void setOriFileName(String oriFileName) {
		this.oriFileName = oriFileName;
	}
	public String getNewFileName() {
		return newFileName;
	}
	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}
	
	
	
	
}
