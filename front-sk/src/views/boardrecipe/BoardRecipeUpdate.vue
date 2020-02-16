<template>
  <div style="padding-top: 100px; color: red;">
<div id="boardrecipe-list-header">
      <h1 id="boardrecipe-category">{{ board.title }}</h1>
    </div>
    <div v-if="!submitted">
      <form
        action="action"
        method="post"
        id="_boardForm"
        name="boaradForm"
        @submit.prevent="updateBoard"
      >
        <div id="boardrecipe-context">
          <h1 style="display: inline;">제목</h1>
          <br />
          <input
            value="제목"
            type="text"
            name="titleid"
            id="_titleid"
            v-model="board.title"
            style="width:100%; border-bottom:1px solid #ccc; margin-bottom: 5vh;"
          />
          <h1>내용</h1>
          <textarea
            value="내용"
            type="text"
            name="contentsid"
            id="_contentsid"
            v-model="board.contents"
            style="width:100%; height: 10rem; border-bottom:1px solid #ccc"
          />
        </div>

        <file-upload />
        <div style="text-align: center;">
          <button class="boardrecipe-button" type="submit" name="button">수정</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import http from "../../http-common";

export default {
  data: () => {
    return {
      rid: 0,
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
      this.rid = this.$route.params.rid;
      http.get("/boardrecipe/" + this.rid).then(res => {
        this.board = res.data;
      });
    },
    updateBoard() {
      http
        .put("/boardrecipe/" + this.rid, {
          rid: this.rid,
          title: this.board.title,
          contents: this.board.contents
        })
        .then(res => {
          this.$router.push({
            name: "BoardRecipeDetail",
            params: {
              rid: this.rid
            }
          });
        });
      this.submitted = true;
    }
  }
};
</script>
<style scoped="scoped">
#boardrecipe-category {
  margin: 0 0 0 15vw;
  display: inline;
  position: relative;
  float: left;
  top: 35vmin;
  font-size: 11vmin;
}
#boardrecipe-context {
  color: #ccc;
  padding: 2rem 5vw;
}
.boardrecipe-button {
  color: #ccc;
  width: 50vmin;
  height: 9vmin;
  margin-bottom: 3vh;
  font-size: 3vmin;
  border: 1px solid #ccc;
  border-radius: 10vmin;
}
</style>