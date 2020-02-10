<template>
  <div></div>
</template>

<script>
const storage = window.sessionStorage;
export default {
  created() {
    this.logout();
  },
  methods: {
    logout() {
      console.log("logout");
      storage.setItem("Authorization", "");
      storage.setItem("login_useremail", "");
      storage.setItem("login_username", "");
      console.log(storage.getItem("Authorization"));
      this.$store.state.username = "";
      this.$router.push("/");
    }
  }
};
</script>
