import request from '../utils/request';

export default {

  addComment (content) {
    return request({
      url: `/admin/fr-comment/addComment`,
      method: 'post',
      params: content
    });
  },
  selectComment (param) {
    return request({
      url: `/admin/fr-comment/selectComment`,
      method: 'post'
    });
  },


}
