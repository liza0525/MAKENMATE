<template>
  <div id="app">
    <Navbar></Navbar>
    <router-view></router-view>
  </div>
</template>
<script>
import Navbar from "@/components/common/Navbar.vue";
export default {
  name: "app",
  components: {
    Navbar
  }
};
</script>
<style>
@import url("https://fonts.googleapis.com/css?family=Libre+Caslon+Text&display=swap");
</style>
