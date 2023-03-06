package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.domain.entity.Comment;
import com.djt.service.CommentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
public class CommonController {
    @Resource
    CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult getCommentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.getLinkCommentList(articleId,pageNum,pageSize);
    }
    @PostMapping()
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

}
