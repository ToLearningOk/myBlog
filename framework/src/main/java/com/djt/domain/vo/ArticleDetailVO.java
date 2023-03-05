package com.djt.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVO {
    private Long id;
    //标题
    private String title;
    //所属分类id
    private Long categoryID;
    //所属分类名
    private String categoryName;
    //缩略图
    private String thumbnail;
    //文章摘要
    private String summary;
    //文章内容
    private String content;
    //访问量
    private Long viewCount;
    //创建时间
    private Date createTime;

    private Date updateTime;
    //删除标志（0 代表未删除，1 代表已删除）
    private Integer delFlag;

}
