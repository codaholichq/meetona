<template>
  <table class="table">
    <thead>
    <tr>
      <th>Username</th>
      <th>Roles</th>
      <th>Email</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="user in users" :key="user.id">
      <td>{{ user.username }}</td>
      <td>{{ rolesToString(user.roles) }}</td>
      <td>{{ user.email }}</td>
      <td>
        <div class="d-flex">
          <router-link class="dropdown-item me-2" to="/dashboard/users/update">
            <icon :icon="['fas', 'edit']" />
          </router-link>
          <button class="btn pt-0" @click="remove(user.id)">
            <icon class="trash-icon" :icon="['fas', 'trash-alt']" />
          </button>
        </div>
      </td>
    </tr>
    </tbody>
  </table>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { userService } from '@/services';

const users = ref(null);
const message = ref('');

const rolesToString = (roles) => {
  return roles.map(role => role.toLowerCase()).join(', ');
};

onMounted(() => { getUsers() });

const getUsers = () => {
  users.value = null;

  userService.getAll().then(
    (data) => {
      users.value = data
    },
    (error) => {
      message.value =
        (error.response && error.response.data && error.response.data.message) ||
        error.message ||
        error.toString();
    }
  )
}

const remove = (userId) => {
  console.log(userId)

  userService.remove(userId).then(
    (data) => {
      console.log(data)
    },
    (error) => {
      message.value =
        (error.response && error.response.data && error.response.data.message) ||
        error.message ||
        error.toString();
    }
  )
}
</script>

<style scoped>
.trash-icon {
  transition: transform 0.3s ease-in-out;
}

.trash-icon:hover {
  transform: rotate(45deg);
  color: red !important;
}
</style>
