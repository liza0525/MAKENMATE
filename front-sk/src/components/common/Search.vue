<template>
  <div>
    <input
      v-model="searchData"
      type="text"
      id="search-bar"
      @keypress.enter="onInput()"
      placeholder="Search"
    />
    <v-btn icon class="v-btn">
      <v-icon @click="onInput()">mdi-magnify</v-icon>
    </v-btn>
  </div>
</template>

<script scoped>
export default {
  name: "Search",
  data() {
    return {
      searchData: ""
    };
  },
  methods: {
    onInput() {
      this.$emit("searchData", this.searchData);
      document.getElementById("search-bar").value = ""; // input data 전송 후 input 창 내 글씨 초기화
    }
  }
};
</script>

<style></style>
