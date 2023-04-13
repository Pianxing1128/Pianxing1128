package com.qc.controller.upload;

import cn.hutool.core.date.DateTime;


import com.qc.module.oss.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.UUID;

@RestController
public class OssController {

    @Autowired
    public OssService ossService;

    @RequestMapping("/upload/Image")
    public String uploadImage(@RequestParam(required = false,name="file") MultipartFile image){
        //获取上传文件 MultipartFile
        //返回图片在oss上的路径
        //获取文件名称
        String originalFilename = image.getOriginalFilename();
        //获得上传图片的宽高
        int width = 0;
        int height = 0;
        try{
            BufferedImage img = ImageIO.read(image.getInputStream());
            width = img.getWidth();
            height = img.getHeight();
        }catch (Exception e){
            e.printStackTrace();
        }
        String[] str = originalFilename.split("\\.");
        int len = str.length;
        String suffix = "."+ str[len-1];
        //生成一个新的文件名（以防有重复的名字存在导致被覆盖）
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");;
        String date = new DateTime().toString("yyyy/MM/dd");
        String filePath = "upload/image/"+date + "/" + uuid+"_"+ width +"x"+ height + suffix;

        return ossService.uploadPicture(image, filePath);
    }


}
