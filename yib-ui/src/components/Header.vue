<template>
  <div>
    <div class="public-head">
      <div class="public-head-nav">
        <div class="public-head-left">
          <h1 class="public-head-logo"><a href="javascript:;">
            <img src="/image/logo.png" alt="">
          </a></h1>
          <ul class="public-head-list">
            <li>
              <router-link to="/index">主页</router-link>
            </li>
            <li class="public-head-hover">
              <a href="javascript:void(0);">我要投资</a>
              <router-link to="/"></router-link>
              <!--二级导航-->
              <div class="two-title">
                <router-link to="/list?type=1">优先类产品</router-link>
                <router-link to="/list?type=2">散标类产品</router-link>
              </div>
            </li>
            <li>
              <router-link to="/userCenter">个人中心</router-link>
            </li>
            <li><a href="javascript:;">信息披露</a></li>
            <li><a href="javascript:;">安全计划</a></li>
          </ul>
        </div>
        <div v-if="!isLogin" class="public-head-right">
          <router-link to="/">登录</router-link>
          <router-link to="/register">注册</router-link>
        </div>
        <div v-else class="public-head-right">
          <a href="javascript:;" @click="logout">退出</a>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "Header",
  data() {
    return {
      isLogin: !!sessionStorage.getItem("token"),
    }
  },
  methods: {
    logout() {
      this.$confirm("确定退出吗？", '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        sessionStorage.removeItem("token");
        this.$router.push("/");
      }).catch(() => {
        // 取消
      });
    }
  }
}
</script>
<style scoped>

</style>