<template>
  <div style="text-align:center;" class="test">
    <v-item-group active-class="primary">
      <v-container fluid ma-0 pa-0 style="width:65%;">
        <div>
          <h1>Material</h1>
        </div>
        <v-row>
          <router-link
            :to="{
              name: 'CocktailList',
              query: {
                pageNm: 1,
                filtered: filter.name,
                searchedFiltered: searchData
              }
            }"
            v-for="filter in filters"
            :key="filter.name"
          >
            <v-col cols="12" md="2">
              <img
                :src="filter.image"
                :alt="filter.name"
                style="height:120px;"
              />
              <div style="font-size:30px;color:#797979">{{ filter.name }}</div>
            </v-col>
          </router-link>
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
      <input
        type="text"
        @input="autocomplete"
        v-model="searchData"
        @keypress.enter="search()"
      />
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
    <router-link
      :to="{
        name: 'CocktailList',
        query: {
          pageNm: pageNm,
          filtered: filter.filtered,
          searchedFiltered: searchData
        }
      }"
      style="margin-right:10px;margin-top:100px; color:white;"
      >{{ fistBt }}
    </router-link>
    <router-link
      :to="{
        name: 'CocktailList',
        query: {
          pageNm: min - 5 < 0 ? 1 : min - 5,
          filtered: filter.filtered,
          searchedFiltered: searchData
        }
      }"
    >
      <span style="margin-right:10px;margin-top:100px; color:white;">
        {{ prevBt }}
      </span>
    </router-link>
    <router-link
      :to="{
        name: 'CocktailList',
        query: {
          pageNm: pageNm,
          filtered: filter.filtered,
          searchedFiltered: searchData
        }
      }"
      v-for="pageNm in pageNms"
      :key="pageNm"
      style="color:white;"
    >
    <router-link
      :to="{
        name: 'CocktailList',
        query: {
          pageNm: min + 5,
          filtered: filter.filtered,
          searchedFiltered: searchData
        }
      }"
    >
      <span style="margin-right:10px;margin-top:100px; color:white;">
        {{ nextBt }}
      </span>
    </router-link>
    <router-link
      :to="{
        name: 'CocktailList',
        query: {
          pageNm: min + 5,
          filtered: filter.filtered,
          searchedFiltered: searchData
        }
      }"
    >
      <span style="margin-right:10px;margin-top:100px; color:white;">
        {{ lastBt }}
      </span>
    </router-link>
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
      filter: {
        filtered: "",
        searchData: ""
      },
      filters: [
        { name: "레몬", image: require("../assets/images/lemon.png") },
        { name: "럼", image: require("../assets/images/lemon.png") },
        { name: "위스키", image: require("../assets/images/lemon.png") },
        { name: "진", image: require("../assets/images/lemon.png") },
        { name: "와인", image: require("../assets/images/lemon.png") },
        { name: "오렌지", image: require("../assets/images/lemon.png") },
        { name: "보드카", image: require("../assets/images/lemon.png") },
        { name: "맥주", image: require("../assets/images/lemon.png") }
      ],
      min: 1
    };
  },
  mounted() {
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
  methods: {
    paginate(pageNm) {
      this.pageNm = pageNm;
      if (this.searchData === "") {
        this.searchData = "h";
      }
      this.$store
        .dispatch(Constant.GET_COCKTAILLIST, {
          pageNm: pageNm - 1,
          filtered: this.filter.filtered,
          searchedFiltered: this.searchData
        })
        .then(() => {
          this.cocktailArray = { ...this.$store.state.cocktailList };
          this.pageNm = pageNm;
        });
      if (this.searchData === "h") {
        this.searchData = "";
      }
      return this.cocktailArray;
    },
    goToDetail(sendCid) {
      this.$router.push("/cocktail/detail/" + sendCid);
    },
    search() {
      this.$router.push({
        name: "CocktailList",
        query: {
          pageNm: 1,
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
