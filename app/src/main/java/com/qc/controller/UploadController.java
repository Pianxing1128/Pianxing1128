package com.qc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
public class UploadController {
    @RequestMapping("/upload")
    public String upload(MultipartFile file) {
        //图片校验（图片是否为空,图片大小，上传的是不是图片、图片类型（例如只能上传png）等等）
        if (file.isEmpty()) {
            return "图片上传失败";
        }
        //可以自己加一点校验 例如上传的是不是图片或者上传的文件是不是png格式等等 这里省略
        //获取原来的文件名和后缀
        String originalFilename = file.getOriginalFilename();
//        String ext = "." + FilenameUtils.getExtension(orgFileName); --需要导依赖
        String ext = "."+ originalFilename.split("\\.")[1];
        String[] str = originalFilename.split("\\.");
        int len = str.length;
        String suffix = "."+ str[len-1];
        //生成一个新的文件名（以防有重复的名字存在导致被覆盖）
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String newName = uuid + suffix;
        //拼接图片上传的路径 url+图片名
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String location = "d:\\documents\\";
        System.out.println("applicationHome.getDir():"+applicationHome.getDir());
        System.out.println(" applicationHome.getDir().getParentFile():"+ applicationHome.getDir().getParentFile());
        System.out.println("applicationHome.getDir().getParentFile().getParentFile():"+applicationHome.getDir().getParentFile().getParentFile());
        System.out.println("applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath():"+applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath());
//      applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() + "\\src\\main\\resources\\static\\images\\";
        //E:\Users\qc112\course-server\app\src\main\resources\static\images\39b35160093b4c12b83731451e640f2c.png
        String wholeName = location + newName;
        try {
            file.transferTo(new File(wholeName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wholeName;
    }

}
