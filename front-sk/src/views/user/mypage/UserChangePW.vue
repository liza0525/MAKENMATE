<template>
  <div class="account-bg">
    <div>
      <div id="login-window">
        <v-row>
          <v-col
            class="neon-card mx-auto"
            style="padding: 10%;width: 30vw; background-color: black"
          >
            <h1 class="neon-label" style="text-align: center;margin-bottom:10%">
              비밀번호 변경
            </h1>
            <label for="password" class="neon-label col-12 mb-2">
              기존 비밀번호
            </label>
            <input
              v-model="password"
              type="password"
              id="password"
              placeholder="기존 비밀번호를 입력하세요."
              class="neon-input col-12 mb-4"
            />
            <label for="new-password " class="neon-label col-12 mb-2"
              >비밀번호</label
            >
            <input
              v-model="newPassword"
              :type="newPasswordType"
              id="new-password"
              placeholder="새 비밀번호를 입력하세요."
              class="neon-input col-12 mb-4"
            />
            <div
              class=" error-text neon-label col-12 mb-2"
              v-if="error.newPassword != false"
            >
              {{ error.newPassword }}
            </div>
            <label for="password-confirm" class="neon-label col-12 mb-2"
              >비밀번호 확인</label
            >
            <input
              v-model="passwordConfirm"
              :type="passwordConfirmType"
              id="password-confirm"
              placeholder="새 비밀번호를 다시한번 입력하세요."
              class="neon-input col-12 mb-4"
            />
            <div
              class=" error-text neon-label col-12 mb-2"
              v-if="error.passwordConfirm != false"
            >
              {{ error.passwordConfirm }}
            </div>
            <button
              class="btn--text neon-btn col-12 mb-4;"
              v-on:click="updatePW()"
              :disabled="!isSubmit"
              :class="{ disabled: !isSubmit }"
            >
              비밀번호 변경
            </button>
            <div
              class=" error-text neon-label col-12 mb-2"
              v-if="isSubmit === true && error.password"
            >
              {{ error.password }}
            </div>
          </v-col>
        </v-row>
      </div>
    </div>
  </div>
</template>
<script>
import PasswordValidator from "password-validator";
import http from "../../../http-common";
export default {
  data: () => {
    return {
      email: "",
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
        http
          .put("/user/changePW", null, {
            params: {
              nickname: this.$store.state.username,
              befPassword: this.password,
              password: this.newPassword
            }
          })
          .then(res => {
            if (res.data.data == "success") {
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

<style scoped>
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
    url("../../../assets/images/account_bg.jpg") no-repeat;
  background-size: cover;
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
