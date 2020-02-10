export default {
  // 댓글
  GET_REPLY: "getReply",
  ADD_REPLY: "addBoardReply",
  MODIFY_REPLY: "modifyReply",
  REMOVE_REPLY: "removeReply",
  // Board 기본 store
  GET_BOARDLIST: "getBoardList", // 전체 LIST
  GET_BOARD: "getBoard", // 상세 정보 페이지
  GET_BOARDTITLE: "getBoardTitle", // 글 제목 검색
  GET_BOARDAUTHOR: "getBoardAuthor", // 사용자 ID 검색
  ADD_BOARD: "addBoard",
  MODIFY_BOARD: "modifyBoard",
  REMOVE_BOARD: "removeBoard",
  // 스크랩 목록 (레시피 공유)
  GET_SCRAPLIST: "getScrapList",
  ADD_SCRAP: "addScrap",
  REMOVE_SCRAP: "removeScrap",
  // 칵테일 기본 store
  GET_COCKTAILLIST: "getCocktailList", // 전체 LIST
  GET_COCKTAIL: "getCocktail", // 상세 정보 페이지
  // 좋아요
  GET_COCKTAILLIKE: "getCocktailLike", // 좋아요한 칵테일 가져오기
  ADD_COCKTAILLIKE: "addCocktailLike", // 내가 좋아하는 술 추가
  REMOVE_COCKTAILLIKE: "removeCocktailLike", // 내가 좋아하는 술 삭제
  GET_LIKEBYCOCKTAIL: "getlikebycocktail", // 칵테일 좋아요 수 가져오기
  // 유저 관리
  USERNAME: "Username",
  LOGIN: "Login",
  SIGNUP: "SignUp",
  GET_USERINFO: "getUserInfo", // 유저 프로필 불러오기
  MODIFY_USERINFO: "modifyUserInfo", // 유저 정보 수정
  REMOVE_USER: "removeUserInfo" // 유저 정보 삭제
};
