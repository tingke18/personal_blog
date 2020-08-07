<template>
  <div class="warp" >

    <div style="float: left;width: 75%;margin-top: 20px">
      <a-card style="text-align: left;height: 800px" >
        <i class="layui-icon layui-icon-list" style="font-size: 30px;color: #009f95;"> 最新博客</i>
        <a-divider/>

        <router-link v-for=" article in articles"  v-bind:key="article" :to="{
              path:'/showArticle',
              query:{
                  articleId: article.id
              }}">
        <div class="blogList" >
          <div class="layui-anim layui-anim-scale">
              <p style="font-size: 20px;margin-bottom: 0px" >{{article.title}}</p>
              <span style="font-size: 13px">时间：{{article.createTime}} | 浏览：{{article.visitCount}} </span>
              <p style="margin-top: 10px">{{article.synopsis}}</p>
            <a-divider/>
          </div>
        </div>
        </router-link>


      </a-card>
    </div>
    <!--监听判断div位置-->
    <div :class="searchBarFixed == true ? 'isFixed animated bounceInDown' : 'tab'" style="text-align: left">
      <a-card title="搜索" style="width: 300px;left: 20px;" class="layui-anim layui-anim-scaleSpring" hoverable>
        <a-form-model ref="ruleForml" :model="articleVo"  layout="inline">
          <a-form-model-item>
            <a-input v-model="articleVo.title" style="width: 210px"  placeholder="please input you search"/>
          </a-form-model-item>
          <a-icon class="arSearch" type="search" @click="onSearch()" style="margin-top: 12px;font-size: 15px"/>
        </a-form-model>
      </a-card>

    </div>

    <div style="float: left;width: 25%;text-align: left">
      <a-card style="width: 300px;left: 20px;top: 20px;background-color: cadetblue;color: #555555"
              class="layui-anim layui-anim-scaleSpring" hoverable>
        <p><span style="font-size: 20px"> <strong>个人名片</strong></span></p>
        <span>网名: xxxx</span><br/>
        <span>职业: xxxx</span><br/>
        <span>现居: 广东省-深圳</span><br/>
        <span>Email: xxxx</span><br/>
        <span>简介: xxxxxxxxxxx</span><br/>
        <i class="layui-icon  layui-icon-more" style="font-size: 40px; color: #2c3e50;"/>
        <i class="layui-icon  layui-icon-more" style="font-size: 40px; color: #2c3e50;"/>
        <i class="layui-icon  layui-icon-more" style="font-size: 40px; color: #2c3e50;"/>
        <i class="layui-icon  layui-icon-more" style="font-size: 40px; color: #2c3e50;"/>
        <i class="layui-icon  layui-icon-right" style="font-size: 40px; color: #2c3e50;"/>
        <a-popover>
          <i class="layui-icon  layui-icon-login-wechat" style="font-size: 40px; color: #5FB878;"></i>
          <img slot="content" src="../../assets/weixin.jpg" style="width: 100px;height: 100px"/><br/>
        </a-popover>
      </a-card>
    </div>

    <div style="float: left;width: 25%;text-align: left">
      <a-card title="随记" style="width: 300px;left: 20px;top: 40px"
              class="layui-anim layui-anim-scaleSpring" hoverable >

        <p class="diaryList" v-for="diary in diarys"  v-bind:key="diary">
          <router-link :to="{
              path:'/showArticle',
              query:{
                  diaryId: diary.id
              }}">
            <a>{{diary.title}}</a>
          </router-link>
        </p>

      </a-card>
    </div>

    <div style="float: left;width: 75%;margin-top:50px;background-color: #eeeeee; padding: 20px;">
      <div style="background: #eeeeee;text-align: left">
        <i class="layui-icon layui-icon-read" style="font-size: 30px; color: #009f95;"> 推荐图书</i>
        <router-link :to="{
              path:'/showBook'}">
          <div style="float: right"><span class="moreBook">更多</span></div>
        </router-link>


      </div>
      <a-divider/>
      <a-row :gutter="[16,16]">
        <a-col :span="6" class="picList" v-for="book in books" v-bind:key="book">
          <a-card hoverable class="animated bounce" @click="showBook(book.url)">
            <img
              slot="cover"
              alt="example"
              :src="book.picture"
              style="height: 300px"
            />
            <a-card-meta :title="book.title">
              <template slot="description">
                <span class="bookShow">{{book.description}}</span>
              </template>
            </a-card-meta>
          </a-card>
        </a-col>
      </a-row>
    </div>

    <div style="margin-top: 20px;float: left;width: 100%" v-if="!index">
      Copyright © 2020 Tingke All Rights Reserved V.1.0.0 备案号:xxx
    </div>

  </div>
</template>
<script>
import articleApi from '../../api/article'
import moment from "moment"
import bookApi from '../../api/book'
import diaryApi from '../../api/diary'

  export default {
    data () {
      return {
        articleVo:{
          title:''
        },
        searchBarFixed: false,
        offsetTop: 50,
        articles:{
          id:'',
          title:'',
          synopsis:'',
          modifiedTime:'',
          createTime:'',
          visitCount:''
        },
        books:{
          title:'',
          description:'',
          picture:'',
          url:''
        },
        diarys:{
          id:'',
          title:''
        }
      }
    },
    created () {

      this.selectArticle()
      this.selectBook()
      this.selecDiary()


      window.layui.use('carousel', function () {
        var carousel = window.layui.carousel
        //建造实例
        carousel.render({
          elem: '#carousel'
          ,
          width: '70%' //设置容器宽度
          ,
          arrow: 'hover' //始终显示箭头
          //,anim: 'updown' //切换动画方式
        })
      })
    },
    components: {

    },
    mounted () {


      /*监听滚动事件，当offsetTop到达指定位置，触发滚动*/
      window.addEventListener('scroll', this.handleScroll)
      this.offsetTop = this.$refs.fixedBar.offsetTop


    },
    destroyed () {
      /*销毁监听事件*/
      window.removeEventListener('scroll', this.handleScroll)
    },
    methods: {

      onSearch(){
        if (this.articleVo.title == "" || this.articleVo.title == null){
          this.selectArticle()
          return;
        }
        articleApi.searchArticle(1,5,this.articleVo).then(res=>{
          if (res.success){
            this.articles = res.queryResponseResultList.list

            for (var i = 0;this.articles.length;i++){
              this.articles[i].createTime = moment(this.articles[i].createTime).format("YYYY-MM-DD")
            }

          }else{
            this.$message.error('无相关内容')
          }
        })
      },

      //查询显示随记
      selecDiary(){
        diaryApi.selecDiary(1,5).then(res=>{
          if (res.success){
            this.diarys = res.queryResponseResultList.list
          }
        })
      },

      //查询显示图书
      showBook(url){
        window.open(url,'_blank')
      },

      //查询推荐图书
      selectBook(){
        bookApi.selectPageBook(1,8).then(res=>{
          if (res.success){
            this.books = res.queryResponseResultList.list
          }
        })
      },

      //查询文章
      selectArticle(){
        articleApi.selectArticle(1,5).then(res=>{
          if (res.success){
            this.articles = res.queryResponseResultList.list

            for (var i = 0;this.articles.length;i++){
              this.articles[i].createTime = moment(this.articles[i].createTime).format("YYYY-MM-DD")
            }
          }
        })

      },
      /*滚动事件*/
      handleScroll () {
        const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
        scrollTop > this.offsetTop ? this.searchBarFixed = true : this.searchBarFixed = false
      }
    }
  }
</script>
<style>
  .moreBook{
    font-size: 17px;
  }

  .arSearch:hover{
    transform: scale(1.2);
    color:  #009f95;
    transition: transform 0.4s, opacity 0.4s;
  }

  .moreBook:hover{
    color: #009f95;
    transform: scale(1.02);
  }
  /* For demo */
  .ant-carousel >>> .slick-slide {
    text-align: center;
    height: 160px;
    line-height: 160px;
    background: #364d79;
    overflow: hidden;
  }

  .bookShow{
    display:-webkit-box;
    -webkit-box-orient:vertical;
    -webkit-line-clamp:3;
    overflow:hidden;
  }

  .picList:hover{
    transform: scale(1.02);
    transition: transform 0.4s, opacity 0.4s;
  }

  .diaryList{
    font-size: 18px
  }

  .diaryList:hover{
    transform: scale(1.02);
    transition: transform 0.4s, opacity 0.4s;
    color: #009f95;
  }

  .blogList {
    padding-left: 15px;
  }

  .blogList:hover {
    transform: scale(1.02);
    transition: transform 0.4s, opacity 0.4s;
    background-color: #d1ecf1;
  }

  .ant-carousel >>> .slick-slide h3 {
    color: #fff;
  }

  .warp {
    margin: 0 auto;
    width: 80%;
  }


  a {
    color: #2c3e50;
  }

  a:hover {
    color: #009f95;
  }

  .tab {
    float: right;
    width: 25%;
    right: 5%;
    margin-top: 20px;
    text-align: right;
  }

  .isFixed {
    float: right;
    width: 25%;
    margin-top: 20px;
    position: fixed;
    z-index: 999;
    right: 5%;
    text-align: right;
  }
</style>
