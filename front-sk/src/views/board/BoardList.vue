<template>
  <div style="background-color: white; padding-bottom: 5vh">
    <div id="board-header">
      <h1 id="board-category">자유 게시판</h1>
    </div>
    <div id="board-context">
      <!-- 검색 기능은 getSearchData 메소드에 정리 -->
      <Search @searchData="getSearchData" id="search"></Search>
      <v-simple-table>
        <template>
          <thead>
            <tr id="table-header">
              <th id="table-header-no">No.</th>
              <th id="table-header-title">제목</th>
              <th id="table-header-writer">글쓴이</th>
              <th id="table-header-date">날짜</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="board in info.content" v-bind:key="board.bid">
              <td class="table-content-no" v-html="board.bid"></td>
              <td class="table-content-title" v-html="board.title" @click="detail_id(board.bid)" style="cursor: pointer;"></td>
              <td class="table-content-writer">
                <router-link
                  :to="{
                  name: 'UserProfile',
                  params: { username: board.user.nickname }
                }"
                  style="color: black; cursor: pointer;"
                >{{ board.user.nickname }}</router-link>
              </td>
              <td class="table-content-date" v-html="board.regdate"></td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
      <div style="width: 100%; text-align: center; color: #000;">
        <div id="pagination">
          <button v-for="pageNm in pageNms" :key="pageNm" @click="retrieveBoard(pageNm)">
            <span style="margin-right:10px;">{{ pageNm }}</span>
          </button>
        </div>
      </div>
    </div>
    <div id="board-list-footer">
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
  name: "board-list",
  components: {
    Search
  },
  data: () => {
    return {
      info: [],
      loading: true,
      errored: false,
      totalPages: 0,
      pageNms: []
    };
  },
  methods: {
    retrieveBoard(pageNm) {
      http
        .get("/board", {
          params: {
            page: pageNm - 1
          }
        })
        .then(response => {
          this.info = response.data.object;
          this.totalPages = response.data.object.totalPages;
          let arr = [];
          console.log(this.info);
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
      this.$router.push({ name: "BoardAdd" });
    },
    detail_id(sendbid) {
      this.$router.push({
        name: "BoardDetail",
        params: {
          bid: sendbid
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
<style scoped>
#board-header {
  background: linear-gradient(rgba(0, 0, 0, 0.5)),
    url("../../assets/images/image5.jpg") no-repeat;
  background-size: 100%;
  height: 60vh;
  background-position-y: 30%;
  color: white;
}
#board-category {
  margin: 0 0 0 15vw;
  display: inline;
  position: relative;
  float: left;
  top: 35vmin;
  font-size: 11vmin;
  font-family: "BBTreeGB";
}
#board-list-footer {
  margin: 0vmax 10vmax;
}
#board-context {
  color: #ccc;
  margin: 3vmax 10vmax;
}
.board-button {
  margin: 3vh 0.5rem;
}
.pagination {
  margin: 1rem 100px;
  font-size: 18px;
  display: inline;
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
