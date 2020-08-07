package com.tingke.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tingke.admin.entity.CommentVo.CommentVo;
import com.tingke.admin.entity.FrComment;
import com.tingke.admin.entity.FrUser;
import com.tingke.admin.exection.CastDiyException;
import com.tingke.admin.mapper.FrCommentMapper;
import com.tingke.admin.model.CommonCode;
import com.tingke.admin.model.QueryResponseResultList;
import com.tingke.admin.model.R;
import com.tingke.admin.service.FrCommentService;
import com.tingke.admin.service.FrUserService;
import com.tingke.admin.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhx
 * @since 2020-06-01
 */
@Service
public class FrCommentServiceImpl extends ServiceImpl<FrCommentMapper, FrComment> implements FrCommentService {

    @Autowired
    private FrUserService frUserService;

    //评论显示
    @Override
    public R selectComment() {

        QueryWrapper wrapper = new QueryWrapper();

        wrapper.orderByDesc("create_time");

        //查询所有评论
        List<FrComment> frComments = baseMapper.selectList(wrapper);

        if (frComments == null){
            CastDiyException.cast(CommonCode.FAIL);
        }


        List<CommentVo> commentVos = new ArrayList<>();

        //遍历封装父评论
        frComments.stream().filter(frComment -> StringUtils.isEmpty(frComment.getTargetId())).forEach(frComment -> {

            CommentVo commentVo = new CommentVo();
            commentVo.setFrComment(frComment);
            commentVos.add(commentVo);
        });

        //遍历封装子评论
        commentVos.forEach(commentVo -> {

            List<FrComment> frCommentBean = new ArrayList<>();

            frComments.forEach(frComment -> {

                if (commentVo.getFrComment().getId().equals(frComment.getTargetId())){

                    frCommentBean.add(frComment);

                }

            });

            commentVo.setChildren(frCommentBean);
        });

        int count = commentVos.size();

        QueryResponseResultList responseResultList = new QueryResponseResultList();
        responseResultList.setList(commentVos);
        responseResultList.setTotal(count);

        return new R(CommonCode.SUCCESS,responseResultList);
    }

    @Override
    @Transactional
    public R addComment(HttpServletRequest httpServletRequest,String content) {

        String id = JwtUtils.getMemberIdByJwtToken(httpServletRequest);

        //判断id是否为空
        if (StringUtils.isEmpty(id)){
            CastDiyException.cast(CommonCode.UNAUTHENTICATED);
        }

        //查询用户
        FrUser frUser = frUserService.getById(id);

        if (frUser == null){
            CastDiyException.cast(CommonCode.UNAUTHENTICATED);
        }

        FrComment userComment = new FrComment();

        userComment.setName(frUser.getName());
        userComment.setContent(content);

        boolean success = this.save(userComment);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS,userComment);
    }

    //查询管理员未读评论
    @Override
    public R selectNotCheckCommentCount() {

        QueryWrapper<FrComment> wrapper = new QueryWrapper<>();
        wrapper.eq("is_check",0);

        Integer count = baseMapper.selectCount(wrapper);

        return new R(CommonCode.SUCCESS,count);
    }

    //查询管理员未读评论
    @Override
    public R selectNotCheckComment() {

        QueryWrapper<FrComment> wrapper = new QueryWrapper<>();
        wrapper.eq("is_check",0);

        List<FrComment> frComments = baseMapper.selectList(wrapper);


        QueryResponseResultList queryResponseResultList = new QueryResponseResultList();
        queryResponseResultList.setTotal(frComments.size());
        queryResponseResultList.setList(frComments);

        return new R(CommonCode.SUCCESS,queryResponseResultList);
    }

    //管理员新增评论显示
    @Override
    public R AdminAddComment(String id,String content) {

        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(content)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        //设置管理员评论
        FrComment frComment = new FrComment();

        frComment.setIsCheck(1);
        frComment.setContent(content);
        frComment.setName("浅乔未果");
        frComment.setTargetId(id);

        this.save(frComment);

        return new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }

    //设置评论已读
    @Override
    public R editCommentRead(String id) {

        if (StringUtils.isEmpty(id)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        FrComment frComment = this.getById(id);

        if (frComment == null){
            CastDiyException.cast(CommonCode.FAIL);
        }

        frComment.setIsCheck(1);

        this.updateById(frComment);

        return new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }

    //查询管理员已读评论
    @Override
    public R selectCheckComment() {

        QueryWrapper<FrComment> wrapper = new QueryWrapper<>();
        wrapper.eq("is_check",1);

        List<FrComment> frComments = baseMapper.selectList(wrapper);


        QueryResponseResultList queryResponseResultList = new QueryResponseResultList();
        queryResponseResultList.setTotal(frComments.size());
        queryResponseResultList.setList(frComments);

        return new R(CommonCode.SUCCESS,queryResponseResultList);
    }

    //设置所有评论已读
    @Override
    public R editAllCommentRead() {

        QueryWrapper<FrComment> wrapper = new QueryWrapper<>();
        wrapper.eq("is_check",0);
        List<FrComment> frComments = baseMapper.selectList(wrapper);

        frComments.forEach(frComment -> {
            frComment.setIsCheck(1);
        });

        boolean success = this.updateBatchById(frComments);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }

    //删除评论
    @Override
    public R deleteComment(String id) {

        boolean success = this.removeById(id);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }


}
