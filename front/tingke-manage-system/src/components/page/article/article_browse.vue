<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 文章</el-breadcrumb-item>
                <el-breadcrumb-item>文章预览</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <h1>{{article.title}}</h1>
            <el-row style="margin-top: 20px">
                <el-col :span="14">
                    <span>时间：{{article.modifiedTime}}</span>
                </el-col>
            </el-row>
            <el-divider></el-divider>
           <div id="article" v-html="article.contentHtml" style=">>> .lang-language{background: #8a6de9}">{{article.contentHtml}}</div>
        </div>
    </div>
</template>
<script>
    import articleApi from "../../../api/admin/article"
    import 'mavon-editor/dist/css/index.css'
    import marked from 'marked'
    import moment from "moment"
    import Highlight from 'vue-markdown-highlight'
    import hljs from 'highlight.js'

    export default {
        data(){
            return{
                id:"",
                article:{
                    title:"",
                    synopsis:"",
                    content:"",
                    contentHtml:"",
                    visitCount:"",
                    author:"",
                    createTime:"",
                    modifiedTime:""
                }
            }
        },
        computed: {
            marked,
            Highlight
        },
        created() {
            this.id = this.$route.query.articleId

            this.showHtml()
        },
        methods:{

            showHtml(){
                articleApi.selectArticleById(this.id).then(res=>{
                    if (res.success) {
                        this.article = res.object

                       // this.article.content = marked(this.article.content)
                        this.article.modifiedTime = moment(this.article.modifiedTime).format("YYYY-MM-DD HH:mm:ss")
                    }
                })
            }

        }
    }
</script>
<style scoped>
    >>> pre{
        background-color: #c2e7b0;
    }
</style>

