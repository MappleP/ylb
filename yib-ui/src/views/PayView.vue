<template>
  <div>
    <Header/>
    <!--充值-->
    <div class="user-pay-box clearfix">
      <div class="user-pay-left fl">
        <p class="user-pay-title1">我的账户</p>
        <div class="user-pay-list">
          <a href="javascript:;" class="user-pay-list-on">充值</a>
          <a href="javascript:;" target="_blank">我的信息</a>
          <a href="javascript:;" target="_blank">投资记录</a>
          <a href="javascript:;" target="_blank">收益记录</a>
        </div>
      </div>
      <div class="user-pay-right fl">
        <p class="user-pay-title2">第三方支付平台</p>
        <div class="user-pay-form">
          <img src="/image/pay-1.jpg" alt="">
          <form action="http://127.0.0.1/pay">
            <input type="hidden" name="token" :value="token" />

            <p class="user-pay-form-ts">请输入金额</p>
            <input type="text" placeholder="元" name="money" class="number-money" />
            <input type="submit" value="支付" class="submit-btn">
          </form>
        </div>
        <div class="user-pay-sm">
          <h3>温馨提示</h3>
          <p>1.为了您的使用安全，充值操作建议不要使用公共电脑。</p>
          <p>2.银行卡充值限额，由各银行限制。</p>
          <p>3.品台禁止用卡套现、虚拟交易、洗钱等行为，一经发现并确认，将终止该账户的使用。</p>
          <p>4.如果充值中出现问题，请联系客服400-890-0000。</p>
        </div>
      </div>
    </div>
    <Footer/>
  </div>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";
export default {
  name: "PayView",
  components: {Footer, Header},
  data() {
    return {
      token: sessionStorage.getItem("token")
    }
  }
}
</script>

<style scoped>

</style>