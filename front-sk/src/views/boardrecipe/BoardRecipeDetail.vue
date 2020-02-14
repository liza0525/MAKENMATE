<template>
    <div style="padding: 100px; color: blue;">
        <p>
            <a href="/#/boardrecipe/list">레시피 공유 게시판 리스트로</a>
        </p>
        <h1>{{ rid }}번 레시피 디테일</h1>
        <p>제목 :
            {{ boardRecipe.title }}</p>
        <p>내용 :
            {{ boardRecipe.contents }}</p>
        <button v-if="!isScrapped" @click="addToScrapList" style="cursor: pointer">스크랩</button>
        <button v-if="isScrapped" @click="removeFromScrapList" style="cursor: pointer">스크랩 취소</button>
    
        <div v-for="images in imagepath" v-bind:key="images">
          <img :src="images"/>
        </div>
    </div>

    

</template>

<script>
    import Constant from "../../Constant";
    import http from "@/http-common";
    export default {
        data() {
            return {
                rid: 0,
                boardRecipe: {},
                userIdScrappingList: [],
                isScrapped: false,
                imagepath: []
              };
        },
        created() {
            this.rid = Number(this.$route.params.rid);
            http
                .get("/boardrecipe/" + this.rid)
                .then(res => {
                    // console.log(res.data)
                    this.boardRecipe = res.data
                    
                    if (this.boardRecipe.filelist.length != 0) {
                        console.log(this.boardRecipe.filelist[0])
                        for (let i = 0; i < this.boardRecipe.filelist.length; i++) {
                          this.imagepath[i] = require("C:/image/" + this.boardRecipe.filelist[i])
                        }
                    }
                })
            http
                .get("/boardrecipe/scrapped/" + this.rid, {
                    params: {
                        username: this.$store.state.username
                    }
                })
                .then(res => {
                    this.isScrapped = res
                        .data
                        console
                        .log("this.isScrapped", this.isScrapped)
                })
        },
        methods: {
            addToScrapList() {
                this.isScrapped = true;
                this
                    .$store
                    .dispatch(Constant.ADD_SCRAP, {
                        username: this.$store.state.username,
                        rid: this.rid
                    });
            },
            removeFromScrapList() {
                this.isScrapped = false;
                this
                    .$store
                    .dispatch(Constant.REMOVE_SCRAP, {
                        username: this.$store.state.username,
                        rid: this.rid
                    });
            }
        }
    };
</script>

<style></style>