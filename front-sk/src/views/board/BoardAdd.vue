<template>
    <div>
        <div id="board-list-header">
            <h1 id="board-category">자유게시판</h1>
        </div>
        <div v-if="!submitted">
            <form
                action="action"
                method="post"
                id="_boardForm"
                name="boaradForm"
                @submit.prevent="addBoard">
                <div id="board-context">
                    <h1>제목</h1>
                    <br/>
                    <input
                        value="제목"
                        type="text"
                        name="titleid"
                        id="_titleid"
                        v-model="btitleid"
                        style="width:100%; border-bottom:1px solid #000;"/>
                    <h1>내용</h1>
                    <textarea
                        value="내용"
                        type="text"
                        name="contentsid"
                        id="_contentsid"
                        v-model="bcontentsid"
                        style="width:100%; height: 10rem; border-bottom:1px solid #000"/>
                </div>

                <button class="board-button" type="submit" name="button">등록</button>
                <file-upload/>
            </form>
        </div>
    </div>
</template>

<script>
    import http from "../../http-common";

    import FileUpload from '@/components/FileUpload';

    export default {
        components: {
            FileUpload
        },
        name: "add-board",

        data() {
            return {
                info: null,
                loading: true,
                errored: false,
                btitleid: "",
                bcontentsid: "",
                submitted: false,
                bid: 0,
                filearr: [],
                fdid: 0,
                aa: []
            };
        },
        methods: {
            addBoard() {
                this.filearr = this.$store.state.filelist
                this.fdid = this
                    .$store
                    .state
                    .filedelete
                if (this.fdid != null) {
                    for (let i = 0; i < this.filearr.length; i++) {
                        if (this.filearr[i].filelist === this.fdid) {
                            this
                                .filearr
                                .splice(this.filearr.indexOf(i).filelist, 1)
                        }
                    }
                }

                for (let i = 0; i < this.filearr.length; i++) {
                    this.aa[i] = this
                        .filearr[i]
                        .filelist
                }
                this.$store.state.filelist = [];
                this.$store.state.filedelete = null;

                http
                    .post("/board/", {
                        title: this.btitleid,
                        contents: this.bcontentsid,
                        user_name: window
                            .sessionStorage
                            .getItem("login_username"),
                            file: JSON.stringify(this.aa)
                    })
                    .then(res => {
                        this.bid = res.data;
                        this
                            .$router
                            .push({
                                name: "BoardDetail",
                                params: {
                                    bid: this.bid
                                }
                            });
                    });
                this.submitted = true;
            }
        }
    };
</script>

<style scoped="scoped">
    #board-context {
        color: #777;
        padding: 2rem 15rem;
        margin: 1rem 0;
    }
    .board-button {
        color: #777;
        margin: 0 0.5rem;
        width: 5rem;
        height: 3rem;
        border: 1px solid #ccc;
        border-radius: 3rem;
    }
</style>