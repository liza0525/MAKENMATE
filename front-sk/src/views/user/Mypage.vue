<template>
  <v-container>
    <v-row no-gutters>
      <v-col cols="4">
        <v-card>
          <v-row>
            <v-card>
              <img :src="user.image" />
            </v-card>
          </v-row>
          <v-row>
            <p>{{user.nickname}}님 환영합니다.</p>
          </v-row>
        </v-card>
      </v-col>
      <v-col>
        <v-card class="pa-2" outlined tile>
          <v-row>
            <router-link v-bind:to="{ name: 'UserChangePW' }" class="btn--text">비밀번호 변경</router-link>
          </v-row>
          <v-row>
            <router-link v-bind:to="{ name: 'UserWithdraw' }" class="btn--text">회원 탈퇴</router-link>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import Constant from "../../Constant";
export default {
  data: () => {
    return {
      user: {
        email: "",
        nickname: "",
        image: ""
      }
    };
  },
  mounted() {
    // this.user.email = this.$router.params.user.email;
    // this.user.nickname = this.$router.params.user.nickname;
    // this.user.image = this.$router.params.user.image;
    console.log(this.$store.state.username);
    this.$store
      .dispatch(Constant.GET_USERINFO, { username: this.$store.state.username })
      .then(() => {
        this.user = { ...this.$store.state.user };
        if (this.user.image === null)
          this.user.image = require(`../../../../images/default.png`);
        console.log(this.user);
      });
  }
};
</script>
