import { defineStore } from 'pinia';
import { apiWrapper } from '@/helpers';

const API_URL = import.meta.env.VITE_API_URL;

export const useUserStore = defineStore({
  id: 'user',

  state: () => ({
    users: []
  }),

  getters: {
    getById(id) {
      const user = apiWrapper.get(`${API_URL}/user`, id)
      this.users.push(user)
      return user.data.id;
    }
  },

  actions: {
    async add(data) {
      const user = await apiWrapper.post(`${API_URL}/user`, data);
      this.users.push(user);
      return this.user;
    }
  }

})
