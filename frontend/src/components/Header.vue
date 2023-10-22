<template>
  <nav class="navbar navbar-expand-sm navbar-dark bg-dark fixed-top">
    <div class="container">
      <a class="navbar-brand">
        <img src="../assets/logo.png" height="55" width="55" alt="RCNLagos Logo" />&nbsp;&nbsp;RCNLagos
      </a>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
          <li v-if="authStore.isLoggedIn" class="nav-item me-2 dropdown">
            <a
              class="nav-link dropdown-toggle"
              id="navbarDropdown"
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >User</a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
              <li><a class="dropdown-item" :href="`/dashboard/users/add`">Add User</a></li>
              <li><a class="dropdown-item" :href="`/dashboard/users`">View Users</a></li>
            </ul>
          </li>

          <li v-if="authStore.isLoggedIn" class="nav-item me-2 dropdown">
            <a
              class="nav-link dropdown-toggle"
              id="navbarDropdown"
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >Todos</a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
              <li><a class="dropdown-item" :href="`/dashboard/todos/add`">Add Todo</a></li>
              <li><a class="dropdown-item" :href="`/dashboard/todos`">Todos</a></li>
            </ul>
          </li>

          <li v-if="authStore.isLoggedIn" class="nav-item me-2 dropdown">
            <a
              class="nav-link dropdown-toggle"
              id="navbarDropdown"
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >Reports</a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
              <li><a class="dropdown-item" :href="`/dashboard/checks`">Checks</a></li>
              <li><a class="dropdown-item" :href="`/dashboard/summary`">Summary</a></li>
            </ul>
          </li>

          <li v-if="authStore.isAdmin && authStore.isLoggedIn" class="nav-item me-2 dropdown">
            <a
              class="nav-link dropdown-toggle"
              id="navbarDropdown"
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >Members</a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
              <li>
                <a class="dropdown-item" :href="`/dashboard/checks/add`">Add Member</a>
              </li>
              <li>
                <a class="dropdown-item" :href="`/dashboard/summary/add`">Add Summary</a>
              </li>
            </ul>
          </li>

          <li v-if="authStore.isLoggedIn" class="nav-item">
            <a class="nav-link" role="button" @click="logout">Logout</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { useAuthStore } from "@/stores";

const authStore = useAuthStore();

const logout = () => {
  authStore.logout()
}
</script>
