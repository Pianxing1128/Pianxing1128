package com.qc.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@Slf4j
public class UrlController {
    @SneakyThrows
    @RequestMapping("/url/download")
    public String urlDownload(String pictureUrl) {

        //pictureUrl = "http://up.deskcity.org/pic_source/2f/f4/42/2ff442798331f6cc6005098766304e39.jpg";
        //建立图片连接
        URL url = new URL(pictureUrl);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        //设置请求方式
        connection.setRequestMethod("GET");
        //设置超时时间
        connection.setConnectTimeout(10*1000);

        //输入流
        InputStream stream = connection.getInputStream();
        int len = 0;
        byte[] test = new byte[1024];

        //获取项目路径
        String path = ResourceUtils.getURL("classpath:").getPath() + "static/images";
        File directory = new File("src/main/resources/static/images");
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String paths =  applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath();
        //如果没有文件夹则创建
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }

        //设置图片名称，这个随意，我是用的当前时间命名 改为UUID
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:mm:dd:hh:mm:ss");
        String date = dateFormat.format(new Date());
        String uuid = UUID.randomUUID().toString();
        String fileName = date + ".png";
        String newFileName= uuid +".png";

        //输出流，图片输出的目的文件
        path = paths  +"\\src\\main\\resources\\static\\images\\"+ newFileName;
        BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(path));

        /**
         * 以流的方式上传
         * public void write(byte[] b,int off,int len)
         * 参数说明
         * b:指定的字节
         * off:数组b中将写入数据的初始偏移量
         * len:待读取的最大字节数
         */

        while ((len =stream.read(test)) !=-1){
            fos.write(test,0,len);
        }

        //记得关闭流，不然消耗资源
        stream.close();
        fos.close();
        return path;
    }
}
