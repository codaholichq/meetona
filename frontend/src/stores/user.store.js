import { defineStore } from 'pinia';
import { http } from '@/http';

export const useUserStore = defineStore({
  id: 'user',

  state: () => ({
    users: []
  }),

  getters: {
    getById(id) {
      const user = http.get("user", id)
      this.users.push(user)
      return user.data.id;
    }
  },

  actions: {
    async add(data) {
      const user = await http.post("user", data);
      this.users.push(user);
      return this.user;
    }
  }

})
