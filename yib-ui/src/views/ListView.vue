<template>
  <div>
    <Header/>
    <div class="content clearfix">
      <!--排行榜-->
      <ul class="rank-list">
        <li v-for="top,index in top3">
          <img :src="'/image/list-rank'+(index+1)+'.png'" alt="">
          <p class="rank-list-phone">{{ top.phone | fmtPhone }}</p>
          <span>{{ top.money | fmtMoney }}元</span>
        </li>
      </ul>
      <ul class="preferred-select clearfix">
        <ProductItem v-for="pro in products" :product="pro"/>
      </ul>
    </div>
    <Footer/>
  </div>
</template>
<script>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import ProductItem from "@/components/ProductItem";
import Vue from "vue";
import filters from "@/utils/filters";

export default {
  name: "ListView",
  components: {ProductItem, Footer, Header},
  data() {
    return {
      products: [],
      top3: [],
    }
  },
  created() {
    this.load();//不要总是犯低级错误，服了
  },
  methods: {//methods中的s不要忘了，服了
    load() {
      Vue.axios.get('/product/list?type=' + this.$route.query.type).then(resp => {
        this.products = resp.data;
      })
      Vue.axios.get("/bid/top3").then(resp => {
        this.top3 = resp.data;
      })
    }
  },
  filters,
  watch: {
    $route(to, from) {
      //对路由变化作出响应
      this.load();
    }
  }
}
</script>
<style scoped>

</style>