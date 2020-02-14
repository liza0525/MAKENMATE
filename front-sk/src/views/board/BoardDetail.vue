<template>
  <div id="board-detail">
    <div id="board-header">
      <h1 id="board-title">{{ board.title }}</h1>
      <h3 id="board-username">by. {{ board.user_name }}</h3>
      <div id="like-button">
        <button @click="clickLike">
          <span v-show="!islike">
            <i class="far fa-heart"></i>
          </span>
          <span v-show="islike" style="color: red;">
            <i class="fas fa-heart"></i>
          </span>
        </button>
        {{ likebyboard }}
      </div>
    </div>
    <div id="board-context" v-html="board.contents"></div>
    <div id="board-footer">
      <button class="board-button" @click="go_to_list()">목록</button>
      <button class="board-button" v-if="this.$store.state.username" @click="update_board(board.bid)">수정</button>
      <button class="board-button" v-if="this.$store.state.username" @click="delete_board(board.bid)">삭제</button>
      <div id="board-date">{{ board.regdate }}</div>
      
      <!-- 댓글 -->
      <div
      :v-if="reply"
      v-for="(re, i) in reply"
      :key="i"
      style="margin-top: 5px; display:block;"
    >
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
    <!-- </v-text> -->
    <input type="text" v-model="comment" />
    <button @click="submitComment" type="submit">button</button>
    <div>
      <button v-for="pageNm in pageNms" :key="pageNm" @click="search(pageNm)">
        <span style="margin-right:10px;">{{ pageNm }}</span>
      </button>
    </div>
    <div v-for="images in imagepath" v-bind:key="images">
          <img :src="images"/>
        </div>
    </div>
  </div>

  
</template>

<script>
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
                        console.log(this.board.filelist[0])
                        for (let i = 0; i < this.board.filelist.length; i++) {
                          this.imagepath[i] = require("C:/image/" + this.board.filelist[i])
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
#board-username {
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
#like-button {
  display: inline;
  position: relative;
  color: white;
  font-size: 2rem;
  line-height: 5rem;
  float: right;
  top: 13.5rem;
  margin: 0 15rem 0 0;
}
</style>
