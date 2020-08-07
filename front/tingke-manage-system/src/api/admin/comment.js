import request from '../../utils/request';

export default {

    selectNotCheckCommentCount(){
        return request({
            url: `/admin/fr-comment/selectNotCheckCommentCount`,
            method: 'post'
        });
    },
    selectNotCheckComment(){
        return request({
            url: `/admin/fr-comment/selectNotCheckComment`,
            method: 'post'
        });
    },
    selectCheckComment(){
        return request({
            url: `/admin/fr-comment/selectCheckComment`,
            method: 'post'
        });
    },
    adminAddComment(id,content){
        return request({
            url: `/admin/fr-comment/AdminAddComment/${id}`,
            method: 'get',
            params:content
        });
    },
    editCommentRead(id){
        return request({
            url: `/admin/fr-comment/editCommentRead/${id}`,
            method: 'get'
        });
    },
    editAllCommentRead(){
        return request({
            url: `/admin/fr-comment/editAllCommentRead`,
            method: 'get'
        });
    },
    deleteComment(id){
        return request({
            url: `/admin/fr-comment/deleteComment/${id}`,
            method: 'get'
        });
    },


}
