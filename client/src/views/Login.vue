<template>
  <div>
    <div class="alert alert-info">
      Username: test<br />
      Password: test
    </div>
    <h2>Login</h2>
    <vee-form @submit="login" :validation-schema="schema" v-slot="{ errors, isSubmitting }">
      <div class="form-group">
        <label>Username</label>
        <vee-field
          name="username"
          type="text"
          class="form-control"
          :class="{ 'is-invalid': errors.username }"
        />
        <div class="invalid-feedback">{{ errors.username }}</div>
      </div>
      <div class="form-group">
        <label>Password</label>
        <vee-field
          name="password"
          type="password"
          class="form-control"
          :class="{ 'is-invalid': errors.password }"
        />
        <div class="invalid-feedback">{{ errors.password }}</div>
      </div>
      <div class="form-group">
        <button class="btn btn-primary" :disabled="isSubmitting">
          <span v-show="isSubmitting" class="spinner-border spinner-border-sm mr-1"></span>
          Login
        </button>
      </div>
      <div v-if="errors.apiError" class="alert alert-danger mt-3 mb-0">{{ errors.apiError }}</div>
    </vee-form>
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
};

// export default {
// eslint-disable-next-line vue/multi-word-component-names
// name: 'Login'
// function login(values, { setErrors }) {
//   const authStore = useAuthStore();
//   const { username, password } = values;

//   return authStore.login(username, password).catch((error) => setErrors({ apiError: error }));
// }
// };
</script>

<style scoped>
label {
  display: block;
  margin-top: 10px;
}

.card-container.card {
  max-width: 350px !important;
  padding: 40px 40px;
}

.card {
  background-color: #f7f7f7;
  padding: 20px 25px 30px;
  margin: 0 auto 25px;
  margin-top: 50px;
  -moz-border-radius: 2px;
  -webkit-border-radius: 2px;
  border-radius: 2px;
  -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
}

.profile-img-card {
  width: 96px;
  height: 96px;
  margin: 0 auto 10px;
  display: block;
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
  border-radius: 50%;
}

.error-feedback {
  color: red;
}
</style>
