package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.enums.AppHttpCodeEnum;
import com.djt.exception.SystemException;
import com.djt.service.TagService;
import io.jsonwebtoken.lang.Strings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Resource
    TagService tagService;
    @GetMapping("/list")
    public ResponseResult list(){
        System.out.println("返回数据");
        return ResponseResult.okResult(tagService.list());
    }

}
