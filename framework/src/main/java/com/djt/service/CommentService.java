package com.djt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.djt.domain.ResponseResult;
import com.djt.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-03-06 15:46:08
 */
public interface CommentService extends IService<Comment> {

    ResponseResult getLinkCommentList(Long articleId, Integer pageNum, Integer pageSize);


    ResponseResult addComment(Comment comment);
}
