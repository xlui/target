<template>
  <div class="main">
    <h1 class="header">Yesterday's targets</h1>

    <div class="navigate">
      <el-button type="primary" size="medium" class="left" @click="$router.push('/')">Home</el-button>
      <span class="statement right">Keep being better step by step!</span>
    </div>

    <div class="box">
      <template v-for="target in targets">
        <YesterdayItem :target="target"></YesterdayItem>
      </template>
      <template v-if="targets.length === 0">
        <p class="statement">No targets valid yesterday!</p>
      </template>
    </div>
  </div>
</template>

<script>
  import {fetchTargetsYesterday} from "../api/api"
  import YesterdayItem from './yesterdayItem'

  export default {
    data() {
      return {
        targets: [],
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

<style lang="less">
  @import "../common/style/layout.less";
</style>
