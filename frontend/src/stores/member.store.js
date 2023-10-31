import { defineStore } from 'pinia';
import { http } from '@/http';

export const useMemberStore = defineStore({
  id: 'member',

  state: () => ({
    members: [],
    member: {}
  }),

  getters: {
    list() {
      return this.member;
    }
  },

  actions: {
    async add(data) {
      const member = await http.post("member", data);
      this.members.push(member);
      return this.members;
    },

    async search(data) {
      try {
        console.log(data)
        const response = await http.get(`member/search?email=${data}`);
        this.member = response.data
        return response.data;
      } catch (error) {
        console.error(error);
        throw error;
      }
    }
  }

})
