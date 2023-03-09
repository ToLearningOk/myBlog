package com.djt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.djt.domain.entity.Article;
import com.djt.mapper.ArticleMapper;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;

@SpringBootTest
//@ConfigurationProperties(prefix = "oss")   //使用时需解开注解
@EnableConfigurationProperties
public class OSSTest {
//    2个解析秘钥+文档空间

    String accessKey;
    String secretKey;
    String bucket;

    @Test
    public void qiNiuYunTest(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huadongZheJiang2());//autoRegin
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
//        String accessKey = "-rDi3tqnE04RhMHY0SwLUJytMmK3pytx4iWD4DEL";
//        String secretKey = "_lnRIOR-YPkVioXEBIFebWJGrt-gMflEPzNMPEDZ";
//        String bucket = "djt-blog";

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "2022/一点点小世界观.docx";

        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            FileInputStream inputStream= new FileInputStream("C:\\Users\\56854\\Desktop\\一点点小世界观.docx");


            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
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

    }
    @Resource
    ArticleMapper mapper;
    @Test
    public void testLambdaWrapper(){
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.select(Article::getStatus);
        queryWrapper.select(Article::getId,Article::getTitle);
        mapper.selectList(queryWrapper);
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
