<template>
  <div style="padding: 100px; color: blue;">
    <h1>{{ rid }}디테일</h1>
    <button @click="addToScrapList" style="cursor: pointer">스크랩</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      rid: 0,
      uid: 3,
    }
  },
  created() {
    this.rid = this.$route.params.rid
  },
  methods: {
    addToScrapList() {
      console.log("gazua", this.uid, this.rid)
    },
  },
}
</script>

<style>

</style>