package com.tingke.admin.service;

import com.tingke.admin.entity.FrComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tingke.admin.model.R;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhx
 * @since 2020-06-01
 */
public interface FrCommentService extends IService<FrComment> {

    //评论显示
    R selectComment();

    //新增评论显示
    R addComment(HttpServletRequest httpServletRequest,String content);

    //查询管理员未读评论
    R selectNotCheckCommentCount();

    //查询管理员未读评论
    R selectNotCheckComment();

    //管理员新增评论显示
    R AdminAddComment(String id,String content);

    //设置评论已读
    R editCommentRead(String id);

    //查询管理员已读评论
    R selectCheckComment();

    //设置所有评论已读
    R editAllCommentRead();

    //删除评论
    R deleteComment(String id);
}
