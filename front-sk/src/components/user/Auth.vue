<template>
  <div>test{{ token }}</div>
</template>

<script>
import http from "../../http-common";
export default {
  data() {
    return {
      token: ""
    };
  },
  mounted() {
    // this.token = this.$route.params.token;
    // console.log(this.token);
    // http
    //   .post("/user/auth", null, {
    //     params: {
    //       token: this.token
    //     }
    //   })
    //   .then(res => {
    //     console.log(res);
    //   });
  }
};
</script>

<style lang="scss" scoped></style>
