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

Vue.config.productionTip = false;

Vue.use(Router);
Vue.use(VueFullPage);
Vue.use(Carousel3d);

const router = new Router({
  routes
});

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount("#app");
