import request from '../utils/request';

export default {
  selectArticle(page,limit){
    return request({
      url: `/admin/fr-article/selectFrontArticle/${page}/${limit}`,
      method: 'post'
    });
  },
  searchArticle(page,limit,condition){
    return request({
      url: `/admin/fr-article/selectSearchArticle/${page}/${limit}`,
      method: 'post',
      data: condition
    });
  },

  selectOneById(id){
    return request({
      url: `/admin/fr-article//selectArticleById/${id}`,
      method: 'get'
    });
  },
}
