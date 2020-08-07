package com.tingke.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tingke.admin.entity.FrLink;
import com.tingke.admin.entity.FrLinkCategory;
import com.tingke.admin.entity.LinkVo.LinkVo;
import com.tingke.admin.exection.CastDiyException;
import com.tingke.admin.mapper.FrLinkMapper;
import com.tingke.admin.model.CommonCode;
import com.tingke.admin.model.QueryResponseResultList;
import com.tingke.admin.model.R;
import com.tingke.admin.service.FrLinkCategoryService;
import com.tingke.admin.service.FrLinkService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhx
 * @since 2020-05-31
 */
@Service
public class FrLinkServiceImpl extends ServiceImpl<FrLinkMapper, FrLink> implements FrLinkService {

    @Autowired
    private FrLinkCategoryService frLinkCategoryService;

    //快捷链接显示
    @Override
    public R selectLink() {
        //查询所有分类
        List<FrLinkCategory> frLinkCategories = frLinkCategoryService.getBaseMapper().selectList(null);
        if (frLinkCategories == null ){
            CastDiyException.cast(CommonCode.FAIL);
        }

        //查询所有link
        List<FrLink> frLinks = baseMapper.selectList(null);

        if (frLinks == null){
            CastDiyException.cast(CommonCode.FAIL);
        }

        List<LinkVo> linkVos = new ArrayList<>();

        frLinkCategories.forEach(frLinkCategory -> {
            LinkVo linkVo = new LinkVo();

            linkVo.setCategory(frLinkCategory.getCategory());

            ArrayList<FrLink> links = new ArrayList<>();

            frLinks.forEach(frLink -> {

                if (frLinkCategory.getId().equals(frLink.getCategoryId())){
                    links.add(frLink);
                }

            });

            linkVo.setFrLinks(links);

            linkVos.add(linkVo);

        });

        int count = linkVos.size();

        QueryResponseResultList<LinkVo> objectQueryResponseResultList = new QueryResponseResultList<>();
        objectQueryResponseResultList.setTotal(count);
        objectQueryResponseResultList.setList(linkVos);

        return new R(CommonCode.SUCCESS,objectQueryResponseResultList);
    }

    //快捷链接分页显示
    @Override
    public R selectPageLink(Long page, Long limit,String condition) {

        if (StringUtils.isEmpty(page.toString()) || StringUtils.isEmpty(limit.toString())){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        //构建分页
        Page<FrLink> spage = new Page<>(page, limit);
        QueryWrapper<FrLink> wrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(condition)){
            wrapper.like("title",condition);
        }

        Page<FrLink> ipage = this.page(spage,wrapper);
        if (ipage == null){
            CastDiyException.cast(CommonCode.FAIL);
        }



        QueryResponseResultList queryResponseResultList = new QueryResponseResultList();
        queryResponseResultList.setTotal((int) spage.getTotal());
        queryResponseResultList.setList(spage.getRecords());

        return new R(CommonCode.SUCCESS,queryResponseResultList);
    }

    //快捷链接添加
    @Override
    @Transactional
    public R addLink(FrLink frLink) {
        if (frLink == null){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        boolean save = this.save(frLink);

        if (!save){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS,frLink);
    }

    //快捷链接删除
    @Override
    @Transactional
    public R deleteLink(String id) {

        if(StringUtils.isEmpty(id)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        boolean success = this.removeById(id);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }

    //快捷链接编辑
    @Override
    @Transactional
    public R editLink(FrLink frLink) {

        if (frLink == null || StringUtils.isEmpty(frLink.getId())){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        boolean success = this.updateById(frLink);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }

    //根据id快捷链接
    @Override
    public R selectLinkById(String id) {

        if(StringUtils.isEmpty(id)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        FrLink frLink = this.getById(id);

        if (frLink == null){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS, frLink);
    }
}
