<template>
  <div>
    <div class="page-title">
      <h3>Wallets</h3>
    </div>
    <section>
      <div class="row">
      <WalletCreate/>

        <!-- <WalletEdit/> -->
      </div>
    </section>
  </div>
</template>

<script>
import WalletCreate from '@/components/WalletCreate'
import WalletEdit from '@/components/WalletEdit'

  export default {
    components: {
      WalletCreate, WalletEdit,
    }


  }

</script>
