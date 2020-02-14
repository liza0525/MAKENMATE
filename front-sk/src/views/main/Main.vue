<template>
  <div>
    <div class="animated infinite pulse" id="scroll-down">scroll down ↓</div>
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
            <v-col cols="12" md="6" id="main-subtitle">
              <br />
              <span style="background-color: #FFBB00;">칵테일</span>을 사랑하는
              <br />모든 이들과 함께하는 Cocktail
            </v-col>
            <v-col cols="12" md="6" class="main-img" id="page2"></v-col>
          </v-row>
      </div>
      <!-- page 3 -->
      <div class="section main-img" id="page3">
        <p id="main-contents">
          술과 술이 만나 칵테일이 되듯이
          <br />
          우리의 레시피를 모아 더 <span style="background-color: #000;">특별한 순간</span>을 만들고자 합니다.
          <br />
          이러한 가치를 아는 사람들이 모여 함께 한다면 
          <br />
          더욱 <span style="background-color: #000;">특별한 하루</span>를 보내지 않을까요..?
          <br />
        </p>
      </div>
      <!-- page 4 -->
      <div class="section">
        <div class="main-img" id="page4">
          <h1 id="info-title">Developers</h1>
          <div>
            <v-row id="info-members">
              <v-col v-for="person in people" lg="3" sm="6" xs="6" :key="person.id">
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
        <div v-for="person in people" class="modal info-modal" :key="person.id">
          <div class="modal-content">
            <p class="close">&times;</p>
            <v-row>
              <v-col cols="6">
                <img :src="person.profileUrl" />
                <div style="display:flex; justfy-content:space-around;font-size: 2rem;">
                  <i class="fab fa-instagram"></i>
                  <i class="fab fa-github"></i>
                  <i class="far fa-envelope"></i>
                </div>
              </v-col>
              <v-col cols="6">
                <h1 class="info-member-name">{{ person.name }}</h1>
                <p>{{ person.biography }}</p>
              </v-col>
            </v-row>
          </div>
        </div>
      </div>
    </full-page>
  </div>
</template>

<script>
// import $ from 'jquery';
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
          biography:
            "므재는 소주를 좋아해요. 사실 소주를 맥주에 말아먹는 걸 좋아한다 봐야지요ㅎㅎ",
          profileUrl: require("../../assets/images/kwj.jpg")
        },
        {
          name: "손현희",
          biography: "혀니는 칵테일 덕후예요. 마스터 하는 것이 취미지요.★",
          profileUrl: require("../../assets/images/shh.jpg")
        },
        {
          name: "이근성",
          biography:
            "근성쓰는 보드카를 마셔요. 매일 현희를 코칭하고 있어욬ㅋㅋ",
          profileUrl: require("../../assets/images/lgs.jpg")
        },
        {
          name: "정윤영",
          biography:
            "쩡뉴는 맥주를 먹어요. 운동은 많이 먹기 위해 하는 거죠...!",
          profileUrl: require("../../assets/images/cyy.jpg")
        }
      ]
    };
  },
  methods: {
    infoPopUp() {
      let modal = document.getElementsByClassName("info-modal");
      let btn = document.getElementsByClassName("info-btn");
      let close = document.getElementsByClassName("close");

      for (let i = 0; i < this.people.length; i++) {
        btn[i].onclick = function(e) {
          modal[i].style.display = "block";
        };
        close[i].onclick = function(e) {
          modal[i].style.display = "none";
        };
      }
    },
  },
  created() {
    // $(window).addEventListener("scroll", function(){
    //   console.log(1)
    // })
  },
  mounted() {
    this.infoPopUp();
  },
  beforeDestroy() {
    window.removeEventListener("close", this.infoPopUp);
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css?family=Playfair+Display:700i&display=swap");
@import url("https://fonts.googleapis.com/css?family=Indie+Flower&display=swap");
@import url("https://fonts.googleapis.com/css?family=Song+Myung&display=swap");
.section {
  background: black;
  color: white;
}
#scroll-down {
  position: fixed;
  text-align: center;
  margin-left: 45vmax;
  margin-right: 45vmax;
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
  height: 45rem;
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
#main-subtitle {
  position: relative;
  padding: 2rem;
  top: 20rem;
  float: right;
  text-align: right;
  vertical-align: middle;
  font-size: 5vmin;
  font-family: "Song Myung", serif;
}
#main-contents {
  position: relative;
  padding: 10vmin;
  font-size: 3vmin;
  font-family: "Song Myung", serif;
}
#info-title {
  position: relative;
  padding-bottom: 4vmax;
  font-family: "Playfair Display";
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
  filter: brightness(130%)
}
.info-members-name {
  margin: 10px 0;
  letter-spacing: 5px;
  font-size: 1vw;
}
.modal {
  display: none;
  position: fixed;
  z-index: 2;
  left: 0;
  top: 75%;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgb(0, 0, 0, 0.7);
}
.modal-content {
  color: black;
  background-color: #fefefe;
  margin: 7vw auto;
  padding: 1vw;
  width: 50vw;
}
::-webkit-scrollbar {
  display: none;
}
.close {
  /* display: inline; */
  float: right;
  font-size: 25px;
  cursor: pointer;
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
      2px 2px at 20px 30px,
      #eee,
      rgba(0, 0, 0, 0)
    ),
    radial-gradient(2px 2px at 40px 70px, #fff, rgba(0, 0, 0, 0)),
    radial-gradient(2px 2px at 50px 160px, #ddd, rgba(0, 0, 0, 0)),
    radial-gradient(2px 2px at 90px 40px, #fff, rgba(0, 0, 0, 0)),
    radial-gradient(2px 2px at 130px 80px, #fff, rgba(0, 0, 0, 0)),
    radial-gradient(2px 2px at 160px 120px, #ddd, rgba(0, 0, 0, 0));
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
  transform: rotate(200deg);
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
</style>
