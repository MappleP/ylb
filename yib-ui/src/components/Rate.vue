<template>
  <span>{{ rate | fmtDecimal3 }}%</span>
</template>

<script>
import Vue from "vue";
import filters from "@/utils/filters";

export default {
  name: "Rate",
  data() {
    return {
      rate: 0
    }
  },
  created() {
    Vue.axios.get("/product/rate").then(resp => {
      this.rate = resp.data;
    })
  },
  // 过滤器对数据进行格式化处理
  filters // 相当于filters: filters
}
</script>

<style scoped>

</style>