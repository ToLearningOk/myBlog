package com.djt.service.impl;

import com.djt.domain.ResponseResult;
import com.djt.enums.AppHttpCodeEnum;
import com.djt.exception.SystemException;
import com.djt.service.UploadService;
import com.djt.utils.PathUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;

@ConfigurationProperties(prefix = "oss")//获取配置文件中oss中信息
@Service
public class OssUploadServiceImpl implements UploadService {
    //需要set方法，否则无法注值
    String accessKey;   String secretKey;   String bucket;


    @Override
    public ResponseResult uploading(MultipartFile img) {
        //TODO 判断文件类型或者文件大小

        //获取原始文件名
        String filename = img.getOriginalFilename();
        //对原始文件名进行判断，是否为图片
        if(!filename.endsWith(".png")&&!filename.endsWith(".jpg")){
            throw new SystemException(AppHttpCodeEnum.CONTENT_TYPE_ERROR);
        }

        //如果判断通过，上传文件到OSS
        String filePath = PathUtils.generateFilePath(filename);
        String oss= qiNiuYunOSS(img,filePath);//上传文件名格式为 xxx/xx/xx/文件名.jpg-png
        return ResponseResult.okResult(oss);
    }

    /**
     * 上传文件，返回上传图片文件的外链
     * @param imgFIle
     * @return 文件外链
     */
    private String qiNiuYunOSS(MultipartFile imgFIle,String filePath){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huadongZheJiang2());//autoRegin
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名,这里获取文件名
        String key = filePath;

        try {
            //创建网络上传的文件流
            InputStream inputStream= imgFIle.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return "http://rr4li3mz7.bkt.clouddn.com/"+key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return key;

    }


    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
}
