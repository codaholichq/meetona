import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'Login',
      alias: "/login",
      meta: {
        requiresAuth: false
      },
      component: () => import('@/views/auth/Login.vue')
    },

    {
      path: '/dashboard/member/add',
      name: 'AddMember',
      meta: { requiresAuth: true },
      component: () => import('@/views/members/AddMember.vue')
    },

    {
      path: '/dashboard/users/add',
      name: 'AddUser',
      meta: { requiresAuth: true },
      component: () => import('@/views/users/AddUser.vue')
    },

    {
      path: '/dashboard/units',
      name: 'GetUnits',
      meta: { requiresAuth: true },
      component: () => import('@/views/Units/GetUnits.vue')
    },

    {
      path: '/dashboard/units/add',
      name: 'AddUnit',
      meta: { requiresAuth: true },
      component: () => import('@/views/Units/AddUnit.vue')
    },

    {
      path: '/:pathMatch(.*)*',
      name: 'NotFound',
      meta: { requiresAuth: false },
      component: () => import('@/views/NotFound.vue')
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
