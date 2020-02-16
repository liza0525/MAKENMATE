<template>
  <div id="app">
    <file-pond
      name="test"
      ref="pond"
      label-idle="Drop files here..."
      allow-multiple="true"
      allowFileTypeValidation="false"
      accepted-file-types="image/jpeg, image/png"
      allowImagePreview="true"
      :server="server"
      v-bind:files="myFiles"
      v-on:init="handleFilePondInit"
    />
  </div>
</template>

<script>
// Import FilePond
import vueFilePond from "vue-filepond";
import Constant from "../Constant";
// Import plugins
import FilePondPluginFileValidateType from "filepond-plugin-file-validate-type/dist/filepond-plugin-file-validate-type.esm.js";
import FilePondPluginImagePreview from "filepond-plugin-image-preview/dist/filepond-plugin-image-preview.esm.js";

// Import styles
import "filepond/dist/filepond.min.css";
import "filepond-plugin-image-preview/dist/filepond-plugin-image-preview.min.css";

import store from "../vuex/store";
// Create component
const FilePond = vueFilePond(
  FilePondPluginFileValidateType,
  FilePondPluginImagePreview
);
//FilePond.registerPlugin(FilePondPluginImagePreview);

export default {
  name: "app",
  data: function() {
    return {
      myFiles: "",
      store,
      server: {
        process: (
          fieldName,
          file,
          metadata,
          load,
          error,
          progress,
          abort,
          transfer,
          options
        ) => {
          // fieldName is the name of the input field file is the actual file object to
          // send
          const formData = new FormData();
          var self = this;
          formData.append(fieldName, file, file.name);

          const request = new XMLHttpRequest();
          request.open("POST", "http://localhost:8080/uploadFile");
          console.log(formData);
          // Should call the progress method to update the progress to 100% before calling
          // load Setting computable to false switches the loading indicator to infinite
          // mode
          request.upload.onprogress = e => {
            progress(e.lengthComputable, e.loaded, e.total);
          };

          // Should call the load method when done and pass the returned server file id
          // this server file id is then used later on when reverting or restoring a file
          // so your server knows which file to return without exposing that info to the
          // client
          request.onload = function() {
            if (request.status >= 200 && request.status < 300) {
              // the  method accepts either a string (id) or an object
              load(request.responseText);
              //this.filenumber = JSON.parse(request.responseText).id
              self.$store.commit(Constant.ADD_FILELIST, {
                filelist: JSON.parse(request.responseText).id
              });
              //console.log(this.$store.state.filelist)
            } else {
              // Can call the error method if something is wrong, should exit after
              error("oh no");
            }
          };
          request.send(formData);
          // Should expose an abort method so the request can be cancelled
          return {
            abort: () => {
              // This function is entered if the user has tapped the cancel button
              request.abort();
              // Let FilePond know the request has been cancelled
              abort();
            }
          };
        },
        revert: (uniqueFileId, load, error) => {
          var self = this;
          const request = new XMLHttpRequest();
          const id = JSON.parse(uniqueFileId).id;
          request.open(
            "DELETE",
            "http://localhost:8080/uploadFileDelete/" + id,
            true
          );
          request.send();
          // Should remove the earlier created temp file here ... Can call the error
          // method if something is wrong, should exit after
          error("oh my goodness");

          // Should call the load method when done, no parameters required
          load();
          request.onload = function() {
            self.$store.commit(Constant.DELETE_FILELIST, {
              filedelete: JSON.parse(uniqueFileId).id
            });
            console.log("commit 후");
          };
        }
      }
    };
  },
  methods: {
    handleFilePondInit: function() {
      console.log("FilePond has initialized");
    }
  },
  components: {
    FilePond
  }
};
</script>
<style scoped>
#app {
  margin: 5vmax 10vmax;
}
</style>