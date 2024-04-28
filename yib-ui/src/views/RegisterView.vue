<template>
  <div>
    <Header/>
    <!--end-->

    <div class="login-content">
      <div class="login-flex">
        <div class="login-left">
          <p>万民用户知心托付&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<rate/>
            历史年化收益
          </p>
          <p>千万级技术研发投入&nbsp;&nbsp;&nbsp;&nbsp;亿级注册资本平台 </p>
        </div>
        <!---->
        <div class="login-box">
          <h3 class="login-title">用户注册</h3>
          <form action="" id="register_Submit">
            <div class="alert-input">
              <input @blur="checkPhone" type="text" class="form-border user-num" v-model="phone" name="mobile"
                     placeholder="请输入11位手机号">
              <p class="prompt_num">{{ phoneErr }}</p>
              <input @blur="checkPwd" type="password" v-model="pwd" placeholder="请输入6-20位英文和数字混合密码"
                     class="form-border user-pass"
                     autocomplete name="password">
              <p class="prompt_pass">{{ pwdErr }}</p>
              <div class="form-yzm form-border">
                <input @blur="checkCode" v-model="code" class="yzm-write" type="text" name=""
                       placeholder="输入短信验证码">
                <input class="yzm-send" @click="sendSms" type="text" :value="sendText" id="yzmBtn"
                       readonly="readonly">
              </div>
              <p class="prompt_yan">{{ codeErr }}</p>
            </div>
            <div class="alert-input-agree">
              <input type="checkbox" v-model="agree"/>
              我已阅读并同意<a href="javascript:;" target="_blank">《十指波金融网注册服务协议》</a>
            </div>
            <div class="alert-input-btn">
              <input type="button" @click="register" class="login-submit" value="注册">
            </div>
          </form>
          <div class="login-skip">
            已有账号？
            <router-link to="/login">登录</router-link>
          </div>
        </div>
      </div>
    </div>

    <!--公共底部-->
    <Footer/>
  </div>
</template>
<script>
import Vue, {defineComponent} from 'vue';
import filters from "@/utils/filters";
import regs from "@/utils/regs";
import qs from "qs";
import Rate from "@/components/Rate.vue";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";

export default defineComponent({
  name: "RegisterView",
  components: {Footer, Header, Rate},
  data() {
    return {
      phone: '',
      interval: 5,//短信验证码的发送间隔，单位为秒
      sendText: '发送验证码',
      cding: false,
      phoneErr: '',
      pwd: '',
      pwdErr: '',
      code: '',
      codeErr: '',
      realCode: '',
      agree: ''
    }
  },
  methods: {
    checkPhone() {
      this.phoneErr = "";
      if (this.phone == "") {
        this.phoneErr = "手机号不能为空!";
      } else if (!regs.phone.test(this.phone)) {
        this.phoneErr = "手机号格式不正确";
      } else {
        //发送异步请求，判断手机号是否已经注册
        Vue.axios.get("/user/checkExists?phone=" + this.phone).then(resp => {
          //true:已注册
          if (resp.data) {
            this.phoneErr = "该手机号已经注册！";
          }
        })
      }

    },
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
      if (resp.data) {
        this.phoneErr = "该手机号已经注册！";
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
      Vue.axios.get("/sms/register?phone=" + this.phone).then(resp => {
        alert(resp.data)
        this.realCode = resp.data
      });

    },
    checkPwd() {
      this.pwdErr = "";
      if (this.pwd == "") {
        this.pwdErr = "密码不能为空";
      } else if (!regs.password.test(this.pwd)) {
        this.pwdErr = "密码格式必须为6-20位字母加英文的组合"
      }
    },
    checkCode() {
      this.codeErr = "";
      if (this.code == "") {
        this.codeErr = "验证码不能为空";
      } else if (!regs.code.test(this.code)) {
        this.codeErr = "验证码为4-6位数字"
      } else if (this.realCode != this.code) {
        this.codeErr = "验证码错误！";
      }
    },
    register() {
      if (this.phoneErr || this.pwdErr || this.codeErr) return;
      if (!this.agree) {
        alert("请勾选协议！");
        return;
      }
      let data = {
        phone: this.phone,
        password: this.pwd,
        code: this.code
      };
      data = qs.stringify(data);
      Vue.axios.post("/user/register", data).then(resp => {
        if (resp.data.success) {
          this.$router.push('/login')
        }
      });
    }
  },

})
</script>
<style scoped>

</style>