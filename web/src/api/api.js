import axios from 'axios';

const host = 'http://127.0.0.1:8080';
const loginUrl = host + '/login';
const targetUrl = host + '/target';
const checkinUrl = host + '/target/tid/checkin';

export const apiLogin = params => {
  return axios({
    method: 'post',
    url: loginUrl,
    headers: {
      'Content-Type': 'application/json'
    },
    data: JSON.stringify(params)
  });
};

export const fetchTargets = params => {
  return axios.get(targetUrl, {
    params: params,
    headers: {
      Authorization: localStorage.token
    }
  });
};

export const fetchTarget = params => {
  return axios.get(targetUrl + '/' + params, {
    headers: {
      Authorization: localStorage.token
    }
  });
};

export const submitTarget = param => {
  return axios({
    method: 'post',
    url: targetUrl,
    headers: {
      Authorization: localStorage.token,
      'Content-Type': 'application/json'
    },
    data: JSON.stringify(param)
  });
};

export const submitCheckIn = (tid, params) => {
  return axios.post(checkinUrl.replace('tid', tid), JSON.stringify(params), {
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
      Authorization: localStorage.token
    }
  });
};

export const fetchCheckIn = (tid, time) => {
  return axios.get(checkinUrl.replace('tid', tid) + '/' + time, {
    headers: {
      Authorization: localStorage.token
    }
  });
};

axios.interceptors.request.use(config => {
  return config
}, error => {
  alert('请求超时！');
  return Promise.resolve(error)
});

// axios.interceptors.response.use(data => {
//   return data
// }, error => {
//   // alert('status: ' + error.response.status + '\nmessage: ' + error.response.data);
//   // console.log('error json: ' + JSON.stringify(error));
//   console.log('Global error interceptor catch error: ' + error);
//   // return Promise.resolve(error);
// });
