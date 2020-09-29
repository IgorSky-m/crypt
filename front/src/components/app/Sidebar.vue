<template>
  <ul class="sidenav app-sidenav" :class="{open: value}">
    <li><i class="material-icons white-text">access_time</i>{{date | date('datetime')}}</li>
    <router-link
      v-for="link in links"
      :key="link.url"
      tag="li"
      active-class="active"
      :to="link.url"
      :exact="link.exact"
    >
      <a href="#" class="waves-effect waves-orange pointer">  <i class="material-icons left">{{link.icon}}</i>{{link.title}}</a>
    </router-link>


  </ul>

</template>

<script>
export default {
  props: [
    'value'
  ],
  data: () => ({
    links: [
      { title: 'Account', url: '/home', exact: true, icon: 'poll' },
      { title: 'History', url: '/history', icon: 'history' },
      { title: 'Mining', url: '/mining', icon: 'memory' },
      { title: 'New transaction', url: '/record', icon: 'add_circle_outline' },
      { title: 'Wallets', url: '/wallets', icon: 'account_balance_wallet' }
    ],
    date: new Date(),
    interval: null,
    dropdown: null
  }),
  mounted () {
    this.interval = setInterval(() =>{
      this.date = new Date()
    } , 1000)
  },
  beforeDestroy() {
    console.log('before destroy'),
    clearInterval(this.interval)
  }
}
</script>
