<template>
  <div>
    <div class="page-title">
      <h3>Account</h3>

      <button class="btn waves-effect waves-light btn-small" @click.prevent="reload()">
        <i class="material-icons">refresh</i>
      </button>
    </div>

    <div class="row">
      <div class="col s12 m6 l8">
         <div class="card light-blue  z-depth-3">
          <div class="card-content white-text">
            <span class="card-title">Wallets</span>
            <table class="highlight">
       <thead>
         <tr>
             <th>Wallet id</th>
             <th>Amount</th>
         </tr>
       </thead>

       <tbody>
         <!-- проваливаемся в history, отфильтрованную по валлету   -->
         <router-link tag="tr" to="/history" v-for="wallet in this.$store.state.wallets" :key="wallet.id">
           <td>{{wallet.id}}</td>
           <td>{{wallet.amount}}</td>
         </router-link>
       </tbody>
     </table>

          </div>
         </div>
      </div>





      <div class="col s12 m6 l4">
        <div class="card orange darken-3 bill-card z-depth-3">
          <div class="card-content white-text">
            <div class="card-header">
              <span class="card-title">Total amount</span>
            </div>
            <table>
              <thead>
              <tr>
                <th>Coin</th>
                <th>Amount</th>
              </tr>
              </thead>

              <tbody>
              <tr>
                <td><p class="currency-line">
                  <span>IGS Coin </span>
                </p>IGS Coin</td>
                <td>{{this.$store.state.totalAmount}}</td>

              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import UserService from '@/service/UserService'
export default {
  name: 'Home',
  methods: {
    rememberMe () {
      this.remember = !this.remember
    },
    reload () {
      window.location.reload()
    }
  },
  mounted () {

  },
  created() {

  }
}
</script>
