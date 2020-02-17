import axios from "axios";

export default axios.create({
  // baseURL: "https://localhost:443/backend/", // local
  baseURL: "https://i02a309.p.ssafy.io:443/backend/", // aws
  headers: {
    "Content-type": "application/json"
  }
});
