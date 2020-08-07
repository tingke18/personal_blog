import request from '../utils/request';

export default {
  selectPageBook(page,limit,condition) {
    return request({
      url: `/admin/fr-book/selectPageBook/${page}/${limit}`,
      method: 'post',
      params:condition
    });
  },
}
