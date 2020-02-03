<template>
  <div>
    <div class="input-with-label" style="margin-top:100px;">
      <input
        v-model="password"
        type="password"
        id="password"
        placeholder="기존 비밀번호를 입력하세요."
      />
      <label for="password">기존 비밀번호</label>
    </div>
    <div class="input-with-label">
      <input
        v-model="newPassword"
        :type="newPasswordType"
        id="new-password"
        placeholder="새 비밀번호를 입력하세요."
      />
      <label for="new-password">비밀번호</label>
      <div class="error-text" v-if="error.newPassword != false">
        {{ error.newPassword }}
      </div>
    </div>
    <div class="input-with-label">
      <input
        v-model="passwordConfirm"
        :type="passwordConfirmType"
        id="password-confirm"
        placeholder="새 비밀번호를 다시한번 입력하세요."
      />
      <label for="password-confirm">비밀번호 확인</label>
      <div class="error-text" v-if="error.passwordConfirm != false">
        {{ error.passwordConfirm }}
      </div>
    </div>
    <button
      class="btn btn--back"
      v-on:click="updatePW()"
      :disabled="!isSubmit"
      :class="{ disabled: !isSubmit }"
    >
      비밀번호 변경
    </button>
    <div class="error-text" v-if="isSubmit === true && error.password">
      {{ error.password }}
    </div>
  </div>
</template>

<script>
import PasswordValidator from "password-validator";
const axios = require("axios");

export default {
  data: () => {
    return {
      email: "aa@aa.com",
      password: "",
      passwordConfirm: "",
      newPasswordSchema: new PasswordValidator(),
      newPassword: "",
      error: {
        password: false,
        passwordConfirm: false,
        newPassword: false
      },
      isSubmit: false,
      passwordType: "password",
      passwordConfirmType: "password",
      newPasswordType: "password"
    };
  },
  created() {
    this.component = this;
    this.newPasswordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  watch: {
    newPassword: function(v) {
      this.checkForm();
    },
    passwordConfirm: function(v) {
      this.checkForm();
    }
  },
  methods: {
    checkForm() {
      if (
        this.newPassword.length > 0 &&
        !this.newPasswordSchema.validate(this.newPassword)
      )
        this.error.newPassword = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.newPassword = false;
      if (
        this.passwordConfirm.length > 0 &&
        this.passwordConfirm != this.newPassword
      )
        this.error.passwordConfirm = "비밀번호와 일치하지 않습니다.";
      else this.error.passwordConfirm = false;

      if (
        this.password.length > 0 &&
        this.newPassword.length > 0 &&
        this.passwordConfirm.length > 0 &&
        this.error.newPassword == false &&
        this.error.passwordConfirm == false
      )
        this.isSubmit = true;
    },
    updatePW() {
      if (this.isSubmit) {
        axios
          .put("http://localhost:8080/user/changePW", null, {
            params: {
              email: this.email,
              befPassword: this.password,
              password: this.newPassword
            }
          })
          .then(res => {
            if (res.data == "success") {
              this.$router.push({
                name: "SuccessPage",
                params: {
                  msg: "비밀번호 변경"
                }
              });
            } else {
              this.error.password = "비밀번호를 다시 확인하세요.";
            }
          });
      }
    }
  }
};
</script>

<style></style>
