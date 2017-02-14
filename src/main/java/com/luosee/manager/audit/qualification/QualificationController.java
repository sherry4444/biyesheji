package com.luosee.manager.audit.qualification;

import com.luosee.common.HttpInfo;
import com.luosee.po.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by server2 on 2016/11/15.
 */
@Controller
public class QualificationController {

//    @Resource(name = "qualificationDao")
//    private QualificationDao qualificationDao;

    @Autowired
    private QualificationService qualificationService;
    private int totalNumber;

    @RequestMapping(value = "audit",produces = "text/html;charset=UTF-8")
    @Secured("ROLE_ADMIN")
    public String select(Model model,
                         @RequestParam(value = "currentPage",defaultValue = "1",required=false)int currentPage,
                         @RequestParam(value="flag",defaultValue = "1",required = false)Integer flag,
                         @RequestParam(value="flag1",defaultValue = "0",required = false)Integer flag1,
                         @RequestParam(value = "title",required = false)String title,
                         @RequestParam(value = "num",required = false)Integer num
    ) throws UnsupportedEncodingException {

        Map<String,Object> parameter = new HashMap<String, Object>();
        Qualification qualification = new Qualification();
        Page page = new Page();

       // title = URLDecoder.decode(title, "UTF-8");
        try {
            if(title !=null && title.equals(new String(title.getBytes("ISO-8859-1"), "ISO-8859-1")))
            {
                title = new String(title.getBytes("ISO-8859-1"), "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
      /*  if (title != null) {
            title = URLDecoder.decode(title, "UTF-8");
            title = new String(title.getBytes("ISO-8859-1"), "utf-8");
        }*/
        if (num != null) {page.setPageNumber(num);} else {num=page.getPageNumber();}
        page.setCurrentPage(currentPage);
        parameter.put("page",page);
        parameter.put("title",title);


        //未审核
        if(flag == 1){
            parameter.put("qualification",qualification);
            totalNumber = qualificationService.countun(parameter);
            page.setTotalNumber(totalNumber);
            page.setCurrentPage(currentPage);
            parameter.put("page",page);
            parameter.put("flag1",flag1);
            model.addAttribute("unauditList",qualificationService.selectAllunaudited(parameter));
        }
        //已审核
        if(flag == 2)
        {
            parameter.put("qualification",qualification);
            totalNumber = qualificationService.count(parameter);
            page.setTotalNumber(totalNumber);
            page.setCurrentPage(currentPage);
            parameter.put("page",page);
            parameter.put("flag1",flag1);
            model.addAttribute("auditList",qualificationService.selectAllaudited(parameter));
        }

        model.addAttribute("flag1",flag1);
        model.addAttribute("flag",flag);
        model.addAttribute("num",num);
        model.addAttribute("title",title);
        model.addAttribute("page",page);
        model.addAttribute("imgurl", new HttpInfo());
        return "/manager/audit";
    }



    /* 审核 否决功能*/
    @RequestMapping(value = "audit/veto", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String veto(@RequestParam("companyName")String companyName,
                       @RequestParam("cerCausepre")String cerCausepre,
                       @RequestParam(value="cerCause",required=false)String cerCause) throws Exception{

        //获取 comanyName 公司名  cerCausepre+cerCause 否决信息
        //String comanyName="佛山市螺丝科技";
        //System.out.println("=========companyName============"+companyName+"=====cerCausepre:"+cerCausepre+"====cerCause:"+cerCause);
        String certificationCause = cerCausepre+","+cerCause;
        Qualification qualification = new Qualification();
        qualification.setCompanyName(companyName);
        qualification.setCertificationStatus("认证失败");
        qualification.setCertificationCause(certificationCause);

        qualificationService.veto(qualification);
        return "否决成功";
    }


   /* 审核 通过功能*/
   @RequestMapping(value = "audit/pass", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
   @ResponseBody
   @Secured("ROLE_ADMIN")
   public String veto(@RequestParam("certificationId")int certificationId)throws Exception{
       //System.out.println("=========certificationId============"+certificationId);
       Qualification qualification = new Qualification();
       qualification.setCertificationId(certificationId);
       qualification.setCertificationStatus("认证成功");
       qualificationService.pass(qualification);
       return "认证成功";
   }

    /* 审核 取消通过功能*/
    @RequestMapping(value = "audit/cancel", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String cancel(@RequestParam("certificationId")int certificationId)throws Exception{
        System.out.println("=========certificationId============"+certificationId);
        Qualification qualification = new Qualification();
        qualification.setCertificationId(certificationId);
        qualification.setCertificationStatus("认证中");
        qualificationService.cancel(qualification);
        return "取消认证";
    }


    /* 审核 取消通过功能*/
    @RequestMapping(value = "audit/change", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String cancel(Qualification qualification)throws Exception{
       // System.out.println(qualification.getCertificationId());
        qualificationService.change(qualification);
        return "状态已改成："+qualification.getCertificationStatus();
    }


}
