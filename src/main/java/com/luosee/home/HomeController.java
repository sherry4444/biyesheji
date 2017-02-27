package com.luosee.home;

import com.luosee.common.SaltedUser;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

//	@Autowired
//	private BusinessService businessService;

//    @Autowired
//    private UserService userService;

//	@Resource(name = "payDao")
//	private PayDao payDao;

	final Logger logger= LoggerFactory.getLogger(HomeController.class);

//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String index(Model model, HttpServletRequest request, HttpSession session, Principal principal) {
//		//判断是否登录了
//		if (principal == null)
//		{
//			return "signin/login";
//		}
//
//		SaltedUser user=(SaltedUser) SecurityContextHolder.getContext()
//				.getAuthentication()
//				.getPrincipal();
//		//BusinessSubUser businessSubUser=businessService.findOneBusinessSub(securityContextImpl.getAuthentication().getName());
//
////		List<Subscribe> userPay = null;
////		if(!"3".equals(user.getGrade()))
////		{
////			userPay=payDao.userPackage(user.getUsername());
////		}
////        session.setAttribute("userPay",userPay);
//		return "";
////		return AcountLogin.loginType(QueryUserRole.findUserRole(request));
//	}

	@RequestMapping("tobusiness")
	@Secured("ROLE_ADMIN")
	public String tobusiness(HttpSession session)
	{
		String page=String.valueOf(session.getAttribute("page"));
		page = "manager/home".equals(page) ? "manager/home" : "home/homeSignedIn";
		session.setAttribute("page","manager/home".equals(page) ? "home/homeSignedIn" : "manager/home");
		return page;
	}
}

