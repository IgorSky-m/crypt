import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import info from './info'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    status: '',
    baseURL: 'http://localhost:9000',
    token: localStorage.getItem('token'),
    isAuth: localStorage.getItem('isAuth'),
    user : {},
    wallets: [],
    totalAmount : '',
    transactions: [],
    transactionDetail: {},
    wallet: null

  },
  mutations: {
    setToken: (state, value) => {
      state.token = value;
    },
    setUser: (state, user) => {
      state.user = user;
    }

  },
  actions: {
    setToken: ({commit}, value) => {
      commit("setToken", value);
    },
    setUser: ({commit}, user) => {
      commit("setUser", user)
    }

  },
  getters: {
    userName: s => s.userName,
    user: s => s.user,


  },
  setters: {

  },
  modules: {
  }
})
