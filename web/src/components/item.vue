<template>
  <a href="#" @click="getTarget(target.tid)">
    <div class="target">
      <div class="desc"><b>{{ target.description }}</b></div>
    </div>
  </a>
</template>

<script>
  import {fetchTarget} from '../api/api'
  export default {
    props: {
      target: {
        type: Object
      }
    },
    methods: {
      getTarget(tid) {
        fetchTarget(tid).then(res => {
          alert(JSON.stringify(res.data))
        })
      }
    }
  }
</script>
