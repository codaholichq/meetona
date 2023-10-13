<script setup>
import { RouterView } from 'vue-router';
import AppHeader from '@/components/Header.vue';
import AppFooter from "@/components/Footer.vue";
</script>

<template>
  <AppHeader />
  <div class="col-md-6 offset-md-3 mt-5 mb-5">
    <RouterView />
  </div>
  <AppFooter />
</template>

<style>
.mt-5 {
  padding-top: 6rem;
}
.mb-5 {
  padding-bottom: 2rem;
}
</style>
