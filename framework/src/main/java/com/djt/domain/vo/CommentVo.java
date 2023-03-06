package com.djt.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {
    private Long id;

    //文章id
    private Long articleId;
//    //子评论
    private List<CommentVo> children;

    //评论内容
    private String content;
    //根评论 id
    private Long rootId;

    //被回复评论的用户id
    private Long toCommentUserId;
    //被回复人昵称
    @Accessors(chain = true)//使添加的set方法返回当前对象本身
    private String toCommentUserName;

    //指向要回复的评论的id
    private Long toCommentId;

    private Long createBy;

    private Date createTime;
    //评论人昵称
    @Accessors(chain = true)//使添加的set方法返回当前对象本身
    private String username;
}
