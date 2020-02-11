<template>
  <div>
    <div class="animated infinite pulse" id="scroll-down">▼ scroll down ▼</div>
    <full-page ref="fullpage" :options="options" id="fullpage">
      <div class="section main-img" id="page1">
        <div class="stars"></div>
        <div class="stars"></div>
        <div class="stars"></div>
        <div class="stars"></div>
        <div class="stars"></div>
        <div class="scroling-intro-wrap"></div>
        <h1 class="animated flipInX delay-1s" id="main-title">Daily Cocktail Party</h1>
      </div>
      <div class="section">
        <div>
          <div class="main-img" id="page2"></div>
          <h1 id="main-subtitle">
            대충
            <span style="background-color: rgb(245, 213, 71);">칵테일</span>을 좋아하는
            <br />사람들을 위한 SNS라는 뜻
          </h1>
        </div>
      </div>
      <div class="section main-img" id="page3">
        <p id="main-contents">
          칵테일 맛있고
          <span style="background-color: black;">레시피 공유</span>도 하고 검색하고
          <br />
          <span style="background-color: black;">칵테일 번개 약속</span>도 잡을 수 있다, 뭐 그런...
          <br />그런
          사이트라고 소개하고 싶은데
          <br />소개 글 좀 깔쌈하게 쓸 수 없을까 하
          이놈의 문장력
        </p>
      </div>
      <div class="section">
        <div class="main-img" id="page4">
          <h1 id="info-title">Developers</h1>
          <div>
            <v-row id="info-members">
              <v-col v-for="person in people" cols="3" :key="person.id">
                <div>
                  <img
                    class="info-members-profile info-btn"
                    @click="infoPopUp"
                    :src="person.profileUrl"
                  />
                </div>
                <br />
                <p class="info-members-name">{{ person.familyName }}{{ person.firstName }}</p>
              </v-col>
            </v-row>
          </div>
        </div>
        <!-- modal -->
        <div v-for="person in people" class="modal info-modal" :key="person.id">
          <div class="modal-content">
            <p class="close">&times;</p>
            <v-row>
              <v-col md="6" sm="12">
                <img :src="person.profileUrl" style="width:30%" />
              </v-col>
              <v-col md="6" sm="12">
                <p>
                  {{ person.firstName }}의 자기소개
                  <br />
                  {{ person.biography }}
                </p>
              </v-col>
            </v-row>
          </div>
        </div>
      </div>
    </full-page>
  </div>
</template>

<script>
export default {
  data() {
    return {
      controlArrows: true,
      options: {},
      people: [
        {
          familyName: "김",
          firstName: "우재",
          biography:
            "므재는 소주를 좋아해요. 사실 소주를 맥주에 말아먹는 걸 좋아한다 봐야지요ㅎㅎ",
          profileUrl: require("../../assets/images/kwj.jpg")
        },
        {
          familyName: "손",
          firstName: "현희",
          biography: "혀니는 칵테일 덕후예요. 마스터 하는 것이 취미지요.★",
          profileUrl: require("../../assets/images/shh.jpg")
        },
        {
          familyName: "이",
          firstName: "근성",
          biography:
            "근성쓰는 보드카를 마셔요. 매일 현희를 코칭하고 있어욬ㅋㅋ",
          profileUrl: require("../../assets/images/lgs.jpg")
        },
        {
          familyName: "정",
          firstName: "윤영",
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
    }
  },
  created() {
    window.addEventListener("scroll", this.pageMove);
  },
  mounted() {
    this.infoPopUp();
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.pageMove);
    window.removeEventListener("close", this.infoPopUp);
  }
};
</script>

<style>
@import url("https://fonts.googleapis.com/css?family=Playfair+Display:700i&display=swap");
@import url("https://fonts.googleapis.com/css?family=Indie+Flower&display=swap");
@import url("https://fonts.googleapis.com/css?family=Song+Myung&display=swap");
body {
  background: none !important;
}
#fullpage {
  background-color: black;
  color: white;
}
#scroll-down {
  position: fixed;
  margin: 1rem;
  bottom: 0px;
  left: 45%;
  z-index: 1;
  font-size: 150%;
  color: white;
  font-family: "Indie Flower", cursive;
}
.main-img {
  padding: 0 10rem;
  height: 42rem;
  animation: fadein 2s;
  -moz-animation: fadein 2s; /* Firefox */
  -webkit-animation: fadein 2s; /* Safari and Chrome */
  -o-animation: fadein 2s; /* Opera */
}
#page1 {
  background: url(../../assets/images/image.jpg);
  background-size: cover;
}
#page2 {
  display: flex;
  float: right;
  background: url(../../assets/images/image4.jpg) no-repeat;
  background-position-x: 30%;
  width: 50%;
  background-size: cover;
}
#page3 {
  background: url(../../assets/images/image3.jpg);
  background-size: cover;
}
#page4 {
  background: url(../../assets/images/image2.jpg);
  background-blend-mode: hue;
  height: 80%;
  background-position-y: 100%;
  background-size: cover;
}
#main-title {
  position: relative;
  text-align: center;
  font-family: "Playfair Display", serif;
  font-size: 550%;
  /* background-image: -webkit-linear-gradient(rgb(112, 233, 128), #e28080, rgb(128, 103, 219));
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
	-webkit-animation: hue 10s infinite linear; */
}
#main-subtitle {
  position: relative;
  padding: 2rem;
  top: 20rem;
  float: right;
  text-align: right;
  vertical-align: middle;
  font-size: 2rem;
  font-family: "Song Myung", serif;
}
#main-contents {
  position: relative;
  font-size: 1.5rem;
  font-family: "Song Myung", serif;
}
#info-title {
  position: relative;
  padding: 3rem 0;
  font-family: "Playfair Display";
  font-size: 3rem;
}
#info-members {
  position: relative;
  text-align: center;
}
.info-members-profile {
  width: 13rem;
  height: 13rem;
  object-fit: cover;
  border-radius: 50%;
  cursor: pointer;
}
.info-members-name {
  margin: 10px 0;
  letter-spacing: 5px;
  font-size: 17px;
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
  margin: 15% auto;
  padding: 20px;
  width: 50%;
}
::-webkit-scrollbar {
  display: none;
}
.close {
  display: inline;
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

/* title color varience */
@-webkit-keyframes hue {
  from {
    -webkit-filter: hue-rotate(0deg);
  }
  to {
    -webkit-filter: hue-rotate(-360deg);
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
