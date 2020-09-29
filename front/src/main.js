import Vue from 'vue'
import Vuelidate from 'vuelidate'
import Paginate from 'vuejs-paginate'
import App from './App.vue'
import Axios from 'axios'
import router from './router'
import store from './store'
import http from '@/http-commons'
import './registerServiceWorker'
import messagePlugin from '@/utills/message.plugin'
import dateFilter from '@/filters/date.filter'
import 'materialize-css/dist/js/materialize.min'

Vue.prototype.$http = Axios;
const token = localStorage.getItem('token')
if (token) {
  Vue.prototype.$http.defaults.headers.common['Authorization'] = token
}

Vue.config.productionTip = false

Vue.use(Vuelidate)
Vue.use(messagePlugin)
Vue.filter('date', dateFilter)
Vue.component('paginate', Paginate)


new Vue({
  router,
  store,
  render: h => h(App),

}).$mount('#app')
