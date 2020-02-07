import Constant from "../Constant";

async function loopArray(array) {
  array.forEach(element => {
    if (element.image != "") {
      element.image = require(`../../../images/${element.cid}.jpg`);
    } else {
      element.image = require(`../../../images/default.png`);
    }
  });
}

export default {
  // 댓글
  [Constant.GET_REPLY]: (state, payload) => {
    state.reply = payload.reply;
    state.users = payload.users;
  },
  [Constant.ADD_REPLY]: (state, payload) => {},
  // 게시판
  [Constant.GET_BOARDLIST]: (state, payload) => {
    state.boardList = payload.boardList;
  },
  [Constant.GET_BOARD]: (state, payload) => {
    state.board = payload.board;
  },
  [Constant.MODIFY_BOARD]: (state, payload) => {
    state.board.title = payload.board.title;
    state.board.contents = payload.board.contents;
    state.board.regdate = payload.board.regdate;
  },
  // 스크랩
  [Constant.GET_SCRAPLIST]: (state, payload) => {
    state.scrapList = payload.scrapList;
  },
  [Constant.ADD_SCRAP]: (state, payload) => {},
  // 칵테일
  [Constant.GET_COCKTAILLIST]: (state, payload) => {
    payload.cocktailList.forEach(element => {
      if (element.image != "") {
        element.image = require(`../../../images/${element.cid}.jpg`);
      } else {
        element.image = require(`../../../images/default.png`);
      }
    });
    state.cocktailList = payload.cocktailList;
    state.totalPages = payload.totalPages;
  },
  [Constant.GET_COCKTAIL]: (state, payload) => {
    state.cocktail = payload.cocktail;
  },
  // 좋아요
  [Constant.GET_COCKTAILLIKE]: (state, payload) => {
    // 좋아요한 칵테일 가져오기
    state.cocktailList = payload.cocktailList;
  },
  //유저
  [Constant.GET_USERINFO]: (state, payload) => {
    state.user = payload.user;
  },
  [Constant.MODIFY_USERINFO]: (state, payload) => {
    state.user.password = payload.user.password;
    state.user.nickname = payload.user.nickname;
    state.user.image = payload.user.image;
    state.user.intro = payload.user.intro;
  }
};
