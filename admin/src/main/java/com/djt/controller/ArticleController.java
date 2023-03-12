package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.domain.dto.ArticleDto;
import com.djt.domain.entity.Article;
import com.djt.domain.vo.ArticleVo;
import com.djt.domain.vo.PageVo;
import com.djt.service.ArticleService;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 模糊查询数据
     * @param pageNum
     * @param pageSize
     * @param article
     * @return
     */
    @GetMapping("/list")
    public ResponseResult ArticleList(Integer pageNum, Integer pageSize, Article article){
        PageVo pageVo = articleService.selectArticlePage(article,pageNum,pageSize);
        return ResponseResult.okResult(pageVo);
    }
    @GetMapping("{id}")
    public ResponseResult getInfo(@PathVariable(value = "id") Long id){
        //获取文章信息
        ArticleVo article = articleService.getInfo(id);
        return ResponseResult.okResult(article);
    }
    @DeleteMapping("{id}")
    public ResponseResult delete(@PathVariable(value = "id") Long id){
        //修改文章
        articleService.removeById(id);
        return ResponseResult.okResult();
    }





}
