<template>
  <div>
    <div id="user-scrap-header">
      <h1 id="user-scrap-title">{{ user.nickname }} Profile</h1>
    </div>
    <div
      class="backgroundcolor"
      v-show="window.width >= 768"
      style="padding-bottom:10%"
    >
      <v-row no-gutters style="margin-bottom:10%">
        <v-col class="col-1"></v-col>
        <div
          class="col-12 col-md-6 col-lg-4 col-sm-6"
          style="text-align:center;"
        >
          <div
            style="position:absoulte; margin-top:4%; display:inline-block; overflow: hidden;  height:300px; width: 300px; border-radius:300px;"
          >
            <img
              :src="user.image"
              style="position:absoulte; width:100%; height:100%;"
            />
            <!--file upload component-->
          </div>
          <FileUploadNotPriview style="margin: 0px" />
        </div>

        <v-col class="col-1"></v-col>

        <v-col>
          <div style="display:inline-block; width: 70%">
            <div
              class="sansfont"
              style="margin-top:10%; font-size:300%; display:inline-block;"
            >
              {{ user.nickname }}
            </div>
            <span
              class="sansfont"
              style="margin-left:3%;font-size:100%;"
              v-show="window.width > 1035"
              >(ID : {{ user.email }})</span
            >
            <span
              class="sansfont"
              style="margin-left:3%;font-size:100%;display:block;margin-top:5%;"
              v-show="window.width < 1035"
              >(ID : {{ user.email }})</span
            >
          </div>
          <div
            class="text-center"
            style="margin-top:13%;display:inline-block;float:right"
          >
            <v-btn
              v-show="updateIntro"
              class="ma-2"
              tile
              outlined
              color="success"
              @click="updatedIntro()"
            >
              <v-icon left>mdi-pencil</v-icon>Submit
            </v-btn>
            <v-btn
              v-show="!updateIntro"
              class="ma-2"
              tile
              outlined
              color="success"
              @click="updatedIntro()"
            >
              <v-icon left>mdi-pencil</v-icon>Edit
            </v-btn>
          </div>
          <div
            class="sansfont"
            style=" margin-top:13%;font-size:200%; text-align:center; font-weight:bolder;"
          >
            자기 소개
          </div>
          <div
            v-show="!updateIntro"
            style="margin-top:5%; display: table; width: 100%; height: 50% ;border: 0.5px solid #DCDCDC;"
          >
            <div
              class="sansfont"
              style="display: table-cell; font-size:130%;
    vertical-align: middle;"
            >
              <div class="sansfont" style="margin-left:3%;margin-right:3%;">
                {{ user.intro }}
              </div>
            </div>
          </div>
          <div
            v-show="updateIntro"
            style="margin-top:5%; width: 100%; height: 50% ;border: 0.5px solid #DCDCDC;"
          >
            <textarea
              v-model="user.intro"
              class="sansfont"
              style="font-size:130%; width:98%; height:96%; margin-top: 0.5%; margin-left: 1%;"
            ></textarea>
          </div>
        </v-col>
      </v-row>
      <v-row v-show="cocktailList.length > 0">
        <v-col>
          <h1
            class="sansfont"
            style=" margin-bottom:5%;font-size:200%; text-align:center; font-weight:bolder;"
          >
            좋아하는 칵테일
          </h1>
          <carousel-3d
            :count="cocktailList.length"
            style="opacity:100 !important; height:600px !important;"
          >
            <slide
              v-for="(slide, i) in cocktailList"
              :index="i"
              :key="i"
              style="opacity:100 !important; visibility:visible;height:500px !important;background-color:#ffffff;"
            >
              <v-card style="height:400px">
                <v-img :src="slide.image" alt="ll" style="height:100%;"></v-img>
                <h1
                  class="sansfont"
                  style="margin-left:30px; margin-top:10px;font-weight:bolder;"
                >
                  {{ slide.cname }}
                </h1>
                <div
                  style="margin-left:30px;margin-top:10px;display:inline-block"
                >
                  <i class="fas fa-lg fa-heart"></i>
                  {{ getLikesByCocktail[i] }}
                </div>
                <button
                  @click="goToDetail(slide.cid)"
                  class="sansfont"
                  style="color:blue;margin-left:230px"
                >
                  ...더보기
                </button>
              </v-card>
            </slide>
          </carousel-3d>
        </v-col>
      </v-row>

      <div v-show="boardArray.length > 0">
        <div
          class="sansfont"
          style="font-size:200%; text-align:center; font-weight:bolder;"
        >
          {{ user.nickname }}님이 쓴 글
        </div>
        <div v-for="(board, i) in boardArray" :key="i">
          {{ board.title }}
        </div>
      </div>
    </div>
    <div
      style="background-color:#FFF;padding-bottom:40%"
      v-show="window.width < 768"
    >
      <v-row no-gutters style="margin-bottom:30%">
        <v-col class="col-1"></v-col>
        <div
          class="col-12 col-md-6 col-lg-4 col-sm-6"
          style="text-align:center;"
        >
          <div
            style="position:absoulte; margin-top:4%; display:inline-block; overflow: hidden;  height:300px; width: 300px; border-radius:300px;"
          >
            <img
              :src="user.image"
              style="position:absoulte; width:100%; height:100%;"
            />
            <!--file upload component-->
          </div>
          <FileUploadNotPriview style="margin: 0px" />
        </div>
        <v-col>
          <div style="display:inline-block; width: 70%">
            <div
              class="sansfont"
              style="margin-top:10%; margin-left:5%; margin-right:2%;font-size:250%; font-weight:300;display:inline-block;"
            >
              {{ user.nickname }}
            </div>
            <span
              class="sansfont"
              style="margin-left:3%;font-size:100%;display:block;margin-top:5%;"
              v-show="window.width < 1035"
              >(ID : {{ user.email }})</span
            >
          </div>
          <div
            class="text-center"
            style=" margin-top:10%;display:inline-block;float:right;margin-right:2%;"
          >
            <v-btn
              v-show="updateIntro"
              class="ma-2"
              tile
              outlined
              color="success"
              @click="updatedIntro()"
            >
              <v-icon left>mdi-pencil</v-icon>Submit
            </v-btn>
            <v-btn
              v-show="!updateIntro"
              class="ma-2"
              tile
              outlined
              color="success"
              @click="updatedIntro()"
            >
              <v-icon left>mdi-pencil</v-icon>Edit
            </v-btn>
          </div>
          <div
            class="sansfont"
            style=" margin-top:15%;font-size:200%; text-align:center; font-weight:bolder;"
          >
            자기 소개
          </div>
          <div
            v-show="!updateIntro"
            style="padding:2%;margin-top:5%; display: table; width: 100%; height: 50% ;border: 0.5px solid #DCDCDC;"
          >
            <div
              class="sansfont"
              style="display: table-cell; font-size:130%;
    vertical-align: middle;"
            >
              <div class="sansfont">{{ user.intro }}</div>
            </div>
          </div>
          <div
            v-show="updateIntro"
            style="margin-top:5%; width: 100%; height: 50% ;border: 0.5px solid #DCDCDC;"
          >
            <textarea
              v-model="user.intro"
              class="sansfont"
              style="font-size:130%; width:98%; height:96%; margin-top: 0.5%;"
            ></textarea>
          </div>
        </v-col>
      </v-row>
      <div v-show="cocktailList.length > 0">
        <h1
          class="sansfont"
          style=" font-size:200%; text-align:center; font-weight:bolder;"
        >
          좋아하는 칵테일
        </h1>
        <carousel-3d
          :count="cocktailList.length"
          :controls-visible="true"
          :controls-prev-html="'&#10092;'"
          :controls-next-html="'&#10093;'"
          :controls-width="20"
          :controls-height="0"
          style="opacity:100 !important; height:600px !important;"
        >
          <slide
            v-for="(slide, i) in cocktailList"
            :index="i"
            :key="i"
            style="opacity:100 !important; visibility:visible;height:500px !important;background-color:#ffffff;"
          >
            <v-card style="height:400px">
              <v-img :src="slide.image" alt="ll" style="height:100%;"></v-img>
              <h1
                class="sansfont"
                style="margin-left:30px; margin-top:10px;font-weight:bolder;"
              >
                {{ slide.cname }}
              </h1>
              <div
                style="margin-left:30px;margin-top:10px;display:inline-block"
              >
                <i class="fas fa-lg fa-heart"></i>
                {{ getLikesByCocktail[i] }}
              </div>
              <button
                @click="goToDetail(slide.cid)"
                class="sansfont"
                style="color:blue;margin-left:230px"
              >
                ...더보기
              </button>
            </v-card>
          </slide>
        </carousel-3d>
        <div style="text-align:center" v-show="boardArray.length > 0">
          <h1
            class="sansfont"
            style=" font-size:200%; text-align:center; font-weight:bolder;"
          >
            {{ user.nickname }}님이 쓴 글
          </h1>
          <div v-for="(board, i) in boardArray" :key="i">
            {{ board.title }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Constant from "../../Constant";
import { Carousel3d, Slide } from "vue-carousel-3d";
import FileUpload from "@/components/FileUpload";
import http from "../../http-common";
import FileUploadNotPriview from "@/components/FileUploadNotPriview";

export default {
  data: () => {
    return {
      user: {
        email: "",
        nickname: "",
        image: "",
        intro: ""
      },
      updateIntro: false,
      cocktailList: [],
      count: [],
      getLikesByCocktail: [],
      pageNms: [],
      pageNm: 1,
      boardArray: [],
      boardRecipeArray: [],
      prevBt: "<",
      nextBt: ">",
      fistBt: "<<",
      lastBt: ">>",
      hey: "",
      window: {
        width: 0,
        height: 0
      },
      page: {
        board: 0,
        boardrecipe: 0
      }
    };
  },
  mounted() {
    window.addEventListener("resize", this.handleResize);
    this.handleResize();
    this.user.nickname = this.$route.params.username;
    this.$store
      .dispatch(Constant.GET_USERINFO, { username: this.user.nickname })
      .then(() => {
        this.user = {
          ...this.$store.state.user
        };
        if (this.user.image === null) {
           this.user.image = require(`../../assets/images/profile_default.png`);
        }else {
          //DB image column 경로
          //this.user.image = require("/home/ubuntu/image/"+this.user.image);
          this.user.image = require(this.user.image);
          console.log(this.user.image);
        }
      });
    this.$store
      .dispatch(Constant.GET_COCKTAILLIKE, { username: this.user.nickname })
      .then(() => {
        this.cocktailList = {
          ...this.$store.state.cocktailList
        };
        this.cocktailList = Object.values(this.cocktailList);
        this.cocktailList.forEach(element => {
          if (element.image != "") {
            element.image = require(`../../../../images/${element.cid}.jpg`);
          } else {
            element.image = require(`../../../../images/default.png`);
          }

          this.$store
            .dispatch(Constant.GET_LIKEBYCOCKTAIL, {
              cid: element.cid
            })
            .then(() => {
              this.getLikesByCocktail.push(this.$store.state.likebycocktail);
            });
        });
        http
          .get("/board/user", {
            params: {
              username: this.$route.params.username
            }
          })
          .then(res => {
            this.boardArray = res.data.boards.content;
            this.page.board = res.data.boards.totalPages;
            console.log(this.boardArray);
          });
        http
          .get("/boardrecipe/user", {
            params: {
              username: this.$route.params.username
            }
          })
          .then(res => {
            this.boardRecipeArray = res.data.object.content;
            this.page.boardrecipe = res.data.object.totalPages;
            console.log(this.boardRecipeArray);
          });
      });
  },
  destroyed() {
    window.removeEventListener("resize", this.handleResize);
  },
  components: {
    FileUploadNotPriview,
    Carousel3d,
    Slide
  },
  methods: {
    goToDetail(sendCid) {
      this.$router.push("/cocktail/detail/" + sendCid);
    },
    updatedIntro() {
      if (!this.updateIntro) {
        this.updateIntro = !this.updateIntro;
      } else {
        this.$store
          .dispatch(Constant.MODIFY_USERINTRO, {
            username: this.user.nickname,
            intro: this.user.intro
          })
          .then(() => {
            this.user.intro = { ...this.$store.state.intro };
          });
        this.updateIntro = !this.updateIntro;
      }
    },
    handleResize() {
      this.window.width = window.innerWidth;
      this.window.height = window.innerHeight;
      // console.log(typeof this.window.height);
      this.window.height = this.window.height + "px";
      this.window.height = "calc(" + this.window.height + " - 4rem)";
    }
  }
};
</script>
<style scoped>
html,
body {
  height: 100%;
  overflow: hidden;
}

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
@import url("https://fonts.googleapis.com/css?family=Jua&display=swap");
carousel-3d {
  padding: 20px;
}
@font-face {
  font-family: "국립박물관문화재단클래식B";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_twelve@1.0/국립박물관문화재단클래식B.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
@import url("https://fonts.googleapis.com/css?family=Indie+Flower&display=swap");
#scroll-right {
  display: inline-block;
  position: relative;
  text-align: right;
  margin-left: 45vmax;
  margin-right: 3vmax;
  top: 43%;
  bottom: 50%;
  font-size: 10px;
  z-index: 1;
  color: #797979;
  font-family: "Indie Flower", cursive;
}
#scroll-left {
  display: inline-block;
  position: relative;
  text-align: left;
  margin-left: 3vmax;
  margin-right: 45vmax;
  top: 50%;
  bottom: 50%;
  font-size: 10px;
  z-index: 1;
  color: #797979;
  font-family: "Indie Flower", cursive;
}
#user-scrap-header {
  background: linear-gradient(rgba(0, 0, 0, 0.5)),
    url("../../assets/images/image6.jpg") no-repeat;
  background-size: 100%;
  height: 50vh;
  background-position-y: 30%;
  color: white;
}
#user-scrap-title {
  margin: 0 0 0 15vw;
  display: inline;
  position: relative;
  float: left;
  top: 30vmin;
  font-size: 11vmin;
  font-family: "BBTreeGB";
}

.backgroundcolor {
  background-color: #ffffff;
  padding-top: 8%;
  padding-left: 10%;
  padding-right: 10%;
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
.carousel-3d-slider {
  height: 600px !important;
}

@media (max-width: 700px) {
  #user-scrap-header {
    height: 35vh;
    background-size: 200vw;
    background-position-x: 50%;
  }
  #user-scrap-title {
    margin-top: 4vmin;
    font-size: 8vmin;
  }
}
</style>
