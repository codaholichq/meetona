<template>
  <table class="table">
    <thead>
      <tr>
        <th>Name</th>
        <th>Address</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="unit in units" :key="unit.id">
        <td>{{ unit.name }}</td>
        <td v-html="formatAddress(unit.address)"></td>
      </tr>
    </tbody>
  </table>
</template>

<script setup>
import { computed, onMounted } from 'vue';
import { useUnitStore } from '@/stores';

const unitStore = useUnitStore()
const unitData = computed(() => unitStore.listAll);
const units = unitData.value

onMounted(() => { unitStore.fetchAll() });

const formatAddress = (address) => {
  return address.split(',').join(',<br/>');
}
</script>

<style scoped>

</style>
