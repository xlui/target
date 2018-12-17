<template>
  <div class="main">
    <h1 class="main-title">This is the manage page</h1>
    <div class="login">
      <template v-if="login">
        <span class="statement">Hello, {{ username }}</span>
        <el-button type="primary" size="medium" class="item" @click="$router.push('/')">回到首页</el-button>
        <el-button type="danger" size="medium" class="item" @click="logout">注销</el-button>
      </template>
    </div>

    <div class="box">
      <div v-if="login">
        <template v-for="target in targets">
          <item :target="target"></item>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
  import {home} from "../api/util";
  import item from "./manageItem";

  export default {
    data() {
      return {
        login: false,
        username: '',
      }
    },
    computed: {
      targets() {
        return this.$store.getters.getTargets;
      }
    },
    methods: {
      logout() {
        localStorage.username = '';
        localStorage.token = '';
        this.login = false;
        location.href = home;
      }
    },
    created() {
      if (localStorage.token) {
        this.login = true;
        this.username = localStorage.username;
        this.$store.dispatch('fetchAllTargets')
      }
    },
    components: {
      item
    }
  }
</script>
