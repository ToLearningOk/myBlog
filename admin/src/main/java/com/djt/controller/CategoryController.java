package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.domain.vo.CategoryVo;
import com.djt.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("content/category")
public class CategoryController {
    @Resource
    CategoryService categoryService;
    @GetMapping("/listAllCategory")
    public ResponseResult getAllCategoryList(){
        //查询所有的分类
        return categoryService.listAllCategory();
    }



}
