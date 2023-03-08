package com.djt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.djt.domain.entity.Tag;
import com.djt.mapper.TagMapper;
import com.djt.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-08 19:22:00
 */
@Service("sgTagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
