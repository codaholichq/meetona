<template>
  <div class="col-md-6 offset-md-3">
    <div class="card">
      <h4 class="card-header">Add Member</h4>
      <div class="card-body">

        <Form @submit="add" :validation-schema="schema" v-slot="{ errors, loading }">
          <div class="form-group col-md-12 mt-3">
            <Field
              id="firstname"
              name="firstname"
              type="text"
              autocomplete="on"
              class="form-control"
              placeholder="First Name"
              :class="{ 'is-invalid': errors.firstname }"
            />
            <div class="invalid-feedback">{{ errors.firstname }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <Field
              id="middlename"
              name="middlename"
              type="text"
              autocomplete="on"
              placeholder="Middle Name"
              class="form-control"
              :class="{ 'is-invalid': errors.middlename }"
            />
            <div class="invalid-feedback">{{ errors.middlename }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <Field
              type="text"
              id="lastname"
              name="lastname"
              autocomplete="on"
              placeholder="Last Name"
              class="form-control"
              :class="{ 'is-invalid': errors.lastname }"
            />
            <div class="invalid-feedback">{{ errors.lastname }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <Field as="select"
              id="gender"
              name="gender"
              class="form-control"
              :class="{ 'is-invalid': errors.gender }"
            >
              <option value="">Gender</option>
              <option value="MALE">Male</option>
              <option value="FEMALE">Female</option>
            </Field>
            <div class="invalid-feedback">{{ errors.gender }}</div>
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
              id="phoneNumber"
              name="phoneNumber"
              type="text"
              autocomplete="on"
              class="form-control"
              placeholder="Phone Number"
              :class="{ 'is-invalid': errors.phoneNumber }"
            />
            <div class="invalid-feedback">{{ errors.phoneNumber }}</div>
          </div>

          <div class="form-group mt-3 d-flex">
            <label for="birthDate" class="col-md-6 pt-1">Birth Date</label>
            <Field
              id="birthDate"
              name="birthDate"
              type="date"
              autocomplete="on"
              class="form-control"
              :class="{ 'is-invalid': errors.birthDate }"
            />
            <div class="invalid-feedback">{{ errors.birthDate }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <Field as="select"
              id="maritalStatus"
              name="maritalStatus"
              class="form-control"
              :class="{ 'is-invalid': errors.maritalStatus }"
            >
              <option value="">Marital Status</option>
              <option value="SINGLE">Single</option>
              <option value="ENGAGED">Engaged</option>
              <option value="MARRIED">Married</option>
            </Field>
            <div class="invalid-feedback">{{ errors.maritalStatus }}</div>
          </div>

          <div class="form-group mt-3 d-flex">
            <label for="marriageDate" class="col-md-6 pt-1">Marriage Date</label>
            <Field
              id="marriageDate"
              name="marriageDate"
              type="date"
              autocomplete="on"
              class="form-control"
              :class="{ 'is-invalid': errors.marriageDate }"
            />
            <div class="invalid-feedback">{{ errors.marriageDate }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <label for="unitId">UnitId</label>
            <Field
              id="unitId"
              name="unitId"
              type="text"
              autocomplete="on"
              placeholder="3fa85f64-5717-4562-b3fc-2c963f66afa6"
              class="form-control"
              :class="{ 'is-invalid': errors.unitId }"
            />
            <div class="invalid-feedback">{{ errors.unitId }}</div>
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
import { useMemberStore } from '@/stores';

const memberStore = useMemberStore()

const loading = ref(false);
const message = ref('');

const schema = yup.object().shape({
  firstname: yup.string().required('First Name is required!'),
  lastname: yup.string().required('Last Name is required!'),
  gender: yup.string().required("Gender is required!"),
  phoneNumber: yup.string().required('Phone Number is required!'),
  email: yup.string().required('Email is required!'),
  birthDate: yup.string().required('Birth Day is required!'),
  maritalStatus: yup.string().required('Marital Status is required!'),
  marriageDate: yup.string().required('Marriage Date is required!')
});

const add = (data, { resetForm }) => {
  loading.value = true;

  memberStore.add(data).then(
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
