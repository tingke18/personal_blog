<template>
  <div class="warp">
    <div class="status1">
      <a-icon class="icon " type="left"  @click="prePage()"/>
    </div>


    <div class="container">
      <br>

      <div class="row animated bounceInUp" >
        <div class="col-md-4 col-sm-6" style="margin-top: 20px;" v-for="article in articles" v-bind:key="article">
          <router-link :to="{
              path:'/showArticle',
              query:{
                  articleId: article.id
              }}">
          <div class="blog" >
            <div class="card" style="height: 500px">
              <img class="card-img-top" :src="article.cover" alt="Card image cap">
              <div class="card-body">
                <h4 class="card-title" >{{article.title}}</h4>
                <p class="card-text" style="color: #2E2D3C">{{article.synopsis}}</p>
              </div>
            </div>
          </div>
          </router-link>
        </div>
      </div>
      <!-- .row -->
      <br>
      <br>


    </div>

    <div class="status2">
      <a-icon class="icon" type="right" @click="nextPage()"/>
    </div>

    <!--    <a-pagination v-model="current" :total="50" show-less-items />-->

  </div>
</template>
<script>
  import articleApi from '../../api/article'

  export default {
    data () {
      return {
        page: 1,
        limit: 6,
        total:'',
        articles:{
          id:'',
          title:'',
          synopsis:'',
          cover:''
        }
      }
    },
    created () {
      this.selectArticle()

    },
    methods:{
      //上一页
      prePage(){
        if (this.page == 1){
          this.$message.info('这是第一页');
          return
        }else {
          this.page -= 1
          this.selectArticle(this.page,this.limit)
        }
      },
      //下一页
      nextPage(){
        if (this.page == (this.total%6)){
          this.$message.info('这是最后一页');
          return
        }else {
          this.page += 1
          this.selectArticle(this.page,this.limit)

        }
      },

      selectArticle(){
        articleApi.selectArticle(this.page,this.limit).then(res=>{
          if (res.success){
            this.articles = res.queryResponseResultList.list
            this.total = res.queryResponseResultList.total
          }
        })
      }
    }

  }
</script>
<style>
  .warp {
    margin: 0 auto;
    width: 80%;
  }

  .blog:hover {
    transform: scale(1.02);
    transition: transform 0.4s, opacity 0.4s;
  }

  .status1 {
    position: absolute;
    left: 100px;
    top: 40%;
  }

  .status2 {
    position: absolute;
    right: 100px;
    top: 40%;
  }

  .icon {
    font-size: 100px;
    opacity: 0.7;
    color: #cccccc;
  }

  .icon:hover {
    transition: transform 0.4s, opacity 0.4s;
    opacity: 0.7;
    transform: scale(1.4);
    color: #009688;
  }

</style>
