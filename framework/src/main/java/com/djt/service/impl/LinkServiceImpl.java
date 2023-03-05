package com.djt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.djt.constants.SystemConstants;
import com.djt.domain.ResponseResult;
import com.djt.domain.entity.Link;
import com.djt.domain.vo.LinkVo;
import com.djt.mapper.LinkMapper;
import com.djt.service.LinkService;
import com.djt.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-03-05 15:27:13
 */
@Service("sgLinkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的友链
        LambdaQueryWrapper<Link> QueryWrapper = new LambdaQueryWrapper<>();
        QueryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(QueryWrapper);
//        VO
        List<LinkVo> listVo = BeanCopyUtils.copyBeanList(links, LinkVo.class);
//        封装返回
        return ResponseResult.okResult(listVo);
    }
}
