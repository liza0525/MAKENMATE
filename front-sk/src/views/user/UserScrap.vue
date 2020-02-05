<template>
  <div style="padding: 100px; color: blue;">
    <h1>스크랩 페이지</h1>
    <p>uid : {{ uid }}</p>
    <p>rid list : {{ scrapList }}</p>
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
  mounted() { 
    // this.$store
    //   .dispatch(Constant.GET_SCRAPLIST, { uid: this.$router.params.uid })
    //   .then(() => {
    //     this.scrapList = { ...this.$store.state.scrapList };
    //   });
    this.uid = this.$route.params.uid
  },
}
</script>

<style>

</style>