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
      </template>
      <el-button type="primary" size="medium" class="right" v-if="!login" @click="postLogin">Login</el-button>
      <template v-else>
        <span class="statement right">Hello, {{ username }}</span>
        <el-button type="danger" size="medium" class="right" @click="logout">Logout</el-button>
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
  </div>
</template>

<script>
  import {checkToken, submitLogin, fetchWeekly, fetchRank} from '../api/api';
  import {home, nowLocal} from "../api/util";
  import item from "./checkInItem";
  import newTarget from './newTarget';

  export default {
    data() {
      return {
        username: 'i@xlui.me',
        password: 'pass',
        login: false,
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
