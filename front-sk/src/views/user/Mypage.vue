<template>
  <div>
    <div id="user-scrap-header">
      <h1 id="user-scrap-title">{{ user.nickname }} 페이지</h1>
    </div>
    <div class="backgroundcolor">
      <v-row no-gutters>
        <v-col md="5">
          <v-card
            style="margin-right:1%;margin-bottom:10%;width:100%;text-align:center"
          >
            <div
              style="display:inline-block; text-align:center;position:absoulte; margin-top:7%; display:inline-block; overflow: hidden;height:300px; width: 300px; border-radius:300px;"
            >
              <img
                :src="user.image"
                style="position:absoulte; width:100%; height:100%;"
              />
            </div>
            <h1
              class="sansfont"
              style="margin-top:4%;text-align:center;font-size:230%"
            >
              {{ user.nickname }}
            </h1>
            <p class="sansfont" style="text-align:center;padding-bottom:6%;">
              (ID : {{ user.email }})
            </p>
          </v-card>
        </v-col>
        <v-col>
          <v-card class="card">
            <h1 class="sansfont" style="margin-bottom:1%">비밀번호 변경</h1>
            <div class="sansfont">비밀번호를 변경하시겠습니까?</div>
            <br />
            <div style="text-align:center;">
              <div class="router sansfont rot-135">
                <router-link
                  tag="button"
                  v-bind:to="{ name: 'UserChangePW' }"
                  class="sansfonts"
                  >비밀번호 변경으로 이동</router-link
                >
              </div>
            </div>
          </v-card>
          <v-card class="card" style="  margin-top:3%; margin-bottom:10%">
            <h1 class="sansfont" style="margin-bottom:2%">회원 탈퇴</h1>
            <div class="sansfont">정말로 탈퇴하시겠습니까?</div>
            <br />
            <div style="text-align:center;">
              <div class="router2 sansfont rot-135">
                <router-link
                  tag="button"
                  v-bind:to="{ name: 'UserWithdraw' }"
                  class="btn--text"
                  >회원 탈퇴로 이동</router-link
                >
              </div>
            </div>
          </v-card>
        </v-col>
      </v-row>
    </div>
  </div>
</template>
<script>
import Constant from "../../Constant";
export default {
  data: () => {
    return {
      user: {
        email: "",
        nickname: "",
        image: ""
      },
      wH: 0
    };
  },
  mounted() {
    // this.user.email = this.$router.params.user.email;
    // this.user.nickname = this.$router.params.user.nickname;
    // this.user.image = this.$router.params.user.image;
    console.log(this.$store.state.username);
    this.$store
      .dispatch(Constant.GET_USERINFO, { username: this.$store.state.username })
      .then(() => {
        this.user = { ...this.$store.state.user };
        if (this.user.image === null)
          this.user.image = require(`../../../../images/default.png`);
        console.log(this.user);
      });
  }
};
</script>
<style scoped>
@import url("https://fonts.googleapis.com/css?family=Song+Myung|Stylish&display=swap");
@font-face {
  font-family: "MapoFlowerIsland";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/MapoFlowerIslandA.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: "MapoGoldenPier";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/MapoGoldenPierA.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: "GyeonggiBatang";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/GyeonggiBatang.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
@import url("https://fonts.googleapis.com/css?family=Jua&display=swap");
carousel-3d {
  padding: 20px;
}
@font-face {
  font-family: "국립박물관문화재단클래식B";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_twelve@1.0/국립박물관문화재단클래식B.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
html,
body {
  width: 100%;
  height: 100vh;
  margin: 0;
  padding: 0;
}

* html #app {
  height: 100%;
}
#user-scrap-header {
  background: linear-gradient(rgba(0, 0, 0, 0.5)),
    url("../../assets/images/image6.jpg") no-repeat;
  background-size: 100%;
  height: 50vh;
  background-position-y: 30%;
  color: white;
}
#user-scrap-title {
  margin: 0 0 0 15vw;
  display: inline;
  position: relative;
  float: left;
  top: 30vmin;
  font-size: 11vmin;
  font-family: "BBTreeGB";
}

.router-link-active {
  color: white;
  background-color: red;
}

.router-link-exact-active {
  color: white;
  background-color: blue;
}

.router {
  width: 50%;
  display: inline-block;
  font-size: 1.5em;
  margin: 0.5em;
  padding: 0.5em;
  border-radius: 5px;
  transition: all 0.5s;
  filter: hue-rotate(0deg);
  color: #ffffff;
  text-decoration: none;
  background: rgba(73, 155, 234, 1);
  background: -moz-linear-gradient(
    45deg,
    rgba(73, 155, 234, 1) 0%,
    rgba(26, 188, 156, 1) 100%
  );
  background: -webkit-gradient(
    left bottom,
    right top,
    color-stop(0%, rgba(73, 155, 234, 1)),
    color-stop(100%, rgba(26, 188, 156, 1))
  );
  background: -webkit-linear-gradient(
    45deg,
    rgba(73, 155, 234, 1) 0%,
    rgba(26, 188, 156, 1) 100%
  );
  background: -o-linear-gradient(
    45deg,
    rgba(73, 155, 234, 1) 0%,
    rgba(26, 188, 156, 1) 100%
  );
  background: -ms-linear-gradient(
    45deg,
    rgba(73, 155, 234, 1) 0%,
    rgba(26, 188, 156, 1) 100%
  );
  background: linear-gradient(
    45deg,
    rgba(73, 155, 234, 1) 0%,
    rgba(26, 188, 156, 1) 100%
  );
  filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#499bea', endColorstr='#1abc9c', GradientType=1 );
}

.router2 {
  width: 50%;
  display: inline-block;
  font-size: 1.5em;
  margin: 0.5em;
  padding: 0.5em;
  border-radius: 5px;
  transition: all 0.5s;
  filter: hue-rotate(0deg);
  color: #ffffff;
  text-decoration: none;
  background: rgba(231, 72, 234, 1);
  background: -moz-linear-gradient(
    45deg,
    rgba(231, 72, 234, 1) 0%,
    rgba(75, 26, 188, 1) 100%
  );
  background: -webkit-gradient(
    left bottom,
    right top,
    color-stop(0%, rgba(231, 72, 234, 1)),
    color-stop(100%, rgba(75, 26, 188, 1))
  );
  background: -webkit-linear-gradient(
    45deg,
    rgba(231, 72, 234, 1) 0%,
    rgba(75, 26, 188, 1) 100%
  );
  background: -o-linear-gradient(
    45deg,
    rgba(231, 72, 234, 1) 0%,
    rgba(75, 26, 188, 1) 100%
  );
  background: -ms-linear-gradient(
    45deg,
    rgba(231, 72, 234, 1) 0%,
    rgba(75, 26, 188, 1) 100%
  );
  background: linear-gradient(
    45deg,
    rgba(231, 72, 234, 1) 0%,
    rgba(75, 26, 188, 1) 100%
  );
  filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#e748ea', endColorstr='#4b1abc', GradientType=1 );
}

.rot-135:hover {
  filter: hue-rotate(135deg);
}

.card {
  padding: 5%;
  margin-left: 4%;
}
.title {
  font-size: 4vmax;
  text-align: center;
  margin-bottom: 5%;
  margin-top: 2.5%;
}

.backgroundcolor {
  background-color: #ffffff;
  padding-top: 3%;
  padding-left: 10%;
  padding-right: 10%;
}
.sansfont {
  /* font-family: "MapoFlowerIsland"; */
  /* font-family: "MapoGoldenPier"; */
  font-family: "GyeonggiBatang";
  /* font-family: "Jua", sans-serif; */
  /* font-family: "Malgun Gothic"; */
}

@media (max-width: 700px) {
  #user-scrap-header {
    height: 35vh;
    background-size: 200vw;
    background-position-x: 50%;
  }
  #user-scrap-title {
    margin-top: 4vmin;
    font-size: 8vmin;
  }
}
</style>
