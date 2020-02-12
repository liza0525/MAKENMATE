<template>
  <v-container>
    <v-row no-gutters>
      <v-col class="col-1"></v-col>
      <div class="col-12 col-md-6 col-lg-4 col-sm-6" style="text-align:center;">
        <div
          style="margin-top:4%;display:inline-block;border-radius:100px; overflow: hidden;  height:62.5%; width: 80%; border-radius:100%;"
        >
          <img :src="user.image" style="width: 100%;" />
        </div>
        <h1 style="margin-top:1%;text-align:center">
          {{ user.nickname }}
        </h1>
        <p style="text-align:center;">(ID : {{ user.email }})</p>
      </div>
      <v-col class="col-1"></v-col>
      <v-col>
        <div style="margin-top:4%; font-size:300%; display:inline-block;">
          {{ user.nickname }}
        </div>
        <span style="margin-left:3%;font-size:150%;"
          >(ID : {{ user.email }})</span
        >
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
