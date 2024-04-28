<template>
  <div>
    <!--头部-->
    <Header/>
    <!--end-->

    <div class="login-content">
      <div class="login-flex">
        <div class="login-left">
          <h3>加入十指波金融网</h3>
          <p>坐享
            <rate/>
            历史年化收益
          </p>
          <p>平台用户<span>539</span>位 </p>
          <p>累计成交金额<span>130000</span>元</p>
        </div>
        <!---->
        <div class="login-box">
          <h3 class="login-title">
            <a href="javascript:;" :class="{curr: pwdLogin}" @click="pwdLogin=true">密码登录</a>
            |
            <a href="javascript:;" :class="{curr: !pwdLogin}" @click="pwdLogin=false">短信登录</a>
          </h3>


          <!--密码登录-->
          <div v-if="pwdLogin">
            <div class="alert-input">
              <!--<input class="form-border user-name" name="username" type="text" placeholder="您的姓名">
              <p class="prompt_name"></p>-->
              <input v-model="phone" type="text" class="form-border user-num" name="mobile"
                     placeholder="请输入11位手机号">
              <p class="prompt_num"></p>
              <input v-model="pwd" type="password" placeholder="请输入登录密码" class="form-border user-pass"
                     autocomplete
                     name="password">
              <p class="prompt_pass"></p>
            </div>
            <div class="alert-input-btn">
              <input @click="login" type="submit" class="login-submit" value="登录">
            </div>
          </div>

          <!--验证码登录-->
          <div v-else>
            <div class="alert-input">
              <!--<input class="form-border user-name" name="username" type="text" placeholder="您的姓名">
              <p class="prompt_name"></p>-->
              <input v-model="phone" type="text" class="form-border user-num" name="mobile"
                     placeholder="请输入11位手机号">
              <p class="prompt_num">{{ phoneErr }}</p>
              <div class="form-yzm form-border">
                <input v-model="code" class="yzm-write" type="text" placeholder="输入短信验证码">
                <input @click="sendSms" class="yzm-send" type="text" value="获取验证码" id="yzmBtn"
                       readonly="readonly">
              </div>
              <p class="prompt_yan">{{ codeErr }}</p>
            </div>
            <div class="alert-input-btn">
              <input @click="login2" type="submit" class="login-submit" value="登录">
            </div>
          </div>


          <div class="login-skip">
            没有账号？
            <router-link to="/register">注册</router-link>
          </div>
        </div>
      </div>
    </div>


    <!--公共底部-->
    <Footer/>
  </div>
</template>
<script>
import Vue from "vue";
import Rate from "@/components/Rate.vue";
import regs from "@/utils/regs";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";

export default {
  name: "LoginView",
  components: {Footer, Header, Rate},
  data() {
    return {
      pwdLogin: true,
      phone: '',
      phoneErr: '',
      pwd: '',
      code: '',
      codeErr: '',
      cding: false,
      sendText: '获取验证码',
      interval: 5,
      realCode: '',
    }
  },
  methods: {
    async sendSms() {
      this.phoneErr = "";
      if (this.phone == "") {
        this.phoneErr = "手机号不能为空!";
        return;
      } else if (!regs.phone.test(this.phone)) {
        this.phoneErr = "手机号格式不正确";
        return;
      }
      //发送异步请求，判断手机号是否已经注册
      let resp = await Vue.axios.get("/user/checkExists?phone=" + this.phone);
      //true:已注册
      if (!resp.data) {
        this.phoneErr = "该手机号还未注册！";
        return

      }
      //判断是否处于倒计时状态
      if (this.cding) return;
      this.cding = true;
      let interval = this.interval;
      let flag = setInterval(() => {
        this.sendText = "重新发送（" + --interval + "...)";
        if (interval == 0) {
          this.sendText = "重新发送"
          clearInterval(flag);//停止计时
          this.cding = false;
        }
      }, 1000);
      Vue.axios.get("/sms/login?phone=" + this.phone).then(resp => {//sms后面那个是决定你放进redis的关键词
        alert(resp.data)
        this.realCode = resp.data
      });

    },
    login2() {
      this.codeErr = '';
      if (this.code == '') {
        this.codeErr = '验证码不能为空！';
      } else if (!regs.code.test(this.code)) {
        this.codeErr = '验证码格式错误！';
      } else if (this.code != this.realCode) {
        this.codeErr = "验证码不正确！";
      } else {
        Vue.axios.post("/user/login2", `phone=${this.phone}&code=${this.code}`).then(resp => {
          // 登录成功
          if (resp.data.success) {
            // 如果已经实名认证，则跳转到首页，否则跳转到实名认证页面
            if (resp.data.auth) {
              this.$router.push('/index');
            } else {
              this.$router.push('/auth');
            }
          }
        })
      }
    },
    login() {
      Vue.axios.post("/user/login", `phone=${this.phone}&password=${this.pwd}`).then(resp => {
        if (resp.data.success) {
          // 主动保存服务器为客户端分配的标识
          sessionStorage.setItem("token", resp.data.token);
          if (resp.data.auth) {
            this.$router.push('/index');
          } else {
            this.$router.push('/auth');
          }
        }
      })
    }
  }
}
</script>
<style scoped>
.curr {
  font-weight: bold;
  color: #000;
  border-bottom: 2px solid #007aff;
}
</style>