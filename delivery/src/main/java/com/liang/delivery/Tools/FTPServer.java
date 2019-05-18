package com.liang.delivery.Tools;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

@ConfigurationProperties(prefix = "ftp.server")
public class FTPServer {

    private String host;
    private int port;
    private String username;
    private String password;
    private String path;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FTPClient getFtpClient() throws IOException {
        FTPClient ftp = new FTPClient();
        //连接ftp服务器
        ftp.connect(this.host,this.port);
        //登录
        ftp.login(this.username, this.password);
        //设置上传路径
        String path=this.path;
        return ftp;
    }

    public void close(FTPClient ftp) throws IOException {
            ftp.logout();
            ftp.disconnect();
    }
    @Override
    public String toString() {
        return "FTPServer{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

}
