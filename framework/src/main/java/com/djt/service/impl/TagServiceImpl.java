package com.djt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.djt.domain.ResponseResult;
import com.djt.domain.dto.TagListDto;
import com.djt.domain.entity.Tag;
import com.djt.domain.vo.PageVo;
import com.djt.mapper.TagMapper;
import com.djt.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-08 19:22:00
 */
@Service("TagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {


    @Override
    public ResponseResult<PageVo> pagTagService(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());

        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        // 封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
