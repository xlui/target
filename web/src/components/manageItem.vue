<template>
  <div>
    <a @click="showModal = true">
      <div class="target" :style="{background: bgColor}">
        <div class="title">{{ target.title }}</div>
        <div class="desc">{{ target.description }}</div>
      </div>
    </a>
    <modal class="manage" v-if="showModal" @close="showModal = false">
      <h2 slot="header">{{ target.title }}</h2>
      <form role="form" class="form-horizontal" slot="body">
        <div class="form-group form-item">
          <label for="title" class="col-sm-offset-1 col-sm-3">Title:</label>
          <div class="col-sm-7">
            <input type="text" id="title" class="form-control" v-model="target.title"/>
          </div>
        </div>
        <div class="form-group form-item">
          <label for="description" class="col-sm-offset-1 col-sm-3">Description:</label>
          <div class="col-sm-7">
            <input type="text" id="description" class="form-control" v-model="target.description"/>
          </div>
        </div>
        <div class="form-group form-item">
          <label for="startDate" class="col-sm-offset-1 col-sm-3">Start date:</label>
          <div class="col-sm-7">
            <input type="date" id="startDate" class="form-control" v-model="target.startDate"/>
          </div>
        </div>
        <div class="form-group form-item">
          <label for="endDate" class="col-sm-offset-1 col-sm-3">End date:</label>
          <div class="col-sm-7">
            <input type="date" id="endDate" class="form-control" v-model="target.endDate"/>
          </div>
        </div>
        <div class="form-group form-item">
          <label for="checkinStart" class="col-sm-offset-1 col-sm-3">Check in start:</label>
          <div class="col-sm-7">
            <input type="time" id="checkinStart" class="form-control" v-model="target.checkinStart"/>
          </div>
        </div>
        <div class="form-group form-item">
          <label for="checkinEnd" class="col-sm-offset-1 col-sm-3">Check in end:</label>
          <div class="col-sm-7">
            <input type="time" id="checkinEnd" class="form-control" v-model="target.checkinEnd"/>
          </div>
        </div>
        <div class="form-group form-item">
          <label for="repeat" class="col-sm-offset-1 col-sm-3">Repeat:</label>
          <div class="col-sm-7">
            <input type="text" id="repeat" class="form-control" v-model="target.repeat"/>
          </div>
        </div>
      </form>
      <div slot="footer">
        <button type="button" class="btn btn-primary" @click="updateTarget">提交</button>
        <button type="button" class="btn btn-default" @click="showModal = false">关闭</button>
      </div>
    </modal>
  </div>
</template>

<script>
  import {putTarget} from "../api/api";
  import {manage, showColor} from "../api/util";
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
        bgColor: '#fff',
      }
    },
    methods: {
      updateTarget() {
        putTarget(this.target.tid, this.target)
          .then(res => {
            if (res.status === 204) {
              alert('Successfully update target!');
              this.showModal = false;
            }
          })
          .catch(error => {
            console.log('Catch error: ' + error)
          });
      }
    },
    components: {
      modal,
    },
    created() {
      showColor(this);
    }
  }
</script>
