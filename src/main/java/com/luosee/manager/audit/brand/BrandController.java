package com.luosee.manager.audit.brand;

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
 * Created by server2 on 2016/12/15.
 */
@Controller
public class BrandController {

    @Autowired
    private BrandService brandService;
    private int totalNumber;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "brand",produces = "text/html;charset=UTF-8")
    public String select(Model model, @RequestParam(value = "currentPage",defaultValue = "1",required=false)int currentPage,
                         @RequestParam(value="flag",defaultValue = "1",required = false)Integer flag,
                         @RequestParam(value="flag1",required = false)Integer flag1,
                         @RequestParam(value = "title",required = false)String title,
                         @RequestParam(value = "num",required = false)Integer num
    ) throws UnsupportedEncodingException {


        Map<String,Object> parameter = new HashMap<String, Object>();
        Brand brand = new Brand();
        Page page = new Page();
//        title = URLDecoder.decode(title, "UTF-8");
        try {
            if(title !=null && title.equals(new String(title.getBytes("ISO-8859-1"), "ISO-8859-1")))
            {
                title = new String(title.getBytes("ISO-8859-1"), "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

       /* if (title != null) {
            title = URLDecoder.decode(title, "UTF-8");
            title = new String(title.getBytes("ISO-8859-1"), "utf-8");
        }*/
        if (num != null) {page.setPageNumber(num);} else {num=page.getPageNumber();}
        page.setCurrentPage(currentPage);
        parameter.put("page",page);
        parameter.put("title",title);



        //未审核
        if(flag == 1){
            parameter.put("brand",brand);
            totalNumber = brandService.countun(parameter);
            page.setTotalNumber(totalNumber);
            page.setCurrentPage(currentPage);
            parameter.put("page",page);
            parameter.put("flag1",flag1);
            model.addAttribute("unauditList",brandService.selectAllunaudited(parameter));
        }
        //已审核
        if(flag == 2)
        {
            parameter.put("brand",brand);
            totalNumber = brandService.count(parameter);
            page.setTotalNumber(totalNumber);
            page.setCurrentPage(currentPage);
            parameter.put("page",page);
            parameter.put("flag1",flag1);
            model.addAttribute("auditList",brandService.selectAllaudited(parameter));
        }

        model.addAttribute("flag1",flag1);
        model.addAttribute("flag",flag);
        model.addAttribute("num",num);
        model.addAttribute("title",title);
        model.addAttribute("page",page);
        model.addAttribute("imgurl", new HttpInfo());
        return "/manager/brand";
    }


    /* 审核 否决功能*/
    @RequestMapping(value = "brand/veto", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String veto(@RequestParam("sellerName")String sellerName,
                       @RequestParam("cerCausepre")String cerCausepre,
                       @RequestParam(value="cerCause",required=false)String cerCause) throws Exception{

        //System.out.println("=========sellerName============"+sellerName+"=====cerCausepre:"+cerCausepre+"====cerCause:"+cerCause);
        String certificationCause = cerCausepre+","+cerCause;
        Brand brand = new Brand();
        brand.setSellerName(sellerName);
        brand.setCertificationStatus("认证失败");
        brand.setCertificationCause(certificationCause);

        brandService.veto(brand);
        return "否决成功";
    }


    /* 审核 通过功能*/
    @RequestMapping(value = "brand/pass", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String veto(@RequestParam("sellerId")int sellerId)throws Exception{
        //System.out.println("=========sellerId============"+sellerId);
        Brand brand = new Brand();
        brand.setSellerId(sellerId);
        brand.setCertificationStatus("认证成功");
        brandService.pass(brand);
        return "认证成功";
    }

    /* 审核 取消通过功能*/
    @RequestMapping(value = "brand/cancel", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String cancel(@RequestParam("sellerId")int sellerId)throws Exception{
        System.out.println("=========sellerId==========="+sellerId);
        Brand brand = new Brand();
        brand.setSellerId(sellerId);
        brand.setCertificationStatus("认证中");
        brandService.cancel(brand);
        return "取消认证";
    }

    /* 审核 取消通过功能*/
    @RequestMapping(value = "brand/change", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String cancel(Brand brand)throws Exception{
        System.out.println(brand.toString());
        brandService.change(brand);
        return "状态已改成："+brand.getCertificationStatus();
    }
}
