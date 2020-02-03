<template>
  <div id="app">
    <Navbar></Navbar>
    <router-view></router-view>
    <Footer></Footer>
  </div>
</template>
<script>
import "@/assets/css/style.scss";
import Navbar from "@/components/common/Navbar.vue";
import Footer from "@/components/common/Footer.vue";
export default {
  name: "app",
  components: {
    Navbar,
    Footer,
  }
};
</script>
<style>
@import url("https://fonts.googleapis.com/css?family=Libre+Caslon+Text&display=swap");
</style>
