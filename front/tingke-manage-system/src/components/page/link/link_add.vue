<template>
    <div>

        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 链接</el-breadcrumb-item>
                <el-breadcrumb-item v-if="!linkId">链接添加</el-breadcrumb-item>
                <el-breadcrumb-item v-else>链接修改</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class="container">

        <el-form ref="form" :model="link" label-width="80px" size="mini">
            <el-form-item label="链接标题:">
                <el-input v-model="link.title" style="width:500px"></el-input>
            </el-form-item>

            <el-form-item label="链接描述:">
                <el-input v-model="link.description" style="width:500px"></el-input>
            </el-form-item>

            <el-form-item label="链接分类:">
                <el-select v-model="link.categoryId" placeholder="链接分类" style="width:100px">
                    <el-option v-for="category in linkCategory" :label="category.category"  :value="category.id" ></el-option>
                </el-select>
                <el-button type="success" @click="dialogFormVisible = true" style="margin-left: 20px">编辑</el-button>
            </el-form-item>

            <el-form-item label="链接地址:">
                <el-input v-model="link.url" style="width:500px"></el-input>
            </el-form-item>

            <el-form-item  label="链接封面:">
                <!-- 课程封面 TODO -->
                <el-upload
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload"
                        :action="'http://localhost:9999/TingkeOss/oss'"
                        field="file"
                        class="avatar-uploader">
                    <img width="60%" height="100%" :src="link.avatar" v-if="link.avatar"/>
                    <i  class="el-icon-plus avatar-uploader-icon" v-else></i>
                </el-upload>
            </el-form-item>

            <el-form-item size="large">
                <el-button :disabled="saveBtnDisavled" type="primary" @click="saveEdit()" v-if="linkId">立即修改</el-button>
                <el-button :disabled="saveBtnDisavled" type="primary" @click="saveLink()" v-else>立即添加</el-button>
                <el-button type="danger" @click="resetForm()">重置</el-button>
            </el-form-item>
        </el-form>


        <el-dialog
                title="链接分类编辑"
                :visible.sync="dialogFormVisible"
                :before-close="handleClose"
                width="30%">
            <el-tabs v-model="activeName" @tab-click="handleClick" >
                <el-tab-pane label="添加" name="first">

                    <el-form ref="form" :model="linkEditCategory">
                        <el-form-item label="分类:" >
                           <el-input v-model="linkEditCategory.category" placeholder="添加分类"></el-input>
                        </el-form-item>
                            <el-button type="primary" @click="addLinkCategory()">添加</el-button>
                    </el-form>

                </el-tab-pane>

                <el-tab-pane label="修改" name="second">

                    <el-form ref="form" :model="linkEditCategory" label-width="80px">

                        <el-form-item label="选择分类:" >
                        <el-select v-model="linkEditCategory.id" placeholder="选择分类" >
                            <el-option v-for="category in linkCategory" :label="category.category"  :value="category.id" ></el-option>
                        </el-select>
                        </el-form-item>

                        <el-form-item label="修改分类:" >
                            <el-input v-model="linkEditCategory.category" placeholder="修改分类"></el-input>
                        </el-form-item>

                        <el-button type="warning" @click="editLinkCategory()">添加</el-button>
                    </el-form>

                </el-tab-pane>
                <el-tab-pane label="删除" name="third">
                    <el-form ref="form" :model="linkEditCategory" label-width="80px">

                        <el-form-item label="选择分类:" >
                            <el-select v-model="linkEditCategory.id" placeholder="选择分类" >
                                <el-option v-for="category in linkCategory" :label="category.category"  :value="category.id" ></el-option>
                            </el-select>
                        </el-form-item>

                        <el-button type="danger"  @click="deleteLinkCategory()">删除</el-button>

                    </el-form>
                </el-tab-pane>
            </el-tabs>

        </el-dialog>
        </div>
    </div>
</template>
<script>
    import linkApi from '../../../api/admin/link';
    import MdInput from '../../../assets/compones/MDinput/index';

    export default {
        components: { MdInput },
        data(){
            return{
                activeName: 'first',
                dialogFormVisible:false,
                link: {
                    saveBtnDisavled:false,
                    avatar: '',
                    title: '',
                    description: '',
                    url: '',
                    categoryId: ''
                },
                linkCategory:{
                    id:'',
                    category:''
                },
                linkEditCategory:{
                    id:'',
                    category:''
                },
                linkId:''
            }
        },
        created() {
            this.linkId = this.$route.query.linkId

            this.selectCategory()
            this.selectLink()
        },
        methods:{
            //删除分类
            deleteLinkCategory(){
                linkApi.deleteLinkCategory(this.linkEditCategory.id).then(res=>{
                    if (res.success){
                        this.$message.success("删除成功!")
                        this.dialogFormVisible = false
                        this.linkEditCategory = {}
                        this.selectCategory()
                    }else {
                        this.$message.error(res.message)
                    }
                })
            },

            //编辑分类
            editLinkCategory(){
               linkApi.editLinkCategory(this.linkEditCategory).then(res=>{
                   if (res.success){
                       this.$message.success("修改成功!")
                       this.dialogFormVisible = false
                       this.linkEditCategory = {}
                       this.selectCategory()
                   }else {
                       this.$message.error(res.message)
                   }
               })
            },
            //添加分类
            addLinkCategory(){
              linkApi.addLinkCategory(this.linkEditCategory.category).then(res=>{
                  if (res.success){
                      this.$message.success("添加成功!")
                      this.dialogFormVisible = false
                      this.linkEditCategory = {}
                      this.selectCategory()
                  }else {
                      this.$message.error(res.message)
                  }
              })
            },
            resetForm(){
              this.link = {}
            },
            //保存修改
            saveEdit(){
                linkApi.editLink(this.link).then(res=>{

                    if (res.success){

                        this.$message.success("修改成功!")
                        this.$router.push({path:'/link_list'})

                    }else {
                        this.$message.error(res.message);
                    }

                })
            },
            //保存添加
            saveLink(){
                linkApi.addLink(this.link).then(res=>{
                    if (res.success){

                        this.$message.success("添加成功!")
                        this.$router.push({path:'/link_list'})

                    }else {
                        this.$message.error(res.message);
                    }
                })
            },

            //上传成功回掉
            handleAvatarSuccess(res, file) {
                console.log(res); // 上传响应
                console.log(URL.createObjectURL(file.raw)); // base64编码
                this.link.avatar = res.object;
                this.$message.success("封面上传成功!");
            },

            //上传前调用
            beforeAvatarUpload(file) {
                const isPNG = file.type === "image/png";
                const isLt2M = file.size / 1024 / 1024 < 5;
                if (!isPNG) {
                    this.$message.error("上传头像图片只支持 PNG 格式!");
                }
                if (!isLt2M) {
                    this.$message.error("上传头像图片大小不能超过 2MB!");
                }
                return isPNG && isLt2M;
            },

            //如果存在linkid查询
            selectLink(){
                if (this.linkId == null || this.linkId == ""){
                    return;
                }
                linkApi.selectLinkById(this.linkId).then(res=>{
                    if (res.success){
                        this.link = res.object;
                    }else {
                        console.log(res.message)
                    }

                })
            },

            //查询分类
            selectCategory(){
                linkApi.selectLinkCategory().then(res=>{
                    if (res.success){
                        this.linkCategory = res.queryResponseResultList.list;
                    }else {
                        console.log(res.message)
                    }
                })
            }

        }
    }
</script>
