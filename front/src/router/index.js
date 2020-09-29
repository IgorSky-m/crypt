import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store/index'


Vue.use(VueRouter)

const routes = [

  {
    path: '/login',
    name: 'login',
    meta: {
      layout: 'empty'
    },
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/register',
    name: 'register',
    meta: {
      layout: 'empty'
    },
    component: () => import('@/views/Register.vue')
  },
  {
    path: '/home',
    name: 'home',
    meta: {
      layout: 'main',
      auth: true
    },
    component: () => import('@/views/Home.vue')
  },
  {
    path: '/history/:hash',
    name: 'transaction',
    meta: {
      layout: 'main',
      auth: true
    },
    component: () => import('@/views/TransactDetails.vue')
  },
  {
    path: '/wallets',
    name: 'wallets',
    meta: {
      layout: 'main',
      auth: true
    },
    component: () => import('@/views/Wallets.vue')
  },
  {
    path: '/profile',
    name: 'profile',
    meta: {
      layout: 'main',
      auth: true
    },
    component: () => import('@/views/Profile.vue')
  },
  {
    path: '/record',
    name: 'record',
    meta: {
      layout: 'main',
      auth: true
    },
    component: () => import('@/views/Record.vue')
  },
  {
    path: '/history',
    name: 'history',
    meta: {
      layout: 'main',
      auth: true
    },
    component: () => import('@/views/History.vue')
  },
  {
    path: '/details',
    name: 'details',
    meta: {
      layout: 'main',
      auth: true
    },
    component: () => import('@/views/Details.vue')
  },
  {
    path: '/mining',
    name: 'mining',
    meta: {
      layout: 'main',
      auth: true
    },
    component: () => import('@/views/Mining.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


router.beforeEach((to, from, next) => {

  if(to.matched.some(record => record.meta.auth)) {
    if (localStorage.getItem('isAuth') && localStorage.getItem('token') != null   ) {
      next()
      return
    }
    next('/login')
  } else {
    next()
  }
})




export default router
