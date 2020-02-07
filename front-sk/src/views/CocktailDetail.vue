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
                <b style>재료</b>
              </v-col>
              <v-col cols="10" v-if="materials[0] != ''">
                <v-row no-gutters>
                  <v-col v-for="(m, i) in materials" :key="i" cols="6">
                    <v-card outlined tile>{{ materials[i] }}</v-card>
                  </v-col>
                </v-row>
              </v-col>
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
      <div :v-if="reply" v-for="(re, i) in reply" :key="i" style="margin-top: 5px; display:block;">
        <!-- <v-text v-if="isInput[i]"> -->
        <div :v-model="isInput[i]">
          {{ users[i] }} : {{ re.content }}
          <p v-if="username === users[i]" style="display:inline-block;">
            <button @click="clicked = !clicked">수정</button>
            <button @click="deleteComment(i, re.cmid)">삭제</button>
          </p>
        </div>
        <div v-if="clicked">
          <input v-model="re.content" />
          <button @click="updateComment(i, re.cmid)">수정</button>
        </div>
      </div>
      <!-- </v-text> -->
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
      materials: [],
      email: "test@test.com",
      comment: "",
      isInput: [],
      username: "",
      updatedComment: ""
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
        this.materials = this.cocktail.material
          .replace(/'/g, "")
          .replace("[", "")
          .replace("]", "")
          .split(",");
      });
    if (this.materials === [""]) {
      this.materials = [];
    }
    this.$store
      .dispatch(Constant.GET_REPLY, { cid: this.$route.params.cid })
      .then(() => {
        this.reply = { ...this.$store.state.reply };
        this.users = { ...this.$store.state.users };
      });
    for (let i = 0; i < this.reply.length; ++i) {
      this.isInput.push(0);
    }
    console.log("-----------" + this.isInput);
    this.username = this.$store.state.username;
    if (this.username === undefined) {
      this.username = "h";
    }
  },
  computed: {
    users: {
      set(val) {
        console.log(val);
      },
      get() {
        return this.$store.state.users;
      }
    },
    reply: {
      set(val) {
        console.log(val);
      },
      get() {
        return this.$store.state.reply;
      }
    }
  },
  methods: {
    submitComment() {
      this.$store.dispatch(Constant.ADD_REPLY, {
        cid: this.cocktail.cid,
        email: this.email,
        comment: this.comment
      });
      this.comment = "";
    },
    updateComment(i, cmid, content) {
      console.log(this.isInput[i]);
      if (!this.isInput[i]) {
        this.isInput[i] = 1;
        console.log(this.isInput[i]);
      } else {
        this.$store.dispatch(Constant.MODIFY_REPLY, {
          cmid: cmid,
          content: this.updatedComment
        });
        this.isInput[i] = 0;
      }
    },
    deleteComment(i, cmid) {
      this.users.splice(i, 1);
      this.$store.dispatch(Constant.REMOVE_REPLY, {
        cmid: cmid,
        cid: this.cocktail.cid
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
