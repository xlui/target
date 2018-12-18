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
        <a href="#" @click="showDialog = true">
          <div class="target">
            <div class="title">New</div>
            <div class="desc">新增一个目标</div>
          </div>
        </a>
        <el-dialog title="Add a new target!" width="40%" :visible="showDialog" @close="showDialog = false">
          <el-form ref="form" label-position="left" label-width="100px">
            <el-col :span="23" :offset="1">
              <el-form-item label="Title">
                <el-input v-model="newTarget.title"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="23" :offset="1">
              <el-form-item label="Description">
                <el-input v-model="newTarget.description"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="23" :offset="1">
              <el-form-item label="Start date">
                <el-col :span="6">
                  <el-date-picker type="date" placeholder="Please choose the start date of target"
                                  v-model="newTarget.startDate"></el-date-picker>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col :span="23" :offset="1">
              <el-form-item label="End date">
                <el-col :span="6">
                  <el-date-picker type="date" placeholder="Please choose the end date of target"
                                  v-model="newTarget.endDate"></el-date-picker>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col :span="23" :offset="1">
              <el-form-item label="Check in start">
                <el-col :span="6">
                  <el-time-picker type="fixed-time" placeholder="Please choose the start time of check in"
                                  v-model="newTarget.checkinStart"></el-time-picker>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col :span="23" :offset="1">
              <el-form-item label="Check in end">
                <el-col :span="6">
                  <el-time-picker type="fixed-time" placeholder="Please choose to end time of check in"
                                  v-model="newTarget.checkinEnd"></el-time-picker>
                </el-col>
              </el-form-item>
            </el-col>
            <el-col :span="23" :offset="1">
              <el-form-item label="Repeat">
                <el-checkbox-group v-model="newTarget.repeat">
                  <el-checkbox label="0b01000000" class="week">Sun</el-checkbox>
                  <el-checkbox label="0b00100000" class="week">Mon</el-checkbox>
                  <el-checkbox label="0b00010000" class="week">Tue</el-checkbox>
                  <el-checkbox label="0b00001000" class="week">Wed</el-checkbox>
                  <el-checkbox label="0b00000100" class="week">Thu</el-checkbox>
                  <el-checkbox label="0b00000010" class="week">Fri</el-checkbox>
                  <el-checkbox label="0b00000001" class="week">Sat</el-checkbox>
                </el-checkbox-group>
              </el-form-item>
            </el-col>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button size="medium" @click="showDialog = false">关闭</el-button>
            <el-button type="primary" size="medium" @click="addTarget">提交</el-button>
          </span>
        </el-dialog>
      </div>
      <div class="logout" v-else>
        Welcome, new user!!
      </div>
    </div>
  </div>
</template>

<script>
  import {checkToken, submitLogin, submitTarget} from '../api/api';
  import {adjust, home, now} from "../api/util";
  import item from "./checkInItem";

  export default {
    data() {
      return {
        username: 'i@xlui.me',
        password: 'pass',
        login: false,
        showDialog: false,
        newTarget: {
          startDate: now(),
          endDate: adjust(now(), 1),
          checkinStart: new Date(2018, 10, 26, 9, 0),
          checkinEnd: new Date(2018, 10, 26, 18, 0),
          repeat: []
        }
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
      },
      addTarget() {
        console.log('Calculate the number value of repeat.');
        let ret = 0;
        for (const r of this.newTarget.repeat) {
          ret += Number(r)
        }
        this.newTarget.repeat = ret;
        submitTarget(this.newTarget).then(res => {
          if (res.status === 201) {
            console.log('Successfully created new target!');
            alert(res.data.content);
            location.href = home;
          }
        }).catch(error => {
          console.log('Error while adding target: ', error);
          alert('Please choose at least one item in repeat!');
          location.href = home;
        })
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
    },
  }
</script>
