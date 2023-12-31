<template>
  <div class="col-md-6 offset-md-3 mt-3">
    <div class="card mt-2">
      <h4 class="card-header">Login</h4>
      <div class="card-body">

        <Form @submit="login" :validation-schema="schema" v-slot="{ errors, loading }">
          <div class="form-group">
            <label for="username">Username</label>
            <Field
              id="username"
              name="username"
              type="text"
              autocomplete="on"
              class="form-control"
              :class="{ 'is-invalid': errors.username }"
            />
            <div class="invalid-feedback">{{ errors.username }}</div>
          </div>

          <div class="form-group mt-3">
            <label for="password">Password</label>
            <Field
              id="password"
              name="password"
              type="password"
              autocomplete="on"
              class="form-control"
              :class="{ 'is-invalid': errors.password }"
            />
            <div class="invalid-feedback">{{ errors.password }}</div>
          </div>

          <div class="form-group">
            <button :disabled="loading" class="btn btn-primary mt-4">
              <span v-if="loading" class="spinner-border spinner-border-sm mr-1"></span>
              Login
            </button>
          </div>

          <div v-if="errors.apiError" class="alert alert-danger mt-3 mb-0">{{ errors.apiError }}</div>
        </Form>

      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import * as yup from 'yup';
import { useAuthStore } from '@/stores';

const authStore = useAuthStore();
const router = useRouter();

const loading = ref(false);
const message = ref('');
const schema = yup.object().shape({
  username: yup.string().required('Username is required!'),
  password: yup.string().required('Password is required!')
});

const loggedIn = computed(() => authStore.loggedIn);

onMounted(() => {
  if (loggedIn.value) {
    router.push('/dashboard/members/add');
  }
});

const login = (username, password) => {
  loading.value = true;

  authStore.login(username, password).then(
    () => {
      router.push('/dashboard/members/add');
    },
    (error) => {
      loading.value = false;
      message.value =
        (error.response && error.response.data && error.response.data.message) ||
        error.message ||
        error.toString();
    }
  );
}
</script>

<style scoped>

</style>
