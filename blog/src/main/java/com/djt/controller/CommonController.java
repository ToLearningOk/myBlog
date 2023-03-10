package com.djt.controller;

import com.djt.constants.SystemConstants;
import com.djt.domain.ResponseResult;
import com.djt.domain.dto.AddCommentDto;
import com.djt.domain.entity.Comment;
import com.djt.service.CommentService;
import com.djt.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
@Api(tags = "评论",description = "评论相关接口")
public class CommonController {
    @Resource
    CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult getCommentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.getCommentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }
    @PostMapping()
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto){
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        return commentService.addComment(comment);
    }
    @GetMapping("/link/CommentList")
    public ResponseResult getLinkCommentList(Integer pageNum,Integer pageSize){
        return commentService.getCommentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }
}
