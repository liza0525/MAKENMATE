<template>
  <div style="text-align:center;" class="test">
    <v-container fluid ma-0 pa-0 style="width:65%;">
      <v-row dense>
        <v-col
          v-for="cocktail in cocktailArray"
          v-bind:key="cocktail.cid"
          cols="3"
        >
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
    <button v-on:click="fistPg()" style="margin-right:10px;">
      {{ fistBt }}
    </button>
    <button v-on:click="prevPg()" style="margin-right:10px;">
      {{ prevBt }}
    </button>
    <button
      v-on:click="paginate(pageNm)"
      v-for="pageNm in pageNms"
      :key="pageNm"
    >
      <span style="margin-right:10px;">{{ pageNm }}</span>
    </button>
    <button v-on:click="nextPg(filteredData)" style="margin-right:10px;">
      {{ nextBt }}
    </button>
    <button v-on:click="lastPg()">{{ lastBt }}</button>
  </div>
</template>

<script>
const axios = require("axios");
import http from "../http-common";
export default {
  data: () => {
    return {
      cocktailArray: [],
      pageNms: [1, 2, 3, 4, 5],
      filteredData: 25,
      prevBt: "<",
      nextBt: ">",
      fistBt: "<<",
      lastBt: ">>",
      filtered: "레몬"
    };
  },
  created() {
    this.paginate(1, this.filtered);
  },
  methods: {
    paginate(pageNm) {
      let data = {
        pageNm
      };
      http
        .get("/posts?page=" + (pageNm - 1), {
          params: { filtered: this.filtered }
        })
        .then(res => {
          this.cocktailArray = res.data.content;
          this.cocktailArray.forEach(element => {
            if (element.image != "") {
              element.image = require(`../../../images/${element.cid}.jpg`);
            } else {
              element.image = require(`../../../images/default.png`);
            }
          });
          this.filteredData = res.data.totalPages;
        });
    },
    nextPg(filteredData) {
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
      this.paginate(this.pageNms[0]);
    },
    prevPg() {
      var flag = false;
      this.pageNms.forEach(function(pageNm) {
        if (pageNm < 5) flag = true;
      });
      if (flag) {
        this.pageNms = [1, 2, 3, 4, 5];
      } else {
        this.pageNms = this.pageNms.map(pageNm => {
          return pageNm - 5;
        });
      }
      this.paginate(this.pageNms[0]);
    },
    fistPg() {
      this.pageNms = [1, 2, 3, 4, 5];
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
