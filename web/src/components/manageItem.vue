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
        <el-col :span="23" :offset="1">
          <el-form-item label="Repeat">
            <el-checkbox-group v-model="showRepeat">
              <el-checkbox label="64">Sun</el-checkbox>
              <el-checkbox label="32">Mon</el-checkbox>
              <el-checkbox label="16">Tue</el-checkbox>
              <el-checkbox label="8">Wed</el-checkbox>
              <el-checkbox label="4">Thu</el-checkbox>
              <el-checkbox label="2">Fri</el-checkbox>
              <el-checkbox label="1">Sat</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-col>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="medium" @click="showDialog = false">关闭</el-button>
        <el-button type="primary" size="medium" @click="updateTarget">提交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import {putTarget} from "../api/api";
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
        bgColor: '#fff',
      }
    },
    computed: {
      showRepeat: {
        get() {
          const ret = [];
          for (const value of weekMap) {
            if ((value & this.target.repeat) === value) {
              ret.push(String(value))
            }
          }
          return ret;
        },
        set(value) {
          let ret = 0;
          for (const r of value) {
            ret += Number(r)
          }
          this.target.repeat = ret;
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
      }
    },
    created() {
      showColor(this);
    }
  }
</script>
