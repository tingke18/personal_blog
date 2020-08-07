<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 图书</el-breadcrumb-item>
                <el-breadcrumb-item v-if="!bookId">图书添加</el-breadcrumb-item>
                <el-breadcrumb-item v-else>图书修改</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class="container">
        <el-form ref="form" :model="book" label-width="80px" size="mini">
            <el-form-item label="图书标题:">
                <el-input v-model="book.title" style="width:500px"></el-input>
            </el-form-item>

            <el-form-item label="图书描述:">
                <el-input v-model="book.description" style="width:500px"></el-input>
            </el-form-item>

            <el-form-item label="图书地址:">
                <el-input v-model="book.url" style="width:500px"></el-input>
            </el-form-item>

            <el-form-item label="图片地址:" v-if="is_link == true" >
                <el-input v-model="book.picture" style="width:500px"></el-input>
            </el-form-item>

            <el-form-item  label="图片封面:" v-else>
                <!-- 课程封面 TODO -->
                <el-upload
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload"
                        :action="'http://localhost:9999/TingkeOss/oss'"
                        field="file"
                        class="avatar-uploader">
                    <img width="60%" height="100%" :src="book.picture" v-if="book.picture"/>
                    <i  class="el-icon-plus avatar-uploader-icon" v-else></i>
                </el-upload>
            </el-form-item>

            <el-form-item size="large">
                <el-button :disabled="saveBtnDisavled" type="primary" @click="saveEdit()" v-if="bookId">立即修改</el-button>
                <el-button :disabled="saveBtnDisavled" type="primary" @click="saveBook()" v-else>立即添加</el-button>
                <el-button type="danger" @click="resetForm()">重置</el-button>
                <el-button class="pan-btn tiffany-btn" @click="is_link = !is_link"  v-if="is_link == true">
                    图片封面添加
                </el-button>
                <el-button class="pan-btn pink-btn" @click="is_link = !is_link"  v-else>
                    图片链接添加
                </el-button>
            </el-form-item>
        </el-form>

        </div>
    </div>
</template>
<script>
    import bookApi from '../../../api/admin/book';
    import linkApi from '../../../api/admin/link';

    export default {
        data(){
            return{
                is_link: true,
                saveBtnDisavled:false,
                book:{
                    title:'',
                    description:'',
                    picture:'',
                    url:''
                },
                bookId: ''
            }
        },
        created() {
            this.bookId = this.$route.query.bookId

            this.selectBook()
        },
        methods:{

            //保存编辑
            saveEdit(){
                bookApi.editBook(this.book).then(res=>{

                    if (res.success){

                        this.$message.success("修改成功!")
                        this.$router.push({path:'/book_list'})

                    }else {
                        this.$message.error(res.message);
                    }

                })
            },
            //提交保存
            saveBook(){
                bookApi.addBook(this.book).then(res=>{
                    if (res.success){

                        this.$message.success("添加成功!")
                        this.$router.push({path:'/book_list'})

                    }else {
                        this.$message.error(res.message);
                    }
                })
            },

            //如果存在bookid查询
            selectBook(){
                if (this.bookId == null || this.bookId == ""){
                    return;
                }
                bookApi.selectBook(this.bookId).then(res=>{
                    if (res.success){
                        this.book = res.object;
                    }else {
                        console.log(res.message)
                    }

                })
            },

            resetForm(){
                this.book = {}
            },

            //上传成功回掉
            handleAvatarSuccess(res, file) {
                console.log(res); // 上传响应
                console.log(URL.createObjectURL(file.raw)); // base64编码
                this.book.picture = res.object;
                this.$message.success("封面上传成功!");
            },

            //上传前调用
            beforeAvatarUpload(file) {
                const isJPG = file.type === "image/jpeg";
                const isLt2M = file.size / 1024 / 1024 < 5;
                if (!isJPG) {
                    this.$message.error("上传头像图片只支持 JPG 格式!");
                }
                if (!isLt2M) {
                    this.$message.error("上传头像图片大小不能超过 2MB!");
                }
                return isJPG && isLt2M;
            },
        }
    }
</script>
