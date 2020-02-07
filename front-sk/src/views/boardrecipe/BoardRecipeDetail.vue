<template>
  <div style="padding: 100px; color: blue;">
    <h1>{{ rid }}디테일</h1>
    <button v-if="!isScrapped" @click="addToScrapList" style="cursor: pointer">스크랩</button>
    <button v-if="isScrapped" @click="removeFromScrapList" style="cursor: pointer">스크랩 취소</button>
  </div>
</template>

<script>
import Constant from "../../Constant"
export default {
  data() {
    return {
      rid: 0,
      isScrapped : false,
    }
  },
  created() {
    this.rid = Number(this.$route.params.rid)
  },
  methods: {
    addToScrapList() {
      this.isScrapped = true
      this.$store.dispatch(Constant.ADD_SCRAP, {
        username: this.$store.state.username,
        rid: this.rid,
      })
    },
    removeFromScrapList() {
      this.isScrapped = false
      this.$store.dispatch(Constant.REMOVE_SCRAP, {
        username: this.$store.state.username,
        rid: this.rid,
      })
    }
  },
}
</script>

<style>

</style>