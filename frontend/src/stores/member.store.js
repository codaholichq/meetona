import { defineStore } from 'pinia';
import { apiWrapper } from '@/helpers';

const API_URL = import.meta.env.VITE_API_URL;

export const useMemberStore = defineStore({
  id: 'member',

  state: () => ({
    members: []
  }),

  actions: {
    async add(data) {
      const member = await apiWrapper.post(`${API_URL}/member`, data);
      this.members = member;
      return member;
    }
  }

})
