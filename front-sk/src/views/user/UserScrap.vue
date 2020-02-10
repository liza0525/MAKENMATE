<template>
  <div style="padding: 100px; color: blue;">
    <h1>스크랩 페이지</h1>
    <p style="font-size:25px; font-weight:bold;">
      username : {{ username }}
    </p>
    <p style="font-size:25px; font-weight:bold;">scrap list</p>
    <li v-for="scrap in scrapList" :key="scrap.id">
      <span @click="goRecipeDetail(scrap.rid)" style="font-size:20px; font-weight:bold; cursor:pointer;">{{ scrap.title }}</span>
      <br />
      <button @click="removeFromScrapList(scrap.rid)" style="color: red">스크랩 취소</button>
      <hr />
    </li>
  </div>
</template>

<script>
import Constant from "../../Constant";
import BoardRecipeDetailVue from '../boardrecipe/BoardRecipeDetail.vue';

export default {
  data() {
    return {
      username: "",
      prevBt: "<",
      nextBt: ">",
      fistBt: "<<",
      lastBt: ">>"
    };
  },
  created() {
    this.username = this.$store.state.username;
    this.$store
      .dispatch(Constant.GET_SCRAPLIST, {
        username: this.$store.state.username
      })
      .then(() => {
        this.scrapList = { ...this.$store.state.scrapList };
      });
  },
  computed: {
    scrapList() {
      return this.$store.state.scrapList;
    }
  },
  methods: {
    removeFromScrapList(scrappedRid) {
      this.$store
        .dispatch(Constant.REMOVE_SCRAP, {
          username: this.$store.state.username,
          rid: scrappedRid
        })
        .then((this.scrapList = { ...this.$store.state.scrapList }));
    },
    goRecipeDetail(selectedRid) {
      console.log(selectedRid)
      this.$router.push({
        name: 'BoardRecipeDetail',
        params: {
          rid: selectedRid
        }
      })
    }
  }
};
</script>
<style>
</style>