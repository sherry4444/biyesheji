package com.luosee.signin;

import com.luosee.common.VerificationImage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("users")
public class SigninController {

	@RequestMapping(value = "login")
	public String signin() {
        return "signin/login";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "home/home";
    }

    @RequestMapping(value = "/home")
    public String homefirst() {
        return "home/home";
    }

    @RequestMapping(value = "verification")
    public void Verification(HttpServletResponse httpResponse, HttpSession httpSession)
    {
        VerificationImage verificationImage=new VerificationImage();
        String code=verificationImage.getImage(httpResponse);
        httpSession.setAttribute("verificationCode",code);
    }
}
