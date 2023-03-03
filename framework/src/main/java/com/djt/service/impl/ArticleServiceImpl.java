package com.djt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.djt.constants.SystemConstants;
import com.djt.domain.ResponseResult;
import com.djt.domain.entity.Article;
import com.djt.domain.vo.HotArticleVo;
import com.djt.mapper.ArticleMapper;
import com.djt.service.ArticleService;
import com.djt.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能实现。
 *  继承MybatisPlus，实现上层接口，获得部分sql
 * */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    public ResponseResult getHotArticleList() {
        //查询热门文章，封装为ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
//        必须是正式文章不是草稿
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);//将表中的status值和0对比
//        按照浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);
//        最多查询十条
        Page<Article> page = new Page<Article>(1,10);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();

        //bean拷贝，处理数据，删去多余的数据
//        List<HotArticleVo> articleVos = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article,vo);//拷贝1->2
//            articleVos.add(vo);
//        }
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(vs);
    }
}
