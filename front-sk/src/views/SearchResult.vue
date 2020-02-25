<template>
  <div style="background-color: white; padding-bottom: 5vh">
    <div id="search-result-header">
      <h1 id="search-result-title">"{{ q }}" 통합 검색 결과</h1>
    </div>
    <div id="search-result-context">
      <h1 class="search-result-category">칵테일 검색 결과</h1>
      <div v-for="(cocktail, i) in cocktailResult" :key=i>
        {{ cocktail.cname }}
      </div>
      <h1 class="search-result-category">자유 게시판 검색 결과</h1>
      <div v-for="(board, j) in boardResult.content" :key=j>
        {{ board.title }}
      </div>
        <div id="pagination">
          <button v-for="pageNm in pageNmsB" :key="pageNm" @click="getBoardRes(pageNm)">
            <span style="margin-right:10px;">{{ pageNm }}</span>
          </button>
        </div>
      <h1 class="search-result-category">레시피 공유 검색 결과</h1>
      <div v-for="(boardrecipe, k) in boardRecipeResult.content" :key=k>
        {{ boardrecipe.title }}
      </div>
        <div id="pagination">
          <button v-for="pageNm in pageNmsBR" :key="pageNm" @click="getBoardRecipeRes(pageNm)">
            <span style="margin-right:10px;">{{ pageNm }}</span>
          </button>
        </div>
    </div>
  </div>
</template>

<script>
import http from "../http-common";
import Constant from "../Constant";
export default {
  data() {
    return {
      q: "",
      cocktailResult: [],
      boardResult: [],
      boardRecipeResult: [],
      pageNm: 0,
      totalPagesB: 0,
      totalPagesBR: 0,
      filtered: "",
      pageNmsB: [],
      pageNmsBR: [],
    };
  },
  methods: {
    getCocktailRes(pageNm) {
      this.$store
        .dispatch(Constant.GET_COCKTAILLIST, {
          pageNm: pageNm,
          filtered: this.filtered,
          searchedFiltered: this.q
        })
        .then(() => {
          this.cocktailResult = { ...this.$store.state.cocktailList };
        });
    },
    getBoardRes(pageNm) {
      http
        .get("/board/search", {
          params: {
            searchData: this.q
          }
        })
        .then(response => {
          this.boardResult = response.data.object;
          this.totalPagesB = response.data.object.totalPages;
          console.log(this.boardResult);
          let arr = [];

          let min = 1;
          for (let index = 0; index < 5; index++) {
            if (Number(min + index) > this.totalPagesB) break;
            arr.push(Number(min + index));
          }
          this.pageNmsB = arr;
        })
        .catch(error => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },
    getBoardRecipeRes(pageNm) {
      http
        .get("/boardrecipe/search", {
          params: {
            searchData: this.q
          }
        })
        .then(response => {
          this.boardRecipeResult = response.data.object;
          this.totalPagesBR = response.data.object.totalPages;
          console.log(this.boardRecipeResult);
          let arr = [];

          let min = 1;
          for (let index = 0; index < 5; index++) {
            if (Number(min + index) > this.totalPagesBR) break;
            arr.push(Number(min + index));
          }
          this.pageNmsBR = arr;
        })
        .catch(error => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    
    },
  },
  created() {
    this.q = this.$route.params.q
    this.getCocktailRes(1)
    this.getBoardRes(1)
    this.getBoardRecipeRes(1)
  },
  mounted() {
  },
  watch: {
    q(){
      this.q = this.$route.params.q
    }
  }
};
</script>

<style>
#search-result-header {
  background: linear-gradient(rgba(0, 0, 0, 0.5)),
    url("../assets/images/image8.jpg") no-repeat;
  background-size: 100%;
  height: 60vh;
  background-position-y: 20%;
  color: white;
}
#search-result-title {
  margin: 0 0 0 15vw;
  display: inline;
  position: relative;
  float: left;
  top: 35vmin;
  font-size: 11vmin;
  font-family: "BBTreeGB";
}
#search-result-context {
  margin: 5vmax 10vmax;
  font-family: "BBTreeGL";
}
.search-result-category {
  font-family: "BBTreeGB";
  border-bottom: 1px solid #ccc;
}
@media (max-width: 960px) {
  #board-context {
    margin: 2vmax 3vmax;
  }
  #search-result-header {
    height: 50vh;
    background-size: 200vw;
    background-position-x: 50%;
  }
  #search-result-title {
    margin-top: 3vmin;
    font-size: 7vmin;
  }
}
</style>