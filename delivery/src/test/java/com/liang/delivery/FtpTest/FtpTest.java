package com.liang.delivery.FtpTest;

import com.liang.delivery.Tools.FtpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FtpTest {
    @Resource
    private FtpClient ftpClient;
    @Test
    public void FtpPropertiesTest(){
        //ftpClient.testFtp1();
        File file = new File("src/test/resources/static/img/1.jpg");
        System.out.println(file.exists());
        System.out.println(file.getAbsolutePath());

        Map map = new HashMap();
        map.put("File",file);
      ftpClient.UploadToFtp(map,"headPortrait/","asd.jpg");
    }
}
