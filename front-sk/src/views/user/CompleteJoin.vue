<template>
  <div class="container">
    <h1>회원가입완료</h1>
    <h2>회원가입이 완료 되었습니다.</h2>
    <p>회원가입이 완료되어 메일이 발송되었습니다.</p>
    <div>{{ email }}</div>

    <router-link v-bind:to="{ name: 'Login' }">
      <button class="btn btn--back">로그인 화면</button>
    </router-link>
    <router-link v-bind:to="{ name: 'InputEmail' }">
      <button class="btn btn--back">메일이 도착하지 않았나요?</button>
    </router-link>
  </div>
</template>

<script>
const storage = window.sessionStorage;
export default {
  data() {
    return {
      email: ""
    };
  },
  created() {
    this.email = storage.getItem("login_useremail");
  }
};
</script>

  <style scoped>
button {
  margin: 5px 0;
}
</style>
