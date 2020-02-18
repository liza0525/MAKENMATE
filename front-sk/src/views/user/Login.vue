<template>
  <div class="account-bg">
    <div>
      <div id="login-window">
        <v-row>
          <v-col class="neon-card mx-auto" style="width: 30vw; background-color: black">
            <h1 class="neon-label" style="text-align: center;">레시피 보러 로그인&#x1F378;</h1>
            <label class="neon-label col-12 mb-2">이메일</label>
            <input
              v-model="email"
              id="email"
              placeholder="이메일을 입력하세요."
              class="neon-input col-12 mb-4"
              @keyup.enter="login"
              type="text"
            />
            <div class="error-text" v-if="error.email">{{ error.email }}</div>
            <label class="neon-label col-12 mb-2">비밀번호</label>
            <input
              v-model="password"
              id="password"
              @keyup.enter="login"
              placeholder="비밀번호를 입력하세요."
              :readonly="isKakao"
              type="password"
              class="neon-input col-12 mb-4"
            />
            <div class="error-text" v-if="password != 0 && error.password">{{ error.password }}</div>
            <div class="my-5 width-100">
              <button
                type="button"
                v-on:click="login()"
                :disabled="!isSubmit"
                :class="{ disabled: !isSubmit }"
                class="neon-btn col-12 mb-4"
              >Log In</button>
              <div class="sns-login">
                <div class="text">
                  <p id="label-title">SNS 로그인</p>
                </div>
                    
                <kakaoLogin :component="component" />
                <!-- <GoogleLogin :component="component" /> -->
                <NaverLogin :component="component"  />
              </div>
              <div class="add-option">
                <div class="text">
                  <p id="label-title">etc.</p>
                </div>
                <div class="wrap">
                  <span style="color: white;">비밀번호 잊으셨나요?</span>
                  <router-link
                    v-bind:to="{ name: 'InputEmail' }"
                    class="btn--text"
                    id="etc-link"
                  >비밀번호 변경</router-link>
                </div>
                <div class="wrap">
                  <span style="color: white;">아직 회원이 아니라면?</span>
                  <router-link v-bind:to="{ name: 'Join' }" class="btn--text" id="etc-link">회원가입</router-link>
                </div>
              </div>
            </div>
          </v-col>
        </v-row>
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
#login-window {
  margin: 15vh auto;
}
.error-text {
  color: red;
  margin: 3px 0px 10px 5px;
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
.account-bg {
  background: linear-gradient(rgba(0, 0, 0, 0.7)),
    url("../../assets/images/account_bg.jpg") no-repeat;
  background-size: cover;
  height: 100vh;
}
#sns-login {
  width: 100%;
}
#kakao-btn {
  margin-right: 10px;
}
#naver-btn,
#kakao-btn {
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
    width: 90%;
  }
}
@media (min-width: 600px) {
  #login-window {
    width: 50%;
  }
}
@media (min-width: 1024px) {
  #login-window {
    width: 30%;
  }
}
/* input 자동완성 */
input:-webkit-autofill,
input:-webkit-autofill:hover,
input:-webkit-autofill:focus,
input:-webkit-autofill:active {
transition: background-color 5000s ease-in-out 0s;
-webkit-transition: background-color 9999s ease-out;
  -webkit-text-fill-color: #fff !important;
}

/* neon */
:root {
  --neon-main: #f7f3f7;
  --neon-shadow: rgb(214, 128, 125);
  --neon-main-glitch: #f7f3f7c2;
  --neon-text-glitch: #f7f3f788;
  --neon-shadow-glitch: rgb(185, 92, 92);
}
.neon-btn {
  color: white;
  border: 3px solid var(--neon-main);
  box-shadow: 0px 0px 10px 3px var(--neon-main),
    inset 0px 0px 10px 3px var(--neon-main);
  border-radius: 25px;
  height: 50px;
  padding: 0;
  margin: 5px 0;
  text-shadow: 0 0 5px var(--neon-shadow);
  transition: box-shadow 0.25s ease;
  animation: neon-text-glitch 3s steps(1) infinite,
    neon-border-glitch 4.5s steps(1) infinite;
}
.neon-btn:focus {
  box-shadow: 0px 0px 25px 10px var(--neon-shadow),
    inset 0px 0px 25px 10px var(--neon-shadow);
  animation: neon-text-glitch 3s steps(1) infinite;
}
.neon-btn:active {
  box-shadow: 0px 0px 50px 20px var(--neon-shadow),
    inset 0px 0px 25px 13px var(--neon-shadow);
  animation: neon-text-glitch 3s steps(1) infinite;
}
.neon-card {
  border: 3px solid var(--neon-main);
  border-radius: 15px;
  box-shadow: 0px 0px 45px 10px var(--neon-shadow),
    inset 0px 0px 25px 10px var(--neon-shadow);
  padding: 25px;
  color: var(--neon-main);
  text-shadow: 0 0 5px var(--neon-shadow);
  animation: neon-card-glitch 4s steps(1) infinite;
}

@keyframes neon-card-glitch {
  0% {
    box-shadow: 0px 0px 15px 0px var(--neon-shadow),
      inset 0px 0px 15px 0px var(--neon-shadow);
  }
  95% {
    box-shadow: 0px 0px 15px 3px var(--neon-shadow-glitch),
      inset 0px 0px 15px 3px var(--neon-shadow-glitch);
  }
  100% {
    box-shadow: 0px 0px 15px 0px var(--neon-shadow),
      inset 0px 0px 15px 0px var(--neon-shadow);
  }
}
.neon-label {
  color: var(--neon-main);
  text-shadow: 0 0 5px var(--neon-shadow);
  animation: neon-label-glitch 3s steps(1) infinite;
  margin: 5px 0;
}
.neon-label::after {
  content: "";
  width: 3px;
  height: 20px;
  background-color: #000;
  position: absolute;
  bottom: -10px;
  left: 16px;
  z-index: -1;
  border-radius: 3px;
}
.neon-input {
  margin: 10px 0;
  border-radius: 25px;
  box-shadow: 0px 0px 25px 10px var(--neon-shadow),
    inset 0px 0px 15px 4px var(--neon-shadow);
  border: 3px solid var(--neon-main);
  background-color: transparent;
  color: var(--neon-main);
  outline: none !important;
  padding: 7px 20px;
  animation: neon-input-glitch 3s steps(1) infinite;
}
.neon-input:focus {
  box-shadow: 0px 0px 25px 10px var(--neon-shadow),
    inset 0px 0px 15px 7px var(--neon-shadow);
  animation: none;
}

.neon-input_disabled {
  box-shadow: 0px 0px 2px 1px var(--neon-shadow-glitch),
    inset 0px 0px 2px 1px var(--neon-shadow-glitch);
  animation: none;
  border: 3px solid var(--neon-main-glitch);
}

@keyframes neon-input-glitch {
  0% {
    box-shadow: 0px 0px 15px 4px var(--neon-shadow),
      inset 0px 0px 15px 4px var(--neon-shadow);
  }

  85% {
    box-shadow: 0px 0px 15px 4px var(--neon-shadow),
      inset 0px 0px 15px 4px var(--neon-shadow);
  }

  90% {
    box-shadow: 0px 0px 10px 4px var(--neon-shadow-glitch),
      inset 0px 0px 10px 4px var(--neon-shadow-glitch);
  }

  100% {
    box-shadow: 0px 0px 15px 4px var(--neon-shadow),
      inset 0px 0px 15px 4px var(--neon-shadow);
  }
}

@keyframes neon-label-glitch {
  0% {
    color: var(--neon-main);
    text-shadow: 0 0 5px var(--neon-shadow);
  }

  85% {
    color: var(--neon-main);
    text-shadow: 0 0 5px var(--neon-shadow);
  }

  90% {
    color: var(--neon-main-glitch);
    text-shadow: 0 0 1px var(--neon-shadow-glitch);
  }

  100% {
    color: var(--neon-main);
    text-shadow: 0 0 5px var(--neon-shadow);
  }
}
.neon-input ~ .neon-input {
  animation-delay: 0.5s;
}
.neon-label ~ .neon-label {
  animation-delay: 1s;
}
</style>