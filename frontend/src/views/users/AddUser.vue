<template>
  <div class="col-md-6 offset-md-3">
    <div class="card">
      <h4 class="card-header">Add User</h4>
      <div class="card-body">

        <Form @submit="add" :validation-schema="schema" v-slot="{ errors, loading }">
          <div class="form-group col-md-12 mt-3">
            <Field
              id="username"
              name="username"
              type="text"
              autocomplete="on"
              class="form-control"
              placeholder="Username"
              :class="{ 'is-invalid': errors.username }"
            />
            <div class="invalid-feedback">{{ errors.username }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <Field
              id="email"
              name="email"
              type="email"
              autocomplete="on"
              class="form-control"
              placeholder="Email"
              :class="{ 'is-invalid': errors.email }"
            />
            <div class="invalid-feedback">{{ errors.email }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <Field
              id="password"
              name="password"
              type="password"
              autocomplete="on"
              class="form-control"
              placeholder="Password"
              :class="{ 'is-invalid': errors.password }"
            />
            <div class="invalid-feedback">{{ errors.password }}</div>
          </div>

          <div class="form-group mt-3">
            <Field
              id="roles"
              name="roles"
              type="text"
              autocomplete="on"
              placeholder="Roles"
              class="form-control"
              :class="{ 'is-invalid': errors.roles }"
            />
            <div class="invalid-feedback">{{ errors.roles }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <label for="memberId">Member Id</label>
            <Field
              id="memberId"
              name="memberId"
              type="text"
              autocomplete="on"
              placeholder="3fa85f64-5717-4562-b3fc-2c963f66afa6"
              class="form-control"
              :class="{ 'is-invalid': errors.memberId }"
            />
            <div class="invalid-feedback">{{ errors.memberId }}</div>
          </div>

          <div class="form-group">
            <button :disabled="loading" class="btn btn-primary mt-4">
              <span v-if="loading" class="spinner-border spinner-border-sm mr-2"></span>
              Add
            </button>
          </div>

          <div v-if="errors.apiError" class="alert alert-danger mt-3 mb-0">{{ errors.apiError }}</div>
        </Form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import * as yup from 'yup';
import { useUserStore } from '@/stores';

const userStore = useUserStore()

const loading = ref(false);
const message = ref('');

const schema = yup.object().shape({
  firstname: yup.string().required('First Name is required!'),
  lastname: yup.string().required('Last Name is required!'),
  gender: yup.string().required("Gender is required!"),
  phoneNumber: yup.string().required('Phone Number is required!'),
  email: yup.string().required('Email is required!')
});

// const unitId = computed(() => memberStore.getById());

// const loggedIn = computed(() => authStore.);

onMounted(() => {
});

const add = (data, { resetForm }) => {
  loading.value = true;

  userStore.add(data).then(
    () => {
      loading.value = false;
      resetForm();
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
