package com.luosee.signup;

import com.luosee.token.Token;
import com.luosee.user.TbUser;
import com.luosee.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


//import com.luosee.account.AccountService;


@Controller
public class SignupController {

    private static final String SIGNUP_VIEW_NAME = "signup/register";
	private static final String CHOICE_ROLE = "signup/choicerole";
	private static final String COMPLETE_HINT="show/completeHint";

	private Logger logger= LoggerFactory.getLogger(SignupController.class);
	@Value("${app.version}")
	private String version;

	@Autowired
	private UserService userService;
//	@Autowired
//	private AccountService accountService;
	
	@RequestMapping(value = "signup")
	@Token(save = true)
	public String signup(Model model) {
		TbUser user=new TbUser();
		user.setRole(0);
		model.addAttribute("user",user);
        return SIGNUP_VIEW_NAME;
	}

//	//选择注册角色
//	@RequestMapping(value = "choiceRole")
//	public String choiceRole()
//	{
//		return CHOICE_ROLE;
//	}

	/*//获取验证码
	@RequestMapping(value = "get_verify",method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String getVerify(Model model,MobileVerify mobileVerify,String url,int type)
	{
		HttpConnection httpConnection=new HttpConnection(url,"POST");
		httpConnection.setParamKey("mobileNumber",mobileVerify.getMobile());
		httpConnection.setParamKey("type",type);
		return httpConnection.EstablishConnection();
	}*/

	/*@RequestMapping(value = "signup", method = RequestMethod.POST)
	@Token(remove = true)
	public String signup(Model model, @ModelAttribute TbUser user, String verificationCode, BindingResult errors, RedirectAttributes ra, ValidatePoJo validatePoJo) {
		if (validatePoJo.setValidPoJo(user,errors).hasErrors()) {
			return SIGNUP_VIEW_NAME;
		}
		String result=null;
		String verifyKey=null;
		int type=0;//账号类型,0为邮箱，1为手机

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
				model.addAttribute("verifyCodeErrors","验证码错误");
				model.addAttribute("user",user);
				return SIGNUP_VIEW_NAME;
			}
			else
			{
				verifyKey= (String) map.get("verifyKey");
				http.setParamKey("verifyKey",verifyKey);
			}
		}

		String password=Utils.getMD5HexString(Utils.getMD5HexString(user.getPassword())+"LSda2015");
		http.setParamKey("username",user.getUsername());
		http.setParamKey("password",password);
		http.setParamKey("type",type);
		http.setParamKey("version",version);
		http.setParamKey("client","server-business");
		http.setParamKey("role",user.getRole());
		result=http.EstablishConnection();
		if(result.contains("\"code\":0"))
		{
			MessageHelper.addSuccessAttribute(ra, "注册成功");
			return COMPLETE_HINT;
		}
		model.addAttribute("user",user);
		return SIGNUP_VIEW_NAME;
	}
*/
	/**
	 * @param username 要注册的用户名
	 * @author hsb
	 */
	@RequestMapping("checkUserName")
	@ResponseBody
	public String checkUserName(String username)
	{
		if(username != "" && username != null && userService.isHasUserName(username))
		{
			return "exist";
		}
		return "not_exist";
	}
}
