<template>
  <div class="test">
    <v-container fluid style="width:60%">
      <div class="title mb-1" style="text-align:center;">
        <h1>
          <b>
            {{ cocktail.cname }}
            <button @click="clickLike">
              <span v-show="!islike"><i class="far fa-heart"></i></span>
              <span v-show="islike"><i class="fas fa-heart"></i></span>
            </button>
            {{ likebycocktail }}
          </b>
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
      <div
        :v-if="reply"
        v-for="(re, i) in reply"
        :key="i"
        style="margin-top: 5px; display:block;"
      >
        <!-- <v-text v-if="isInput[i]"> -->
        <div v-if="isInput[i] === 0">
          <span>{{ users[i] }} : {{ re.content }}</span>
          <button @click="click(i)">수정</button>
          <button @click="deleteComment(i, re.cmid)">삭제</button>
        </div>
        <div v-else>
          <span>
            {{ users[i] }} :
            <input v-model="re.content" />
          </span>
          <p v-if="username === users[i]" style="display:inline-block;">
            <button @click="updateComment(i, re.cmid, re.content)">수정</button>
          </p>
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
        if (this.materials === [""]) {
          this.materials = [];
        }
        this.$store
          .dispatch(Constant.GET_REPLY, { cid: this.$route.params.cid })
          .then(() => {
            this.reply = { ...this.$store.state.reply };
            this.users = { ...this.$store.state.users };
            for (let i = 0; i < this.reply.length; ++i) {
              this.isInput.push(0);
            }
            this.$store
              .dispatch(Constant.GET_LIKEBYCOCKTAIL, {
                cid: this.$route.params.cid
              })
              .then(() => {
                this.$store.dispatch(Constant.GET_LIKEBYUSERANDCOCKTAIL, {
                  cid: this.cocktail.cid,
                  username: this.$store.state.username
                });
              });
            console.log(this.isInput);
          });
      });
    this.username = this.$store.state.username;
    if (this.username === undefined) {
      this.username = "h";
    }
  },
  computed: {
    users: {
      set(val) {},
      get() {
        return this.$store.state.users;
      }
    },
    reply: {
      set(val) {},
      get() {
        return this.$store.state.reply;
      }
    },
    clicked: {
      set(val) {
        this.isInput[val] = 1;
      },
      get() {
        return true;
      }
    },
    likebycocktail() {
      return this.$store.state.likebycocktail;
    },
    islike: {
      set(val) {
        this.$store.state.isLike = val;
      },
      get() {
        console.log("test " + this.$store.state.isLike);
        return this.$store.state.isLike;
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
      this.isInput.push(0);
      this.users.push(this.username);
      this.comment = "";
    },
    updateComment(i, cmid, content) {
      this.$store
        .dispatch(Constant.MODIFY_REPLY, {
          cmid: cmid,
          content: content,
          cid: this.cocktail.cid
        })
        .then(() => {
          let list = [...this.isInput];
          list.splice(i, 1, 0);
          this.isInput = list;
        });
    },
    deleteComment(i, cmid) {
      this.users.splice(i, 1);
      this.isInput.splice(i, 1);
      this.$store.dispatch(Constant.REMOVE_REPLY, {
        cmid: cmid,
        cid: this.cocktail.cid
      });
    },
    click(i) {
      let list = [...this.isInput];
      list.splice(i, 1, 1);
      this.isInput = list;
      console.log(this.isInput[i]);
    },
    clickLike() {
      if (this.islike == false) {
        this.$store
          .dispatch(Constant.ADD_COCKTAILLIKE, {
            cid: this.cocktail.cid,
            username: this.$store.state.username
          })
          .then(() => (this.islike = this.$store.state.isLike));
      } else {
        this.$store
          .dispatch(Constant.REMOVE_COCKTAILLIKE, {
            cid: this.cocktail.cid,
            username: this.$store.state.username
          })
          .then(() => (this.islike = this.$store.state.isLike));
      }
    }
  }
};
</script>
<style scoped>
.test {
  /* background: linear-gradient(rgba(0, 0, 0, 0.7)),
    url(../assets/images/image.png) no-repeat; */
}
</style>
