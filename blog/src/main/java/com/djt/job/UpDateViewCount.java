package com.djt.job;

import com.djt.constants.SystemConstants;
import com.djt.domain.entity.Article;
import com.djt.service.ArticleService;
import com.djt.utils.RedisCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UpDateViewCount {
    @Resource
    private RedisCache redisCache;
    @Resource
    private ArticleService articleService;
    @Scheduled(cron = "0 0/10 * * * ?")
    private void upDateViewCount(){
        //每隔10分钟将redis中的viewCount数据记录到mybatis中
        //1.读取Redis中的ViewCount数据
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.REDIS_VIEW_KEY);

        List<Article> articles = viewCountMap.entrySet().stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), Long.valueOf(entry.getValue())))
                .collect(Collectors.toList());

        //2.存入myBatis中
        articleService.updateBatchById(articles);

    }

}
