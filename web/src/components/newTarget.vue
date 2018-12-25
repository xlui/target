<template>
  <div>
    <a href="#" @click="showDialog = true">
      <div class="target">
        <div class="title">New</div>
        <div class="desc">Add a new target!</div>
      </div>
    </a>
    <el-dialog title="Add a new target!" width="40%" :visible="showDialog" @close="showDialog = false">
      <el-form ref="form" label-position="left" label-width="100px">
        <el-col :span="23" :offset="1">
          <el-form-item label="Title">
            <el-input v-model="newTarget.title"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="23" :offset="1">
          <el-form-item label="Description">
            <el-input v-model="newTarget.description"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="23" :offset="1">
          <el-form-item label="Start date">
            <el-col :span="6">
              <el-date-picker type="date"
                              placeholder="Choose the start date of target"
                              v-model="newTarget.startDate"
                              :picker-options="dateStart"
              ></el-date-picker>
            </el-col>
          </el-form-item>
        </el-col>
        <el-col :span="23" :offset="1">
          <el-form-item label="End date">
            <el-col :span="6">
              <el-date-picker type="date"
                              placeholder="Choose the end date of target"
                              v-model="newTarget.endDate"
                              :picker-options="dateEnd"
              ></el-date-picker>
            </el-col>
          </el-form-item>
        </el-col>
        <el-col :span="23" :offset="1">
          <el-form-item label="Check in start">
            <el-col :span="6">
              <el-time-picker type="fixed-time" placeholder="Choose the start time of check in"
                              v-model="newTarget.checkinStart"></el-time-picker>
            </el-col>
          </el-form-item>
        </el-col>
        <el-col :span="23" :offset="1">
          <el-form-item label="Check in end">
            <el-col :span="6">
              <el-time-picker type="fixed-time" placeholder="Choose to end time of check in"
                              v-model="newTarget.checkinEnd"></el-time-picker>
            </el-col>
          </el-form-item>
        </el-col>
      </el-form>
      <span slot="footer" class="dialog-footer">
            <el-button type="danger" size="medium" @click="showDialog = false">Close</el-button>
            <el-button type="primary" size="medium" @click="addTarget">Submit</el-button>
          </span>
    </el-dialog>
  </div>
</template>

<script>
  import {submitTarget} from "../api/api";
  import {adjust, extractTime, home, local} from "../api/util";

  export default {
    data() {
      return {
        newTarget: {
          startDate: new Date(),
          endDate: adjust(new Date(), 1),
          checkinStart: new Date(2018, 10, 26, 9, 0),
          checkinEnd: new Date(2018, 10, 26, 18, 0),
        },
        showDialog: false,
        dateStart: {
          disabledDate: (time) => {
            return time.getTime() > new Date(this.newTarget.endDate);
          }
        },
        dateEnd: {
          disabledDate: (time) => {
            return time.getTime() < new Date(this.newTarget.startDate);
          }
        }
      }
    },
    methods: {
      addTarget() {
        // extract time
        this.newTarget.checkinStart = extractTime(local(this.newTarget.checkinStart));
        this.newTarget.checkinEnd = extractTime(local(this.newTarget.checkinEnd));
        submitTarget(this.newTarget).then(res => {
          if (res.status === 201) {
            console.log('Successfully created new target!');
            alert(res.data.content);
            location.href = home;
          }
        }).catch(error => {
          console.log('Error while adding target: ', error);
          console.log(JSON.stringify(error));
          location.href = home;
        })
      }
    }
  }
</script>
