<template>
  <div class="test">
    <v-container fluid style="width:60%">
      <div class="title mb-1" style="text-align:center;">
        <h1>
          <b>{{ cocktail.cname }}</b>
        </h1>
      </div>
      <v-row justify="space-around">
        <v-col cols="4">
          <v-img v-bind:src="cocktail.image"></v-img>
          <div class="subheading pt-4">#고독을 즐기는</div>
        </v-col>
        <v-col cols="6">
          <div class="subheading">
            <v-row justify="space-around">
              <v-col cols="2">
                <b>유래</b>
              </v-col>
              <v-col cols="10">
                <div v-html="cocktail.description"></div>
              </v-col>
            </v-row>
          </div>
          <div class="subheading pt-4">
            <v-row justify="space-around">
              <v-col cols="2">
                <b>재료</b>
              </v-col>
              <v-col cols="10">{{ cocktail.material }}</v-col>
            </v-row>
          </div>
          <div class="subheading pt-4">
            <v-row justify="space-around">
              <v-col cols="2">
                <b>만드는 법</b>
              </v-col>
              <v-col cols="10">
                <div v-html="cocktail.method"></div>
              </v-col>
            </v-row>
          </div>
        </v-col>
      </v-row>
      <v-text
        :v-if="reply"
        v-for="(re, i) in reply"
        :key="i"
        style="margin-top: 5px; display:block;"
      >
        {{ users[i] }} : {{ re.content }}
      </v-text>
      <input type="text" v-model="comment" />
      <button @click="submitComment" type="submit">button</button>
    </v-container>
  </div>
</template>
<script>
// const axios = require("axios");
// import http from "../http-common";
import Constant from "../Constant";
export default {
  data: () => {
    return {
      cid: 0,
      cocktail: {
        cname: "",
        snack: "",
        material: "",
        base: "",
        description: "",
        degree: "",
        cid: "",
        image: "",
        bar: "",
        method: ""
      },
      email: "test@test.com",
      comment: ""
    };
  },
  mounted() {
    this.$store
      .dispatch(Constant.GET_COCKTAIL, { cid: this.$route.params.cid })
      .then(() => {
        this.cocktail = { ...this.$store.state.cocktail };
        if (this.cocktail.image === "") {
          this.cocktail.image = require(`../../../images/default.png`);
        } else {
          this.cocktail.image = require(`../../../images/${this.cocktail.cid}.jpg`);
        }
      });
    this.$store
      .dispatch(Constant.GET_REPLY, { cid: this.$route.params.cid })
      .then(() => {
        this.reply = { ...this.$store.state.reply };
        this.users = { ...this.$store.state.users };
      });
  },
  computed: {
    reply() {
      console.log(this.$store.state.reply);
      return this.$store.state.reply;
    },
    users() {
      return this.$store.state.users;
    }
  },
  methods: {
    submitComment() {
      console.log(this.cocktail);
      this.$store.dispatch(Constant.ADD_REPLY, {
        cid: this.cocktail.cid,
        email: this.email,
        comment: this.comment
      });
      this.comment = "";
    }
  }
};
</script>
<style scoped>
.test {
  background: linear-gradient(rgba(0, 0, 0, 0.7)),
    url(../assets/images/image.png) no-repeat;
}
</style>
