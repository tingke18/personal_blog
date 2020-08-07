<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 随笔</el-breadcrumb-item>
                <el-breadcrumb-item>随笔预览</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <h1>{{diary.title}}</h1>
            <el-row style="margin-top: 20px">
                <el-col :span="14">
                    <span>时间：{{diary.modifiedTime}}</span>
                </el-col>
            </el-row>
            <el-divider></el-divider>
            <div id="article" v-html="diary.contentHtml" style=">>> .lang-language{background: #8a6de9}">{{diary.contentHtml}}</div>
        </div>
    </div>
</template>
<script>
    import diaryApi from '../../../api/admin/diary';
    import moment from "moment"
    import Highlight from 'vue-markdown-highlight'

    export default {
        data(){
            return{
            diary:{
                title:"",
                content:"",
                contentHtml:"",
                createTime:"",
                modifiedTime:""
            },
            id:''
            }
        },
        created() {
            this.id = this.$route.query.diaryId

            this.showHtml()
        },
        components:{
            moment,
            Highlight
        },
        methods:{
            showHtml(){
                diaryApi.selectDiaryById(this.id).then(res=>{
                    if (res.success) {
                        this.diary = res.object

                        // this.article.content = marked(this.article.content)
                        this.diary.modifiedTime = moment(this.diary.modifiedTime).format("YYYY-MM-DD HH:mm:ss")
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
