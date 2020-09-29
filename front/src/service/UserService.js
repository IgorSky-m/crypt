import http from "@/http-commons"
import axios from "axios";
class UserService {

  getUrl() {
    return this.$store.state.baseURL
}
  get(id) {
    return http.get(`/users/${id}`);
  }

   getUserInfo() {
    //работае только с хардкодом адреса
    return axios.get("http://localhost:9000/users/info"
    );
  }

  getUserWallets(id) {

   return http.get(`/users/${id}/wallets`
   );

 }



  hello() {
    return http.get("/hello")
  }

  create(data) {
    return http.post("/users", data);
  }

  update(id, data) {
    return http.put(`/users/${id}`, data);
  }

  delete(id) {
    return http.delete(`/users/${id}`);
  }


}

export default new UserService();
