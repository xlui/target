<template>
  <div>
    <a href="#" @click="checkin" :id="'target' + target.tid">
      <div class="target">
        <div class="title">{{ target.title }}</div>
        <div class="desc">{{ target.description }}</div>
      </div>
    </a>
    <div class="modal fade" :id="target.tid" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
              &times;
            </button>
          </div>
          <div class="modal-title">
            <h1>{{ target.title }}</h1>
          </div>
          <div class="modal-body">
            <span class="statement">{{ this.prompt }}</span>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {tryCheckIn} from "../api/api";

  export default {
    props: {
      target: {
        type: Object
      }
    },
    methods: {
      checkin() {
        tryCheckIn(this.target.tid, {
          uid: this.target.uid,
          tid: this.target.tid,
          checkinDateTime: new Date(Date.now() - this.tzOffset).toISOString()
          // checkinDateTime: 'asdasda'
        }).then(res => {
          if (res.status === 200) {
            this.prompt = res.data.content;
            $('#' + this.target.tid).modal();
            console.log('start setting color');
            $('#target' + this.target.tid + ' > div').css('background', '#ffd633');
          }
        }).catch(error => {
          if (error.response) {
            console.log('Error response: ' + error.response);
            this.prompt = error.response.data.content;
            $('#' + this.target.tid).modal();
          } else if (error.request) {
            console.log('Error request: ' + error.request);
          } else {
            console.log('Error: ' + error.message);
          }
          console.log(error.config);
        })
      }
    },
    data() {
      return {
        tzOffset: new Date().getTimezoneOffset() * 60000,
        prompt: ''
      }
    }
  }
</script>
