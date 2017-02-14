package com.luosee.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.luosee.common.SaltedUser;
import com.luosee.user.UserDao;
import net.sf.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.luosee.pay.AlipayInfo.*;

/**
 * Created by server1 on 2017/1/7.
 */
@Controller
public class PayController {

    @Resource(name="userDao")
    private UserDao userDao;

    @Resource(name="payDao")
        private PayDao payDao;

    @RequestMapping("subscribe")
    public String subscribe(Principal principal)
    {
        return "user/subscribe";
    }


    @RequestMapping(value = "payScanCode",produces = "text/html;charset=UTF-8")
    public String payScanCode(HttpSession session,UserPay userPay,Principal principal,Model model)
    {
        SaltedUser user=(SaltedUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Integer userId=null;
        //判断是不是子账号
        if(!"3".equals(user.getGrade()))
        {
            userId=userDao.getUserIdByName(principal.getName());
        }
        else
        {
            userId=userDao.findOneParentId(principal.getName());
        }

        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date=new Date();

        String tradeno=dateFormat.format(date)+userId;

        Map<String,Object> alipayMap=new HashMap<String,Object>();
        alipayMap.put("out_trade_no",tradeno);
        alipayMap.put("total_amount","0.01");
        alipayMap.put("subject","月费");
        alipayMap.put("store_id","2088102169502750");
        alipayMap.put("timeout_express","90m");

        userPay.setUserId(userId);
        userPay.setPayType(0);
        userPay.setMoney(BigDecimal.valueOf(0.01));
        userPay.setOutTradeNo(tradeno);
        session.setAttribute("userPaySettlement",userPay);
        payDao.createTransaction(userPay);

        AlipayClient alipayClient = new DefaultAlipayClient(APLIAY_TEST_URL,APPID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY);
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();//创建API对应的request类
        request.setBizContent(String.valueOf(JSONObject.fromObject(alipayMap)));//设置业务参数
        String result=null;
        try {
            AlipayTradePrecreateResponse response = alipayClient.execute(request);
            result=response.getQrCode();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        model.addAttribute("result",result);
        return "user/ScanCode";
    }


    @RequestMapping(value = "paySettlement",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String Settlement(HttpSession session, UserPay userPay, Principal principal)
    {
        SaltedUser user=(SaltedUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Integer userId=null;
        //判断是不是子账号
        if(!"3".equals(user.getGrade()))
        {
            userId=userDao.getUserIdByName(principal.getName());
        }
        else
        {
            userId=userDao.findOneParentId(principal.getName());
        }
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date=new Date();

        String tradeno=dateFormat.format(date)+userId;

        Map<String,Object> alipayMap=new HashMap<String,Object>();
        alipayMap.put("out_trade_no",tradeno);
        alipayMap.put("total_amount","0.01");
        alipayMap.put("subject","月费");
        alipayMap.put("seller_id","2088102169502750");
        alipayMap.put("product_code","QUICK_WAP_PAY");

        userPay.setUserId(userId);
        userPay.setPayType(0);
        userPay.setMoney(BigDecimal.valueOf(0.01));
        userPay.setOutTradeNo(tradeno);
        session.setAttribute("userPaySettlement",userPay);
        payDao.createTransaction(userPay);

        AlipayClient alipayClient = new DefaultAlipayClient(APLIAY_TEST_URL,APPID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY);
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(PAY_RETURN);
        alipayRequest.setNotifyUrl(PAY_NOTIFY);//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent(String.valueOf(JSONObject.fromObject(alipayMap)));//填充业务参数
        String form = null; //调用SDK生成表单
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    @RequestMapping("pay_return")
    public String pay_return(Model model,HttpServletRequest httpRequest,HttpSession session)
    {
        Map<String, String> paramsMap =paramMap(httpRequest);
        boolean signVerified = false;
        String result=null;

        model.addAttribute("status",1121);
        try {
            signVerified = AlipaySignature.rsaCheckV1(paramsMap, ALIPAY_PUBLIC_KEY, CHARSET);
            if(signVerified){
                // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
                AlipayClient alipayClient = new DefaultAlipayClient(APLIAY_TEST_URL,APPID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY);//获得初始化的AlipayClient
                AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类

                Map<String,Object> alipayMap=new HashMap<String,Object>();
                alipayMap.put("out_trade_no",paramsMap.get("out_trade_no"));
                alipayMap.put("trade_no",paramsMap.get("trade_no"));

                request.setBizContent(String.valueOf(JSONObject.fromObject(alipayMap)));//设置业务参数
                AlipayTradeQueryResponse response = alipayClient.execute(request);
                result=response.getCode();

                Date date=new Date();
                UserPay pay= (UserPay) session.getAttribute("userPaySettlement");

                pay.setTradeNo(response.getTradeNo());
                pay.setTradeStatus(response.getTradeStatus());
                pay.setPayDate(new java.sql.Date(date.getTime()));
                payDao.updateUserPayInfo(pay);

                Subscribe payment=new Subscribe();
                payment.setPayType(pay.getPayType());
                payment.setUserId(pay.getUserId());

                Subscribe validityPay=payDao.hasPayment(pay.getUserId());
                Calendar calendar=Calendar.getInstance();
                //判断当前时间是否在有效期之后
                if(date.after(validityPay.getValidityDate()))
                {
                    calendar.setTime(date);
                }
                else
                {
                    calendar.setTime(validityPay.getValidityDate());
                }

                switch (pay.getPayType())
                {
                    case 0:calendar.add(Calendar.YEAR,1);
                        break;
                    case 1:calendar.add(Calendar.MONTH,1);
                        break;
                }

                payment.setValidityDate(new java.sql.Date(calendar.getTime().getTime()));

                if( validityPay == null)
                {
                    payDao.createPayment(payment);
                }
                else if ( validityPay != null)
                {
                    payDao.updateUserPayment(payment);
                }
            }else{
                // TODO 验签失败则记录异常日志，并在response中返回failure.
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return result;
    }


    @RequestMapping(value = "pay_notify",method = RequestMethod.POST)
    @ResponseBody
    public String pay_notify(HttpServletRequest httpRequest,HttpSession session)
    {
        Map<String, String> paramsMap =paramMap(httpRequest);

        boolean signVerified = false;
        String result=null;
        try {
            signVerified = AlipaySignature.rsaCheckV1(paramsMap, ALIPAY_PUBLIC_KEY, CHARSET);
            if(signVerified){
                // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
                UserPay pay= (UserPay) session.getAttribute("userPaySettlement");
                AlipayClient alipayClient = new DefaultAlipayClient(APLIAY_TEST_URL,APPID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY);//获得初始化的AlipayClient
                AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类

                Map<String,Object> alipayMap=new HashMap<String,Object>();
                alipayMap.put("out_trade_no",pay.getOutTradeNo());
                alipayMap.put("trade_no",paramsMap.get("trade_no"));

                request.setBizContent(String.valueOf(JSONObject.fromObject(alipayMap)));//设置业务参数
                AlipayTradeQueryResponse response = alipayClient.execute(request);

                Date date=new Date();

                pay.setTradeNo(response.getTradeNo());
                pay.setTradeStatus(response.getTradeStatus());
                pay.setPayDate(new java.sql.Date(date.getTime()));
                payDao.updateUserPayInfo(pay);

                Subscribe payment=new Subscribe();
                payment.setPayType(pay.getPayType());
                payment.setUserId(pay.getUserId());

                Subscribe validityPay=payDao.hasPayment(pay.getUserId());
                Calendar calendar=Calendar.getInstance();
                //判断当前时间是否在有效期之后
                if(date.after(validityPay.getValidityDate()))
                {
                    calendar.setTime(date);
                }
                else
                {
                    calendar.setTime(validityPay.getValidityDate());
                }

                switch (pay.getPayType())
                {
                    case 0:calendar.add(Calendar.YEAR,1);
                        break;
                    case 1:calendar.add(Calendar.MONTH,1);
                        break;
                }
                payment.setValidityDate(new java.sql.Date(calendar.getTime().getTime()));

                if( validityPay == null)
                {
                    payDao.createPayment(payment);
                }
                else if ( validityPay != null)
                {
                    payDao.updateUserPayment(payment);
                }

                if(response.isSuccess())
                {
                    result="success";
                }
                else
                {
                    result="failure";
                }
            }else{
                // TODO 验签失败则记录异常日志，并在response中返回failure.
                result="failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("tradeQuery")
    @ResponseBody
    public String tradeQuery(HttpSession session)
    {
        String result = null;
        try {
            UserPay pay= (UserPay) session.getAttribute("userPaySettlement");
            AlipayClient alipayClient = new DefaultAlipayClient(APLIAY_TEST_URL,APPID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY);//获得初始化的AlipayClient
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类

            Map<String,Object> alipayMap=new HashMap<String,Object>();
            alipayMap.put("out_trade_no",pay.getOutTradeNo());

            request.setBizContent(String.valueOf(JSONObject.fromObject(alipayMap)));//设置业务参数
            AlipayTradeQueryResponse response = null;
            response = alipayClient.execute(request);


            if(response.isSuccess())
            {
                result="success";
                Date date=new Date();

                pay.setTradeNo(response.getTradeNo());
                pay.setTradeStatus(response.getTradeStatus());
                pay.setPayDate(new java.sql.Date(date.getTime()));
                payDao.updateUserPayInfo(pay);

                Subscribe payment=new Subscribe();
                payment.setPayType(pay.getPayType());
                payment.setUserId(pay.getUserId());

                Subscribe validityPay=payDao.hasPayment(pay.getUserId());
                Calendar calendar=Calendar.getInstance();
                //判断当前时间是否在有效期之后
                if(date.after(validityPay.getValidityDate()))
                {
                    calendar.setTime(date);
                }
                else
                {
                    calendar.setTime(validityPay.getValidityDate());
                }

                switch (pay.getPayType())
                {
                    case 0:calendar.add(Calendar.YEAR,1);
                        break;
                    case 1:calendar.add(Calendar.MONTH,1);
                        break;
                }
                payment.setValidityDate(new java.sql.Date(calendar.getTime().getTime()));
                if( validityPay == null)
                {
                    payDao.createPayment(payment);
                }
                else if ( validityPay != null)
                {
                    payDao.updateUserPayment(payment);
                }
            }
            else
            {
                result="failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("paySuccess")
    public String paySuccess()
    {
        return "10000";
    }

    public Map<String, String> paramMap(HttpServletRequest request)
    {
        Map<String, String> paramsMap =new HashMap<String,String>();
        Enumeration enumeration= request.getParameterNames();
        while (enumeration.hasMoreElements())
        {
            String name= String.valueOf(enumeration.nextElement());
            paramsMap.put(name,request.getParameter(name));
        }
        return paramsMap;
    }
}
