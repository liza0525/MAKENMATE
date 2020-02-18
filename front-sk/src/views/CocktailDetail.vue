<template>
  <div class="test">
    <div class="cocktailheader">
      <div v-show="window.width >= 435">
        <h1
          class="cocktailtitle sign "
          style="z-index:200;margin-right;auto;margin-left;auto;display:inline-block;z-index:200;font-family: 'neontubes';"
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
        </h1>
      </div>
      <div v-show="window.width < 435">
        <h1
          class="cocktailtitle sign "
          style="z-index:200;margin-top:15vmax;margin-right;auto;margin-left;auto;display:inline-block;z-index:200;font-family: 'neontubes';"
        >
          {{ cocktail.cname }} <br />
          <button @click="clickLike" style="margin-top:10px;">
            <span v-show="!islike">
              <i class="far fa-heart"></i>
            </span>
            <span v-show="islike">
              <i class="fas fa-heart"></i>
            </span>
          </button>
          {{ likebycocktail }}
        </h1>
      </div>
    </div>
    <img
      src="../assets/images/cocktail_list_copy02.png"
      class="rightImage titlefont"
      alt="cocktail_detail_background"
    />
    <div class="hcontainer">
      <div v-show="window.width >= 435">
        <i class="fas fa-4x fa-quote-left" style="color:white;opacity:0.5"></i>
      </div>
      <div v-show="window.width < 435">
        <i class="fas fa-lg fa-quote-left" style="color:white;opacity:0.5"></i>
      </div>
      <div style="text-align:center;">
        <div
          class="sansfont"
          :style="{ 'font-size': fontsize }"
          style="display:inline-block;margin-left:4%;margin-right:4%;opacity:0.7;font-style:italic"
          v-html="cocktail.description"
        ></div>
      </div>
      <div
        v-show="window.width >= 435"
        style="text-align:right;margin-bottom:5%;padding-right:3%"
      >
        <i
          class="fas fa-4x fa-quote-right"
          style="color:white;opacity:0.5;"
        ></i>
      </div>
      <div
        v-show="window.width < 435"
        style="text-align:right;margin-bottom:5%;padding-right:3%"
      >
        <i
          class="fas fa-lg fa-quote-right"
          style="color:white;opacity:0.5;"
        ></i>
      </div>

      <v-row justify="space-around">
        <div
          v-show="!(materials[0] != '' || cocktail.method != '')"
          style="text-align:center;"
        >
          <img v-bind:src="cocktail.image" style="width:30%;" />
        </div>
        <v-col
          v-show="materials[0] != '' || cocktail.method != ''"
          md="6"
          sm="12"
        >
          <v-img
            v-show="window.width >= 1024"
            v-bind:src="cocktail.image"
            style="float:right;"
          ></v-img>
          <v-img
            v-show="window.width < 1024"
            v-bind:src="cocktail.image"
            style="float:center;"
          ></v-img>
        </v-col>
        <v-col md="6" sm="12">
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
                    style="background-color: transparent;"
                    :style="{ 'font-size': fontsize }"
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
                style=" display:inline;"
                :style="{ 'font-size': fontsize }"
                v-html="cocktail.method"
              ></div>
            </div>
          </div>
        </v-col>
      </v-row>
      <v-row style="margin-top:5%">
        <v-col cols="2">
          <div
            style="margin-top:4%; display:inline-block; overflow: hidden;  float:right;height:50px; width: 50px; border-radius:50px;"
          >
            <img
              :src="user.image"
              style="width:100%; height:100%;display:table-cell; vertical-align:middle"
            />
          </div>
        </v-col>
        <v-col>
          <input
            class="reply_input"
            type="text"
            v-model="comment"
            placeholder="댓글 추가"
            v-on:keyup.enter="submitComment"
          />
          <button @click="submitComment" class="neon reply_btn" type="submit">
            등록
          </button>
        </v-col>
      </v-row>
      <div
        :v-if="reply"
        v-for="(re, i) in reply"
        :key="i"
        style="margin-top: 5px; display:block;"
        class="sansfont"
      >
        <v-row>
          <v-col cols="2">
            <div
              style="margin-top:4%; display:inline-block; overflow: hidden; float:right; height:50px; width: 50px; border-radius:50px;"
            >
              <img :src="userImg[i]" style="width:100%; height:100%;" />
            </div>
          </v-col>
          <v-col>
            <div v-if="isInput[i] === 0">
              <v-row>
                <v-col cols="8" style="margin-top:2vh" class="sansfont">
                  <span class="sansfont"
                    >{{ users[i] }} : {{ re.content }}</span
                  >
                </v-col>
                <v-col cols="4">
                  <p
                    v-if="user.nickname === users[i]"
                    style="display:inline-block;"
                    class="sansfont"
                  >
                    <button @click="click(i)">
                      수정
                    </button>
                    <button
                      @click="deleteComment(i, re.cmid)"
                      style="margin-left:1vw"
                      class="sansfont"
                    >
                      삭제
                    </button>
                  </p>
                </v-col>
              </v-row>
            </div>
            <div v-else>
              <v-row>
                <v-col cols="10">
                  <span class="sansfont">
                    {{ users[i] }} :
                    <input v-model="re.content" />
                  </span>
                </v-col>
                <v-col cols="2">
                  <p
                    v-if="user.nickname === users[i]"
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
                </v-col>
              </v-row>
            </div>
          </v-col>
        </v-row>
      </div>
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
      user: {
        nickname: "",
        image: ""
      },
      comment: "",
      isInput: [],
      userImg: [],
      updatedComment: "",
      pageNm: 1,
      pageNms: [],
      window: {
        width: 0,
        height: 0
      }
    };
  },
  mounted() {
    window.addEventListener("resize", this.handleResize);
    this.handleResize();
    this.$store
      .dispatch(Constant.GET_USERINFO, { username: this.$store.state.username })
      .then(() => {
        this.user = { ...this.$store.state.user };
        if (this.user.image === null)
          this.user.image = require(`../../../images/default.png`);
      });
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
            this.userImg = { ...this.$store.state.userImg };
            this.userImg = Object.values(this.userImg);
            for (let i = 0; i < this.reply.length; ++i) {
              this.isInput.push(0);
              if (this.userImg[i] === null) {
                this.userImg[i] = require(`../../../images/default.png`);
              }
            }
            let arr = [];
            console.log(this.reply, this.users, this.isInput);
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
    this.user.nickname = this.$store.state.username;
    if (this.user.nickname === undefined) {
      this.user.nickname = "h";
    }
  },
  destroyed() {
    window.removeEventListener("resize", this.handleResize);
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
        return Object.values(this.$store.state.reply);
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
    fontsize() {
      if (this.window.width > 425) {
        return "3vmin";
      } else {
        return "4vmin";
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
    handleResize() {
      this.window.width = window.innerWidth;
      this.window.height = window.innerHeight;
      // console.log(typeof this.window.height);
      this.window.height = this.window.height + "px";
      this.window.height = "calc(" + this.window.height + " - 4rem)";
    },
    submitComment() {
      this.$store.dispatch(Constant.ADD_REPLY, {
        cid: this.cocktail.cid,
        username: this.$store.state.username,
        comment: this.comment
      });
      this.isInput.push(0);
      this.users.splice(0, 0, this.user.nickname);
      this.userImg.splice(0, 0, this.user.image);
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
@font-face {
  font-family: "neontubes";
  src: url("https://bitbucket.org/kennethjensen/webfonts/raw/fc13c1cb430a0e9462da56fe3f421ff7af72db71/neontubes/neontubes-webfont.eot");
  src: url("https://bitbucket.org/kennethjensen/webfonts/raw/fc13c1cb430a0e9462da56fe3f421ff7af72db71/neontubes/neontubes-webfont.eot?#iefix")
      format("embedded-opentype"),
    url("https://bitbucket.org/kennethjensen/webfonts/raw/fc13c1cb430a0e9462da56fe3f421ff7af72db71/neontubes/neontubes-webfont.woff2")
      format("woff2"),
    url("https://bitbucket.org/kennethjensen/webfonts/raw/fc13c1cb430a0e9462da56fe3f421ff7af72db71/neontubes/neontubes-webfont.woff")
      format("woff"),
    url("https://bitbucket.org/kennethjensen/webfonts/raw/fc13c1cb430a0e9462da56fe3f421ff7af72db71/neontubes/neontubes-webfont.ttf")
      format("truetype");
  font-weight: normal;
  font-style: normal;
}
@import url("https://fonts.googleapis.com/css?family=Jua&display=swap");

body {
  background-color: black !important;
  z-index: -1;
}
.cocktailheader {
  background-image: url("https://www.dropbox.com/s/2ct0i6kc61vp0bh/wall.jpg?raw=1");
  background-size: 100%;
  height: 38vh;
  background-position-y: 30%;
  color: white;
  z-index: 200;
  text-align: center;
}
.cocktailtitle {
  font-family: "neontubes";
  text-align: center;
  margin-top: 20vmin;
  font-size: 11vmin;
  font-weight: 500;
}

/* Button: Neon */
.button.neon,
.neon-box,
button.neon,
input[type="reset"].neon,
input[type="submit"].neon,
input[type="text"].neon {
  font-weight: 400;
  background: transparent !important;
  font-family: "GyeonggiBatang";
  border: 3px solid lighten(hsl(308, 100%, 50%), 51%);
  color: #ffffff;
  padding: 2%;
  text-shadow: hsl(0, 100%, 100%) 0px 0px 0.15em,
    hsl(0, 100%, 100%) 0px 0px 0.3em, hsl(0, 100%, 100%) 0px 0px 0.45em,
    hsl(308, 100%, 50%) 0px 0px 0.3em, hsl(308, 100%, 50%) 0px 0px 0.52em,
    hsl(308, 100%, 50%) 0px 0px 0.6em, hsl(308, 100%, 50%) 0px 0px 0.75em;
  box-shadow: hsl(0, 100%, 100%) 0px 0px 0.075em,
    hsl(0, 100%, 100%) 0px 0px 0.15em, hsl(0, 100%, 100%) 0px 0px 0.22em,
    hsl(308, 100%, 50%) 0px 0px 0.3em, hsl(308, 100%, 50%) 0px 0px 0.52em,
    hsl(308, 100%, 50%) 0px 0px 0.6em, hsl(308, 100%, 50%) 0px 0px 0.75em,
    inset hsl(308, 100%, 50%) 0 0 0.6em;
}
.button.neon:hover,
button.neon:hover,
input[type="text"].neon,
input[type="reset"].neon:hover,
input[type="submit"].neon:hover {
  text-shadow: hsl(0, 100%, 100%) 0px 0px 0.3em,
    hsl(0, 100%, 100%) 0px 0px 0.6em, hsl(0, 100%, 100%) 0px 0px 0.9em,
    hsl(308, 100%, 50%) 0px 0px 0.6em, hsl(308, 100%, 50%) 0px 0px 1.04em,
    hsl(308, 100%, 50%) 0px 0px 1.2em, hsl(308, 100%, 50%) 0px 0px 1.5em;
  box-shadow: hsl(0, 100%, 100%) 0px 0px 0.15em,
    hsl(0, 100%, 100%) 0px 0px 0.3em, hsl(0, 100%, 100%) 0px 0px 0.44em,
    hsl(308, 100%, 50%) 0px 0px 0.6em, hsl(308, 100%, 50%) 0px 0px 1.04em,
    hsl(308, 100%, 50%) 0px 0px 1.2em, hsl(308, 100%, 50%) 0px 0px 1.5em,
    inset hsl(308, 100%, 50%) 0 0 1.2em;
}
.neon::placeholder {
  font-family: "GyeonggiBatang";
  font-size: 1.3rem;
  color: #c6e2ff;
  text-shadow: hsl(0, 100%, 100%) 0px 0px 0.3em,
    hsl(0, 100%, 100%) 0px 0px 0.6em, hsl(0, 100%, 100%) 0px 0px 0.9em,
    hsl(308, 100%, 50%) 0px 0px 0.6em, hsl(308, 100%, 50%) 0px 0px 1.04em,
    hsl(308, 100%, 50%) 0px 0px 1.2em, hsl(308, 100%, 50%) 0px 0px 1.5em;
}

.fullfontsize {
  font-size: 3vmin;
}
.mobilefontsize {
  font-size: 4vmin;
}
.reply_input {
  border-bottom: 0.5px solid hsl(308, 100%, 50%);
  box-shadow: 0px 15px 10px -15px hsl(308, 100%, 50%);
  width: 80%;
  color: #ffffff;
  text-shadow: hsl(0, 100%, 100%) 0px 0px 0.15em,
    hsl(0, 100%, 100%) 0px 0px 0.3em, hsl(0, 100%, 100%) 0px 0px 0.45em,
    hsl(308, 100%, 50%) 0px 0px 0.3em, hsl(308, 100%, 50%) 0px 0px 0.52em,
    hsl(308, 100%, 50%) 0px 0px 0.6em, hsl(308, 100%, 50%) 0px 0px 0.75em;
}
.reply_btn {
  width: 20%;
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
  animation: neon-box 0.08s infinite alternate;
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
