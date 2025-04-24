import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'Login',
      alias: "/login",
      meta: { requiresAuth: false },
      component: () => import('@/views/auth/Login.vue')
    },

    {
      path: '/dashboard/members/add',
      name: 'AddMember',
      meta: { requiresAuth: true },
      component: () => import('@/views/members/AddMember.vue')
    },

    {
      path: '/dashboard/members',
      name: 'GetMembers',
      meta: { requiresAuth: true },
      component: () => import('@/views/members/GetMembers.vue')
    },

    {
      path: '/dashboard/users/add',
      name: 'AddUser',
      meta: { requiresAuth: true },
      component: () => import('@/views/users/AddUser.vue')
    },

    {
      path: '/dashboard/users',
      name: 'GetUsers',
      meta: { requiresAuth: true },
      component: () => import('@/views/users/GetUsers.vue')
    },

    {
      path: '/dashboard/cells',
      name: 'GetCells',
      meta: { requiresAuth: true },
      component: () => import('@/views/Cells/GetCells.vue')
    },

    {
      path: '/dashboard/cells/add',
      name: 'AddCells',
      meta: { requiresAuth: true },
      component: () => import('@/views/Cells/AddCell.vue')
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
