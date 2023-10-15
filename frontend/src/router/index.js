import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.API_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      alias: "/login",
      meta: {
        requiresAuth: false
      },
      component: () => import('@/views/Login.vue')
    },

    {
      path: '/admin',
      name: 'admin',
      meta: { requiresAuth: true },
      component: () => import('@/views/Admin.vue')
    },

    {
      path: '/:pathMatch(.*)*',
      // component: NotFound
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth) {
    const token = localStorage.getItem('token');
    if (token) {
      // User is authenticated, proceed to the route
      next();
    } else {
      // User is not authenticated, redirect to login
      next('/login');
    }
  } else {
    // Non-protected route, allow access
    next();
  }
});

export default router
