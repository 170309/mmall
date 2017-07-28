package com.mmall.util;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by lucifer on 17-6-15.
 */
public class FTPUtil {

    private static Logger logger = LoggerFactory.getLogger(FTPUtil.class);
    private  static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
    private  static String ftpUser = PropertiesUtil.getProperty("ftp.user");
    private  static String ftpPass = PropertiesUtil.getProperty("ftp.pass");


    private String ip;
    private int port;
    private String user;
    private String passwd;
    private FTPClient ftpClient;


    public FTPUtil(String ip,int port,String user,String passwd){
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.passwd = passwd;

    }

    /**
     *
     * @param fileList 上传的文件列表
     * @return
     */
    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp,21,ftpUser,ftpPass);
        logger.info("开始连接FTP服务器");
        boolean result = ftpUtil.uploadFile("img",fileList);
        logger.info("开始连接FTP服务器，结束上传，上传的结果:{}",result);
        return result;
    }

    /**
     *
     * @param remotePath 文件工作目录 ，需要更改到的路径
     * @param fileList 文件列表
     * @return
     */
    private boolean uploadFile(String remotePath,List<File> fileList) throws IOException{
        boolean uploaded = true;
        FileInputStream fis = null;
        if(connectServer(this.ip,this.port,this.user,this.passwd)){
            try {
                //更改工作目录
                ftpClient.changeWorkingDirectory(remotePath);
                //设置缓冲区长度
                ftpClient.setBufferSize(1024);
                //设置字符集编码格式UTF_8
                ftpClient.setControlEncoding("UTF-8");
                //设置文件类型 为二进制文件类型,可以防止乱码
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                //打开本地被动模式
                ftpClient.enterLocalPassiveMode();
                for(File fileItem : fileList){
                    fis = new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(),fis);
                }
            } catch (IOException e) {
                logger.error("上传文件异常",e);
                uploaded = false;
            } finally {
                fis.close();
                ftpClient.disconnect();
            }
        }
        return uploaded;
    }

    /**
     *
     * @param ip ftp服务器地址
     * @param port ftp服务端口
     * @param user ftpuser
     * @param passwd ftppasswd
     * @return
     */
    private boolean connectServer(String ip,int port ,String user,String passwd){
        ftpClient = new FTPClient();
        boolean isSuccess = false;
        try {
            //建立连接
            ftpClient.connect(ip);
            //登录
            isSuccess=ftpClient.login(user,passwd);
        } catch (IOException e) {
            //错误日志
             logger.error("连接ftp服务器异常",e);
        }
        return isSuccess;

    }



    public static String getFtpIp() {
        return ftpIp;
    }

    public static void setFtpIp(String ftpIp) {
        FTPUtil.ftpIp = ftpIp;
    }

    public static String getFtpUser() {
        return ftpUser;
    }

    public static void setFtpUser(String ftpUser) {
        FTPUtil.ftpUser = ftpUser;
    }

    public static String getFtpPass() {
        return ftpPass;
    }

    public static void setFtpPass(String ftpPass) {
        FTPUtil.ftpPass = ftpPass;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}
