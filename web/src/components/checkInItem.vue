<template>
  <div>
    <a @click="checkin">
      <div class="target" :style="{background: bgColor}">
        <div class="title">{{ target.title }}</div>
        <div class="desc">{{ target.description }}</div>
      </div>
    </a>
    <el-dialog :title="target.title" :visible.sync="showDialog" width="30%">
      <span class="statement">{{ prompt }}</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="medium" @click="showDialog = false">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import {submitCheckIn} from "../api/api";
  import {now, showColor} from "../api/util";

  export default {
    props: {
      target: {
        type: Object
      }
    },
    data() {
      return {
        showDialog: false,
        prompt: '',
        bgColor: '#fff'
      }
    },
    methods: {
      checkin() {
        submitCheckIn(this.target.tid, {
          uid: this.target.uid,
          tid: this.target.tid,
          checkinDateTime: now().toISOString()
        }).then(res => {
          if (res.status === 200) {
            this.prompt = res.data.content;
            this.showDialog = true;
            this.bgColor = '#ffd633';
          }
        }).catch(error => {
          if (error.response) {
            console.log(`Error response: ${error.response}`);
            this.prompt = error.response.data.content;
            this.showDialog = true;
          } else if (error.request) {
            console.log(`Error request: ${error.request}`);
          } else {
            console.log(`Error: ${error.message}`);
          }
          console.log(error.config);
        })
      },
    },
    mounted() {
      showColor(this);
    },
  }
</script>
