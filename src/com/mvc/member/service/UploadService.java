package com.mvc.member.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.mvc.member.dto.MemberDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class UploadService {

	private HttpServletRequest req = null;
	
	public UploadService(HttpServletRequest req) {
		this.req = req;
	}

	public MemberDTO Upload(String email) {
		String savePath="C:/img/";
		int maxSize = 15 * 1024 * 1024;
		MemberDTO dto = null;	
		System.out.println("누구? :" +email);
		
		try {
			File dir = new File(savePath);
			if(!dir.exists()) {
				dir.mkdir();
			}	
			DefaultFileRenamePolicy policy = new DefaultFileRenamePolicy();
			MultipartRequest multi = new MultipartRequest(req, savePath, maxSize, "UTF-8", policy);
		
			dto = new MemberDTO();
			String oriName = multi.getFilesystemName("photo");	
			System.out.println("기존 파일 이름 : "+oriName);
			if(oriName != null) {//업로드 된 파일이 있을 경우
				String ext = oriName.substring(oriName.lastIndexOf("."));
				String newName  = System.currentTimeMillis()+ext;
				System.out.println("새로운 파일 이름 : "+newName);
				File oldName = new File(savePath+oriName);
				File saveName = new File(savePath+newName);
				oldName.renameTo(saveName);	
				dto.setEmail(email);
				dto.setOriName(oriName);
				dto.setNewName(newName);
				System.out.println("upservice 완료");
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public boolean del(String newName) {
		File file = new File("C:/img/"+newName);
		boolean success = false;
		
		if(file.exists()) {
			success = file.delete();
			System.out.println("파일 삭제 성공 여부 : "+success);
		}
		return success;
		
	}

	
	
}
