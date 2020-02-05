<template>
    <div style="padding-top: 100px; color: red;">
        <table class='list_table'>
            <col witdh="10%">
                <col width="20%">
                    <col width="20%">
                        <col width="20%">
                            <col witdh="10%">
                                <tr>
                                    <th>번호</th>
                                    <th>제 목</th>
                                    <th>내 용</th>
                                    <th>날 짜</th>
                                    <th>작성자</th>
                                    <th>삭 제</th>
                                    <th>수 정</th>
                                </tr>
                                <tr>
                                    <td v-html="board.bid"></td>
                                    <td v-html="board.title"></td>
                                    <td v-html="board.contents"></td>
                                    <td v-html="board.regdate"></td>
                                    <td v-html="board.user_name"></td>
                                    <td class='button' @click="delete_board(board.bid)">
                                        <input type="button" class="blue" value="삭제">
                                    </td>
                                    <td class='button' @click="update_board(board.bid)">
                                        <input type="button" class="blue" value="수정">
                                    </td>
                                    </tr>
                                </table>
                            </div>
                        </template>

                        <script>
                            import http from "../../http-common";
                            export default {
                                data: () => {
                                    return {
                                        bid: 0,
                                        board: {
                                            title: "",
                                            file: "",
                                            contents: "",
                                            regdate: "",
                                            user_name: ""
                                        }
                                    };
                                },
                                created() {
                                    this.getData();
                                },
                                methods: {
                                    getData() {
                                        this.bid = this.$route.params.bid;
                                        http
                                            .get("/board/" + this.bid)
                                            .then(res => {
                                                this.board = res.data;
                                                console.log(this.board);
                                            });

                                    },
                                    delete_board(boardid) {
                                        http
                                            .delete('/board/' + boardid)
                                            .then(response => {
                                                if (response.data.data == "Success") {
                                                    alert("삭제하였습니다.");
                                                    this.$router.push("BoardList");
                                                } else {
                                                    alert("삭제 땡 ~!!");
                                                }
                                            })
                                            .catch(error => {
                                                this.errored = true
                                            })
                                            . finally(() => this.loading = false);
                                    },
                                    update_board(boardid) {
                                        this.$router.push({name:"BoardUpdate",params: {bid: boardid}});
                                    }
                                }
                            }
                        </script>

                        <style></style>