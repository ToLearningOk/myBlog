package com.djt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.djt.constants.SystemConstants;
import com.djt.domain.ResponseResult;
import com.djt.domain.entity.Comment;
import com.djt.domain.vo.CommentVo;
import com.djt.domain.vo.PageVo;
import com.djt.enums.AppHttpCodeEnum;
import com.djt.exception.SystemException;
import com.djt.mapper.CommentMapper;
import com.djt.service.CommentService;
import com.djt.service.UserService;
import com.djt.utils.BeanCopyUtils;
import com.djt.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-03-06 15:46:09
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Resource
    private UserService userService;
    @Override
    public ResponseResult getCommentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //1.完成对应文章的根评论的查询
        //对文章ID articleID进行判断
        LambdaQueryWrapper<Comment> QueryWrapper = new LambdaQueryWrapper<>();
        //当type是文章时 添加文章评论的对比
        QueryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId, articleId);

        QueryWrapper.eq(Comment::getType,commentType);
        //根评论 rootID为-1
        QueryWrapper.eq(Comment::getRootId , SystemConstants.COMMENT_STATUS_ROOT);
        QueryWrapper.orderByDesc(Comment::getCreateTime);
        //分页查询
        Page<Comment> page = new Page<>(pageNum,pageSize);
        page(page,QueryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());//调用方法，封装完所有根评论字段

        //2.完成对所有根评论的查询。查询对应的子评论集合，并赋值给对应的属性
        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论,去调用一个方法用根评论id 获取 所有子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            //赋值
            commentVo.setChildren(children);}
        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        comment.setCreateBy(SecurityUtils.getUserId());
//        添加条件，比如评论内容不能为空
        if(!StringUtils.hasText(comment.getContent()))
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        save(comment);
        return ResponseResult.okResult();
    }

    /**处理字段映射，给username和toCommentUserName字段赋值*/
    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历Vo
        for (CommentVo commentVo : commentVos) {
            // 通过creatBy查询用户的昵称，给username字段赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            // 通过toCommentId查询用户名，给toCommentUserName字段赋值
            //如果toCommentUserId才不为-1才进行查询
            if(commentVo.getToCommentUserId()!=-1){
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }


//        commentVos.stream()
//                // 通过creatBy查询用户的昵称，给username字段赋值
//                .map(commentVo -> commentVo.setToCommentUserName(userService.getById(commentVo.getCreateBy()).toString()))
//                // 通过toCommentId查询用户名，给toCommentUserName字段赋值
//                .map(commentVo -> commentVo.setToCommentUserName(userService.getById(commentVo.getToCommentId()).toString()))
//                .collect(Collectors.toList());//        List<CommentVo> collect = commen
        return commentVos;
    }
    /**
     * 根据根评论id查询所对应的子评论并返回子评论Vo集合
     * @param id 根评论id
     * @return List<CommentVo>
     */
    private List<CommentVo> getChildren(Long id){
        //根据传入id 查询子评论
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper();
        wrapper.eq(Comment::getRootId, id);
        wrapper.orderByAsc(Comment::getCreateTime);//创建时间降序

        List<Comment> list = list(wrapper);
        List<CommentVo> commentVos = toCommentVoList(list);
        //将子评论封装
        return commentVos;
    }
}
