package com.tingke.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tingke.admin.entity.FrBook;
import com.tingke.admin.exection.CastDiyException;
import com.tingke.admin.mapper.FrBookMapper;
import com.tingke.admin.model.CommonCode;
import com.tingke.admin.model.QueryResponseResultList;
import com.tingke.admin.model.R;
import com.tingke.admin.service.FrBookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhx
 * @since 2020-05-30
 */
@Service
public class FrBookServiceImpl extends ServiceImpl<FrBookMapper, FrBook> implements FrBookService {

    //推荐书籍显示
    @Override
    public R selectBookById(String id) {

        if(StringUtils.isEmpty(id)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        FrBook frBook = this.getById(id);

        if (frBook == null){
            CastDiyException.cast(CommonCode.FAIL);
        }


        return new R(CommonCode.SUCCESS,frBook);

    }

    //推荐书籍添加
    @Override
    @Transactional
    public R addBook(FrBook frBook) {
        if (frBook == null){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        boolean save = this.save(frBook);

        if (!save){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS,frBook);
    }

    //推荐书籍修改
    @Override
    @Transactional
    public R editBook(FrBook frBook) {
        if (frBook == null || StringUtils.isEmpty(frBook.getId())){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        boolean success = this.updateById(frBook);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }

    //推荐书籍删除
    @Override
    @Transactional
    public R deleteBook(String id) {

        if(StringUtils.isEmpty(id)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        boolean success = this.removeById(id);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }

    @Override
    public R selectPageBook(Long page, Long limit, String condition) {
        if (StringUtils.isEmpty(page.toString()) || StringUtils.isEmpty(limit.toString())){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        //构建分页
        Page<FrBook> spage = new Page<>(page, limit);
        QueryWrapper<FrBook> wrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(condition)){
            wrapper.like("title",condition);
        }

        Page<FrBook> ipage = this.page(spage,wrapper);
        if (ipage == null){
            CastDiyException.cast(CommonCode.FAIL);
        }



        QueryResponseResultList queryResponseResultList = new QueryResponseResultList();
        queryResponseResultList.setTotal((int) spage.getTotal());
        queryResponseResultList.setList(spage.getRecords());

        return new R(CommonCode.SUCCESS,queryResponseResultList);
    }
}
