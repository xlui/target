<template>
  <div class="main">
    <h1 class="header">This is the manage page</h1>
    <div class="navigate">
      <template v-if="login">
        <el-button type="primary" size="medium" class="left" @click="$router.push('/')">Home</el-button>
        <el-tooltip effect="dark" placement="top" content="Your journey in this project">
          <el-button type="success" size="medium" class="left" @click="getJourney">Journey</el-button>
        </el-tooltip>
        <span class="statement">Hello, {{ username }}</span>
        <el-button type="danger" size="medium" class="item" @click="logout">Logout</el-button>
      </template>
    </div>

    <el-dialog :title="'Journey'" :visible.sync="showJourney" style="text-align: left" width="40%">
      <el-form ref="form" label-position="left" label-width="100px">
        <template v-for="journey in journeys">
          <el-col :offset="1" :span="23">
            <el-form-item :label="journey.date">
              <el-tag>{{ journey.journey }}</el-tag>
              <el-tag type="warning">{{ journey.time }}</el-tag>
            </el-form-item>
          </el-col>
        </template>
      </el-form>
      <span slot="footer">
        <el-button type="danger" size="medium" @click="showJourney = false">Close</el-button>
      </span>
    </el-dialog>

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
  import {fetchJourney} from "../api/api";
  import {home} from "../api/util";
  import item from "./manageItem";

  export default {
    data() {
      return {
        login: false,
        username: '',
        showJourney: false,
        journeys: []
      }
    },
    computed: {
      targets() {
        return this.$store.getters.getAllTargets;
      }
    },
    methods: {
      logout() {
        localStorage.username = '';
        localStorage.token = '';
        this.login = false;
        location.href = home;
      },
      getJourney() {
        fetchJourney().then(res => {
          if (res.data.status === 'OK') {
            this.journeys = res.data.content;
            this.showJourney = true;
          }
        })
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

<style lang="less">
  @import "../common/style/layout.less";
</style>
