<template>
  <div style="padding: 100px; color: blue;">
    <h1>{{ rid }}디테일</h1>
    <button @click="addToScrapList" style="cursor: pointer">스크랩</button>
  </div>
</template>

<script>
import Constant from "../Constant"
export default {
  data() {
    return {
      rid: 0,
      uid: 4,
    }
  },
  created() {
    this.rid = Number(this.$route.params.rid)
  },
  methods: {
    addToScrapList() {
      console.log("gazua", this.uid, this.rid)
      this.$store.dispatch(Constant.ADD_SCRAP, {
        uid: this.uid,
        rid: this.rid,
      })
    },
  },
}
</script>

<style>

</style>