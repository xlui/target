<template>
  <div>
    <div class="login">
      <a href="#" class="btn btn-primary" v-if="!login" @click="postLogin">登录</a>
      <template v-else>
        Hello, {{ username }}
        <a href="#" class="btn btn-primary" @click="logout">注销</a>
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
          <a href="#" data-toggle="modal" data-target="#newTarget">
            <div class="target">
              <div class="desc">新增一个目标</div>
            </div>
          </a>
          <div class="modal fade" id="newTarget" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
               aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-body">
                  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                  </button>
                </div>
                <div class="modal-title">
                  <h1>新增一个目标</h1>
                </div>
                <div class="modal-body">
                  新目标的基本属性
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                  <button type="button" class="btn btn-primary">提交</button>
                </div>
              </div>
            </div>
          </div>
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
        username: 'xlui',
        password: 'pass',
        targets: [],
        login: false
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
