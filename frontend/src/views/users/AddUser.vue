<template>
  <div class="col-md-6 offset-md-3">
    <div class="card">
      <h4 class="card-header">Add User</h4>
      <div class="card-body">

        <Form @submit="add" :validation-schema="schema" v-slot="{ errors, loading }">

          <div class="form-group col-md-12 mt-3">
            <Field
              type="email"
              name="email"
              placeholder="Email"
              class="form-control"
              autocomplete="off"
              @focusout="search"
              :class="{ 'is-invalid': errors.email }"
            />
            <div class="invalid-feedback">{{ errors.email }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <Field
              disabled
              type="text"
              name="username"
              class="form-control"
              placeholder="Username"
              autocomplete="off"
              v-model="member.username"
              :class="{ 'is-invalid': errors.username }"
            />
            <div class="invalid-feedback">{{ errors.username }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <Field
              disabled
              type="text"
              name="memberId"
              autocomplete="off"
              placeholder="Member ID"
              v-model="member.id"
              class="form-control"
              :class="{ 'is-invalid': errors.memberId }"
            />
            <div class="invalid-feedback">{{ errors.memberId }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <Field
              as="select"
              name="roles"
              class="form-control"
              autocomplete="off"
              :class="{ 'is-invalid': errors.roles }"
            >
              <option value="">Roles</option>
              <option value="ADMIN">Admin</option>
              <option value="USER">User</option>
            </Field>
            <div class="invalid-feedback">{{ errors.roles }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <Field
              id="password"
              name="password"
              type="password"
              class="form-control"
              autocomplete="off"
              placeholder="Password"
              :class="{ 'is-invalid': errors.password }"
            />
            <div class="invalid-feedback">{{ errors.password }}</div>
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
import { ref } from 'vue';
import * as yup from 'yup';
import { useMemberStore, useUserStore } from '@/stores';

const userStore = useUserStore()
const memberStore = useMemberStore();

const loading = ref(false);
const message = ref('');
const member = ref({})

const schema = yup.object().shape({
  email: yup.string().required('Email is required!'),
  roles: yup.string().required('Role is required!')
});

const add = (data, { resetForm }) => {
  loading.value = true;
  data.roles = data.roles.split(','); // convert roles to array of strings

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

const search = (event) => {
  const email = event.target.value;

  memberStore.search(email).then(
    (data) => {
      console.log(data)
      member.value = data

      const firstLetter = data.firstName.charAt(0);
      member.value.username = (firstLetter + data.lastName).toLowerCase();
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
</style>
