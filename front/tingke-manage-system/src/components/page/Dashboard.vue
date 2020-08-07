<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="9">
                <el-card shadow="hover" class="mgb20" >
                    <div class="user-info">
                        <img :src=userAvatar class="user-avator" alt />
                        <div class="user-info-cont">
                            <div class="user-info-name">{{name}}</div>
                            <div>{{role}}</div>
                            <el-divider></el-divider>
                            <div style="font-size: 17px">{{tag}}</div>
                        </div>
                    </div>
                    <div class="user-info-list">
                        上次登录时间：
                        <span>{{loginTime}}</span>
                    </div>
                </el-card>
                <el-card shadow="hover" >
                    <div slot="header" class="clearfix">
                        <span>{{dateEvent}}</span>
                        <el-button style="float: right; padding: 3px 8px" type="text" @click="deleteEvent()">删除</el-button>
                        <el-button style="float: right; padding: 3px 8px" type="text" @click="openDialog()">添加</el-button>
                    </div>

                    <el-table :show-header="false" :data="todoList" style="width:100%;" ref="multipleTable">
                        <el-table-column  type="selection" width="45"></el-table-column>
                        <el-table-column>
                            <template slot-scope="scope" id="container">
                                <div  class="todo-item" :class="{'todo-item-del': scope.row.status}" style="margin-left: -10px">{{scope.row.event}}</div>
                            </template>
                        </el-table-column>
                        <el-table-column width="85" >
                            <template slot-scope="scope">
                                <el-button size="mini" @click="selectOneEvent(scope.row.id)" style="padding: 6px"><i class="el-icon-edit" ></i></el-button>
                                <el-button size="mini" @click="completeEvent(scope.row.id,scope.row.event)" :disabled="scope.row.status" style="padding: 6px"><i class="el-icon-check"></i></el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
            <el-col :span="15">
                <el-card shadow="hover">
                    <iframe scrolling="no" src="https://tianqiapi.com/api.php?style=tg&skin=sogou" frameborder="0"
                            width="470" height="40" allowtransparency="true" style="margin-top: -15px;margin-bottom: -15px"></iframe>
                </el-card>
            </el-col>

            <el-col :span="15" shadow="hover">
                <el-card shadow="hover">
                    <el-calendar v-model="calendar" style="margin-top: -15px">
                        <!-- 这里使用的是 2.5 slot 语法，对于新项目请使用 2.6 slot 语法-->
                        <template
                                slot="dateCell"
                                slot-scope="{date, data}">
                            <p :class="data.isSelected ? 'is-selected' : ''" style="height: 150px;width: 150px" @click="selectEventByDate(date)">
                                {{ data.day.split('-').slice(2).join('-') }} {{ data.isSelected ? '＾－＾' : ''}}
                            </p>
                        </template>
                    </el-calendar>
                </el-card>
            </el-col>
        </el-row>


        <el-dialog
                :title="eventTitle"
                :visible.sync="dialogVisible"
                width="30%"
                :before-close="handleClose">
            <el-form ref="form" label-width="80px">
                <el-form-item label="任务名称">
                    <el-input v-model="events.event" @keyup.enter.native="saveORedit()"></el-input>
                </el-form-item>
                <el-form-item style="margin-left: 180px">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveORedit()">确 定</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

    </div>
</template>

<script>
    import Schart from 'vue-schart'
    import eventApi from '../../api/admin/event'
    import { SlickList, SlickItem } from 'vue-slicksort'
    import moment from "moment"


    export default {
        data() {
            return {
                sliderVal:0,
                dateEvent:"",//日期显示
                eventTitle:"",
                events:{//任务
                    id:"",
                    event:""
                },//任务尸实体
                dialogVisible: false,//设置弹窗
                calendar: new Date(), //日期
                name: localStorage.getItem('ms_username'),
                userAvatar: localStorage.getItem('ms_avatar'),
                tag: localStorage.getItem('ms_tag'),
                loginTime: "",
                todoList: [],

            };
        },
        components: {
            Schart
        },
        computed: {
            role() {
                return localStorage.getItem('ms_role') === 'admin' ? '超级管理员' : '管理员';
            }

        },
        created() {
            this.loginTime = moment(localStorage.getItem('ms_login_time')).format("YYYY-MM-DD HH:mm:ss")
            this.selectEvent()
        },
        methods: {
            deleteEvent(){
                //遍历获取选中的id数据
                var checks = this.$refs.multipleTable.selection
                var checkIds = ""
                    for (var i = 0;i < checks.length;i++ ){
                    checkIds += checks[i].id
                        if (i+1 < checks.length){
                            checkIds += ","
                        }
                }
                //删除提示
                this.$confirm('确定要删除吗!', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    eventApi.deleteEventByIds(checkIds).then(res=>{
                        if(res.success){
                            this.$message({
                                type: 'success',
                                message: '删除成功'
                            });
                            this.selectEventByDate(res.object)
                        }else {
                            this.$message({
                                type: 'error',
                                message: '删除失败'
                            });
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消'
                    });
                });

                console.log(checkIds)
            },
            selectEventByDate(date){
                var selectDate = moment(date).format("YYYY-MM-DD")
                eventApi.selectEventByDate(selectDate).then(res=>{
                    if (res.success){
                        console.log(res)
                        this.todoList = res.queryResponseResultList.list
                        //判断点击日期
                        if (selectDate == moment(new Date()).format("YYYY-MM-DD")){
                            this.dateEvent = "今日任务"
                        }else {
                            this.dateEvent = "日期："+selectDate
                        }
                    }
                })

            },
            handleClose(){//对话框关闭前事件
                this.events.id = ""
                this.events.event = ""
                this.dialogVisible = false
            },
            completeEvent(id,event){//完成事件
                this.$confirm('任务 “'+event+'” ， Are you sure!', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    eventApi.completeEvent(id).then(res=>{
                        if (res.success){
                            this.$message({
                                type: 'success',
                                message: '又完成了一个!'
                            });
                            this.selectEventByDate(res.object)
                            this.selectEvent()
                        }else {
                            this.$message({
                                type: 'info',
                                message: res.message
                            });
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消'
                    });
                });

            },
            selectOneEvent(id){
                this.eventTitle = "请修改任务"
                this.dialogVisible = true
                eventApi.selectOneEvent(id).then(res=>{
                    console.log(res)
                    if (res.success){
                        this.events.id = res.object.id
                        this.events.event = res.object.event
                    }else {
                        this.$message.error(res.message);
                    }
                })
            },
            saveORedit(){
                console.log(this.events)
                //如果event存在，则是添加否则是编辑
                if (this.events.id == ""||this.events.id == null){
                    this.addEvent()
                }else {
                    this.editEvent()
                }
            },
            openDialog(){//打开对话框
                this.dialogVisible=true
                this.eventTitle="请添加任务"
            },
            addEvent(){
                eventApi.addEvent(this.events.event).then(res=>{
                    if (res.success){
                        this.$message.success('添加成功');
                        this.todoList.push(res.object)
                        console.log(this.todoList)
                        this.dialogVisible = false,//设置弹窗
                            this.events.id = ""
                        this.events.event = ""
                    }else{
                        this.$message.error(res.message);
                    }
                })
            },
            editEvent(){
                eventApi.editEvent(this.events).then(res=>{
                    if (res.success){
                        this.$message.success('修改成功');
                        this.dialogVisible = false,//设置弹窗
                            this.selectEvent()
                        this.events.id = ""
                        this.events.event = ""
                        this.selectEventByDate(res.object)
                    }else{
                        this.$message.error(res.message);
                    }
                })
            },
            selectEvent(){
                eventApi.selectEvent().then(res=>{
                    if (res.success){
                        this.dateEvent = "今日任务"
                        console.log(res)
                        this.todoList = res.queryResponseResultList.list
                    }

                })
            },
            changeDate() {
                const now = new Date().getTime();
                this.data.forEach((item, index) => {
                    const date = new Date(now - (6 - index) * 86400000);
                    item.name = `${date.getFullYear()}/${date.getMonth() + 1}/${date.getDate()}`;
                });
            }

        }
    };
</script>


<style scoped>
    .el-row {
        margin-bottom: 20px;
    }

    .grid-content {
        display: flex;
        align-items: center;
        height: 100px;
    }

    .grid-cont-right {
        flex: 1;
        text-align: center;
        font-size: 14px;
        color: #999;
    }

    .grid-num {
        font-size: 30px;
        font-weight: bold;
    }

    .grid-con-icon {
        font-size: 50px;
        width: 100px;
        height: 100px;
        text-align: center;
        line-height: 100px;
        color: #fff;
    }

    .grid-con-1 .grid-con-icon {
        background: rgb(45, 140, 240);
    }

    .grid-con-1 .grid-num {
        color: rgb(45, 140, 240);
    }

    .grid-con-2 .grid-con-icon {
        background: rgb(100, 213, 114);
    }

    .grid-con-2 .grid-num {
        color: rgb(45, 140, 240);
    }

    .grid-con-3 .grid-con-icon {
        background: rgb(242, 94, 67);
    }

    .grid-con-3 .grid-num {
        color: rgb(242, 94, 67);
    }

    .user-info {
        display: flex;
        align-items: center;
        padding-bottom: 20px;
        border-bottom: 2px solid #ccc;
        margin-bottom: 20px;
    }

    .user-avator {
        width: 120px;
        height: 120px;
        border-radius: 50%;
    }

    .user-info-cont {
        padding-left: 50px;
        flex: 1;
        font-size: 14px;
        color: #999;
    }

    .user-info-cont div:first-child {
        font-size: 30px;
        color: #222;
    }

    .user-info-list {
        font-size: 14px;
        color: #999;
        line-height: 25px;
    }

    .user-info-list span {
        margin-left: 70px;
    }

    .mgb20 {
        margin-bottom: 20px;
    }

    .todo-item {
        font-size: 14px;
    }

    .todo-item-del {
        text-decoration: line-through;
        color: #999;
    }

    .schart {
        width: 100%;
        height: 300px;
    }
</style>
