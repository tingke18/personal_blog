import request from '../utils/request';

export default {
  selecLink () {
    return request({
      url: `/admin/fr-link/selectLink`,
      method: 'post'
    });
  },
}
