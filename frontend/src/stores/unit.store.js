import {defineStore} from 'pinia';
import {apiWrapper} from '@/helpers';

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
      const units = await apiWrapper.get(`${API_URL}/unit`);
      this.units = units.data;
    },

    async add(data) {
      const units = await apiWrapper.post(`${API_URL}/unit`, data);
      this.units.push(units);
      return units;
    }
  }

})
