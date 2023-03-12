package com.djt.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.djt.domain.ResponseResult;
import com.djt.domain.entity.Category;
import com.djt.domain.vo.CategoryVo;
import com.djt.domain.vo.ExcelCategoryVo;
import com.djt.domain.vo.PageVo;
import com.djt.enums.AppHttpCodeEnum;
import com.djt.service.CategoryService;
import com.djt.utils.BeanCopyUtils;
import com.djt.utils.WebUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.util.Lists.list;

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
    // 模糊分页查询分类列表
    @GetMapping("/list")
    public ResponseResult CategoryList(Category category, Integer pageNum, Integer pageSize){
        PageVo pageVo = categoryService.selectCategoryPage(category,pageNum,pageSize);

    return ResponseResult.okResult(pageVo);
    }


    @GetMapping("/export")
    @PreAuthorize("@ps.hasPermission('content:category:export')") //内容调用方法去判断当前用户是否具有权限
    public void  export(HttpServletResponse response){
        //成功的话直接导出一个Excel文件
        //设置需要下载文件的请求头
        try {
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //设置需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<CategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, CategoryVo.class);
            //把数据写入Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
            //如果出现异常也要响应JSON
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));

        }
    }


}
