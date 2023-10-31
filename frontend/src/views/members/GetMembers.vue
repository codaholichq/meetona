<template>
  <table class="table">
    <thead>
      <tr>
        <th class="text-nowrap">Name</th>
        <th class="text-nowrap">Gender</th>
        <th class="text-nowrap">Phone Number</th>
        <th class="text-nowrap">Marital Status</th>
        <th class="text-nowrap">Unit</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="member in members" :key="member.id">
        <td class="text-nowrap">{{ member.name }}</td>
        <td class="text-nowrap">{{ convertToSentenceCase(member.gender) }}</td>
        <td class="text-nowrap">{{ member.phoneNumber }}</td>
        <td class="text-nowrap">{{ convertToSentenceCase(member.maritalStatus) }}</td>
        <td class="text-nowrap">{{ member.unit }}</td>
      </tr>
    </tbody>
  </table>
</template>

<script setup>
import { computed, onMounted } from 'vue';
import { useMemberStore } from '@/stores';

const memberStore = useMemberStore()

const memberData = computed(() => memberStore.listAll);
const members = computed(() => {
  return memberData.value.map(member => ({
    ...member,
    name: `${member.firstName} ${member.middleName.charAt(0)}. ${member.lastName}`
  }));
});

const convertToSentenceCase = (str) => {
  return str.toLowerCase().split(' ')
    .map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ');
}

onMounted(() => { memberStore.fetchAll() });
</script>

<style scoped>
</style>
