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
            <form role="form" class="form-inline">
              <div class="form-group">
                <label for="tid" class="control-label">tid:</label>
                <input id="tid" class="form-control" type="text" :value="target.tid" readonly/>
              </div>
              <div class="form-group">
                <label for="uid">uid:</label>
                <input id="uid" class="form-control" type="text" :value="target.uid" readonly/>
              </div>
              <div class="form-group">
                <label for="title">Title:</label>
                <input id="title" class="form-control" type="text" :value="target.title"/>
              </div>
              <div class="form-group">
                <label for="description">Description:</label>
                <input id="description" class="form-control" type="text" :value="target.description"/>
              </div>
              <div class="form-group">
                <label for="startDate">Start date:</label>
                <input id="startDate" class="form-control" type="text" :value="target.startDate"/>
              </div>
              <div class="form-group">
                <label for="endDate">End date:</label>
                <input id="endDate" class="form-control" type="text" :value="target.endDate"/>
              </div>
              <div class="form-group">
                <label for="checkinStart">Check in start:</label>
                <input id="checkinStart" class="form-control" type="text" :value="target.checkinStart"/>
              </div>
              <div class="form-group">
                <label for="checkinEnd">Check in end:</label>
                <input id="checkinEnd" class="form-control" type="text" :value="target.checkinEnd"/>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary">提交更改</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  // import {fetchTarget} from '../api/api'
  export default {
    props: {
      target: {
        type: Object
      }
    },
    methods: {}
  }
</script>
