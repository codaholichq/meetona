<template>
  <footer class="footer py-3">
    <div class="container">
        <span class="text-muted">
          &copy; {{ showYear }} <a href="https://codaholic.com" target="blank">Codaholic Inc</a>
        </span>
    </div>
  </footer>
</template>

<style scoped>
.footer {
  position: fixed !important;
  right: 0;
  left: 0;
  bottom: 0;
  background-color: #f5f5f5;
}

.footer > .container {
  padding: 0 25px 0 5px;
}

code {
  font-size: 80%;
}

.footer a,
.footer a:link {
  color: #343a40;
  font-style: normal;
  text-decoration: none;
}
</style>

<script>
export default {
  name: 'AppFooter',
  data() {
    return {
      showYear: new Date().getFullYear()
    }
  }
}
</script>
