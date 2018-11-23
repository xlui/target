<template>
  <div>
    <div class="login">
      <template v-if="login">
        <span style="font-size: 20px">Hello, {{ username }}</span>
        <button class="btn btn-primary" @click="logout">注销</button>
      </template>
      <!--<a href="#" data-toggle="modal" data-target="#myModal">测试</a>-->
    </div>
    <h1 class="title">Your targets</h1>

    <div class="main">
      <div class="box">
        <div v-if="login">
          <template v-for="target in targets">
            <item :target="target"></item>
          </template>
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
  import item from './checkInItem'

  export default {
    data() {
      return {
        username: 'i@xlui.me',
        password: 'pass',
        login: false,
        targets: [],
        newTarget: Object
      }
    },
    name: 'App',
    methods: {
      postLogin() {
        apiLogin({
          username: this.username,
          password: this.password
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
      },
      addTarget(target) {
        // pass
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
