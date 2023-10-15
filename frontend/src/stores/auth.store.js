import { defineStore } from 'pinia';

import { apiWrapper } from '@/helpers';
import router from '@/router'

const API_URL = import.meta.env.VITE_API_URL;

export const useAuthStore = defineStore({
  id: 'auth',
  state: () => ({
    // initialize state from local storage to enable user to stay logged in
    user: JSON.parse(localStorage.getItem('token')),
    returnUrl: null
  }),
  actions: {
    async login(data) {
      const user = await apiWrapper.post(`${API_URL}/auth`, data);

      // update pinia state
      this.user = user;

      // store user details and jwt in local storage to keep user logged in between page refreshes
      localStorage.setItem('token', JSON.stringify(user));

      // redirect to previous url or default to home page
      router.push(this.returnUrl || '/');
    },
    logout() {
      this.user = null;
      localStorage.removeItem('user');
      router.push('/login');
    }
  }
});
