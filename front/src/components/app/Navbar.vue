<template>

  <nav class="navbar blue lighten-1">
    <div class="nav-wrapper">
      <div class="navbar-left">
        <a href="#" @click.prevent="$emit('click')">
          <i class="material-icons white-text">dehaze</i>
        </a>
        <!-- <span class="black-text">{{date | date('datetime')}}</span> -->

      </div>
      <div class="container center">
       <img src="logo-account.png" alt="" class="responsive-img" width="19%" heigth="15%">
     </div>
      <ul class="right hide-on-small-and-down">
        <li>
          <a
              class="dropdown-trigger white-text"
              href="#"
              data-target="dropdown"
              ref="dropdown"
          >
            {{this.$store.state.user.userName}}
            <i class="material-icons left">account_circle</i>
            <i class="material-icons right">arrow_drop_down</i>

          </a>

          <ul id='dropdown' class='dropdown-content'>
            <li>
              <router-link to="/profile" class="black-text">
                <i class="material-icons grey-text">account_circle</i>Profile
              </router-link>
            </li>
            <li>
              <router-link to="/profile" class="black-text">
                <i class="material-icons grey-text">settings</i>Settings
              </router-link>
            </li>
            <li class="divider" tabindex="-1"></li>
            <li>
              <a href="#" class="black-text" @click.prevent="loguot">
                <i class="material-icons blue-text">exit_to_app</i>Loguot
              </a>
            </li>
          </ul>
        </li>
      </ul>
    </div>
  </nav>

</template>
<!-- eslint-disable -->
<script>
import axios from 'axios'
export default {

  data: ()  => ({
    date: new Date(),
    interval: null,
    dropdown: null
  }),
  methods: {
    loguot () {
      localStorage.removeItem('token')
      localStorage.removeItem('jwtToken')
      localStorage.removeItem('isAuth')
      this.$router.push('/login?message=logout')
    }
  },
  mounted () {
    this.interval = setInterval(() =>{
      this.date = new Date()
    } , 1000)
    this.dropdown = M.Dropdown.init(this.$refs.dropdown, {
      constrainWith: false,
      coverTrigger: false
    })
  },
  beforeDestroy() {
    console.log('before destroy'),
    clearInterval(this.interval)
    localStorage.removeItem('token')
    localStorage.removeItem('jwtToken')
    localStorage.removeItem('isAuth')
    this.$store.state.user = null
    if (this.dropdown && this.dropdown.destroy) {
      this.dropdown.destroy
    }
    window.location.reload()

  }
}
</script>
