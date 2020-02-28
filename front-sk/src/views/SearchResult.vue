<template>
  <div style="background-color: white; padding-bottom: 5vh">
    <div id="search-result-header">
      <h1 id="search-result-title">"{{ q }}" 통합 검색 결과</h1>
    </div>
    <div id="search-result-context">
      <h1 v-if="cocktailResult.content !== null" class="search-result-category">칵테일 검색 결과</h1>
      <!-- 칵테일 게시판 -->
      <div class="array-container">
        <div
          v-for="(cocktail, i) in cocktailResult"
          :index="i"
          :key="i"
          style="display: inline-block; margin: 0 0.5rem; width: 200px !important; height:350px !important; background-color:#ffffff;"
        >
          <v-card style="height:300px">
            <v-img :src="cocktail.image" 
            alt="cocktail.cname" 
            style="width: 100%; height:100%; cursor:pointer" 
            @click="goToCocktailDetail(cocktail.cid)"></v-img>
            <h1
              class="sansfont"
              style="margin-left:30px; margin-top:10px;font-weight:bolder; cursor: pointer"
              @click="goToCocktailDetail(cocktail.cid)"
            >{{ cocktail.cname }}</h1>
          </v-card>
        </div>
      </div>
      <!-- 공유 게시판 -->
      <h1 v-if="boardRecipeResult.content != null" class="search-result-category">레시피 공유 검색 결과</h1>
      <div class="array-container">
        <v-card
          v-for="(boardrecipe, j) in boardRecipeResult.content"
          :key="j"
          style="display: inline-block; width: 250px; margin: 0 0.5rem;"
        >
          <v-card-text>
            <v-list-item-avatar size="20" style="display: inline; cursor: pointer"
            @click="goToUserDetail(boardrecipe.user.nickname)">
              <v-img class="elevation-3" :src="boardrecipe.user.image"></v-img>
            </v-list-item-avatar>
            <div style="display: inline; margin-left: 5px; cursor: pointer"
            @click="goToUserDetail(boardrecipe.user.nickname)">{{ boardrecipe.user.nickname }}</div>
            <div @click="goToBRecipeDetail(boardrecipe.rid)" style="cursor: pointer">
            <h2>{{ boardrecipe.title }}</h2>
            <div class="card-contents">{{ boardrecipe.contents }}</div>
            </div>
            <div style="border-top: 0.5px solid #ccc; padding-top: 1rem; text-align: right;">
              {{ boardrecipe.regdate }}
            </div>
          </v-card-text>
        </v-card>
      </div>
      <!-- <div id="pagination">
        <button v-for="pageNm in pageNmsBR" :key="pageNm" @click="getBoardRecipeRes(pageNm)">
          <span style="margin-right:10px;">{{ pageNm }}</span>
        </button>
      </div>-->
      <!-- 자유 게시판 -->
      <h1 class="search-result-category">자유 게시판 검색 결과</h1>
      <div class="array-container">
        <v-card
          v-for="(board, j) in boardResult.content"
          :key="j"
          style="display: inline-block; width: 250px; margin: 0 0.5rem;"
        >
          <v-card-text>
            <v-list-item-avatar size="20" style="display: inline; cursor: pointer">
              <v-img class="elevation-3" :src="board.user.image" @click="goToUserDetail(board.user.nickname)"></v-img>
            </v-list-item-avatar>
            <div style="display: inline; margin-left: 5px; cursor: pointer;" 
            @click="goToUserDetail(board.user.nickname)">{{ board.user.nickname }}</div>
            <div @click="goToBoardDetail(board.bid)" style="cursor: pointer">
            <h2>{{ board.title }}</h2>
            <div class="card-contents">{{ board.contents }}</div>
            </div>
            <div style="border-top: 0.5px solid #ccc; padding-top: 1rem; text-align: right;">
              {{ board.regdate }}
            </div>
          </v-card-text>
        </v-card>
      </div>

      <!---->
      <!-- <div id="pagination">
        <button v-for="pageNm in pageNmsB" :key="pageNm" @click="getBoardRes(pageNm)">
          <span style="margin-right:10px;">{{ pageNm }}</span>
        </button>
      </div> -->
    </div>
  </div>
</template>

<script>
import http from "../http-common";
import Constant from "../Constant";
export default {
  data() {
    return {
      // q: "",
      cocktailResult: [],
      boardResult: [],
      boardRecipeResult: [],
      pageNm: 0,
      totalPagesB: 0,
      totalPagesBR: 0,
      filtered: "",
      pageNmsB: [],
      pageNmsBR: []
    };
  },
  methods: {
    getCocktailRes(pageNm) {
      this.$store
        .dispatch(Constant.GET_COCKTAILLIST, {
          pageNm: pageNm,
          filtered: this.filtered,
          searchedFiltered: this.$store.state.totalSearchKeyword
        })
        .then(() => {
          this.cocktailResult = { ...this.$store.state.cocktailList };
        });
    },
    getBoardRes(pageNm) {
      http
        .get("/board/search", {
          params: {
            searchData: this.$store.state.totalSearchKeyword
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
            searchData: this.$store.state.totalSearchKeyword
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
    goToCocktailDetail(cid) {
      this.$router.push("/cocktail/detail/" + cid);
    },
    goToUserDetail(username) {
      this.$router.push("/user/userprofile/" + username);
    },
    goToBoardDetail(bid) {
      this.$router.push("/board/detail/" + bid);
    },
    goToBRecipeDetail(rid) {
      this.$router.push("/boardrecipe/detail/" + rid);
    },
  },
  created() {
    this.q = this.$store.state.totalSearchKeyword;
    this.getCocktailRes(1);
    this.getBoardRes(1);
    this.getBoardRecipeRes(1);
  },
  computed: {
    q: {
      get() {
        return this.$store.state.totalSearchKeyword;
      },
      set(val){
        this.$store.state.totalSearchKeyword = val;
        this.getCocktailRes(1);
        this.getBoardRes(1);
        this.getBoardRecipeRes(1);
      }
    }
  },
  watch: {
    q(val){
      this.q = val
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
  margin-top: 3rem;
  margin-bottom: 1.5rem;
}
.card-contents {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  margin: 0.5rem 0;

  white-space: normal;
  line-height: 1.2rem;
  height: 3.6rem;
  text-align: left;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}
.array-container {
  padding: 1rem 0;
  overflow-x: scroll;
  white-space: nowrap;
}
.array-container::-webkit-scrollbar {
  width: 10px;
  height: 10px;
}

.array-container ::-webkit-scrollbar-track {
  border-radius: 1px;
}

.array-container::-webkit-scrollbar-thumb {
  border-radius: 10px;
  background-color: #bfbfbf;
}
@media (max-width: 960px) {
  #search-result-context {
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
  .array-container {
    -ms-overflow-style: none; /* IE and Edge */
    scrollbar-width: none; /* Firefox */
  }
  .array-container::-webkit-scrollbar {
      display: none; /* Chrome, Safari, Opera*/
  }
}
</style>