<template>
  <nav class="navbar navbar-expand-sm navbar-dark bg-dark fixed-top">
    <div class="container">
      <a class="navbar-brand">
        <img src="../assets/logo.png" height="55" width="55" />&nbsp;&nbsp;RCNLagos
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
          <li class="nav-item me-2 dropdown">
            <a
              class="nav-link dropdown-toggle"
              id="navbarDropdown"
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
              >User</a
            >
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
              <li><a class="dropdown-item" routerLink="/dashboard/users/add">Add User</a></li>
              <li><a class="dropdown-item" routerLink="/dashboard/users">View Users</a></li>
            </ul>
          </li>

          <li class="nav-item me-2 dropdown">
            <a
              class="nav-link dropdown-toggle"
              id="navbarDropdown"
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
              >Todos</a
            >
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
              <li><a class="dropdown-item" routerLink="/dashboard/todos/add">Add Todo</a></li>
              <li><a class="dropdown-item" routerLink="/dashboard/todos">Todos</a></li>
            </ul>
          </li>

          <li class="nav-item me-2 dropdown">
            <a
              class="nav-link dropdown-toggle"
              id="navbarDropdown"
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
              >Reports</a
            >
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
              <li><a class="dropdown-item" routerLink="/dashboard/checks">Checks</a></li>
              <li><a class="dropdown-item" routerLink="/dashboard/summary">Summary</a></li>
            </ul>
          </li>

          <li class="nav-item me-2 dropdown">
            <a
              class="nav-link dropdown-toggle"
              id="navbarDropdown"
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
              >Checks</a
            >
            <!-- <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
              <li>
                <router-link to="/dashboard/checks/add" class="dropdown-item"
                  >Add Checks</router-link
                >
              </li>
              <li>
                <router-link to="/dashboard/summary/add" class="dropdown-item"
                  >Add Summary</router-link
                >
              </li>
            </ul> -->
          </li>

          <li class="nav-item">
            <a class="nav-link" role="button" @click="logout()">Logout</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'AppHeader'
};
</script>
