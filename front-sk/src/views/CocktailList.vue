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
            @click="clickFilter(filter.name)"
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
    <router-link
      :to="{ name: 'CocktailList', params: { pageNm: 1 } }"
      style="margin-right:10px;margin-top:100px; color:white;"
    >{{ fistBt }}</router-link>
    <router-link :to="{ name: 'CocktailList', params: { pageNm: min - 5 } }">
      <span style="margin-right:10px;margin-top:100px; color:white;">{{ prevBt }}</span>
    </router-link>
    <router-link
      :to="{ name: 'CocktailList', params: { pageNm: pageNm } }"
      v-for="pageNm in pageNms"
      :key="pageNm"
      style="color:white;"
    >
      <span style="margin-right:10px;">{{ pageNm }}</span>
    </router-link>
    <!-- <button
      v-on:click="paginate(pageNm)"
      v-for="pageNm in pageNms"
      :key="pageNm"
    >
      <span style="margin-right:10px;">{{ pageNm }}</span>
    </button>-->
    <router-link :to="{ name: 'CocktailList', params: { pageNm: min + 5 } }">
      <span style="margin-right:10px;margin-top:100px; color:white;">{{ nextBt }}</span>
    </router-link>
    <router-link :to="{ name: 'CocktailList', params: { pageNm: filteredData } }">
      <span style="margin-right:10px;margin-top:100px; color:white;">{{ lastBt }}</span>
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
      filteredData: 25,
      prevBt: "<",
      nextBt: ">",
      fistBt: "<<",
      lastBt: ">>",
      filtered: "all",
      searchedData: [],
      pageNms: [],
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
    this.paginate(this.$route.params.pageNm);
    this.pageNm = this.$route.params.pageNm;
    this.getCocktailName();
  },
  computed: {
    pageNm: {
      set: function(val) {
        let arr = [];
        this.min = parseInt((val - 1) / 5) * 5 + 1;
        for (let index = 0; index < 5; index++) {
          arr.push(Number(this.min + index));
          if (Number(this.min + index) > this.$store.state.totalPages) break;
        }
        this.pageNms = arr;
      },
      get: function() {
        return this.$route.params.pageNm;
      }
    }
  },
  methods: {
    paginate(pageNm) {
      if (this.searchData === "") {
        this.searchData = "h";
      }
      this.$store
        .dispatch(Constant.GET_COCKTAILLIST, {
          pageNm: pageNm - 1,
          filtered: this.filtered,
          searchedFiltered: this.searchData
        })
        .then(() => {
          this.cocktailArray = { ...this.$store.state.cocktailList };
          this.filteredData = this.$store.state.totalPages;
          console.log(this.cocktailArray);
        });
      if (this.searchData === "h") {
        this.searchData = "";
      }
      return this.cocktailArray;
    },
    goToDetail(sendCid) {
      this.$router.push("/cocktail/detail/" + sendCid);
    },
    clickFilter(filter) {
      this.filtered = filter;
      this.paginate(1);
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
