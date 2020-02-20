<template>
  <div class="test">
    <div class="cocktailheader">
      <div v-show="window.width >= 435">
        <h1
          class="cocktailtitle sign"
          style="z-index:200;margin-right;auto;margin-left;auto;display:inline-block;z-index:200;font-family: 'neontubes';"
        >COCKTAIL</h1>
      </div>
      <div v-show="window.width < 435">
        <h1
          class="cocktailtitle sign"
          style="z-index:200;margin-top:20vmax;margin-right;auto;margin-left;auto;display:inline-block;z-index:200;font-family: 'neontubes';"
        >COCKTAIL</h1>
      </div>
    </div>
    <img
      src="../assets/images/cocktail_list_copy02.png"
      class="rightImage titlefont"
      alt="cocktail_list_background"
    />
    <button
      class="neon-button"
      @click="openFilterBox = true"
      style="width: 3%"
      v-show="window.width >= 1024"
    >
      F
      <br />I
      <br />L
      <br />T
      <br />E
      <br />R
      <br />>
    </button>
    <button
      class="neon-button"
      @click="openFilterBox = true"
      style="width: 30px;"
      v-show="window.width < 1024"
    >
      F
      <br />I
      <br />L
      <br />T
      <br />E
      <br />R
      <br />>
    </button>
    <div v-show="openFilterBox" class="filter-box">
      <div class="hcontainer" style="margin-bottom:10%">
        <p
          type="button"
          class="close sign"
          style="font-size:4vmin;margin-top:5%;position:absolute;right:10%;"
          @click="openFilterBox = !openFilterBox"
        >&times;</p>
        <div
          class="sign"
          style="font-size:3vmax;padding-top:7%;margin-bottom:3%;text-align:center;  font-family: 'neontubes';"
        >Material</div>
        <div
          style="text-align:center;display:inline-block;width:20%;margin-bottom:5%"
          v-for="filter in filters"
          :key="filter.name"
        >
          <router-link
            :to="{
              name: 'CocktailList',
              query: {
                pageNm: 1,
                filtered: filter.name,
                searchedFiltered: $route.query.searchedFiltered
              }
            }"
          >
            <v-img :src="filter.image" :alt="filter.name" style="width:100%;" />
            <div class="x-sign" style="font-size:1.5vmax;text-align:center;">{{ filter.title }}</div>
          </router-link>
        </div>
      </div>
    </div>
    <div style="text-align:center; color:#ffffff" v-show="window.width < 435">
      <input
        type="text"
        @input="autocomplete"
        v-model="searchData"
        @keypress.enter="search(1)"
        class="search-input"
        placeholder="Search"
        style="width:50%; font-size:0.8rem"
      />
      <button @click="search(1)" style="margin-top: 1%; margin-left:1%">
        <i class="fas fa-2x fa-search"></i>
      </button>
    </div>
    <div class="hcontainer">
      <div
        class="sign"
        style="z-index:200;font-size:400%;margin-top:10%;margin-bottom:10%;text-align:center;"
      ></div>
      <v-row>
        <v-col
          v-for="(cocktail, i) in cocktailArray"
          v-bind:key="cocktail.cid"
          md="3"
          sm="6"
          xs="12"
        >
          <v-card v-on:click="goToDetail(cocktail.cid)" style="height:100%">
            <v-img
              :src="cocktail.image"
              class="white--text align-end"
              gradient="to bottom, rgba(0, 0,0,.1), rgba(0,0,0,.5)"
              :alt="cocktail.cname"
              style="height:87%;margin-top:0"
            ></v-img>
            <h1
              class="sansfont"
              style="margin-top:10px;display:inline-block;width:72%;overflow:auto;height:7%;font-size:120%;margin-left:15px;font-weight:bolder;"
            >{{ cocktail.cname }}</h1>
            <div style="margin-top:12px;margin-right:15px;float:right;display:inline-block;">
              <i class="fas fa-lg fa-heart"></i>
              {{ getLikesByCocktail[i] }}
            </div>
          </v-card>
        </v-col>
      </v-row>
      <div style="text-align:center; color:#ffffff" v-show="window.width >= 435">
        <input
          type="text"
          @input="autocomplete"
          v-model="searchData"
          @keypress.enter="search(1)"
          class="search-input"
          placeholder="Search"
        />
        <button @click="search(1)" style="margin-top: 1%; margin-left:1%">
          <i class="fas fa-2x fa-search"></i>
        </button>
      </div>
      <div v-if="searchedData.length > 0" style="text-align:center">
        <div class="autocomplete">
          <div v-for="(item, i) in searchedData" :key="i">
            <button @click="searchDetailPage(item)">
              <div
                v-text="item"
                style="font-family: 'GyeonggiBatang';text-align:left; margin-top:1%"
              ></div>
            </button>
          </div>
        </div>
      </div>
      <div style="text-align:center; margin-top:3%; margin-bottom:3%">
        <button
          v-if="pageNm > 5"
          v-on:click="search(1)"
          style="margin-right:10px;margin-top:100px;color:#ffffff"
          class="paging-size"
        >{{ fistBt }}</button>
        <button
          v-if="pageNm > 5"
          v-on:click="search(min - 5 < 0 ? 1 : min - 5)"
          style="margin-right:10px;color:#ffffff"
          class="paging-size"
        >{{ prevBt }}</button>
        <button v-for="pageNm in pageNms" :key="pageNm" @click="search(pageNm)">
          <span style="margin-right:10px;color:#ffffff;" class="paging-size">{{ pageNm }}</span>
        </button>
        <button
          v-if="min + 5 <= totalPages"
          class="paging-size"
          v-on:click="search(min + 5)"
          style="margin-right:10px;color:#ffffff"
        >{{ nextBt }}</button>
        <button
          class="paging-size"
          v-if="min + 5 <= totalPages"
          v-on:click="search(totalPages)"
          style="color:#ffffff;"
        >{{ lastBt }}</button>
      </div>
    </div>
  </div>
</template>

<script>
import http from "../http-common";
import Constant from "../Constant";
export default {
  data: () => {
    return {
      searchData: "",
      cocktailArray: [],
      cocktailNameArray: [],
      prevBt: "<",
      nextBt: ">",
      fistBt: "<<",
      lastBt: ">>",
      searchedData: [],
      pageNms: [],
      totalPages: 0,

      filter: {
        filtered: "",
        searchData: ""
      },
      openFilterBox: false,
      clicked: false,
      getLikesByCocktail: [],
      filters: [
        {
          name: "all",
          image: require("../assets/images/all.png"),
          title: "ALL"
        },
        {
          name: "레몬",
          image: require("../assets/images/lemon.png"),
          title: "LEMON"
        },
        {
          name: "오렌지",
          image: require("../assets/images/orange.png"),
          title: "ORANGE"
        },
        {
          name: "럼",
          image: require("../assets/images/rum.png"),
          title: "RUM"
        },
        {
          name: "위스키",
          image: require("../assets/images/whiskey.png"),
          title: "WHISKEY"
        },
        {
          name: "진",
          image: require("../assets/images/gin.png"),
          title: "GIN"
        },
        {
          name: "와인",
          image: require("../assets/images/wine.png"),
          title: "WINE"
        },
        {
          name: "보드카",
          image: require("../assets/images/vodka.png"),
          title: "VODKA"
        },
        {
          name: "테킬라",
          image: require("../assets/images/tequila.png"),
          title: "TEQUILA"
        },
        {
          name: "맥주",
          image: require("../assets/images/beer-removebg-preview.png"),
          title: "BEER"
        }
      ],
      min: 1,
      window: {
        width: 0,
        height: 0
      }
    };
  },
  mounted() {
    window.addEventListener("resize", this.handleResize);
    this.handleResize();
    this.pageNm = this.$route.query.pageNm;
    this.filtered = this.$route.query.filtered;
    this.getCocktailName();
    this.paginate(this.$route.query.pageNm);
  },
  computed: {
    pageNm: {
      set: function(val) {
        if (val < 0) val = 1;
        let arr = [];
        this.min = parseInt((val - 1) / 5) * 5 + 1;
        for (let index = 0; index < 5; index++) {
          if (Number(this.min + index) > this.$store.state.totalPages) break;
          arr.push(Number(this.min + index));
        }
        this.pageNms = arr;
      },
      get: function() {
        return this.$route.query.pageNm;
      }
    },
    filtered: {
      set: function(val) {
        this.filter.filtered = val;
      },
      get: function() {
        return this.$route.query.filtered;
      }
    }
  },
  destroyed() {
    window.removeEventListener("resize", this.handleResize);
  },
  methods: {
    paginate(pageNm) {
      this.pageNm = pageNm;
      if (this.searchData === "") {
        if (this.$route.query.searchedFiltered === "") this.searchData = "h";
        else this.searchData = this.$route.query.searchedFiltered;
      }
      this.$store
        .dispatch(Constant.GET_COCKTAILLIST, {
          pageNm: pageNm - 1,
          filtered: this.filter.filtered,
          searchedFiltered: this.searchData
        })
        .then(() => {
          this.cocktailArray = { ...this.$store.state.cocktailList };
          this.totalPages = this.$store.state.totalPages;
          this.pageNm = pageNm;
          this.cocktailArray = Object.values(this.cocktailArray);
          // console.log(typeof this.cocktailArray, this.cocktailArray);
          this.cocktailArray.forEach(element => {
            this.$store
              .dispatch(Constant.GET_LIKEBYCOCKTAIL, {
                cid: element.cid
              })
              .then(() => {
                this.getLikesByCocktail.push(this.$store.state.likebycocktail);
                // console.log(this.getLikesByCocktail);
              });
          });
        });
      if (this.searchData === "h") {
        this.searchData = "";
      }
      return this.cocktailArray;
    },
    search(pageNm) {
      if (this.searchData == "")
        this.searchData = this.$route.query.searchedFiltered;
      this.$router.push({
        name: "CocktailList",
        query: {
          pageNm: pageNm,
          filtered: this.filter.filtered,
          searchedFiltered: this.searchData
        }
      });
    },
    getCocktailName() {
      http.get("/cocktail/name").then(res => {
        this.cocktailNameArray = res.data.object;
      });
    },
    autocomplete() {
      this.searchedData = [];
      if (this.searchData === "") {
        this.searchedData = [];
      } else {
        for (let i = 0; i < this.cocktailNameArray.length; ++i) {
          if (
            this.cocktailNameArray[i].indexOf(this.searchData) != -1 &&
            this.searchedData.indexOf(this.cocktailNameArray[i]) === -1
          ) {
            this.searchedData.push(this.cocktailNameArray[i]);
          }
        }
      }
    },
    goToDetail(sendCid) {
      this.$router.push("/cocktail/detail/" + sendCid);
    },
    searchDetailPage(item) {
      let id = this.cocktailNameArray.indexOf(item) + 1;
      this.$router.push({
        name: "CocktailDetail",
        params: {
          cid: id
        }
      });
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

.hcontainer {
  margin-left: 15%;
  margin-right: 15%;
}
.filter-box {
  font-family: "neontubes";
  font-size: 1rem;
  background-image: url("https://www.dropbox.com/s/2ct0i6kc61vp0bh/wall.jpg?raw=1");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-color: #141414;
  position: fixed;
  z-index: 500;
  width: 100% !important;
  top: 0px;
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
.autocomplete {
  font-family: "GyeonggiBatang";
  color: rgba(242, 30, 178, 0.78);
  background-color: #ffffff;
  display: inline-block;
  width: 30%;
  font-size: 1.3rem;
  text-align: left;
  margin-right: 3.6%;
  padding-left: 1.5%;
  padding-bottom: 0.7%;
  padding-top: 0.7%;
}
.sign {
  min-height: 100%;
  font-family: "Vibur", cursive;
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
  margin-top: 0%;
  float: left;
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
  margin-top: 0%;
  float: left;
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
.paging-size {
  font-size: 150%;
  font-family: "국립박물관문화재단클래식B";
}
.search-input {
  width: 30%;
  padding-top: 1%;
  padding-bottom: 1%;
  font-size: 1.3rem;
  font-family: "GyeonggiBatang";
  color: #c6e2ff;
  border-bottom: 2px solid #ffffff;
  /* border-radius: 5px; */
  background-color: transparent;
}
.search-input::placeholder {
  font-family: "GyeonggiBatang";
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
