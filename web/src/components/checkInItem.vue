<template>
  <div>
    <a @click="checkin">
      <div class="target" :style="{background: bgColor}">
        <div class="title">{{ target.title }}</div>
        <div class="desc">{{ target.description }}</div>
      </div>
    </a>
    <modal v-if="showModal" @close="showModal = false">
      <h2 slot="header">{{ target.title }}</h2>
      <span class="statement" slot="body">{{ this.prompt }}</span>
    </modal>
  </div>
</template>

<script>
  import {submitCheckIn} from "../api/api";
  import {showColor, now} from "../api/util";
  import modal from "./modal";

  export default {
    props: {
      target: {
        type: Object
      }
    },
    data() {
      return {
        showModal: false,
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
            this.showModal = true;
            this.bgColor = '#ffd633';
          }
        }).catch(error => {
          if (error.response) {
            console.log(`Error response: ${error.response}`);
            this.prompt = error.response.data.content;
            this.showModal = true;
          } else if (error.request) {
            console.log(`Error request: ${error.request}`);
          } else {
            console.log(`Error: ${error.message}`);
          }
          console.log(error.config);
        })
      },
    },
    components: {
      modal
    },
    created() {
      showColor(this);
    }
  }
</script>
