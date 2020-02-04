<template>
    <div style="padding-top: 100px; color: red;">
        <div>
            <table class='list_table'>
                <col width="10%">
                    <col width="20%">
                        <col width="20%">
                            <col width="10%">
                                <tr>
                                    <th>글 번호</th>
                                    <th>제 목</th>
                                    <th>날 짜</th>
                                    <th>삭 제</th>
                                </tr>

                                <tr v-for="board in info.object" v-bind:key="board.bid">
                                    <td v-html="board.bid" @click="detail_id(board.bid)"></td>
                                    <td v-html="board.title"></td>
                                    <td v-html="board.regdate"></td>
                                    <!-- <td class='button'><input type="button" value="삭제"></td> -->
                                    <!-- <td class='button' @click="delete_board(board.bid)"><input type="button"
                                    class="blue" value="삭제"></td> -->
                                </tr>
                            </table>
                        </div>
                    </div>
                </template>

                <script>
                    import http from "../../http-common";
                    export default {
                        name: "board-list",
                        data: () => {
                            return {info: [], loading: true, errored: false}
                        },
                        methods: {
                            retrieveBoard() {
                                http
                                    .get('/board')
                                    .then(response => (this.info = response.data))
                                    .catch(error => {
                                        this.errored = true
                                    })
                                    . finally(() => this.loading = false);
                            },
                            detail_id(sendBid) {
                                this
                                    .$router
                                    .push({
                                        name: "BoardDetail",
                                        params: {
                                            bid: sendBid
                                        }
                                    });
                            }
                        },
                        mounted() {
                            this.retrieveBoard();
                        }
                    };
                </script>

                <style></style>