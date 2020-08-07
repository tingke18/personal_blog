<template>
  <div class="warp animated fadeIn">

    <div class="animated fadeIn" style="margin: 0 auto;width: 50%;">
      <div class="commentTitle"><i class="layui-icon layui-icon-dialogue"
                                   style="font-size: 30px; color: #009f95;"></i> 欢迎评论
      </div>

      <div v-if="islogin == false">
        <a-popover placement="right" style="margin-top: 20px;float: left" @click="visible = true">
          <template slot="content">
            <p style="text-align: center;font-size: 14px;color: #009f95">点击登陆</p>
          </template>
          <a-avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" class="login" :size="40"
                    icon="user"/>
        </a-popover>

        <div style="float: left;margin-top: 30px;font-size: 17px">
          <span>{{loginInfo.name != '' ? loginInfo.name : loginInfo.email}}</span>
        </div>
      </div>

      <div v-else>
        <a-popover placement="right" style="margin-top: 20px;float: left" @click="outLogin()">
          <template slot="content">
            <p style="text-align: center;font-size: 14px;color: #009f95">点击退出</p>
          </template>
          <a-avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" class="login" :size="40"
                    icon="user"/>
        </a-popover>

        <div style="float: left;margin-top: 30px;font-size: 17px">
          <span>{{loginInfo.name != '' ? loginInfo.name : loginInfo.email}}:</span>
        </div>

      </div>


      <a-form-model :model="CommentContent">
        <div class="animated fadeIn">
          <a-form-model-item style="width: 100%;margin-top: 80px">
            <a-textarea :rows="5" v-model="CommentContent.content" type="textarea"/>
          </a-form-model-item>
          <div style="text-align: right">
            <a-popconfirm
              title="确认提交评论吗?"
              ok-text="Yes"
              cancel-text="No"
              @confirm="submitComment()"
              @cancel="submitCancel()"
            >
            <button class="layui-btn layui-btn-primary">
              评论
            </button>
            </a-popconfirm>
          </div>
        </div>
      </a-form-model>

<!--      <div v-for="comment in commentList" v-bind:key="comment">
        {{comment.frComment.name}}
      </div>-->

      <a-comment class="animated fadeIn" v-for="comment in commentList" v-bind:key="comment">
        <a slot="author">{{comment.frComment.name}}:</a>
        <div style="float:right;text-align: right">{{comment.frComment.createTime}}</div>
        <a-avatar
          slot="avatar"
          src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
          alt="Han Solo"
        />
        <p slot="content" style="text-align: left;">
          {{comment.frComment.content}}
        </p>
        <a-divider v-if="comment.children != ''"/>
        <a-comment class="animated flipInx" v-for="children in comment.children" v-bind:key="children">
          <a slot="author">{{children.name}}<span style="color: #009f95;">回复</span> {{comment.frComment.name}}: </a>
          <div style="float:right;text-align: right">{{comment.frComment.createTime}}</div>
          <a-avatar
            slot="avatar"
            src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
            alt="Han Solo"
          />
          <p slot="content" style="text-align: left">
            {{children.content}}
          </p>
        </a-comment>
        <a-divider />
      </a-comment>

    </div>


    <a-drawer
      title="用户组成/登陆"
      :width="720"
      :height="400"
      :visible="visible"
      :body-style="{ paddingBottom: '80px' }"
      @close="onClose"
    >

      <a-tabs default-active-key="1" style="text-align: center">
        <a-tab-pane key="1">
      <span slot="tab">
        <a-icon type="user"/>
        登陆
      </span>
          <a-form-model ref="ruleForml" :rules="lrules" :model="login" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
            <a-form-model-item label="邮箱" prop="email">
              <a-input
                v-model="login.email"
              />
            </a-form-model-item>
            <a-form-model-item label="密码" prop="password">
              <a-input
                type="password"
                v-model="login.password"
              />
            </a-form-model-item>
            <a-button :style="{ marginRight: '8px' }" @click="login = {}">
              重置
            </a-button>
            <a-button type="primary" @click="userLogin()">
              登陆
            </a-button>
          </a-form-model>

        </a-tab-pane>


        <a-tab-pane key="2">
      <span slot="tab">
        <a-icon type="user-add"/>
        注册
      </span>
          <a-form-model ref="ruleForm" :rules="rules" :model="user" :label-col="{ span: 5 }"
                        :wrapper-col="{ span: 12 }">
            <a-form-model-item label="邮箱" prop="email">
              <a-input
                v-model="user.email"
              />
            </a-form-model-item>

            <a-form-model-item label="用户名" prop="name">
              <a-input
                v-model="user.name"
              />
            </a-form-model-item>

            <a-form-model-item label="密码" prop="password">
              <a-input
                type="password"
                v-model="user.password"
              />
            </a-form-model-item>

            <a-form-model-item label="确认密码" prop="checkPassword">
              <a-input
                type="password"
                v-model="user.checkPassword"
              />
            </a-form-model-item>

            <a-button :style="{ marginRight: '8px' }" @click="user = {}">
              重置
            </a-button>
            <a-button type="primary" @click="addUser()">
              提交
            </a-button>
          </a-form-model>
        </a-tab-pane>
      </a-tabs>
    </a-drawer>

    <a-modal v-model="visibleLogin" title="评论需要登陆" @ok="showLogin()">
      <p>请前往登陆!</p>
    </a-modal>

  </div>
</template>
<script>
  import moment from 'moment'
  import userApi from '../../api/user'
  import commentApi from '../../api/comment'
  import cookie from 'js-cookie'

  export default {
    data () {
      return {
        visibleLogin:false,
        form: this.$form.createForm(this, { name: 'coordinated' }),
        visible: false,
        comments: [],
        submitting: false,
        value: '',
        user: {
          email: '',
          name: '',
          password: '',
          checkPassword: ''
        },
        login: {
          name: '',
          email: '',
          password: ''
        },
        loginInfo: {
          email: '',
          name: '',
        },
        lrules:{
          email: [{
            required: true,
            message: '请输入邮箱',
            trigger: 'blur'
          }],
          password: [
            {
              required: true,
              message: '请输入密码',
              trigger: 'blur'
            },
            {
              min: 6,
              message: '密码最小6位',
              trigger: 'blur'
            },
          ],
        },
        rules: {
          email: [{
            required: true,
            message: '请输入邮箱',
            trigger: 'blur'
          }],
          name: [{
            required: true,
            message: '请输入用户名',
            trigger: 'blur'
          }],
          password: [
            {
              required: true,
              message: '请输入密码',
              trigger: 'blur'
            },
            {
              min: 6,
              message: '密码最小6位',
              trigger: 'blur'
            },
          ],
          checkPassword: [
            {
              required: true,
              message: '请输入确认密码',
              trigger: 'blur'
            },
            {
              min: 6,
              message: '密码最小6位',
              trigger: 'blur'
            },],

        },
        islogin: false,
        commentList:{},
        CommentContent:{
          content:''
        }//评论内容
      }
    },
    created () {
      this.getUserInfo()
      this.selectAllComment()
    },
    methods: {
      //取消添加评论
      submitCancel(){
        this.$message.success('评论已取消')
      },

      showLogin(){
        this.visible = true;
        this.visibleLogin = false
      },

      //用户评论
      submitComment(){

        if (this.CommentContent.content == "" || this.CommentContent.content == null){
          this.$message.error('评论不能为空')
          return;
        }

        var user_str = cookie.get('user_Info')

        if (user_str != null && user_str != ''){
          commentApi.addComment(this.CommentContent).then(res=>{
            if (res.success){
              this.$message.success('评论已添加')
              this.selectAllComment()
              this.CommentContent.content = ''
            }else {
              this.$message.error(res.message)
            }
          })
        }else {

          this.visibleLogin = true

        }
      },

      //用户退出
      outLogin () {
        //设置cookie为空
        cookie.set('user_token', '', { domain: 'localhost' })
        cookie.set('user_Info', '', { domain: 'localhost' })

        this.$message.info('用户已退出')

        this.getUserInfo()
      },

      //显示评论
      selectAllComment(){
        commentApi.selectComment().then(res=>{
          if(res.success){
            this.commentList = res.queryResponseResultList.list
            for (var i = 0;this.commentList.length;i++){
              this.commentList[i].frComment.createTime = moment(this.commentList[i].frComment.createTime).format("YYYY-MM-DD HH:mm:ss")
              if ( this.commentList[i].children != "" &&  this.commentList[i].children != null){
                for (var m = 0;this.commentList[i].children.length;m++ ){
                  this.commentList[i].children[m].createTime = moment(this.commentList[i].children[m].createTime).format("YYYY-MM-DD  HH:mm:ss")
                }
              }
            }
          }
        })
      },

      //添加用户
      addUser () {
        //判断校验
        this.$refs.ruleForm.validate(valid => {
          if (valid) {
        //判断校验
            if (this.user.checkPassword != this.user.password) {
              this.$message.error('两次密码不同')
              return
            }

            userApi.addUser(this.user).then(res => {
              if (res.success) {

                //把token存入cookie中
                cookie.set('user_token', res.object, { domain: 'localhost' })

                this.$message.success('注册成功')

                userApi.queryUserById().then(response => {
                  if (response.success) {
                    //将用户信息记录cookie
                    cookie.set('user_Info', response.object, { domain: 'localhost' })

                    this.getUserInfo()

                    this.user = {}

                    //跳转页面
                    this.onClose()
                  }
                })
              } else {
                this.$message.error(res.message)
              }
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },

      //用户登陆
      userLogin () {

        //判断校验
        this.$refs.ruleForml.validate(valid => {
          if (valid) {
            userApi.userLogin(this.login).then(res => {
              if (res.success) {
                //把token存入cookie中
                cookie.set('user_token', res.object, { domain: 'localhost' })

                this.$message.success('登陆成功')

                userApi.queryUserById().then(response => {
                  if (response.success) {
                    //将用户信息记录cookie
                    cookie.set('user_Info', response.object, { domain: 'localhost' })

                    this.getUserInfo()

                    this.login = {}

                    //跳转页面
                    this.onClose()
                  }
                })
              } else {
                this.$message.error(res.message)
              }
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },

      //获取用户信息
      getUserInfo () {
        var user_str = cookie.get('user_Info')

        if (user_str != null && user_str != '') {
          this.loginInfo = JSON.parse(user_str)
          this.islogin = true//已登陆
        } else {
          this.islogin = false//未登录
          this.loginInfo = {}
        }
      },
      onClose () {
        this.visible = false
      },
    },
  }
</script>
<style>
  .warp {
    margin: 0 auto;
    width: 80%;
  }

  .login {
    float: left;
    cursor: pointer;
  }

  .login:hover {
    float: left;
    transform: scale(1.05);
    transition: transform 0.4s, opacity 0.4s;
  }


  .commentTitle {
    text-align: left;
    margin-top: 30px;
    font-size: 30px;
    color: #009f95;
  }
</style>
