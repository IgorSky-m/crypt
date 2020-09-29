<template>

<div>
<div class="page-title">
  <h3>History</h3>
</div>
<ul class="pagination">
   <li class="disabled"><a href="#!"><i class="material-icons">chevron_left</i></a></li>
   <li class="active"><a href="#!">1</a></li>
   <li class="waves-effect"><a href="#!">2</a></li>
   <li class="waves-effect"><a href="#!">3</a></li>
   <li class="waves-effect"><a href="#!">4</a></li>
   <li class="waves-effect"><a href="#!">5</a></li>
   <li class="waves-effect"><a href="#!"><i class="material-icons">chevron_right</i></a></li>
 </ul>
<div class="history-chart">

</div>
<section>
  <table class="highlight">
    <thead>

      <tr class="card light-blue z-depth-2 white-text">
      <th>#</th>
      <th>wallet to</th>
      <th>value</th>
      <!-- <th>type</th> -->
      <th>status</th>
      <th>details</th>
    </tr>

    </thead>
    <tbody>
      <tr v-for="walletTransactions in this.$store.state.transactions" :key="walletTransactions.hash">

      <td>{{walletTransactions.hash}}</td>
      <td>{{walletTransactions.walletIdTo}}</td>
      <td>{{walletTransactions.value}}</td>
      <!-- <td>
        <span class="white-text badge green">deposit</span> <!--withdraw or deposit validate !-->
      <!--</td> -->
      <td>
        <span :class="checkStatus(walletTransactions.status)">{{walletTransactions.status}}</span>
      </td>
      <td>
        <router-link tag="tr" :to="/history/+walletTransactions.hash" >
        <button class="btn-small btn"@click="showDetail(walletTransactions)">
          <i class="material-icons">open_in_new</i>
        </button>
      </router-link>
      </td>

    </tr>
    </tbody>
  </table>
</section>
</div>
</template>
<script>
export default {
  data: () => ({
    path: 'transaction/',

  }),
  methods: {
    checkStatus(status) {
      if (status == "SAVED" ) return "white-text badge green"
      else if ( status == "APPROVED" || status == "IN_PROGRESS") return "white-text badge blue"
      else return "white-text badge red"
    },
    showDetail(detail) {
      this.$store.state.transactionDetail = detail

    }
  }
}





</script>
