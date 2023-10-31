import {defineStore} from 'pinia';
import { http } from '@/http';

export const useUnitStore = defineStore({
  id: 'unit',

  state: () => ({
    units: []
  }),

  getters: {
    listAll() {
      return this.units;
    }
  },

  actions: {
    async fetchAll() {
      const units = await http.get("unit");
      this.units = units.data;
    },

    async add(data) {
      const units = await http.post("unit", data);
      this.units.push(units);
      return units;
    }
  }

})
