<template>
  <div>
    <h1>SIA Event Manager</h1>
    <LoginComponent @login="handleLogin" v-if="!isLoggedIn" />
    <!-- Your app content goes here -->
  </div>
</template>

<script>
import LoginComponent from "@/components/LoginComponent.vue";

export default {
  name: "App",
  components: { LoginComponent, },
  data() {
    return {
      isLoggedIn: false,
    };
  },
  methods: {
    handleLogin(credentials) {
      // You can now send the credentials to the backend for authentication
      console.log("Login:", credentials);
      // Once authenticated, update the state and perform any necessary actions
      this.isLoggedIn = true;
    },
  },
};
</script>

<style scoped>
/* Your component styles go here */
</style>
