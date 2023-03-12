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
    /**
     * 审核通过
     */
    public static final String LINK_STATUS_NORMAL="0";
    /**
     * 审核未通过
     */
    public static final String LINK_STATUS_FAIL="1";
    /**
     * 未审核
     */
    public static final String LINK_STATUS_NOTLOOK="2";
    /**
     * 根评论
     */
    public static final String COMMENT_STATUS_ROOT="-1";
    //
    public static final String LINK_STATUS_CHILDREN="2";
    /**
     * 存入redis中浏览量ViewCount的键
     */

    public static final String REDIS_VIEW_KEY="viewCount";
    public static final String ARTICLE_COMMENT = "0";
    public static final String LINK_COMMENT = "1";
    public static final String MENU_TYPE_MENU = "C";
    public static final String MENU_TYPE_BUTTON = "F";
    public static final String MENU_TYPE_CATALOG = "M";
    public static final String MENU_STATUS_NORMAL = "0";
    public static final String MENU_STATUS_DISABLE = "1";
    //标签删除状态 0代表未删除，1代表删除
    public static final String TAG_DEL_FLAG_DEL = "1";
    /**
     * 管理员
     */
    public static final String ADMIN = "1";
    /**
     * 普通用户
     */
    public static final String NORMAL_USER = "1";

}