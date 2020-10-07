<template>
<div class="col s12 m6">
  <div >
    <div class="page-subtitle ">
      <h4>Generate new wallet</h4>
    </div>

    <table v-if="this.$store.state.wallet" class="highlight border">
      <thead>


        <!-- <tr class="card light-blue z-depth-3 white-text">

        <th>Param</th>
        <th>Value</th>

      </tr> -->

      </thead>
      <tbody>

        <tr>
        <td><b>Wallet id</b></td>
        <td>{{this.$store.state.wallet.id}}</td>
      </tr>
      <tr>
      <td><b>secret key</b></td>
      <td>{{this.$store.state.wallet.secretKey}}</td>
    </tr>

  </tbody>
</table>



    <form>
      <div class="input-field">
        <!-- <input type="text" id="name">
        <label for="name">wallet password</label>
        <span class="helper-text invalid">dsa</span> -->
      </div>

      <tr>
        <td>


            <button class="btn waves-effect waves-light blue" type="submit" @click.prevent="generateWallet()">
              Generate
              <i class="material-icons right">cached</i>
            </button></td>

            <td>  <button class="btn waves-effect waves-light blue" type="submit" @click.prevent="saveWallet()">
                save
                <i class="material-icons right">send</i>
              </button></td>
      </tr>


    </form>
  </div>
</div>
</template>
<script>

import WalletService from '@/service/WalletService'
  export default {
    data : () => ({
    wallet : null
  }),
  methods: {
    generateWallet() {
      WalletService.generateWallet().then(response => {
         this.$store.state.wallet = response.data
         console.log(this.$store.state.wallet)
       })
       .catch(e => {
         console.log(e);
       });
    },
    saveWallet() {
      WalletService.postWallet(this.$store.state.wallet).then(response => {
         this.$store.state.wallet = null
         console.log(this.$store.state.wallet)
            this.$router.push('/home')
            window.location.reload()
       })
       .catch(e => {
         console.log(e);
       });
     }
  }
}
</script>
