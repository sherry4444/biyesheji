package com.luosee.verify;

import com.luosee.common.HttpConnection;
import com.luosee.user.TbUser;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by server1 on 2016/11/18.
 */
public class VerifyType {
    public Map<String,Object> Mobeil(TbUser user,String verificationCode,int type)
    {
        HttpConnection httpConnection=new HttpConnection("users/verify_mobile_code","POST");
        httpConnection.setParamKey("mobileNumber",user.getUsername());
        httpConnection.setParamKey("verifyCode",verificationCode);
        httpConnection.setParamKey("type",type);
        String result=null;
        result=httpConnection.EstablishConnection();
        JSONObject jsonObject = JSONObject.fromObject(result);
        Map<String,Object> map = new HashMap<String,Object>();
        for(Iterator iter = jsonObject.keys(); iter.hasNext();){
            String key = (String)iter.next();
            map.put(key,jsonObject.get(key));
        }
        return  map;
    }
}
