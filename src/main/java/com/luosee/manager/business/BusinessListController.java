package com.luosee.manager.business;

import com.luosee.business.BusinessModelService;
import com.luosee.common.QualifyInfo;
import com.luosee.po.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * Created by server1 on 2016/12/10.
 */
@Controller
public class BusinessListController {

    @Autowired
    private BusinessModelService modelService;

    @RequestMapping(value = "queryAllModel")
    @Secured("ROLE_ADMIN")
    public String queryModel(Model model, QualifyInfo qualifyInfo, Page page,
                             @RequestParam(value = "num",required = false)Integer num)
    {
        try {
            qualifyInfo.setQualifyContent(new String(qualifyInfo.getQualifyContent().getBytes("ISO-8859-1"),"utf-8"));
        }catch (NullPointerException e){

        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (num != null) {page.setPageNumber(num);} else {num=page.getPageNumber();}
        page.setQuerytool(qualifyInfo);
        Integer count=modelService.queryModelCount(page);
        count=count==null?0:count;
        page.setTotalNumber(count);

        model.addAttribute("modelList",modelService.findModelByName(page));
        model.addAttribute("page",page);
        model.addAttribute("num",num);
        return "manager/model";
    }
}
