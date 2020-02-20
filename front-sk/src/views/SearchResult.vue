<template>
  <div style="background-color: white; padding-bottom: 5vh">
    <div id="search-result-header">
      <h1 id="search-result-title">"{{ q }}" 통합 검색 결과</h1>
    </div>
    <div id="search-result-context">
      <h1 class="search-result-category">칵테일 검색 결과</h1>
      <div v-for="(cocktail, i) in cocktailResult" :key=i>
        {{ cocktail }}
      </div>
      <h1 class="search-result-category">자유 게시판 검색 결과</h1>
      <div v-for="(board, j) in boardResult" :key=j>
        {{ board }}
      </div>
      <h1 class="search-result-category">레시피 공유 검색 결과</h1>
      <div v-for="(boardrecipe, k) in boardRecipeResult" :key=k>
        {{ boardrecipe }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      q: "",
      cocktailResult: [1, 2, 3, 4],
      boardResult: [5, 6, 7, 8],
      boardRecipeResult: [9, 10, 11, 12]
    };
  },
  methods: {},
  created() {
    this.q = this.$route.params.q
  },
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