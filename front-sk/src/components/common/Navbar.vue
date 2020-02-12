<template>
  <v-app-bar id="navbar" color="#000" data-app hide-on-scroll>
    <router-link to="/">
      <h1 id="nav-logo" color="#fff">Cocktail</h1>
    </router-link>
    <v-spacer></v-spacer>
    <div id="nav-contents">
      <router-link
        :to="{
          name: 'CocktailList',
          query: { pageNm: 1, filtered: 'all', searchedFiltered: '' }
        }"
      >
        <v-btn text color="#fff">칵테일 정보</v-btn>
      </router-link>
      <!-- board dropdown -->
      <v-menu offset-y bottom>
        <template v-slot:activator="{ on }">
          <v-btn text color="#fff" v-on="on">게시판 선택</v-btn>
        </template>
        <v-list stlye="width:400px;">
          <v-list-item>
            <v-list-item-title>
              <router-link :to="{ name: 'BoardRecipeList' }"
                >레시피 공유</router-link>
            </v-list-item-title>
          </v-list-item>
          <v-list-item>
            <v-list-item-title>
              <!-- <router-link :to="{ name: 'CocktailParty' }"> -->
              칵테일 파티
              <!-- </router-link> -->
            </v-list-item-title>
          </v-list-item>
          <v-list-item>
            <v-list-item-title>
              <router-link :to="{ name: 'BoardList' }">자유게시판</router-link>
            </v-list-item-title>
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
              <v-text-field
                label="칵테일에 관한 모든 검색"
                placeholder="검색어 입력"
              ></v-text-field>
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
          <v-btn icon color="#fff" v-on="on">
            <v-icon>mdi-account-circle</v-icon>
          </v-btn>
        </template>
        <v-list v-if="this.username" stlye="width:400px;">
          <v-list-item>
            <v-list-item-title>
              <router-link :to="{ name: 'Mypage' }">마이페이지</router-link>
            </v-list-item-title>
          </v-list-item>
          <v-list-item>
            <v-list-item-title>
              <router-link :to="{ name: 'UserProfile' }">
                유저프로필
              </router-link>
            </v-list-item-title>
          </v-list-item>
          <v-list-item>
            <v-list-item-title>
              <router-link :to="{ name: 'UserScrap' }">유저스크랩</router-link>
            </v-list-item-title>
          </v-list-item>
          <v-list-item>
            <a href="/#/logout">
              <v-list-item-title>
                <router-link :to="{ name: 'Logout' }">로그아웃</router-link>
              </v-list-item-title>
            </a>
          </v-list-item>
        </v-list>
        <v-list v-else>
          <v-list-item>
            <v-list-item-title>
              <router-link :to="{ name: 'Login' }">로그인</router-link>
            </v-list-item-title>
          </v-list-item>
          <v-list-item>
            <v-list-item-title>
              <router-link :to="{ name: 'Join' }">회원가입</router-link>
            </v-list-item-title>
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
    username: {
      get() {
        return this.$store.state.username;
      },
      set(val) {
        this.$store.commit("Username", { username: val });
      }
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
