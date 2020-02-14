<template>
  <div style="padding: 100px; color: blue;">
    <p>
      <a @click="goback()">레시피 공유 게시판 리스트로</a>
    </p>
    <h1>
      {{ rid }}번 레시피 디테일
      <button @click="clickLike">
        <span v-show="!islike">
          <i class="far fa-heart"></i>
        </span>
        <span v-show="islike">
          <i class="fas fa-heart"></i>
        </span>
      </button>
      {{ likebyboardrecipe }}
    </h1>

    <p>제목 : {{ boardRecipe.title }}</p>
    <p>내용 : {{ boardRecipe.contents }}</p>
    <button v-if="!isScrapped" @click="addToScrapList" style="cursor: pointer">스크랩</button>
    <button v-if="isScrapped" @click="removeFromScrapList" style="cursor: pointer">스크랩 취소</button>
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
import Constant from "../../Constant";
import http from "@/http-common";
export default {
  data() {
    return {
      rid: 0,
      boardRecipe: {},
      userIdScrappingList: [],
      isScrapped: false,
      username: "",
      comment: "",
      isInput: [],
      updatedComment: "",
      pageNm: 1,
      pageNms: []
    };
  },
  created() {
    this.rid = Number(this.$route.params.rid);
    http.get("/boardrecipe/" + this.rid).then(res => {
      // console.log(res.data)
      this.boardRecipe = res.data;

      this.$store
        .dispatch(Constant.GET_REPLYBOARDRECIPE, {
          rid: this.$route.params.rid,
          pageNm: 1
        })
        .then(() => {
          this.reply = { ...this.$store.state.reply };
          this.users = { ...this.$store.state.users };
          for (let i = 0; i < this.reply.length; ++i) {
            this.isInput.push(0);
            // this.likebycomments.push(0);
            // this.$store
            //   .dispatch(Constant.GET_LIKEBYUSERANDBOARDRECIPECOMMENTS, {
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
            //   .dispatch(Constant.GET_LIKEBYBOARDRECIPECOMMENTS, {
            //     cmid: this.reply[i].cmid
            //   })
            //   .then(res => {
            //     let list = [...this.likebycomments];
            //     list.splice(i, 1, this.$store.state.likebycomments);
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
            .dispatch(Constant.GET_LIKEBYBOARDRECIPE, {
              rid: this.$route.params.rid
            })
            .then(() => {
              this.$store.dispatch(Constant.GET_LIKEBYUSERANDBOARDRECIPE, {
                rid: this.rid,
                username: this.$store.state.username
              });
            });
        });
    });
    http
      .get("/boardrecipe/scrapped/" + this.rid, {
        params: {
          username: this.$store.state.username
        }
      })
      .then(res => {
        this.isScrapped = res.data;
        console.log("this.isScrapped", this.isScrapped);
      });
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
    likebyboardrecipe() {
      return this.$store.state.likebyboardrecipe;
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
    addToScrapList() {
      this.isScrapped = true;
      this.$store.dispatch(Constant.ADD_SCRAP, {
        username: this.$store.state.username,
        rid: this.rid
      });
    },
    removeFromScrapList() {
      this.isScrapped = false;
      this.$store.dispatch(Constant.REMOVE_SCRAP, {
        username: this.$store.state.username,
        rid: this.rid
      });
    },
    submitComment() {
      this.$store.dispatch(Constant.ADD_REPLYBOARDRECIPE, {
        rid: this.rid,
        username: this.$store.state.username,
        comment: this.comment
      });
      this.isInput.push(0);
      this.comment = "";
    },
    updateComment(i, cmid, content) {
      this.$store
        .dispatch(Constant.MODIFY_REPLYBOARDRECIPE, {
          cmid: cmid,
          content: content,
          rid: this.rid
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
      this.$store.dispatch(Constant.REMOVE_REPLYBOARDRECIPE, {
        cmid: cmid,
        rid: this.rid
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
          .dispatch(Constant.ADD_BOARDRECIPELIKE, {
            rid: this.rid,
            username: this.$store.state.username
          })
          .then(() => (this.islike = this.$store.state.isLike));
      } else {
        this.$store
          .dispatch(Constant.REMOVE_BOARDRECIPELIKE, {
            rid: this.rid,
            username: this.$store.state.username
          })
          .then(() => (this.islike = this.$store.state.isLike));
      }
    },
    // clickLikeComments(i) {
    //   console.log(this.reply[i]);

    //   if (this.isLike[i] == false) {
    //     this.$store
    //       .dispatch(Constant.ADD_BOARDRECIPECOMMENTSLIKE, {
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
    //       .dispatch(Constant.REMOVE_BOARDRECIPECOMMENTSLIKE, {
    //         cmid: this.reply[i].cmid,
    //         username: this.$store.state.username
    //       })
    //       .then(() => {
    //         let list = [...this.isLike];
    //         0;
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
        .dispatch(Constant.GET_REPLYBOARDRECIPE, {
          rid: this.$route.params.rid,
          pageNm: pageNm
        })
        .then(() => {
          this.reply = { ...this.$store.state.reply };
          this.users = { ...this.$store.state.users };
          for (let i = 0; i < this.reply.length; ++i) {
            this.isInput.push(0);
          }
          let arr = [];

          let min = parseInt((pageNm - 1) / 5) * 5 + 1;
          for (let index = 0; index < 5; index++) {
            if (Number(min + index) > this.$store.state.totalPages) break;
            arr.push(Number(min + index));
          }
          this.pageNms = arr;
        });
    },
    goback() {
      this.$router.go(-1);
    }
  }
};
</script>

<style></style>
