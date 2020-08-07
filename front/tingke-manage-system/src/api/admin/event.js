import request from '../../utils/request';

export default {
    //对事件增删改查
    addEvent(event) {
        return request({
            url: `/admin/acl-event/addEvent/${event}`,
            method: 'get'
        });
    },
    editEvent(event){
        return request({
            url: `/admin/acl-event/editEvent`,
            method: 'post',
            data: event
        });
    },
    completeEvent(id){
        return request({
            url: `/admin/acl-event/completeEvent/${id}`,
            method: 'get'
        });
    },
    selectOneEvent(id){
        return request({
            url: `/admin/acl-event/selectOneEvent/${id}`,
            method: 'post'
        });
    },
    selectEvent(){
        return request({
            url: `/admin/acl-event/selectEvent`,
            method: 'post'
        });
    },
    selectEventByDate(date){
        return request({
            url: `/admin/acl-event/selectEventByDate/${date}`,
            method: 'get'
        });
    },
    deleteEventByIds(ids){
        return request({
            url: `/admin/acl-event/deleteEventByIds/${ids}`,
            method: 'get',
        });
    },




}
