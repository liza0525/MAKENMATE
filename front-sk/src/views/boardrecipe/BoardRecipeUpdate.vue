<template>
  <div style="padding-top: 100px; color: red;">
    <div v-if="!submitted">
      <form
        action=""
        method="post"
        id="_boardForm"
        name="boaradForm"
        @submit.prevent="updateBoard"
      >
        <table>
          <colgroup>
            <col style="width:30%;" />
            <col style="width:70%;" />
          </colgroup>
          <tr>
            <th>제목</th>
            <td>
              <input
                data-msg="제목"
                type="text"
                name="title"
                id="_title"
                size="50"
                v-model="board.title"
                style="width:40%"
              />
            </td>
          </tr>
          <tr>
            <th>내용</th>
            <td>
              <input
                data-msg="내용"
                type="text"
                name="contents"
                id="_contents"
                size="50"
                v-model="board.contents"
                style="width:40%"
              />
            </td>
          </tr>
          <tr>
            <td colspan="2" style="height:50px; text-align:center;">
              <button type="submit" name="button">글 수정</button>
            </td>
          </tr>
        </table>
      </form>
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
      },
      submitted: false
    };
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      this.bid = this.$route.params.bid;
      http.get("/board/" + this.bid).then(res => {
        this.board = res.data;
        console.log(this.board);
      });
    },
    updateBoard() {
      http
        .put("/board/" + this.bid, {
          bid: this.bid,
          title: this.board.title,
          contents: this.board.contents
        })
        .then(res => {
          this.$router.push({
            name: "BoardDetail",
            params: {
              bid: this.bid
            }
          });
        });
      this.submitted = true;
    }
  }
};
</script>

<style></style>
