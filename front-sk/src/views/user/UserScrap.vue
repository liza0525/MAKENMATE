<template>
  <div>
    <div id="user-scrap-header">
      <h1 id="user-scrap-title">스크랩 목록</h1>
    </div>
    <div id="user-scrap-context">
      <v-simple-table dark>
        <template>
          <thead>
            <tr>
              <th>제목</th>
              <th id="writer-col">글쓴이</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="scrap in scrapList" v-bind:key="scrap.bid">
              <td v-html="scrap.title" @click="goRecipeDetail(scrap.rid)" style="cursor: pointer;"></td>
              <td id="writer-col" v-html="scrap.user.nickname"></td>
              <td><button @click="removeFromScrapList(scrap.rid)" style="color: rgb(230, 0, 0);">스크랩 취소</button></td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </div>
      <div>
        <button v-for="pageNm in pageNms" :key="pageNm" @click="loadScrapList(pageNm)">
          <span style="margin-right:10px;">{{ pageNm }}</span>
        </button>
      </div>
  </div>
</template>

<script>
import Constant from "../../Constant";
import BoardRecipeDetailVue from "../boardrecipe/BoardRecipeDetail.vue";

export default {
  data() {
    return {
      username: "",
      prevBt: "<",
      nextBt: ">",
      fistBt: "<<",
      lastBt: ">>",
      pageNms: []
    };
  },
  created() {
    this.username = this.$store.state.username;
    this.loadScrapList(1);
  },
  computed: {
    scrapList() {
      return this.$store.state.scrapList;
    },
    totalPages() {
      return this.$store.state.totalPages;
    },
  },
  methods: {
    loadScrapList(pageNm) {
      this.$store
        .dispatch(Constant.GET_SCRAPLIST, {
          username: this.$store.state.username,
          pageNm
        })
        .then(() => {
          let arr = [];

          let min = 1;
          for (let index = 0; index < 5; index++) {
            if (Number(min + index) > this.totalPages) break;
            arr.push(Number(min + index));
          }
          this.pageNms = arr;
        });
    },
    removeFromScrapList(scrappedRid) {
      this.$store
        .dispatch(Constant.REMOVE_SCRAP, {
          username: this.$store.state.username,
          rid: scrappedRid
        })
        .then(() => {
          let arr = [];
          let min = 1;
          for (let index = 0; index < 5; index++) {
            if (Number(min + index) > this.totalPages) break;
            arr.push(Number(min + index));
          }
          this.pageNms = arr;
        });
    },
    goRecipeDetail(selectedRid) {
      console.log(selectedRid);
      this.$router.push({
        name: "BoardRecipeDetail",
        params: {
          rid: selectedRid
        }
      });
    }
  }
};
</script>
<style>
#user-scrap-header {
  background: linear-gradient(rgba(0, 0, 0, 0.5)),
    url("../../assets/images/image5.jpg") no-repeat;
  background-size: 100%;
  height: 60vh;
  background-position-y: 20%;
  color: white;
}
#user-scrap-title {
  margin: 0 0 0 15vw;
  display: inline;
  position: relative;
  float: left;
  top: 35vmin;
  font-size: 11vmin;
}
#user-scrap-context {
  color: #ccc;
  margin: 5vmax 10vmax;
}
@media (max-width: 700px) {
  #user-scrap-header {
    height: 50vh;
    background-size: 200vw;
    background-position-x: 50%;
  }
  #user-scrap-title {
    margin-top: 3vmin; 
    font-size: 7vmin;
  }
  #writer-col{
    display: none;
  }
}
</style>
