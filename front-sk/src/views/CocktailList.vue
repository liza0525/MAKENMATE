<template>
  <div style="text-align:center;" class="test">
    <v-item-group active-class="primary">
      <v-container fluid ma-0 pa-0 style="width:65%;">
        <div>
          <h1>Material</h1>
        </div>
        <v-row>
          <v-col
            v-for="filter in filters"
            :key="filter.name"
            cols="12"
            md="2"
            v-on:click="clickFilter(filter.name)"
          >
            <img :src="filter.image" :alt="filter.name" style="height:120px;" />
            <div style="font-size:30px;color:#797979">{{ filter.name }}</div>
          </v-col>
        </v-row>
      </v-container>
    </v-item-group>

    <v-container fluid ma-0 pa-0 style="width:65%;">
      <v-row dense>
        <v-col v-for="cocktail in cocktailArray" v-bind:key="cocktail.cid" cols="3">
          <v-card v-on:click="goToDetail(cocktail.cid)">
            <v-img
              :src="cocktail.image"
              class="white--text align-end"
              gradient="to bottom, rgba(0, 0,0,.1), rgba(0,0,0,.5)"
              alt="cocktail"
              style="height:400px"
            >
              <v-card-title
                v-text="cocktail.cname"
                style="background-color:#1B2631;opacity:50%;color:white;"
              ></v-card-title>
            </v-img>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn icon>
                <v-icon>mdi-heart</v-icon>
              </v-btn>

              <v-btn icon>
                <v-icon>mdi-bookmark</v-icon>
              </v-btn>

              <v-btn icon>
                <v-icon>mdi-share-variant</v-icon>
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
    <div>
      <input type="text" @input="autocomplete" v-model="searchData" @keypress.enter="paginate(0)" />
    </div>
    <v-container v-if="searchedData.length > 0">
      <div style="text-align:center;">
        <v-card class="mx-auto" max-width="500" style="overflow-y:auto; float:center;">
          <v-list style=" float:center;">
            <v-list-item-group v-model="searchedData">
              <v-list-item v-for="(item, i) in searchedData" :key="i">
                <v-list-item-content @click="searchDetailPage(item)">
                  <v-list-item-title v-text="item"></v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-card>
      </div>
    </v-container>
    <button v-on:click="fistPg()" style="margin-right:10px;margin-top:100px;">{{ fistBt }}</button>
    <button v-on:click="prevPg()" style="margin-right:10px;">{{ prevBt }}</button>
    <button v-on:click="paginate(pageNm)" v-for="pageNm in pageNms" :key="pageNm">
      <span style="margin-right:10px;">{{ pageNm }}</span>
    </button>
    <button v-on:click="nextPg(filteredData)" style="margin-right:10px;">{{ nextBt }}</button>
    <button v-on:click="lastPg()">{{ lastBt }}</button>
  </div>
</template>

<script>
const axios = require("axios");
import http from "../http-common";
export default {
  data: () => {
    return {
      searchData: "",
      cocktailArray: [],
      pageNms: [],
      cocktailNameArray: [],
      filteredData: 25,
      prevBt: "<",
      nextBt: ">",
      fistBt: "<<",
      lastBt: ">>",
      filtered: "all",
      searchedData: [],
      filters: [
        { name: "레몬", image: require("../assets/images/lemon.png") },
        { name: "럼", image: require("../assets/images/lemon.png") },
        { name: "위스키", image: require("../assets/images/lemon.png") },
        { name: "진", image: require("../assets/images/lemon.png") },
        { name: "와인", image: require("../assets/images/lemon.png") },
        { name: "오렌지", image: require("../assets/images/lemon.png") },
        { name: "보드카", image: require("../assets/images/lemon.png") },
        { name: "맥주", image: require("../assets/images/lemon.png") }
      ]
    };
  },
  created() {
    this.paginate(1, this.filtered);
    this.cocktailName();
  },
  methods: {
    paginate(pageNm) {
      let data = {
        pageNm
      };
      if (this.searchData === "") {
        this.searchData = "h";
      }
      http
        .get("/cocktail/list?page=" + (pageNm - 1), {
          params: { filtered: this.filtered, searchedFiltered: this.searchData }
        })
        .then(res => {
          this.cocktailArray = res.data.content;
          console.log(res);
          this.cocktailArray.forEach(element => {
            if (element.image != "") {
              element.image = require(`../../../images/${element.cid}.jpg`);
            } else {
              element.image = require(`../../../images/default.png`);
            }
          });
          this.filteredData = res.data.totalPages;
          if (this.filteredData < 5) {
            this.pageNms = [];
            for (var i = 1; i <= this.filteredData; i++) {
              this.pageNms.push(i);
            }
          } else {
            this.pageNms = [1, 2, 3, 4, 5];
          }
        });
      if (this.searchData === "h") {
        this.searchData = "";
      }
    },
    nextPg(filteredData) {
      if (this.filteredData < 5) {
        this.pageNms = [];
        for (var i = 1; i <= this.filteredData; i++) {
          this.pageNms.push(i);
        }
      } else {
        if (this.pageNms[4] + 5 >= filteredData) {
          this.pageNms = [
            this.filteredData - 4,
            this.filteredData - 3,
            this.filteredData - 2,
            this.filteredData - 1,
            this.filteredData
          ];
        } else {
          this.pageNms = this.pageNms.map(pageNm => {
            return pageNm + 5;
          });
        }
      }
      console.log(this.pageNms[0]);
      this.$router.push("/cocktail/list/" + this.pageNms[0]);
      // this.paginate(this.pageNms[0]);
    },
    prevPg() {
      var flag = false;
      this.pageNms.forEach(function(pageNm) {
        if (pageNm < 5) flag = true;
      });
      if (flag) {
        if (this.filteredData < 5) {
          this.pageNms = [];
          for (var i = 1; i <= this.filteredData; i++) {
            this.pageNms.push(i);
          }
        } else {
          this.pageNms = [1, 2, 3, 4, 5];
        }
      } else {
        this.pageNms = this.pageNms.map(pageNm => {
          return pageNm - 5;
        });
      }
      this.paginate(this.pageNms[0]);
    },
    fistPg() {
      if (this.filteredData < 5) {
        this.pageNms = [];
        for (var i = 1; i <= this.filteredData; i++) {
          this.pageNms.push(i);
        }
      } else {
        this.pageNms = [1, 2, 3, 4, 5];
      }
      this.paginate(1);
    },
    lastPg() {
      this.pageNms = [
        this.filteredData - 4,
        this.filteredData - 3,
        this.filteredData - 2,
        this.filteredData - 1,
        this.filteredData
      ];
      this.paginate(this.filteredData - 4);
    },
    goToDetail(sendCid) {
      this.$router.push({
        name: "CocktailDetail",
        params: {
          cid: sendCid
        }
      });
    },
    clickFilter(filter) {
      this.filtered = filter;
      this.paginate(this.pageNms[0]);
    },
    cocktailName() {
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
    searchDetailPage(item) {
      let id = this.cocktailNameArray.indexOf(item) + 1;
      this.$router.push({
        name: "CocktailDetail",
        params: {
          cid: id
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
