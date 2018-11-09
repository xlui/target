<template>
  <div id="app">
    <div style="float: right;">
      <a href="#" style="font-size: 1.3em; margin-right: 50px">登录</a>
    </div>
    <h1 style="text-align: center">Your targets</h1>
    <router-view/>
  </div>
</template>

<script>
  export default {
    name: 'App'
  }
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
</style>
