package com.luosee.manager.audit.product;

import com.luosee.common.HttpInfo;
import com.luosee.manager.modeling.UploadFile;
import com.luosee.oss.Constant;
import com.luosee.oss.UploadImage;
import com.luosee.po.Page;
import com.luosee.product.style.StyleService;
import com.luosee.product.tag.TagsService;
import com.luosee.product.type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by server2 on 2016/12/22.
 */
@Controller
public class ProductAuditController {

    @Autowired
    private ProductAuditService productAuditService;
    private int totalNumber;
    @Autowired
    private StyleService styleService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagsService tagsService;
    private UploadImage uploadImage;
    @Value("${dataSource.url}")
    private String datasource;

    @RequestMapping(value = "productaudit",produces = "text/html;charset=UTF-8")
    @Secured("ROLE_ADMIN")
    public String select(Model model,
                         @RequestParam(value = "currentPage",defaultValue = "1",required=false)int currentPage,
                         @RequestParam(value="flag",defaultValue = "1",required = false)Integer flag,
                         @RequestParam(value="flag1",required = false)Integer flag1,
                         @RequestParam(value = "title",required = false)String title,
                         @RequestParam(value = "num",required = false)Integer num
    ) throws UnsupportedEncodingException {


        Map<String,Object> parameter = new HashMap<String, Object>();
        com.luosee.manager.audit.product.ProductAudit product = new com.luosee.manager.audit.product.ProductAudit();
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
            parameter.put("product",product);
            totalNumber =  productAuditService.countun(parameter);
            page.setTotalNumber(totalNumber);
            page.setCurrentPage(currentPage);
            parameter.put("page",page);
            parameter.put("flag1",flag1);
            model.addAttribute("unauditList",productAuditService.selectAllunaudited(parameter));
        }
        //已审核
        if(flag == 2)
        {
            parameter.put("product",product);
            totalNumber = productAuditService.count(parameter);
            page.setTotalNumber(totalNumber);
            page.setCurrentPage(currentPage);
            parameter.put("page",page);
            parameter.put("flag1",flag1);
            model.addAttribute("auditList",productAuditService.selectAllaudited(parameter));
            model.addAttribute("typeList",typeService.findType());
            model.addAttribute("styleList",styleService.findStyle());
            model.addAttribute("tagsList",tagsService.findTag());
        }

        model.addAttribute("flag1",flag1);
        model.addAttribute("flag",flag);
        model.addAttribute("num",num);
        model.addAttribute("title",title);
        model.addAttribute("page",page);
        model.addAttribute("imgurl", new HttpInfo());

        return "/manager/productaudit";
    }



    /* 审核 否决功能*/
    @RequestMapping(value = "productaudit/veto", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String veto(@RequestParam("productName")String productName,
                       @RequestParam("cerCausepre")String cerCausepre,
                       @RequestParam(value="cerCause",required=false)String cerCause) throws Exception{

        String certificationCause = cerCausepre+","+cerCause;
        com.luosee.manager.audit.product.ProductAudit product=new com.luosee.manager.audit.product.ProductAudit();
        product.setProductName(productName);
        product.setCertificationStatus("认证失败");
        product.setCertificationCause(certificationCause);

        productAuditService.veto(product);
        return "否决成功";
    }


    /* 审核 通过功能*/
    @RequestMapping(value = "productaudit/pass", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String veto(@RequestParam("productId")int productId)throws Exception{
        //System.out.println("=========productId============"+productId);
        com.luosee.manager.audit.product.ProductAudit product=new com.luosee.manager.audit.product.ProductAudit();
        product.setProductId(productId);
        product.setCertificationStatus("认证成功");
        productAuditService.pass(product);
        return "认证成功";
    }

    /* 审核 取消通过功能*/
    @RequestMapping(value = "productaudit/cancel", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String cancel(@RequestParam("productId")int productId)throws Exception{

        com.luosee.manager.audit.product.ProductAudit product=new com.luosee.manager.audit.product.ProductAudit();
        product.setProductId(productId);
        product.setCertificationStatus("认证中");
        productAuditService.cancel(product);
        return "取消认证";
    }

    /* 审核 取消通过功能*/
    @RequestMapping(value = "productaudit/change", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String cancel(ProductAudit productAudit)throws Exception{
        System.out.println(productAudit.toString());
        productAuditService.change(productAudit);
        return "状态已改成："+productAudit.getCertificationStatus();
    }


    /*上架*/
    @RequestMapping(value = "productaudit/status", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String changeProductStatus(ProductAudit productAudit) throws Exception{
        if(productAudit.getProductstatus().equals("0")){ productAudit.setProductstatus("试上架");}
        else if(productAudit.getProductstatus().equals("1")){ productAudit.setProductstatus("正式上架");}
        productAuditService.changestatus(productAudit);
        return "操作成功";
    }

    /*编辑*/
    @RequestMapping(value = "productaudit/edit", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String edit(
            @RequestParam(value = "ImgFile",required = false)MultipartFile ImgFile,
            HttpServletRequest request, HttpServletResponse response,ProductAudit productAudit) throws Exception{

        UploadFile uploadFile = new UploadFile();
        if (ImgFile != null && !ImgFile.isEmpty()) {
            try {
                String imgpath = uploadFile.Uploadimg(ImgFile, Constant.OSS_PRODUCT_IMAGE, datasource);
                productAudit.setIntroduceImg(imgpath);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        productAuditService.edit(productAudit);
        return "上传成功";
    }


}
