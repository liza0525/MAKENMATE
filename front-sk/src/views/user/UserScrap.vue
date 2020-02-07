<template>
  <div style="padding: 100px; color: blue;">
    <h1>스크랩 페이지</h1>
    <p><strong style="font-size:20px">uid</strong> : {{ uid }}</p>
    <p style="font-size:20px; font-weight:bold;">scrap list</p>
    <li v-for="scrap in scrapList" :key=scrap.id>
      title : {{ scrap.title }} <br /> content : {{ scrap.contents }}
      <hr />
    </li>
  </div>
</template>

<script>
import Constant from "../../Constant";
export default {
  data() {
    return {
      uid: 0,
      scrapList : [],
    }
  },
  created() {
    this.uid = this.$route.params.uid
    this.$store
      .dispatch(Constant.GET_SCRAPLIST, { uid: this.$route.params.uid })
      .then(() => {
        this.scrapList = {...this.$store.state.scrapList };
      });
  },

}
</script>

<style>

</style>