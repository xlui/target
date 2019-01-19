<template>
  <div class="main">
    <h1 class="main-title">Yesterday's targets</h1>

    <div class="navigate">
      <el-button type="primary" size="medium" class="left" @click="$router.push('/')">Home</el-button>
      <span class="statement right">Keep being better step by step!</span>
    </div>

    <div class="box">
      <template v-for="target in targets">
        <YesterdayItem :target="target"></YesterdayItem>
      </template>
    </div>
  </div>
</template>

<script>
  import {fetchTargets, fetchTargetsYesterday, submitCheckIn} from "../api/api"
  import {nowISO, adjustDay} from "../api/util"
  import YesterdayItem from './yesterdayItem'

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
    },
    components: {
      YesterdayItem
    }
  }
</script>
