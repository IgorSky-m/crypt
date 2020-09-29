import axios from "axios"
import store from './store'

axios.defaults.headers.common['Authorization'] = localStorage.getItem('token');



export default axios.create({

  // local url change when build production
  baseURL: "http://localhost:9000/",
  Authorization: localStorage.getItem('token')
});
