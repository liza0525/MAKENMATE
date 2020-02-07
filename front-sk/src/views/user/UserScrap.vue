<template>
  <div style="padding: 100px; color: blue;">
    <h1>스크랩 페이지</h1>
    <p>
      <strong style="font-size:20px">uid</strong>
      : {{ username }}
    </p>
    <p style="font-size:20px; font-weight:bold;">scrap list</p>
    <li v-for="scrap in scrapList" :key="scrap.id">
      title : {{ scrap.title }}
      <br />
      content : {{ scrap.contents }}
      <br />
      <button @click="removeFromScrapList(scrap.rid)" style="color: red">스크랩 취소</button>
      <hr />
    </li>
  </div>
</template>

<script>
import Constant from "../../Constant";
export default {
  data() {
    return {
      username: ""
    }
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
  methods: {
    removeFromScrapList(scrappedRid) {
      this.isScrapped = false;
      this.$store.dispatch(Constant.REMOVE_SCRAP, {
        username: this.$store.state.username,
        rid: scrappedRid
      });
    }
  },
  computed: {
    scrapList: {
      get() {
        console.log(this.$store.state.scrapList);
        return this.$store.state.scrapList;
      },
      set() {
        console.log("setter");
      }
    }
  },
};
</script>

<style>
</style>