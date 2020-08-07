<template>
  <div>
    <div calss="warp">
      <!--   <div style="position: relative">
         <img src="../../assets/aboutHeader.jpg" style="height: 300px;width: 50%;margin-top:10px">
           <div style="position: absolute;top: 60px;left: 40%">
             <h1 style="font-size: 43px;color: #009688">关于我</h1>
             <p style="font-size: 20px;color: #009688">爱 兮 爱 乎 爱 乎 爱 乎</p>
             <span style="font-size: 20px;color: #009688"> 爱 青 剑 兮 一 个 仇 人 自 屠</span>
           </div>
         </div>-->



      <div class="skw-pages" v-if="index == true">

        <div class="skw-page skw-page-1 active ">
          <div class="skw-page__half skw-page__half--left animated fadeInLeftBig">
            <div class="skw-page__skewed">
              <div class="skw-page__content"></div>
            </div>
          </div>
          <div class="skw-page__half skw-page__half--right animated fadeInRightBig">
            <div class="skw-page__skewed">
              <div class="skw-page__content">
                <h2 class="skw-page__heading" style="color: #eeeeee">梧桐山顶的日出</h2>
              </div>
            </div>
          </div>
        </div>

        <div class="skw-page skw-page-2">
          <div class="skw-page__half skw-page__half--left">
            <div class="skw-page__skewed">
              <div class="skw-page__content">
                <h2 class="skw-page__heading" style="color: #eeeeee">水库</h2>
              </div>
            </div>
          </div>
          <div class="skw-page__half skw-page__half--right">
            <div class="skw-page__skewed">
              <div class="skw-page__content"></div>
            </div>
          </div>
        </div>

        <div class="skw-page skw-page-3">
          <div class="skw-page__half skw-page__half--left">
            <div class="skw-page__skewed">
              <div class="skw-page__content"></div>
            </div>
          </div>
          <div class="skw-page__half skw-page__half--right">
            <div class="skw-page__skewed">
              <div class="skw-page__content">
                <h2 class="skw-page__heading" style="color: #eeeeee">随途一拍</h2>
              </div>
            </div>
          </div>
        </div>

        <div class="skw-page skw-page-4">
          <div class="skw-page__half skw-page__half--left">
            <div class="skw-page__skewed">
              <div class="skw-page__content">
                <h2 class="skw-page__heading" style="color: #eeeeee">第一辆公路</h2>
              </div>
            </div>
          </div>
          <div class="skw-page__half skw-page__half--right">
            <div class="skw-page__skewed">
              <div class="skw-page__content"></div>
            </div>
          </div>
        </div>

        <div class="skw-page skw-page-5">
          <div class="skw-page__half skw-page__half--left">
            <div class="skw-page__skewed">
              <div class="skw-page__content"></div>
            </div>
          </div>
          <div class="skw-page__half skw-page__half--right">
            <div class="skw-page__skewed">
              <div class="skw-page__content">
                <h2 class="skw-page__heading"><a-icon class="aicon" type="double-right"  @click="index=false" />   </h2>
              </div>
            </div>
          </div>
        </div>
      </div>




      <div class="main-container  animated rollIn"  v-if="index == false">
        <div class="left-container">
          <div class="title" style="color: #009f95">
            <p><span>关于本站</span><br/></p>
            <p> 爱兮爱乎爱乎</p>
            <p> 爱青剑兮一个仇人自屠</p>
            <div class="line"></div>
          </div>
          <div class="profile">
            <img src="../../../public/aboutList/img/320755dbe6849f1c26308e67c5eb2b67.jpeg" alt="user-avatar" />
          </div>
        </div>

        <div class="right-container">
          <div class="text">
            <p >
              <span>打码为了什么？</span><br/><br/>
              还能为了什么，为了生活而已,<br/><br/>
              好像也没什么打码改变世界的想法,<br/><br/>
              因为实在没有那个能力，就只能打打Bug,<br><br/>
              然后修修补补，过过生活那个样子,<br><br/>
              在校大学生，为了工作努力学习,<br/><br/>
            </p>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script >



  export default {
    data(){
      return{
        checkon:true,
        index: true
      }
    },
    methods:{
      active(){
      }
    },
    created () {
      this.index = true
    },
    mounted () {
      this.active()
    },
    destroyed () {
      this.index = false
    }
  }
</script>

<style>
  @import url('https://fonts.googleapis.com/css?family=Roboto:300,400&display=swap');

  .warp {
    margin: 0 auto;
    width: 80%;
  }

  .aicon{
    font-size: 60px
  }

  .aicon:hover{
    transition: transform 1s, opacity 1s;
    color: #009688;
    font-size: 60px;
    transform: rotate(360deg) scale(2,1.2);
  }


  :root {
    --background-color: #f2ce97;
    --dark-orange: #e37b62;
    --dark-gray: #484956;
    --light-gray: rgb(215, 219, 223);
  }

  * {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
  }


  .main-container {
    width: 1000px;
    height: 560px;
    max-height: 900px;
    margin: 20px auto;
    display: flex;
    align-content: center;
    justify-content: center;
    box-shadow: 0 5px 15px 1px rgba(0, 0, 0, 0.2);
  }

  .left-container {
    background: var(--dark-gray);
    color: #fff;
    width: 400px;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    font-weight: 300;
  }

  .title {
    font-size: 2rem;
    padding: 65px 40px;
  }

  .title span {
    font-weight: bold;
  }

  .line {
    width: 65px;
    border: 2px solid var(--dark-orange);
    margin-top: 10px;
  }

  .profile {
    display: flex;
    align-items: center;
    padding: 35px 40px;
  }

  .profile img {
    width: 65px;
    border-radius: 50%;
    margin-right: 15px;
    box-shadow: 0 5px 15px 1px rgba(0, 0, 0, 0.5);
  }

  .right-container {
    background: #fcfcfc;
    width: 600px;
    height: auto;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

  .text {
    line-height: 1.4;
    font-weight: 300;
    font-size: 1.2rem;
    padding: 65px 40px 35px 40px;
  }

  .text span {
    font-size: 2rem;
    font-weight: bold;
  }

  .footer {
    display: flex;
    align-items: center;
    justify-content: space-between;
    background: #fff;
    padding: 35px 40px;
    border-top: 1px solid #fbfbfb;
    box-shadow: 0px -15px 10px -10px rgba(0, 0, 0, 0.1);
  }

  .comments {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .comments p {
    margin: 0 20px 0 5px;
    color: var(--light-gray);
  }

</style>
