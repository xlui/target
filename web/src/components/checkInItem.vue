<template>
  <div>
    <a href="#" data-toggle="modal" :data-target="'#' + target.tid">
      <div class="target">
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
            <span class="statement">打卡成功！</span>
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
  export default {
    props: {
      target: {
        type: Object
      }
    },
    methods: {}
  }
</script>
