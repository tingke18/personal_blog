<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 随笔</el-breadcrumb-item>
                <el-breadcrumb-item v-if="!diaryId">随笔添加</el-breadcrumb-item>
                <el-breadcrumb-item v-else>随笔修改</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <el-form ref="diary" :model="diary" :rules="rules">
            <div class="container">
                <el-form-item prop="title" style="margin-top: -30px">
                    <MDinput v-model="diary.title" :maxlength="100" name="name" required style="width: 50%">
                        标题
                    </MDinput>
                </el-form-item>

                <mavon-editor v-model="diary.content" ref="md" @imgAdd="uploadImage" @change="change"
                              @save="saveDiary()"
                              style="min-height: 600px"/>
                <el-button class="editor-btn" style="margin-top: 20px" type="primary" @click="submitORedit()">提交</el-button>
            </div>
        </el-form>
    </div>
</template>
<script>
    import {mavonEditor} from 'mavon-editor'
    import 'mavon-editor/dist/css/index.css'
    import diaryApi from '../../../api/admin/diary';
    import articleApi from '../../../api/admin/article';
    import MDinput from '../../../assets/compones/MDinput'

    export default {
        data() {
            return {
                diary: {
                    title: '',
                    content: '',
                    contentHtml: ''
                },
                diaryId:'',
                rules: {
                    title: [{ required: true, message: '请输入标题', trigger: 'blur' }]
                },
            };
        },
        created() {

            localStorage.setItem('ms_isCloseDiary',"true") //设置如果没有操作可正常关闭
            localStorage.setItem('ms_isCloseArticle',"false")//设置防干扰
            this.selectDiary()
        },
        components:{
            mavonEditor,
            MDinput
        },
        methods: {

            selectDiary(){

                this.diaryId = this.$route.query.diaryId

                if (this.diaryId == null || this.diaryId == ""){
                    return;
                }
                diaryApi.selectDiaryById(this.diaryId).then(res=>{
                    if (res.success){
                        this.diary = res.object
                    }else {
                        alert(res.message)
                    }
                })
            },

            editDiary(){
                diaryApi.editDiary(this.diary).then(res=>{
                    if (res.success){
                        this.$message({
                            type: 'success',
                            message: '修改成功'
                        });
                        this.$router.push({ path: '/diary_list' });
                    }else{
                        this.$message({
                            type: 'error',
                            message: res.message
                        });
                    }
                })
            },

            submitORedit(){
                if (this.diaryId == null || this.diaryId == ""){
                    this.addDiary()
                }else {
                    this.editDiary()
                }
            },

            change(value, render) {
                // render 为 markdown 解析后的结果
                this.diary.contentHtml = render;
            },

            // 将图片上传到服务器，返回地址替换到md中
            uploadImage(pos, $file) {
                var formdata = new FormData();
                formdata.append('file', $file);
                // 这里没有服务器供大家尝试，可将下面上传接口替换为你自己的服务器接口
                articleApi.uploadImage(formdata).then(res => {
                    if (res.success) {
                        var url = res.object;
                        console.log(url);
                        this.$refs.md.$img2Url(pos, url);
                    }
                });
            },

            saveDiary(){
                articleApi.editArticle(this.postForm).then(res=>{
                    if (res.success){
                        localStorage.setItem('ms_isCloseDiary', "true"); //设置窗口可以正常关闭
                    }
                })
            },

            addDiary() {
                diaryApi.addDiary(this.diary).then(res => {
                    if (res.success) {
                        this.$message({
                            type: 'success',
                            message: '添加成功'
                        });
                        this.$router.push({ path: '/diary_list' });
                    }
                });
            }
        },
        watch:{
            //判断文本域是否发生更改
            'diary.title':{
                handler(){
                    localStorage.setItem('ms_isCloseDiary', "false")//不可以正常关闭
                }
            },
            'diary.content':{
                handler(){
                    localStorage.setItem('ms_isCloseDiary', "false")//不可以正常关闭
                }
            }
        },
    };
</script>
