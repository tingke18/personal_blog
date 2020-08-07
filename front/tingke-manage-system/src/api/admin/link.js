import request from '../../utils/request';

export default {
    //链接增删改查
    selectLink(page,limit,condition) {
        return request({
            url: `/admin/fr-link/selectPageLink/${page}/${limit}`,
            method: 'post',
            params:condition
        });
    },
    addLink(link) {
        return request({
            url: `/admin/fr-link/addLink`,
            method: 'post',
            data: link
        });
    },
    deleteLink(id) {
        return request({
            url: `/admin/fr-link/deleteLink/${id}`,
            method: 'get'
        });
    },
    editLink(link) {
        return request({
            url: `/admin/fr-link/editLink`,
            method: 'post',
            data: link
        });
    },
    selectLinkCategory(){
        return request({
            url: `/admin/fr-link-category/selectLinkCategory`,
            method: 'get'
        });
    },
    addLinkCategory(category){
        return request({
            url: `/admin/fr-link-category/addLinkCategory/${category}`,
            method: 'get'
        });
    },
    editLinkCategory(category){
        return request({
            url: `/admin/fr-link-category/editLinkCategory`,
            method: 'post',
            data:category
        });
    },
    deleteLinkCategory(id){
        return request({
            url: `/admin/fr-link-category/deleteLinkCategory/${id}`,
            method: 'get'
        });
    },
    selectLinkById(id){
        return request({
            url: `/admin/fr-link/selectLinkById/${id}`,
            method: 'get'
        });
    }




}
