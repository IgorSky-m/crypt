<template>
  <div>
    <div class="page-title">
      <h3>Profile</h3>
    </div>

    <form class="form">

<!-- this is user id, it need for tests only   -->
      <!-- <div class="row">
      <div class="input-field col s12">
         <input disabled :value=this.$store.state.user.id id="disabled" type="text" class="validate">
        <label class="active" for="disabled">id</label>
      </div>
    </div> -->


    <div class="col s12 m8 offset-m2 l6 offset-l3">
       <!-- <div class="card-panel grey lighten-5 z-depth-1"> -->
       <div class="file-field input-field">


         <div class="row valign-wrapper">
           <div class="col s2">
             <img :src="`${publicPath}cycle.png`" alt="" class="circle responsive-img"> <!-- notice the "circle" class -->
           </div>
           <div class="col s10">
             <span class="black-text">
               {{this.$store.state.user.userName}}
             </span>



                <input type="file" :model="avatar">


            </div>

           </div>

         </div>

       <!-- </div> -->
     </div>




    <div class="row">
    <div class="input-field col s12">
      <input :value=this.$store.state.user.emailAddress id="profile_user_email" type="text" :model="this.sustomEmailAddress" class="validate">
      <label class="active" for="profile_user_email">Email address</label>
    </div>

  </div>
  <div class="row">
  <div class="input-field col s12">
    <input :value=this.$store.state.user.mobile id="profile_user_phone" type="text" :model="mobilePhone" class="validate">
    <label class="active" for="profile_user_phone">Phone number</label>
  </div>

  </div>

  <div class="row">
  <div class="input-field col s12">
    <input id="password" type="password" class="validate" :model="password">
    <label class="active" for="password">New password</label>
  </div>

  </div>


      <button class="btn waves-effect waves-light blue" type="submit" @click="updateUser" >
        Save changes
        <i class="material-icons right">send</i>
      </button>
    </form>
  </div>
</template>



<script>
import AuthService from "@/service/AuthService"
import UserService from '@/service/UserService'
import {email, required, minLength} from 'vuelidate/lib/validators'
import messages from '@/utills/messages'
import axios from 'axios'

export default {
  name: 'profile',
  data: () => ({
    avatar: '',
    userName: '',
    emailAddress: '',
    mobile: '',
    password:'',
    publicPath: process.env.BASE_URL


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
    updateUser(){
      const formData = {
        id : this.$store.state.user.id,
        userName: this.$store.state.user.userName,
        userPassword: this.password,
        emailAddress: this.emailAddress,
        mobile: this.mobile
      }

      UserService.updateAvatar(this.$store.state.user.id, avatar)
      .then(response => {
        this.$store.state.avatar = response.data
      })
      .catch(e => {
         console.log(e);
       });

      UserService.update(this.$store.state.user.id, formData)
      .then(response => {
        this.$store.state.user = response.data;
      })
      .catch(e => {
         console.log(e);
       });

    },
  }
}

</script>
