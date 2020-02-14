import axios from "axios";

export default axios.create({
  baseURL: "http://localhost:8080/backend/", // local
  // baseURL: "http://13.125.94.200/backend/", // aws
  headers: {
    "Content-type": "application/json"
  }
});
