import {fetchCheckIn} from './api';

const offset = new Date().getTimezoneOffset() * 60000;

export const now = () => new Date(Date.now() - offset);

export const showColor = function (vue) {
  fetchCheckIn(
    vue.target.tid,
    now().toISOString().split('T')[0]
  ).then(res => {
    if (res.status === 200 && res.data.status === 'OK') {
      vue.bgColor = '#ffd633';
    }
  }).catch(error => {
    vue.bgColor = '#fff';
  })
};
