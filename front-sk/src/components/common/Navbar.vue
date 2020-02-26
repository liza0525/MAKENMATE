<template>
  <div>
    <v-app-bar id="navbar" color="#000" data-app hide-on-scroll>
      <router-link :to="{ name: 'Main' }">
        <h1 color="#fff" class="x-sign">MAKE&MATE</h1>
      </router-link>
      <v-spacer></v-spacer>
      <!-- nav-contents-lg -->
      <div id="nav-contents-lg">
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
                <router-link :to="{ name: 'BoardRecipeList' }">레시피 공유</router-link>
              </v-list-item-title>
            </v-list-item>
            <v-list-item>
              <v-list-item-title>
                <router-link :to="{ name: 'Meeting' }">칵테일 파티</router-link>
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
            <div style="margin-left: 15px; margin-top: 15px;">통합 검색</div>
              <Search @searchData="goToTotalSearch" id="search"></Search>
          </v-list>
        </v-menu>
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
                <router-link
                  :to="{
                    name: 'UserProfile',
                    params: {
                      username: this.$store.state.username
                    }
                  }"
                >유저프로필</router-link>
              </v-list-item-title>
            </v-list-item>
            <v-list-item>
              <v-list-item-title>
                <router-link :to="{
                    name: 'UserScrap'
                  }">유저스크랩</router-link>
              </v-list-item-title>
            </v-list-item>
            <v-list-item>
              <v-list-item-title>
                <router-link :to="{ name: 'Logout' }">로그아웃</router-link>
              </v-list-item-title>
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
      <!-- nav-contents-sm -->
      <!-- nav-contents-sm -->
      <div id="nav-contents-sm">
        <!-- search bar -->
        <v-menu offset-y :close-on-content-click="false">
          <template v-slot:activator="{ on }">
            <v-btn icon color="#fff" v-on="on">
              <v-icon>mdi-magnify</v-icon>
            </v-btn>
          </template>
          <v-list>
            <div style="margin-left: 15px; margin-top: 15px">통합 검색</div>
              <Search @searchData="goToTotalSearch" id="search"></Search>
          </v-list>
        </v-menu>
        <!-- hamberger button -->
        <v-app-bar-nav-icon color="#fff" @click.stop="right = !right" />
        <v-navigation-drawer dark v-model="right" app right>
          <v-list dense>
            <v-list-item>
              <div>
                <v-btn text color="#fff">닫기</v-btn>
              </div>
            </v-list-item>
          </v-list>
          <v-list dense>
            <v-list-item>
              <router-link
                :to="{
                  name: 'CocktailList',
                  query: { pageNm: 1, filtered: 'all', searchedFiltered: '' }
                }"
              >
                <v-btn text color="#fff">칵테일 정보</v-btn>
              </router-link>
            </v-list-item>
            <v-list-item>
              <v-menu offset-y bottom>
                <template v-slot:activator="{ on }">
                  <v-btn text color="#fff" v-on="on">게시판 선택</v-btn>
                </template>
                <v-list stlye="width:400px;">
                  <v-list-item>
                    <v-list-item-title>
                      <router-link :to="{ name: 'BoardRecipeList' }">레시피 공유</router-link>
                    </v-list-item-title>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-title>
                      <router-link :to="{ name: 'Meeting' }">칵테일 파티</router-link>
                    </v-list-item-title>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-title>
                      <router-link :to="{ name: 'BoardList' }">자유게시판</router-link>
                    </v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </v-list-item>
            <v-list-item>
              <!-- account dropdown -->
              <v-menu offset-y bottom>
                <template v-slot:activator="{ on }">
                  <v-btn text color="#fff" v-on="on">회원 정보</v-btn>
                </template>
                <v-list v-if="this.username" stlye="width:400px;">
                  <v-list-item>
                    <v-list-item-title>
                      <router-link :to="{ name: 'Mypage' }">마이페이지</router-link>
                    </v-list-item-title>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-title>
                      <router-link
                        :to="{
                          name: 'UserProfile',
                          params: {
                            username: this.$store.state.username
                          }
                        }"
                      >유저프로필</router-link>
                    </v-list-item-title>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-title>
                      <router-link
                        :to="{
                          name: 'UserScrap'
                        }"
                      >유저스크랩</router-link>
                    </v-list-item-title>
                  </v-list-item>
                  <v-list-item>
                    <v-list-item-title>
                      <router-link :to="{ name: 'Logout' }">로그아웃</router-link>
                    </v-list-item-title>
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
            </v-list-item>
          </v-list>
        </v-navigation-drawer>
      </div>
    </v-app-bar>
  </div>
</template>
<script>
const storage = window.sessionStorage;
import Search from "../../components/common/Search.vue";
export default {
  components: {
    Search,
  },
  data() {
    return {
      acc_menus: [],
      drawer: true,
      mini: true,
      right: false
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
  },
  methods: {
    goToTotalSearch(InputValue) {
      this.$store.state.totalSearchKeyword = InputValue
      console.log(InputValue)
      this.$router.push({
        name: "SearchResult",
        params: {
          q: InputValue
        }
      }).catch(err => this.callbackFunc(InputValue))
    },
    callbackFunc(InputValue){
      this.$store.state.totalSearchKeyword = InputValue
    }
  },
};
</script>
<style scoped>
@import url("https://fonts.googleapis.com/css?family=Lobster&display=swap");
@import url("https://fonts.googleapis.com/css?family=Noto+Sans+KR:500&display=swap");
@import url("https://fonts.googleapis.com/css?family=Open+Sans|Yellowtail&display=swap");
.x-sign {
  font-family: "Yellowtail", cursive;
  --interval: 1s;
  display: block;
  text-shadow: 0 0 10px rgba(242, 30, 178, 1), 0 0 20px rgba(242, 30, 178, 1),
    0 0 40px rgba(242, 30, 178, 1), 0 0 80px rgba(242, 30, 178, 1);
  will-change: filter, color;
  filter: saturate(60%);
  /* animation: flicker steps(100) var(--interval) 1s infinite; */
  color: #fff;
  --color1: rgba(242, 30, 178, 1);
  --color2: rgba(242, 30, 178, 1);
  --color3: rgba(242, 30, 178, 1);
  --color4: rgba(242, 30, 178, 1);
}
@keyframes flicker {
  50% {
    color: white;
    filter: saturate(200%) hue-rotate(20deg);
  }
}

#search {
  border-bottom: 1px solid #000;
  width: 230px;
  margin: 0 10px;
  margin-bottom: 20px;
}
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
@media (max-width: 700px) {
  #nav-contents-lg {
    display: none;
  }
}
@media (min-width: 700px) {
  #nav-contents-sm {
    display: none;
  }
}
</style>
