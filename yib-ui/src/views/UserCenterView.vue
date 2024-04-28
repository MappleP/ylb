<template>
  <div>
    <Header/>
    <div class="content clearfix">
      <!--个人信息-->
      <div class="user-head">
        <div class="user-head-left fl">
          <div class="user-head-img">
            <label for="headerImage">
              <img :src="src"/>
            </label>
          </div>
          <p>上传头像</p>
          <input id="headerImage" @change="upload" accept="image/*" type="file" style="display: none"/>
        </div>
        <div class="user-head-right fl">
          <ul class="user-head-name fl">
            <li><b>{{ user.name }}</b></li>
            <li>{{ user.phone }}</li>
            <li>最近登录:{{ user.lastLoginTime }}</li>
          </ul>
          <div class="user-head-money fr">
            <p>可用余额：<span>￥{{ balance | fmtMoney }}元</span></p>
            <router-link to="/pay" class="user-head-a1">充值</router-link>
            <router-link to="/index" class="user-head-a2">投资</router-link>
          </div>
        </div>

      </div>
      <!--记录-->
      <div class="user-record-box clearfix">
        <div class="user-record user-record-1">
          <h3 class="user-record-title">最近投资</h3>
          <table align="center" width="388" border="0" cellspacing="0" cellpadding="0">
            <thead class="datail_thead">
            <tr>
              <th>序号</th>
              <th>投资产品</th>
              <th>投资金额</th>
              <th>投资时间</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(record,index) in bidRecords">
              <td>{{ index + 1 }}</td>
              <td>{{ record.productName }}</td>
              <td>{{ record.bidMoney }}</td>
              <td>{{ record.bidTime }}</td>
            </tr>
            </tbody>
          </table>
          <!--无记录-->
          <p class="user-record-no">还没有投资记录，请投资：<a href="user_center.html" target="_blank">投资</a></p>
        </div>
        <div class="user-record user-record-2">
          <h3 class="user-record-title">最近充值</h3>
          <table align="center" width="388" border="0" cellspacing="0" cellpadding="0">
            <thead class="datail_thead">
            <tr>
              <th>序号</th>
              <th>充值描述</th>
              <th>充值金额</th>
              <th>充值时间</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td>1</td>
              <td>新手宝</td>
              <td>1500.0</td>
              <td>2021-08-19</td>
            </tr>
            <tr>
              <td>2</td>
              <td>新手宝</td>
              <td>1500.0</td>
              <td>2021-08-19</td>
            </tr>
            <tr>
              <td>3</td>
              <td>新手宝</td>
              <td>1500.0</td>
              <td>2021-08-19</td>
            </tr>
            <tr>
              <td>4</td>
              <td>新手宝</td>
              <td>1500.0</td>
              <td>2021-08-19</td>
            </tr>
            <tr>
              <td>5</td>
              <td>新手宝</td>
              <td>1500.0</td>
              <td>2021-08-19</td>
            </tr>
            <tr>
              <td>6</td>
              <td>新手宝</td>
              <td>1500.0</td>
              <td>2021-08-19</td>
            </tr>
            </tbody>
          </table>
          <!--无记录-->
          <p class="user-record-no">还没有充值记录，请充值：<a href="user_pay.html" target="_blank">充值</a></p>
        </div>
        <div class="user-record user-record-3">
          <h3 class="user-record-title ">最近收益</h3>
          <table align="center" width="388" border="0" cellspacing="0" cellpadding="0">
            <thead class="datail_thead">
            <tr>
              <th>序号</th>
              <th>项目名称</th>
              <th>投资日期</th>
              <th>收益金额</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td>1</td>
              <td>新手宝</td>
              <td>2021-08-19</td>
              <td>0.46</td>
            </tr>
            <tr>
              <td>2</td>
              <td>新手宝</td>
              <td>2021-08-19</td>
              <td>0.46</td>
            </tr>
            <tr>
              <td>3</td>
              <td>新手宝</td>
              <td>2021-08-19</td>
              <td>0.46</td>
            </tr>
            <tr>
              <td>4</td>
              <td>新手宝</td>
              <td>2021-08-19</td>
              <td>0.46</td>
            </tr>
            <tr>
              <td>5</td>
              <td>新手宝</td>
              <td>2021-08-19</td>
              <td>0.46</td>
            </tr>
            <tr>
              <td>6</td>
              <td>新手宝</td>
              <td>2021-08-19</td>
              <td>0.46</td>
            </tr>
            </tbody>
          </table>
          <!--无记录-->
          <p class="user-record-no">还没有收益记录</p>
        </div>
      </div>
    </div>

    <Footer/>

  </div>
</template>
<script>
import Footer from "@/components/Footer.vue";
import Header from "@/components/Header.vue";
import Vue from "vue";
import filters from "@/utils/filters";

export default {
  name: 'UserCenter',
  components: {Header, Footer},
  data() {
    return {
      user: {},
      balance: '',
      bidRecords: [],
    }
  },
  computed: {
    src() {
      return this.user.headerImage || '/img/user-head.png';
    },
  },
  created() {
    Vue.axios.get("/user/info").then(resp => this.user = resp.data);
    Vue.axios.get("/account/balance").then(resp => this.balance = resp.data);
    Vue.axios.get("/bid/records2").then(resp => this.bidRecords = resp.data);
  },
  filters,
  methods: {
    upload() {
      let files = headerImage.files;
      if (files.length) {
        Vue.axios.postForm('/user/upload', {
          file: files[0]
        }).then(resp => {
          this.user.headerImage = resp.data + "?" + Math.random();
        })
      }
    }
  }
}
</script>

<style scoped>

</style>