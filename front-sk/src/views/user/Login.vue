<template>
  <div id="login-bg">
    <div id="login-window">
      <h1 id="title">한 잔 하러 갈까요?&#x1F378;</h1>
      <div class="input-with-label">
        <input
          v-model="email"
          @keyup.enter="login"
          id="email"
          placeholder="이메일"
          type="text"
          style="color:white;"
        />
        <label for="email" id="label-text">이메일</label>
        <div class="error-text" v-if="error.email">{{ error.email }}</div>
      </div>

      <div class="input-with-label">
        <input
          v-model="password"
          type="password"
          id="password"
          @keyup.enter="login"
          placeholder="비밀번호"
          style="color:white;"
        />
        <label for="password" id="label-text">비밀번호</label>
        <div class="error-text" v-if="error.password">{{ error.password }}</div>
      </div>
      <button
        class="btn btn--back btn--login"
        v-on:click="login"
        :disabled="!isSubmit"
        :class="{ disabled: !isSubmit }"
      >로그인</button>
      <div class="sns-login">
        <div class="text">
          <p id="label-title">SNS 로그인</p>
        </div>
        <div id="sns-login-button">
          <kakaoLogin :component="component" />
          <!-- <GoogleLogin :component="component" /> -->
          <NaverLogin :component="component" />
        </div>
      </div>
      <div class="add-option">
        <div class="text">
          <p id="label-title">혹시,</p>
        </div>
        <div class="wrap">
          <span style="color: white;">비밀번호 잊으셨나요?</span>
          <router-link v-bind:to="{ name: 'InputEmail' }" class="btn--text" id="etc-link">비밀번호 변경</router-link>
        </div>
        <div class="wrap">
          <span style="color: white;">아직 회원이 아니라면?</span>
          <router-link v-bind:to="{ name: 'Join' }" class="btn--text" id="etc-link">회원가입</router-link>
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
@font-face {
  font-family: "MapoPeacefull";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/MapoPeacefullA.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
#title {
  font-family: "MapoPeacefull";
  color: white !important;
}
#label-text {
  color: white !important;
}
input::placeholder {
  color: white;
}
#login-bg {
  background: linear-gradient(rgba(0, 0, 0, 0.7)),
    url("../../assets/images/login_bg.jpg") no-repeat;
  background-size: cover;
}
#login-window {
  background: rgba(43, 19, 15, 0.815);
  padding: 20vh 5vw;
  height: 110vh;
  margin-left: auto;
}
#kakao-login {
  margin: 0 10px;
}
#naverIdLogin_loginButton {
  width: 55px;
  height: 55px;
  border-radius: 55px;
}
.btn--naver,
#kakao-login {
  display: inline-block;
}
#etc-link {
  float: right;
}
.wrap {
  margin: 1vh 0;
}
#label-title {
  color: white;
  margin: 3vh 0;
  text-align: right;
  border-bottom: 1px solid #fff;
}
@media (min-width: 0px) {
  #login-window {
    width: 100%;
    padding: 20vh 10vw;
  }
}
@media (min-width: 600px) {
  #login-window {
    width: 60%;
    padding: 20vh 7vw;
  }
}
@media (min-width: 1024px) {
  #login-window {
    width: 40%;
  }
}
</style>