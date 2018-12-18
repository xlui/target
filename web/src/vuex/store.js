import Vue from 'vue';
import Vuex from 'vuex';
import * as getters from './getters';
import * as actions from './actions';

Vue.use(Vuex);

const state = {
  filterTargets: [],
  allTargets: []
};

const mutations = {
  FILTER(state, data) {
    state.filterTargets = data;
  },
  ALL(state, data) {
    state.allTargets = data;
  }
};

export default new Vuex.Store({
  state,
  getters,
  actions,
  mutations
});
