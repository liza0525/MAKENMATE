<!--
    가입하기는 기본적인 폼만 제공됩니다
    기능명세에 따라 개발을 진행하세요.
    Sub PJT I에서는 UX, 디자인 등을 포함하여 백엔드를 제외하여 개발합니다.
 -->
<template>
  <div class="wrapC" style="margin-top : 30px">
    <div>
      <h1>가입하기</h1>
      <div class="input-with-label">
        <input v-model="nickname" id="nickname" placeholder="닉네임을 입력하세요." type="text" />
        <label for="nickname">닉네임</label>
      </div>

      <div class="input-with-label">
        <input v-model="email" id="email" placeholder="이메일을 입력하세요." type="text" />
        <label for="email">이메일</label>
        <!-- <button @click="checkEmail()" :disabled="!duplicateEmail">중복 체크</button> -->
        <div class="error-text" v-if="error.email">{{ error.email }}</div>
      </div>

      <div class="input-with-label">
        <input v-model="password" type="password" id="password" placeholder="비밀번호를 입력하세요." />
        <label for="password">비밀번호</label>
        <div class="error-text" v-if="password != 0 && error.password">{{ error.password }}</div>
      </div>

      <div class="input-with-label">
        <input
          v-model="passwordConfirm"
          :type="passwordConfirmType"
          id="password-confirm"
          placeholder="비밀번호를 다시한번 입력하세요."
        />
        <label for="password-confirm">비밀번호 확인</label>
        <div
          class="error-text"
          v-if="passwordConfirm != 0 && password != passwordConfirm"
        >{{ error.passwordConfirm }}</div>
      </div>
    </div>
    <div>
      <label>
        <input v-model="isTerm" @click="isTerm = !isTerm" type="checkbox" id="term" />
        <span>약관을 동의합니다.</span>
      </label>

      <span @click="termPopup = !termPopup">약관보기</span>
      <div v-if="termPopup == true">"약관입니다"</div>
      <router-link v-bind:to="{ name: 'CompleteJoin' }">
        <button
          class="btn btn--back"
          v-on:click="join()"
          :disabled="!isSubmit"
          :class="{ disabled: !isSubmit }"
        >회원 가입</button>
      </router-link>
    </div>
  </div>
</template>

<script>
import "../../assets/css/style.scss";
import "../../assets/css/user.scss";
import PasswordValidator from "password-validator";
import * as EmailValidator from "email-validator";
import UserApi from "../../apis/UserApi";

export default {
  data: () => {
    return {
      email: "",
      password: "",
      passwordConfirm: "",
      nickname: "",
      isTerm: false,
      isLoading: false,
      error: {
        email: false,
        password: false,
        nickname: false,
        passwordConfirm: false,
        term: false
      },
      isSubmit: false,
      duplicateEmail: false,
      passwordType: "password",
      passwordConfirmType: "password",
      passwordSchema: new PasswordValidator(),
      termPopup: false
    };
  },
  created() {
    this.component = this;
    this.passwordSchema
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
    email: function(v) {
      this.checkForm();
    },
    password: function(v) {
      this.checkForm();
    },
    isTerm: function(v) {
      this.checkForm();
    }
  },
  methods: {
    checkForm() {
      if (this.email.length > 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      if (
        this.password.length > 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      let isSubmit = false;
      if (this.email.length > 0 && this.password.length > 0) isSubmit = true;

      let duplicateEmail = true;
      if (this.error.email) duplicateEmail = false;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
      this.duplicateEmail = duplicateEmail;
    },
    join() {
      if (this.isSubmit) {
        let { email, nickname, password } = this;
        let data = {
          email,
          nickname,
          password
        };
        this.isSubmit = false;

        if (!this.isTerm || this.passwordConfirm != this.password) {
          if (!this.isTerm) this.error.term = "약관에 동의해야 합니다.";
          else this.error.term = false;

          if (this.passwordConfirm != this.password)
            this.error.passwordConfirm = "비밀번호와 일치하지 않습니다.";
          else this.error.passwordConfirm = false;

          this.isSubmit = true;
        } else {
          UserApi.requestSignup(
            data,
            res => {
              console.log(res);
              alert("회원가입 mail이 전송되었습니다.");
              if (res == "success")
                this.$router.push({
                  name: "SuccessPage",
                  params: {
                    msg: "회원가입"
                  }
                });

              this.isSubmit = true;
            },
            error => {
              console.log(error);
              this.isSubmit = true;
            }
          );
        }
      }
    },
    checkEmail() {
      if (this.duplicateEmail) {
        let { email } = this;
        let data = {
          email
        };

        this.duplicateEmail = false;

        UserApi.requestCheckEmail(
          data,
          res => {
            console.log(res);
            if (res == "success") alert("사용 가능한 email입니다.");
            else alert("사용 불가능한 email입니다.");
            this.duplicateEmail = true;
          },
          error => {
            console.log(error);
            this.duplicateEmail = true;
          }
        );
      }
    }
  }
};
</script>
<style scoped>
.wrapC {
  color: white;
}
.input-with-label input,
label {
  color: white;
}
</style>