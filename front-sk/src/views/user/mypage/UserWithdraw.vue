<template>
  <div>
    <div class="input-with-label" style="margin-top:100px;">
      <input
        v-model="password"
        :type="passwordType"
        id="password"
        placeholder="비밀번호를 입력하세요."
      />
      <label for="password">비밀번호 확인</label>
      <div class="error-text" v-if="error.password != false">
        {{ error.password }}
      </div>
    </div>
    <button class="btn btn--back" v-on:click="withdraw()">탈퇴 요청</button>
  </div>
</template>

<script>
const axios = require("axios");

export default {
  data: () => {
    return {
      email: "aa@aa.com",
      password: "",
      error: {
        password: false
      },
      isSubmit: false,
      passwordType: "password"
    };
  },
  methods: {
    withdraw() {
      if (confirm("탈퇴하시겠습니까?")) {
        axios
          .delete("http://localhost:8080/user/withdraw", null, {
            params: {
              email: this.email,
              password: this.password
            }
          })
          .then(res => {
            if (res.data == "success") {
              this.$router.push({
                name: "SuccessPage",
                params: {
                  msg: "탈퇴"
                }
              });
            } else {
              this.error.password = "비밀번호를 다시 확인하세요.";
            }
          })
          .catch(error => {
            console.log(error);
          });
      }
    }
  }
};
</script>

<style></style>
