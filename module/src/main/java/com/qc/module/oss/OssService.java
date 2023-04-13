package com.qc.module.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;

@Service
@Slf4j
public class OssService {
    public String uploadPicture(MultipartFile image,String filePath) {

        String endpoint = AliyunOssConstant.END_POINT;
        String accessKeyId = AliyunOssConstant.KEY_ID;
        String accessKeySecret = AliyunOssConstant.KEY_SECRET;
        String bucketName = AliyunOssConstant.BUCKET_NAME;

        try {
            InputStream inputStream = image.getInputStream();
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            ossClient.putObject(bucketName, filePath, inputStream);
            ossClient.shutdown();
            String url = "https://" + bucketName + "." + endpoint + "/" + filePath;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
    }
}
