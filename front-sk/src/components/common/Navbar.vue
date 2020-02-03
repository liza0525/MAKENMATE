<template>
  <v-app-bar id="navbar" color="#000" data-app hide-on-scroll>
    <router-link to="/">
      <h1 id="nav-logo" color="#fff">Cocktail</h1>
    </router-link>
    <v-spacer></v-spacer>
    <div id="nav-contents">
      <v-btn text color="#fff" href="/#/cocktail/list/1">칵테일 정보</v-btn>
      <!-- board dropdown -->
      <v-menu offset-y bottom>
        <template v-slot:activator="{ on }">
          <v-btn text color="#fff" v-on="on">게시판 선택</v-btn>
        </template>
        <v-list>
          <v-list-item v-for="(board, index) in boards" :key="index">
            <v-list-item-title>{{ board.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <!-- search bar -->
      <v-menu offset-y :close-on-content-click="false">
        <template v-slot:activator="{ on }">
          <v-btn icon color="#fff" v-on="on">
            <v-icon>mdi-magnify</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-row style="margin: 0 0.5rem 0 0.5rem;">
            <v-col cols="9">
              <v-text-field label="칵테일에 관한 모든 검색" placeholder="검색어 입력"></v-text-field>
            </v-col>
            <v-col cols="2">
              <v-btn icon>
                <v-icon>mdi-magnify</v-icon>
              </v-btn>
            </v-col>
          </v-row>
        </v-list>
      </v-menu>

      <!-- alert icon -->
      <v-btn icon color="#fff">
        <v-icon>mdi-bell</v-icon>
      </v-btn>
      <!-- account dropdown -->
      <v-menu offset-y bottom>
        <template v-slot:activator="{ on }">
          <v-btn icon color="#fff" v-on="on" @click="check()">
            <v-icon>mdi-account-circle</v-icon>
          </v-btn>
        </template>
        <v-list v-if="this.username" stlye="width:400px;">
          <v-list-item>
            <a href="/#/user/mypage">
              <v-list-item-title>마이페이지</v-list-item-title>
            </a>
          </v-list-item>
          <v-list-item>
            <a href="/#">
              <v-list-item-title>스크랩 목록</v-list-item-title>
            </a>
          </v-list-item>
          <v-list-item>
            <a href="/#/logout">
              <v-list-item-title>로그아웃</v-list-item-title>
            </a>
          </v-list-item>
        </v-list>
        <v-list v-else>
          <v-list-item>
            <a href="/#/login">
              <v-list-item-title>로그인</v-list-item-title>
            </a>
          </v-list-item>
          <v-list-item>
            <a href="/#/user/join">
              <v-list-item-title>회원가입</v-list-item-title>
            </a>
          </v-list-item>
        </v-list>
      </v-menu>
    </div>
  </v-app-bar>
</template>
<script>
const storage = window.sessionStorage;
export default {
  data() {
    return {
      boards: [
        { title: "레시피 공유" },
        { title: "칵테일 파티" },
        { title: "자유 게시판" }
      ],
      acc_menus: [],
      drawer: true,
      mini: true
    };
  },
  created() {
    this.$store.state.username = storage.getItem("login_username");
    this.username = this.$store.state.username;
  },
  computed: {
    username() {
      return this.$store.state.username;
    }
  },
  methods: {
    check() {
      console.log(storage.getItem("Authorization"));
    }
  }
};
</script>
<style>
@import url("https://fonts.googleapis.com/css?family=Lobster&display=swap");
@import url("https://fonts.googleapis.com/css?family=Noto+Sans+KR:500&display=swap");
#navbar {
  top: 0px;
  left: 0px;
  height: 50px;
  line-height: 10px;
  position: fixed;
  z-index: 10;
  width: 100%;
  margin: 0px !important;
}
#nav-logo {
  display: inline-flex;
  float: left;
  line-height: 50px;
  margin: 0;
  padding: 0 20px;
  font-family: "Lobster", cursive;
  color: white;
}
</style>
