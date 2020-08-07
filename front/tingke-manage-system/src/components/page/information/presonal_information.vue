<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 个人信息
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-form :ref="informationEdit"  label-width="130px" >
                <el-form-item>
                <pan-thumb :image="image" style="margin-left: -50px" />
                    <el-button type="primary" icon="el-icon-upload" style="position: absolute;bottom: 15px;margin-left: 40px;" @click="imagecropperShow = true">
                        修改头像
                    </el-button>

                    <image-cropper
                            v-show="imagecropperShow"
                            :key="imagecropperKey"
                            :width="300"
                            :height="300"
                            :url="'TingkeOss/oss/'+id"
                            field="file"
                            @close="close"
                            @crop-upload-success="cropSuccess"
                    />
                </el-form-item>


                <div v-if="is_edit == false">
                <el-form-item label="用户名：" >
                    <span>{{information.name}}</span>
                </el-form-item>
                <el-form-item label="手机号：" >
                    <span>{{information.phone}}</span>
                </el-form-item>
                <el-form-item label="角色：" >
                    <span >{{information.role == "admin"?"超级管理员":"管理员"}}</span>
                </el-form-item>
                <el-form-item label="签名：" >
                    <span>{{information.tag}}</span>
                </el-form-item>
                <el-form-item label="创建时间：" >
                    <span>{{information.createTime}}</span>
                </el-form-item>
                <el-form-item label="修改时间：" >
                    <span>{{information.modifiedTime}}</span>
                </el-form-item>
                </div>

                <div v-if="is_edit == true">
                    <el-form-item label="用户名：" style="width: 600px">
                        <el-input v-model="informationEdit.name" style="width: 600px"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号：" >
                        <el-input v-model="informationEdit.phone" style="width: 600px"></el-input>
                    </el-form-item>
                    <el-form-item label="角色：" >
                        <el-input  v-model="informationEdit.role" style="width: 600px"></el-input><span>(admin:超级管理员||order:管理员)</span>
                    </el-form-item>
                    <el-form-item label="签名：" >
                        <el-input v-model="informationEdit.tag" style="width: 600px"></el-input>
                    </el-form-item>
                    <el-form-item label="创建时间：" >
                        <span>{{informationEdit.createTime}}</span>
                    </el-form-item>
                    <el-form-item label="修改时间：" >
                        <span>{{informationEdit.modifiedTime}}</span>
                    </el-form-item>
                </div>

                <div style="margin-left: 50px">
                    <el-button class="pan-btn tiffany-btn"  @click="editUser()"  v-if="is_edit == true">
                        提交
                    </el-button>

                    <el-button class="pan-btn pink-btn"   @click="editModel()" v-if="is_edit == false">
                        修改
                    </el-button>

                    <el-button class="pan-btn yellow-btn"  @click="is_edit=!is_edit" v-if="is_edit == true">
                        取消
                    </el-button>

                    <el-button  style="margin-left: 100px" class="pan-btn green-btn"  @click="dialogVisible = true" >
                        密码修改
                    </el-button>
                </div>

            </el-form>

        </div>


        <el-dialog
                title="密码修改"
                :visible.sync="dialogVisible"
                width="30%">
            <el-form ref="form" label-width="80px">
                <el-form-item prop="email" label="邮箱">
                    <el-row>
                        <el-col :span="16">
                            <el-input v-model="passwordVo.email" :disabled="emailIsDisable"  placeholder="请输入邮箱" >
                            </el-input>
                        </el-col>
                        <el-col :span="6">
                            <el-button  type="success" plain @click="sendEmailCode()" style="margin-left: 30px">{{codeTest}}</el-button>
                        </el-col>
                    </el-row>
                </el-form-item>
                <el-form-item  prop="code" label="校验码">
                    <el-input v-model="passwordVo.code" placeholder="请输入校验码"></el-input>
                </el-form-item>
                <el-form-item prop="prePassword" label="原始密码">
                    <el-input v-model="passwordVo.prePassword" placeholder="请输入原始" show-password></el-input>
                </el-form-item>
                <el-form-item prop="currentPassword" label="修改密码">
                    <el-input v-model="passwordVo.currentPassword" placeholder="请输入密码" show-password></el-input>
                </el-form-item>
                <el-form-item prop="checkPassword" label="确认密码">
                    <el-input v-model="passwordVo.checkPassword" placeholder="请输确认密码" show-password @keyup.enter.native="saveORedit()"></el-input>
                </el-form-item>
                <el-form-item style="margin-left: 180px">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="editPassword()" >确 定</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>


    </div>
</template>
<script>
    import ImageCropper from '../../../assets/compones/ImageCropper'
    import PanThumb from '../../../assets/compones/PanThumb'
    import informationApi from '../../../api/admin/information'
    import moment from "moment"
    import eventApi from "../../../api/admin/event";

    export default {
        components:{
            ImageCropper,
            PanThumb
        },
        data(){
            return{
                rules: {
                    email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
                    code: [{ required: true, message: '请输入校验码', trigger: 'blur' }],
                    prePassword: [{ required: true, message: '请输入原始密码', trigger: 'blur' }],
                    currentPassword: [{ required: true, message: '请输入密码', trigger: 'blur' }],
                    checkPassword: [{ required: true, message: '请输入确认密码', trigger: 'blur' }],
                },
                emailIsDisable:false,
                passwordVo:{
                    id:localStorage.getItem('ms_id'), //用户id
                    email:"",
                    prePassword:"",
                    currentPassword:"",
                    checkPassword:"",
                    code:""
                },
                dialogVisible:false,
                is_edit: false,
                BASE_API: process.env.BASE_API,
                imagecropperShow: false,
                imagecropperKey: 0,//修改头像防止头像在修改区回显的记录值
                image: '',
                information:{
                    name:"",
                    tag:"",
                    phone:"",
                    createTime:"",
                    role:"",
                    loginTime:"",
                    email:"",
                    modifiedTime:""
                },
                informationEdit:{
                    id:localStorage.getItem('ms_id'), //用户id
                    name:"",
                    tag:"",
                    phone:"",
                    createTime:"",
                    role:"",
                    loginTime:"",
                    email:"",
                    modifiedTime:""
                },
                id: localStorage.getItem('ms_id'), //用户id
                codeTest:"验证码",
                second:60//倒计时
            }
        },
        created() {
            this.selectPresonalInformation()
        },
        methods:{
            sendEmailCode(){//发送邮箱验证码
                if (this.passwordVo.email == null || this.passwordVo.email == ""){
                    this.$message.wraning('请输入邮箱')
                }
                this.emailIsDisable = true //邮箱输入框禁止
                this.timeDown()
                informationApi.sendEmailCode(this.passwordVo.id,this.passwordVo.email).then(res=>{
                    if (res.success){
                        this.$message.success('邮箱验证码发送成功请注意查验')

                    }else {
                        this.$message.error(res.message)
                    }
                })

            },
            //倒计时
            timeDown() {
                let result = setInterval(() => {
                    --this.second;
                    this.codeTest = this.second
                    if (this.second < 1) {
                        clearInterval(result);
                        this.sending = true;
                        //this.disabled = false;
                        this.second = 60;
                        this.codeTest = "验证码"
                        this.emailIsDisable = false
                    }
                }, 1000);
            },
            editPassword(){//修改密码
                informationApi.editPassword(this.passwordVo).then(res=>{
                    if (res.success){
                        this.$message.success('密码修改成功，请重新登陆')

                        localStorage.removeItem('ms_username');
                        localStorage.removeItem('ms_avatar'); //头像
                        localStorage.removeItem('ms_id'); //id
                        localStorage.removeItem('ms_tag'); //标签
                        localStorage.removeItem('ms_role'); //角色
                        localStorage.removeItem('ms_login_time'); //上次登陆时间
                        this.$router.push('/login');

                        this.$router.push({path:'/login'});

                    }else {
                        this.$message.error(res.message)
                    }
                })
            },

            //编辑模式
            editModel(){

                this.informationEdit.name = this.information.name
                this.informationEdit.phone = this.information.phone
                this.informationEdit.tag = this.information.tag
                this.informationEdit.role = this.information.role
                this.informationEdit.loginTime = this.information.loginTime
                this.informationEdit.email = this.information.email
                this.informationEdit.createTime = this.information.createTime
                this.informationEdit.modifiedTime = this.information.modifiedTime

                this.is_edit = !this.is_edit

            },
            editUser(){//编辑用户信息
                this.$confirm('确定要修改吗!修改好将无法返回', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.informationEdit.createTime = null  //设置创建时间与修改时间否则会报错
                    this.informationEdit.modifiedTime = null
                    this.informationEdit.loginTime = null

                    informationApi.editUser(this.informationEdit).then(res=>{
                        if (res.success){
                            this.$message.success('修改信息成功');
                            this.information = res.object
                            this.selectPresonalInformation()//信息回显
                            this.is_edit = false //设置观看模式
                        }else {
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
            selectPresonalInformation(){
                informationApi.selectPresonalInformation(this.id).then(res=>{
                    if (res.success){
                        this.information = res.object
                        this.image = res.object.avatar


                        localStorage.setItem('ms_avatar', res.object.avatar);
                        localStorage.setItem('ms_username', this.information.name);
                        localStorage.setItem('ms_tag',this.information.tag);
                        localStorage.setItem('ms_role', this.information.role);
                        localStorage.setItem('ms_login_time', this.information.loginTime);

                        this.information.createTime = moment(this.information.createTime).format("YYYY-MM-DD HH:mm:ss")
                        this.information.modifiedTime = moment(this.information.modifiedTime).format("YYYY-MM-DD HH:mm:ss")
                    }
                })

            },
            close(){
                this.imagecropperShow = false; //关闭头像弹框

                // 上传失败后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
                this.imagecropperKey = this.imagecropperKey + 1;
            },
            cropSuccess(){

                this.selectPresonalInformation()


                //上传成功关闭窗口
                this.imagecropperShow = false

                this.$message.success('修改头像成功');

                // 上传成功后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
                this.imagecropperKey = this.imagecropperKey + 1;
            }
        }
    }
</script>
<style>
    .mixin-components-container {
        background-color: #f0f2f5;
        padding: 30px;
        min-height: calc(100vh - 84px);
    }
    .component-item{
        min-height: 100px;
    }
</style>
