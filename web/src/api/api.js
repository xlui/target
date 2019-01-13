import axios from 'axios';

const host = `${process.env.NODE_ENV === 'production' ? 'https://target.xlui.app':'http://localhost:8080'}`;
const targetBaseUrl = `${host}/target`;
const contentType = 'application/json;charset=utf-8';

export const submitLogin = params => {
  let login = `${host}/login`;
  return axios.post(login, JSON.stringify(params), {
    headers: {
      'Content-Type': contentType
    }
  })
};

export const fetchTargets = (filter = true) => {
  return axios.get(targetBaseUrl, {
    params: {
      filter: filter
    },
    headers: {
      Authorization: localStorage.token
    }
  });
};

export const submitTarget = params => {
  return axios.post(targetBaseUrl, JSON.stringify(params), {
    headers: {
      'Content-Type': contentType,
      Authorization: localStorage.token
    }
  })
};

export const fetchTarget = tid => {
  let specifyTarget = `${targetBaseUrl}/${tid}`;
  return axios.get(specifyTarget, {
    headers: {
      Authorization: localStorage.token
    }
  });
};

export const putTarget = (tid, params) => {
  let specifyTarget = `${targetBaseUrl}/${tid}`;
  return axios.put(specifyTarget, JSON.stringify(params), {
    headers: {
      Authorization: localStorage.token,
      'Content-Type': contentType
    }
  })
};

export const submitCheckIn = (tid, params) => {
  let checkin = `${targetBaseUrl}/${tid}/checkin`;
  return axios.post(checkin, JSON.stringify(params), {
    headers: {
      'Content-Type': contentType,
      Authorization: localStorage.token
    }
  });
};

export const fetchCheckIn = (tid, time) => {
  let someday = `${targetBaseUrl}/${tid}/checkin/${time.getFullYear()}/${time.getMonth() + 1}/${time.getDate()}`;
  return axios.get(someday, {
    headers: {
      Authorization: localStorage.token
    }
  });
};

export const fetchStatistics = tid => {
  return axios.get(`${targetBaseUrl}/${tid}/statistics`, {
    headers: {
      Authorization: localStorage.token
    }
  })
};

export const fetchWeekly = date => {
  return axios.get(`${host}/weekly/${date.getFullYear()}/${date.getMonth() + 1}/${date.getDate()}`, {
    headers: {
      Authorization: localStorage.token
    }
  })
};

export const fetchRank = epoch => {
  return axios.get(`${host}/rank/${epoch}`, {
    headers: {
      Authorization: localStorage.token
    }
  })
};

export const fetchJourney = () => {
  return axios.get(`${host}/journey`, {
    headers: {
      Authorization: localStorage.token
    }
  })
};

export const checkToken = token => {
  return axios.get(`${host}/token`, {
    headers: {
      Authorization: token
    }
  })
};

axios.interceptors.request.use(config => {
  return config
}, error => {
  alert('请求超时！');
  return Promise.resolve(error)
});
