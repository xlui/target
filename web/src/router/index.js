import Vue from 'vue'
import Router from 'vue-router'
import CheckIn from '@/components/checkin'
import Manage from '@/components/manage'
import Yesterday from '@/components/yesterday'

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'CheckIn',
      component: CheckIn
    },
    {
      path: '/manage',
      name: 'Manage',
      component: Manage
    },
    {
      path: '/yesterday',
      name: 'Yesterday',
      component: Yesterday
    }
  ]
})
