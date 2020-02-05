import Constant from "../Constant";
import http from "../http-common";
export default {
  // 댓글 id를 어떻게 백엔드로 보낼지 고민 필요~~
  //   [Constant.GET_REPLY]: (store, payload) => {
  //     http
  //       .get("/reply", {
  //         params: {
  //           id: payload.id
  //           // 유저인지 칵테일인지 게시판인지
  //           // 구분 필요함
  //         }
  //       })
  //       .then(res => {
  //         store.commit(Constant.GET_REPLY, {
  //           reply: res.data.object
  //         });
  //       })
  //       .catch(exp => {
  //     console.log(exp);
  //     reject();
  // });
  //   },
  //   [Constant.ADD_REPLY]: (store, payload) => {
  //     http
  //       .post("/reply", payload.reply, {
  //         params: {
  //           username
  //         }
  //       })
  //       .then(res => {
  //         store.dispatch(Constant.GET_REPLY, {});
  //       }).catch(exp => {
  //     console.log(exp);
  //     reject();
  // });;
  //   },
  //   [Constant.MODIFY_REPLY]: (store, payload) => {
  //     http.put("/reply", payload.reply).then(res => {
  //       store.commit(Constant.GET_REPLY);
  //     }).catch(exp => {
  //     console.log(exp);
  //     reject();
  // });;
  //   },
  //   [Constant.REMOVE_REPLY]: (store, payload) => {
  //     http.delete("/reply", payload.reply).then(res => {
  //       store.commit(Constant.GET_REPLY);
  //     }).catch(exp => {
  //     console.log(exp);
  //     reject();
  // });
  //   },
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
        .get("/user/scrap/" + payload.username)
        .then(res => {
          store.commit(Constant.GET_SCRAPLIST, { scrap: res.data.object });
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
        .post("/user/scrap/" + payload.bid)
        .then(res => {
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
        .delete("/user/scrap/" + payload.sid)
        .then(res => {
          store.dispatch(Constant.GET_SCRAPLIST);
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
          store.commit(Constant.GET_COCKTAILLIST, {
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
  // Cocktail 좋아요
  [Constant.GET_COCKTAILLIKE]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .get("/user/like", payload.username)
        .then(res => {
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
  [Constant.ADD_COCKTAILLIKE]: (store, payload) => {
    return new Promise((resolve, reject) => {
      http
        .post("/user/like", payload.cname, {
          params: {
            username: payload.username
          }
        })
        .then(res => {
          store.dispatch(Constant.GET_COCKTAILLIKE);
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
        .delete("/user/like", payload.username)
        .then(res => {
          store.dispatch(Constant.GET_COCKTAILLIKE);
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
        .get("/user/info", payload.username)
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
  }
};
