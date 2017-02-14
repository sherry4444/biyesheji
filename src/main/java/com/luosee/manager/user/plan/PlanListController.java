package com.luosee.manager.user.plan;


import com.luosee.common.HttpInfo;
import com.luosee.po.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/*方案列表管理页面*/

@Controller
public class PlanListController {

    final Logger logger = LoggerFactory.getLogger(PlanListController.class);
    @Resource(name = "planListDao")
    private PlanListDao planListDao;
    private Integer totalNumber;
    @Autowired
    private PlanListService planListService;


    @RequestMapping(value = "planList",produces = "text/html;charset=UTF-8")
    @Secured("ROLE_ADMIN")
    public String select(Model model, Page page,
                         @RequestParam(value = "currentPage", defaultValue = "1", required = false) int currentPage,
                         @RequestParam(value = "flag", required = false, defaultValue = "0") Integer flag,
                         @RequestParam(value = "title", required = false) String title,
                         @RequestParam(value = "num", required = false)Integer num) throws UnsupportedEncodingException {


        PlanList planList = new PlanList();
        Map<String, Object> parameter = new HashMap<String, Object>();

        if (title != null) {
//            title = URLDecoder.decode(title, "UTF-8");
            try {
                if(title !=null && title.equals(new String(title.getBytes("ISO-8859-1"), "ISO-8859-1")))
                {
                    title = new String(title.getBytes("ISO-8859-1"), "utf-8");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        if (num != null) {page.setPageNumber(num);} else {num=page.getPageNumber();}
        totalNumber = planListDao.count(title);
        page.setTotalNumber(totalNumber);
        page.setCurrentPage(currentPage);
        parameter.put("page", page);
        parameter.put("title", title);
        parameter.put("flag", flag);
        /*
           0---> 升序  1--->降序 方案ID
           2---> 升序  3--->降序 方案名
           4---> 升序  5--->降序 用户名
           6---> 升序  7--->降序 创建时间
           8---> 升序  9--->降序 最后更新时间
        */

        /*将图片前后缀imgurl和记录哪种排序flag传到页面*/
        model.addAttribute("imgurl", new HttpInfo());
        model.addAttribute("num", num);
        model.addAttribute("flag", flag);
        model.addAttribute("title", title);
        model.addAttribute("planList", planListDao.selectAll(parameter));
        return "/manager/planList";
    }

    @RequestMapping(value = "planList/copyaccount", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String copyAccount(@RequestParam(value = "account", required = false) String account,
                              @RequestParam(value = "planId", required = false) String planId,
                              @RequestParam(value = "userId", required = false) String userId) throws Exception {
        planListService.copyaccount(account, userId, planId);
        return "成功复制账号";
    }
}
