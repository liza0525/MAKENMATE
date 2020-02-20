<template>
  <div id="board-detail" style="background-color: white;">
    <div id="board-header">
      <h1 id="board-title">{{ board.title }}</h1>
      <h3 id="board-username">
        by.
        <router-link
          :to="{
          name: 'UserProfile',
          params: {
            username: board.user_name,
          }
        }"
          style="color:white;"
        >{{ board.user_name }}</router-link>
      </h3>
    </div>
    <div id="board-context" v-html="board.contents"></div>

    <!-- 업로드 이미지 -->
    <!-- <v-row id="img-contents">
    <v-col cols="6" md="3" v-for="images in imagepath" v-bind:key="images">
      <v-card class="d-inline-block mx-auto"> 
      <v-img :src="images"></v-img>
      </v-card>
      </v-col>
    </v-row>-->

    <!-- like button -->
    <div style="text-align: center;">
      <v-btn v-show="!islike" fab small dark @click="clickLike">
          <v-icon>mdi-heart</v-icon>
      </v-btn>
      <v-btn v-show="islike" fab small dark @click="clickLike" style="background: red;">
          <v-icon>mdi-heart</v-icon>
      </v-btn>
    </div>
    <!-- crud 버튼 -->
    <div id="board-footer">
      <v-icon class="board-button" @click="go_to_list()">mdi-format-list-bulleted</v-icon>
      <v-icon
        class="board-button"
        v-if="this.$store.state.username === board.user_name"
        @click="update_board(board.bid)"
      >mdi-pencil</v-icon>
      <v-icon
        class="board-button"
        v-if="this.$store.state.username === board.user_name"
        @click="delete_board(board.bid)"
      >mdi-delete</v-icon>
      <!--  -->
      <div id="board-date">좋아요
          <v-icon style="color: red;">mdi-heart</v-icon> {{ likebyboard }}개 | {{ board.regdate }}</div>
    </div>

    <!-- 댓글 -->
    <div id="board-comment-set">
      <div id="comment-title">
        <h1 style="display: inline; margin-right: 3vw">Comment</h1>
        <div style="display: inline;">
          <input type="text" v-model="comment" style="border-bottom: 1px solid #ccc; width: 45vw" />
          <button @click="submitComment" type="submit">댓글</button>
        </div>
      </div>
      <div :v-if="reply" v-for="(re, i) in reply" :key="i" style="margin-top: 5px; display:block;">
        <div v-if="isInput[i] === 0">
          <span>{{ users[i] }} : {{ re.content }}</span>
          <p v-if="username === users[i]" style="display:inline-block;">
            <button @click="click(i)">수정</button>
            <button @click="deleteComment(i, re.cmid)">삭제</button>
          </p>
        </div>
        <div v-else>
          <span>
            {{ users[i] }} :
            <input v-model="re.content" />
          </span>
          <p v-if="username === users[i]" style="display:inline-block;">
            <button @click="updateComment(i, re.cmid, re.content)">수정</button>
          </p>
        </div>
      </div>
      <div>
        <button v-for="pageNm in pageNms" :key="pageNm" @click="search(pageNm)">
          <span style="margin-right:10px;">{{ pageNm }}</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script scoped>
import http from "../../http-common";
import Constant from "../../Constant";
export default {
  data: () => {
    return {
      bid: 0,
      board: {
        title: "",
        file: "",
        contents: "",
        regdate: "",
        user_name: "",
        filelist: []
      },
      username: "",
      comment: "",
      isInput: [],
      updatedComment: "",
      pageNm: 1,
      pageNms: [],
      isLike: [],
      likebycomments: [],
      imagepath: []
    };
  },
  mounted() {
    this.getData();
  },
  computed: {
    users: {
      set(val) {},
      get() {
        return this.$store.state.users;
      }
    },
    reply: {
      set(val) {},
      get() {
        return this.$store.state.reply;
      }
    },
    clicked: {
      set(val) {
        this.isInput[val] = 1;
      },
      get() {
        return true;
      }
    },
    likebyboard() {
      return this.$store.state.likebyboard;
    },
    islike: {
      set(val) {
        this.$store.state.isLike = val;
      },
      get() {
        return this.$store.state.isLike;
      }
    }
  },
  methods: {
    getData() {
      this.bid = this.$route.params.bid;
      this.username = this.$store.state.username;
      http.get("/board/" + this.bid).then(res => {
        this.board = res.data;

        if (this.board.filelist.length != 0) {
          console.log(this.board.filelist[0]);
          for (let i = 0; i < this.board.filelist.length; i++) {
            this.imagepath[i] = require("C:/image/" + this.board.filelist[i]);
          }
        }

        this.search(0);
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
    go_to_list() {
      this.$router.push({ name: "BoardList" });
    },
    submitComment() {
      this.$store.dispatch(Constant.ADD_REPLYBOARD, {
        bid: this.bid,
        username: this.$store.state.username,
        comment: this.comment
      });
      this.search(0);
      this.users.push(this.username);
      this.comment = "";
    },
    updateComment(i, cmid, content) {
      this.$store
        .dispatch(Constant.MODIFY_REPLYBOARD, {
          cmid: cmid,
          content: content,
          bid: this.bid
        })
        .then(() => {
          let list = [...this.isInput];
          list.splice(i, 1, 0);
          this.isInput = list;
        });
    },
    deleteComment(i, cmid) {
      this.users.splice(i, 1);
      this.isInput.splice(i, 1);
      this.$store.dispatch(Constant.REMOVE_REPLYBOARD, {
        cmid: cmid,
        bid: this.bid
      });
    },
    click(i) {
      let list = [...this.isInput];
      list.splice(i, 1, 1);
      this.isInput = list;
    },
    clickLike() {
      if (this.islike == false) {
        this.$store
          .dispatch(Constant.ADD_BOARDLIKE, {
            bid: this.bid,
            username: this.$store.state.username
          })
          .then(() => (this.islike = this.$store.state.isLike));
      } else {
        this.$store
          .dispatch(Constant.REMOVE_BOARDLIKE, {
            bid: this.bid,
            username: this.$store.state.username
          })
          .then(() => (this.islike = this.$store.state.isLike));
      }
    },
    // clickLikeComments(i) {
    //   if (this.isLike[i] == false) {
    //     this.$store
    //       .dispatch(Constant.ADD_BOARDCOMMENTSLIKE, {
    //         cmid: this.reply[i].cmid,
    //         username: this.$store.state.username
    //       })
    //       .then(() => {
    //         let list = [...this.isLike];
    //         list.splice(i, 1, true);
    //         this.isLike = list;
    //         let list2 = [...this.likebycomments];
    //         list2.splice(i, 1, this.likebycomments[i] + 1);
    //         this.likebycomments = list2;
    //       });
    //   } else {
    //     this.$store
    //       .dispatch(Constant.REMOVE_BOARDCOMMENTSLIKE, {
    //         cmid: this.reply[i].cmid,
    //         username: this.$store.state.username
    //       })
    //       .then(() => {
    //         let list = [...this.isLike];
    //         list.splice(i, 1, false);
    //         this.isLike = list;

    //         let list2 = [...this.likebycomments];
    //         list2.splice(i, 1, this.likebycomments[i] - 1);
    //         this.likebycomments = list2;
    //       });
    //   }
    // },
    search(pageNm) {
      this.$store
        .dispatch(Constant.GET_REPLYBOARD, {
          bid: this.$route.params.bid,
          pageNm: pageNm
        })
        .then(() => {
          this.reply = { ...this.$store.state.reply };
          this.users = { ...this.$store.state.users };
          this.isInput = [];
          this.likebycomments = [];
          for (let i = 0; i < this.reply.length; ++i) {
            this.isInput.push(0);
            // this.$store
            //   .dispatch(Constant.GET_LIKEBYUSERANDBOARDCOMMENTS, {
            //     username: this.$store.state.username,
            //     cmid: this.reply[i].cmid
            //   })
            //   .then(res => {
            //     let list = [...this.isLike];
            //     if (res.data.object == null) list.splice(i, 1, false);
            //     else list.splice(i, 1, true);
            //     this.isLike = list;
            //   });
            // this.$store
            //   .dispatch(Constant.GET_LIKEBYBOARDCOMMENTS, {
            //     cmid: this.reply[i].cmid
            //   })
            //   .then(res => {
            //     console.log(res);
            //     let list = [...this.likebycomments];
            //     list.splice(i, 1, res);
            //     this.likebycomments = list;
            //   });
          }
          let arr = [];

          let min = 1;
          for (let index = 0; index < 5; index++) {
            if (Number(min + index) > this.$store.state.totalPages) break;
            arr.push(Number(min + index));
          }
          this.pageNms = arr;
          this.$store
            .dispatch(Constant.GET_LIKEBYBOARD, {
              bid: this.$route.params.bid
            })
            .then(() => {
              this.$store.dispatch(Constant.GET_LIKEBYUSERANDBOARD, {
                bid: this.bid,
                username: this.$store.state.username
              });
            });
        });
    }
  }
};
</script>
<style scoped>
/* 본문 서체 */
@font-face {
  font-family: "BBTreeGL";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_nine_@1.1/BBTreeGL.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
#board-header {
  background: linear-gradient(rgba(0, 0, 0, 0.5)),
    url("../../assets/images/image5.jpg") no-repeat;
  background-size: 100%;
  height: 60vh;
  background-position-y: 30%;
  color: white;
}
#board-title {
  margin: 0 0 0 15vw;
  display: inline;
  position: relative;
  float: left;
  top: 35vmin;
  font-size: 11vmin;
  font-family: "BBTreeGB";
}
#board-username {
  margin: 0 0 0 2rem;
  display: inline;
  position: relative;
  float: left;
  top: 40vh;
  font-family: "BBTreeGB";
}
#board-date {
  display: inline;
  bottom: 0;
  float: right;
  margin-top: 3vh;
  font-family: "BBTreeGL";
}
#board-context {
  margin: 5vw 15vw;
  font-size: 1.2rem;
  font-size: 1rem;
  line-height: 1.5rem;
  font-family: "BBTreeGL";
}
#board-footer {
  margin: 0vmax 10vmax;
  padding: 0 1rem;
  padding-bottom: 2rem;
  border-bottom: 1px solid #ccc;
}
.board-button {
  font-size: 2rem;
  margin: 0 5px;
}
#board-like-button {
  width: 7rem;
  height: 3rem !important;
  margin: 0 5px;
  margin-bottom: 10vh;
  font-size: 1.3rem;
  border-radius: 10vmin;
}
#board-comment-set {
  margin: 3vmin;
  margin: 0vmax 10vmax;
  padding: 2rem 1rem;
  font-family: "GyeonggiBatang";
}
#img-contents {
  margin: 5vh 15vw;
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
