package com.tingke.admin.service;

import com.tingke.admin.entity.FrBook;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tingke.admin.model.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhx
 * @since 2020-05-30
 */
public interface FrBookService extends IService<FrBook> {

    //推荐书籍显示
    R selectBookById(String id);

    //推荐书籍添加
    R addBook(FrBook frBook);

    //推荐书籍修改
    R editBook(FrBook frBook);

    //推荐书籍删除
    R deleteBook(String id);

    //快捷图书分页显示
    R selectPageBook(Long page, Long limit, String condition);
}
