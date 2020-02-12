<template>
  <div class="test">
    <v-container fluid style="width:60%">
      <div class="title mb-1" style="text-align:center;">
        <h1>
          <b>
            {{ cocktail.cname }}
            <button @click="clickLike">
              <span v-show="!islike">
                <i class="far fa-heart"></i>
              </span>
              <span v-show="islike">
                <i class="fas fa-heart"></i>
              </span>
            </button>
            {{ likebycocktail }}
          </b>
        </h1>
      </div>
      <v-row justify="space-around">
        <v-col cols="4">
          <v-img v-bind:src="cocktail.image"></v-img>
          <div class="subheading pt-4 sansfont">#고독을 즐기는</div>
        </v-col>
        <v-col cols="6">
          <div class="subheading">
            <v-row justify="space-around">
              <v-col cols="2">
                <b class="titlefont">유래</b>
              </v-col>
              <v-col cols="10">
                <div
                  class="sansfont"
                  style="font-size:18px;"
                  v-html="cocktail.description"
                ></div>
              </v-col>
            </v-row>
          </div>
          <div class="subheading pt-4">
            <v-row justify="space-around">
              <v-col cols="2">
                <b class="titlefont">재료</b>
              </v-col>
              <v-col cols="10" v-if="materials[0] != ''">
                <v-row no-gutters>
                  <v-col v-for="(m, i) in materials" :key="i" cols="6">
                    <v-card outlined tile class="sansfont">{{
                      materials[i]
                    }}</v-card>
                  </v-col>
                </v-row>
              </v-col>
            </v-row>
          </div>
          <div class="subheading pt-4">
            <v-row justify="space-around">
              <v-col cols="2">
                <b class="titlefont">만드는 법</b>
              </v-col>
              <v-col cols="10">
                <div class="sansfont" v-html="cocktail.method"></div>
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
        class="sansfont"
      >
        <!-- <v-text v-if="isInput[i]"> -->
        <div v-if="isInput[i] === 0">
          <span class="sansfont">{{ users[i] }} : {{ re.content }}</span>
          <button @click="click(i)" class="sansfont">수정</button>
          <button @click="deleteComment(i, re.cmid)" class="sansfont">
            삭제
          </button>
        </div>
        <div v-else>
          <span class="sansfont">
            {{ users[i] }} :
            <input v-model="re.content" />
          </span>
          <p
            v-if="username === users[i]"
            class="sansfont"
            style="display:inline-block;"
          >
            <button
              @click="updateComment(i, re.cmid, re.content)"
              class="sansfont"
            >
              수정
            </button>
          </p>
        </div>
        <button @click="clickLikeComments(i)">
          <span v-show="!isLike[i]">
            <i class="far fa-heart"></i>
          </span>
          <span v-show="isLike[i]">
            <i class="fas fa-heart"></i>
          </span>
        </button>
        {{ likebycomments[i] }}
      </div>
      <!-- </v-text> -->
      <input type="text" v-model="comment" />
      <button @click="submitComment" type="submit">button</button>
      <div>
        <button v-for="pageNm in pageNms" :key="pageNm" @click="search(pageNm)">
          <span style="margin-right:10px;">{{ pageNm }}</span>
        </button>
      </div>
    </v-container>
  </div>
</template>
<script>
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
      email: "",
      comment: "",
      isInput: [],
      username: "",
      updatedComment: "",
      pageNm: 1,
      pageNms: [],
      isLike: [],
      likebycomments: []
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
          .dispatch(Constant.GET_REPLY, {
            cid: this.$route.params.cid,
            pageNm: 1
          })
          .then(() => {
            this.reply = { ...this.$store.state.reply };
            this.users = { ...this.$store.state.users };
            for (let i = 0; i < this.reply.length; ++i) {
              this.isInput.push(0);
              this.likebycomments.push(0);
              this.$store
                .dispatch(Constant.GET_LIKEBYUSERANDCOCKTAILCOMMENTS, {
                  username: this.$store.state.username,
                  cmid: this.reply[i].cmid
                })
                .then(res => {
                  let list = [...this.isLike];
                  if (res.data.object == null) list.splice(i, 1, false);
                  else list.splice(i, 1, true);
                  this.isLike = list;
                });
              this.$store
                .dispatch(Constant.GET_LIKEBYCOCKTAILCOMMENTS, {
                  cmid: this.reply[i].cmid
                })
                .then(res => {
                  let list = [...this.likebycomments];
                  list.splice(i, 1, this.$store.state.likebycomments);
                  this.likebycomments = list;
                });
            }
            let arr = [];

            let min = 1;
            for (let index = 0; index < 5; index++) {
              if (Number(min + index) > this.$store.state.totalPages) break;
              arr.push(Number(min + index));
            }
            this.pageNms = arr;
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
        return this.$store.state.isLike;
      }
    }
  },
  methods: {
    submitComment() {
      this.$store.dispatch(Constant.ADD_REPLY, {
        cid: this.cocktail.cid,
        username: this.$store.state.username,
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
    },
    clickLikeComments(i) {
      console.log(this.reply[i]);

      if (this.isLike[i] == false) {
        this.$store
          .dispatch(Constant.ADD_COCKTAILCOMMENTSLIKE, {
            cmid: this.reply[i].cmid,
            username: this.$store.state.username
          })
          .then(() => {
            let list = [...this.isLike];
            list.splice(i, 1, true);
            this.isLike = list;
            let list2 = [...this.likebycomments];
            list2.splice(i, 1, this.likebycomments[i] + 1);
            this.likebycomments = list2;
          });
      } else {
        this.$store
          .dispatch(Constant.REMOVE_COCKTAILCOMMENTSLIKE, {
            cmid: this.reply[i].cmid,
            username: this.$store.state.username
          })
          .then(() => {
            let list = [...this.isLike];
            0;
            list.splice(i, 1, false);
            this.isLike = list;

            let list2 = [...this.likebycomments];
            list2.splice(i, 1, this.likebycomments[i] - 1);
            this.likebycomments = list2;
          });
      }
    },
    search(pageNm) {
      this.$store
        .dispatch(Constant.GET_REPLY, {
          cid: this.$route.params.cid,
          pageNm: pageNm
        })
        .then(() => {
          this.reply = { ...this.$store.state.reply };
          this.users = { ...this.$store.state.users };
          for (let i = 0; i < this.reply.length; ++i) {
            this.isInput.push(0);
          }
          let arr = [];

          let min = parseInt((pageNm - 1) / 5) * 5 + 1;
          for (let index = 0; index < 5; index++) {
            if (Number(min + index) > this.$store.state.totalPages) break;
            arr.push(Number(min + index));
          }
          this.pageNms = arr;
        });
    }
  }
};
</script>
<style scoped>
@import url("https://fonts.googleapis.com/css?family=Song+Myung|Stylish&display=swap");
@font-face {
  font-family: "MapoFlowerIsland";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/MapoFlowerIslandA.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: "MapoGoldenPier";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/MapoGoldenPierA.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: "GyeonggiBatang";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/GyeonggiBatang.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: "국립박물관문화재단클래식B";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_twelve@1.0/국립박물관문화재단클래식B.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
@import url("https://fonts.googleapis.com/css?family=Jua&display=swap");

.test {
  background: linear-gradient(rgba(0, 0, 0, 0.7)),
    url(../assets/images/image.png) no-repeat;
}

.sansfont {
  color: #ffffff;
  /* font-family: "MapoFlowerIsland"; */
  /* font-family: "MapoGoldenPier"; */
  font-family: "GyeonggiBatang";
  line-height: 130%;
  /* font-family: "Jua", sans-serif; */
  /* font-family: "Malgun Gothic"; */
}
.titlefont {
  color: #ffffff;
  /* font-family: "MapoFlowerIsland"; */
  /* font-family: "MapoGoldenPier"; */
  /* font-family: "GyeonggiBatang"; */
  line-height: 130%;
  font-family: "국립박물관문화재단클래식B";
  /* font-family: "Jua", sans-serif; */
  /* font-family: "Malgun Gothic"; */
}
</style>
