<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">
                <mallki class-name="mallki-text" text="Tingke博客后台管理系统" />
            </div>
            <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick" :stretch="true" >
                <el-tab-pane label="用户名密码登陆"  name="first" >
                <el-form :model="paramName" :rules="rules" ref="login" label-width="0px" class="ms-content">
                    <el-form-item prop="name">
                        <el-input v-model="paramName.name" placeholder="请输入用户名">
                            <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="password">

                        <el-input
                                type="password"
                                placeholder="请输入密码"
                                v-model="paramName.password"
                                @keyup.enter.native="submitByName()">
                            <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                        </el-input>
                    </el-form-item>
                    <div class="login-btn">
                        <el-button class="pan-btn tiffany-btn"  @click="submitByName()">
                            登陆
                        </el-button>
                    </div>
                </el-form>
                </el-tab-pane>
                <el-tab-pane label="手机登陆" name="second">
                    <el-form :model="paramPhone" :rules="rules" ref="login" label-width="0px" class="ms-content">
                        <el-form-item prop="phone">
                            <el-input v-model="paramPhone.phone" placeholder="请输入手机号">
                                <el-button slot="prepend" icon="el-icon-lx-mobile"></el-button>
                            </el-input>
                        </el-form-item>
                        <el-form-item prop="code">
                            <el-row :gutter="25">
                                <el-col :span="16">
                            <el-input v-model="paramPhone.code" placeholder="请输入验证码" @keyup.enter.native="submitByPhone()">
                                <el-button slot="prepend" icon="el-icon-lx-commentfill"></el-button>
                            </el-input>
                                </el-col>
                                <el-col :span="6">
                                <el-button type="success" plain @click="sendPhoneCode()">{{codeTest}}</el-button>
                                </el-col>
                            </el-row>

                        </el-form-item>
                        <div class="login-btn">
                                <el-button class="pan-btn tiffany-btn"  @click="submitByPhone()">
                                    登陆
                                </el-button>
                        </div>
                    </el-form>
                </el-tab-pane>
            </el-tabs>

        </div>
    </div>
</template>

<script>
    import loginApi from '../../api/admin/login';
    import mallki from '../../assets/compones/TextHoverEffect/Mallki'


export default {
   components:{
       mallki
   },
    data: function() {
        var checkPhone = (rule, value, callback) => {
            if (!value){
                return callback(new Error('手机不能为空'));
            }else {
                if (!(/^1[34578]\d{9}$/.test(value))){
                    return callback(new Error('请输入正确手机号'));
                }
            }
        }
        return {
            activeName: 'first',
            paramName: {
                name: '',
                password: '',
            },
            paramPhone: {
                phone: '',
                code: '',
            },
            rules: {
                name: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
                phone: { validator: checkPhone, trigger: 'blur' },
            },
            codeTest: '验证码',
            sending: true,
            second: 60,        //倒计时间
        };
    },
    methods: {
        handleClick(tab, event) {
            console.log(tab, event);
        },
        //发送验证码
        sendPhoneCode(){
            var phone = this.paramPhone.phone
            if(!(/^1[34578]\d{9}$/.test(phone))){
                this.$message.error("请输入正确手机号");
                return false;
            }
            loginApi.sendPhoneCode(phone).then(res=>{
                this.sending = false
                this.timeDown()
                //调用倒计时的方法
                if (res.success){
                    this.$message.success('发送成功');
                }else {
                    this.$message.error(res.message);
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
                }
            }, 1000);
        },
        //通过手机号登陆
        submitByPhone(){
            loginApi.loginByPhone(this.paramPhone).then(res=>{
                if (res.success){
                    this.$message.success('登录成功');
                    localStorage.setItem('ms_username', res.queryResponseResultMap.map.name); //设置用户名
                    localStorage.setItem('ms_avatar', res.queryResponseResultMap.map.avatar); //头像
                    localStorage.setItem('ms_id', res.queryResponseResultMap.map.id); //id
                    localStorage.setItem('ms_tag', res.queryResponseResultMap.map.tag); //标签
                    localStorage.setItem('ms_role', res.queryResponseResultMap.map.role); //角色
                    localStorage.setItem('ms_login_time', res.queryResponseResultMap.map.loginTime); //上次登陆时间
                    this.$router.push({path:'/'});
                }else {
                    this.$message.error(res.message);
                    console.log('error submit!!');
                    return false;
                }
            })

        },
        //通过用户名密码登陆
        submitByName() {
            loginApi.loginByName(this.paramName).then(res=>{
                console.log(res)
                if (res.success){
                    this.$message.success('登录成功');
                    localStorage.setItem('ms_username', res.queryResponseResultMap.map.name);
                    localStorage.setItem('ms_avatar', res.queryResponseResultMap.map.avatar);
                    localStorage.setItem('ms_id', res.queryResponseResultMap.map.id);
                    localStorage.setItem('ms_tag', res.queryResponseResultMap.map.tag);
                    localStorage.setItem('ms_role', res.queryResponseResultMap.map.role);
                    localStorage.setItem('ms_login_time', res.queryResponseResultMap.map.loginTime);

                    this.$router.push('/');
                }else {
                    this.$message.error(res.message);
                    console.log('error submit!!');
                    return false;
                }
            })
        },
    },
};
</script>

<style scoped>
.login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-image: url(../../assets/img/login-bg.jpg);
    background-size: 100%;
}
.ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #fff;
    border-bottom: 1px solid #ddd;
}
.ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 350px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.3);
    overflow: hidden;
}
.ms-content {
    padding: 30px 30px;
}
.login-btn {
    text-align: center;
}
.login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
}
.login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #fff;
}
</style>
