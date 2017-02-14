package com.luosee.user;


import com.luosee.business.*;
import com.luosee.common.*;
import com.luosee.oss.Constant;
import com.luosee.oss.UploadImage;
import com.luosee.po.Page;
import com.luosee.support.web.MessageHelper;
import com.luosee.token.Token;
import com.luosee.verify.VerifyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by server1 on 2016/11/15.
 */
@Controller
public class UserController {

    @Resource(name="userDao")
    private UserDao userDao;

    @Resource(name = "businessDao")
    private BusinessDao businessDao;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private UserService userService;

    @Value("${dataSource.url}")
    private  String datasource;

    @Value("${app.version}")
    private String version;

    private final static  String RETRIEVE_PASSWORD="user/retrievePassword";
    private final static  String RETRIEVE_NEW_PASSWORD="user/retrieveNewPassword";
    private static final String COMPLETE_HINT="show/completeHint";
    final Logger logger= LoggerFactory.getLogger(UserController.class);

    /**
     *通过用户名查找用户信息
     * @param principal
     * 用户信息类
     * @return
     * 返回json格式的用户信息
     */
    @RequestMapping(value = "user/current", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public TbUser currentAccount(Principal principal) {
        Assert.notNull(principal);
        return userDao.findOneByEmail(principal.getName());
    }

    /**
     *通过ID查找用户信息
     * @param id
     * 用户的ID
     * @return
     * 返回json格式的用户信息
     */
    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public TbUser account(@PathVariable("id") Long id) {
        return userDao.findOne(id);
    }

    /**
     *
     * @return
     * 未登录用户跳转的密码修改页
     */
    @RequestMapping("toretrievePage")
    public String toretrieve()
    {
        return RETRIEVE_PASSWORD;
    }

    /**
     *
     * @param principal
     * 用户信息类
     * @param model
     *
     * @return
     * 已登录用户跳转到相应的密码修改页
     */
    @RequestMapping("modifyPassword")
    @Token(save = true)
    public String modifyPassword(Principal principal,Model model)
    {
        SaltedUser user=(SaltedUser)SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if("3".equals(user.getGrade()))
        {
            return "redirect:/qualifyUserInfo";
        }
        Pattern pattern=Pattern.compile("^[0-9]{11}$");
        Matcher matcher=pattern.matcher(principal.getName());
        boolean ismobile=false;
        if(matcher.matches())
        {
            ismobile=true;
        }
        model.addAttribute("ismobile",ismobile);
        return "user/modifyPassword";
    }

    /**
     *
     * @param model
     * @param user
     * 用户信息
     * @param verificationCode
     * 手机验证码
     * @param session
     * @return
     */
    @RequestMapping(value = "retrieveNewPassword",method = RequestMethod.POST)
    @Token(save = true)
    public String retrieveNew(Model model, TbUser user, String verificationCode, HttpSession session)
    {
        if("3".equals(userDao.queryUserGrade(user.getUsername())))
        {
            model.addAttribute("subUserErrors",3);
            return RETRIEVE_PASSWORD;
        }
        Pattern pattern=Pattern.compile("^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$");
        Matcher matcher=pattern.matcher(user.getUsername());
        model.addAttribute("user",user);
        if(!matcher.matches())
        {
            session.setAttribute("verifyCode",verificationCode);
            return RETRIEVE_NEW_PASSWORD;
        }
        HttpConnection httpConnection=new HttpConnection("users/forget_password","POST");
        httpConnection.setParamKey("username",user.getUsername());
        String result=httpConnection.EstablishConnection();
        if("\"code\":1004".contains(result))
        {
            model.addAttribute("message","用户名不存在");
            return RETRIEVE_PASSWORD;
        }
        return "user/toemail";
    }

    //保存新的密码
    @RequestMapping(value = "saveNewPassword",method = RequestMethod.POST)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Token(remove = true)
    public String saveNewPassword(Principal principal,Model model, @Valid TbUser user, RedirectAttributes ra, HttpSession session, BindingResult result, ValidatePoJo validatePoJo,String verificationCode)
    {
        if(user.getUsername() == null)
        {
            user.setUsername(principal.getName());
        }

        if("3".equals(userDao.queryUserGrade(user.getUsername())))
        {
            model.addAttribute("subUserErrors",3);
            return RETRIEVE_PASSWORD;
        }

        if (validatePoJo.setValidPoJo(user,result).hasErrors())
        {
            model.addAttribute("user",user);
            return RETRIEVE_NEW_PASSWORD;
        }

        Pattern pattern=Pattern.compile("^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$");
        Matcher matcher=pattern.matcher(user.getUsername());
        if(!matcher.matches())
        {
            String verifyCode= (String) session.getAttribute("verifyCode");
            verificationCode=verifyCode==null?verificationCode:verifyCode;

            VerifyType verifyType=new VerifyType();
            Map<String,Object> map=verifyType.Mobeil(user,verificationCode,1);

            boolean hasverifyKey=map.containsKey("verifyKey");
            //判断验证码是否错误
            if(!hasverifyKey)
            {
                model.addAttribute("verifyCodeErrors","验证码错误");
                model.addAttribute("user",user);
                return RETRIEVE_PASSWORD;
            }
        }

        DaedalusePasswordEncoder passwordEncoder=new DaedalusePasswordEncoder();
        String salt=userService.qualifyUserInfo(user.getUsername()).getSalt();
        user.setPassword(passwordEncoder.encodePassword(user.getPassword(),salt));
        userService.updataPassword(user);

        MessageHelper.addSuccessAttribute(ra, "修改密码成功");
        return COMPLETE_HINT;
    }

    //跳到品牌商认证页
    @RequestMapping(value = "authentication")
    @Token(save = true)
    public String authentication()
    {
        return "user/attestation";
    }

    //查询用户信息
    @RequestMapping(value = "qualifyUserInfo",method = RequestMethod.GET)
    public String qualifyUserInfo(Model model, Principal principal, HttpServletRequest request)
    {
        BusinessSubUser businessSubUser=businessService.findOneBusinessSub(principal.getName());
        model.addAttribute("user",userService.qualifyUserInfo(principal.getName()));

        if(("ROLE_SELLER".equals(QueryUserRole.findUserRole(request)) || "ROLE_DECORATION".equals(QueryUserRole.findUserRole(request))) && businessSubUser!=null)
        {
            model.addAttribute("businessInfo",businessService.findBusinessInfoByName(principal.getName()));
            model.addAttribute("Certification",businessService.findOneCertification(principal.getName()));
        }
//        else if ("ROLE_SELLER".equals(QueryUserRole.findUserRole(request)) && businessSubUser==null)
//        {
//            return "home/homeSignedIn";
//        }
        return "user/userinfo";
    }

    //修改用户信息
    @RequestMapping(value = "updateUserInfo",method = RequestMethod.POST)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String updateUserInfo(Principal principal, @Valid TbUser user, BusinessInfo businessInfo, MultipartFile logoFile, HttpServletRequest request,BindingResult result)
    {
        SaltedUser tbuser=(SaltedUser)((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());

        user.setUsername(principal.getName());
        String HasNickName=null;
        if(!user.getNickname().equals("") && user.getNickname() != null)
        {
            HasNickName=userDao.isHasNickName(user);
        }
        if(user.getNickname() != null && HasNickName !=null &&  !HasNickName.equals(principal.getName()) )
        {
            return "redirect:/qualifyUserInfo?error=2";
        }
        user.setUsername(principal.getName());
        userService.updateUserInfo(user);

        user=userService.qualifyUserInfo(principal.getName());
        QueryUserRole.updateRole(request,user,businessDao);

        if(("ROLE_SELLER".equals(QueryUserRole.findUserRole(request)) || "ROLE_DECORATION".equals(QueryUserRole.findUserRole(request))) && !"3".equals(user.getGrade()) && businessInfo!=null)
        {
            if (!logoFile.getOriginalFilename().equals(""))
            {
                String logo=businessService.findBusinessLogoByName(principal.getName());
                UploadImage uploadImage=new UploadImage();

                uploadImage.deleteFile(datasource,logo);

                businessInfo.setLogo(uploadImage.uploadFile(logoFile,datasource,"logo", Constant.OSS_BUSINESS_IMAGES+"/"));
            }
            Map<String,Object> infoMap=new HashMap<String,Object>();
            infoMap.put("username",principal.getName());
            infoMap.put("business",businessInfo);
            businessService.updateBusinessUserInfo(infoMap);
        }
        return "redirect:/qualifyUserInfo";
    }

    @RequestMapping("queryChildUser")
    @Token(save = true)
    public String queryChildUser(Principal principal, Model model, Page page)
    {
        SaltedUser user=(SaltedUser)((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
        if("3".equals(user.getGrade()) && !"认证成功".equals(user.getCertificationStatus()))
        {
            return "redirect:/qualifyUserInfo";
        }
        Integer count=userService.findChildCount(principal.getName());
        count=count==null?0:count;
        page.setTotalNumber(count);
        int userId=userDao.getUserIdByName(principal.getName());
        page.setQuerytool(userId);
        model.addAttribute("childUserList",userService.findChildUser(page));
        model.addAttribute("page",page);
        return "user/childAccount";
    }

    @RequestMapping(value = "addChildUser")
    @ResponseBody
    @Token(remove = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String addChildUser(Principal principal,TbUser user,UserSubAccount subAccount, String verificationCode,HttpServletRequest request)
    {
        Integer count=userService.findChildCount(principal.getName());
        if(count >= 5)
        {
            return "more than 5 Child account";
        }
        else if(userService.qualifyUserInfo(user.getUsername()) == null)
        {
            java.util.regex.Pattern pattern= java.util.regex.Pattern.compile("^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$");
            Matcher matcher=pattern.matcher(user.getUsername());
            int type=matcher.matches()? 0 : 1;

            String verifyKey=null;

            HttpConnection http=new HttpConnection("users/register1","POST");
            //判断是否为手机注册，是则确认验证码,不是则直接注册
            if(verificationCode!=""&&verificationCode!=null)
            {
                type=1;
                VerifyType verifyType=new VerifyType();
                Map<String,Object> map=verifyType.Mobeil(user,verificationCode,0);

                boolean hasverifyKey=map.containsKey("verifyKey");
                //判断验证码是否错误
                if(!hasverifyKey)
                {
                    return "verifyCode errors";
                }
                else
                {
                    verifyKey= (String) map.get("verifyKey");
                    http.setParamKey("verifyKey",verifyKey);
                }
            }

            String password= Utils.getMD5HexString(Utils.getMD5HexString(user.getPassword())+"LSda2015");
            String result=null;
            http.setParamKey("username",user.getUsername());
            http.setParamKey("password",password);
            http.setParamKey("type",type);
            http.setParamKey("version",version);
            http.setParamKey("client","server-business");
            http.setParamKey("role",0);
            result=http.EstablishConnection();
            if(result.contains("\"code\":0"))
            {
                return addSubUser(user,subAccount,principal,request);
            }
            return "Add sub account not success";
        }
        else
        {
            return "Child account already add";
        }
    }

    @RequestMapping(value = "delChildAccount",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String deleteChildAccount(UserItem userItem,Principal principal)
    {
        if(userItem.getChildId()!=null && userItem.getChildId().size() > 0)
        {
            Map<String,Object> childInfo=new HashMap<String,Object>();
            childInfo.put("username",principal.getName());
            childInfo.put("childIds",userItem.getChildId());
            userService.deleteChildAccount(childInfo,datasource);
        }
        return "delete success";
    }

    @RequestMapping(value = "updateChildPwd",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Token(remove = true)
    public String updateChildPwd(UserItem userItem,Principal principal)
    {
        if(userItem.getAccount()!=null && userItem.getAccount().size() > 0)
        {
            for(int i=0;i<userItem.getAccount().size();i++)
            {

                DaedalusePasswordEncoder passwordEncoder=new DaedalusePasswordEncoder();
                TbUser tbuser=userService.qualifyUserInfo(userItem.getAccount().get(i).getUsername());
                String password=passwordEncoder.encodePassword(userItem.getAccount().get(i).getPassword(),tbuser.getSalt());
                tbuser.setPassword(password);
                userItem.getAccount().set(i,tbuser);
            }

            Map<String,Object> childInfo=new HashMap<String,Object>();
            int userId=userDao.getUserIdByName(principal.getName());
            childInfo.put("userId",userId);
            childInfo.put("userInfo",userItem.getAccount());
            userService.updateChildPassWord(childInfo);
        }
        return "update success";
    }

    public String addSubUser(TbUser user,UserSubAccount subAccount,Principal principal,HttpServletRequest request)
    {
        TbUser tbuser=userService.queryUserIdPassword(user.getUsername());
        DaedalusePasswordEncoder passwordEncoder=new DaedalusePasswordEncoder();
        String password=passwordEncoder.encodePassword(user.getPassword(),tbuser.getSalt());
        if(password.equals(tbuser.getPassword()))
        {
            int userId=userDao.getUserIdByName(principal.getName());
            subAccount.setChildUserId(tbuser.getUserId());
            subAccount.setParentUserId(BigInteger.valueOf(userId));

            try {
                if(subAccount.getRemarks() !=null && subAccount.getRemarks().equals(new String(subAccount.getRemarks().getBytes("ISO-8859-1"), "ISO-8859-1"))) {
                    subAccount.setRemarks(new String(subAccount.getRemarks().getBytes("ISO-8859-1"), "utf-8"));
                }
            }catch (NullPointerException e){

            } catch(UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            userService.addChildUser(subAccount);
            tbuser.setGrade(String.valueOf(3));


            TbUser parentUser=new TbUser();
            parentUser.setUsername(principal.getName());
            parentUser.setGrade(String.valueOf(2));

            List<TbUser> userList=new ArrayList<TbUser>();
            userList.add(tbuser);
            userList.add(parentUser);
            userService.updateSubUserInfo(userList);

            //子账号设置为装修公司账号
            String userRole= String.valueOf(userDao.queryUserRole(tbuser.getUsername()));
            if(!"2".equals(userRole) && !"5".equals(userRole))
            {
                tbuser.setRole(2);
                userService.updateUserRole(tbuser);
            }
            return "Add sub account success";
        }
        return "Sub account password error";
    }
}
