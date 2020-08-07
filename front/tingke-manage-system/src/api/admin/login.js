import request from '../../utils/request';

export default {
    loginByName(params) {
        return request({
            url: `/admin/acl-user/loginByName`,
            method: 'post',
            params: params
        });
    },
    sendPhoneCode(phone) {
        return request({
            url: `/admin/acl-user/send/${phone}`,
            method: 'get'
        });
    },
    loginByPhone(params){
        return request({
            url: `/admin/acl-user/loginByPhone`,
            method: 'post',
            params: params
        });
    }
};
