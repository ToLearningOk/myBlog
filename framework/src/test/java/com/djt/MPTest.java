package com.djt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.djt.domain.entity.Article;
import com.djt.domain.entity.User;
import com.djt.mapper.ArticleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Predicate;

@SpringBootTest()
public class MPTest {
    @Resource
    ArticleMapper mapper;
    @Test

    public void testLambdaWrapper(){
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.select(Article::getStatus);
//        queryWrapper.select("id","title",)x
//        mapper.selectList(queryWrapper);
    }


}
