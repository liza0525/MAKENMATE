<template>
  <div style="padding-top: 100px; color: red;">
    <button @click="clickLike">
      <span v-show="!islike">
        <i class="far fa-heart"></i>
      </span>
      <span v-show="islike">
        <i class="fas fa-heart"></i>
      </span>
    </button>
    {{ likebyboard }}
    <table class="list_table">
      <tr>
        <th style="width:10%">번호</th>
        <th style="width:20%">제 목</th>
        <th style="width:20%">내 용</th>
        <th style="width:20%">날 짜</th>
        <th style="width:10%">작성자</th>
        <th>삭 제</th>
        <th>수 정</th>
      </tr>
      <tr>
        <td v-html="board.bid"></td>
        <td v-html="board.title"></td>
        <td v-html="board.contents"></td>
        <td v-html="board.regdate"></td>
        <td v-html="board.user_name"></td>
        <td class="button" @click="delete_board(board.bid)">
          <input type="button" class="blue" value="삭제" />
        </td>
        <td class="button" @click="update_board(board.bid)">
          <input type="button" class="blue" value="수정" />
        </td>
      </tr>
    </table>

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
        user_name: ""
      },
      username: "",
      comment: "",
      isInput: [],
      updatedComment: "",
      pageNm: 1,
      pageNms: [],
      isLike: [],
      likebycomments: []
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

<style></style>
