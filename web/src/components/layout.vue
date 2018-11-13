<template>
  <div>
    <div class="login">
      <a href="#" v-if="!login" @click="postLogin">登录</a>
      <a href="#" v-else @click="logout">注销</a>
    </div>
    <h1 class="title">Your targets</h1>

    <div class="main">
      <div class="box">
        <div v-if="login">
          <template v-for="target in targets">
            <item :target="target"></item>
          </template>
          <a href="#">
            <div class="target">
              <div class="desc">新增一个目标</div>
            </div>
          </a>
        </div>
        <div class="logout" v-else>
          Welcome, new user!!
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {apiLogin, fetchTargets, fetchTarget} from '../api/api';
  import item from './item'

  export default {
    data() {
      return {
        targets: [],
        login: false
      }
    },
    name: 'App',
    methods: {
      postLogin() {
        apiLogin({
          username: 'xlui',
          password: 'pass'
        }).then(res => {
          if (res.data.status === 'OK') {
            console.log('Successfully login using default user information.');
            localStorage.token = res.data.content;
            this.login = true;
            this.getTargets()
          }
        })
      },
      logout() {
        localStorage.token = "";
        this.login = false;
        location.href = "/"
      },
      getTargets() {
        fetchTargets({}).then(res => {
          if (res.data.status === 'OK') {
            this.targets = res.data.content;
            console.log('Total targets: ' + res.data.content.length)
          }
        })
      },
      getTarget(tid) {
        fetchTarget(tid).then(res => {
          alert(JSON.stringify(res.data))
        })
      }
    },
    components: {
      item
    }
  }
</script>

<style>
  @import "../common/style/layout.css";
</style>
