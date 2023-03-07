package com.djt.service;

import com.djt.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    ResponseResult uploading(MultipartFile img);
}
