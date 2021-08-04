package com.mvc.member.dto;

import java.sql.Date;

public class MemberDTO {
	private String email;
	private String nickname;
	private String pw;
	private String name;
	private String gender;
	private Date birth;	
	private String phone;
	private char blackList;
	private char accountBan;	
	private char adminState;
	private int warning;
	private char cancelMember;
	private String tourstyle;
	// 프로필 사진
	private String oriName;
	private String newName;
	
	
	public String getOriName() {
		return oriName;
	}
	public void setOriName(String oriName) {
		this.oriName = oriName;
	}
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	public String getTourstyle() {
		return tourstyle;
	}
	public void setTourstyle(String tourstyle) {
		this.tourstyle = tourstyle;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
		public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public char getBlackList() {
		return blackList;
	}
	public void setBlackList(char blackList) {
		this.blackList = blackList;
	}
	public char getAccountBan() {
		return accountBan;
	}
	public void setAccountBan(char accountBan) {
		this.accountBan = accountBan;
	}
	public char getAdminState() {
		return adminState;
	}
	public void setAdminState(char adminState) {
		this.adminState = adminState;
	}
	public int getWarning() {
		return warning;
	}
	public void setWarning(int warning) {
		this.warning = warning;
	}
	public char getCancelMember() {
		return cancelMember;
	}
	public void setCancelMember(char cancelMember) {
		this.cancelMember = cancelMember;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	
}
