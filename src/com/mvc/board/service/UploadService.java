package com.mvc.board.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;


import com.mvc.board.dto.FootprintDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;


public class UploadService {

	private HttpServletRequest req = null;
	
	public UploadService(HttpServletRequest req) {
		this.req = req;
	}

	public FootprintDTO photoUpload() {
		String savePath ="C:/img/";
		int maxSize = 10*1024*1024;
		FootprintDTO dto = null;
		
		
		try {
			File dir = new File(savePath); 
			if(!dir.exists()) {
				dir.mkdir();
			}
			MultipartRequest multi = new MultipartRequest(req, savePath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
			
			dto = new FootprintDTO();
			
			String footprintText = multi.getParameter("footprintText");
			System.out.println("footprintText : "+footprintText);
			dto.setFootprintText(footprintText);
			
			String footPrintNO = multi.getParameter("footPrintNO");
			if(footPrintNO != null) {
				System.out.println("footPrintNO :"+footPrintNO);
				dto.setFootPrintNO(Integer.parseInt(footPrintNO));
			}
			String ok = multi.getParameter("ok");
			dto.setRelease(ok.charAt(0));
			
			String oriFileName = multi.getFilesystemName("PostPic");
			System.out.println("oriFileName : "+oriFileName);
			if(oriFileName != null) {
				String ext = oriFileName.substring(oriFileName.lastIndexOf("."));
				String newFileName = System.currentTimeMillis()+ext;
				System.out.println("newFileName : "+newFileName);
				File oldName = new File(savePath+oriFileName);
				File newName = new File(savePath+newFileName);
				oldName.renameTo(newName);
				dto.setOriFileName(oriFileName);
				dto.setNewFileName(newFileName);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public boolean fpdel(String newFileName) {
		
		File file = new File("C:/img/"+newFileName);
		boolean success = false;
		
		if(file.exists()) {
			success = file.delete();
			System.out.println("사진 삭제 성공 여부 : "+success);
		}
		return success;
	}

}

