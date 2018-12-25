import {fetchCheckIn} from './api';

const offset = new Date().getTimezoneOffset() * 60000;

export const home = '/';
export const manage = '/manage';
export const yesterday = '/yesterday';
export const weekMap = [
  0b01000000, // Sunday
  0b00100000, // Monday
  0b00010000, // Tuesday
  0b00001000, // Wednesday
  0b00000100, // Thursday
  0b00000010, // Friday
  0b00000001, // Saturday
];

export const nowISO = () => new Date(Date.now() - offset);

export const nowLocal = () => new Date();

export const local = date => new Date(date - offset);

export const adjust = (date, months) => {
  date.setMonth(date.getMonth() + months);
  return date;
};

export const extractDate = date => date.toISOString().split('T')[0];

export const extractTime = date => date.toISOString().split('T')[1].split('.')[0];

export const showColor = function (vue) {
  fetchCheckIn(
    vue.target.tid,
    nowLocal()
  ).then(res => {
    if (res.status === 200 && res.data.status === 'OK') {
      vue.bgColor = '#ffd633';
    }
  }).catch(error => {
    vue.bgColor = '#fff';
  })
};
