<template>
  <div>

    <Header></Header>

    <router-view></router-view>

  <!--  <Footer></Footer>-->
  </div>
</template>

<script>
  // @ is an alias to /src
  import Header from './Header.vue'
  //import Footer from './Footer.vue'


  export default {
    name: 'Home',
    data () {
      return {
        tagsList: [],
        collapse: false,
        name: '',
      }
    },
    components: {
      Header,
     // Footer
    }
  }
</script>
<style>

</style>
