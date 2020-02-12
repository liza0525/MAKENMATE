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
  scrapList: [],
  user: "",
  users: [],
  reply: [],
  comment: "",
  email: "",
  cmid: "",
  content: "",
  intro: "",
  likebycocktail: 0,
  isLike: false,
  likebycomments: 0,
  totalPages: 0
};

export default new Vuex.Store({
  state,
  mutations,
  getters,
  actions
});
