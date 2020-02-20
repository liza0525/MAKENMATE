<template>
  <div style="background-color: white;">
    <div id="board-header">
      <h1 id="board-category">자유 게시판</h1>
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
          <v-btn class="board-button" type="submit" dark color="dark">
            <v-icon left>mdi-checkbox-marked-circle</v-icon> 등록
          </v-btn>
          <v-btn class="board-button" @click="$router.go(-1)" dark color="light">
            <v-icon left>mdi-cancel</v-icon> 취소
          </v-btn>
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
<style scoped="scoped">
#board-header {
  background: linear-gradient(rgba(0, 0, 0, 0.5)),
    url("../../assets/images/image5.jpg") no-repeat;
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
  font-family: "BBTreeGB";
}
#board-context {
  margin: 5vw 15vw;
  font-family: "GyeonggiBatang";
}
.board-button {
  width: 7rem;
  height: 3rem !important;
  margin: 0 5px;
  margin-bottom: 10vh;
  font-size: 1rem;
  background-color: black;
  border-radius: 10vmin;
  color: white;
}
@media (max-width: 700px) {
  #board-context {
    margin: 10vw 15vw 15vw 15vw;
    font-size: 15px;
  }
  #board-header {
    height: 35vh;
    background-size: 200vw;
    background-position-x: 50%;
  }
  #board-title {
    margin-top: 4vmin;
    font-size: 8vmin;
  }
  #board-username {
    margin: 0 0 0 2vw;
    display: inline;
    float: left;
    top: 40vmin;
    font-size: 3vmin;
  }
}
</style>