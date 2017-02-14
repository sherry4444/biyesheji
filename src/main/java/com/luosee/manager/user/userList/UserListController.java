package com.luosee.manager.user.userList;

import com.luosee.common.HttpInfo;
import com.luosee.po.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by server2 on 2016/12/12.
 */
@Controller
public class UserListController {

    @Autowired
    private UserListService userListService;
    private int totalNumber;

    private final Logger logger = LoggerFactory.getLogger(UserListController.class);

    @RequestMapping(value="userList",produces = "text/html;charset=UTF-8")
    @Secured("ROLE_ADMIN")
    public String selectAll(Model model, Page page,
                                                    @RequestParam(value = "title",required = false)String title,
                                                    @RequestParam(value = "currentPage",defaultValue = "1",required=false)int currentPage,
                                                    @RequestParam(value = "flag",required=false,defaultValue = "0")Integer flag,
                                                    @RequestParam(value = "num",required=false)Integer num) throws UnsupportedEncodingException {

        if (num != null) {page.setPageNumber(num);} else {num=page.getPageNumber();}
        totalNumber = userListService.count(title);
        page.setCurrentPage(currentPage);
        page.setTotalNumber(totalNumber);
//        title = URLDecoder.decode(title, "UTF-8");
        if (title != null) {

            try {
                if(title !=null && title.equals(new String(title.getBytes("ISO-8859-1"), "ISO-8859-1")))
                {
                    title = new String(title.getBytes("ISO-8859-1"), "utf-8");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
           /* title = URLDecoder.decode(title, "UTF-8");
            logger.debug(byteArrayToHex(title.getBytes()));
            logger.debug(byteArrayToHex(title.getBytes("ISO-8859-1")));
            title = new String(title.getBytes("ISO-8859-1"), "utf-8");
            logger.debug(byteArrayToHex(title.getBytes()));*/
        }
        Map<String,Object> parameter = new HashMap<String, Object>();
        parameter.put("title",title);
        parameter.put("page",page);
        parameter.put("flag",flag);

        model.addAttribute("userList",userListService.selectAll(parameter));
        model.addAttribute("num", num);
        model.addAttribute("page",page);
        model.addAttribute("flag",flag);
        model.addAttribute("title",title);
        model.addAttribute("imgurl", new HttpInfo());
        model.addAttribute("userIncre",userListService.countIncre());

        return "manager/userList";
    }


    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
