<template>
  <div>
    <div class="page-title">
      <h3>New transaction</h3>
    </div>

    <Loader v-if="loading"/>

    <p v-else-if="!this.$store.state.wallets.length" class="center">Not have wallets?. <router-link to="/wallets">Create one or add new!</router-link></p>

    <form class="form" v-else>
      <div class="input-field" >
        <select ref="select" v-model="wallet">
          <option v-for="w in wallets" :key="w.id" :value="w"
          >{{w.id}}</option>
        </select>
        <label>Select wallet from</label>

      </div>



      <div class="input-field">
        <input
            id="balance"
            type="number"
            disabled

        >
        <label for="amount">{{"Current balance: "+this.wallet.amount}}</label>
        <span class="helper-text"></span>
      </div>

      <div class="input-field">
        <input
            id="walletIdTo"
            type="text"
            v-model="walletIdTo"
        >
        <label for="walletIdTo">Wallet to</label>
        <span
              class="helper-text invalid"></span>
      </div>



      <div class="input-field">
        <input
            id="amount"
            type="number"
            v-model="value"
        >
        <label for="amount">Transfer amount</label>

      </div>

      <div class="input-field">
        <input
            id="walletSecretKey"
            type="text"
            v-model="secretKey"
        >
        <label for="walletSecretKey">wallet signature</label>
        <span
              class="helper-text invalid"></span>
      </div>

      <button class="btn waves-effect waves-light blue" type="submit" @click.prevent="send()">
        Create
        <i class="material-icons right">send</i>
      </button>
    </form>
  </div>
</template>

<script>
import BlockTransactionService from '@/service/BlockTransactionService'
import Encoder from '@/service/Encoder'
export default{
  name: 'newtransaction',
  data: () => ({
    loading: true,
    wallets: [],
    select: null,
    wallet: null,
    walletIdTo: '',
    secretKey: '',
    value: ''

  }),

  async mounted () {
    this.wallets = this.$store.state.wallets,
    this.loading = false

    

    if (this.$store.state.wallets) {
      this.wallet = this.$store.state.wallets[0]
    }

    setTimeout( () => {
    this.select = M.FormSelect.init(this.$refs.select)
  },2)
  },
  destroyed () {
    if (this.select && this.select.destroy) {
      this.select.destroy()
    }
  },
  methods: {
   send () {
      const Transaction = {
        walletIdTo: this.walletIdTo,
        signature: this.secretKey,
        value: this.value,
        timestamp: Date.now()
      }


      const result =  BlockTransactionService.sendTransaction(Transaction).then(response => {
        if (response.data.status == "APPROVED") {
        this.$router.push('/record?=success')
      } else {
        this.$router.push('record?=fail')
      }

      })




    }
  }

}

</script>
