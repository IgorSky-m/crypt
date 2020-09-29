<template>

  <form class="card auth-card" @submit.prevent="onSubmit">
    <div class="card-content">
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
                    v-else-if="this.responseUser.userName === 'already exist'"
                    >{{this.responseUser.userName}}
                  </small>
        </div>
<!--EMAIL field  -->
        <div class="input-field">
          <input
              id="email"
              type="text"
              v-model.trim="email"
              :class="{invalid: ($v.email.$dirty && !$v.email.required) || ($v.email.$dirty && !$v.email.email)}"
          >
          <label for="email">Email</label>

          <small
            class="helper-text invalid"
            v-if="$v.email.$dirty && !$v.email.required"
            >Enter email
          </small>

                  <small
                    class="helper-text invalid"
                    v-else-if="$v.email.$dirty && !$v.email.email"
                    >Incorrect email
                  </small>
                  <small
                    class="helper-text invalid"
                    v-else-if="this.responseUser.emailAddress === 'already exist'"
                    >{{this.responseUser.emailAddress}}
                  </small>

        </div>

<!--Password field  -->
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
<!-- Mobile field -->
<div class="input-field">
  <input
      id="mobile"
      type="number"
      v-model.trim="mobile"
      :class="{invalid: ($v.mobile.$dirty && !$v.mobile.required)}"
  >
  <label for="mobile">Mobile</label>
  <small
    class="helper-text invalid"
    v-if="$v.mobile.$dirty && !$v.mobile.required"
    >Enter mobile
  </small>
  <small
    class="helper-text invalid"
    v-else-if="this.responseUser.mobile === 'already exist'"
    >{{this.responseUser.mobile}}
  </small>
</div>


      <p>
        <label>
          <input type="checkbox" v-model="agree" />
          <span>Agreements</span>
        </label>
      </p>
    </div>
    <div class="card-action">
      <div>
        <button
            class="btn waves-effect waves-light auth-submit blue"
            type="submit"
        >
          Create account
          <i class="material-icons right">send</i>
        </button>
      </div>

      <p class="center">
        Have account?
        <router-link to="/login">Login!</router-link>
      </p>
    </div>
  </form>

</template>

<script>
import UserService from "@/service/UserService";
import {email, required, minLength} from 'vuelidate/lib/validators'
export default {
  name: 'register',
  data: () => ({
    serverErrorMsg: 'already exist',
    email: '',
    login: '',
    password: '',
    mobile: '',
    agree: false,
    responseUser: {
      id : '',
      userName: '',
      userPassword: '',
      emailAddress: '',
      mobile: ''
    }
  }),
  validations: {
    email: {email, required},
    login: {required, minLength: minLength(4)},
    password: {required, minLength: minLength(6)},
    agree: {checked: v => v},
    mobile: {required}
  },
  methods: {
    generateUUID () {
      return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
      (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
    )

      console.log(uuid)
      return uuid
    },
    onSubmit () {

      if (this.$v.$invalid) {
        this.$v.$touch()
        return
      }
      const formData = {
        id : this.generateUUID(),
        userName: this.login,
        userPassword: this.password,
        emailAddress: this.email,
        mobile: this.mobile
      }

      UserService.create(formData)
      .then(response => {
         console.log(response.data);
         this.responseUser = response.data
         if (this.responseUser.userName !== this.serverErrorMsg && this.responseUser.emailAddress !== this.serverErrorMsg && this.responseUser.mobile !== this.serverErrorMsg) {
           this.$router.push('/login?message=reg-success')
         }
       })
       .catch(e => {
         console.log(e);
       });


//console.log(formData);
//
    },
  }
}

</script>
