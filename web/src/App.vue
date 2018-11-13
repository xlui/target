<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
  import './common/js/jquery.min';
  import './common/js/bootstrap.min';
  export default {};
</script>

<style>
  @import "./common/style/bootstrap.min.css";
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
</style>
