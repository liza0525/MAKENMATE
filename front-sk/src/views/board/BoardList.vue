<template>
  <div>
    <div id="board-list-header">
      <h1 id="board-category">자유 게시판</h1>
    </div>
    <div id="board-context">
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
            <tr v-for="board in info.content" v-bind:key="board.bid">
              <td class="numbering-col" v-html="board.bid"></td>
              <td v-html="board.title" @click="detail_id(board.bid)" style="cursor: pointer;"></td>
              <td v-html="board.user_name"></td>
              <td class="date-col" v-html="board.regdate"></td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </div>
    <div id="board-list-footer">
      <button v-if="this.$store.state.username" class="board-button" @click="add_move()">글쓰기</button>
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
td {
  font-family: "GyeonggiBatang";
}
#board-list-header {
  background: linear-gradient(rgba(0, 0, 0, 0.3)),
    url("../../assets/images/image.png") no-repeat;
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
  font-family: 'BBTreeGB';
}
#board-list-footer {
  color: #ccc;
  margin: 0vmax 10vmax;
  padding: 2rem 1rem;
  border-top: 1px solid #ccc;
}
#board-context {
  color: #ccc;
  margin: 5vmax 10vmax;
  font-family: "GyeonggiBatang";
}
.board-button {
  margin: 0 0.5rem;
  width: 15vmin;
  height: 9vmin;
  border: 1px solid #ccc;
  border-radius: 10vmin;
  font-size: 2vmin;
  font-family: "GyeonggiBatang";
}
#pagination {
  display: inline;
  float: right;
  font-size: 18px;
  font-family: "GyeonggiBatang";
}
@media (max-width: 700px) {
  #board-context {
  margin: 2vmax 3vmax;
  }
  .numbering-col, .date-col{
    display: none;
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
}
</style>