import Vue from 'vue'
import App from './App.vue'
import router from './router'
// 整合elementUI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);
//整合axios代码
import axios from "axios";
//设置服务器默认地址
axios.defaults.baseURL = "http://127.0.0.1";
import VueAxios from "vue-axios";

Vue.use(VueAxios, axios);
//导入CSS
import '../public/css/details.css'
import '../public/css/font-awesome.min.css'
import '../public/css/index.css'
import '../public/css/list.css'
import '../public/css/login.css'
import '../public/css/public-head.css'
import '../public/css/reset.css'
import '../public/css/swiper.css'
import '../public/css/user_center.css'
import '../public/css/user_pay.css'

Vue.config.productionTip = false
// 添加请求拦截器：自动携带客户端标识
axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    let token = sessionStorage.getItem("token");
    if (token) {
        config.headers.token = token;
    }

    return config;
});
// 添加响应拦截器：统一处理错误提示
axios.interceptors.response.use(function (response) {
    if (response.data.success === false) {
        app.$alert(response.data.errMsg, '操作提示', {
            confirmButtonText: '确定',
            callback: action => {
                if (response.data.op == "reLogin") {
                    app.$router.push("/");
                }
            }
        });
    } 
    return response;
});
let app = new Vue({
    router,
    render: h => h(App)
}).$mount('#app')
