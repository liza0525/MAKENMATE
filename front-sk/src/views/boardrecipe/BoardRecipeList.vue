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

        <tr v-for="board in info.object" v-bind:key="board.rid">
          <td v-html="board.rid"></td>
          <td v-html="board.title" @click="detail_id(board.rid)" style="cursor: pointer;"></td>
          <td v-html="board.regdate"></td>
        </tr>
      </table>
    </div>
    <div>
      <a href="/#/boardrecipe/add">
        <input type="button" value="글쓰기" />
      </a>
    </div>
  </div>
</template>

<script>
import http from "../../http-common";
export default {
  name: "boardrecipe-list",
  data: () => {
    return { info: [], loading: true, errored: false };
  },
  methods: {
    retrieveBoard() {
      http
        .get("/boardrecipe")
        .then(response => (this.info = response.data))
        .catch(error => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },
    add_move() {
      this.$router.push("BoardRecipeAdd");
    },
    detail_id(sendrid) {
      this.$router.push({
        name: "BoardRecipeDetail",
        params: {
          rid: sendrid
        }
      });
    }
  },
  mounted() {
    this.retrieveBoard();
  }
};
</script>
<style>
</style>