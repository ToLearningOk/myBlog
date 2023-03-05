package com.djt.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.djt.domain.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {
    //  $/#{ew.xxx}在xml中获取wrapper对象
    Article findMyArticle(@Param(Constants.WRAPPER) Wrapper<Article> wrapper);
}
