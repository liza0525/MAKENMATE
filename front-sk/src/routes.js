import Login from "./views/user/Login.vue";
import Join from "./views/user/Join.vue";
import Components from "./views/Components.vue";
import InputEmail from "./views/user/InputEmail.vue";
import SuccessPage from "./views/user/SuccessPage.vue";
import SendEmail from "./views/user/SendEmail.vue";
import PageNotFound from "./views/PageNotFound.vue";
import Mypage from "./views/user/Mypage.vue";
import CocktailList from "./views/CocktailList.vue";
import CocktailDetail from "./views/CocktailDetail.vue";
import UserProfile from "./views/user/Userprofile";
import UserChangePW from "./views/user/mypage/UserChangePW";
import UserWithdraw from "./views/user/mypage/UserWithdraw";
import BoardDetail from "./views/board/BoardDetail";
import Main from "./views/main/Main";
import Logout from "./views/user/Logout";
import BoardList from "./components/board/BoardList.vue";

export default [
  {
    path: "/",
    name: "Main",
    component: Main
  },
  {
    path: "/login",
    name: "Login",
    component: Login
  },
  {
    path: "/logout",
    name: "Logout",
    component: Logout
  },
  {
    path: "/user/join",
    name: "Join",
    component: Join
  },
  {
    path: "/components",
    name: "Components",
    component: Components
  },
  {
    path: "/user/inputemail",
    name: "InputEmail",
    component: InputEmail
  },
  {
    path: "/user/successpage",
    name: "SuccessPage",
    component: SuccessPage
  },
  {
    path: "/user/sendemail",
    name: "SendEmail",
    component: SendEmail
  },
  {
    path: "/user/mypage",
    name: "Mypage",
    component: Mypage
  },
  {
    path: "/cocktail/list/:pageNm",
    name: "CocktailList",
    component: CocktailList
  },
  {
    path: "/cocktail/detail",
    name: "CocktailDetail",
    component: CocktailDetail
  },
  {
    path: "/user/mypage/userwithdraw",
    name: "UserWithdraw",
    component: UserWithdraw
  },
  {
    path: "/user/mypage/userchangepw",
    name: "UserChangePW",
    component: UserChangePW
  },
  {
    path: "/user/userprofile",
    name: "UserProfile",
    component: UserProfile
  },
  {
    path: "*",
    redirect: "/404"
  },
  {
    path: "/404",
    name: "PageNotFound",
    component: PageNotFound
  },
  {
    path: "/boardlist",
    name: "BoardList",
    component: BoardList
  }
];
