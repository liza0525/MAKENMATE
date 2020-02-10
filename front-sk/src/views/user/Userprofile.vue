<template>
  <v-container>
    <v-row no-gutters>
      <v-col class="col-12 col-sm-4">
        <v-card style="text-align:center">
          <v-row style="display:inline-block;">
            <img
              :src="user.image"
              style="background: linear-gradient(rgba(0, 0, 0, 0.7))"
            />
          </v-row>
          <h1 style="margin-top:50px;text-align:center">
            {{ user.nickname }}
          </h1>
          <p style="text-align:center;margin-:50px;">(ID : {{ user.email }})</p>
        </v-card>
      </v-col>
      <v-col>
        <v-card class="pa-2" outlined tile>
          <v-row>
            <router-link v-bind:to="{ name: 'UserChangePW' }" class="btn--text"
              >비밀번호 변경</router-link
            >
          </v-row>
          <v-row>
            <router-link v-bind:to="{ name: 'UserWithdraw' }" class="btn--text"
              >회원 탈퇴</router-link
            >
          </v-row>
        </v-card>
      </v-col>
    </v-row>
    <v-row> </v-row>
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
