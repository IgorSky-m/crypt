<template>
  <form class="card auth-card" @submit.prevent="onSubmit">
    <div class="card-content ">
      <!-- <span class="card-title"><i class="material-icons left">supervisor_account</i>IGS Crypto</span> -->
      <div class="container center">
       <img src="logo-sign.png" alt="" class="responsive-img" width="90%" heigth="90%"> <!-- notice the "circle" class -->
     </div>
<!-- Login field -->
      <div class="input-field">
        <input
            id="login"
            type="text"
            v-model.trim="login"
            :class="{invalid: ($v.login.$dirty && !$v.login.required) || ($v.login.$dirty && !$v.login.minLength)}"
        >
        <label for="login">Login</label>
        <small
          class="helper-text invalid"
          v-if="$v.login.$dirty && !$v.login.required"
          >Enter login
        </small>

                <small
                  class="helper-text invalid"
                  v-else-if="$v.login.$dirty && !$v.login.minLength"
                  >Too small login: {{login.length}} chars. Minimal length is {{$v.login.$params.minLength.min}} symbols
                </small>
                <small
                  class="helper-text invalid"
                  v-else-if="serverCheckError"
                  >invalid login or password
                </small>
      </div>



<!-- Password field -->
          <div class="input-field">
        <input
            id="password"
            type="password"
            v-model.trim="password"
            :class="{invalid: ($v.password.$dirty && !$v.password.required) || ($v.password.$dirty && !$v.password.minLength)}"
        >

        <label for="password">Password</label>
        <small
          class="helper-text invalid"
          v-if="$v.password.$dirty && !$v.password.required"
          >Enter password
        </small>

                <small
                  class="helper-text invalid"
                  v-else-if="$v.password.$dirty && !$v.password.minLength"
                  >Too small password: {{password.length}} chars. Minimal length is {{$v.password.$params.minLength.min}} symbols
                </small>
      </div>
      <label>
        <input type="checkbox" @click="rememberMe"/>
        <span>Remember me</span>
      </label>
    </div>
    <div class="card-action">
      <div>
        <button
            class="btn waves-effect waves-light auth-submit blue"
            type="submit"
        >
          Sign in
          <i class="material-icons right">send</i>
        </button>
      </div>

      <p class="center">
        No account?
        <router-link to="/register">Sign up!</router-link>
      </p>
    </div>
  </form>
</template>

<script>
import AuthService from "@/service/AuthService";
import {email, required, minLength} from 'vuelidate/lib/validators'
import messages from '@/utills/messages'
import axios from 'axios'
import UserService from '@/service/UserService'
import Encoder from '@/service/Encoder'
export default {
  name: 'login',
  data: () => ({
    login: '',
    password: '',
    remember: false,
    serverCheckError: false
  }),
  validations: {
    login: {required, minLength: minLength(4)},
    password: {required, minLength: minLength(6)}
  },
  mounted () {
    if (messages[this.$route.query.message]) {
      this.$message(messages[this.$route.query.message])
    }
  },
  methods: {
  async onSubmit () {
      this.serverCheckError = false
      console.log();
      if (this.$v.$invalid) {
        this.$v.$touch()
        return
      }
      const formData = {
        name: this.login,
        password: this.password,
        remember: this.remember
      }
      console.log(formData)

      const auth = AuthService.auth(formData)
      .then(response => {
//         localStorage.setItem('jwtToken', response.data.jwtToken)
         if (String(response.data.jwtToken).length !== 0) {
           const token = `Bearer ${response.data.jwtToken}`

           const isAuth = true
           localStorage.setItem('isAuth', isAuth)
           localStorage.setItem('token', token)
           console.log(localStorage.token)
           this.$router.push('/home')
         }

       })
       .catch(e => {
         this.serverCheckError = true
         console.log(e);
       });


    },
     rememberMe () {
      this.remember = !this.remember
      
    }
  }
}

</script>
