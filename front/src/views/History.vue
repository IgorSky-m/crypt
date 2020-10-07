<template>

<div>
<div class="page-title">
  <h3>History</h3>
</div>




<div class="history-chart">

</div>
<section>
  <span>page {{page}} of {{Math.ceil(this.posts.length / this.perPage)}}</span>
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
      <tr v-for="walletTransactions in displayedPosts" :key="walletTransactions.hash">

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

  <ul class="pagination">
     <li class="waves-effect page-link"><a href="#!"  v-if="page != 1" @click.prevent="page--"><i class="material-icons">chevron_left</i></a></li>

     <li class="waves-effect page-link" v-for="pageNumber in pages" @click.prevent="page = pageNumber"><a href="#!">{{pageNumber}}</a></li>

     <li class="waves-effect page-link" @click.prevent="page++" v-if="page < pages.length"><a href="#!"><i class="material-icons">chevron_right</i></a></li>
   </ul>





</section>
</div>
</template>
<script>
import paginationMixin from '@/mixins/pagination.mixin'
export default {
  data: () => ({
    path: 'transaction/',
    mixins: [paginationMixin],
    posts : [''],
    unique: [],
			page: 1,
			perPage: 5,
			pages: [],
      currentPageClass:'' ,
  }),
  methods: {
    checkStatus(status) {
      if (status == "SAVED" ) return "white-text badge green"
      else if ( status == "APPROVED" || status == "IN_PROGRESS") return "white-text badge blue"
      else return "white-text badge red"
    },
    pageChangeHandler() {

    },
    showDetail(detail) {
      this.$store.state.transactionDetail = detail
    },
    getPosts () {
      let data = [];
       let uniqArr = Array.from(new Set(this.$store.state.transactions));
        this.posts = uniqArr;

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
    curPage() {
      if (page) return "active"
      return "waves-effect page-link active"
    },
    getUniqTransactions(transactions) {
      let results = [];

      transactions.forEach(function (value) {
        
        if (results.indexOf(value) === -1) {
            results.push(value);
        }
      });

      return results;
    },
	},
	computed: {
		displayedPosts () {
			return this.paginate(this.posts);
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
