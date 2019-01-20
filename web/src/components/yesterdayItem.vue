<template>
  <div>
    <a @click="check">
      <div class="target" :style="{background: checked ? '#ffd633' : '#fff'}">
        <div class="title">{{ target.title }}</div>
        <div class="desc">{{ target.description }}</div>
      </div>
    </a>
    <el-dialog title="Reason" :visible.sync="showReason" width="50%">
      <div class="reason">
        <label>Leave a reason for reCheckIn:</label>
        <div class="content">
          <el-input v-model="reason"></el-input>
        </div>
      </div>
      <span slot="footer">
        <el-button type="danger" size="medium" @click="showReason = false">Close</el-button>
        <el-button type="primary" size="medium" @click="recheckin">OK</el-button>
      </span>
    </el-dialog>
    <el-dialog :title="target.title" :visible.sync="showResponse" width="30%">
      <span class="statement">{{ prompt }}</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="medium" @click="showResponse = false">OK</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import {yesterdayISO, yesterdayLocal} from "../api/util";
  import {fetchCheckIn, submitCheckInYesterday} from "../api/api";

  export default {
    props: {
      target: {
        type: Object
      }
    },
    data() {
      return {
        showReason: false,
        reason: '',
        showResponse: false,
        prompt: '',
        checked: false,
      }
    },
    created() {
      fetchCheckIn(
        this.target.tid,
        yesterdayLocal()
      ).then(res => {
        if (res.status === 200 && res.data.status === 'OK') {
          this.checked = true;
        }
      }).catch(error => {
        console.log(`Error in fetching checkin status: ${error}`)
      })
    },
    methods: {
      check() {
        if (this.checked) {
          this.prompt = "You have checked this target yesterday!";
          this.showResponse = true;
        } else {
          this.showReason = true;
        }
      },
      recheckin() {
        if (this.reason === '') {
          alert('You must fill out the reason for reCheckIn!')
        } else {
          submitCheckInYesterday(this.target.tid, {
            uid: this.target.uid,
            tid: this.target.tid,
            checkinDateTime: yesterdayISO().toISOString(),
            reason: this.reason
          }).then(res => {
            this.prompt = res.data.content;
            this.showResponse = true;
            this.showReason = false;
          }).catch(error => {
            console.log(`Catch error while recheckin: ${error}`)
            console.log(JSON.stringify(error))
          })
        }
      }
    }
  }
</script>

<style>
  .reason {
    width: 100%;
  }

  .reason label {
    float: left;
    font-size: 16px;
    width: 250px;
    text-align: left;
    vertical-align: center;
    padding: 10px 0;
    box-sizing: border-box;
  }

  .reason .content {
    margin-left: 250px;
  }
</style>
