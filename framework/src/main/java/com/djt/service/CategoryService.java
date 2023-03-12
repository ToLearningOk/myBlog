package com.djt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.djt.domain.ResponseResult;
import com.djt.domain.entity.Category;
import com.djt.domain.vo.PageVo;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-03-04 14:26:29
 */
public interface CategoryService extends IService<Category> {


    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    PageVo selectCategoryPage(Category category, Integer pageNum, Integer pageSize);
}
