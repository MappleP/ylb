<template>
  <div>
    <Header/>
    <div class="banner-content">
      <div class="swiper-container banner-lb">
        <div class="swiper-wrapper">
          <div class="swiper-slide">
            <a href="javascript:;">
              <img src="/image/banner.jpg" alt="">
            </a>
          </div>
          <div class="swiper-slide">
            <a href="javascript:;">
              <img src="/image/banner.jpg" alt="">
            </a>
          </div>
        </div>
      </div>
      <div class="banner-abs">
        <div class="banner-abs-box">
          <div class="banner-abs-title">历史年化收益率</div>
          <b>5.61<i>%</i></b>
          <p>平台用户数</p>
          <span>39<i>位</i></span>
          <p class="banner-abs-border">累计成交金额</p>
          <span>5000.0<i>元</i></span>
        </div>
      </div>
    </div>
    <div class="banner-list">
      <ul>
        <li>
          <img src="/image/banner-tag1.png" alt="">
          <p>
            <b>实力雄厚</b>
            <span>亿级注册资本 ,千万技术投入</span>
          </p>
        </li>
        <li>
          <img src="/image/banner-tag2.png" alt="">
          <p>
            <b>风控严苛</b>
            <span>30道风控工序，60项信用审核</span>
          </p>
        </li>
        <li>
          <img src="/image/banner-tag3.png" alt="">
          <p>
            <b>投资省心</b>
            <span>资金安全风控，银行安全托管</span>
          </p>
        </li>
      </ul>
    </div>

    <!--产品-->
    <div class="content">
      <h2 class="public-title"><span>新手宝</span></h2>
      <div class="new-user">
        <div class="new-user-sm">
          <span>{{ pros.new.bidMinLimit }}元起投</span>
          <span>投资最高限额{{ pros.new.bidMaxLimit }}元</span>
          <span>当日计息</span>
        </div>
        <div class="new-user-number">
          <ul>
            <li>
              <p><b>{{ pros.new.rate }}</b>%</p>
              <span>历史年化收益率</span>
            </li>
            <li>
              <p><b>{{ pros.new.cycle }}</b>个月</p>
              <span>投资周期</span>
            </li>
            <li>
              <p><b>{{ pros.new.leftProductMoney }}</b>元</p>
              <span>余利可投资金额</span>
            </li>
          </ul>
          <a href="details.html" target="_blank" class="new-user-btn">立即投资</a>
        </div>
        <span class="new-tag">新用户专享</span>
      </div>

      <h2 class="public-title"><span>优选产品</span>
        <router-link to="/list?type=1" class="public-title-more">查看更多产品>></router-link>
      </h2>
      <ul class="preferred-select clearfix">
        <ProductItem v-for="pro in pros.yxs" :product="pro"/>
      </ul>
      <h2 class="public-title"><span>散标产品</span>
        <router-link to="/list?type=2" class="public-title-more">查看更多产品>></router-link>
      </h2>
      <ul class="preferred-select clearfix">
        <ProductItem v-for="pro in pros.sbs" :product="pro"/>
      </ul>

    </div>

    <!--说明-->
    <div class="information-box">
      <ul>
        <li>
          <img src="/image/2-icon1.png" alt="">
          <p>优质借款</p>
          <span>严苛风控，多重审核</span>

        </li>
        <li>
          <img src="/image/2-icon2.png" alt="">
          <p>次日计息</p>
          <span>闪电成交，谁比我快</span>
        </li>
        <li>
          <img src="/image/2-icon3.png" alt="">
          <p>全年无休</p>
          <span>百万用户，一路同行</span>
        </li>
        <li>
          <img src="/image/2-icon4.png" alt="">
          <p>知心托付</p>
          <span>百万用户，一路同行</span>
        </li>
        <li>
          <img src="/image/2-icon5.png" alt="">
          <p>技术保障</p>
          <span>千万投入，专注研发</span>
        </li>
      </ul>
    </div>
    <Footer/>
  </div>
</template>
<script>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import Vue from "vue";
import ProductItem from "@/components/ProductItem.vue";

export default {
  name: "IndexView",
  components: {Footer, Header,ProductItem},
  data() {
    return {
      pros: {
        new: {},
        yxs: [],
        sbs: []
      }
    }
  },
  created() {
    Vue.axios.get("/product/index").then(resp => {
      this.pros = resp.data;
    })
  }
}
</script>

<style>

</style>