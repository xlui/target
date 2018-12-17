import {fetchTargets} from "../api/api";

export const fetchFilterTargets = ({commit}) => {
  return new Promise((resolve) => {
    fetchTargets().then(res => {
      if (res.data.status === 'OK') {
        commit('UPDATE', res.data.content);
        resolve();
      }
    })
  })
};

export const fetchAllTargets = ({commit}) => {
  return new Promise((resolve) => {
    fetchTargets(false).then(res => {
      if (res.data.status === 'OK') {
        commit('UPDATE', res.data.content);
        resolve();
      }
    })
  })
};
