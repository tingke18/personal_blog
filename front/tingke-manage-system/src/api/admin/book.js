import request from '../../utils/request';

export default {
    //图书增删改查
    addBook(book) {
        return request({
            url: `/admin/fr-book/addBook`,
            method: 'post',
            data:book
        });
    },
    selectPageBook(page,limit,condition) {
        return request({
            url: `/admin/fr-book/selectPageBook/${page}/${limit}`,
            method: 'post',
            params:condition
        });
    },
    selectBook(id) {
        return request({
            url: `/admin/fr-book/selectBookById/${id}`,
            method: 'post'
        });
    },
    editBook(book){
        return request({
            url: `/admin/fr-book/editBook`,
            method: 'post',
            data:book
        });
    },
    deleteBook(id){
        return request({
            url: `/admin/fr-book/deleteBook/${id}`,
            method: 'delete'
        });
    }


}
