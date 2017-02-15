package com.luosee.signin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("users")
public class LoginController {

	@RequestMapping(value = "login")
	public String signin() {
        return "signin/login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login() {
        return "home/home";
    }

    @RequestMapping(value = "/home")
    public String homefirst() {
        return "home/home";
    }

//    @RequestMapping(value = "verification")
//    public void Verification(HttpServletResponse httpResponse, HttpSession httpSession)
//    {
//        VerificationImage verificationImage=new VerificationImage();
//        String code=verificationImage.getImage(httpResponse);
//        httpSession.setAttribute("verificationCode",code);
//    }
}
