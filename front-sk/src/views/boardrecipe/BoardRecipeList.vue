<template>
  <div style="background-color: white; padding-bottom: 5vh">
    <div id="boardrecipe-list-header">
      <h1 id="boardrecipe-category">레시피 공유</h1>
    </div>
    <div id="boardrecipe-context">
      <!-- 검색 기능은 getSearchData 메소드에 정리 -->
      <Search @searchData="getSearchData" id="search"></Search>
      <v-simple-table>
        <template>
          <thead>
            <tr>
            <tr id="table-header">
              <th id="table-header-no">No.</th>
              <th id="table-header-title">제목</th>
              <th id="table-header-writer">글쓴이</th>
              <th id="table-header-date">날짜</th>
            </tr>
          </thead>
          <tbody>
            <tr class="text-center" v-for="board in info.content" v-bind:key="board.rid">
              <td class="table-content-no" v-html="board.rid"></td>
              <td class="table-content-title" v-html="board.title" @click="detail_id(board.rid)" style="cursor: pointer;"></td>
              <td class="table-content-writer">
                <router-link
                  :to="{
                  name: 'UserProfile',
                  params: {
                    username: board.user.nickname
                  }
                }"
                 style="cursor: pointer;">
                {{ board.user.nickname }}</router-link>
              </td>
              <td class="table-content-date" v-html="board.regdate"></td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </div>
      <div style="width: 100%; text-align: center; color: #000;">
      <div id="pagination">
        <button v-for="pageNm in pageNms" :key="pageNm" @click="retrieveBoard(pageNm)">
          <span style="margin-right:10px;">{{ pageNm }}</span>
        </button>
      </div>
    </div>
    <div id="boardrecipe-list-footer">
      <div class="board-button">
        <v-btn fab small dark @click="add_move()">
          <v-icon>mdi-pencil</v-icon>
        </v-btn>
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
    Search
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
      http
        .get("/boardrecipe/search", {
          params: {
            searchData: inputValue
          }
        })
        .then(response => {
          this.info = response.data.object;
          this.totalPages = response.data.object.totalPages;
          console.log(this.info);
          let arr = [];

          let min = 1;
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
  height: 60vh;
  background-position-y: 30%;
  color: white;
}
#boardrecipe-category {
  margin: 0 0 0 15vw;
  display: inline;
  position: relative;
  float: left;
  top: 35vmin;
  font-size: 11vmin;
  font-family: "BBTreeGB";
}
#boardrecipe-list-footer {
  margin: 0vmax 10vmax;
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
#search {
  border-bottom: 1px solid #000;
  width: 230px;
  margin-right: 10px;
  margin-left: auto;
  margin-bottom: 20px;
}
tr {
  text-align: center;
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
#table-header-no, .table-content-no {
  width: 10%;
}
#table-header-title, .table-content-title {
  width: 50%;
}
#table-header-writer, .table-content-writer {
  width: 20%;
}
#table-header-date, .table-content-date {
  width: 20%;
}
@media (max-width: 960px) {
  #board-context {
    margin: 2vmax 3vmax;
  }
  #board-list-header {
    height: 50vh;
    background-size: 200vw;
    background-position-x: 50%;
  }
  #board-category {
    margin-top: 3vmin;
    font-size: 7vmin;
  }
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
