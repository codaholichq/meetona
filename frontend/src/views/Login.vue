<template>
  <div class="col-md-6 offset-md-3 mt-5">
    <div class="card mt-2">
      <h4 class="card-header">Login</h4>
      <div class="card-body">

        <vee-form @submit="login" :validation-schema="schema" v-slot="{ errors, isSubmitting }">
          <div class="form-group">
            <label for="username">Username</label>
            <vee-field
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
            <vee-field
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
            <button class="btn btn-primary mt-4" :disabled="isSubmitting">
              <span v-show="isSubmitting" class="spinner-border spinner-border-sm mr-1"></span>
              Login
            </button>
          </div>

          <div v-if="errors.apiError" class="alert alert-danger mt-3 mb-0">{{ errors.apiError }}</div>
        </vee-form>

      </div>
    </div>
  </div>
</template>

<script>
import * as yup from 'yup';
import { useAuthStore } from '@/stores';

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Login',
  data() {
    const schema = yup.object().shape({
      username: yup.string().required('Username is required!'),
      password: yup.string().required('Password is required!')
    });

    return {
      loading: false,
      message: '',
      schema
    };
  },

  // computed: {
  //   loggedIn() {
  //     return this.$store.state.auth.status.loggedIn;
  //   }
  // },
  created() {
    if (this.loggedIn) {
      this.$router.push('/admin');
    }
  },

  methods: {
    login(username, password) {
      this.loading = true;
      const authStore = useAuthStore();

      authStore.login(username, password).then(
        () => {
          this.$router.push('/admin');
        },
        (error) => {
          this.loading = false;
          this.message =
            (error.response && error.response.data && error.response.data.message) ||
            error.message ||
            error.toString();
        }
      );
    }
  }
}
</script>

<style scoped>

</style>
