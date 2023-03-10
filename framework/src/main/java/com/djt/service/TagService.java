package com.djt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.djt.domain.ResponseResult;
import com.djt.domain.dto.TagListDto;
import com.djt.domain.entity.Tag;
import com.djt.domain.vo.PageVo;
import com.djt.domain.vo.TagVo;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-03-08 19:21:59
 */
public interface TagService extends IService<Tag> {


    ResponseResult<PageVo> pagTagService(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagListDto addTag);

    ResponseResult deleteTag(String id);

    ResponseResult getTag(String id);

    ResponseResult UpdateTag(TagVo tagVo);

    ResponseResult getAllTag();
}
