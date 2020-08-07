import request from '../utils/request';

export default {

  addUser (params) {
    return request({
      url: `/admin/fr-user/addUser`,
      method: 'post',
      data: params
    });
  },
  queryUserById(){
    return request({
      url: `/admin/fr-user/queryUserById`,
      method: 'post'
    });
  },
  userLogin(params){
    return request({
      url: `/admin/fr-user/userLogin`,
      method: 'post',
      data: params
    });
  }
}
