<template>
	<div id="app">
		<div class="headtitle"><h2>Board List</h2></div>
		<div class="search_box">
			<nav>
				<router-link class="btn btn-primary" to="/boardlist">게시판 보기</router-link>
			</nav>
		</div>
		<router-view/>
	</div>
</template>

<script>
	export default{
		name:"app"
	};
</script>

<style>
</style>