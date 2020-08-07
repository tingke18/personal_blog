package com.tingke.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tingke.admin.entity.ArticleVo.ArticleVo;
import com.tingke.admin.entity.FrArticle;
import com.tingke.admin.exection.CastDiyException;
import com.tingke.admin.mapper.FrArticleMapper;
import com.tingke.admin.model.CommonCode;
import com.tingke.admin.model.QueryResponseResultList;
import com.tingke.admin.model.R;
import com.tingke.admin.service.FrArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhx
 * @since 2020-05-21
 */
@Service
public class FrArticleServiceImpl extends ServiceImpl<FrArticleMapper, FrArticle> implements FrArticleService {



    //分页查询文章
    @Override
    public R selectAllArticle(Long page, Long limit, ArticleVo articleVo) {
        //校空
        if (StringUtils.isEmpty(page.toString()) || StringUtils.isEmpty(limit.toString())){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        //分页
        Page<FrArticle> pageQ = new Page<>(page, limit);

        if (pageQ == null){
            CastDiyException.cast(CommonCode.FAIL);
        }

        //条件
        QueryWrapper<FrArticle> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");

        if (articleVo != null){
            if (StringUtils.isNoneBlank(articleVo.getTitle())){
                wrapper.like("title",articleVo.getTitle());
            }
            if (StringUtils.isNoneBlank(articleVo.getStart())){
                wrapper.gt("create_time",articleVo.getStart());
            }
            if (StringUtils.isNoneBlank(articleVo.getEnd())){
                wrapper.gt("create_time",articleVo.getStart());
            }
        }

        Page<FrArticle> iPage = this.page(pageQ, wrapper);

        long total = iPage.getTotal();//条数
        List<FrArticle> data = iPage.getRecords();

        QueryResponseResultList queryResponseResultList = new QueryResponseResultList();
        queryResponseResultList.setList(data);
        queryResponseResultList.setTotal((int) total);

        return new R(CommonCode.SUCCESS,queryResponseResultList);

    }

    //根据id查询文章
    @Override
    public R selectArticleById(String id) {

        //校空
        if (StringUtils.isEmpty(id)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        FrArticle frArticle = this.getById(id);

        frArticle.setVisitCount(frArticle.getVisitCount()+1);

        baseMapper.updateById(frArticle);

        return new R(CommonCode.SUCCESS,frArticle);

    }

    //新增文章
    @Override
    @Transactional
    public R addArticle(FrArticle frArticle) {
        //校空
        if (frArticle == null){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        boolean success = this.save(frArticle);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }

    //逻辑删除文章
    @Override
    @Transactional
    public R deleteArticle(String id) {
        //校空
        if (StringUtils.isEmpty(id)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        boolean success = this.removeById(id);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }


        return  new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }

    //修改编辑文章
    @Override
    @Transactional
    public R editArticle(FrArticle frArticle) {
        //校空
        if (frArticle == null){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        int count = baseMapper.updateById(frArticle);

        if (count < 1){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return  new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }

    //修改文章状态
    @Override
    @Transactional
    public R editArticlePublish(String id, Integer isPublish) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(isPublish.toString())){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        FrArticle frArticle = new FrArticle();
        frArticle.setId(id);
        frArticle.setIsPublish(isPublish);

        boolean success = this.updateById(frArticle);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }

        if (isPublish == 1){
            return  new R(CommonCode.SUCCESS, 1);
        }else {
            return  new R(CommonCode.SUCCESS, 0);
        }
    }

    @Override
    public R selectFrontArticle(Long page, Long limit) {
        //校空
        if (StringUtils.isEmpty(page.toString()) || StringUtils.isEmpty(limit.toString())){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        //分页
        Page<FrArticle> pageQ = new Page<>(page, limit);

        if (pageQ == null){
            CastDiyException.cast(CommonCode.FAIL);
        }

        //条件
        QueryWrapper<FrArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("is_publish",1);
        wrapper.orderByDesc("create_time");

        Page<FrArticle> iPage = this.page(pageQ, wrapper);

        long total = iPage.getTotal();//条数
        List<FrArticle> data = iPage.getRecords();


        QueryResponseResultList queryResponseResultList = new QueryResponseResultList();
        queryResponseResultList.setList(data);
        queryResponseResultList.setTotal((int) total);

        return new R(CommonCode.SUCCESS,queryResponseResultList);

    }

    @Override
    public R selectSearchArticle(Long page, Long limit, ArticleVo articleVo) {
        //校空
        if (StringUtils.isEmpty(page.toString()) || StringUtils.isEmpty(limit.toString())){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        //分页
        Page<FrArticle> pageQ = new Page<>(page, limit);

        if (pageQ == null){
            CastDiyException.cast(CommonCode.FAIL);
        }

        //条件
        QueryWrapper<FrArticle> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        wrapper.eq("is_publish",1);

        if (articleVo != null){
            if (StringUtils.isNoneBlank(articleVo.getTitle())){
                wrapper.like("title",articleVo.getTitle());
            }
        }

        Page<FrArticle> iPage = this.page(pageQ, wrapper);

        long total = iPage.getTotal();//条数
        List<FrArticle> data = iPage.getRecords();

        QueryResponseResultList queryResponseResultList = new QueryResponseResultList();
        queryResponseResultList.setList(data);
        queryResponseResultList.setTotal((int) total);

        return new R(CommonCode.SUCCESS,queryResponseResultList);

    }
}
