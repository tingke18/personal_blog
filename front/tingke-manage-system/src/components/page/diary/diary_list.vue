<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 随笔</el-breadcrumb-item>
                <el-breadcrumb-item>随笔列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class="container">
            <el-form :inline="true" :model="condition" class="demo-form-inline" style="margin-left: 100px">
                <el-date-picker
                        v-model="condition.start"
                        type="date"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="选择开始日期">
                </el-date-picker>

                <i class="el-icon-d-arrow-right"/>

                <el-date-picker
                        v-model="condition.end"
                        type="date"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="选择结束日期">
                </el-date-picker>

                <el-form-item>
                    &nbsp;&nbsp;&nbsp;
                    <el-button type="primary" @click="selectDiary()">查询</el-button>
                </el-form-item>
            </el-form>


            <el-table :data="diaryList" border stripe fit highlight-current-row>
                <el-table-column prop="id" label="#" align="center"></el-table-column>

                <el-table-column prop="title" label="标题" align="center"></el-table-column>

                <el-table-column prop="visitCount" label="访问量" align="center"></el-table-column>

                <el-table-column prop="createTime" label="创建时间" align="center"></el-table-column>

                <el-table-column prop="modifiedTime" label="修改时间" align="center"></el-table-column>

                <el-table-column label="是否发布" align="center">
                    <template slot-scope="scope">
                        <el-switch
                                v-model="scope.row.isPublish"
                                active-color="#13ce66"
                                inactive-color="#ff4949"
                                :active-value="1"
                                :inactive-value="0"
                                @change="editDiaryPublish(scope.row.id,scope.row.isPublish)"
                        >
                        </el-switch>
                    </template>

                </el-table-column>

                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button
                                type="primary"
                                icon="el-icon-edit"
                                @click="getInfoDiary(scope.row.id)"
                                circle
                        >
                        </el-button>

                        <router-link :to="{ path: '/diary_browse',
                        query:{//传递参数给跳转页面
                          diaryId : scope.row.id}
                        }">
                            <el-button type="success" style="margin-left: 3px" icon="el-icon-document"
                                       circle></el-button>
                        </router-link>

                        <el-button
                                style="margin-left: 3px"
                                type="danger"
                                icon="el-icon-delete"
                                @click="removeDiary(scope.row.id)"
                                circle
                        >
                        </el-button>
                    </template>
                </el-table-column>

            </el-table>

            <br/>
            <div style="text-align: center">
                <el-pagination
                        background
                        style="padding:30 20;text-align: center"
                        layout="total, prev, pager, next, jumper"
                        :total="total"
                        :current-page="page"
                        :page-size="limit"
                        @current-change="selectDiary"
                ></el-pagination>
            </div>
        </div>

    </div>
</template>
<script>
    import diaryApi from '../../../api/admin/diary';
    import moment from 'moment';

    export default {
        data() {
            return {
                diaryList: [],
                total: '',
                page: '',
                limit: '',
                condition: {
                    start: '',
                    end: ''
                }
            };
        },
        created() {
            this.selectDiary();
        },
        methods: {

            getInfoDiary(id) {
                this.$router.push({path:'/diary_add', query:{ diaryId:id }})
            },

            removeDiary(id) {//自定义的方法
                this.$confirm('此操作将永久删除该随笔, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    diaryApi.deleteDiary(id).then(res => {
                        if (res.success) {
                            this.$message({
                                type: 'success',
                                message: '删除成功'
                            });
                            this.selectDiary();
                        } else {
                            this.$message({
                                type: 'error',
                                message: res.message
                            });
                        }
                    });
                });

            },

            //监听发布状态改变
            editDiaryPublish(id, val) {
                diaryApi.editDiaryPublish(id, val).then(res => {
                    if (res.success) {
                        if (res.object == 1) {
                            this.$message({
                                type: 'success',
                                message: '发布成功'
                            });
                        } else {
                            this.$message({
                                type: 'warning',
                                message: '下线成功'
                            });
                        }
                    }
                });
            },

            selectDiary(page = 1, limit = 3) {
                this.page = page;
                this.limit = limit;
                diaryApi.selectDiary(this.page, this.limit, this.condition).then(res => {
                    if (res.success) {
                        this.diaryList = res.queryResponseResultList.list;
                        this.total = res.queryResponseResultList.total;


                        for (let i = 0; this.diaryList.length; i++) {
                            this.diaryList[i].createTime = moment(this.diaryList[i].createTime).format('YYYY-MM-DD HH:mm:ss');
                            this.diaryList[i].modifiedTime = moment(this.diaryList[i].modifiedTime).format('YYYY-MM-DD HH:mm:ss');
                        }

                    }
                });
            }

        }
    };
</script>
<style scoped>
    .information-title {
        width: 210px;
        font-size: 13px;
        display: -webkit-box;
        /* -webkit-box-orient: vertical; */
        /*! autoprefixer: off */
        -webkit-box-orient: vertical;
        /* autoprefixer: on */
        -webkit-line-clamp: 10;
        overflow: hidden;
    }

    .transition-box {
        margin-bottom: 10px;
        width: 200px;
        height: 100px;
        border-radius: 4px;
        background-color: #409EFF;
        text-align: center;
        color: #fff;
        padding: 40px 20px;
        box-sizing: border-box;
        margin-right: 20px;
    }

    .icon_relative {
        position: relative;
        top: -20px;
        left: -20px
    }

    .text {
        font-size: 14px;
    }

    .item {
        margin-bottom: 18px;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }

    .clearfix:after {
        clear: both
    }

    .box-card {
        width: 480px;
    }

    .g-of-h {
        overflow: hidden;
    }

    .g-f-l {
        float: left;
    }

    .g-f-r {
        float: right;
    }

    /*
        .main-box {
            width: 1200px;
            margin: 0 auto;
        }
    */

    .box-l {
        width: 200px;
        height: 200px;
        background-color: #8a6de9;
        animation-name: fadeLeftIn;
        -webkit-animation-name: fadeLeftIn;
    }

    .box-r {
        margin-left: 20px;
        width: 500px;
        height: 200px;
        background-color: #FDB9B9;
        animation-name: fadeRightIn;
        -webkit-animation-name: fadeRightIn;
    }

    .box-b {
        width: 240px;
        height: 300px;
        animation-name: fadeBottomIn;
        -webkit-animation-name: fadeBottomIn;
        background-color: #F2F6FC;
        margin-top: 20px;
    }

    .animated {
        -webkit-animation-duration: 1s;
        animation-duration: 1s;
        -webkit-animation-fill-mode: both;
        animation-fill-mode: both;
    }

    @keyframes fadeLeftIn {
        0% {
            opacity: 0;
            transform: translate3d(-100%, 0, 0);
        }
        100% {
            opacity: 1;
            transform: none;
        }
    }

    @keyframes fadeRightIn {
        0% {
            opacity: 0;
            transform: translate3d(100%, 0, 0);
        }
        100% {
            opacity: 1;
            transform: none;
        }
    }

    @keyframes fadeBottomIn {
        0% {
            opacity: 0;
            transform: translate3d(0, 100%, 0);
            -webkit-transform: translate3d(0, 100%, 0);
        }
        100% {
            opacity: 1;
            transform: none;
            -webkit-transform: none;
        }
    }
</style>
