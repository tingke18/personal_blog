import request from '../utils/request';

export default {
  selecDiary (page,limit) {
    return request({
      url: `/admin/acl-diary/selectDiary/${page}/${limit}`,
      method: 'get'
    });
  },
  selectDiaryById(id){
    return request({
      url: `/admin/acl-diary/selectDiaryById/${id}`,
      method: 'get'
    });
  },
  selectDiaryVo(id){
    return request({
      url: `/admin/acl-diary/selectDiaryVo`,
      method: 'get'
    });
  }
}
