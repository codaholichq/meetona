<template>
  <div class="col-md-6 offset-md-3">
    <div class="card">
      <h4 class="card-header">Add Cell</h4>
      <div class="card-body">

        <Form @submit="add" :validation-schema="schema" v-slot="{ errors, loading }">

          <div class="form-group col-md-12 mt-3">
            <Field
              type="text"
              name="name"
              class="form-control"
              placeholder="Cell Name"
              autocomplete="off"
              :class="{ 'is-invalid': errors.name }"
            />
            <div class="invalid-feedback">{{ errors.name }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <Field
              as="select"
              name="unitId"
              class="form-control"
              :class="{ 'is-invalid': errors.unitId }"
            >
              <option value="">Assign Cell Leader</option>
              <option v-for="user in users" :key="user.id" :value="user.id">
                {{ user.username }}
              </option>
            </Field>
            <div class="invalid-feedback">{{ errors.unitId }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <Field
              rows="7"
              as="textarea"
              name="address"
              class="form-control"
              autocomplete="off"
              placeholder="Address"
              :class="{ 'is-invalid': errors.address }"
            />
            <div class="invalid-feedback">{{ errors.address }}</div>
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
import { ref, computed, onMounted } from 'vue';
import * as yup from 'yup';
import { useUnitStore, useUserStore } from '@/stores';

const unitStore = useUnitStore()
const userStore = useUserStore()

const loading = ref(false);
const message = ref('');

const schema = yup.object().shape({
  name: yup.string().required('Name is required!'),
  address: yup.string().required('Address is required!')
});

const userData = computed(() => userStore.listAll);
const users = userData.value

onMounted(() => { userStore.fetchAll() });

const add = (data, { resetForm }) => {
  loading.value = true;

  unitStore.add(data).then(
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
