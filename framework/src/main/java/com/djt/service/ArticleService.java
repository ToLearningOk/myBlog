package com.djt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.djt.domain.ResponseResult;
import com.djt.domain.entity.Article;

public interface ArticleService extends IService<Article> {

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    /**
     * 查询热门文章，封装为ResponseResult返回
     */
    ResponseResult getHotArticleList();


    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);
}
