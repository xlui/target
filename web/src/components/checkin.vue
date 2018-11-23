<template>
  <div>
    <div class="login">
      <button class="btn btn-primary" v-if="!login" @click="postLogin">登录</button>
      <template v-else>
        <span class="statement">Hello, {{ username }}</span>
        <button class="btn btn-primary">目标管理</button>
        <button class="btn btn-primary" @click="logout">注销</button>
      </template>
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
                  <form role="form" class="form-inline">
                    <div class="form-group">
                      <label for="title">Title:</label>
                      <input type="text" id="title" class="form-control" :value="newTarget.title"/>
                    </div>
                    <div class="form-group">
                      <label for="description">Description:</label>
                      <input type="text" id="description" class="form-control" :value="newTarget.description"/>
                    </div>
                    <div class="form-group">
                      <label for="startDate">Start date:</label>
                      <input type="text" id="startDate" class="form-control" :value="newTarget.startDate"/>
                    </div>
                    <div class="form-group">
                      <label for="endDate">End date:</label>
                      <input type="text" id="endDate" class="form-control" :value="newTarget.endDate"/>
                    </div>
                    <div class="form-group">
                      <label for="checkinStart">Check in start:</label>
                      <input type="text" id="checkinStart" class="form-control" :value="newTarget.checkinStart"/>
                    </div>
                    <div class="form-group">
                      <label for="checkinEnd">Check in end:</label>
                      <input type="text" id="checkinEnd" class="form-control" :value="newTarget.checkinEnd"/>
                    </div>
                    <div class="form-group">
                      <label for="repeat">Repeat:</label>
                      <input type="text" id="repeat" class="form-control" :value="newTarget.repeat"/>
                    </div>
                  </form>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                  <button type="button" class="btn btn-primary" @click="addTarget(newTarget)">提交</button>
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
