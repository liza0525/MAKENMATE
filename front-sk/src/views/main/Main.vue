<template>
  <div>
    <div>
      <div class="animated infinite pulse" id="scroll-down" style="width: 100%;">scroll down ↓</div>
    </div>
    <full-page ref="fullpage" :options="options" id="fullpage">
      <!-- page 1 -->
      <div class="section main-img" id="page1">
        <div class="stars"></div>
        <div class="stars"></div>
        <div class="stars"></div>
        <div class="stars"></div>
        <div class="stars"></div>
        <div class="scroling-intro-wrap"></div>
        <h1 class="animated flipInX delay-1s" id="main-title">Daily Cocktail Party</h1>
      </div>
      <!-- page 2 -->
      <div class="section">
        <v-row>
          <v-col class="main-subtitle" id="main-st-1">
            <span style="background-color: #FFBB00;">칵테일</span>을 사랑하는
            <br />모든 이들과 함께하는 MAKE&MATE
          </v-col>
          <v-col cols="12" sm="6" class="main-img" id="page2">
            <div class="main-subtitle" id="main-st-2">
              <span style="background-color: #FFBB00;">칵테일</span>을 사랑하는
              <br />모든 이들과 함께하는 MAKE&MATE
            </div>
          </v-col>
        </v-row>
      </div>
      <!-- page 3 -->
      <div class="section main-img" id="page3">
        <p id="main-contents">
          술과 술이 만나 칵테일이 되듯이
          <br />우리의 레시피를 모아 더
          <span style="background-color: #000;">특별한 순간</span>을 만들고자
          합니다.
          <br />이러한 가치를 아는 사람들이 모여 함께 한다면
          <br />더욱
          <span style="background-color: #000;">특별한 하루</span>를 보내지
          않을까요..?
          <br />
        </p>
      </div>
      <!-- page 4 -->
      <div class="section">
        <div class="main-img" id="page4">
          <h1 id="info-title">Developers</h1>
          <div>
            <v-row id="info-members">
              <v-col v-for="person in people" cols="6" md="3" :key="person.id">
                <img
                  class="info-members-profile info-btn"
                  @click="infoPopUp"
                  :src="person.profileUrl"
                />
              </v-col>
            </v-row>
          </div>
        </div>
        <!-- modal -->
        <div v-for="person in people" class="modal" :key="person.id">
          <v-card class="modal-content" max-width="344" outlined>
            <p class="close">&times;</p>
            <v-img height="40vh" :src="person.profileUrl"></v-img>
            <v-card-title>
              <h1 class="info-members-name">{{ person.name }}</h1>
              <div style="margin-left: auto; float:right; font-size: 2rem;">
                <a :href="person.instagram" target="_blank">
                  <i v-if="person.instagram != null" class="fab fa-instagram"></i>
                </a>
                <a :href="person.github" target="_blank">
                  <i v-if="person.github != null" class="fab fa-github" style="margin-left: 10px;"></i>
                </a>
                <a :href="`mailto:${person.email}`">
                  <i v-if="person.email != null" class="far fa-envelope" style="margin-left: 10px;"></i>
                </a>
              </div>
            </v-card-title>
            <v-card-text>
              <p style="font-weight: bold;">Role : {{ person.role }}</p>
              <div class="ability-chart">
                Java/Spring
                <v-progress-circular rotate="270" :value="person.java" color="#EC380B"></v-progress-circular>
              </div>
              <div class="ability-chart">
                HTML/CSS/Vue.js
                <v-progress-circular rotate="270" :value="person.vue" color="#F05F3B"></v-progress-circular>
              </div>
              <div class="ability-chart">
                Python/Django
                <v-progress-circular rotate="270" :value="person.python" color="#429F9E"></v-progress-circular>
              </div>
              <div class="ability-chart">
                Server
                <v-progress-circular rotate="2 70" :value="person.server" color="#007872"></v-progress-circular>
              </div>
            </v-card-text>
          </v-card>
        </div>
      </div>
    </full-page>
  </div>
</template>

<script>
const storage = window.sessionStorage;
import http from "../../http-common";
export default {
  data() {
    return {
      controlArrows: true,
      options: {
        // navigation: true,
      },
      people: [
        {
          name: "김우재",
          role: "backend / frontend",
          profileUrl: require("../../assets/images/kwj.jpg"),
          instagram: "http://www.instagram.com",
          github: "https://www.github.com",
          email: "a@naver.com",
          java: 15,
          vue: 20,
          python: 30,
          server: 40
        },
        {
          name: "손현희",
          role: "backend / frontend",
          profileUrl: require("../../assets/images/shh.jpg"),
          instagram: "http://www.instagram.com",
          github: "https://www.github.com",
          email: "a@naver.com",
          java: 15,
          vue: 20,
          python: 30,
          server: 40
        },
        {
          name: "이근성",
          role: "backend / frontend",
          profileUrl: require("../../assets/images/lgs.jpg"),
          instagram: "http://www.instagram.com",
          github: "https://www.github.com",
          email: "a@naver.com",
          java: 15,
          vue: 20,
          python: 30,
          server: 40
        },
        {
          name: "정윤영",
          role: "backend / frontend",
          profileUrl: require("../../assets/images/cyy.jpg"),
          instagram: "http://www.instagram.com",
          github: "https://www.github.com",
          email: "a@naver.com",
          java: 15,
          vue: 20,
          python: 30,
          server: 40
        }
      ]
    };
  },
  methods: {
    infoPopUp() {
      let modal = document.getElementsByClassName("modal");
      let btn = document.getElementsByClassName("info-btn");
      let close = document.getElementsByClassName("close");

      for (let i = 0; i < this.people.length; i++) {
        btn[i].onclick = function(e) {
          modal[i].style.display = "block";
        };
        close[i].onclick = function(e) {
          modal[i].style.display = "none";
        };
        modal[i].onclick = function(e) {
          modal[i].style.display = "none";
        };
      }
    }
  },
  created() {
    // $(window).addEventListener("scroll", function(){
    //   console.log(1)
    // })
  },
  mounted() {
    this.infoPopUp();
    if (
      !(
        this.$route.query.msg == "undefined" ||
        this.$route.query.msg == null ||
        this.$route.query.msg == ""
      )
    ) {
      alert(this.$route.query.msg);
      this.$router.push({
        name: "Join",
        params: { nickname: this.$route.query.nickname }
      });
    } else {
      http
        .post("/user/auth", null, {
          params: {
            token: this.$route.query.token
          }
        })
        .then(res => {
          storage.setItem("Authorization", res.headers["authorization"]);
          storage.setItem("login_username", res.data.object.nickname);
          storage.setItem("login_useremail", res.data.object.email);
          this.$store.state.username = res.data.object.nickname;
          this.$store.state.useremail = res.data.object.email;
          this.$router.push({ name: "Main" });
        })
        .catch(exp => console.log(exp));
    }
  },
  beforeDestroy() {
    window.removeEventListener("close", this.infoPopUp);
  }
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css?family=Playfair+Display:700i&display=swap");
@import url("https://fonts.googleapis.com/css?family=Indie+Flower&display=swap");
.section {
  background: black;
  color: white;
}
#scroll-down {
  position: fixed;
  text-align: center;
  margin-left: auto;
  margin-right: auto;
  bottom: 0px;
  font-size: 150%;
  z-index: 1;
  color: white;
  font-family: "Indie Flower", cursive;
}
.main-img {
  padding: 5vmin 10vmin;
  height: 42rem;
  animation: fadein 2s;
  -moz-animation: fadein 2s; /* Firefox */
  -webkit-animation: fadein 2s; /* Safari and Chrome */
  -o-animation: fadein 2s; /* Opera */
}
#page1 {
  background: url(../../assets/images/image.jpg);
  background-size: cover;
  background-position-x: 50%;
  width: 100%;
}
#page2 {
  display: flex;
  float: right;
  background: url(../../assets/images/image4.jpg) no-repeat;
  background-position-x: 45%;
  height: 100vh;
  width: 50%;
  background-size: cover;
}
#page3 {
  background: url(../../assets/images/image3.jpg);
  background-size: cover;
  background-position-x: 50%;
  width: 100%;
}
#page4 {
  background: url(../../assets/images/image2.jpg);
  background-blend-mode: hue;
  background-size: cover;
  background-position-x: 50%;
  background-position-y: 100%;
  padding-left: 15vmin;
  padding-right: 15vmin;
  width: 100%;
  height: 80%;
}
#main-title {
  position: relative;
  text-align: center;
  font-family: "Playfair Display", serif;
  font-size: 10vmin;
}
.main-subtitle {
  position: relative;
  padding: 2rem;
  float: right;
  text-align: right;
  font-size: 5vmin;
  font-family: "GyeonggiBatang";
}
span {
  padding: 0.3%;
  font-family: "GyeonggiBatang";
  font-weight: bold;
}
#main-contents {
  position: relative;
  padding: 10vmin;
  font-size: 3vmin;
  font-family: "GyeonggiBatang";
}
#info-title {
  position: relative;
  padding: 3vmax 0;
  font-family: "Playfair Display", serif;
  font-size: 7vmin;
}
#info-members {
  position: relative;
  text-align: center;
}
.info-members-profile {
  width: 25vmin;
  height: 25vmin;
  margin-left: auto;
  margin-right: auto;
  object-fit: cover;
  border-radius: 50%;
  cursor: pointer;
  align-content: center;
}
.info-members-profile:hover {
  filter: brightness(130%);
}
.info-members-name {
  display: inline;
  font-size: 2rem;
  font-family: "BBTreeGB";
}
.modal {
  display: none;
  position: fixed;
  z-index: 2;
  left: 0;
  bottom: 0;
  width: 100%;
  overflow: auto;
  background-color: rgb(0, 0, 0, 0.7);
}
.modal-content {
  color: black;
  background-color: #fefefe;
  margin: 12vh auto;
  font-family: "GyeonggiBatang";
}
.close {
  font-size: 25px;
  cursor: pointer;
}
.ability-chart {
  display: inline;
  margin-left: 5px;
}
a {
  color: black;
}
/* fade in 효과 */
@keyframes fadein {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
@-moz-keyframes fadein {
  /* Firefox */
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
@-webkit-keyframes fadein {
  /* Safari and Chrome */
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
@-o-keyframes fadein {
  /* Opera */
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

/* twinkling star */
.stars {
  overflow: hidden;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 1;
}

.stars {
  background-image: radial-gradient(
      2.3px 2.3px at 20px 30px,
      #eee,
      rgba(0, 0, 0, 0)
    ),
    radial-gradient(2px 2px at 40px 70px, #fff, rgba(0, 0, 0, 0)),
    radial-gradient(2.3px 2.3px at 50px 160px, #ddd, rgba(0, 0, 0, 0)),
    radial-gradient(2px 2px at 90px 40px, #fff, rgba(0, 0, 0, 0)),
    radial-gradient(2px 2px at 130px 80px, #fff, rgba(0, 0, 0, 0)),
    radial-gradient(2.3px 2.3px at 160px 120px, #ddd, rgba(0, 0, 0, 0));
  background-repeat: repeat;
  background-size: 200px 200px;
  animation: twinkle 4s infinite;
  opacity: 0;
}

.stars:nth-child(1) {
  background-position: 50% 50%;
  animation-delay: 0s;
  transform: rotate(200deg) scale(0.6);
}
.stars:nth-child(2) {
  background-position: 20% 60%;
  animation-delay: -1s;
}
.stars:nth-child(3) {
  background-position: -20% -30%;
  animation-delay: -2s;
}
.stars:nth-child(4) {
  background-position: 40% -80%;
  animation-delay: -3s;
}
.stars:nth-child(5) {
  background-position: -20% 30%;
  animation-delay: -4s;
}

@keyframes twinkle {
  0% {
    opacity: 0.1;
    animation-timing-function: ease-in;
  }
  85% {
    opacity: 1;
    animation-timing-function: linear;
  }
  100% {
    opacity: 0.1;
  }
}

@media (max-width: 600px) {
  #main-st-1 {
    display: none;
  }

  #main-st-2 {
    display: block;
    top: 35vmin;
    left: 25vmin;
    margin-right: 25vmin;
  }
}
@media (min-width: 600px) {
  #main-st-1 {
    display: block;
    top: 40vh;
  }
  #main-st-2 {
    display: none;
  }
}
</style>
