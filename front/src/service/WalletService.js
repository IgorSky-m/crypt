import http from "@/http-commons"
import axios from "axios";
class WalletService {

  generateWallet() {
     return http.get('/generate/wallet')
      }

  postWallet(wallet) {
    return http.post('/wallets',wallet)
  }
}

export default new WalletService();
