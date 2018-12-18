<template>
  <div class="main">
    <h1 class="main-title">Your targets</h1>

    <div class="login">
      <el-button type="primary" size="medium" v-if="!login" @click="postLogin">登录</el-button>
      <template v-else>
        <span class="statement">Hello, {{ username }}</span>
        <el-button type="primary" size="medium" class="item" @click="$router.push('/manage')">目标管理</el-button>
        <el-button type="danger" size="medium" class="item" @click="logout">注销</el-button>
      </template>
    </div>

    <div class="box">
      <div v-if="login">
        <template v-for="target in targets">
          <item :target="target"></item>
        </template>
        <new-target></new-target>
      </div>
      <div class="logout" v-else>
        Welcome, new user!!
      </div>
    </div>
  </div>
</template>

<script>
  import {checkToken, submitLogin} from '../api/api';
  import {home} from "../api/util";
  import item from "./checkInItem";
  import newTarget from './newTarget';

  export default {
    data() {
      return {
        username: 'i@xlui.me',
        password: 'pass',
        login: false,
      }
    },
    computed: {
      targets() {
        return this.$store.getters.getTargets;
      }
    },
    name: 'App',
    methods: {
      postLogin() {
        submitLogin({
          username: this.username,
          password: this.password
        }).then(res => {
          if (res.data.status === 'OK') {
            console.log('Successfully login using default user information.');
            localStorage.username = this.username;
            localStorage.token = res.data.content;
            this.login = true;
            this.$store.dispatch('fetchFilterTargets')
          }
        }).catch(error => {
          alert('Failed to login. Please check the server is up or your network is available or not?');
        })
      },
      logout() {
        localStorage.username = '';
        localStorage.token = '';
        this.login = false;
        location.href = home;
      }
    },
    created() {
      // check user login or not
      if (localStorage.token) {
        // if local token is valid, use local token to fetch targets.
        checkToken(localStorage.token).then(res => {
          if (res.status === 200) {
            console.log('Token check passed.');
            this.login = true;
            this.$store.dispatch('fetchFilterTargets')
          }
        }).catch(error => {
          console.log('Error while checking token: ', error);
          localStorage.token = '';
        });
      }
    },
    components: {
      item,
      newTarget,
    },
  }
</script>
