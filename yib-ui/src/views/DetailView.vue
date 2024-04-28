<template>
  <div>
    <Header/>
    <div class="content clearfix">
      <div class="detail-left">
        <div class="detail-left-title">{{ product.productName }}（{{ product.productNo }}期）</div>
        <ul class="detail-left-number">
          <li>
            <span>历史年化收益率</span>
            <p><b>{{ product.rate }}</b>%</p>
            <span>历史年化收益率</span>
          </li>
          <li>
            <span>募集金额（元）</span>
            <p><b>{{ product.productMoney | fmtMoney }}</b>元</p>
            <span>募集中&nbsp;&nbsp;剩余募集金额{{ product.leftProductMoney | fmtMoney }}元</span>
          </li>
          <li>
            <span>投资周期</span>
            <p><b>{{ product.cycle }}</b>个月</p>
          </li>

        </ul>
        <div class="detail-left-way">
          <span>收益获取方式</span>
          <span>收益返还：<i>到期还本付息</i></span>
        </div>
        <!--投资记录-->
        <div class="datail-record">
          <h2 class="datail-record-title">投资记录</h2>
          <div class="datail-record-list">
            <table align="center" width="880" border="0" cellspacing="0" cellpadding="0">
              <colgroup>
                <col style="width: 72px"/>
                <col style="width: 203px"/>
                <col style="width: 251px"/>
                <col style="width: 354px"/>
              </colgroup>
              <thead class="datail_thead">
              <tr>
                <th>序号</th>
                <th>投资人</th>
                <th>投资金额（元）</th>
                <th>投资时间</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(record,i) in records">
                <td>{{ i + 1 }}</td>
                <td class="datail-record-phone">{{ record.phone | fmtPhone }}</td>
                <td>{{ record.bidMoney |fmtMoney }}</td>
                <td>{{ record.bidTime }}</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>

      </div>
      <!--右侧-->
      <div class="detail-right">
        <div class="detail-right-title">立即投资</div>
        <div class="detail-right-mode">
          <h3 class="detail-right-mode-title">收益方式</h3>
          <p class="detail-right-mode-p"><span>到期还本付息</span></p>
          <h3 class="detail-right-mode-title">我的账户可用</h3>
          <div class="detail-right-mode-rmb">
            <p>资金（元）：{{ balance }}</p>
            <router-link v-if="!isLogin" to="/">请登录</router-link>
          </div>
          <h3 class="detail-right-mode-title">预计本息收入（元）</h3>
          <form action="" id="number_submit">
            <p>请在下方输入投资金额</p>
            <input v-model="bidMoney" type="text" placeholder="请输入日投资金额，应为100元整倍数" name=""
                   class="number-money">
            <p>{{ errMsg }}</p>
            <input @click="bid" type="button" value="立即投资" class="submit-btn">
          </form>
        </div>
      </div>
    </div>
    <Footer/>
  </div>
</template>
<script>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import Vue from "vue";
import filters from "@/utils/filters";

export default {
  name: 'DetailView',
  components: {Footer, Header},
  data() {
    return {
      product: {},
      records: [],
      balance: "****",
      isLogin: !!sessionStorage.getItem("token"),
      bidMoney: '',
      errMsg: ''
    }
  },
  created() {
    this.load();
  },
  filters,
  methods: {
    load() {
      this.errMsg = '';
      let id = this.$route.params.id;
      Vue.axios.get("/product/" + id).then(resp => {
            this.product = resp.data
          }
      );
      Vue.axios.get("/bid/records?id=" + id).then(resp => {
        this.records = resp.data
      });

      //如果已登录，则获取金额
      Vue.axios.get("user/isLogin").then(resp => {
        if (resp.data) {
          Vue.axios.get("/account/balance").then(resp => {
            this.balance = resp.data;
          })
        }
      })

    },
    bid() {
      if (this.bidMoney == "") {
        this.errMsg = "请输入投资金额！";
      } else if (this.bidMoney % 100 != 0) {
        this.errMsg = "投资金额必须是100的整数倍！";
      } else if (this.bidMoney < this.product.bidMinLimit || this.bidMoney > this.product.bidMaxLimit) {
        this.errMsg = `投资金额必须介于${this.product.bidMinLimit}~${this.product.bidMaxLimit}！`;
      } else {
        let id = this.$route.params.id;
        Vue.axios.post('/bid/bid', `bidMoney=${this.bidMoney}&pid=${id}`).then(resp => {
          if (resp.data.success) {
            // ...
            this.load(); // 重新加载数据
            this.$alert('投资成功！', '操作提示');
          }
        })
      }
    },
  }
}

</script>

<style scoped>

</style>