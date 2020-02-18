<template>
  <div class="account-bg">
    <div>
      <div id="input-email-window">
        <v-row>
          <v-col class="neon-card mx-auto" style="width: 30vw; background-color: black">
            <h1>이메일 전송</h1>
            <div class="input-with-label">
              <input v-model="email" id="email" placeholder="이메일을 입력하세요." type="text" />
              <label class="neon-label" for="email">이메일</label>
            </div>
            <div class="error-text" v-if="error.email" style="color:red; margin-bottom: 7px;">{{ error.email }}</div>
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
/* neon */
:root {
  --neon-main: #f7f3f7;
  --neon-shadow: rgb(210, 153, 153);
  --neon-main-glitch: #f7f3f7c2;
  --neon-text-glitch: #f7f3f788;
  --neon-shadow-glitch: rgba(210, 153, 172, 0.76);
}
.neon-btn {
  background-color: transparent;
  color: var(--neon-main);
  border: 3px solid var(--neon-main);
  box-shadow: 0px 0px 25px 10px var(--neon-shadow),
    inset 0px 0px 25px 10px var(--neon-shadow);
  border-radius: 25px;
  height: 50px;
  padding: 0;
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
    box-shadow: 0px 0px 25px 10px var(--neon-shadow),
      inset 0px 0px 25px 10px var(--neon-shadow);
  }
  90% {
    box-shadow: 0px 0px 25px 10px var(--neon-shadow),
      inset 0px 0px 25px 10px var(--neon-shadow);
  }
  95% {
    box-shadow: 0px 0px 10px 5px var(--neon-shadow-glitch),
      inset 0px 0px 10px 5px var(--neon-shadow-glitch);
  }
  100% {
    box-shadow: 0px 0px 25px 10px var(--neon-shadow),
      inset 0px 0px 25px 10px var(--neon-shadow);
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