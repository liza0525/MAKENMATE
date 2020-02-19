<template>
  <div class="account-bg">
    <div>
      <div id="input-email-window">
        <v-row>
          <v-col class="neon-card mx-auto" style="width: 30vw; background-color: black">
            <h1>이메일 전송</h1>
            <input
              v-model="email"
              id="email"
              placeholder="이메일을 입력하세요."
              class="neon-input col-12 mb-4"
              @keyup.enter="login"
              type="text"
            />
            <div class="error-text" v-if="error.email">{{ error.email }}</div>
            <router-link v-bind:to="{name:'SendEmail'}">
              <button
                class="neon-btn col-12 mb-4"
                :disabled="!isSubmit"
                :class="{disabled : !isSubmit}"
              >메일 전송</button>
            </router-link>
          </v-col>
        </v-row>
      </div>
    </div>
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
      if (this.email.length > 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
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
#input-email-window {
  margin: 15vh auto;
}

@media (min-width: 0px) {
  #input-email-window {
    width: 90%;
  }
}
@media (min-width: 600px) {
  #input-email-window {
    width: 50%;
  }
}
@media (min-width: 1024px) {
  #input-email-window {
    width: 30%;
  }
}
</style>