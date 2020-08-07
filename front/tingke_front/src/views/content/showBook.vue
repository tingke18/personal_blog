<template>
  <div class="warp">
  <div style="  margin: 0 auto;width: 80%;">
    <div style="float: left;width: 100%;margin-top:50px;background-color: #eeeeee; padding: 20px;">
      <i class="layui-icon layui-icon-read" style="font-size: 30px; color: #009f95;"> 推荐图书</i>
      <a-row :gutter="[16,16]">
        <a-col :span="6" class="picList" v-for="book in books" v-bind:key="book">
          <a-card hoverable class="animated bounce" @click="showBook(book.url)"  style="height: 460px">
            <img
              slot="cover"
              alt="example"
              :src="book.picture"
              style="height: 330px"
            />
            <a-card-meta :title="book.title">
              <template slot="description">
                <span class="bookShow">{{book.description}}</span>
              </template>
            </a-card-meta>
          </a-card>
        </a-col>
      </a-row>
      <a-pagination
        :current="page"
        :total="total"
        :defaultCurrent="page"
        :page-size="limit"
        @change="selectPageBook"  style="margin-top: 20px"/>
    </div>

  </div>


  </div>
</template>
<script>

  import bookApi from '../../api/book'

  export default {
    data(){
      return{
        books:{
          title:'',
          description:'',
          picture:'',
          url:''
        },
        page:1,
        limit: 12,
        total:''
      }
    },
    created() {
      this.selectPageBook()
    },
    methods:{
      //查询显示图书
      showBook(url){
        window.open(url,'_blank')
      },

      selectPageBook(page){
        if (page>0){
          this.page = page
        }
        bookApi.selectPageBook(this.page,this.limit).then(res=>{
          if (res.success){
            this.books = res.queryResponseResultList.list
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

  .bookShow{
    display:-webkit-box;
    -webkit-box-orient:vertical;
    -webkit-line-clamp:3;
    overflow:hidden;
  }
</style>
