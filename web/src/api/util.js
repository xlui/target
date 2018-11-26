import {fetchCheckIn} from './api';

const offset = new Date().getTimezoneOffset() * 60000;

export const home = '/';
export const manage = '/manage';

export const now = () => new Date(Date.now() - offset);

export const adjust = (date, months) => {
  date.setMonth(date.getMonth() + months);
  return date;
};

export const extractDate = date => date.toISOString().split('T')[0];

export const extractTime = date => date.toISOString().split('T')[1];

export const showColor = function (vue) {
  fetchCheckIn(
    vue.target.tid,
    extractDate(now())
  ).then(res => {
    if (res.status === 200 && res.data.status === 'OK') {
      vue.bgColor = '#ffd633';
    }
  }).catch(error => {
    vue.bgColor = '#fff';
  })
};
