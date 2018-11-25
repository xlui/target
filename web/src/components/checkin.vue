<template>
  <div class="main">
    <h1 class="main-title">Your targets</h1>

    <div class="login">
      <button class="btn btn-primary" v-if="!login" @click="postLogin">登录</button>
      <template v-else>
        <span class="statement">Hello, {{ username }}</span>
        <button class="item btn btn-primary" @click="$router.push('/manage')">目标管理</button>
        <button class="item btn btn-primary" @click="logout">注销</button>
      </template>
    </div>

    <div class="box">
      <div v-if="login">
        <template v-for="target in targets">
          <item :target="target"></item>
        </template>
        <a href="#" @click="showModal = true">
          <div class="target">
            <div class="title">New</div>
            <div class="desc">新增一个目标</div>
          </div>
        </a>
        <modal v-if="showModal" @close="showModal = false">
          <h2 slot="header">新增一个目标</h2>
          <form role="form" class="form-horizontal" slot="body">
            <div class="form-group form-item">
              <label for="title" class="col-sm-offset-1 col-sm-3">Title:</label>
              <div class="col-sm-8">
                <input type="text" id="title" class="form-control" v-model="newTarget.title"/>
              </div>
            </div>
            <div class="form-group form-item">
              <label for="description" class="col-sm-offset-1 col-sm-3">Description:</label>
              <div class="col-sm-8">
                <input type="text" id="description" class="form-control" v-model="newTarget.description"/>
              </div>
            </div>
            <div class="form-group form-item">
              <label for="startDate" class="col-sm-offset-1 col-sm-3">Start date:</label>
              <div class="col-sm-8">
                <input type="date" id="startDate" class="form-control" v-model="newTarget.startDate"/>
              </div>
            </div>
            <div class="form-group form-item">
              <label for="endDate" class="col-sm-offset-1 col-sm-3">End date:</label>
              <div class="col-sm-8">
                <input type="date" id="endDate" class="form-control" v-model="newTarget.endDate"/>
              </div>
            </div>
            <div class="form-group form-item">
              <label for="checkinStart" class="col-sm-offset-1 col-sm-3">Check in start:</label>
              <div class="col-sm-8">
                <input type="time" id="checkinStart" class="form-control" v-model="newTarget.checkinStart"/>
              </div>
            </div>
            <div class="form-group form-item">
              <label for="checkinEnd" class="col-sm-offset-1 col-sm-3">Check in end:</label>
              <div class="col-sm-8">
                <input type="time" id="checkinEnd" class="form-control" v-model="newTarget.checkinEnd"/>
              </div>
            </div>
            <div class="form-group form-item">
              <label for="repeat" class="col-sm-offset-1 col-sm-3">Repeat:</label>
              <div class="col-sm-8">
                <input type="text" id="repeat" class="form-control" v-model="newTarget.repeat"/>
              </div>
            </div>
          </form>
          <div slot="footer">
            <button type="button" class="btn btn-default" @click="showModal = false">关闭</button>
            <button type="button" class="btn btn-primary" @click="addTarget">提交</button>
          </div>
        </modal>
      </div>
      <div class="logout" v-else>
        Welcome, new user!!
      </div>
    </div>
  </div>
</template>

<script>
  import {checkToken, fetchTargets, submitLogin, submitTarget} from '../api/api';
  import {adjust, extractDate, extractTime, home, now} from "../api/util";
  import item from "./checkInItem";
  import modal from "./modal";

  export default {
    data() {
      return {
        username: 'i@xlui.me',
        password: 'pass',
        login: false,
        showModal: false,
        targets: [],
        newTarget: {
          startDate: extractDate(now()),
          endDate: extractDate(adjust(now(), 1)),
          checkinStart: '09:00',
          checkinEnd: '18:00'
        }
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
            this.getTargets()
          }
        })
      },
      logout() {
        localStorage.username = '';
        localStorage.token = '';
        this.login = false;
        location.href = home;
      },
      getTargets() {
        fetchTargets().then(res => {
          if (res.data.status === 'OK') {
            this.targets = res.data.content;
            console.log(`Successfully fetch ${res.data.content.length} targets from server.`)
          }
        })
      },
      addTarget() {
        submitTarget(this.newTarget)
          .then(res => {
            if (res.status === 201) {
              console.log('Successfully created new target');
              alert(res.data.content);
              location.href = home;
            }
          })
          .catch(error => {
            console.log('Error while adding target: ', error)
          })
      }
    },
    created() {
      // check user login or not
      if (localStorage.token) {
        checkToken(localStorage.token)
          .then(res => {
            if (res.status === 200) {
              console.log('Token check passed.');
              this.login = true;
              this.getTargets()
            }
          })
          .catch(error => {
            console.log('Error while checking token: ', error);
          });
      }
    },
    components: {
      item, modal
    },
  }
</script>
