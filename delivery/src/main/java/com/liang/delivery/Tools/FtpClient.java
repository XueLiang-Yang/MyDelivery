package com.liang.delivery.Tools;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Map;
import java.util.UUID;

public class FtpClient {
    private FTPServer ftpServer;
    private static final Logger logger = LoggerFactory.getLogger(FtpClient.class);

    public FtpClient(FTPServer ftpServer) {
        this.ftpServer = ftpServer;
    }
    /*根据*/
    /*创建用户文档*/
    public boolean createDocument(String path){
        FTPClient ftp = null; //创建客户端对象
        try {
            ftp = ftpServer.getFtpClient();
            //检查上传路径是否存在 如果不存在返回false
            boolean flag = ftp.changeWorkingDirectory(path);
            if(!flag){
                //创建上传的路径  该方法只能创建一级目录，在这里如果/home/ftpuser存在则可创建指定目录
                ftp.makeDirectory(path);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public String UploadToFtp(Map uploadfile, String relativePath, String fileName){
        String path = ftpServer.getPath()+relativePath;
        InputStream local=null;
        FTPClient ftp = null;
        //获取文件名后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 创建临时文件
        File tempFile = null;
        try {
            //创建客户端对象
            ftp = ftpServer.getFtpClient();
            //检查上传路径是否存在 如果不存在返回false
            boolean flag = ftp.changeWorkingDirectory(path);
            if(!flag){
                //创建上传的路径  该方法只能创建一级目录，在这里如果/home/ftpuser存在则可创建指定目录
                ftp.makeDirectory(path);
            }
            //指定上传路径
            ftp.changeWorkingDirectory(path);
            //指定上传文件的类型  二进制文件
            ftp.setFileType(FTP.BINARY_FILE_TYPE);


            //读取上传的文件
            //File file = new File("E:\\apache-tomcat-8.5.34-windows-x64.zip");
            if(uploadfile.get("MultipartFile")!=null){
                //以uuid作为临时文件名，防止临时文件重复命名
                tempFile = File.createTempFile(UUID.randomUUID().toString(),prefix);
                MultipartFile file = (MultipartFile) uploadfile.get("MultipartFile");
                file.transferTo(tempFile);
                local = new FileInputStream(tempFile);
            }else{
                File file = (File) uploadfile.get("File");
                local = new FileInputStream(file);
            }

            //第一个参数是文件名
            ftp.storeFile(fileName, local);
            return "SUCCESS";
        } catch (SocketException e) {
            return "NORESPONSE";
        } catch (IOException e) {
            return "FILENOTEXIST";
        }finally {
            try {
                /*如果有临时文件*/
                if(uploadfile.get("MultipartFile")!=null){
                    //删除临时文件
                    tempFile.delete();
                }
                //关闭文件流
                local.close();
                //退出
                ftpServer.close(ftp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
