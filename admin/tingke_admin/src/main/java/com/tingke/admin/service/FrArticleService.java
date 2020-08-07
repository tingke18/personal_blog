package com.tingke.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tingke.admin.entity.ArticleVo.ArticleVo;
import com.tingke.admin.entity.FrArticle;
import com.tingke.admin.model.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhx
 * @since 2020-05-21
 */
public interface FrArticleService extends IService<FrArticle> {

    //分页查询文章
    R selectAllArticle(Long page, Long limit, ArticleVo articleVo);

    //根据id查询文章
    R selectArticleById(String id);

    //新增文章
    R addArticle(FrArticle frArticle);

    //逻辑删除文章
    R deleteArticle(String id);

    //修改编辑文章
    R editArticle(FrArticle frArticle);

    //修改文章状态
    R editArticlePublish(String id, Integer isPublish);

    //前端分页查询文章
    R selectFrontArticle(Long page, Long limit);

    //前端搜索查询文章
    R selectSearchArticle(Long page, Long limit, ArticleVo articleVo);
}
