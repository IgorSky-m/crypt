<template>
<div class="app-main-layout">

  <Navbar @click="isOpen = !isOpen"/>

  <Sidebar v-model="isOpen"/>

  <main class="app-content" :class = "{full: !isOpen}">
    <div class="app-page">
      <router-view/>
    </div>
  </main>

<div class="fixed-action-btn">
<router-link
class="btn-floating btn-large orange pulse" to="/record">
  <i class="large material-icons">add</i>
</router-link>
</div>
</div>

</template>

<script>
import UserService from '@/service/UserService'
import WalletService from '@/service/WalletService'
import Navbar from '@/components/app/Navbar'
import Sidebar from '@/components/app/Sidebar'
import BlockTransactionService from '@/service/BlockTransactionService'

export default {
  name: 'main-layout',
  data: () => ({
    isOpen: true
  }),
created() {

},
 mounted() {
   this.setUp()
   },
   methods: {
     async setUp() {

      await UserService.getUserInfo()
         .then(response => {

           console.log("look")
              this.$store.state.user = response.data
              console.log(this.$store.state.user);
          })
          .catch(e => {

            console.log(e);
           window.location.reload()
          });



      await UserService.getUserWallets(this.$store.state.user.id)
      .then(response => {
            this.$store.state.wallets = response.data
            console.log(this.$store.state.wallets);
      })
      .catch(e => {

        console.log(e);
    //    window.location.reload()
  });

      this.$store.state.totalAmount = await this.getTotalAmount()


      for(let i = 0; i < this.$store.state.wallets.length; i++ ) {

       await BlockTransactionService.getAllWalletTransactions(this.$store.state.wallets[i].id)
         .then(response => {
              for (let k = 0; k < response.data.length; k++)

              this.$store.state.transactions.push(response.data[k])


          })
          .catch(e => {

            console.log(e);
        //      window.location.reload()
          });

        }
        console.log(this.$store.state.transactions);


    },

     getTotalAmount() {
       let result = 0
       for(let i = 0; i < this.$store.state.wallets.length; i++ ) {
           let amount = parseInt(this.$store.state.wallets[i].amount)

           result +=amount;
       }

       return result;
     }

   },
  components: {
    Navbar, Sidebar
  }
}
</script>
