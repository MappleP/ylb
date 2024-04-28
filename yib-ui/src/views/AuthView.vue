<template>
  <div>
    <Header/>
    <div class="login-content">
      <div class="login-flex">
        <div class="login-left"></div>
        <!---->
        <div class="login-box">
          <h3 class="login-title">实名认证</h3>
          <form action="" id="renZ_Submit">
            <div class="alert-input">
              <input v-model="name" type="text" class="form-border user-name" name="username"
                     placeholder="请输入您的真实姓名">
              <p class="prompt_name">{{nameErr}}</p>
              <input v-model="idCard" type="text" class="form-border user-sfz" name="sfz"
                     placeholder="请输入15位或18位身份证号">
              <p class="prompt_sfz">{{ idErr}}</p>
            </div>
            <div class="alert-input-btn">
              <input @click="auth" type="button" class="login-submit" value="认证">
            </div>
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
import regs from "@/utils/regs";
import Vue from "vue";

export default {
  name: "auth",
  components: {Footer, Header},
  data() {
    return {
      name: "",
      nameErr: "",
      idCard: "",
      idErr: ""
    }

  },
  methods: {
    auth() {
      if (!this.name) {
        this.nameErr = "名字不能为空";
      } else if (!regs.name.test(this.name)) {
        this.nameErr = "名字格式错误";
      } else if (!this.idCard) {
        this.idErr = "身份证号码不能为空";
      } else if (!regs.id.test(this.idCard)) {
        this.idErr = "身份证格式错误";
      } else {
        Vue.axios.post("/user/auth/", `name=${this.name}&idCard=${this.idCard}`).then(resp=>{
          if (resp.data.success) {
            this.$alert("验证成功", '操作提示', {
              confirmButtonText: '确定',
              callback: action => {
                this.$router.push("/index");
              }
            });
          }
        })
      }
    }
  }
}
</script>


<style>

</style>