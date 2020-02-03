<template>
  <full-page ref="fullpage" :options="options" id="fullpage">
    <div class="section">
      <div class="section" id="main-img">
        <h1 id="main-title">
          Daily Cocktail Party
        </h1>
        <p id="main-contents">
          칵테일 맛있고 레시피도 공유하고 검색하고
          <br />칵테일 번개 약속도 잡을 수 있다, 뭐 그런...
          <br />그런 사이트라고 소개하고 싶은데
          <br />소개 글 좀 깔쌈하게 쓸 수 없을까 하 이놈의 문장력
        </p>
      </div>
    </div>
    <div class="section">
      <div>
        <h1 id="info-title">Who are we?</h1>
        <v-row id="info-members">
          <v-col v-for="person in people" lg="3" md="6" sm="12" :key="person.id">
            <img class="info-members-profile info-btn" @click="infoPopUp" :src="person.profileUrl" />
            <br />
            <p class="info-members-name">{{ person.familyName }}{{ person.firstName }}</p>
          </v-col>
        </v-row>
        <!-- modal -->
        <div v-for="person in people" class="modal info-modal" :key="person.id">
          <div class="modal-content">
            <p class="close">&times;</p>
            <p>
              {{ person.firstName }}의 자기소개
              <br />
              {{ person.biography }}
            </p>
          </div>
        </div>
      </div>
    </div>
  </full-page>
</template>

<script>
export default {
  data() {
    return {
      people: [
        {
          familyName: "김",
          firstName: "우재",
          biography: "므재는 소주를 좋아해요. 사실 소주를 맥주에 말아먹는 걸 좋아한다 봐야지요ㅎㅎ",
          profileUrl: require("../../assets/images/kwj.jpg"),
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
          biography: "근성쓰는 보드카를 마셔요. 매일 현희를 코칭하고 있어욬ㅋㅋ",
          profileUrl: require("../../assets/images/lgs.jpg")
        },
        {
          familyName: "정",
          firstName: "윤영",
          biography: "쩡뉴는 맥주를 먹어요. 운동은 많이 먹기 위해 하는 거죠...!",
          profileUrl: require("../../assets/images/cyy.jpg")
        }
      ],
    };
  },
  methods: {
    infoPopUp() {
      let modal = document.getElementsByClassName("info-modal")
      let btn = document.getElementsByClassName("info-btn")
      let close = document.getElementsByClassName("close")
      
      for (let i=0 ; i < this.people.length ; i++){
        btn[i].onclick= function(e) {
          modal[i].style.display = "block"
        }
        close[i].onclick = function(e) {
          modal[i].style.display = "none"
        }
      }
    },
  },
  created() {
    window.addEventListener("scroll", this.pageMove)
  },
  mounted() {
    this.infoPopUp()
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.pageMove)
    window.removeEventListener("close", this.infoPopUp)
  }
};
</script>

<style>
@import url("https://fonts.googleapis.com/css?family=Song+Myung&display=swap");
body {
  background: none !important;
}
#fullpage {
  background-color: black;
}
#main-img {
  padding: 0 10rem;
  height: 42rem;
  background: url(../../assets/images/image.png) no-repeat;
}
#main-title {
  position: relative;
  color: white;
  font-family: "Song Myung", serif;
  font-size: 3rem;
}
#main-contents {
  position: relative;
  margin: 1rem 0;
  color: white;
  font-size: 1rem;
}
#info-title {
  position: relative;
  color: white;
  padding: 0 10rem;
  font-family: "Song Myung", serif;
  font-size: 3rem;
}
#info-members {
  position: relative;
  text-align: center;
  padding: 0 100px;
  margin: 3rem;
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
  color: white;
}
.modal {
  display: none;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 50%;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgb(0, 0, 0, 0.7);
}
.modal-content {
  color: black;
  background-color: #fefefe;
  margin: 10% auto;
  padding: 20px;
  width: 50%;
}
::-webkit-scrollbar{
  display: none;
}
.close {
  display: inline;
  font-size: 25px;
  cursor: pointer;
}
</style>