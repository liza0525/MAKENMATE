<template>
  <div style="padding-top: 100px; color: red;">
    <div id="boardrecipe-list-header">
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
          <button class="boardrecipe-button" type="submit" name="button">등록</button>
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
      // this.$store.commit(Constant.ADD_FILELIST, {filelist: null})
      // this.$store.commit(Constant.DELETE_FILELIST, {filedelete: null})
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
#boardrecipe-category {
  margin: 0 0 0 15vw;
  display: inline;
  position: relative;
  float: left;
  top: 35vmin;
  font-size: 11vmin;
  font-family: 'BBTreeGB';
}
#boardrecipe-context {
  color: #ccc;
  padding: 2rem 5vw;
  font-family: "GyeonggiBatang";
}
.boardrecipe-button {
  color: #ccc;
  width: 50vmin;
  height: 9vmin;
  margin-bottom: 3vh;
  font-size: 3vmin;
  border: 1px solid #ccc;
  border-radius: 10vmin;
}
</style>