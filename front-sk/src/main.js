import Vue from "vue";
import App from "./App.vue";
import Router from "vue-router";
import routes from "./routes";
import store from "./vuex/store";
import vuetify from "./plugins/vuetify";
import "roboto-fontface/css/roboto/roboto-fontface.css";
import "@mdi/font/css/materialdesignicons.css";
import "fullpage.js/vendors/scrolloverflow"; // Optional. When using scrollOverflow:true
import VueFullPage from "vue-fullpage.js";
import Carousel3d from "vue-carousel-3d";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faUserSecret } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import vueGoogleMapWrapper from "@/plugins/vue-google-map-wrapper";
import config from "../config";

Vue.config.productionTip = false;

Vue.use(Router);
Vue.use(VueFullPage);
Vue.use(Carousel3d);
Vue.use(vueGoogleMapWrapper, {
  apiKey: config.apiKey
});
library.add(faUserSecret);
Vue.component("font-awesome-icon", FontAwesomeIcon);

Vue.config.productionTip = false;

const router = new Router({
  mode: "history",
  base: process.env.BASE_URL,
  scrollBehavior(to, from, savedPosition) {
    return { x: 0, y: 0 };
  },
  routes
});

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount("#app");
