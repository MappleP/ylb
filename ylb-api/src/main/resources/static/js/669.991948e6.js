"use strict";(self["webpackChunkyib_ui"]=self["webpackChunkyib_ui"]||[]).push([[669],{4848:function(t,e,s){s.d(e,{Z:function(){return c}});var a=function(){var t=this;t._self._c,t._self._setupProxy;return t._m(0)},i=[function(){var t=this,e=t._self._c;t._self._setupProxy;return e("div",[e("div",{staticClass:"public-bottom"},[e("div",[e("p",[t._v("版权所有© 重庆十指波网络科技有限公司 渝ICP备00000000号 | 渝公网安备000000000000000000号")]),e("p",[t._v("一家专业的线上培训机构"),e("a",{attrs:{href:"javascript:;"}},[t._v("Visit the HomePage")])])])])])}],r={__name:"Footer",setup(t){return{__sfc:!0}}},n=r,o=s(1001),l=(0,o.Z)(n,a,i,!1,null,"bb821140",null),c=l.exports},3549:function(t,e,s){s.d(e,{Z:function(){return c}});var a=function(){var t=this;t._self._c,t._self._setupProxy;return t._m(0)},i=[function(){var t=this,e=t._self._c;t._self._setupProxy;return e("div",[e("div",{staticClass:"public-head"},[e("div",{staticClass:"public-head-nav"},[e("div",{staticClass:"public-head-left"},[e("h1",{staticClass:"public-head-logo"},[e("a",{attrs:{href:"javascript:;"}},[e("img",{attrs:{src:"/image/logo.png",alt:""}})])]),e("ul",{staticClass:"public-head-list"},[e("li",[e("a",{attrs:{href:"index.html",target:"_blank"}},[t._v("主页")])]),e("li",{staticClass:"public-head-hover"},[e("a",{attrs:{href:"javascript:void(0);"}},[t._v("我要投资")]),e("div",{staticClass:"two-title"},[e("a",{attrs:{href:"javascript:;"}},[t._v("优先类产品")]),e("a",{attrs:{href:"javascript:;"}},[t._v("散标类产品")])])]),e("li",[e("a",{attrs:{href:"user_center.html",target:"_blank"}},[t._v("借款人信息")])]),e("li",[e("a",{attrs:{href:"javascript:;",target:"_blank"}},[t._v("信息披露")])]),e("li",[e("a",{attrs:{href:"javascript:;",target:"_blank"}},[t._v("安全计划")])])])]),e("div",{staticClass:"public-head-right"},[e("a",{attrs:{href:"login.html",target:"_blank"}},[t._v("登录")]),e("a",{attrs:{href:"register.html",target:"_blank"}},[t._v("注册")])])])])])}],r={__name:"Header",setup(t){return{__sfc:!0}}},n=r,o=s(1001),l=(0,o.Z)(n,a,i,!1,null,"632a6302",null),c=l.exports},8583:function(t,e,s){s.d(e,{Z:function(){return u}});var a=function(){var t=this,e=t._self._c;return e("span",[t._v(t._s(t._f("fmtDecimal3")(t.rate))+"%")])},i=[],r=s(6369),n=s(6395),o={name:"Rate",data(){return{rate:0}},created(){r["default"].axios.get("/product/rate").then((t=>{this.rate=t.data}))},filters:n.Z},l=o,c=s(1001),d=(0,c.Z)(l,a,i,!1,null,"0295b088",null),u=d.exports},4669:function(t,e,s){s.r(e),s.d(e,{default:function(){return v}});var a=function(){var t=this,e=t._self._c;return e("div",[e("Header"),e("div",{staticClass:"login-content"},[e("div",{staticClass:"login-flex"},[e("div",{staticClass:"login-left"},[e("h3",[t._v("加入十指波金融网")]),e("p",[t._v("坐享 "),e("rate"),t._v(" 历史年化收益 ")],1),t._m(0),t._m(1)]),e("div",{staticClass:"login-box"},[e("h3",{staticClass:"login-title"},[e("a",{class:{curr:t.pwdLogin},attrs:{href:"javascript:;"},on:{click:function(e){t.pwdLogin=!0}}},[t._v("密码登录")]),t._v(" | "),e("a",{class:{curr:!t.pwdLogin},attrs:{href:"javascript:;"},on:{click:function(e){t.pwdLogin=!1}}},[t._v("短信登录")])]),t.pwdLogin?e("div",[e("div",{staticClass:"alert-input"},[e("input",{directives:[{name:"model",rawName:"v-model",value:t.phone,expression:"phone"}],staticClass:"form-border user-num",attrs:{type:"text",name:"mobile",placeholder:"请输入11位手机号"},domProps:{value:t.phone},on:{input:function(e){e.target.composing||(t.phone=e.target.value)}}}),e("p",{staticClass:"prompt_num"}),e("input",{directives:[{name:"model",rawName:"v-model",value:t.pwd,expression:"pwd"}],staticClass:"form-border user-pass",attrs:{type:"password",placeholder:"请输入登录密码",autocomplete:"",name:"password"},domProps:{value:t.pwd},on:{input:function(e){e.target.composing||(t.pwd=e.target.value)}}}),e("p",{staticClass:"prompt_pass"})]),e("div",{staticClass:"alert-input-btn"},[e("input",{staticClass:"login-submit",attrs:{type:"submit",value:"登录"},on:{click:t.login}})])]):e("div",[e("div",{staticClass:"alert-input"},[e("input",{directives:[{name:"model",rawName:"v-model",value:t.phone,expression:"phone"}],staticClass:"form-border user-num",attrs:{type:"text",name:"mobile",placeholder:"请输入11位手机号"},domProps:{value:t.phone},on:{input:function(e){e.target.composing||(t.phone=e.target.value)}}}),e("p",{staticClass:"prompt_num"},[t._v(t._s(t.phoneErr))]),e("div",{staticClass:"form-yzm form-border"},[e("input",{directives:[{name:"model",rawName:"v-model",value:t.code,expression:"code"}],staticClass:"yzm-write",attrs:{type:"text",placeholder:"输入短信验证码"},domProps:{value:t.code},on:{input:function(e){e.target.composing||(t.code=e.target.value)}}}),e("input",{staticClass:"yzm-send",attrs:{type:"text",value:"获取验证码",id:"yzmBtn",readonly:"readonly"},on:{click:t.sendSms}})]),e("p",{staticClass:"prompt_yan"},[t._v(t._s(t.codeErr))])]),e("div",{staticClass:"alert-input-btn"},[e("input",{staticClass:"login-submit",attrs:{type:"submit",value:"登录"},on:{click:t.login2}})])]),e("div",{staticClass:"login-skip"},[t._v(" 没有账号？ "),e("router-link",{attrs:{to:"/register"}},[t._v("注册")])],1)])])]),e("Footer")],1)},i=[function(){var t=this,e=t._self._c;return e("p",[t._v("平台用户"),e("span",[t._v("539")]),t._v("位 ")])},function(){var t=this,e=t._self._c;return e("p",[t._v("累计成交金额"),e("span",[t._v("130000")]),t._v("元")])}],r=(s(560),s(6369)),n=s(8583),o=s(7971),l=s(3549),c=s(4848),d={name:"LoginView",components:{Footer:c.Z,Header:l.Z,Rate:n.Z},data(){return{pwdLogin:!0,phone:"",phoneErr:"",pwd:"",code:"",codeErr:"",cding:!1,sendText:"获取验证码",interval:5,realCode:""}},methods:{async sendSms(){if(this.phoneErr="",""==this.phone)return void(this.phoneErr="手机号不能为空!");if(!o.Z.phone.test(this.phone))return void(this.phoneErr="手机号格式不正确");let t=await r["default"].axios.get("/user/checkExists?phone="+this.phone);if(!t.data)return void(this.phoneErr="该手机号还未注册！");if(this.cding)return;this.cding=!0;let e=this.interval,s=setInterval((()=>{this.sendText="重新发送（"+--e+"...)",0==e&&(this.sendText="重新发送",clearInterval(s),this.cding=!1)}),1e3);r["default"].axios.get("/sms/login?phone="+this.phone).then((t=>{alert(t.data),this.realCode=t.data}))},login2(){this.codeErr="",""==this.code?this.codeErr="验证码不能为空！":o.Z.code.test(this.code)?this.code!=this.realCode?this.codeErr="验证码不正确！":r["default"].axios.post("/user/login2",`phone=${this.phone}&code=${this.code}`).then((t=>{t.data.success&&(t.data.auth?this.$router.push("/index"):this.$router.push("/auth"))})):this.codeErr="验证码格式错误！"},login(){r["default"].axios.post("/user/login",`phone=${this.phone}&password=${this.pwd}`).then((t=>{t.data.success&&(t.data.auth?this.$router.push("/index"):this.$router.push("/auth"))}))}}},u=d,p=s(1001),h=(0,p.Z)(u,a,i,!1,null,"18271a99",null),v=h.exports},6395:function(t,e){const s={fmtDecimal3(t){return t?(isNaN(t)&&(t-=0),t.toFixed(3)):t}};e.Z=s},7971:function(t,e){const s={phone:/^1[3-9]\d{9}$/,password:/^(?=.*\d)(?=.*[a-zA-Z])[\da-zA-Z]{6,20}$/,code:/^\d{4,6}$/,name:/^[\u4e00-\u9fa5]{2,}$/,id:/^(([1-6][1-9]|50)\d{4}(18|19|20)\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\d{3}[\dXx]|([1-6][1-9]|50)\d{4}\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\d{3})$/,replace:{thChar:/\B(?=(\d{3})+\b)/g}};e.Z=s}}]);
//# sourceMappingURL=669.991948e6.js.map