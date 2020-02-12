import Constant from "../Constant";
import http from "../http-common";
export default {
  [Constant.GET_REPLY]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .get("/comments/cocktail/" + payload.cid, {
          params: {
            page: payload.pageNm - 1
          }
        })
        .then(res => {
          store.commit(Constant.GET_REPLY, {
            reply: res.data.comments.content,
            users: res.data.UserArray,
            totalPages: res.data.comments.totalPages
          });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.ADD_REPLY]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .post("/comments/cocktail/" + payload.cid, null, {
          params: {
            username: payload.username,
            content: payload.comment
          }
        })
        .then(() => {
          store.dispatch(Constant.GET_REPLY, {
            cid: payload.cid
          });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.MODIFY_REPLY]: (store, payload) => {
    return new Promise((resolve, reject) => {
      console.log(payload.content);
      http
        .put("/comments/cocktail/" + payload.cmid, null, {
          params: {
            content: payload.content
          }
        })
        .then(() => {
          store.dispatch(Constant.GET_REPLY, {
            cid: payload.cid
          });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.REMOVE_REPLY]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .delete("/comments/cocktail/" + payload.cmid)
        .then(() => {
          store.dispatch(Constant.GET_REPLY, {
            cid: payload.cid
          });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.GET_REPLYBOARDRECIPE]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .get("/comments/boardRecipe/" + payload.cid, {
          params: {
            page: payload.pageNm - 1
          }
        })
        .then(res => {
          store.commit(Constant.GET_REPLYBOARDRECIPE, {
            reply: res.data.comments.content,
            users: res.data.UserArray,
            totalPages: res.data.comments.totalPages
          });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.ADD_REPLYBOARDRECIPE]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .post("/comments/boardRecipe/" + payload.cid, null, {
          params: {
            username: payload.username,
            content: payload.comment
          }
        })
        .then(() => {
          store.dispatch(Constant.GET_REPLYBOARDRECIPE, {
            cid: payload.cid
          });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.MODIFY_REPLYBOARDRECIPE]: (store, payload) => {
    return new Promise((resolve, reject) => {
      console.log(payload.content);
      http
        .put("/comments/boardRecipe/" + payload.cmid, null, {
          params: {
            content: payload.content
          }
        })
        .then(() => {
          store.dispatch(Constant.GET_REPLYBOARDRECIPE, {
            cid: payload.cid
          });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.REMOVE_REPLYBOARDRECIPE]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .delete("/comments/boardRecipe/" + payload.cmid)
        .then(() => {
          store.dispatch(Constant.GET_REPLYBOARDRECIPE, {
            cid: payload.cid
          });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.GET_REPLYBOARD]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .get("/comments/board/" + payload.cid, {
          params: {
            page: payload.pageNm - 1
          }
        })
        .then(res => {
          store.commit(Constant.GET_REPLYBOARD, {
            reply: res.data.comments.content,
            users: res.data.UserArray,
            totalPages: res.data.comments.totalPages
          });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.ADD_REPLYBOARD]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .post("/comments/board/" + payload.cid, null, {
          params: {
            username: payload.username,
            content: payload.comment
          }
        })
        .then(() => {
          store.dispatch(Constant.GET_REPLYBOARD, {
            cid: payload.cid
          });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.MODIFY_REPLYBOARD]: (store, payload) => {
    return new Promise((resolve, reject) => {
      console.log(payload.content);
      http
        .put("/comments/board/" + payload.cmid, null, {
          params: {
            content: payload.content
          }
        })
        .then(() => {
          store.dispatch(Constant.GET_REPLYBOARD, {
            cid: payload.cid
          });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.REMOVE_REPLYBOARD]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .delete("/comments/board/" + payload.cmid)
        .then(() => {
          store.dispatch(Constant.GET_REPLYBOARD, {
            cid: payload.cid
          });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  // Board CRUD
  [Constant.GET_BOARDLIST]: store => {
    return new Promise((resolve, reject) => {
      http
        .get("/board")
        .then(res => {
          store.commit(Constant.GET_BOARDLIST, { boardList: res.data.object });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.GET_BOARD]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .get("/board/" + payload.bid)
        .then(res => {
          store.commit(Constant.GET_BOARD, { board: res.data.object });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.GET_BOARDTITLE]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .get("/boardTitleSearch/" + payload.title)
        .then(res => {
          store.commit(Constant.GET_BOARDLIST, { boardList: res.data.object });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.GET_BOARDAUTHOR]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .get("/boardAuthorSearch/" + payload.author)
        .then(res => {
          store.commit(Constant.GET_BOARDLIST, { boardList: res.data.object });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.ADD_BOARD]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .post("/board", payload.board)
        .then(res => {
          store.commit(Constant.GET_BOARD, { board: res.data.object });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.MODIFY_BOARD]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .put("/board/" + payload.bid)
        .then(res => {
          store.commit(Constant.GET_BOARD, { board: res.data.object });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.REMOVE_BOARD]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .delete("/board/" + payload.bid)
        .then(res => {
          store.dispatch(Constant.GET_BOARDLIST);
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  // 스크랩 CRUD
  [Constant.GET_SCRAPLIST]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .get("/user/scrap/", {
          params: {
            username: payload.username
          }
        })
        .then(res => {
          store.commit(Constant.GET_SCRAPLIST, { scrapList: res.data.object });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.ADD_SCRAP]: (store, payload) => {
    return new Promise((resolve, reject) => {
      // recipe 공유 게시판만 추가
      http
        .post("/user/scrap/", null, {
          params: {
            username: payload.username,
            rid: payload.rid
          }
        })
        .then(res => {
          console.log("add success");
          // 추가하고 다시 게시판 목록으로
          store.dispatch(Constant.GET_BOARDLIST);
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.REMOVE_SCRAP]: (store, payload) => {
    return new Promise((resolve, reject) => {
      // 스크랩 TABLE의 id
      http
        .delete("/user/scrap/", {
          params: {
            username: payload.username,
            rid: payload.rid
          }
        })
        .then(res => {
          let list = [...res.data.object];
          store.scrapList = list;
          store.commit(Constant.GET_SCRAPLIST, {
            scrapList: res.data.object
          });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  // Cocktail READ
  [Constant.GET_COCKTAIL]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .get("/cocktail/detail/" + payload.cid)
        .then(res => {
          store.commit(Constant.GET_COCKTAIL, { cocktail: res.data.object });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.GET_COCKTAILLIST]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .get("/cocktail/list", {
          params: {
            page: payload.pageNm,
            filtered: payload.filtered,
            searchedFiltered: payload.searchedFiltered
          }
        })
        .then(res => {
          console.log(res);
          store.commit(Constant.GET_COCKTAILLIST, {
            cocktailList: res.data.content,
            totalPages: res.data.totalPages
          });
          resolve(res);
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  // Cocktail 좋아요
  [Constant.GET_COCKTAILLIKE]: (store, payload) => {
    console.log(payload);
    return new Promise((resolve, reject) => {
      http
        .get("/cocktail/getlikebyuser", {
          params: { username: payload.username }
        })
        .then(res => {
          console.log(res.data.object);
          store.commit(Constant.GET_COCKTAILLIKE, {
            cocktailList: res.data.object
          });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.GET_LIKEBYCOCKTAIL]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .get("/cocktail/getlikebycocktail", {
          params: { cid: payload.cid }
        })
        .then(res => {
          store.commit(Constant.GET_LIKEBYCOCKTAIL, {
            likebycocktail: res.data.object
          });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.GET_LIKEBYUSERANDCOCKTAIL]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .get("/cocktail/getlikebyuserandcocktail", {
          params: {
            username: payload.username,
            cid: payload.cid
          }
        })
        .then(res => {
          store.commit(Constant.GET_LIKEBYUSERANDCOCKTAIL, {
            isLike: res.data.object
          });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.ADD_COCKTAILLIKE]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .post("/cocktail/like", null, {
          params: {
            username: payload.username,
            cid: payload.cid
          }
        })
        .then(() => {
          store.commit(Constant.ADD_COCKTAILLIKE);
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.REMOVE_COCKTAILLIKE]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .delete("/cocktail/like", {
          params: {
            username: payload.username,
            cid: payload.cid
          }
        })
        .then(() => {
          store.commit(Constant.REMOVE_COCKTAILLIKE);
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  // User CRUD
  [Constant.GET_USERINFO]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .get("/user/info/" + payload.username)
        .then(res => {
          store.commit(Constant.GET_USERINFO, { user: res.data.object });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.MODIFY_USERINFO]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .put("/user/info", payload.user)
        .then(res => {
          store.commit(Constant.MODIFY_USERINFO, { user: res.data.object });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.REMOVE_USER]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .delete("/user/withdraw", {
          params: {
            email: payload.email,
            password: payload.password
          }
        })
        .then(res => {
          console.log(res.data.data);
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.MODIFY_USERINTRO]: (store, payload) => {
    return new Promise((resolve, reject) => {
      console.log(payload);
      http
        .put("/user/updateProfile", null, {
          params: {
            username: payload.username,
            intro: payload.intro
          }
        })
        .then(res => {
          store.commit(Constant.MODIFY_USERINTRO, {
            intro: res.data.object.intro
          });
          console.log(res.data.data);
        });
    });
  },
  [Constant.ADD_COCKTAILCOMMENTSLIKE]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .post("/comments/like", null, {
          params: {
            username: payload.username,
            cmid: payload.cmid
          }
        })
        .then(() => {
          store.commit(Constant.ADD_COCKTAILCOMMENTSLIKE);
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.REMOVE_COCKTAILCOMMENTSLIKE]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .delete("/comments/like", {
          params: {
            username: payload.username,
            cmid: payload.cmid
          }
        })
        .then(() => {
          store.commit(Constant.REMOVE_COCKTAILCOMMENTSLIKE);
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.GET_LIKEBYCOCKTAILCOMMENTS]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .get("/comments/getlikebycomments", {
          params: { cmid: payload.cmid }
        })
        .then(res => {
          store.commit(Constant.GET_LIKEBYCOCKTAILCOMMENTS, {
            likebycomments: res.data.object
          });
          resolve();
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  },
  [Constant.GET_LIKEBYUSERANDCOCKTAILCOMMENTS]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .get("/comments/getlikebyuserandcomments", {
          params: {
            username: payload.username,
            cmid: payload.cmid
          }
        })
        .then(res => {
          resolve(res);
        })
        .catch(exp => {
          console.log(exp);
          reject();
        });
    });
  }
};
