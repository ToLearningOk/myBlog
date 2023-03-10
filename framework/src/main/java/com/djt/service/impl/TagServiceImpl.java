package com.djt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.djt.constants.SystemConstants;
import com.djt.domain.ResponseResult;
import com.djt.domain.dto.TagListDto;
import com.djt.domain.entity.Tag;
import com.djt.domain.vo.PageVo;
import com.djt.domain.vo.TagVo;
import com.djt.mapper.TagMapper;
import com.djt.service.TagService;
import com.djt.utils.BeanCopyUtils;
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
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());

        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        // 封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(TagListDto addTag) {
        //TODO 还未自动填充
        Tag tag = BeanCopyUtils.copyBean(addTag, Tag.class);
        save(tag);
        return ResponseResult.okResult();
    }

    /**
     * 更更新Tag状态，将tag的状态标识del_flag设置为1表示为已删除
     * @param id
     * @return 200
     */
    @Override
    public ResponseResult deleteTag(String id) {
        LambdaUpdateWrapper<Tag> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Tag::getId,Long.parseLong(id));
        updateWrapper.set(Tag::getDelFlag, SystemConstants.TAG_DEL_FLAG_DEL);
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    /**
     * 根据id获取一个标签信息
     * @param id
     * @return
     */
    @Override
    public ResponseResult getTag(String id) {
        Tag tag = getBaseMapper().selectById(Long.parseLong(id));
        return ResponseResult.okResult(tag);
    }

    @Override
    public ResponseResult UpdateTag(TagVo tagVo) {
        Tag tag = BeanCopyUtils.copyBean(tagVo, Tag.class);
        getBaseMapper().updateById(tag);
        return ResponseResult.okResult();
    }
}
