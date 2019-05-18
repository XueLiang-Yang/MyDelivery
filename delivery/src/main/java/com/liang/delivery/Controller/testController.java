package com.liang.delivery.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class testController {

    @RequestMapping("/toUpload")
    public String toUpload(){
        return "info/Info";
    }

    @RequestMapping("/testUpload")
    @ResponseBody
    public String testUpload(@RequestParam(name = "uploadFile") MultipartFile file, String infoTitle, String infoContent){
        return "userfile:"+file.getOriginalFilename()+"  infoTitle:"+infoTitle+"  infoContent:"+infoContent;
    }
}