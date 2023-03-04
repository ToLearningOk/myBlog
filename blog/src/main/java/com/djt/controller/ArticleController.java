package com.djt.controller;


import com.djt.domain.ResponseResult;
import com.djt.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
//@RequestMapping("/api/article")
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;
//    @GetMapping("/articles")
//    public List<Article> test(){
//        System.out.println(articleService.list());
//        return articleService.list();
//    }

    @GetMapping("/hotArticleList")
//    @GetMapping("/hot-list")
    public ResponseResult hotArticleList(){
        ResponseResult result = articleService.getHotArticleList();
        return result;
    }
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    public ResponseResult (){

    }
}
