<template>
  <div>
    <div class="page-title">
      <h3>Transaction details</h3>
    </div>

    <table class="highlight border">
      <thead>

<!--
        <tr class="card light-blue z-depth-3 white-text">

        <th>Param</th>
        <th>Value</th>

      </tr> -->

      </thead>
      <tbody>

        <tr>
        <td><b>Transaction hash</b></td>
        <td>{{this.$store.state.transactionDetail.hash}}</td>
      </tr>
      <tr>
      <td><b>Block hash</b></td>
      <td>{{this.$store.state.transactionDetail.blockHash}}</td>
    </tr>
    <tr>
    <td><b>Signature</b></td>
    <td>{{this.$store.state.transactionDetail.signature}}</td>
  </tr>
  <tr>
  <td><b>Timestamp</b></td>
  <td>{{this.$store.state.transactionDetail.timestamp}}</td>
</tr>
<tr>
<td><b>Value</b></td>
<td>{{this.$store.state.transactionDetail.value}}</td>
</tr>
<tr>
<td><b>Current status</b></td>
<td>{{this.$store.state.transactionDetail.status}}</td>
</tr>
<tr>
<td><b>Wallet to</b></td>
<td>{{this.$store.state.transactionDetail.walletIdTo}}</td>
</tr>


      </tr>
      </tbody>
    </table>
<div class="input-field">
</div>
<div>
      <button class="btn waves-effect waves-light blue" type="submit" @click.prevent="goBack()" >
        Back
        <i class="material-icons right">arrow_back</i>
      </button>
</div>
  </div>
</template>



<script>
import AuthService from "@/service/AuthService";
import {email, required, minLength} from 'vuelidate/lib/validators'
import messages from '@/utills/messages'
import axios from 'axios'
import UserService from '@/service/UserService'
export default {
  name: 'details',
  data: () => ({
    userName: '',
    emailAddress: '',
    mobile: '',


  }),
  validations: {
    login: {required, minLength: minLength(4)},
    password: {required, minLength: minLength(6)}
  },
  created: {

  },
  mounted () {
    if (messages[this.$route.query.message]) {
      this.$message(messages[this.$route.query.message])
    }
  },
  methods: {
    goBack(){
      this.$router.push('/history')
    }
  }
}

</script>
