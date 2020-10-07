<template>
  <div>
    <div class="page-title">
      <h3>Account</h3>

      <button class="btn waves-effect waves-light btn-small" @click.prevent="reload()">
        <i class="material-icons">refresh</i>
      </button>
    </div>

    <div class="row">
      <div class="col s12 m6 l8">
         <div class="card light-blue bill-card  z-depth-3">
          <div class="card-content white-text">
            <span class="card-title">Wallets</span>
            <table class="highlight">
       <thead>
         <tr>
             <th>Wallet id</th>
             <th>Amount</th>
         </tr>
       </thead>

       <tbody>
         <!-- проваливаемся в history, отфильтрованную по валлету   -->
         <router-link tag="tr" to="/history" v-for="wallet in displayedPosts" :key="wallet.id">
           <td>{{wallet.id}}</td>
           <td>{{wallet.amount}}</td>
         </router-link>
       </tbody>
     </table>

          </div>
         </div>
      </div>







      <div class="col s12 m6 l4">
        <div class="card orange darken-3 bill-card z-depth-3">
          <div class="card-content white-text">
            <div class="card-header">
              <span class="card-title">Total amount</span>
            </div>
            <table>
              <thead>
              <tr>
                <th>Coin</th>
                <th>Amount</th>
              </tr>
              </thead>

              <tbody>
              <tr>
                <td><p class="currency-line">
                  <span>IGS Coin </span>
                </p></td>

                  <td><p class="currency-line"><span>{{this.$store.state.totalAmount}}</span>
                </p></td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>


    </div>
    <ul class="pagination">
       <li class="waves-effect page-link"><a href="#!"  v-if="page != 1" @click.prevent="page--"><i class="material-icons">chevron_left</i></a></li>

       <li class="waves-effect page-link" v-for="pageNumber in pages" @click.prevent="page = pageNumber"><a href="#!">{{pageNumber}}</a></li>

       <li class="waves-effect page-link" @click.prevent="page++" v-if="page < pages.length"><a href="#!"><i class="material-icons">chevron_right</i></a></li>
     </ul>

  </div>

</template>
<script>
import UserService from '@/service/UserService'
export default {
  name: 'Home',
  data: () => ({
    posts : [],
      page: 1,
      perPage: 3,
      pages: [],
      currentPageClass:'' ,
  }),
  methods: {
    rememberMe () {
      this.remember = !this.remember
    },
    reload () {
      window.location.reload()
    },
  getPosts () {
    let data = [];
      this.posts = this.$store.state.wallets;

  },
  setPages () {
    let numberOfPages = Math.ceil(this.posts.length / this.perPage);
    for (let index = 1; index <= numberOfPages; index++) {
      this.pages.push(index);
    }
  },
  paginate (posts) {
    let page = this.page;
    let perPage = this.perPage;
    let from = (page * perPage) - perPage;
    let to = (page * perPage);
    return  posts.slice(from, to);
  },
},
computed: {
  displayedPosts () {
    return this.paginate(this.$store.state.wallets);
  }
},
watch: {
  posts () {
    this.setPages();
  }
},
created(){
  this.getPosts();
},
filters: {
  trimWords(value){
    return value.split(" ").splice(0,20).join(" ") + '...';
  }
},
}
</script>
