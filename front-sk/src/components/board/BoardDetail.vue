<template>
  <div>
      <table class='list_table'>
          <col witdh="10%"><col width="20%"><col width="20%"><col width="20%">
            <tr>
                <th>번호</th>
                <th>제 목</th>
                <th>내 용</th>
                <th>날 짜</th>
                <!-- <th>작성자</th> -->
            </tr>
            <tr>
                <td v-html="board.bid"></td>
                <td v-html="board.title"></td>
                <td v-html="board.contents"></td>
                <td v-html="board.regdate"></td>
                <!-- 작성자 찾기-->
            </tr>
      </table>
    
</template>

<script>
export default {

}
</script>

<style>

</style>