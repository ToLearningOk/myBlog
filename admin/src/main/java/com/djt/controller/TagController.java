package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.domain.dto.TagListDto;
import com.djt.domain.vo.PageVo;
import com.djt.service.TagService;
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
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto ){
        System.out.println("返回数据");
        return tagService.pagTagService(pageNum,pageSize,tagListDto);
    }

}
