import { defineStore } from 'pinia';
import { http } from '@/http';

export const useMemberStore = defineStore({
  id: 'member',

  state: () => ({
    members: [],
    member: {}
  }),

  getters: {
    listAll() {
      return this.members;
    }
  },

  actions: {
    async add(data) {
      const member = await http.post("member", data);
      this.members.push(member);
      return this.members;
    },

    async fetchAll() {
      const members = await http.get("member");
      this.members = members.data;
      console.log(members.data)
    },

    async search(data) {
      try {
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
