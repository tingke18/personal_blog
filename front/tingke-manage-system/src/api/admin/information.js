import request from '../../utils/request';

export default {
    //对个人信息的增删改查
    selectPresonalInformation(id){
        return request({
            url: `/admin/acl-user/findUserById/${id}`,
            method: 'get'
        });
    },
    editUser(params){
        return request({
            url: `/admin/acl-user/editUser`,
            method: 'post',
            data:params
        });
    },
    sendEmailCode(id,email){
        return request({
            url: `/admin/acl-user/sendEmail/${id}/${email}`,
            method: 'post'
        });
    },
    editPassword(params) {
        return request({
            url: `/admin/acl-user/editPassword`,
            method: 'post',
            data:params
        });
    }

}
