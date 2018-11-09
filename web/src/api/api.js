import axios from 'axios';

const host = 'http://127.0.0.1:8080';
const loginUrl = host + '/login';

export const login = params => {
  console.log('login use: ' + JSON.stringify(params));
  return axios({
    method: 'post',
    url: loginUrl,
    headers: {
      'Content-Type': 'application/json'
    },
    data: JSON.stringify(params)
  }).then(res => res.data)
};

axios.interceptors.request.use(config => {
  return config
}, error => {
  alert('请求超时！');
  return Promise.resolve(error)
});

axios.interceptors.response.use(data => {
  return data
}, error => {
  // alert('status: ' + error.response.status + '\nmessage: ' + error.response.data);
  alert('error: ' + error);
  return Promise.resolve(error);
});
