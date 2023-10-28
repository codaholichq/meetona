import {defineStore} from 'pinia';
import {httpService} from '@/services';

const API_URL = import.meta.env.VITE_API_URL;

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
      const units = await httpService.get(`${API_URL}/unit`);
      this.units = units.data;
    },

    async add(data) {
      const units = await httpService.post(`${API_URL}/unit`, data);
      this.units.push(units);
      return units;
    }
  }

})
