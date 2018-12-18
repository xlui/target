<template>
  <div class="main">
    <h1 class="main-title">Your targets</h1>

    <div class="navigate">
      <template v-if="login">
        <el-tooltip effect="dark" placement="top" content="Nonsupport now!">
          <el-button type="success" size="medium"
                     class="left">Yesterday
          </el-button>
        </el-tooltip>
        <el-button type="primary" size="medium"
                   class="left"
                   @click="$router.push('/manage')">Target Management
        </el-button>
      </template>
      <el-button type="primary" size="medium" class="right" v-if="!login" @click="postLogin">登录</el-button>
      <template v-else>
        <span class="statement right">Hello, {{ username }}</span>
        <el-button type="danger" size="medium" class="right" @click="logout">注销</el-button>
      </template>
    </div>

    <div class="box">
      <div v-if="login">
        <template v-for="target in targets">
          <item :target="target"></item>
        </template>
        <new-target></new-target>
      </div>
      <div class="nologin" v-else>
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
        return this.$store.getters.getFilterTargets;
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
