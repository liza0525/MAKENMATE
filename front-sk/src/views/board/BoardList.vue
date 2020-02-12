<template>
  <div style="padding-top: 100px; color: red;">
    <div>
      <table class="list_table">
        <col width="10%" />
        <col width="20%" />
        <col width="20%" />
        <col width="10%" />
        <tr>
          <th>글 번호</th>
          <th>제 목</th>
          <th>날 짜</th>
        </tr>

        <tr v-for="board in info.object" v-bind:key="board.bid">
          <td v-html="board.bid" @click="detail_id(board.bid)"></td>
          <td v-html="board.title"></td>
          <td v-html="board.regdate"></td>
        </tr>
      </table>
    </div>
    <div>
      <!-- <input type="button" @click=" add_move()" value="글쓰기"></div> -->
      <a href="/#/board/add">
        <input type="button" value="글쓰기" />
      </a>
    </div>
  </div>
</template>

                    <script>
import http from "../../http-common";
export default {
  name: "board-list",
  data: () => {
    return { info: [], loading: true, errored: false };
  },
  methods: {
    retrieveBoard() {
      http
        .get("/board")
        .then(response => (this.info = response.data))
        .catch(error => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },
    add_move() {
      this.$router.push("BoardAdd");
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
    this.retrieveBoard();
  }
};
</script>

                    <style></style>