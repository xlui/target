import Vue from 'vue';
import Vuex from 'vuex';
import * as getters from './getters';
import * as actions from './actions';

Vue.use(Vuex);

const state = {
  targets: []
};

const mutations = {
  UPDATE(state, data) {
    state.targets = data;
  }
};

export default new Vuex.Store({
  state,
  getters,
  actions,
  mutations
});
