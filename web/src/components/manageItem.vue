<template>
  <div>
    <a @click="showDialog = true">
      <div class="target" :style="{background: bgColor}">
        <div class="title">{{ target.title }}</div>
        <div class="desc">{{ target.description }}</div>
      </div>
    </a>
    <el-dialog :title="target.title" :visible.sync="showDialog">
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
              <el-date-picker type="date" v-model="target.startDate"></el-date-picker>
            </el-col>
          </el-form-item>
        </el-col>
        <el-col :span="23" :offset="1">
          <el-form-item label="End date">
            <el-col :span="6">
              <el-date-picker type="date" v-model="target.endDate"></el-date-picker>
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
        <el-button type="info" size="medium" @click="getStatistics">统计信息</el-button>
        <el-button type="danger" size="medium" @click="showDialog = false">关闭</el-button>
        <el-button type="primary" size="medium" @click="updateTarget">提交</el-button>
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
        <el-button type="danger" size="medium" @click="showStatistics = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import {putTarget, fetchStatistics} from "../api/api";
  import {manage, showColor, weekMap} from "../api/util";

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
        statistics: {}
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
      }
    },
    created() {
      showColor(this);
    }
  }
</script>
