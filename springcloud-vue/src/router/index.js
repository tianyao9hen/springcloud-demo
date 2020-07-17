import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

  const routes = [
      // {
      //     path: '/',
      //     redirect: '/dashboard',
      // },
      {
          path: '/login',
          component: resolve => require(['views/common/Login'],resolve),
      },
      {
          path: '/',
          component: resolve => require(['components/common/Home'],resolve),
          children: [
              {
                  path: '/dashboard',
                  component: resolve => require(['views/common/dashboard'],resolve),
                  meta: {title: '系统首页'}
              }
          ]
      },
]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router
