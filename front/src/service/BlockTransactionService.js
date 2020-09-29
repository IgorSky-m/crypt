import http from "@/http-commons"
import axios from "axios";
class BlockTransactionService {

  getAllWalletTransactions(walletId) {
    
    return http.get(`/wallets/${walletId}/transactions`
    );
  }



sendTransaction(Transaction) {
  return http.post('/transactions',Transaction)
}
}

export default new BlockTransactionService();
