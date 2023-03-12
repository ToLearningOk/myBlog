package com.djt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.djt.constants.SystemConstants;
import com.djt.domain.ResponseResult;
import com.djt.domain.dto.ArticleDto;
import com.djt.domain.entity.Article;
import com.djt.domain.entity.ArticleTag;
import com.djt.domain.entity.Category;
import com.djt.domain.vo.ArticleDetailVo;
import com.djt.domain.vo.ArticleListVo;
import com.djt.domain.vo.HotArticleVo;
import com.djt.domain.vo.PageVo;
import com.djt.mapper.ArticleMapper;
import com.djt.service.ArticleService;
import com.djt.service.ArticleTagService;
import com.djt.utils.BeanCopyUtils;
import com.djt.utils.RedisCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 功能实现。
 *  继承MybatisPlus，实现上层接口，获得部分sql
 * */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Resource
    RedisCache redisCache;
    @Resource
    private CategoryServiceImpl categoryService;
    @Resource
    private ArticleTagService articleTagService;
    /**
     * 获取热门文章列表，响应体内含热门文章列表
     * */
    @Override
    public ResponseResult getHotArticleList() {
        //查询热门文章，封装为ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        //必须是正式文章不是草稿
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);//将表中的status值和0匹配
        //按照浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多查询十条
        Page<Article> page = new Page<>(1,10);
        //将分页和Wrapper 封装
        page(page,queryWrapper);
        //获得当前页数据
        List<Article> articles = page.getRecords();
        //将viewCount更换成redis中的值
        articles.forEach(article ->
                article.setViewCount(Long.valueOf(
                        redisCache.getCacheMapValue(SystemConstants.REDIS_VIEW_KEY,
                                article.getId().toString()).toString()
                )));

        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(vs);
    }


    /**
     * 查询所有文字，首页和分类页面需要查询的
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId){
        QueryWrapper<Object> queryWrapper = new QueryWrapper<>();
//       查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //查询条件
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&& categoryId > 0, Article::getCategoryId, categoryId);
        //状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
//        分页查询 使用分页时需要配置拦截器 com.djt.config.MybatisPlusConfig
        Page<Article> page =new Page<>(pageNum,pageSize);//page.setSize() setCurrent() 每页条数、查询第几页
        page(page,lambdaQueryWrapper);
        List<Article> articles = page.getRecords();

        //        查询categoryName
        // 给article中空的 categoryName 赋值
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .map(article -> article.setViewCount(Long.valueOf(
                        redisCache.getCacheMapValue(SystemConstants.REDIS_VIEW_KEY,
                                article.getId().toString()).toString()
                )))
                .collect(Collectors.toList());

//        封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        PageVo pageVo=new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 根据id查询文章详细内容
     * @param id
     * @return
     */
    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询相应文章内容
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDIS_VIEW_KEY, id.toString());
        article.setViewCount(Long.valueOf(viewCount));
        //VO转换
        ArticleDetailVo detailVO = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据 *分类id 查询 *分类名
        Category category = categoryService.getById(detailVO.getCategoryID());
        //不为空时封装响应
        if(category!=null) detailVO.setCategoryName(category.getName());
        return ResponseResult.okResult(detailVO);
    }

    /***
     * 根据文章id增加文章的访问量，注意这里更新的数据存入redis中
     * @param id
     * @return 成功信息
     */
    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中，对应iD的浏览量ViewCount
        redisCache.IncrementCacheMapValue(SystemConstants.REDIS_VIEW_KEY,id.toString(),1);
        return ResponseResult.okResult();

    }

    /**
     * 新增博客
     * @return 200响应码
     */
    @Override
    @Transactional
    public ResponseResult addArticle(ArticleDto articleDto) {
        //将articleDto映射至article并存入表
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);


        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客 和 标签 的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

}
