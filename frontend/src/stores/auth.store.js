import { defineStore } from 'pinia';
import { jwtDecode } from "jwt-decode";
import { http } from '@/http';
import router from '@/router'

export const useAuthStore = defineStore({
  id: 'auth',

  state: () => ({
    user: JSON.parse(localStorage.getItem('token')),
    returnUrl: null
  }),

  getters: {
    isLoggedIn() {
      const user = this.user;
      if (user !== null) {
        const decoded = jwtDecode(user.data.accessToken);
        const currentTime = Math.floor(Date.now() / 1000);

        if (decoded.exp && decoded.exp > currentTime) return true;
      }
      this.user = null;
      localStorage.removeItem('token');
      return false;
    },

    isAdmin() {
      const user = this.user;
      return user !== null ? user.data.roles.includes("ADMIN") : false;
    },

    username() {
      const user = this.user;
      return user !== null ? user.data.username : null;
    }
  },

  actions: {
    async login(data) {
      const user = await http.post("auth", data);

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
