<template>
  <div class="account-bg">
    <div>
      <div id="success-page-window">
        <v-row>
          <v-col class="neon-card mx-auto" style="width: 30vw; background-color: black">
            <h1 class="neon-text">{{ msg }}완료</h1>
            <h2 class="neon-text" v-if="isTrue">{{ msg }}이 완료 되었습니다.</h2>
            <h2 class="neon-text" v-if="!isTrue">{{ msg }}가 완료 되었습니다.</h2>
            <p class="neon-text" v-if="isTrue">{{ msg }}이 완료되어 메일이 발송되었습니다.</p>
            <router-link v-bind:to="{ name: 'Main' }">
              <button class="neon-btn col-12">메인 화면</button>
            </router-link>
            <router-link v-if="isTrue" v-bind:to="{ name: 'InputEmail' }">
              <button class="neon-btn col-12">메일이 도착하지 않았나요?</button>
            </router-link>
          </v-col>
        </v-row>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data: () => {
    return {
      msg: "",
      isTrue: false
    };
  },
  mounted() {
    this.msg = this.$route.params.msg;
    // this.msg = "비밀번호 변경";
    if (this.msg === "회원가입" || this.msg === "비밀번호 변경") {
      this.isTrue = true;
    }
  }
};
</script>

<style>
#success-page-window {
  margin: 15vh auto;
}

@media (min-width: 0px) {
  #success-page-window {
    width: 90%;
  }
}
@media (min-width: 600px) {
  #success-page-window {
    width: 50%;
  }
}
@media (min-width: 1024px) {
  #success-page-window {
    width: 30%;
  }
}
</style>
