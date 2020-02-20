<template>
  <div style="background-color: white; padding-bottom: 5vh">
    <div id="user-scrap-header">
      <h1 id="user-scrap-title" class="sansfont">스크랩 목록</h1>
    </div>
    <div id="user-scrap-context">
      <v-simple-table>
        <template>
          <thead>
            <tr id="table-header">
              <th id="table-header-title">제목</th>
              <th id="table-header-writer">글쓴이</th>
              <th id="table-header-scrap">-</th>
            </tr>
          </thead>
          <tbody>
            <tr class="table-content-title" v-for="scrap in scrapList" v-bind:key="scrap.bid">
              <td
                v-html="scrap.title"
                @click="goRecipeDetail(scrap.rid)"
                style="cursor: pointer;"
              ></td>
              <td class="table-content-writer">
                
                <router-link
                  :to="{
                  name: 'UserProfile',
                  params: {
                    username: scrap.user.nickname
                  }
                }"
                 style="color: black; cursor: pointer;">{{ scrap.user.nickname }}</router-link></td>
              <td class="table-content-scrap">
                <button
                  @click="removeFromScrapList(scrap.rid)"
                  style="color: rgb(230, 0, 0); font-weight: bold;"
                >
                  스크랩 취소
                </button>
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
      <div style="width: 100%; text-align: center; color: #000;">
      <div id="pagination">
        <button
          v-for="pageNm in pageNms"
          :key="pageNm"
          @click="loadScrapList(pageNm)"
        >
          <span style="margin-right:10px;">{{ pageNm }}</span>
        </button>
      </div>
      </div>
    </div>
  </div>
</template>

<script>
import Constant from "../../Constant";
import BoardRecipeDetailVue from "../boardrecipe/BoardRecipeDetail.vue";

export default {
  data() {
    return {
      username: "",
      prevBt: "<",
      nextBt: ">",
      fistBt: "<<",
      lastBt: ">>",
      pageNms: [],
      window: {
        width: 0,
        height: 0
      }
    };
  },
  created() {
    window.addEventListener("resize", this.handleResize);
    this.handleResize();
    this.username = this.$store.state.username;
    this.loadScrapList(1);
  },
  computed: {
    scrapList() {
      return this.$store.state.scrapList;
    },
    totalPages() {
      return this.$store.state.totalPages;
    }
  },
  methods: {
    loadScrapList(pageNm) {
      this.$store
        .dispatch(Constant.GET_SCRAPLIST, {
          username: this.$store.state.username,
          pageNm
        })
        .then(() => {
          let arr = [];

          let min = 1;
          for (let index = 0; index < 5; index++) {
            if (Number(min + index) > this.totalPages) break;
            arr.push(Number(min + index));
          }
          this.pageNms = arr;
        });
    },
    removeFromScrapList(scrappedRid) {
      this.$store
        .dispatch(Constant.REMOVE_SCRAP, {
          username: this.$store.state.username,
          rid: scrappedRid
        })
        .then(() => {
          let arr = [];
          let min = 1;
          for (let index = 0; index < 5; index++) {
            if (Number(min + index) > this.totalPages) break;
            arr.push(Number(min + index));
          }
          this.pageNms = arr;
        });
    },
    goRecipeDetail(selectedRid) {
      console.log(selectedRid);
      this.$router.push({
        name: "BoardRecipeDetail",
        params: {
          rid: selectedRid
        }
      });
    },
    handleResize() {
      this.window.width = window.innerWidth;
      this.window.height = window.innerHeight;
      // console.log(typeof this.window.height);
      this.window.height = this.window.height + "px";
      this.window.height = "calc(" + this.window.height + " - 4rem)";
    }
  },
  destroyed() {
    window.removeEventListener("resize", this.handleResize);
  },
};
</script>
<style scoped>
#user-scrap-header {
  background: linear-gradient(rgba(0, 0, 0, 0.5)),
    url("../../assets/images/image6.jpg") no-repeat;
  background-size: 100%;
  height: 50vh;
  background-position-y: 30%;
  color: white;
}
#user-scrap-title {
  margin: 0 0 0 15vw;
  display: inline;
  position: relative;
  float: left;
  top: 30vmin;
  font-size: 11vmin;
  font-family: "BBTreeGB";
}
#user-scrap-context {
  margin: 5vmax 10vmax;
  font-family: "GyeonggiBatang";
}
@media (max-width: 700px) {
  #user-scrap-header {
    height: 35vh;
    background-size: 200vw;
    background-position-x: 50%;
  }
  #user-scrap-title {
    margin-top: 4vmin;
    font-size: 8vmin;
  }
}
td {
  padding: 20px !important;
}
th {
  font-size: 15px !important;
  color: white !important;
}
#table-header {
  background-color: #000;
}
#table-header-title, .table-content-title {
  width: 60%;
}
#table-header-writer, .table-content-writer {
  width: 25%;
}
#table-header-scrap, .table-content-scrap {
  width: 15%;
}
@media (max-width: 960px) {
  #board-context {
    margin: 2vmax 3vmax;
  }
  #board-list-header {
    height: 50vh;
    background-size: 200vw;
    background-position-x: 50%;
  }
  #board-category {
    margin-top: 3vmin;
    font-size: 7vmin;
  }
  #table-header-writer,
  .table-content-writer {
    display: none;
  }
  #table-header-scrap,
  .table-content-scrap {
    width: 30%;
  }
}
.pagination {
  margin: 1rem 100px;
  font-size: 18px;
  display: inline;
}
</style>
