<template>
  <div id="app">
<!--    <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </div>
    <router-view/>-->
    <router-view>

    </router-view>
    <Player></Player>
  </div>
</template>
<script>
  import Player from 'zw-player'
  export default {
    data(){
      return{
      }
    },
    components:{
      Player
    }
  }
</script>
<style lang="less">
  @import "../public/static/css/style.css";
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

</style>
