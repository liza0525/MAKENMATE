<template>
  <div class="container">
    <h1>{{ msg }}완료</h1>
    <h2 v-if="isTrue">{{ msg }}이 완료 되었습니다.</h2>
    <h2 v-if="!isTrue">{{ msg }}가 완료 되었습니다.</h2>
    <p v-if="isTrue">{{ msg }}이 완료되어 메일이 발송되었습니다.</p>
    <router-link v-bind:to="{ name: 'Login' }">
      <button class="btn btn--back">로그인 화면</button>
    </router-link>
    <router-link v-if="isTrue" v-bind:to="{ name: 'InputEmail' }">
      <button class="btn btn--back">메일이 도착하지 않았나요?</button>
    </router-link>
  </div>
</template>

<script>
export default {
  data: () => {
    return {
      msg: "",
      isTrue: false
    };
  },
  mounted() {
    this.msg = this.$router.params.msg;
    // this.msg = "비밀번호 변경";
    if (this.msg === "회원가입" || this.msg === "비밀번호 변경") {
      this.isTrue = true;
    }
  }
};
</script>

<style>
button {
  margin: 5px 0;
}
</style>
