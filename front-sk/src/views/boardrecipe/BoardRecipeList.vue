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
        <tr v-for="board in info.content" v-bind:key="board.rid">
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
    <div>
        <button v-for="pageNm in pageNms" :key="pageNm" @click="retrieveBoard(pageNm)">
          <span style="margin-right:10px;">{{ pageNm }}</span>
        </button>
    </div>
  </div>
</template>

<script>
import http from "../../http-common";
export default {
  name: "boardrecipe-list",
  data: () => {
    return { info: [], loading: true, errored: false, totalPages: 0, pageNms: [] };
  },
  methods: {
    retrieveBoard(pageNm) {
      http
        .get("/boardrecipe",{
          params:{
            page: pageNm -1
          }
        })
        .then(response => {
          this.info = response.data.object
          this.totalPages = response.data.object.totalPages
          console.log(this.info)
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
    this.retrieveBoard(1);
  }
};
</script>
<style>
</style>