import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const routes = [
    {
        path: '/register',
        name: 'register',

        component: () => import('../views/RegisterView.vue'),
        meta: {
            title: "用户注册"
        }
    },
    {
        path: '/login',
        name: 'login',

        component: () => import('../views/LoginView.vue'),
        meta: {
            title: "用户登录"
        }
    },
    {
        path: '/auth',
        name: 'auth',

        component: () => import('../views/AuthView.vue'),
        meta: {
            title: "实名认证"
        }
    },
    {
        path: '/index',
        name: 'index',

        component: () => import('../views/IndexView.vue'),
        meta: {
            title: "首页"
        }
    },
    {
        path: '/list',
        name: 'list',

        component: () => import('../views/ListView.vue'),
        meta: {
            title: "产品列表"
        }
    },
    {
        path: '/detail/:id',//动态路由
        name: 'detail',

        component: () => import('../views/DetailView.vue'),
        meta: {
            title: "产品详情"
        }
    },
    {
        path: '/userCenter',
        name: 'userCenter',

        component: () => import('../views/UserCenterView.vue'),
        meta: {
            title: "用户中心"
        }
    },
    {
        path: '/pay',
        name: 'pay',

        component: () => import('../views/PayView.vue'),
        meta: {
            title: "用户充值"
        }
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})
let loginNames = ["userCenter"];
// 全局前置守卫：在进入组件之间执行
router.beforeEach(async (to, from, next) => {
    if (loginNames.includes(to.name)) {
        let token = sessionStorage.getItem("token");
        if (!token) {
            next({name: "login"}); // 改变方向
        } else if (!(await Vue.axios.get('/user/isLogin')).data) {
            next({name: "login"});
        } else {
            next(); // 放行
        }
    } else {
        next();
    }
});
// 全局后置守卫
router.afterEach((to, from) => {
    document.title = to.meta.title;
})
export default router
