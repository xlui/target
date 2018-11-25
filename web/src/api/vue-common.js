import {fetchCheckIn} from './api';

export const showColor = function (vue) {
  fetchCheckIn(
    vue.target.tid,
    new Date(Date.now() - new Date().getTimezoneOffset() * 60000).toISOString().split('T')[0]
  ).then(res => {
    if (res.status === 200) {
      vue.bgColor = '#ffd633';
    }
  }).catch(error => {
    vue.bgColor = '#fff';
  })
};
