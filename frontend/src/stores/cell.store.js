import { defineStore } from 'pinia';
import { ref, computed, readonly } from 'vue';
import { http } from '@/http';

export const useCellStore = defineStore('cell', () => {
  // State
  const cells = ref([]);

  // Getters
  const listAll = computed(() => cells.value);

  // Actions
  async function fetchAll() {
    try {
      const response = await http.get("cell");
      cells.value = response.data;
    } catch (error) {
      console.error('Failed to fetch cells:', error);
      throw error;
    }
  }

  async function add(data) {
    try {
      const response = await http.post("cell", data);
      cells.value.push(response.data); // Push the data, not the entire response
      return response.data;
    } catch (error) {
      console.error('Failed to add cell:', error);
      throw error;
    }
  }

  return {
    cells: readonly(cells), // Make state readonly from outside
    listAll,
    fetchAll,
    add
  };
});
