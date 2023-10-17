import { defineStore } from 'pinia';
import { apiWrapper } from '@/helpers';
import router from '@/router'

const API_URL = import.meta.env.VITE_API_URL;

export const useAuthStore = defineStore({
  id: 'auth',

  state: () => ({
    user: JSON.parse(localStorage.getItem('token')),
    returnUrl: null
  }),

  actions: {
    async login(data) {
      const user = await apiWrapper.post(`${API_URL}/auth`, data);

      this.user = user;
      localStorage.setItem('token', JSON.stringify(user));

      router.push(this.returnUrl || '/');
    },

    async logout() {
      this.user = null;
      localStorage.removeItem('token');
      router.push('/login');
    }
  }
});
