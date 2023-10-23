<template>
  <div class="col-md-6 offset-md-3">
  <div class="card">
    <h4 class="card-header">Add Summary</h4>
    <div class="card-body">

      <vee-form @submit="login" :validation-schema="schema" v-slot="{ errors, isSubmitting }">

          <div class="form-group col-md-12">
            <label for="firstname">First Name</label>
            <vee-field
              id="firstname"
              name="firstName"
              type="text"
              autocomplete="on"
              class="form-control"
              :class="{ 'is-invalid': errors.firstname }"
            />
            <div class="invalid-feedback">{{ errors.firstname }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <label for="username">Middle Name</label>
            <vee-field
              id="middlename"
              name="middlename"
              type="text"
              autocomplete="on"
              class="form-control"
              :class="{ 'is-invalid': errors.middlename }"
            />
            <div class="invalid-feedback">{{ errors.middlename }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <label for="username">Last Name</label>
            <vee-field
              id="lastname"
              name="lastname"
              type="text"
              autocomplete="on"
              class="form-control"
              :class="{ 'is-invalid': errors.lastname }"
            />
            <div class="invalid-feedback">{{ errors.lastname }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <label for="username">Gender</label>
            <vee-field
              id="gender"
              name="gender"
              type="text"
              autocomplete="on"
              class="form-control"
              :class="{ 'is-invalid': errors.gender }"
            />
            <div class="invalid-feedback">{{ errors.gender }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <label for="email">Email</label>
            <vee-field
              id="email"
              name="email"
              type="text"
              autocomplete="on"
              class="form-control"
              :class="{ 'is-invalid': errors.email }"
            />
            <div class="invalid-feedback">{{ errors.email }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <label for="email">PhoneNumber</label>
            <vee-field
              id="phoneNumber"
              name="phoneNumber"
              type="text"
              autocomplete="on"
              class="form-control"
              :class="{ 'is-invalid': errors.phoneNumber }"
            />
            <div class="invalid-feedback">{{ errors.phoneNumber }}</div>
          </div>

        <div class="form-group">
          <button class="btn btn-primary mt-4" :disabled="isSubmitting">
            <span v-show="isSubmitting" class="spinner-border spinner-border-sm mr-1"></span>
            Login
          </button>
        </div>
      </vee-form>

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
    router.push('/dashboard/member/add');
  }
});

const login = (username, password) => {
  loading.value = true;

  authStore.login(username, password).then(
    () => {
      router.push('/dashboard/member/add');
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
