<template>
  <div>
    <div id="board-list-header">
      <h1 id="board-category">자유게시판</h1>
    </div>
    <div id="board-context">
      <!-- {{ info.content }} -->
      <v-simple-table>
        <template>
          <thead>
            <tr>
              <th>No.</th>
              <th>제목</th>
              <th>글쓴이</th>
              <th>날짜</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="board in info.content" v-bind:key="board.bid">
              <td v-html="board.bid"></td>
              <td v-html="board.title" @click="detail_id(board.bid)" style="cursor: pointer;"></td>
              <td v-html="board.user_name"></td>
              <td v-html="board.regdate"></td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
      
    </div>
    <div id="board-list-footer">
      <button class="board-button" @click="add_move()">글쓰기</button>
      <div id="pagination">
        <button v-for="pageNm in pageNms" :key="pageNm" @click="retrieveBoard(pageNm)">
          <span style="margin-right:10px;">{{ pageNm }}</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import http from "../../http-common";
export default {
  name: "board-list",
  data: () => {
    return { info: [], loading: true, errored: false, totalPages: 0, pageNms: [] };
  },
  methods: {
    retrieveBoard(pageNm) {
      http
        .get("/board",{
          params:{
            page: pageNm -1
          }
        })
        .then(response => {
          this.info = response.data.object
          this.totalPages = response.data.object.totalPages
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
      this.$router.push({name: "BoardAdd"});
    },
    detail_id(sendbid) {
      this.$router.push({
        name: "BoardDetail",
        params: {
          bid: sendbid
        }
      });
    }
  },
  mounted() {
    this.retrieveBoard(1);
  }
};
</script>
<style>
#board-list-header {
  background: linear-gradient(rgba(0, 0, 0, 0.3)),
    url("../../assets/images/image.png") no-repeat;
  background-size: 100%;
  height: 20rem;
  background-position-y: 30%;
  color: white;
}
#board-category {
  margin: 0 0 0 15rem;
  display: inline;
  position: relative;
  float: left;
  top: 12rem;
  font-size: 4rem;
}
#board-list-footer {
  color: #777;
  margin: 0 10rem;
  padding: 2rem 1rem;
  border-top: 1px solid #ccc;
}
#board-context {
  color: #777;
  padding: 2rem 15rem;
  margin: 1rem 0;
}
.board-button {
  margin: 0 0.5rem;
  width: 5rem;
  height: 3rem;
  border: 1px solid #ccc;
  border-radius: 3rem;
}
#pagination {
  display: inline;
  margin: 30rem;
}
</style>