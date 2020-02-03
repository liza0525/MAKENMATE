<template>
  <div>
    <router-link v-bind:to="{ name: 'UserChangePW' }" class="btn--text">비밀번호 변경</router-link>
    <router-link v-bind:to="{ name: 'UserWithdraw' }" class="btn--text">회원 탈퇴</router-link>
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
