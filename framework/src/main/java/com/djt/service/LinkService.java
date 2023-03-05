package com.djt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.djt.domain.ResponseResult;
import com.djt.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-03-05 15:27:13
 */
public interface LinkService extends IService<Link> {
    /**获取所有友链信息*/
    ResponseResult getAllLink();
}
