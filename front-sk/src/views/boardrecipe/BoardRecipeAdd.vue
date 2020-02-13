<template>
    <div style="padding-top: 100px; color: red;">
        <div v-if="!submitted">
            <form
                action="action"
                method="post"
                id="_boardForm"
                name="boardForm"
                @submit.prevent="addBoard">
                <table>
                    <colgroup>
                        <col style="width:30%;"/>
                        <col style="width:70%;"/>
                    </colgroup>
                    <tr>
                        <th>제목</th>
                        <td>
                            <input
                                data-msg="제목"
                                type="text"
                                name="titleid"
                                id="_titleid"
                                size="50"
                                v-model="btitleid"
                                style="width:40%"/>
                        </td>
                    </tr>
                    <tr>
                        <th>내용</th>
                        <td>
                            <input
                                data-msg="내용"
                                type="text"
                                name="contentsid"
                                id="_contentsid"
                                size="50"
                                v-model="bcontentsid"
                                style="width:40%"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="height:50px; text-align:center;">
                            <button type="submit" name="button">글 등록</button>
                        </td>
                    </tr>
                </table>
                <FileUpload/>
            </form>
        </div>
    </div>
</template>

<script>
    import http from "../../http-common";
    import FileUpload from '@/components/FileUpload';
    import Constant from '../../Constant';

    export default {
        components: {
            FileUpload
        },
        name: "add-boardrecipe",
        data() {
            return {
                info: null,
                loading: true,
                errored: false,
                btitleid: "",
                bcontentsid: "",
                submitted: false,
                rid: 0,
                filearr: [],
                fdid: 0,
                aa: []
            };
        },
        methods: {
            addBoard() {
                console.log("filelist", this.$store.state.filelist);
                console.log("filedelete", this.$store.state.filedelete);
                this.filearr = this.$store.state.filelist
                this.fdid = this
                    .$store
                    .state
                    .filedelete
                    console
                    .log("this.filearr length before delete ", this.filearr.length)

                if (this.fdid != null) {
                    for (let i = 0; i < this.filearr.length; i++) {
                        if (this.filearr[i].filelist === this.fdid) {
                            this
                                .filearr
                                .splice(this.filearr.indexOf(i).filelist, 1)
                        }
                    }
                }

                console.log("-------------------")
                for (let i = 0; i < this.filearr.length; i++) {
                    console.log(this.filearr[i].filelist)
                    this.aa[i] = this
                        .filearr[i]
                        .filelist
                }
                console.log(this.aa.length)
                console.log(":SDfasdjfbhaskdf")
                console.log(this.aa)
                this.$store.state.filelist = [];
                this.$store.state.filedelete = null;
                // this.$store.commit(Constant.ADD_FILELIST, {filelist: null})
                // this.$store.commit(Constant.DELETE_FILELIST, {filedelete: null})
                http
                    .post("/boardrecipe/", {
                        title: this.btitleid,
                        contents: this.bcontentsid,
                        user_name: window
                            .sessionStorage
                            .getItem("login_username"),
                        file: JSON.stringify(this.aa)
                    })
                    .then(res => {
                        this.rid = res.data;
                        this
                            .$router
                            .push({
                                name: "BoardRecipeDetail",
                                params: {
                                    rid: this.rid
                                }
                            });
                    });
                this.submitted = true;
            }
        }
    };
</script>

<style></style>