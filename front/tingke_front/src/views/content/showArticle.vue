
<template>
  <div class="warp">

    <div class="animated fadeIn" style="width: 80%; margin: 0 auto;text-align: left;margin-top: 30px">
      <span style="font-size: 35px;">{{article.title}}</span><br/><br/>

      <span>时间：{{article.createTime}}  | 访问: {{article.visitCount}} </span>
      <hr class="layui-bg-green">
      <div style="background-color: #d1ecf1;font-size: 16px">{{article.synopsis}}</div>
      <article  v-html="article.contentHtml">
       {{article.contentHtml}}
      </article>


    </div>

  </div>
</template>
<script>
  import articleApi from '../../api/article'
  import diaryApi from '../../api/diary'
  import moment from "moment"


  export default {
    data(){
      return{
        articleId: this.$route.query.articleId,
        diaryId: this.$route.query.diaryId,
        article:{
          title:'',
          synopsis:'',
          contentHtml:'',
          modifiedTime:'',
          visitCount:'',
          createTime:''
        },
/*        diary:{
          title:'',
          contentHtml:'',
          visitCount:'',
          modifiedTime:'',
        }*/

      }
    },
    created () {
      this.selectArticleORDiary()
    },
    methods:{
      selectArticleORDiary(){
        if (this.articleId != "" || this.articleId != null){
          articleApi.selectOneById(this.articleId).then(res=>{
            if (res.success){
              this.article = res.object
              this.article.createTime = moment(this.article.createTime).format("YYYY-MM-DD")
            }
          })
        }

        if (this.diaryId != "" || this.diaryId != null){
          diaryApi.selectDiaryById(this.diaryId).then(res=>{
            if (res.success){
              this.article = res.object
              this.article.createTime = moment(this.article.createTime).format("YYYY-MM-DD")
            }
          })
        }
        }


      },



  }
</script>
<style scoped>
  .warp {
    margin: 0 auto;
    width: 80%;
  }


  >>> pre{
    background-color: #c2e7b0;
  }
</style>
