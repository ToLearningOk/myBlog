package com.djt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.djt.constants.SystemConstants;
import com.djt.domain.ResponseResult;
import com.djt.domain.entity.Article;
import com.djt.domain.entity.Category;
import com.djt.domain.vo.CategoryVo;
import com.djt.mapper.CategoryMapper;
import com.djt.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import com.djt.service.CategoryService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-03-04 14:26:30
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Resource
    ArticleServiceImpl articleService;

    @Override
    public ResponseResult getCategoryList() {
        //1.查询文章表，找到状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);//包装器：等值匹配
        List<Article> articles = articleService.list(articleWrapper);
        // 2.获取文章的分类id，并去重
        Set<Long> categoryIds = articles.stream()
                .map(article -> article.getCategoryId())  //获取每个元素的CategoryId，
                .collect(Collectors.toSet());//存入set实现去重
        // 3.查询分类表，找到id对应文章种类
        List<Category> categories = listByIds(categoryIds);

        List<Category> categoryList = categories.stream()
                .filter(category -> SystemConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        // 4.封装数据vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }
}
