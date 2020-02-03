<template>
  <div class="wrapC" style="margin-top : 30px">
    <h1>이메일 전송</h1>
    <div class="input-with-label">
      <input
        v-model="email"
        id="email"
        placeholder="이메일을 입력하세요."
        type="text"
      />
      <label for="email">이메일</label>
    </div>
    <div class="error-text" v-if="error.email">
      {{ error.email }}
    </div>
    <router-link v-bind:to="{name:'SendEmail'}">
      <button class="btn btn--back" :disabled="!isSubmit" :class="{disabled : !isSubmit}">메일 전송</button>
    </router-link>
  </div>
</template>

<script>
import * as EmailValidator from "email-validator";
export default {
  watch: {
    email: function(v) {
      this.checkForm();
    }
  },
  methods: {
    checkForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    sendemail() {}
  },
  data: () => {
    return {
      email: "",
      error: {
        email: false
      },
      isSubmit: false
    };
  }
};
</script>

<style>
</style>