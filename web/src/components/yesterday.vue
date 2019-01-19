<template>
  <div class="main">
    <h1 class="main-title">Yesterday's targets</h1>

    <div class="box">
      <template v-for="target in targets">
        <a @click="checkinYesterday(target)">
          <div class="target" :style="{background: bgColor}">
            <div class="title">{{ target.title }}</div>
            <div class="desc">{{ target.description }}</div>
          </div>
        </a>
        <el-dialog :title="target.title" :visible.sync="showDialog" width="30%">
          <span class="statement">{{ prompt }}</span>
          <span slot="footer" class="dialog-footer">
          <el-button type="primary" size="medium" @click="showDialog = false">OK</el-button>
        </span>
        </el-dialog>
      </template>
    </div>
  </div>
</template>

<script>
  import {fetchTargets, fetchTargetsYesterday, submitCheckIn} from "../api/api"
  import {nowISO, adjustDay} from "../api/util"

  export default {
    data() {
      return {
        targets: {},
        bgColor: '#fff',
        showDialog: false,
        prompt: '',
      }
    },
    methods: {
      checkinYesterday(target) {
        //   submitCheckIn(target.tid, {
        //     uid: this.target.uid,
        //     tid: this.target.tid,
        //     checkinDateTime: nowISO().toISOString()
        //   })
        console.log(nowISO().toISOString())
      }
    },
    created() {
      fetchTargetsYesterday().then(res => {
        if (res.data.status === 'OK') {
          this.targets = res.data.content
        }
      })
    }
  }
</script>
