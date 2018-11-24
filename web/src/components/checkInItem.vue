<template>
  <div>
    <a href="#" @click="checkin" :id="'target' + target.tid">
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
  import {submitCheckIn, fetchCheckIn} from "../api/api";
  import modal from './modal';

  export default {
    props: {
      target: {
        type: Object
      }
    },
    methods: {
      checkin() {
        submitCheckIn(this.target.tid, {
          uid: this.target.uid,
          tid: this.target.tid,
          checkinDateTime: new Date(Date.now() - this.tzOffset).toISOString()
          // checkinDateTime: 'asdasda'
        }).then(res => {
          if (res.status === 200) {
            this.prompt = res.data.content;
            this.showModal = true;
            this.bgColor = '#ffd633';
          }
        }).catch(error => {
          if (error.response) {
            console.log('Error response: ' + error.response);
            this.prompt = error.response.data.content;
            this.showModal = true;
          } else if (error.request) {
            console.log('Error request: ' + error.request);
          } else {
            console.log('Error: ' + error.message);
          }
          console.log(error.config);
        })
      }
    },
    components: {
      modal
    },
    data() {
      return {
        showModal: false,
        tzOffset: new Date().getTimezoneOffset() * 60000,
        prompt: '',
        bgColor: '#fff'
      }
    },
    created() {
      var today = new Date(Date.now() - this.tzOffset).toISOString().split('T')[0];
      fetchCheckIn(this.target.tid, today)
        .then(res => {
          if (res.status === 200) {
            console.log('tid ' + this.target.tid + ' has checked in today');
            console.log(res.data.content);
            this.bgColor = '#ffd633';
          }
        })
        .catch(error => {
          console.log('Catch error while fetching checkin status for today');
          // console.log(JSON.stringify(error));
          console.log(error.response.data.content);
          this.bgColor = '#fff';
        })
    }
  }
</script>
