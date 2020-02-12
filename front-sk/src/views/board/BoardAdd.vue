<template>
    <div style="padding-top: 100px; color: red;">
        <div v-if="!submitted">
            <form
                action=""
                method="post"
                id="_boardForm"
                name="boaradForm"
                @submit.prevent="addBoard">
                <table>
                    <colgroup>
                        <col style="width:30%;"/>
                        <col style="width:70%;"/>
                    </colgroup>
                    <tr>
                        <th>제목</th>
                        <td><input
                            data-msg="제목"
                            type="text"
                            name="titleid"
                            id="_titleid"
                            size="50"
                            v-model="btitleid"
                            style="width:40%"/></td>
                    </tr>
                    <tr>
                        <th>내용</th>
                        <td><textarea
                            data-msg="내용"
                            type="text"
                            name="contentsid"
                            id="_contentsid"
                            size="50"
                            v-model="bcontentsid"
                            style="width:40%"/></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="height:50px; text-align:center;">
                            <button type="submit" name="button">글 등록</button>
                        </td>
                    </tr>
                </table>
                 <file-upload/>
            </form>
        </div>
       
    </div>
</template>

<script>
    import http from "../../http-common";
    
    import FileUpload from '@/components/FileUpload';
    
    export default {
        components: { FileUpload },
        name: "add-board",
        
        data() {
            return {
                info: null,
                loading: true,
                errored: false,
                btitleid: "",
                bcontentsid: "",
                submitted: false,
                bid: 0
            };
        },
        methods: {
            addBoard() {
                http
                    .post("/board/", {
                        title: this.btitleid,
                        contents: this.bcontentsid,
                        user_name: window.sessionStorage.getItem("login_username") ///window.sessionStorage.getItem("login_username") ??? 모르거응ㅇ
                    })
                    .then(res => {
                        this.bid = res.data;
                        this.$router.push({
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

<style></style>