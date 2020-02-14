<template>
  <div>
    <div id="board-list-header">
      <h1 id="board-category">{{ board.title }}</h1>
    </div>
    <div v-if="!submitted">
      <form action method="post" id="_boardForm" name="boaradForm" @submit.prevent="updateBoard">
        <div id="board-context">
          <h1>제목</h1>
          <br />
          <input
            value="제목"
            type="text"
            name="titleid"
            id="_titleid"
            v-model="board.title"
            style="width:100%; border-bottom:1px solid #000;"
          />
          <h1>내용</h1>
          <textarea
            value="내용"
            type="text"
            name="contentsid"
            id="_contentsid"
            v-model="board.contents"
            style="width:100%; height: 10rem; border-bottom:1px solid #000"
          />
        </div>
        <file-upload />
        <button class="board-button" type="submit" name="button">수정</button>
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