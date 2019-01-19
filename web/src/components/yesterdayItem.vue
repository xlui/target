<template>
  <div>
    <a @click="check">
      <div class="target" :style="{background: checked ? '#ffd633' : '#fff'}">
        <div class="title">{{ target.title }}</div>
        <div class="desc">{{ target.description }}</div>
      </div>
    </a>
    <el-dialog title="Reason" :visible.sync="showReason" width="50%">
      Leave a reason for reCheckIn:
      <el-input v-model="reason"></el-input>
      <span slot="footer">
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
  import {showColor, yesterdayISO} from "../api/util";
  import {fetchCheckIn} from "../api/api";

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
        yesterdayISO()
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

      },
      recheckin() {

      }
    }
  }
</script>
