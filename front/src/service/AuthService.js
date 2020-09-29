import http from "@/http-commons"

class AuthService {



  auth(data) {
    return http.post("/authenticate", data);
  }

  //checkAuth()



}

export default new AuthService();
