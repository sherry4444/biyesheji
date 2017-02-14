package com.luosee.manager.product;

import com.luosee.common.QualifyInfo;
import com.luosee.po.Page;
import com.luosee.product.ProductService;
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
public class ProductListController {

    @Autowired
    private ProductService productService;

    @RequestMapping("queryAllProduct")
    @Secured("ROLE_ADMIN")
    public String queryProduct(Model model, Page page, QualifyInfo qualifyInfo,
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
        Integer count=productService.queryProductCount(page);
        count=count==null?0:count;
        page.setTotalNumber(count);

        model.addAttribute("productList",productService.queryProduct(page));
        model.addAttribute("page",page);
        model.addAttribute("num",num);
        return "manager/product";
    }
}
