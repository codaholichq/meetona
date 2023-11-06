<template>
  <div class="col-md-6 offset-md-3">
    <div class="card">
      <h4 class="card-header">Add Member</h4>
      <div class="card-body">

        <Form @submit="create" :validation-schema="schema" v-slot="{ errors, loading }">
          <div class="form-group col-md-12 mt-3">
            <Field
              name="firstName"
              type="text"
              autocomplete="on"
              class="form-control"
              placeholder="First Name"
              :class="{ 'is-invalid': errors.firstName }"
            />
            <div class="invalid-feedback">{{ errors.firstName }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <Field
              name="middleName"
              type="text"
              autocomplete="on"
              placeholder="Middle Name"
              class="form-control"
              :class="{ 'is-invalid': errors.middleName }"
            />
            <div class="invalid-feedback">{{ errors.middleName }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <Field
              type="text"
              name="lastName"
              autocomplete="on"
              placeholder="Last Name"
              class="form-control"
              :class="{ 'is-invalid': errors.lastName }"
            />
            <div class="invalid-feedback">{{ errors.lastName }}</div>
          </div>

          <div class="form-group col-md-12 mt-3">
            <Field
              as="select"
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
            <Field
              as="select"
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
            <Field
              as="select"
              name="unitId"
              class="form-control"
              :class="{ 'is-invalid': errors.unitId }"
            >
              <option value="">Choose Unit</option>
              <option v-for="unit in units" :key="unit.id" :value="unit.id">
                {{ unit.name }}
              </option>
            </Field>
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
import { ref, onMounted } from 'vue';
import * as yup from 'yup';
import { unitService, memberService } from '@/services';

const loading = ref(false);
const message = ref('');
const units = ref(null);

const schema = yup.object().shape({
  firstName: yup.string().required('First Name is required!'),
  lastName: yup.string().required('Last Name is required!'),
  gender: yup.string().required("Gender is required!"),
  phoneNumber: yup.string().required('Phone Number is required!'),
  email: yup.string().required('Email is required!'),
  birthDate: yup.string().required('Birth Day is required!'),
  maritalStatus: yup.string().required('Marital Status is required!'),
  unitId: yup.string().required('Unit Id is required!')
});

onMounted(() => { getUnits() });

const create = (data, { resetForm }) => {
  loading.value = true;
  console.log(data)

  memberService.create(data).then(
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

const getUnits = () => {
  units.value = null;

  unitService.getAll().then(
    (data) => {
      units.value = data
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
