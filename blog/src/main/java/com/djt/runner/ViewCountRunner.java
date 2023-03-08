package com.djt.runner;

import com.djt.constants.SystemConstants;
import com.djt.domain.entity.Article;
import com.djt.mapper.ArticleMapper;
import com.djt.utils.RedisCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {
    @Resource
    ArticleMapper articleMapper;
    @Resource
    RedisCache redisCache;
    @Override
    public void run(String... args) {
        //需求，将数据库中的阅读播放量放进redis中
        //1.查询博客形象 id viewCount

        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                //数据流中的值为article单个对象
                .collect(Collectors.toMap(article -> article.getId().toString(),
                        //Long 这种数据类型无法自动递增
                        article -> article.getViewCount().intValue()));

        //4.存入redis中
        redisCache.setCacheMap(SystemConstants.REDIS_VIEW_KEY, viewCountMap);

    }
}
