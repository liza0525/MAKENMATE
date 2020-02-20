<template>
  <div style="background-color: white;">
    <div id="boardrecipe-header">
      <h1 id="boardrecipe-category">레시피 공유</h1>
    </div>
    <div v-if="!submitted">
      <form
        action="action"
        method="post"
        id="_boardForm"
        name="boaradForm"
        @submit.prevent="addBoard"
      >
        <div id="boardrecipe-context">
          <h1 style="display: inline;">제목</h1>
          <br />
          <input
            value="제목"
            type="text"
            name="titleid"
            id="_titleid"
            v-model="btitleid"
            style="width:100%; border-bottom:1px solid #ccc; margin-bottom: 5vh;"
          />
          <h1>내용</h1>
          <textarea
            value="내용"
            type="text"
            name="contentsid"
            id="_contentsid"
            v-model="bcontentsid"
            style="width:100%; height: 10rem; border-bottom:1px solid #ccc"
          />
        </div>

        <file-upload />
        <div style="text-align: center;">
          <v-btn class="boardrecipe-button" type="submit" dark color="dark">
            <v-icon left>mdi-checkbox-marked-circle</v-icon> 등록
          </v-btn>
          <v-btn class="boardrecipe-button" @click="$router.go(-1)" dark color="light">
            <v-icon left>mdi-cancel</v-icon> 취소
          </v-btn>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import http from "../../http-common";
import FileUpload from "@/components/FileUpload";
import Constant from "../../Constant";

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
      this.filearr = this.$store.state.filelist;
      this.fdid = this.$store.state.filedelete;
      console.log("this.filearr length before delete ", this.filearr.length);

      if (this.fdid != null) {
        for (let i = 0; i < this.filearr.length; i++) {
          if (this.filearr[i].filelist === this.fdid) {
            this.filearr.splice(this.filearr.indexOf(i).filelist, 1);
          }
        }
      }

      console.log("-------------------");
      for (let i = 0; i < this.filearr.length; i++) {
        console.log(this.filearr[i].filelist);
        this.aa[i] = this.filearr[i].filelist;
      }
      console.log(this.aa.length);
      console.log(":SDfasdjfbhaskdf");
      console.log(this.aa);
      this.$store.state.filelist = [];
      this.$store.state.filedelete = null;
      
      if(this.aa.length === 0) {
        this.aa[0] = 49104755;
        console.log(this.add);
      }
      

      http
        .post("/boardrecipe/", {
          title: this.btitleid,
          contents: this.bcontentsid,
          user_name: window.sessionStorage.getItem("login_username"),
          file: JSON.stringify(this.aa)
        })
        .then(res => {
          this.rid = res.data;
          this.$router.push({
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
<style scoped="scoped">
#boardrecipe-header {
  background: linear-gradient(rgba(0, 0, 0, 0.5)),
    url("../../assets/images/image7.jpg") no-repeat;
  background-size: 100%;
  height: 60vh;
  background-position-y: 30%;
  color: white;
}
#boardrecipe-category {
  margin: 0 0 0 15vw;
  display: inline;
  position: relative;
  float: left;
  top: 35vmin;
  font-size: 11vmin;
  font-family: "BBTreeGB";
}
#boardrecipe-context {
  margin: 5vw 15vw;
  font-family: "BBTreeGL";
}
.boardrecipe-button {
  width: 7rem;
  height: 3rem !important;
  margin: 0 5px;
  margin-bottom: 10vh;
  font-size: 1rem;
  background-color: black;
  border-radius: 10vmin;
  color: white;
}
@media (max-width: 700px) {
  #board-context {
    margin: 10vw 15vw 15vw 15vw;
    font-size: 15px;
  }
  #boardrecipe-header {
    height: 35vh;
    background-size: 200vw;
    background-position-x: 50%;
  }
  #board-title {
    margin-top: 4vmin;
    font-size: 8vmin;
  }
  #board-username {
    margin: 0 0 0 2vw;
    display: inline;
    float: left;
    top: 40vmin;
    font-size: 3vmin;
  }
}
</style>