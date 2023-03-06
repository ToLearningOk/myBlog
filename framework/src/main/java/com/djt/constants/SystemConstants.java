package com.djt.constants;

public class SystemConstants
{
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常发布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;
    /**
     *  分类是正常启用状态
     */
    public static final String CATEGORY_STATUS_NORMAL = "0";
    /**
     *  分类是异常封禁状态
     */
    public static final String CATEGORY_STATUS_DRAFT = "1";
    //审核通过
    public static final String LINK_STATUS_NORMAL="0";
    //审核未通过
    public static final String LINK_STATUS_FAIL="1";
    //未审核
    public static final String LINK_STATUS_NOTLOOK="2";
    //根评论
    public static final String COMMENT_STATUS_ROOT="-1";
    //
    public static final String LINK_STATUS_CHILDREN="2";


    public static final String ARTICLE_COMMENT = "0";
    public static final String LINK_COMMENT = "1";
}