<template>
  <div>
    <a @click="showDialog = true">
      <div class="target" :style="{background: bgColor}">
        <div class="title">{{ target.title }}</div>
        <div class="desc">{{ target.description }}</div>
      </div>
    </a>
    <el-dialog :title="target.title" :visible.sync="showDialog" width="45%">
      <el-form ref="form" label-position="left" label-width="150px">
        <el-col :span="23" :offset="1">
          <el-form-item label="Title">
            <el-input v-model="target.title"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="23" :offset="1">
          <el-form-item label="Description">
            <el-input v-model="target.description"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="23" :offset="1">
          <el-form-item label="Start date">
            <el-col :span="6">
              <el-date-picker type="date" v-model="target.startDate" :picker-options="dateStart"></el-date-picker>
            </el-col>
          </el-form-item>
        </el-col>
        <el-col :span="23" :offset="1">
          <el-form-item label="End date">
            <el-col :span="6">
              <el-date-picker type="date" v-model="target.endDate" :picker-options="dateEnd"></el-date-picker>
            </el-col>
          </el-form-item>
        </el-col>
        <el-col :span="23" :offset="1">
          <el-form-item label="Check in start">
            <el-col :span="6">
              <el-time-picker type="fixed-time" value-format="HH:mm:ss" v-model="target.checkinStart"></el-time-picker>
            </el-col>
          </el-form-item>
        </el-col>
        <el-col :span="23" :offset="1">
          <el-form-item label="Check in end">
            <el-col :span="6">
              <el-time-picker type="fixed-time" value-format="HH:mm:ss" v-model="target.checkinEnd"></el-time-picker>
            </el-col>
          </el-form-item>
        </el-col>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="danger" size="medium" @click="deleteTarget">Delete</el-button>
        <el-button type="info" size="medium" @click="getStatistics">Statistics</el-button>
        <el-button type="primary" size="medium" @click="updateTarget">Submit</el-button>
      </span>
    </el-dialog>
    <el-dialog :title="target.title" :visible.sync="showStatistics" width="25%">
      <el-form ref="form" label-position="left" label-width="180px" style="text-align: left">
        <el-col :offset="1" :span="23">
          <el-form-item label="Checked">
            <el-tag>{{ statistics.checked }} {{ statistics.checked === 1 ? 'Day' : 'Days'}}</el-tag>
          </el-form-item>
        </el-col>
        <el-col :offset="1" :span="23">
          <el-form-item label="Continuous">
            <el-tag type="danger">{{ statistics.continuous }} {{ statistics.continuous === 1 ? 'Day' : 'Days'}}</el-tag>
          </el-form-item>
        </el-col>
        <el-col :offset="1" :span="23">
          <el-form-item label="Left to Done">
            <el-tag>{{ statistics.leftToDone }} {{ statistics.leftToDone === 1 ? 'Day' : 'Days'}}</el-tag>
          </el-form-item>
        </el-col>
        <el-col :offset="1" :span="23">
          <el-form-item label="Longest Continuous">
            <el-tag type="danger">{{ statistics.longestContinuous }} {{ statistics.longestContinuous === 1 ? 'Day' :
              'Days'}}
            </el-tag>
          </el-form-item>
        </el-col>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="danger" size="medium" @click="showStatistics = false">Close</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import {putTarget, fetchStatistics, removeTarget} from "../api/api";
  import {home, manage, showColor, weekMap} from "../api/util";

  export default {
    props: {
      target: {
        type: Object
      }
    },
    data() {
      return {
        showDialog: false,
        showStatistics: false,
        bgColor: '#fff',
        statistics: {},
        dateStart: {
          disabledDate: (time) => {
            return time.getTime() > new Date(this.target.endDate);
          }
        },
        dateEnd: {
          disabledDate: (time) => {
            return time.getTime() < new Date(this.target.startDate);
          }
        }
      }
    },
    methods: {
      updateTarget() {
        putTarget(this.target.tid, this.target).then(res => {
          if (res.status === 204) {
            alert('Successfully update target!');
            this.showDialog = false;
            location.href = manage
          }
        }).catch(error => {
          console.log('Catch error: ' + error)
        });
      },
      getStatistics() {
        fetchStatistics(this.target.tid).then(res => {
          if (res.data.status === 'OK') {
            this.statistics = res.data.content;
            this.showStatistics = true;
          }
        })
      },
      deleteTarget() {
        removeTarget(this.target.tid).then(res => {
          alert(res.data.content)
          location.href = home
        }).catch(error => {
          alert(error.response.data.content)
        })
      }
    },
    created() {
      showColor(this);
    }
  }
</script>
