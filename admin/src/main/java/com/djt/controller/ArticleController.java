package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.domain.dto.ArticleDto;
import com.djt.service.ArticleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Resource
    ArticleService articleService;
    @PostMapping
    public ResponseResult addArticle(@RequestBody ArticleDto articleDto){
            return articleService.addArticle(articleDto);
    }
}
