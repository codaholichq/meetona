<template>
  <table class="table">
    <thead>
      <tr>
        <th class="text-nowrap">Name</th>
        <th class="text-nowrap">Gender</th>
        <th class="text-nowrap">Phone Number</th>
        <th class="text-nowrap">Marital Status</th>
        <th class="text-nowrap">Cell</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="member in members" :key="member.id">
        <td class="text-nowrap">{{ member.name }}</td>
        <td class="text-nowrap">{{ convertToSentenceCase(member.gender) }}</td>
        <td class="text-nowrap">{{ member.phoneNumber }}</td>
        <td class="text-nowrap">{{ convertToSentenceCase(member.maritalStatus) }}</td>
        <td class="text-nowrap">{{ member.cell }}</td>
      </tr>
    </tbody>
  </table>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { memberService } from '@/services';

const message = ref('');
const members = ref(null);

onMounted(() => { getMembers() });

const getMembers = () => {
  members.value = null;

  memberService.getAll().then(
    (data) => {
      members.value = data.map(member => ({
        ...member,
        name: `${member.firstName} ${member.middleName.charAt(0)}. ${member.lastName}`
      }));
    },
    (error) => {
      message.value =
        (error.response && error.response.data && error.response.data.message) ||
        error.message ||
        error.toString();
    }
  )
}

const convertToSentenceCase = (str) => {
  return str.toLowerCase().split(' ')
    .map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ');
}
</script>

<style scoped>
</style>
