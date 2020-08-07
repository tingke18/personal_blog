<template>
  <div class="warp animated fadeInLeft">
    <div class="quickLink"><i class="layui-icon layui-icon-link"
                              style="font-size: 30px; color: #009f95;margin-left: 55px"></i> 快捷连接
    </div>

    <div v-for="link in links" v-bind:key="link">
      <span class="category">{{link.category}}</span>
      <div class="showLink" v-for="flink in link.frLinks" v-bind:key="flink">
        <div @click="showlink(flink.url)">
        <div class="avatar"><img style="height: 80px;width: 80px"
                                 :src="flink.avatar"></div>
        <div class="titles"><span>{{flink.title}}</span></div>
        <div class="contents">{{flink.description}}</div>
      </div>
    </div>
    </div>

  </div>
</template>
<script>
  import linkApi from '../../api/link'

  export default {
    data () {
      return {
        links: []
      }
    },
    created () {
      this.selectLink()
    },
    methods: {
      showlink(url){
        window.open(url,"_blank");
      },

      selectLink () {
        linkApi.selecLink().then(res => {
          if (res.success) {
            this.links = res.queryResponseResultList.list
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

  .category{
    margin-top: 30px;
    font-size: 24px;
    color: #117a8b;
    float: left;
    width: 100%;
    text-align: left;
    margin-left: 55px
  }

  .quickLink {
    text-align: left;
    margin-top: 30px;
    font-size: 30px;
    color: #009f95;
  }

  .contents {
    margin-right: 5px;
    margin-top: 5px;
    float: right;
    width: 170px;
    height: 90px;
    word-wrap: break-word;
    word-break: break-all;
  }


  .titles {
    margin-left: 8px;
    margin-top: 13px;
    float: left;
    width: 150px;
    height: 40px;
    font-size: 17px;

  }


  .titles:hover {
    margin-top: 13px;
    float: left;
    width: 150px;
    height: 40px;
    font-size: 17px;
  }

  .showLink {
    cursor: pointer;
    opacity: 0.8;
    float: left;
    height: 140px;
    width: 270px;
    margin-top: 20px;
    margin-left: 55px;
    border: 0.5px solid #009f95;
    border-radius: 15px;
  }

  .showLink:hover {
    transform: scale(1.04);
    transition: transform 0.4s, opacity 0.4s;
    float: left;
    height: 140px;
    width: 270px;
    border: 1px solid #34ce57;
    border-radius: 15px;
    background-color: #d1ecf1;
  }

  .avatar {
    float: left;
    margin-top: 10px;
    margin-left: 10px;
  }

  @keyframes myfirst {
    from {
      background: #d1ecf1;
    }
    to {
      background: #ffffff;
    }
  }

  @-webkit-keyframes myfirst /* Safari and Chrome */
  {
    from {
      background: red;
    }
    to {
      background: yellow;
    }
  }
</style>
