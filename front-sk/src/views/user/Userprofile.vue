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
            style="position:absoulte; margin-top:4%; margin-bottom: 8%; display:inline-block; overflow: hidden;  height:300px; width: 300px; border-radius:300px;"
          >
            <img
              :src="user.image"
              style="position:absoulte; width:100%; height:100%;"
            />
            <!--file upload component-->
          </div>
          <label id="largeFile" for="file">
            <input type="file" id="file" />
          </label>
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
          style="font-size:200%; text-align:center; font-weight:bolder; margin-bottom: 15px;"
        >
          {{ user.nickname }}님이 쓴 글
        </div>
        <div
          class="table-font"
          style="font-size: 15px; margin-left: 10px; margin-bottom: 10px;"
        >
          <span @click="selectTable = boardArray" style="cursor: pointer;"
            >자유 게시판</span
          >
          |
          <span @click="selectTable = boardRecipeArray" style="cursor: pointer;"
            >레시피 공유</span
          >
        </div>
        <!-- toggling table -->
        <v-simple-table>
          <template>
            <thead>
              <tr id="table-header">
                <th id="table-header-title">제목</th>
                <th id="table-header-writer">글쓴이</th>
                <th id="table-header-date">날짜</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(board, i) in selectTable" :key="i">
                <td
                  v-if="selectTable === boardArray"
                  class="table-content-title"
                  v-html="board.title"
                  @click="detail_bid(board.bid)"
                  style="cursor: pointer;"
                ></td>
                <td
                  v-if="selectTable === boardRecipeArray"
                  class="table-content-title"
                  v-html="board.title"
                  @click="detail_rid(board.rid)"
                  style="cursor: pointer;"
                ></td>
                <td class="table-content-writer">
                  {{ board.user.nickname }}
                </td>
                <td class="table-content-date" v-html="board.regdate"></td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
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
          <label id="largeFile" for="file">
            <input type="file" id="file" />
          </label>
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
            style=" font-size:200%; text-align:center; font-weight:bolder; margin-bottom: 10px;"
          >
            {{ user.nickname }}님이 쓴 글
          </h1>
          <div
            class="table-font"
            style="font-size: 15px; margin-left: 10px; margin-bottom: 10px;"
          >
            <span @click="selectTable = boardArray" style="cursor: pointer;"
              >자유 게시판</span
            >
            |
            <span
              @click="selectTable = boardRecipeArray"
              style="cursor: pointer;"
              >레시피 공유</span
            >
          </div>
          <v-simple-table class="table-font" style="padding: 0 10px;">
            <template>
              <thead>
                <tr id="table-header">
                  <th id="table-header-title">제목</th>
                  <th id="table-header-writer">글쓴이</th>
                  <th id="table-header-date">날짜</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(board, i) in selectTable" :key="i">
                  <td
                    v-if="selectTable === boardArray"
                    class="table-content-title"
                    v-html="board.title"
                    @click="detail_bid(board.bid)"
                    style="cursor: pointer;"
                  ></td>
                  <td
                    v-if="selectTable === boardRecipeArray"
                    class="table-content-title"
                    v-html="board.title"
                    @click="detail_rid(board.rid)"
                    style="cursor: pointer;"
                  ></td>
                  <td class="table-content-writer">
                    <router-link
                      :to="{
                        name: 'UserProfile',
                        params: { username: board.user.nickname }
                      }"
                      style="color: black; cursor: pointer;"
                      >{{ board.user.nickname }}</router-link
                    >
                  </td>
                  <td class="table-content-date" v-html="board.regdate"></td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Constant from "../../Constant";
import { Carousel3d, Slide } from "vue-carousel-3d";
import http from "../../http-common";

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
      selectTable: [],
      prevBt: "<",
      nextBt: ">",
      fistBt: "<<",
      lastBt: ">>",
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
    var file = document.getElementById("file");
    var self = this;
    file.onchange = function(event) {
      var target = event.currentTarget;
      var xmlHttpRequest = new XMLHttpRequest();
      xmlHttpRequest.open("POST", "https://api.imgur.com/3/image/", true);
      xmlHttpRequest.setRequestHeader(
        "Authorization",
        "Client-ID 5d0f43f26473d77"
      );
      xmlHttpRequest.onreadystatechange = function() {
        if (xmlHttpRequest.readyState == 4) {
          if (xmlHttpRequest.status == 200) {
            var result = JSON.parse(xmlHttpRequest.responseText);
            // this.user.image = result.data.link;
            http
              .put("/userprofileimage/", null, {
                params: {
                  image: result.data.link,
                  username: self.$store.state.username
                }
              })
              .then(res => {
                console.log(res.data);
                self.user = res.data.object;
              });
            console.log(result);
          } else {
            alert("업로드 실패");
            // this.user.image = "http://dy.gnch.or.kr/img/no-image.jpg";
          }
        }
      };
      xmlHttpRequest.send(target.files[0]);
      // this.user.image = "https://nrm.dfg.ca.gov/images/image-loader.gif";
    };
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
        } else {
          //DB image column 경로
          //this.user.image = require("/home/ubuntu/image/"+this.user.image);
          //this.user.image = require(this.user.image); //ciritical dependncy error
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
          });
      });
  },
  destroyed() {
    window.removeEventListener("resize", this.handleResize);
  },
  components: {
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
    },
    detail_rid(sendrid) {
      this.$router.push({
        name: "BoardRecipeDetail",
        params: {
          rid: sendrid
        }
      });
    },
    detail_bid(sendbid) {
      this.$router.push({
        name: "BoardDetail",
        params: {
          bid: sendbid
        }
      });
    }
  },
  watch: {
    boardArray() {
      this.selectTable = this.boardArray;
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
td,
tr,
th,
span {
  font-family: "GyeonggiBatang";
}
.carousel-3d-slider {
  height: 600px !important;
}

label#largeFile:after {
  width: 10%;
  max-width: 200px;
  content: "Upload your Image";
  left: 0;
  right: 0;
  margin: 50px auto;
  text-align: center;
  padding: 20px 0px 20px 0px;
  border-radius: 10px;
  border: 4px dashed #ccc;
  color: #ccc;
  font-family: "Helvetica Neue", Helvetica, Arial;
  font-size: 30px;
}

label#largeFile:hover:after {
  background: #ccc;
  color: #fff;
  cursor: pointer;
}

label#largeFile input#file {
  width: 0px;
  height: 0px;
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
tr {
  text-align: center;
}
td {
  padding: 20px !important;
}
th {
  font-size: 15px !important;
  color: white !important;
}
#table-header {
  background-color: #000;
}
#table-header-no,
.table-content-no {
  width: 10%;
}
#table-header-title,
.table-content-title {
  width: 50%;
}
#table-header-writer,
.table-content-writer {
  width: 20%;
}
#table-header-date,
.table-content-date {
  width: 20%;
}
@media (max-width: 960px) {
  #table-header-no,
  #table-header-date,
  .table-content-no,
  .table-content-date {
    display: none;
  }
  #table-header-writer,
  .table-content-writer {
    width: 30%;
  }
}
</style>
