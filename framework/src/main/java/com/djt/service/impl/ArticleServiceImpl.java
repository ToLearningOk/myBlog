package com.djt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.djt.constants.SystemConstants;
import com.djt.domain.ResponseResult;
import com.djt.domain.entity.Article;
import com.djt.domain.entity.Category;
import com.djt.domain.vo.ArticleListVo;
import com.djt.domain.vo.HotArticleVo;
import com.djt.domain.vo.PageVo;
import com.djt.mapper.ArticleMapper;
import com.djt.service.ArticleService;
import com.djt.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 功能实现。
 *  继承MybatisPlus，实现上层接口，获得部分sql
 * */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Resource
    private CategoryServiceImpl categoryService;
    /**
     * 获取热门文章列表，响应体内含热门文章列表
     * */
    @Override
    public ResponseResult getHotArticleList() {
        //查询热门文章，封装为ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
//        必须是正式文章不是草稿
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);//将表中的status值和0匹配
//        按照浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);
//        最多查询十条
        Page<Article> page = new Page<Article>(1,10);
        page(page,queryWrapper);//将分页和Wrapper 封装

        List<Article> articles = page.getRecords();//执行查询，获得数据

        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(vs);
    }
    /**
     * 首页和分类页面需要查询的文章列表
     * 首页：查询所有文章，分类页面：查询对应分类下的文章
     * 需求：1.只能查询发布的文章 2.置顶文章显示最前
     * */
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId){
//        查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();//封装查询条件的封装器
        //查询条件  如何有 categoryId 就要 查询时和传入的相同  ---类似动态sql
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&& categoryId > 0, Article::getCategoryId, categoryId);
        //状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
//        分页查询 使用分页时需要配置拦截器 com.djt.config.MbatisPlusConfig
        Page<Article> page =new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        List<Article> articles = page.getRecords();
        //        查询categoryName

        //articlesId 去查询ArticleName并设置
        //        方法1：for循环
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        //      方法2：流，给article中空的 categoryName 赋值
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());


//        封装查询结果  注意，此时字段categoryName 尚未映射，值为空
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo=new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
