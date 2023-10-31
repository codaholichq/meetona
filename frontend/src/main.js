import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import { icon, validation } from './plugins'

createApp(App)
  .use(createPinia())
  .use(validation)
  .use(router)
  .use(icon)
  .mount('#app')
