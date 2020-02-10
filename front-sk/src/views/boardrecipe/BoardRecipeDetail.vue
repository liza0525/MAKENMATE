<template>
  <div style="padding: 100px; color: blue;">
    <h1>{{ rid }}번 레시피 디테일</h1>
    <p>제목 : {{ boardRecipe.title }}</p>
    <p>내용 : {{ boardRecipe.contents }}</p>
    <button v-if="!isScrapped" @click="addToScrapList" style="cursor: pointer">스크랩</button>
    <button v-if="isScrapped" @click="removeFromScrapList" style="cursor: pointer">스크랩 취소</button>
  </div>
</template>

<script>
import Constant from "../../Constant";
import http from "@/http-common";
export default {
  data() {
    return {
      rid: 0,
      boardRecipe: {},
      userIdScrappingList: [],
      isScrapped: false,
    };
  },
  created() {
    this.rid = Number(this.$route.params.rid);
    http
    .get("/boardrecipe/"+this.rid)
    .then(res => {
      // console.log(res.data)
      this.boardRecipe = res.data
    })
    http
    .get("/boardrecipe/scrapped/"+this.rid, {
      params: {
        username: this.$store.state.username
      }
    })
    .then(res => {
      this.isScrapped = res.data
      console.log("this.isScrapped", this.isScrapped)
    })
  },
  methods: {
    addToScrapList() {
      this.isScrapped = true;
      this.$store.dispatch(Constant.ADD_SCRAP, {
        username: this.$store.state.username,
        rid: this.rid
      });
    },
    removeFromScrapList() {
      this.isScrapped = false;
      this.$store.dispatch(Constant.REMOVE_SCRAP, {
        username: this.$store.state.username,
        rid: this.rid
      });
    }
  }
};
</script>

<style>
</style>