<template>
  <div>
    <div id="board-list-header">
      <h1 id="board-category">자유게시판</h1>
    </div>
    <div v-if="!submitted">
      <form
        action="action"
        method="post"
        id="_boardForm"
        name="boaradForm"
        @submit.prevent="addBoard"
      >
        <div id="board-context">
          <h1 style="display: inline;">제목</h1>
          <br />
          <input
            value="제목"
            type="text"
            name="titleid"
            id="_titleid"
            v-model="btitleid"
            style="width:100%; border-bottom:1px solid #ccc; margin-bottom: 5vh;"
          />
          <h1>내용</h1>
          <textarea
            value="내용"
            type="text"
            name="contentsid"
            id="_contentsid"
            v-model="bcontentsid"
            style="width:100%; height: 10rem; border-bottom:1px solid #ccc"
          />
        </div>

        <file-upload />
        <div style="text-align: center;">
        <button class="board-button" type="submit" name="button">등록</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import http from "../../http-common";

import FileUpload from "@/components/FileUpload";

export default {
  components: {
    FileUpload
  },
  name: "add-board",

  data() {
    return {
      info: null,
      loading: true,
      errored: false,
      btitleid: "",
      bcontentsid: "",
      submitted: false,
      bid: 0,
      filearr: [],
      fdid: 0,
      aa: []
    };
  },
  methods: {
    addBoard() {
      this.filearr = this.$store.state.filelist;
      this.fdid = this.$store.state.filedelete;
      if (this.fdid != null) {
        for (let i = 0; i < this.filearr.length; i++) {
          if (this.filearr[i].filelist === this.fdid) {
            this.filearr.splice(this.filearr.indexOf(i).filelist, 1);
          }
        }
      }

      for (let i = 0; i < this.filearr.length; i++) {
        this.aa[i] = this.filearr[i].filelist;
      }
      this.$store.state.filelist = [];
      this.$store.state.filedelete = null;

      http
        .post("/board/", {
          title: this.btitleid,
          contents: this.bcontentsid,
          user_name: window.sessionStorage.getItem("login_username"),
          file: JSON.stringify(this.aa)
        })
        .then(res => {
          this.bid = res.data;
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

<style scoped="scoped">
#board-category {
  margin: 0 0 0 15vw;
  display: inline;
  position: relative;
  float: left;
  top: 35vmin;
  font-size: 11vmin;
}
#board-context {
  color: #ccc;
  padding: 2rem 5vw;
}
.board-button {
  color: #ccc;
  width: 50vmin;
  height: 9vmin;
  margin-bottom: 3vh;
  font-size: 3vmin;
  border: 1px solid #ccc;
  border-radius: 10vmin;
}
</style>