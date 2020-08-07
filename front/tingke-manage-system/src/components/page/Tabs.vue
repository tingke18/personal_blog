<template>
    <div class="">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-copy"></i> 用户评论消息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-tabs v-model="message">
                <el-tab-pane :label="`未读消息(${unread.length})`" name="first">
                    <!--
                                        <el-collapse-item   v-for="ur in unread"  :title="ur.name" :name="index">
                                            <div>内容：{{ur.content}}</div>
                                            <div>{{ur.createTime}}</div>
                                            <el-button size="small" >标为已读</el-button>
                                        </el-collapse-item>-->
                    <el-table :data="unread" :show-header="false" style="width: 100%">
                        <el-table-column>
                            <template slot-scope="scope">
                                <span class="message-title" @click="showComment(scope.row.id)">{{scope.row.name}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="createTime" width="180"></el-table-column>
                        <el-table-column width="120">
                            <template slot-scope="scope">
                                <el-button size="small" @click="editCommentRead(scope.row.id)">标为已读</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="handle-row">
                        <el-button type="primary" @click="editAllCommentRead()">全部标为已读</el-button>
                    </div>
                </el-tab-pane>
                <el-tab-pane :label="`已读消息(${read.length})`" name="second">
                    <template v-if="message === 'second'">
                        <el-table :data="read" :show-header="false" style="width: 100%">
                            <el-table-column>
                                <template slot-scope="scope">
                                    <span class="message-title">{{scope.row.name}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="createTime" width="150"></el-table-column>
                            <el-table-column width="120">
                                <template slot-scope="scope">
                                    <el-button type="danger" @click="deleteComment(scope.row.id)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </template>
                </el-tab-pane><!--
                <el-tab-pane :label="`回收站()`" name="third">
                    <template v-if="message === 'third'">
                        <el-table :data="recycle" :show-header="false" style="width: 100%">
                            <el-table-column>
                                <template slot-scope="scope">
                                    <span class="message-title">{{scope.row.title}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" width="150"></el-table-column>
                            <el-table-column width="120">
                                <template slot-scope="scope">
                                    <el-button @click="handleRestore(scope.$index)">还原</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <div class="handle-row">
                            <el-button type="danger">清空回收站</el-button>
                        </div>
                    </template>
                </el-tab-pane>-->
            </el-tabs>
        </div>

        <el-dialog
                title="评论"
                :visible.sync="dialogVisible"
                width="30%"
                :before-close="handleClose">
            <el-col :span="24">
                <span style="margin-left: 28px">名称： {{comment.name}}</span>
            </el-col>
            <el-col :span="24">
                <span style="margin-left: 28px">评论： {{comment.content}}</span>

            </el-col>

            <el-form :model="adminComment"   label-width="100px">
                <el-form-item label="回复评论" prop="reply">
                    <el-input type="textarea" v-model="adminComment.content" ></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitReplyComment()">提交</el-button>
                    <el-button @click="resetReplyComment()">重置</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

    </div>
</template>

<script>
    import commentApi from '../../api/admin/comment';
    import moment from 'moment';

    export default {
        name: 'tabs',
        data() {
            return {
                dialogVisible: false,
                activeNames: 3,
                message: 'first',
                showHeader: false,
                unread: [],
                read: [],
                comment: {},
                adminComment:{
                    content:''
                }
            };
        },
        created() {
            this.selectUnRead();
            this.selectReadComment();
        },
        methods: {
            resetReplyComment(){
                this.adminComment.content = ''
             },
            //删除评论
            deleteComment(id){
                commentApi.deleteComment(id).then(res=>{
                    if (res.success){
                        this.$message({
                            type: 'success',
                            message: '删除成功'
                        });
                        this.selectReadComment()
                    }
                })
            },
            //设置所有评论已读
            editAllCommentRead(){
              commentApi.editAllCommentRead().then(res=>{
                  if (res.success){
                      this.$message({
                          type: 'success',
                          message: '设置成功'
                      });
                      this.selectUnRead()
                      this.selectReadComment()
                  }
              })
            },

            //设置评论已读
            editCommentRead(id){
                commentApi.editCommentRead(id).then(res=>{
                    if (res.success){
                        this.selectUnRead()
                        this.selectReadComment()
                    }else {
                        console.log(res.message)
                    }
                })
            },
            submitReplyComment(){
                commentApi.adminAddComment(this.comment.id,this.adminComment).then(res=>{
                    if (res.success){
                        this.$message({
                            type: 'success',
                            message: '回复成功'
                        });
                        this.editCommentRead(this.comment.id)
                        this.dialogVisible = false
                        this.selectUnRead()
                        this.selectReadComment()
                    }else{
                        console.log(res.message)
                    }
                })
            },


            handleClose(){
                this.$confirm('确认关闭？')
                    .then(_ => {
                        this.dialogVisible = false
                        this.comment = {}
                    })
                    .catch(_ => {});
            },

            selectUnRead() {
                commentApi.selectNotCheckComment().then(res => {
                    if (res.success) {
                        this.unread = res.queryResponseResultList.list;
                        for (let i = 0; this.unread.length; i++) {
                            this.unread[i].createTime = moment(this.unread[i].createTime).format('YYYY-MM-DD HH:mm:ss');
                        }
                    } else {
                        console.log(res.message);
                    }
                });
            },

            showComment(id) {
                this.dialogVisible = true
                //通过遍历获取指定的评论
                for (var i = 0; this.unread.length; i++) {
                    if (id == this.unread[i].id) {
                        this.comment = this.unread[i];
                    }
                }
            },

            selectReadComment() {
                commentApi.selectCheckComment().then(res => {
                    if (res.success) {
                        this.read = res.queryResponseResultList.list;
                        for (let i = 0; this.read.length; i++) {
                            this.read[i].createTime = moment(this.read[i].createTime).format('YYYY-MM-DD HH:mm:ss');
                        }
                    } else {
                        console.log(res.message);
                    }
                });
            }

        },
        computed: {}
    };

</script>

<style>
    .message-title {
        cursor: pointer;
    }

    .handle-row {
        margin-top: 30px;
    }
</style>

