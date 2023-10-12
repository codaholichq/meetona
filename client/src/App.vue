<script setup>
import { RouterView } from 'vue-router';
import AppHeader from '@/components/Header.vue';
</script>

<template>
  <AppHeader />
  <RouterView />
</template>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
body {
  color: #b4b8bd;
  font-family: 'montserrat', sans-serif;
}
header {
  width: 100vw;
  background-color: rgb(88, 82, 82);
  padding: 25px;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #202427;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
