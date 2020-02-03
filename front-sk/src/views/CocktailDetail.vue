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
    </v-container>
  </div>
</template>
<script>
const axios = require("axios");
import http from "../http-common";
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
      }
    };
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      this.cid = this.$route.params.cid;
      http.get("/cocktail/detail/" + this.cid).then(res => {
        this.cocktail = res.data.object;
        if (this.cocktail.image === "") {
          this.cocktail.image = require(`../../../images/default.png`);
        } else {
          this.cocktail.image = require(`../../../images/${this.cid}.jpg`);
        }
      });
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
