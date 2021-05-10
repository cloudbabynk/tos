package net.huadong.tech.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import net.huadong.tech.base.bean.HdRunTimeException;

public class FtpUpload {
	 //上传到滚装客户passinfoack目录下
	 public static void uploadPassInfoAck_GZ (String filePath,String fileName) {
		 /**
		  * 1:本机FTP服务器测试   、2:客户FTP服务器测试
		  */
//	        String ftpHost = "10.130.2.236";
//	        String ftpUserName = "scott"; 
//	        String ftpPassword = "scott";
//	        int ftpPort = 21;
//	        String ftpPath = "FTP下载/";
//	        String localPath = "";
		    String ftpHost = "10.128.137.65";
	        String ftpUserName = "ggzmt"; 
	        String ftpPassword = "ggzmt";
	        int ftpPort = 21;
	        String ftpPath = "passinfoack/";
	        String localPath = "";
	        //上传一个文件
	        try{
	        	File file=new File(filePath);
	        	if(!file.exists()){
	            throw new HdRunTimeException("要读取的文件不存在");  	
	        	}
	            FileInputStream input=new FileInputStream(file);

	            boolean test =FtpUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName, input);
	            System.out.println(test);
	        } catch (FileNotFoundException e){
	            e.printStackTrace();
	            System.out.println(e);
	        }

	        //在FTP服务器上生成一个文件，并将一个字符串写入到该文件中
//	        try {
//	            InputStream input = new ByteArrayInputStream("test ftp jyf".getBytes("GBK"));
//	            boolean flag = FtpUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName,input);;
//	            System.out.println(flag);
//	        } catch (UnsupportedEncodingException e) {
//	            e.printStackTrace();
//	        }

	        //下载一个文件
//	        FtpUtil.downloadFtpFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, localPath, fileName);
	    }
	 //上传到环球客户passinfoack目录下
	 public static void uploadPassInfoAck_HQ (String filePath,String fileName) {
		    String ftpHost = "10.128.137.65";
	        String ftpUserName = "ghqgz"; 
	        String ftpPassword = "ghqgz";
	        int ftpPort = 21;
	        String ftpPath = "passinfoack/";
	        String localPath = "";
	        //上传一个文件
	        try{
	        	File file=new File(filePath);
	        	if(!file.exists()){
	            throw new HdRunTimeException("要读取的文件不存在");  	
	        	}
	            FileInputStream input=new FileInputStream(file);

	            boolean test =FtpUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName, input);
	            System.out.println(test);
	        } catch (FileNotFoundException e){
	            e.printStackTrace();
	            System.out.println(e);
	        }

	    }
}
