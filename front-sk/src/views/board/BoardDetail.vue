<template>
  <div id="board-detail">
    <div id="board-header">
        <h1 id="board-title">{{ board.title }}</h1>
        <h3 id="board-username">by. {{ board.user_name }}</h3>
    </div>
    <div id="board-context" v-html="board.contents"></div>
    <div id="board-footer"> 
      <button class="board-button"  @click="go_to_list()">목록</button>
      <button class="board-button" @click="update_board(board.bid)">수정</button>
      <button class="board-button" @click="delete_board(board.bid)">삭제</button>
      <div id="board-date">{{ board.regdate }}</div>
    </div>
  </div>
</template>

<script>
import http from "../../http-common";
export default {
  data: () => {
    return {
      bid: 0,
      board: {
        title: "",
        file: "",
        contents: "",
        regdate: "",
        user_name: ""
      }
    };
  },
  mounted() {
    this.getData();
  },
  methods: {
    getData() {
      this.bid = this.$route.params.bid;
      http.get("/board/" + this.bid).then(res => {
        this.board = res.data;
        this.board.contents = this.board.contents
      });
    },
    delete_board(boardid) {
      http
        .delete("/board/" + boardid)
        .then(response => {
          this.$router.push({ name: "BoardList" });
        })
        .catch(error => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },
    update_board(boardid) {
      this.$router.push({ name: "BoardUpdate", params: { bid: boardid } });
    },
    go_to_list(){
      this.$router.push({ name: "BoardList" });
    }
  }
};
</script>
<style scoped>
#board-header {
  background: linear-gradient(rgba(0, 0, 0, 0.3)),
    url("../../assets/images/image.png") no-repeat;
  background-size: 100%;
  height: 20rem;
  background-position-y: 30%;
  color: white;
}
#board-title {
  margin: 0 0 0 15rem;
  display: inline;
  position: relative;
  float: left;
  top: 12rem;
  font-size: 4rem;
  /* text-align: center; */
}
#board-username{
  margin: 0 0 0 2rem;
  display: inline;
  position: relative;
  float: left;
  top: 15rem;
}
#board-date {
  display: inline;
  bottom: 0;
  float: right;
}
#board-context {
  color: #777;
  padding: 2rem 15rem;
  margin: 1rem 0;
}
#board-footer {
  color: #777;
  margin: 0 10rem;
  padding: 2rem 1rem;
  border-top: 1px solid #ccc;
}
.board-button {
  margin: 0 0.5rem;
  width: 5rem;
  height: 3rem;
  border: 1px solid #ccc;
  border-radius: 3rem;
}
</style>