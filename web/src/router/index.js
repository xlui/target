import Vue from 'vue'
import Router from 'vue-router'
import CheckIn from '@/components/checkin'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'CheckIn',
      component: CheckIn
    }
  ]
})
