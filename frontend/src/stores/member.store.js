import { defineStore } from 'pinia';
import { httpService } from '@/services';

const API_URL = import.meta.env.VITE_API_URL;

export const useMemberStore = defineStore({
  id: 'member',

  state: () => ({
    members: []
  }),

  getters: {
    getById(id) {
      const member = httpService.get(`${API_URL}/member`, id)
      this.members.push(member)
      return member.data.id;
    }
  },

  actions: {
    async add(data) {
      const member = await httpService.post(`${API_URL}/member`, data);
      this.members.push(member);
      return this.members;
    }
  }

})
