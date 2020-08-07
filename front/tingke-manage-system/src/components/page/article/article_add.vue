<template>
    <div>
        <div class="crumbs">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 文章</el-breadcrumb-item>
            <el-breadcrumb-item v-if="!postForm.id">文章添加</el-breadcrumb-item>
            <el-breadcrumb-item v-else>文章修改</el-breadcrumb-item>
        </el-breadcrumb>
        </div>
        <el-form ref="postForm" :model="postForm" :rules="rules">
            <div class="container">
                <el-form-item prop="title" style="margin-top: -30px">
                    <MDinput v-model="postForm.title" :maxlength="100" name="name" required style="width: 50%">
                        标题
                    </MDinput>
                </el-form-item>
                <el-form-item prop="synopsis">
                    <MDinput v-model="postForm.synopsis"  :maxlength="100"  name="name" required>
                        概要
                    </MDinput>
                </el-form-item>

                <el-form-item  label="课程封面:">
                <!-- 课程封面 TODO -->
                    <el-upload
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                            :before-upload="beforeAvatarUpload"
                            :action="'http://localhost:9999/TingkeOss/oss'"
                            field="file"
                            class="avatar-uploader">
                        <img width="100%" :src="postForm.cover" />
                    </el-upload>
                </el-form-item>

                <mavon-editor v-model="postForm.content" ref="md" @imgAdd="uploadImage" @change="change" @save="saveArticle()"
                              style="min-height: 600px"/>
                <el-button class="editor-btn" type="primary" @click="submitORedit()">提交</el-button>
            </div>
        </el-form>
    </div>
</template>

<script>
    import {mavonEditor} from 'mavon-editor'
    import 'mavon-editor/dist/css/index.css'
    import MDinput from '../../../assets/compones/MDinput'
    import articleApi from "../../../api/admin/article"
    import eventApi from "../../../api/admin/event";
    import VueCropper  from 'vue-cropperjs';


    export default {
        name: 'markdown',
        data: function () {
            return {
                content: '',
                html: '',
                configs: {},
                postForm: {
                    id:"",
                    cover:'',
                    title: "",
                    synopsis:"",
                    content: "",
                    contentHtml:""
                },
                rules: {
                    title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
                    synopsis: [{ required: true, message: '请输入梗概', trigger: 'blur' }],
                },

            }
        },
        created() {
            localStorage.setItem('ms_isCloseArticle',"true"),
            localStorage.setItem('ms_isCloseDiary',"false")

            this.postForm.id = this.$route.query.adticleId || ""

            //回显文章
            if (this.postForm.id != null || this.postForm.id != ""){
                articleApi.selectArticleById(this.postForm.id).then(res=>{
                    this.postForm= res.object
                })
            }

        },
        components: {
            mavonEditor,
            MDinput,
            VueCropper
        },
        methods: {

            //上传成功回掉
            handleAvatarSuccess(res, file) {
                console.log(res); // 上传响应
                console.log(URL.createObjectURL(file.raw)); // base64编码
                this.postForm.cover = res.object;
                this.$message.success("封面上传成功!");
            },

            //上传前调用
            beforeAvatarUpload(file) {
                const isJPG = file.type === "image/jpeg";
                const isLt2M = file.size / 1024 / 1024 < 2;
                if (!isJPG) {
                    this.$message.error("上传头像图片只能是 JPG或PNG 格式!");
                }
                if (!isLt2M) {
                    this.$message.error("上传头像图片大小不能超过 2MB!");
                }
                return isJPG && isLt2M;
            },


            saveArticle(){
                articleApi.editArticle(this.postForm).then(res=>{
                    if (res.success){
                        localStorage.setItem('ms_isCloseArticle', "true"); //设置窗口可以正常关闭
                    }
                })
            },
            // 将图片上传到服务器，返回地址替换到md中
            uploadImage(pos, $file) {
                var formdata = new FormData();
                formdata.append('file', $file);
                // 这里没有服务器供大家尝试，可将下面上传接口替换为你自己的服务器接口
                articleApi.uploadImage(formdata).then(res => {
                    if (res.success) {
                        var url = res.object
                        console.log(url)
                        this.$refs.md.$img2Url(pos, url)
                    }
                })

                /*  this.$axios({
                      url: 'http://localhost:9999/TingkeOss/oss',
                      method: 'post',
                      data: formdata,
                      headers: {'Content-Type': 'multipart/form-data'},
                  }).then((url) => {
                      this.$refs.md.$img2Url(pos, url);
                  })*/
            },
            change(value, render) {
                // render 为 markdown 解析后的结果
                this.postForm.contentHtml = render;
            },
            submitORedit(){
                if (this.postForm.id == null || this.postForm.id == ""){
                    this.submitArticle()
                }else {
                    this.editArticle()
                }
            },
            editArticle(){
                this.$confirm('确定修改文章内容吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    articleApi.editArticle(this.postForm).then(res=>{
                        if (res.success){
                            this.$message.success('文章修改成功！')
                            this.$router.push({path:"/article_list"})
                            localStorage.setItem('ms_isCloseArticle', "true"); //设置窗口可以正常关闭
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消'
                    });
                });
            },
            submitArticle() {

                this.$confirm('确定提交文章？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    articleApi.addArticle(this.postForm).then(res => {
                        if (res.success) {
                            this.$message.success('文章添加成功！')
                            this.$router.push({path:"/article_list"})
                            localStorage.setItem('ms_isCloseArticle', "true"); //设置窗口可以正常关闭
                        } else {
                            this.$message.error(res.message);
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消'
                    });
                });
            },
        },
        watch:{
            //判断文本域是否发生更改
            'postForm.title':{
                handler(){
                    localStorage.setItem('ms_isCloseArticle', "false")//不可以正常关闭
                }
            },
            'postForm.synopsis':{
                handler(){
                    localStorage.setItem('ms_isCloseArticle', "false")//不可以正常关闭
                }
            },
            'postForm.author':{
                handler(){
                    localStorage.setItem('ms_isCloseArticle', "false")//不可以正常关闭
                }
            },
            'postForm.content':{
                handler(){
                    localStorage.setItem('ms_isCloseArticle', "false")//不可以正常关闭
                }
            },
        },
    }
</script>
<style scoped>
    .pre-img{
        width: 100px;
        height: 100px;
        background: #f8f8f8;
        border: 1px solid #eee;
        border-radius: 5px;
    }
    .crop-demo{
        display: flex;
        align-items: flex-end;
    }
    .crop-demo-btn{
        position: relative;
        width: 100px;
        height: 40px;
        line-height: 40px;
        padding: 0 20px;
        margin-left: 30px;
        background-color: #409eff;
        color: #fff;
        font-size: 14px;
        border-radius: 4px;
        box-sizing: border-box;
    }
    .crop-input{
        position: absolute;
        width: 100px;
        height: 40px;
        left: 0;
        top: 0;
        opacity: 0;
        cursor: pointer;
    }
    .editor-btn {
        margin-top: 20px;
    }
    >>> .lang-language{
        background-color: #8a6de9;
    }
</style>
