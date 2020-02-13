<template>
  <div>{{ token }}</div>
</template>

<script>
import http from "../../http-common";
const storage = window.sessionStorage;
export default {
  data() {
    return {
      token: ""
    };
  },
  mounted() {
    this.token = this.$route.query.token;
    if (
      !(
        this.$route.query.msg == "undefined" ||
        this.$route.query.msg == null ||
        this.$route.query.msg == ""
      )
    ) {
      alert(this.$route.query.msg);
      this.$router.push({
        name: "Join",
        params: { nickname: this.$route.query.nickname }
      });
    } else {
      http
        .post("/user/auth", null, {
          params: {
            token: this.token
          }
        })
        .then(res => {
          storage.setItem("Authorization", res.headers["authorization"]);
          storage.setItem("login_username", res.data.object.nickname);
          storage.setItem("login_useremail", res.data.object.email);
          this.$store.state.username = res.data.object.nickname;
          this.$store.state.useremail = res.data.object.email;
          this.$router.push({ name: "Main" });
        })
        .catch(exp => console.log(exp));
    }
  }
};
</script>

<style lang="scss" scoped></style>
