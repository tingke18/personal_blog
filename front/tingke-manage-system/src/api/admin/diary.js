import request from '../../utils/request';

export default {
    //随笔增删改查
    selectDiary(page,limit,condition) {
        return request({
            url: `/admin/acl-diary/selectAllDiary/${page}/${limit}`,
            method: 'post',
            data: condition
        });
    },
    selectDiaryById(id){
        return request({
            url: `/admin/acl-diary/selectDiaryById/${id}`,
            method: 'get'
        });
    },
    addDiary(diary){
        return request({
            url: `/admin/acl-diary/addDiary`,
            method: 'post',
            data: diary
        });
    },
    editDiary(diary){
        return request({
            url: `/admin/acl-diary/editDiary`,
            method: 'post',
            data: diary
        });
    },
    deleteDiary(id){
        return request({
            url: `/admin/acl-diary/deleteDiary/${id}`,
            method: 'get'
        });
    },
    editDiaryPublish(id,isPublish){
        return request({
            url: `/admin/acl-diary/editDiaryPublish/${id}/${isPublish}`,
            method: 'get'
        });
    }
}
