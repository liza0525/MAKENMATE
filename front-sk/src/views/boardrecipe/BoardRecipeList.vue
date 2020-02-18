<template>
  <div>
    <div id="boardrecipe-list-header">
      <h1 id="boardrecipe-category">레시피 공유</h1>
    </div>
    <div id="boardrecipe-context">
      <!-- 검색 기능은 getSearchData 메소드에 정리 -->
      <Search @searchData="getSearchData" class="search"></Search>
      <v-simple-table dark>
        <template>
          <thead>
            <tr>
              <th class="numbering-col">No.</th>
              <th>제목</th>
              <th>글쓴이</th>
              <th class="date-col">날짜</th>
            </tr>
          </thead>
          <tbody>
            <tr
              class="text-center"
              v-for="board in info.content"
              v-bind:key="board.rid"
            >
              <td class="numbering-col" v-html="board.rid"></td>
              <td
                v-html="board.title"
                @click="detail_id(board.rid)"
                style="cursor: pointer;"
              ></td>
              <td v-html="board.user.nickname"></td>
              <td class="date-col" v-html="board.regdate"></td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </div>
    <div id="boardrecipe-list-footer">
      <button
        v-if="this.$store.state.username"
        class="boardrecipe-button"
        @click="add_move()"
      >
        글쓰기
      </button>
      <div id="pagination">
        <button
          v-for="pageNm in pageNms"
          :key="pageNm"
          @click="retrieveBoard(pageNm)"
        >
          <span style="margin-right:10px;">{{ pageNm }}</span>
        </button>
      </div>
    </div>
  </div>
</template>
<script>
import http from "../../http-common";
import Search from "../../components/common/Search.vue";
export default {
  name: "boardrecipe-list",
  data: () => {
    return {
      info: [],
      loading: true,
      errored: false,
      totalPages: 0,
      pageNms: []
    };
  },
  components: {
    Search,
  },
  methods: {
    retrieveBoard(pageNm) {
      http
        .get("/boardrecipe", {
          params: {
            page: pageNm - 1
          }
        })
        .then(response => {
          this.info = response.data.object;
          this.totalPages = response.data.object.totalPages;
          console.log(this.info);
          let arr = [];

          let min = parseInt((pageNm - 1) / 5) * 5 + 1;
          for (let index = 0; index < 5; index++) {
            if (Number(min + index) > this.totalPages) break;
            arr.push(Number(min + index));
          }
          this.pageNms = arr;
        })
        .catch(error => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },
    add_move() {
      this.$router.push({ name: "BoardRecipeAdd" });
    },
    detail_id(sendrid) {
      this.$router.push({
        name: "BoardRecipeDetail",
        params: {
          rid: sendrid
        }
      });
    },
    getSearchData(inputValue) {
      console.log(inputValue);
    }
  },
  mounted() {
    this.retrieveBoard(1);
  }
};
</script>
<style>
@font-face {
  font-family: "BBTreeGB";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_nine_@1.1/BBTreeGB.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
#boardrecipe-list-header {
  background: linear-gradient(rgba(0, 0, 0, 0.5)),
    url("../../assets/images/image7.jpg") no-repeat;
  background-size: 100%;
  height: 50vh;
  background-position-y: 30%;
  color: white;
}
#boardrecipe-category {
  margin: 0 0 0 15vw;
  display: inline;
  position: relative;
  float: left;
  top: 30vmin;
  font-size: 11vmin;
  font-family: "BBTreeGB";
}
#boardrecipe-list-footer {
  color: #ccc;
  margin: 0vmax 10vmax;
  padding: 2rem 1rem;
  border-top: 1px solid #ccc;
}
#boardrecipe-context {
  color: #ccc;
  margin: 5vmax 10vmax;
  font-family: "GyeonggiBatang";
}
.boardrecipe-button {
  margin: 0 0.5rem;
  width: 15vmin;
  height: 9vmin;
  border: 1px solid #ccc;
  border-radius: 10vmin;
  font-size: 2vmin;
  font-family: "GyeonggiBatang";
}
@media (max-width: 700px) {
  #boardrecipe-context {
    margin: 2vmax 3vmax;
  }
  .numbering-col,
  .date-col {
    display: none;
  }
  #boardrecipe-list-header {
    height: 35vh;
    background-size: 200vw;
    background-position-x: 50%;
  }
  #boardrecipe-category {
    margin-top: 4vmin;
    font-size: 8vmin;
  }
}
</style>
