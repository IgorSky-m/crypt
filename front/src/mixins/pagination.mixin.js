import _ from 'lodash'

export default {
  data ()  {
    return {
      page: this.$store.state.page,
      pageSize: this.$store.state.pageSize,
      pageCount: 0,
      allItems: [],
      items: [],

    }
  },
  methods: {
    setupPagination(allItems){
        this.$store.state.allItems = _.chunk(allItems,this.pageSize)
      this.$store.state.pageCount = _.size(this.allItems)
        this.$store.state.items = this.allItems[this.page -1] || this.allItems[0]


    
    }
  }
}
