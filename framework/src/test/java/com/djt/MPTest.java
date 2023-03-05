package com.djt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.djt.domain.entity.Article;
import com.djt.mapper.ArticleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MPTest {
    @Resource
    ArticleMapper mapper;
    @Test

    public void testLambdaWrapper(){
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.select(Article::getStatus)
//        queryWrapper.select("id","title",)
//        mapper.selectList(queryWrapper);
        Article article = new Article();
        article.getId();
    }

}
