<template>
  <div class="account-bg">
    <div>
      <div id="join-window">
        <v-row>
          <v-col class="neon-card mx-auto" style="width: 30vw; background-color: black">
            <h1 class="neon-label" style="text-align: center;">한 잔 하러 회원가입&#x1F378;</h1>
            <label class="neon-label col-12 mb-2">닉네임</label>
            <input
              v-model="nickname"
              id="nickname"
              placeholder="닉네임을 입력하세요."
              style="disabled:true;"
              :readonly="isKakao"
              type="text"
              class="neon-input col-12 mb-4"
            />
            <label class="neon-label col-12 mb-2">이메일</label>
            <input
              v-model="email"
              id="email"
              placeholder="이메일을 입력하세요."
              class="neon-input col-12 mb-4"
            />
            <!-- <button @click="checkEmail()" :disabled="!duplicateEmail">중복 체크</button> -->
            <div class="error-text" v-if="error.email">{{ error.email }}</div>
            <label class="neon-label col-12 mb-2">비밀번호</label>
            <input
              v-model="password"
              id="password"
              placeholder="비밀번호를 입력하세요."
              :readonly="isKakao"
              type="password"
              class="neon-input col-12 mb-4"
            />
            <div class="error-text" v-if="password != 0 && error.password">{{ error.password }}</div>
            <label class="neon-label col-12 mb-2">비밀번호 확인</label>
            <input
              v-model="passwordConfirm"
              :type="passwordConfirmType"
              id="password-confirm"
              placeholder="비밀번호를 다시한번 입력하세요."
              :readonly="isKakao"
              class="neon-input col-12 mb-4"
            />
            <div
              class="error-text"
              v-if="passwordConfirm != 0 && password != passwordConfirm"
            >{{ error.passwordConfirm }}</div>
            <label>
              <input @click="termConfirm" type="checkbox" id="term" />
              <span>약관에 동의합니다.</span>
            </label>
            <span
              @click="termPopup = !termPopup"
              style="margin-top: 5px; margin-left: 10px; cursor: pointer;"
            >약관 보기</span>

            <v-card v-if="termPopup" dark scrollable style="10px">
              <v-card-title class="headline">Cocktail 이용 약관 동의</v-card-title>
              <v-card-text>개인정보 수집 및 이용 동의(필수)<br />
              위치 정보 제공 동의(필수)
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
            <div class="my-5 width-100">
              <button
                type="button"
                v-on:click="join()"
                :disabled="!isSubmit"
                :class="{ disabled: !isSubmit }"
                class="neon-btn col-12 mb-4"
              >Sign Up</button>
            </div>
          </v-col>
        </v-row>
      </div>
    </div>
  </div>
</template>

<script>
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
      termPopup: false,
      isKakao: false
    };
  },
  created() {
    this.component = this;
    console.log(this.$route.params.nickname);
    if (
      !(
        this.$route.params.nickname == "undefined" ||
        this.$route.params.nickname == null ||
        this.$route.params.nickname == ""
      )
    ) {
      this.nickname = this.$route.params.nickname;
      this.password = "kakao4312!@#$";
      this.passwordConfirm = "kakao4312!@#$";
      this.isKakao = true;
    }
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
    },
    termConfirm() {
      this.isTerm = !this.isTerm;
      this.termPopup = false;
    }
  }
};
</script>
<style>
#join-window {
  margin: 15vh auto;
}
/* check box */
[type="checkbox"]:checked + span:before,
[type="radio"]:checked + span:before {
  background: rgb(194, 78, 97);
  border: 1px solid rgb(194, 78, 97);
}
[type="checkbox"]:checked + span,
[type="checkbox"]:not(:checked) + span,
[type="radio"]:checked + span,
[type="radio"]:not(:checked) + span {
  position: relative;
  padding-left: 38px;
  cursor: pointer;
  display: inline-block;
  color: rgb(255, 255, 255);
  font-weight: 600;
  line-height: 30px;
}
@media (min-width: 0px) {
  #join-window {
    width: 90%;
  }
}
@media (min-width: 600px) {
  #join-window {
    width: 50%;
  }
}
@media (min-width: 1024px) {
  #join-window {
    width: 30%;
  }
}

/* neon */

.neon-input ~ .neon-input ~ .neon-input {
  animation-delay: 1.5s;
}
.neon-input ~ .neon-input ~ .neon-input ~ .neon-input {
  animation-delay: 1s;
}
</style>
