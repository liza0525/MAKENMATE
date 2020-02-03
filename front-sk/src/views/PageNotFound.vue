<template>
<div>
  <h1>404 Page Not Found</h1>
  <button @click="click">back to prior page</button>
</div>
</template>

<script>
export default {
  methods: {
    click() {
      this.$router.go(-1)
    },
  },
}
</script>

<style>
</style>