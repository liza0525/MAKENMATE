<template>
  <div class="wrapC">
    <img v-bind:src="user.image" alt />
  </div>
</template>

<script>
export default {
  data: () => {
    return {
      user: {
        email: "",
        nickname: "",
        image: ""
      }
    };
  },
  mounted() {
    this.user.email = this.$router.params.user.email;
    this.user.nickname = this.$router.params.user.nickname;
    this.user.image = this.$router.params.user.image;
  }
};
</script>
