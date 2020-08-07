import request from '../../utils/request';

export default {
    //对文章增删改查
    addArticle(article) {
        return request({
            url: `/admin/fr-article/addArticle`,
            method: 'post',
            data: article
        });
    },
    editArticle(article){
        return request({
            url: `/admin/fr-article/editArticle`,
            method: 'post',
            data: article
        });
    },
    deleteArticle(id){
        return request({
            url: `/admin/fr-article/deleteArticle/${id}`,
            method: 'get'
        });
    },
    selectAllArticle(page,limit,condition){
        return request({
            url: `/admin/fr-article/selectAllArticle/${page}/${limit}`,
            method: 'post',
            data: condition
        });
    },
    selectArticleById(id){
        return request({
            url: `/admin/fr-article/selectArticleById/${id}`,
            method: 'get'
        });
    },
    editArticlePublish(id,isPublish){
        return request({
            url: `/admin/fr-article/editArticlePublish/${id}/${isPublish}`,
            method: 'get'
        });
    },
    uploadImage(file){
        return request({
            url: `/TingkeOss/oss`,
            method: 'post',
            data: file,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }

}
