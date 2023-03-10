package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Resource
    UploadService uploadService;

    @PostMapping()
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile img){
//        try {
//            return uploadService.uploadImg(img);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("文件上传上传失败");
//        }
        return uploadService.uploadImg(img);
    }
}
