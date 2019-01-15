<template>
  <div class="main">
    <h1 class="main-title">Your targets</h1>

    <div class="navigate">
      <template v-if="login">
        <el-button type="primary"
                   size="medium"
                   class="left"
                   @click="$router.push('/manage')">Manage
        </el-button>
        <el-button type="success"
                   size="medium"
                   class="left"
                   @click="getWeeklyReport">Weekly Report
        </el-button>
        <el-button type="success"
                   size="medium"
                   class="left"
                   @click="getRank('weekly')">Rank
        </el-button>
        <el-tooltip effect="dark" placement="top" content="Nonsupport now!">
          <el-button type="warning" size="medium" class="left">Yesterday</el-button>
        </el-tooltip>

        <span class="statement right">Hello, {{ username }}</span>
        <el-button type="danger" size="medium" class="right" @click="logout">Logout</el-button>
      </template>
      <template v-else>
        <el-button type="success" size="medium" class="right" @click="showRegister = true">Register</el-button>
        <el-button type="primary" size="medium" class="right" @click="postLogin">Login</el-button>
      </template>
    </div>

    <div class="box" v-loading.fullscreen.lock="loading">
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

    <el-dialog title="Weekly Report" :visible.sync="showWeekly" width="30%">
      <el-form ref="form" label-position="left" label-width="150px" style="text-align: left">
        <el-col :offset="3" :span="23">
          <el-form-item label="Week start">
            <el-tag>{{ weekly.WeekStart }}</el-tag>
          </el-form-item>
        </el-col>
        <el-col :offset="3" :span="23">
          <el-form-item label="Week end">
            <el-tag>{{ weekly.WeekEnd }}</el-tag>
          </el-form-item>
        </el-col>
        <el-col :offset="3" :span="23">
          <el-form-item label="Total check in">
            <el-tag type="success">{{ weekly.TotalCheckIn }}</el-tag>
          </el-form-item>
        </el-col>
        <el-col :offset="3" :span="23">
          <el-form-item label="Should check in">
            <el-tag type="success">{{ weekly.ShouldCheckIn }}</el-tag>
          </el-form-item>
        </el-col>
        <el-col :offset="3" :span="23">
          <el-form-item label="Complete percentage">
            <el-tag type="warning">{{ weekly.CompletePercentage.toFixed(2) }}%</el-tag>
          </el-form-item>
        </el-col>
      </el-form>
      <span slot="footer">
        <el-button type="danger" size="medium" @click="showWeekly = false">Close</el-button>
      </span>
    </el-dialog>
    <el-dialog title="Rank" :visible.sync="showRank" width="50">
      <el-tabs center="true" v-model="rankEpoch" @tab-click="getRank(rankEpoch)">
        <el-tab-pane label="Weekly" name="weekly"></el-tab-pane>
        <el-tab-pane label="Monthly" name="monthly"></el-tab-pane>
        <el-tab-pane label="Totally" name="totally"></el-tab-pane>
      </el-tabs>
      <el-table :data="totalRanks" :row-class-name="highlightMyRank">
        <el-table-column prop="rank" label="Rank" align="center"></el-table-column>
        <el-table-column prop="user" label="User" align="center"></el-table-column>
        <el-table-column prop="checkin" label="Check in count" align="center"></el-table-column>
      </el-table>
      <span slot="footer">
        <el-button type="danger" size="medium" @click="showRank = false">Close</el-button>
      </span>
    </el-dialog>
    <el-dialog title="Register" :visible.sync="showRegister" width="30%">
      <el-form status-icon ref="registerForm" :model="registerUser" :rules="registerRules" label-position="left" label-width="150px" style="text-align: left">
        <el-form-item label="Username" prop="username">
          <el-input v-model="registerUser.username"></el-input>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input type="password" v-model="registerUser.password"></el-input>
        </el-form-item>
        <el-form-item label="Retype password" prop="checkPassword">
          <el-input type="password" v-model="registerUser.checkPassword"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button type="danger" size="medium" @click="showRegister = false">Close</el-button>
        <el-button type="primary" size="medium" @click="postRegister">Register</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import {checkToken, submitLogin, submitRegister, fetchWeekly, fetchRank} from '../api/api';
  import {home, nowLocal} from "../api/util";
  import item from "./checkInItem";
  import newTarget from './newTarget';

  export default {
    data() {
      let checkPassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('Please input the password again!'));
        } else if (value !== this.registerUser.password) {
          callback(new Error("The two password don't match!"));
        } else {
          callback();
        }
      };

      return {
        username: 'i@xlui.me',
        password: 'pass',
        login: false,

        showRegister: false,
        registerUser: {},
        registerRules: {
          username: [
            {required: true, message: 'Please input the username!', trigger: 'change'},
            {type: 'email', message: 'Username must be a valid email address', trigger: 'change'}
          ],
          password: [
            {required: true, message: 'Please input the password!', trigger: 'change'},
            {min: 8, max: 128, message: 'Password must between 8 and 128 characters', trigger: ['change', 'blur']}
          ],
          checkPassword: [
            {validator: checkPassword, trigger: ['change', 'blur']}
          ]
        },

        showWeekly: false,
        weekly: {
          CompletePercentage: 0.0
        },

        showRank: false,
        rankEpoch: 'weekly',
        totalRanks: {},
        myRank: 0,

        loading: false,
      }
    },
    computed: {
      targets() {
        return this.$store.getters.getFilterTargets;
      }
    },
    name: 'App',
    methods: {
      postRegister() {
        this.$refs['registerForm'].validate((valid) => {
          if (valid) {
            delete this.registerUser.checkPassword;
            submitRegister(this.registerUser).then(res => {
              if (res.data.status === 'CREATED') {
                alert('Successfully register!');
                this.showRegister = false;
              }
            }).catch(error => {
              if (error.response.status === 400) {
                alert('Username has been registered!');
                this.registerUser = {};
              } else {
                console.log(`register response: ${JSON.stringify(res.data)}`);
              }
            })
          } else {
            alert('Please check your input!');
          }
        })
      },
      postLogin() {
        this.loading = true;
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
        });
        // this.loading = false;
        setTimeout(() => {
          this.loading = false;
        }, 1000);
      },
      logout() {
        localStorage.username = '';
        localStorage.token = '';
        this.login = false;
        location.href = home;
      },
      getWeeklyReport() {
        fetchWeekly(nowLocal()).then(res => {
          if (res.data.status === 'OK') {
            this.weekly = res.data.content;
            this.showWeekly = true;
          }
        }).catch(err => {
          console.log(`Error while fetching weekly report: ${err}`)
        })
      },
      getRank(epoch = 'weekly') {
        fetchRank(epoch).then(res => {
          switch (res.data.status) {
            case 'OK': {
              this.myRank = res.data.content.myself;
              this.totalRanks = res.data.content.total;
              this.rankEpoch = epoch;
              this.showRank = true;
            }
              break;
            case 'NO_CONTENT': alert(res.data.content); break;
          }
        })
      },
      highlightMyRank({row, rowIndex}) {
        if (rowIndex + 1 === this.myRank) {
          return 'myself';
        }
      }
    },
    created() {
      // check user login or not
      if (localStorage.token) {
        this.loading = true;
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
        this.loading = false;
      }
    },
    components: {
      item,
      newTarget,
    },
  }
</script>
