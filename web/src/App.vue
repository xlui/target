<template>
  <div id="app">
    <div style="float: right;">
      <a @click="defaultLogin" href="#" style="font-size: 1.3em; margin-right: 50px">登录</a>
    </div>
    <h1 style="text-align: center">Your targets</h1>
    <router-view/>
  </div>
</template>

<script>
  import {login} from './api/api';
  export default {
    name: 'App',
    methods: {
      defaultLogin() {
        login({
          username: 'xlui',
          password: 'pass'
        }).then(res => {
          alert(JSON.stringify(res.data))
        })
      }
    }
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
