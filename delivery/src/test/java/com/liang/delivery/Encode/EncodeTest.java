package com.liang.delivery.Encode;

import com.liang.delivery.Entity.UserEntity;
import com.liang.delivery.Tools.Encode;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class EncodeTest {
    @Test
    public void md5Test(){
        byte b = 1;
        byte b2 = 'a';
       /* System.out.println(b2);
        System.out.println(Encode.MD5EncodeUtf8("agoihaoigjhgoiahewgeoihgaeognaewopigajgoaewjgfaepojgoapg"));
        System.out.println(UUID.randomUUID().toString());*/
       String fileName = "asd.asd.asd.zip";
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String relativeFileName = UUID.randomUUID().toString()+suffix;
        System.out.println(relativeFileName);
    }

    @Test
    public void test(){
        UserEntity user = new UserEntity();
        user.setId(123);
        change(user);
        System.out.println(user.getId());
    }

    @Test
    public void TestMap(){
        Map<String,Integer> map = new HashMap<>();
        map.put("123",1);
        int result = map.get("321");
        System.out.println(result);
    }
    public void change(UserEntity user){
        user.setId(233);
    }
}
