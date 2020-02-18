<template>
  <div class="account-bg">
    <div>
      <div id="send-email-window">
        <v-row>
          <v-col class="neon-card mx-auto" style="width: 30vw; background-color: black">
            <h1 class="neon-text">메일 전송</h1>
            <p class="neon-text">메일이 전송되었습니다.</p>
            <router-link v-bind:to="{name:'Login'}">
              <button class="neon-btn col-12">로그인 화면</button>
            </router-link>
            <router-link v-bind:to="{ name: 'InputEmail' }">
              <button class="neon-btn col-12">메일이 도착하지 않았나요?</button>
            </router-link>
          </v-col>
        </v-row>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
#send-email-window {
  margin: 15vh auto;
}

@media (min-width: 0px) {
  #send-email-window {
    width: 90%;
  }
}
@media (min-width: 600px) {
  #send-email-window {
    width: 50%;
  }
}
@media (min-width: 1024px) {
  #send-email-window {
    width: 30%;
  }
}
</style>