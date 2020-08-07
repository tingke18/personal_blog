<template>
  <div class="header">


<!--    <a-menu  v-model="current" mode="horizontal">

      <a-menu-item key="mail"><router-link to="/index"><span style="font-size: 22px">首页</span></router-link> </a-menu-item>
      <a-menu-item key="blog" ><router-link to="/blog"><span style="font-size: 22px">博客</span></router-link></a-menu-item>
      <a-menu-item key="diary" ><router-link to="/diary"><span style="font-size: 22px">随记</span></router-link></a-menu-item>
      <a-menu-item key="link" ><router-link to="/links"><span style="font-size: 22px">链接</span></router-link></a-menu-item>
      <a-menu-item key="about" ><router-link to="/about"><span style="font-size: 22px">关于</span></router-link></a-menu-item>

    </a-menu>-->
    <ul class="layui-nav " style="opacity:0.9;" lay-filter="">
      <img src="../assets/title_tag.png" style="margin-left: -400px">
      <li class="layui-nav-item layui-this" style="left: 250px"  v-if="name == 'index'"><router-link to="/index"><span style="font-size: 22px;color: #009688">首页</span></router-link></li>
      <li class="layui-nav-item" style="left: 250px"  v-else><router-link to="/index"><span style="font-size: 22px;color: #009688">首页</span></router-link></li>

      <li class="layui-nav-item layui-this" style="left: 270px" v-if="name == 'blog'"><router-link to="/blog"><span style="font-size: 22px;color: #009688">博客</span></router-link></li>
      <li class="layui-nav-item" style="left: 270px" v-else><router-link to="/blog"><span style="font-size: 22px;color: #009688">博客</span></router-link></li>

      <li class="layui-nav-item layui-this" style="left: 290px" v-if="name == 'diary'"><router-link to="/diary"><span style="font-size: 22px;color: #009688">随记</span></router-link></li>
      <li class="layui-nav-item" style="left: 290px" v-else><router-link to="/diary"><span style="font-size: 22px;color: #009688">随记</span></router-link></li>

      <li class="layui-nav-item layui-this" style="left: 310px" v-if="name == 'comment'"><router-link to="/comment"><span style="font-size: 22px;color: #009688">留言</span></router-link></li>
      <li class="layui-nav-item" style="left: 310px" v-else><router-link to="/comment"><span style="font-size: 22px;color: #009688">留言</span></router-link></li>

      <li class="layui-nav-item layui-this" style="left: 330px" v-if="name == 'links'"><router-link to="/links"><span style="font-size: 22px;color: #009688">链接</span></router-link></li>
      <li class="layui-nav-item" style="left: 330px" v-else><router-link to="/links"><span style="font-size: 22px;color: #009688">链接</span></router-link></li>

      <li class="layui-nav-item layui-this" style="left: 350px" v-if="name == 'about'" ><router-link to="/about"><span  style="font-size: 22px;color: #009688">关于</span></router-link></li>
      <li class="layui-nav-item" style="left: 350px" v-else><router-link to="/about" ><span style="font-size: 22px;color: #009688" >关于</span></router-link></li>
    </ul>


    </div>
</template>
<script>


  export default {
    data() {
      return {
        current: ['mail'],
        name: this.$route.name
      };
    },
    created () {

      //注意：导航 依赖 element 模块，否则无法进行功能性操作
      window.layui.use('element', function () {
        var element = window.layui.element;
      });
    },
    mounted () {
      this.name = this.$route.name
    },
    components:{

    }
  };
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  #warp{margin:0 auto;width:1000px}
</style>
