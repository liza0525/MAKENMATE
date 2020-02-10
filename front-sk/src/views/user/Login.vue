<template>
  <div class="user" id="login">
    <div class="wrapC">
      <h1 id="title">Login to drink Cocktail&#x1F378;</h1>

      <div class="input-with-label">
        <input v-model="email" @keyup.enter="login" id="email" placeholder="email" type="text" />
        <label for="email" id="label-text">Email</label>
        <div class="error-text" v-if="error.email">{{ error.email }}</div>
      </div>

      <div class="input-with-label">
        <input
          v-model="password"
          type="password"
          id="password"
          @keyup.enter="login"
          placeholder="password"
        />
        <label for="password" id="label-text">Password</label>
        <div class="error-text" v-if="error.password">{{ error.password }}</div>
      </div>
      <button
        class="btn btn--back btn--login"
        v-on:click="login"
        :disabled="!isSubmit"
        :class="{ disabled: !isSubmit }"
      >sign in</button>
      <div class="sns-login">
        <div class="text">
          <p id="label-text">Sign in with SNS</p>
          <div class="bar"></div>
        </div>

        <kakaoLogin :component="component" />
        <!-- <GoogleLogin :component="component" /> -->
        <NaverLogin :component="component" />
      </div>
      <div class="add-option">
        <div class="text">
          <p id="label-text">etc.</p>
          <div class="bar"></div>
        </div>
        <div class="wrap">
          <p>forget your password,</p>
          <router-link v-bind:to="{ name: 'InputEmail' }" class="btn--text">Change your password</router-link>
        </div>
        <div class="wrap">
          <p>Not join our site yet,</p>
          <router-link v-bind:to="{ name: 'Join' }" class="btn--text">Sign up</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "../../assets/css/style.scss";
import "../../assets/css/user.scss";
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import KakaoLogin from "../../components/user/snsLogin/Kakao.vue";
// import GoogleLogin from "../../components/user/snsLogin/Google.vue";
import NaverLogin from "../../components/user/snsLogin/Naver.vue";
import UserApi from "../../apis/UserApi";

const storage = window.sessionStorage;

export default {
  components: {
    KakaoLogin,
    // GoogleLogin,
    NaverLogin
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
    password: function(v) {
      this.checkForm();
    },
    email: function(v) {
      this.checkForm();
    }
  },
  methods: {
    checkForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    login() {
      if (this.isSubmit) {
        let { email, password } = this;
        let data = {
          email,
          password
        };
        //요청 후에는 버튼 비활성화
        this.isSubmit = false;
        UserApi.requestLogin(
          data,
          res => {
            //통신을 통해 전달받은 값 콘솔에 출력
            if (res.data.status) {
              storage.setItem("Authorization", res.headers["authorization"]);
              storage.setItem("login_username", res.data.object.nickname);
              storage.setItem("login_useremail", res.data.object.email);
              this.$store.state.username = res.data.object.nickname;
              this.$store.state.useremail = res.data.object.email;
              this.$router.push("/");
            }
            //요청이 끝나면 버튼 활성화
            this.isSubmit = true;
          },
          error => {
            //요청이 끝나면 버튼 활성화
            this.isSubmit = true;
          }
        );
      }
    }
  },
  data: () => {
    return {
      email: "",
      password: "",
      passwordSchema: new PV(),
      error: {
        email: false,
        password: false
      },
      isSubmit: false,
      component: this
    };
  }
};
</script>
<style>
#title {
  font-family: "Libre Caslon Text", serif;
  color: white !important;
}
#label-text {
  color: white !important;
}
input::placeholder {
  color: white;
}
</style>
