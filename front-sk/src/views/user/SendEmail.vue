<template>
  <div class="container">
      <h1>메일 전송</h1>
      <p>메일이 전송되었습니다</p>
        <router-link v-bind:to="{name:'Login'}">
            <button class="btn btn--back">로그인 화면</button>
        </router-link>
        <router-link v-bind:to="{name:'InputEmail'}">
            <button class="btn btn--back">메일이 도착하지 않았나요?</button>
        </router-link>
  </div>
</template>

<script>
export default {

}
</script>

<style>
button {
    margin: 5px 0;
}
</style>