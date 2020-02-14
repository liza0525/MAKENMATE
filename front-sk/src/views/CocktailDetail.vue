<template>
  <div class="test">
    <img
      src="../assets/images/cocktail_list_copy02.jpg"
      class="rightImage titlefont"
      alt="cocktail_list_background"
    />
    <div class="hcontainer">
      <div
        class="sign titlefont"
        style="z-index:200;font-size:400%;margin-top:10%;margin-bottom:3%;text-align:center;"
      >
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
      </div>
      <i class="fas fa-4x fa-quote-left" style="color:white;opacity:0.5"></i>
      <div style="text-align:center;">
        <div
          class="sansfont"
          style="display:inline-block;font-size:130%;margin-left:4%;margin-right:4%;opacity:0.7;font-style:italic"
          v-html="cocktail.description"
        ></div>
      </div>
      <div style="text-align:right;margin-bottom:5%;padding-right:3%">
        <i
          class="fas fa-4x fa-quote-right"
          style="color:white;opacity:0.5;"
        ></i>
      </div>
      <div style="white">{{ materials[0] }} , {{ cocktail.method }}</div>
      <v-row justify="space-around">
        <div
          v-show="!(materials[0] != '' || cocktail.method != '')"
          style="text-align:center;"
        >
          <img v-bind:src="cocktail.image" style="width:30%;" />
        </div>
        <v-col v-show="materials[0] != '' || cocktail.method != ''" cols="6">
          <v-img
            v-bind:src="cocktail.image"
            style="width:70%; float:right;"
          ></v-img>
        </v-col>
        <v-col cols="6">
          <div class="subheading">
            <div v-if="materials[0] != ''">
              <v-row no-gutters style="padding-top:5%;padding-bottom:5%">
                <v-col
                  v-for="(m, i) in materials"
                  :key="i"
                  cols="6"
                  style="text-align:center;"
                >
                  <v-card
                    outlined
                    tile
                    class="sansfont"
                    style="background-color: transparent; font-size:130%;"
                    >{{ materials[i] }}</v-card
                  >
                </v-col>
              </v-row>
            </div>
            <div v-show="cocktail.method">
              <span
                class="sansfont"
                style="font-size:500%;opacity:0.7;font-style:italic;font-weight: 900;"
                >1.</span
              >
              <div
                class="sansfont"
                style=" display:inline; font-size:130%;"
                v-html="cocktail.method"
              ></div>
            </div>
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
          <span>{{ users[i] }} : {{ re.content }}</span>
          <p v-if="username === users[i]" style="display:inline-block;">
            <button @click="click(i)">수정</button>
            <button @click="deleteComment(i, re.cmid)">삭제</button>
          </p>
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
      </div>
      <!-- </v-text> -->
      <input type="text" v-model="comment" />
      <button @click="submitComment" type="submit">button</button>
      <div>
        <button v-for="pageNm in pageNms" :key="pageNm" @click="search(pageNm)">
          <span style="margin-right:10px;">{{ pageNm }}</span>
        </button>
      </div>
    </div>
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
      username: "",
      comment: "",
      isInput: [],
      updatedComment: "",
      pageNm: 1,
      pageNms: []
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
        if (this.cocktail.method) {
          this.cocktail.method = this.cocktail.method.replace("1.", "");
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

body {
  background-color: black !important;
  z-index: -1;
}
.hcontainer {
  margin-left: 15%;
  margin-right: 15%;
}
.rightImage {
  bottom: 0px;
  right: 1%;
  width: 30%;
  height: auto;
  position: fixed;
  background: linear-gradient(rgba(0, 0, 0, 0.7));
  z-index: -1;
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
  /* font-family: "국립박물관문화재단클래식B"; */
  font-family: "Jua", sans-serif;
  /* font-family: "Malgun Gothic"; */
}
.sign {
  min-height: 100%;
  /* font-family: "Vibur", cursive; */
  text-align: center;
  font-weight: border;
  line-height: 1;
  color: #c6e2ff;
  animation: neon 0.08s ease-in-out infinite alternate;
}

.x-sign {
  --interval: 1s;
  text-shadow: 0 0 10px var(--color1), 0 0 20px var(--color2),
    0 0 40px var(--color3), 0 0 80px var(--color4);
  will-change: filter, color;
  filter: saturate(60%);
  animation: flicker steps(100) var(--interval) 1s infinite;
  color: lightyellow;
  --color1: yellow;
  --color2: orange;
  --color3: brown;
  --color4: purple;
  font-family: Bad Script;
}
.neon-button {
  position: fixed;
  margin-top: 18%;
  float: left;
  width: 3%;
  padding-top: 1%;
  padding-bottom: 1%;
  font-size: 1.3rem;
  font-weight: bolder;
  font-family: "Jua", sans-serif;
  animation: neon-box 0.08s ease-in-out infinite alternate;
  color: #c6e2ff;
  border: 2px solid;
  border-radius: 5px;
  background-color: transparent;
}
.neon-button:hover {
  position: fixed;
  margin-top: 18%;
  float: left;
  width: 3%;
  padding-top: 1%;
  padding-bottom: 1%;
  font-size: 1.3rem;
  font-weight: bolder;
  font-family: "Jua", sans-serif;
  animation: neon-box 0.08s ease-in-out infinite alternate;
  color: rgba(242, 30, 178, 0.52);
  border: 2px solid;
  border-radius: 5px;
  background-color: #ffffff;
}

.neon-input {
  width: 15%;
  padding-top: 1%;
  padding-bottom: 1%;
  font-size: 1.3rem;
  font-family: "GyeonggiBatang";
  color: #c6e2ff;
  border: 2px solid #ffffff;
  border-radius: 5px;
  background-color: transparent;
}
.paging-size {
  font-size: 150%;
  font-family: "국립박물관문화재단클래식B";
}
.neon-input::placeholder {
  font-family: "GyeonggiBatang";
  font-size: 1.3rem;
  color: #c6e2ff;
  animation: neon-box 0.08s ease-in-out infinite alternate;
}
.sansfont {
  /* font-family: "MapoFlowerIsland"; */
  /* font-family: "MapoGoldenPier"; */
  font-family: "GyeonggiBatang";
  /* font-family: "Jua", sans-serif; */
  /* font-family: "Malgun Gothic"; */
}
.cocktailfont {
  font-family: "국립박물관문화재단클래식B";
}
/*-- Animation Keyframes --*/

@keyframes neon {
  from {
    text-shadow: 0 0 6px rgba(202, 228, 225, 0.92),
      0 0 30px rgba(202, 228, 225, 0.34), 0 0 12px rgba(242, 30, 178, 0.52),
      0 0 21px rgba(242, 30, 178, 0.92), 0 0 34px rgba(242, 30, 178, 0.78),
      0 0 54px rgba(242, 30, 178, 0.92);
  }
  to {
    text-shadow: 0 0 6px rgba(202, 228, 225, 0.98),
      0 0 30px rgba(202, 228, 225, 0.42), 0 0 12px rgba(242, 30, 178, 0.58),
      0 0 22px rgba(242, 30, 178, 0.84), 0 0 38px rgba(242, 30, 178, 0.88),
      0 0 60px rgba(242, 30, 178, 1);
  }
}
@keyframes neon-box {
  from {
    text-shadow: 0 0 6px rgba(202, 228, 225, 0.92),
      0 0 30px rgba(202, 228, 225, 0.34), 0 0 12px rgba(242, 30, 178, 0.52),
      0 0 21px rgba(242, 30, 178, 0.92), 0 0 34px rgba(242, 30, 178, 0.78),
      0 0 54px rgba(242, 30, 178, 0.92);
    box-shadow: 0 0 6px rgba(202, 228, 225, 0.92),
      0 0 30px rgba(202, 228, 225, 0.34), 0 0 12px rgba(242, 30, 178, 0.52),
      0 0 21px rgba(242, 30, 178, 0.92), 0 0 34px rgba(242, 30, 178, 0.78),
      0 0 54px rgba(242, 30, 178, 0.92);
  }
  to {
    text-shadow: 0 0 6px rgba(202, 228, 225, 0.98),
      0 0 30px rgba(202, 228, 225, 0.42), 0 0 12px rgba(242, 30, 178, 0.58),
      0 0 22px rgba(242, 30, 178, 0.84), 0 0 38px rgba(242, 30, 178, 0.88),
      0 0 60px rgba(242, 30, 178, 1);
    box-shadow: 0 0 6px rgba(202, 228, 225, 0.98),
      0 0 30px rgba(202, 228, 225, 0.42), 0 0 12px rgba(242, 30, 178, 0.58),
      0 0 22px rgba(242, 30, 178, 0.84), 0 0 38px rgba(242, 30, 178, 0.88),
      0 0 60px rgba(242, 30, 178, 1);
  }
}
@keyframes flicker {
  50% {
    color: white;
    filter: saturate(200%) hue-rotate(20deg);
  }
}
</style>
