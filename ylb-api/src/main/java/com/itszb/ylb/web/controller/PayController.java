package com.itszb.ylb.web.controller;

import com.itszb.ylb.beans.Recharge;
import com.itszb.ylb.beans.User;
import com.itszb.ylb.service.PayService;
import com.itszb.ylb.service.RechargeService;
import com.itszb.ylb.web.pay.Pkipair;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

@Controller
@CrossOrigin
public class PayController {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RechargeService rechargeService;

    @Resource
    private PayService payService;

    @GetMapping("payResult")
    @ResponseBody
    public String payResult(HttpServletRequest request) {
        String merchantAcctId = request.getParameter("merchantAcctId");
        String version = request.getParameter("version");
        String language = request.getParameter("language");
        String signType = request.getParameter("signType");
        String payType = request.getParameter("payType");
        String bankId = request.getParameter("bankId");
        String orderId = request.getParameter("orderId");
        String orderTime = request.getParameter("orderTime");
        String orderAmount = request.getParameter("orderAmount");
        String bindCard = "";
        if (request.getParameter("bindCard") != null) {
            bindCard = request.getParameter("bindCard");
        }
        String bindMobile = "";
        if (request.getParameter("bindMobile") != null) {
            bindMobile = request.getParameter("bindMobile");
        }
        String dealId = request.getParameter("dealId");
        String bankDealId = request.getParameter("bankDealId");
        String dealTime = request.getParameter("dealTime");
        String payAmount = request.getParameter("payAmount");
        String fee = request.getParameter("fee");
        String ext1 = request.getParameter("ext1");
        String ext2 = request.getParameter("ext2");
        String payResult = request.getParameter("payResult");
        String aggregatePay = request.getParameter("aggregatePay");
        String errCode = request.getParameter("errCode");
        String signMsg = request.getParameter("signMsg");
        String merchantSignMsgVal = "";

        merchantSignMsgVal = appendParam(merchantSignMsgVal, "merchantAcctId", merchantAcctId);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "version", version);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "language", language);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "signType", signType);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "payType", payType);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankId", bankId);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderId", orderId);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderTime", orderTime);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderAmount", orderAmount);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "bindCard", bindCard);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "bindMobile", bindMobile);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealId", dealId);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankDealId", bankDealId);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealTime", dealTime);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "payAmount", payAmount);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "fee", fee);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext1", ext1);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext2", ext2);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "payResult", payResult);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "aggregatePay", aggregatePay);
        merchantSignMsgVal = appendParam(merchantSignMsgVal, "errCode", errCode);
        System.out.println("merchantSignMsgVal=" + merchantSignMsgVal);

        // 签名验证
        Pkipair pki = new Pkipair();
        boolean flag = pki.enCodeByCer(merchantSignMsgVal, signMsg);
        int rtnOK = 1;
        String rtnUrl = "";

        // 对签名的验证，true表示验证通过
        if (flag) {
            if (Integer.parseInt(payResult) == 10) {
                // 此处商户可以做业务逻辑处理
                rtnOK = 1;
            } else {
                rtnOK = 0;
            }
        } else {
            rtnOK = 0;
        }

        //以下是我们快钱设置的show页面，商户需要自己定义该页面。
        if (rtnOK == 1) {
            rtnUrl = "http://127.0.0.1:8080/payResult?success=true";
        } else {
            rtnUrl = "http://127.0.0.1:8080/payResult?success=false";
        }

        // 更新订单状态，同时如果支付成功，还需要更新用户的资金账户表
        Integer status = rtnOK == 0 ? 2 : 1;
        Long uid = Long.parseLong(ext1); // 在支付的时候，扩展字段1为用户id
        Double money = Double.parseDouble(payAmount) / 100;
        payService.payResult(orderId, status, payType, uid, money);

        return "<result>"+rtnOK+"</result><redirecturl>"+rtnUrl+"</redirecturl>";
    }

    @RequestMapping("pay")
    public String pay(String token, Double money, HttpServletRequest request) throws Exception {
        User user = (User) redisTemplate.opsForValue().get(token);
        //人民币网关账号，该账号为11位人民币网关"商户编号+01",该参数必填。
        String merchantAcctId = "1001214035601";//
        //编码方式，1代表 UTF-8; 2 代表 GBK; 3代表 GB2312 默认为1,该参数必填。
        String inputCharset = "1";
        //接收支付结果的页面地址，该参数一般置为空即可。
        String pageUrl = "";
        //服务器接收支付结果的后台地址，该参数务必填写，不能为空。
        String bgUrl = "http://3t567e.natappfree.cc/payResult";

        //网关版本，固定值：v2.0,该参数必填。
        String version = "v2.0";
        //语言种类，1代表中文显示，2代表英文显示。默认为1,该参数必填。
        String language = "1";
        //签名类型,该值为4，代表PKI加密方式,该参数必填。
        String signType = "4";
        //支付人姓名,可以为空。
        String payerName = user.getName();
        //支付人联系类型，1 代表电子邮件方式；2 代表手机联系方式。可以为空。
        String payerContactType = "2";
        //支付人联系方式，与payerContactType设置对应，payerContactType为1，则填写邮箱地址；payerContactType为2，则填写手机号码。可以为空。
        String payerContact = user.getPhone();
        //指定付款人，可以为空
        String payerIdType = "3";
        //付款人标识，可以为空
        String payerId = user.getId() + "";
        //付款人IP，可以为空
        String payerIP = request.getRemoteAddr();
        //商家的终端ip，支持Ipv4和Ipv6
        String terminalIp = "192.168.1.1";

        //网络交易平台简称，英文或中文字符串,除微信支付宝支付外其他交易方式必传
        String tdpformName = "盈利宝";
        //商户订单号，以下采用时间来定义订单号，商户可以根据自己订单号的定义规则来定义该值，不能为空。
        String orderId = UUID.randomUUID().toString().replace("-", "");
        //订单金额，金额以“分”为单位，商户测试以1分测试即可，切勿以大金额测试。该参数必填。
        // 元 ==> 分
        Double amount = money * 100;
        String orderAmount = amount.intValue() + "";

        //订单提交时间，格式：yyyyMMddHHmmss，如：20071117020101，不能为空。
        String orderTime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        //快钱时间戳，格式：yyyyMMddHHmmss，如：20071117020101， 可以为空
        String orderTimestamp = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        //商品名称，不可为空。
        String productName = "盈利宝充值";
        //商品数量，不可为空。
        String productNum = orderAmount;
        //商品代码，可以为空。
        String productId = "10000";
        //商品描述，可以为空。
        String productDesc = "盈利宝充值";
        //扩展字段1，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空。
        String ext1 = user.getId() + "";
        //扩展自段2，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空。
        String ext2 = "扩展2";
        //支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10-1或10-2，必填。
        String payType = "00";
        //银行代码，如果payType为00，该值可以为空；如果payType为10-1或10-2，该值必须填写，具体请参考银行列表。
        String bankId = "";
        //同一订单禁止重复提交标志，实物购物车填1，虚拟产品用0。1代表只能提交一次，0代表在支付不成功情况下可以再提交。可为空。
        String redoFlag = "0";

        // signMsg 签名字符串 不可空，生成加密签名串
        String signMsgVal = "";
        signMsgVal = appendParam(signMsgVal, "inputCharset", inputCharset);
        signMsgVal = appendParam(signMsgVal, "pageUrl", pageUrl);
        signMsgVal = appendParam(signMsgVal, "bgUrl", bgUrl);
        signMsgVal = appendParam(signMsgVal, "version", version);
        signMsgVal = appendParam(signMsgVal, "language", language);
        signMsgVal = appendParam(signMsgVal, "signType", signType);
        signMsgVal = appendParam(signMsgVal, "merchantAcctId", merchantAcctId);
        signMsgVal = appendParam(signMsgVal, "payerName", payerName);
        signMsgVal = appendParam(signMsgVal, "payerContactType", payerContactType);
        signMsgVal = appendParam(signMsgVal, "payerContact", payerContact);
        signMsgVal = appendParam(signMsgVal, "payerIdType", payerIdType);
        signMsgVal = appendParam(signMsgVal, "payerId", payerId);
        signMsgVal = appendParam(signMsgVal, "payerIP", payerIP);
        signMsgVal = appendParam(signMsgVal, "orderId", orderId);
        signMsgVal = appendParam(signMsgVal, "orderAmount", orderAmount);
        signMsgVal = appendParam(signMsgVal, "orderTime", orderTime);
        signMsgVal = appendParam(signMsgVal, "orderTimestamp", orderTimestamp);
        signMsgVal = appendParam(signMsgVal, "productName", productName);
        signMsgVal = appendParam(signMsgVal, "productNum", productNum);
        signMsgVal = appendParam(signMsgVal, "productId", productId);
        signMsgVal = appendParam(signMsgVal, "productDesc", productDesc);
        signMsgVal = appendParam(signMsgVal, "ext1", ext1);
        signMsgVal = appendParam(signMsgVal, "ext2", ext2);
        signMsgVal = appendParam(signMsgVal, "payType", payType);
        signMsgVal = appendParam(signMsgVal, "bankId", bankId);
        signMsgVal = appendParam(signMsgVal, "redoFlag", redoFlag);

        Pkipair pki = new Pkipair();
        String signMsg = pki.signMsg(signMsgVal);

        String url = "https://sandbox.99bill.com/gateway/recvMerchantInfoAction.htm";
        String encoding = "UTF-8";
        url += "?inputCharset=" + URLEncoder.encode(inputCharset, encoding);
        url += "&pageUrl=" + URLEncoder.encode(pageUrl, encoding);
        url += "&bgUrl=" + URLEncoder.encode(bgUrl, encoding);
        url += "&version=" + URLEncoder.encode(version, encoding);
        url += "&language=" + URLEncoder.encode(language, encoding);
        url += "&signType=" + URLEncoder.encode(signType, encoding);
        url += "&signMsg=" + URLEncoder.encode(signMsg, encoding);
        url += "&merchantAcctId=" + URLEncoder.encode(merchantAcctId, encoding);
        url += "&payerName=" + URLEncoder.encode(payerName, encoding);
        url += "&payerContactType=" + URLEncoder.encode(payerContactType, encoding);
        url += "&payerContact=" + URLEncoder.encode(payerContact, encoding);
        url += "&payerIdType=" + URLEncoder.encode(payerIdType, encoding);
        url += "&payerId=" + URLEncoder.encode(payerId, encoding);
        url += "&payerIP=" + URLEncoder.encode(payerIP, encoding);
        url += "&terminalIp=" + URLEncoder.encode(terminalIp, encoding);
        url += "&tdpformName=" + URLEncoder.encode(tdpformName, encoding);
        url += "&orderId=" + URLEncoder.encode(orderId, encoding);
        url += "&orderAmount=" + URLEncoder.encode(orderAmount, encoding);
        url += "&orderTime=" + URLEncoder.encode(orderTime, encoding);
        url += "&orderTimestamp=" + URLEncoder.encode(orderTimestamp, encoding);
        url += "&productName=" + URLEncoder.encode(productName, encoding);
        url += "&productNum=" + URLEncoder.encode(productNum, encoding);
        url += "&productId=" + URLEncoder.encode(productId, encoding);
        url += "&productDesc=" + URLEncoder.encode(productDesc, encoding);
        url += "&ext1=" + URLEncoder.encode(ext1, encoding);
        url += "&ext2=" + URLEncoder.encode(ext2, encoding);
        url += "&payType=" + URLEncoder.encode(payType, encoding);
        url += "&bankId=" + URLEncoder.encode(bankId, encoding);
        url += "&redoFlag=" + URLEncoder.encode(redoFlag, encoding);

        // 生成支付订单
        Recharge recharge = new Recharge();
        recharge.setUid(user.getId());
        recharge.setRechargeStatus(0);
        recharge.setRechargeTime(new Date());
        recharge.setRechargeMoney(money);
        recharge.setRechargeDesc("盈利宝账户充值");
        recharge.setRechargeNo(orderId);
        rechargeService.add(recharge);

        return "redirect:" + url;
    }

    public String appendParam(String returns, String paramId, String paramValue) {
        if (StringUtils.isNotBlank(returns)) {
            if (StringUtils.isNotBlank(paramValue)) {
                returns += "&" + paramId + "=" + paramValue;
            }
        } else {
            if (StringUtils.isNotBlank(paramValue)) {
                returns = paramId + "=" + paramValue;
            }
        }

        return returns;
    }
}
