<template>
  <div>
    <a href="#" @click="showCheckIn">
      <div class="target" :style="{background: bgColor}">
        <div class="title">{{ target.title }}</div>
        <div class="desc">{{ target.description }}</div>
      </div>
    </a>
    <modal v-if="showModal" @close="showModal = false">
      <!-- todo: show target details here -->
      <p slot="body">This is the details page of the checkin</p>
    </modal>
  </div>
</template>

<script>
  import {showColor} from "../api/util";
  import modal from './modal';

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
      showCheckIn() {
        this.showModal = true;
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
