import Vue from "vue";
import Vuex from "vuex";
import getters from "./getters";
import actions from "./actions";
import mutations from "./mutations";

Vue.use(Vuex);

const state = {
  useremail: "",
  username: "",
  cocktail: "",
  coctktailList: [],
  board: "",
  boardList: [],
  boardRecipeList: [],
  scrapList: [],
  user: "",
  users: [],
  reply: [],
  comment: "",
  email: "",
  cmid: "",
  content: "",
  likebycocktail: 0,
  isLike: false,
  likebycomments: 0,
  likebyboardrecipe: 0,
  likebyboard: 0
};

export default new Vuex.Store({
  state,
  mutations,
  getters,
  actions
});
