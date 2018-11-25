<template>
  <div class="main">
    <h1 class="main-title">This is the manage page</h1>
    <div class="login">
      <template v-if="login">
        <span class="statement">Hello, {{ username }}</span>
        <button class="btn btn-primary" @click="$router.push('/')">注销</button>
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
  import {fetchTargets} from "../api/api";
  import item from './manageItem';

  export default {
    data() {
      return {
        login: false,
        username: '',
        targets: [],
      }
    },
    methods: {},
    created() {
      if (localStorage.token) {
        this.login = true;
        this.username = localStorage.username;
        fetchTargets({}).then(res => {
          if (res.data.status === 'OK') {
            this.targets = res.data.content;
          }
        }).catch(error => {
          console.log(error);
        })
      }
    },
    components: {
      item
    }
  }
</script>
