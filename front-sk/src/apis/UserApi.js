/*
 User API 예시
 */
import http from "../http-common";

const requestLogin = (data, callback, errorCallback) => {
  //백앤드와 로그인 통신하는 부분
  http
    .post("/user/login", null, {
      params: {
        email: data.email,
        password: data.password
      }
    })
    .then(response => callback(response))
    .catch(exp => errorCallback(exp));
};
const requestSignup = (data, callback, errorCallback) => {
  //백앤드와 회원가입 통신하는 부분
  http
    .post("/user/signup", null, {
      params: {
        email: data.email,
        password: data.password,
        nickname: data.nickname
      }
    })
    .then(response => callback(response.data.data))
    .catch(exp => errorCallback(exp));
};

const requestGetPassword = (data, callback, errorCallback) => {
  //백앤드와 이전 비밀번호 통신하는 부분
  // http
  //   .post("/user/getPw", data)
  //   .then(response => callback(response.data.data))
  //   .catch(exp => errorCallback(exp));
};

const requestCheckEmail = (data, callback, errorCallback) => {
  // 이메일 중복체크
  console.log(data.email);
  http
    .post("/user/checkEmail", null, {
      params: {
        email: data.email
      }
    })
    .then(res => callback(res.data.data))
    .catch(exp => errorCallback(exp));
};
const requestChangePassword = (data, callback, errorCallback) => {
  //백앤드와 비밀번호 변경 통신하는 부분
  // http
  //   .put("/user/changePw", {
  // params: {
  //   email: data.email
  //  befPassword: data.befPassword
  //  password: data.password
  // })
  //   .then(response => callback(response.data.data))
  //   .catch(exp => errorCallback(exp));
};
const UserApi = {
  requestLogin: (data, callback, errorCallback) =>
    requestLogin(data, callback, errorCallback),
  requestSignup: (data, callback, errorCallback) =>
    requestSignup(data, callback, errorCallback),
  requestGetPassword: (data, callback, errorCallback) =>
    requestGetPassword(data, callback, errorCallback),
  requestCheckEmail: (data, callback, errorCallback) =>
    requestCheckEmail(data, callback, errorCallback),
  requestChangePassword: (data, callback, errorCallback) =>
    requestChangePassword(data, callback, errorCallback)
};

export default UserApi;
