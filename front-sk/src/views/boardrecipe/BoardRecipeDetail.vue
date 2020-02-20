<template>
  <div id="boardrecipe-detail" style="background-color: white;">
    <div id="boardrecipe-header">
      <h1 id="boardrecipe-title">{{ boardRecipe.title }}</h1>
      <h3 id="boardrecipe-username">
        by.
        <router-link
          :to="{
          name: 'UserProfile',
          params: {
            username: boardRecipe.user_name,
          }
        }"
          style="color:white;"
        >{{ boardRecipe.user_name }}</router-link>
      </h3>
    </div>
    <div id="boardrecipe-context" v-html="boardRecipe.contents"></div>

    <!-- 업로드 이미지-->
    <v-row id="img-contents">
      <v-col cols="6" md="3" v-for="images in imagepath" v-bind:key="images">
        <v-card class="d-inline-block mx-auto">
          <v-img :src="images"></v-img>
        </v-card>
      </v-col>
    </v-row>

    <!-- like button -->
    <div style="text-align: center; margin-bottom: 3rem;">
      <v-btn v-show="!islike" fab small dark @click="clickLike" style="margin-right: 10px;">
        <v-icon>mdi-heart</v-icon>
      </v-btn>
      <v-btn
        v-show="islike"
        fab
        small
        dark
        @click="clickLike"
        style="background: red; margin-right: 10px;"
      >
        <v-icon>mdi-heart</v-icon>
      </v-btn>
      <!-- 스크랩 -->
      <v-btn
        fab
        small
        dark
        v-if="this.$store.state.username && !isScrapped"
        @click="addToScrapList"
      >
        <v-icon>mdi-bookmark</v-icon>
      </v-btn>
      <v-btn
        fab
        small
        dark
        v-if="this.$store.state.username && isScrapped"
        @click="removeFromScrapList"
        style="background: green;"
      >
        <v-icon>mdi-bookmark</v-icon>
      </v-btn>
    </div>

    <!-- crud 버튼 -->
    <div id="boardrecipe-footer">
      <v-icon class="boardrecipe-button" @click="go_to_list()">mdi-format-list-bulleted</v-icon>
      <v-icon
        class="boardrecipe-button"
        v-if="this.$store.state.username === boardRecipe.user_name"
        @click="update_board(boardRecipe.rid)"
      >mdi-pencil</v-icon>
      <v-icon
        class="boardrecipe-button"
        v-if="this.$store.state.username === boardRecipe.user_name"
        @click="delete_board(boardRecipe.rid)"
      >mdi-delete</v-icon>
      <!--  -->
      <div id="boardrecipe-date">
        좋아요
        <v-icon style="color: red;">mdi-heart</v-icon>
        {{ likebyboardrecipe }}개
        <br />
        {{ boardRecipe.regdate }}
      </div>
    </div>

    <!-- 댓글 -->
    <div id="boardrecipe-comment-set">
      <div id="comment-title" style="text-align: center;">
        <h1 style="display: inline; margin-right: 3vw; font-family: 'BBTreeGB';">Comment</h1>
        <div style="display: inline;">
          <input type="text" v-model="comment" style="border-bottom: 1px solid #ccc; width: 45vw" @keydown.enter="submitComment" />
          <v-btn fab small dark @click="submitComment" type="submit">
            <v-icon>mdi-plus</v-icon>
          </v-btn>
        </div>
      </div>
      <div :v-if="reply" v-for="(re, i) in reply" :key="i" style="margin-top: 5px; display:block;">
        <div v-if="isInput[i] === 0">
          <p style="margin: 10px 0px;">
            <font size="3">
                {{ users[i] }}
            </font>
          </p>
          <span>
            <font size="3">{{ re.content }}</font>
          </span>
          <p v-if="username === users[i]" style="display:inline-block; float: right;">
            <v-icon @click="click(i)" style="margin-right: 15px">mdi-pencil-plus</v-icon>
              <v-icon @click="deleteComment(i, re.cmid)">mdi-close</v-icon>
          </p>
          <br />
          <br />
          <hr style="opacity: 0.3;" />
          <br />
        </div>
        <div v-else>
          <p style="margin: 10px 0px;">
            <font size="3">
                {{ users[i] }}
            </font>
          </p>
          <div>
            <input id="comment-input" v-model="re.content" @keydown.enter="updateComment(i, re.cmid, re.content)"  />
          <p v-if="username === users[i]" style="display:inline-block;">
              <v-icon @click="updateComment(i, re.cmid, re.content)">
                mdi-check
              </v-icon>
          </p>
          </div>
          <hr style="opacity: 0.3;" />
        </div>
      </div>
      <div style="text-align: center; margin-top: 15px;">
        <button v-for="pageNm in pageNms" :key="pageNm" @click="search(pageNm)">
          <span style="margin-right:10px;">{{ pageNm }}</span>
        </button>
      </div>
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
      pageNms: [],
      imagepath: [],
      window: {
        width: 0,
        height: 0
      }
    };
  },
  created() {
    window.addEventListener("resize", this.handleResize);
    this.handleResize();
    (this.username = this.$store.state.username),
      (this.rid = Number(this.$route.params.rid));
    http.get("/boardrecipe/" + this.rid).then(res => {
      // console.log(res.data)
      this.boardRecipe = res.data;
      if (this.boardRecipe.filelist.length != 0) {
        console.log(this.boardRecipe.filelist[0]);
        for (let i = 0; i < this.boardRecipe.filelist.length; i++) {
          this.imagepath[i] = require("C:/image/" +
            this.boardRecipe.filelist[i]);
        }
      }

      this.$store
        .dispatch(Constant.GET_REPLYBOARDRECIPE, {
          rid: this.$route.params.rid,
          pageNm: 1
        })
        .then(() => {
          this.reply = {
            ...this.$store.state.reply
          };
          this.users = {
            ...this.$store.state.users
          };
          for (let i = 0; i < this.reply.length; ++i) {
            this.isInput.push(0);
            // this.likebycomments.push(0); this.$store
            // .dispatch(Constant.GET_LIKEBYUSERANDBOARDRECIPECOMMENTS, {     username:
            // this.$store.state.username,     cmid: this.reply[i].cmid   })   .then(res =>
            // {     let list = [...this.isLike];     if (res.data.object == null)
            // list.splice(i, 1, false);     else list.splice(i, 1, true);     this.isLike =
            // list;   }); this.$store   .dispatch(Constant.GET_LIKEBYBOARDRECIPECOMMENTS, {
            // cmid: this.reply[i].cmid   })   .then(res => {     let list =
            // [...this.likebycomments];     list.splice(i, 1,
            // this.$store.state.likebycomments);     this.likebycomments = list;   });
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
    handleResize() {
      this.window.width = window.innerWidth;
      this.window.height = window.innerHeight;
      console.log(typeof this.window.height);
      this.window.height = this.window.height + "px";
      this.window.height = "calc(" + this.window.height + " - 4rem)";

      console.log(this.window);
    },

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
    delete_board(boardid) {
      http
        .delete("/boardrecipe/" + boardid)
        .then(response => {
          this.$router.push({ name: "BoardRecipeList" });
        })
        .catch(error => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },
    update_board(boardid) {
      this.$router.push({
        name: "BoardRecipeUpdate",
        params: {
          rid: boardid
        }
      });
    },
    go_to_list() {
      this.$router.push({ name: "BoardRecipeList" });
    },
    // clickLikeComments(i) {   console.log(this.reply[i]);   if (this.isLike[i] ==
    // false) {     this.$store .dispatch(Constant.ADD_BOARDRECIPECOMMENTSLIKE, {
    // cmid: this.reply[i].cmid,         username: this.$store.state.username
    // }) .then(() => {         let list = [...this.isLike];         list.splice(i,
    // 1, true);         this.isLike = list;         let list2 =
    // [...this.likebycomments];         list2.splice(i, 1, this.likebycomments[i] +
    // 1);         this.likebycomments = list2;       });   } else {     this.$store
    // .dispatch(Constant.REMOVE_BOARDRECIPECOMMENTSLIKE, {         cmid:
    // this.reply[i].cmid,         username: this.$store.state.username       })
    // .then(() => {         let list = [...this.isLike];         0; list.splice(i,
    // 1, false);         this.isLike = list;         let list2 =
    // [...this.likebycomments];         list2.splice(i, 1, this.likebycomments[i] -
    // 1);         this.likebycomments = list2;       });   } },
    search(pageNm) {
      this.$store
        .dispatch(Constant.GET_REPLYBOARDRECIPE, {
          rid: this.$route.params.rid,
          pageNm: pageNm
        })
        .then(() => {
          this.reply = {
            ...this.$store.state.reply
          };
          this.users = {
            ...this.$store.state.users
          };
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
  },
  destroyed() {
    window.removeEventListener("resize", this.handleResize);
  }
};
</script>

<style scoped>
#boardrecipe-header {
  background: linear-gradient(rgba(0, 0, 0, 0.5)),
    url("../../assets/images/image7.jpg") no-repeat;
  background-size: 100%;
  height: 60vh;
  background-position-y: 30%;
  color: white;
}
#boardrecipe-title {
  margin: 0 0 0 15vw;
  display: inline;
  position: relative;
  float: left;
  top: 35vmin;
  font-size: 11vmin;
  font-family: "BBTreeGB";
}
#boardrecipe-username {
  margin: 0 0 0 2rem;
  display: inline;
  position: relative;
  float: left;
  top: 40vh;
  font-family: "BBTreeGB";
}
#boardrecipe-date {
  display: inline;
  bottom: 0;
  float: right;
  text-align: right;
  font-family: "BBTreeGL";
}
#boardrecipe-context {
  margin: 5vw 15vw;
  font-size: 1.2rem;
  font-size: 1rem;
  line-height: 1.5rem;
  font-family: "BBTreeGL";
}
#boardrecipe-footer {
  margin: 0vmax 10vmax;
  padding: 0 1rem;
  padding-bottom: 2rem;
  border-bottom: 1px solid #ccc;
}
.boardrecipe-button {
  font-size: 2rem;
  margin: 0 5px;
}
#boardrecipe-comment-set {
  margin: 0vmax 10vmax;
  padding: 2rem 1rem;
  font-family: "BBTreeGL";
}
#img-contents {
  margin: 5vh 15vw;
}
#comment-input {
  padding: 0;
  width: 93%;
  height: 100%;
  margin-bottom: 15px;
  font-size: 15px;
}
@media (max-width: 700px) {
  #boardrecipe-context {
    margin: 10vw 15vw 15vw;
    font-size: 15px;
  }
  #boardrecipe-header {
    height: 35vh;
    background-size: 200vw;
    background-position-x: 50%;
  }
  #boardrecipe-title {
    margin-top: 4vmin;
    font-size: 8vmin;
  }
  #boardrecipe-username {
    margin: 0 0 0 2vw;
    display: inline;
    float: left;
    top: 40vmin;
    font-size: 3vmin;
  }
}
@media (max-width: 375px) {
  #boardrecipe-footer {
    margin: 0vmax 5vmax;
  }
  #boardrecipe-comment-set {
    margin: 0vmax 1vw;
  }
}
@media (max-width:426px) {
  #boardrecipe-comment-set {
    margin: 0vmax 5vmax;
  }
  #comment-input {
    width: 70%;
  }
}
@media (max-width:768px) {
  #boardrecipe-comment-set {
    margin: 0vmax 10vmax;
  }
}
</style>