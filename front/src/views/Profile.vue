<template>
  <div>
    <div class="page-title">
      <h3>Profile</h3>
    </div>

    <form class="form">

      <div class="row">
      <div class="input-field col s12">
         <input disabled :value=this.$store.state.user.id id="disabled" type="text" class="validate">
        <label class="active" for="disabled">id</label>
      </div>
    </div>

      <div class="row">
      <div class="input-field col s12">
        <input :value=this.$store.state.user.userName id="profile_user_name" type="text" class="validate">
        <label class="active" for="profile_user_name">Name</label>
      </div>

    </div>
    <div class="row">
    <div class="input-field col s12">
      <input :value=this.$store.state.user.emailAddress id="profile_user_email" type="text" class="validate">
      <label class="active" for="profile_user_email">Email address</label>
    </div>

  </div>
  <div class="row">
  <div class="input-field col s12">
    <input :value=this.$store.state.user.mobile id="profile_user_phone" type="text" class="validate">
    <label class="active" for="profile_user_phone">Phone number</label>
  </div>

  </div>


      <button class="btn waves-effect waves-light blue" type="submit" >
        Save changes
        <i class="material-icons right">send</i>
      </button>
    </form>
  </div>
</template>



<script>
import AuthService from "@/service/AuthService";
import {email, required, minLength} from 'vuelidate/lib/validators'
import messages from '@/utills/messages'
import axios from 'axios'
import UserService from '@/service/UserService'
export default {
  name: 'profile',
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
  }
}

</script>
