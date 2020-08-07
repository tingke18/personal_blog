<template>
    <div>
        <div class="crumbs">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 文章</el-breadcrumb-item>
            <el-breadcrumb-item>文章列表</el-breadcrumb-item>
        </el-breadcrumb>
        </div>

        <div class="container">
        <el-form :inline="true" :model="condition" class="demo-form-inline" style="text-align: center">
            <el-form-item label="标题:">
                <el-input v-model="condition.title" placeholder="请输入标题"></el-input>
            </el-form-item>
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
                <el-button type="primary" @click="selectAllArticle()">查询</el-button>
            </el-form-item>
        </el-form>

        <el-row :gutter="10" style="margin-left: 150px;margin-top: -20px">
            <el-col :sm="5" v-for="article in articleList">
                <div class="main-box" @click="editModel(article.id)" style="cursor: pointer">
                    <el-card class="box-b animated" shadow="hover">
                        <div slot="header" class="clearfix">
                            <p>{{article.title}}</p>
                        </div>
                        <div class="information-title" v-html="article.synopsis">
                                {{article.synopsis}}
                        </div>
                    </el-card>
                </div>
            </el-col>
        </el-row>

        <br/>
        <div  style="text-align: center">
            <el-pagination
                    background
                    layout="prev, pager, next"
                    :total="total"
                    :current-page="page"
                    class="el-pager"
                    @current-change="selectAllArticle"
            >
            </el-pagination>
        </div>



            <el-dialog
                    :title="eventTitle"
                    :visible.sync="dialogFormVisible"
                    :before-close="handleClose"
                    width="40%">
                <el-form ref="form" :model="article" label-width="80px">

                <el-form-item label="标题:" >
                    <el-input v-model="article.title" readonly="true" style="width: 450px"></el-input>
                </el-form-item>

                <el-form-item label="访问数量:" >
                    <el-input v-model="article.visitCount" readonly="true" style="width: 450px"></el-input>
                </el-form-item>

                <el-form-item label="创建时间:" >
                    <el-input v-model="article.createTime" readonly="true" style="width: 450px"></el-input>
                </el-form-item>

                <el-form-item label="修改时间:" >
                    <el-input v-model="article.modifiedTime" readonly="true" style="width: 450px"></el-input>
                </el-form-item>




                <el-form-item label="是否发布:">
                    <el-switch
                            v-model="article.isPublish"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                            :active-value="1"
                            :inactive-value="0"
                    >
                    </el-switch>
                </el-form-item>
                <el-form-item label="点击预览:">

                        <router-link :to="{
                        path: 'article_browse',
                        query:{//传递参数给跳转页面
                          articleId : this.article.id
                        }
                    }"><el-button type="primary" icon="el-icon-document">预览</el-button></router-link>
                </el-form-item>
                <el-form-item label="修改/删除:">
                    <el-button type="primary" icon="el-icon-edit" @click="editArticle(article.id)">修改</el-button>
                    <el-button type="danger" icon="el-icon-delete" @click="deleteArticle(article.id)">删除</el-button>
                </el-form-item>
                </el-form>
            </el-dialog>

        </div>




    </div>
</template>

<script>

    import articleApi from "../../../api/admin/article"
    import moment from "moment"

    export default {
        data() {
            return {
                condition:{//搜索条件
                    title:"",
                    start:"",
                    end:""
                },
                eventTitle: "文章详情",
                dialogFormVisible: false,//弹窗
                name: "animate",//动画标签
                article: {//文章字段
                    id: "",
                    title: "",
                    synopsis:"",
                    content: "",
                    createTime: "",
                    modifiedTime: "",
                    visitCount:'',
                    isPublish: 0
                },
                page: 1, //初始化查询条件
                limit: 8,
                total:"",//多少条
                articleList: [], //文章列表
                checkNumber:0 //是否发布的校验数字
            }
        },
        created() {
            this.selectAllArticle();
        },
        methods: {
            deleteArticle(id){
                this.$confirm('确定要删除文章吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    articleApi.deleteArticle(id).then(res=>{
                        if (res.success){
                            this.$message({
                                type: 'success',
                                message: '删除成功'
                            });
                            this.dialogFormVisible = false
                            this.selectAllArticle()
                        } else {
                            this.$message({
                                type: 'error',
                                message: res.meaasge
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
            editArticle(id){
                   this.$router.push({path:"/article_add",query:{adticleId:id}})
            },
            handleClose(){//关闭前
                this.article = {}
                this.dialogFormVisible = false
                this.checkNumber = 0
            },
            editModel(id) {
                this.dialogFormVisible = true
                articleApi.selectArticleById(id).then(res=>{
                    if (res.success){
                        this.article = res.object
                        this.article.createTime = moment(this.article.createTime).format("YYYY-MM-DD HH:mm:ss")
                        this.article.modifiedTime = moment(this.article.modifiedTime).format("YYYY-MM-DD HH:mm:ss")
                        console.log(this.article)
                    }
                })
            },
            selectAllArticle(page = 1) {
                this.page = page
                articleApi.selectAllArticle(this.page, this.limit,this.condition).then(res => {
                    if (res.success) {
                        this.articleList = res.queryResponseResultList.list
                        this.total = res.queryResponseResultList.total


                    } else {
                        console.log(res.message)
                    }
                })
            }
        },
        watch:{
            'article.isPublish':{
                handler(){
                    if (this.dialogFormVisible == true&&this.checkNumber == 1){//判断弹窗是否开启，且校验数字是否为1
                        articleApi.editArticlePublish(this.article.id,this.article.isPublish).then(res=>{
                            if (res.success){
                                if (res.object == 1){
                                    this.$message({
                                        type: 'success',
                                        message: '发布成功'
                                    });
                                }else {
                                    this.$message({
                                        type: 'warning',
                                        message: '下线成功'
                                    });
                                }
                                this.checkNumber = 1  //因为窗口没有关闭，所有操作做完都把检验数字设置为1
                            }else {
                                this.$message({
                                    type: 'error',
                                    message: res.message
                                });
                                this.checkNumber = 1
                            }
                        })
                    }else if (this.dialogFormVisible == true){ //第一次窗口打开，将校验数字设置为1
                        this.checkNumber = 1
                    }else {return}
                },
                //immediate: true//当第一次绑定值是不会被监听
            }
        }
    }
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
