package com.luosee.business;

import com.luosee.common.SaltedUser;
import com.luosee.common.ValidatePoJo;
import com.luosee.oss.Constant;
import com.luosee.oss.UploadImage;
import com.luosee.po.Page;
import com.luosee.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.math.BigInteger;
import java.security.Principal;

/**
 * Created by server1 on 2016/11/21.
 */
@Controller
public class SellerConroller {
    @Autowired
    private SellerService sellerService;

    final Logger logger= LoggerFactory.getLogger(SellerConroller.class);


    @Autowired
    private BusinessService businessService;

    @Value("${dataSource.url}")
    private  String datasource;
    //查询品牌
    @RequestMapping("qualifyBrand")
    @Token(save=true)
    @Secured({"ROLE_SELLER", "ROLE_ADMIN","ROLE_DECORATION"})
    public String qualifyBrand(Model model,Page page)
    {
        SaltedUser user=(SaltedUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if("3".equals(user.getGrade()) && !"认证成功".equals(user.getCertificationStatus()))
        {
            return "redirect:/qualifyUserInfo";
        }
        page.setQuerytool(user.getUsername());
        Integer count=sellerService.sellerCount(page);
        count=count==null?0:count;
        page.setTotalNumber(count);
        model.addAttribute("sellerList",sellerService.findSellerByName(page));
        model.addAttribute("page",page);
        return  "business/brandList";
    }

    //增加品牌
    @RequestMapping(value = "addSeller",method = RequestMethod.POST)
    @Token(remove=true)
    @Secured({"ROLE_SELLER", "ROLE_ADMIN","ROLE_DECORATION"})
    public String addSeller(@Valid Seller seller, MultipartFile sellerImgFile, BindingResult result, ValidatePoJo validatePoJo, MultipartFile powerAttorneyFile, MultipartFile trademarkFile)
    {
        SaltedUser user=(SaltedUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if("3".equals(user.getGrade()) && !"认证成功".equals(user.getCertificationStatus()))
        {
            return "redirect:/qualifyUserInfo";
        }
        if (validatePoJo.setValidPoJo(seller,result).hasErrors() || sellerImgFile.getOriginalFilename().equals("")||  powerAttorneyFile.getOriginalFilename().equals("") ||  trademarkFile.getOriginalFilename().equals(""))
        {
            return  "business/brandList";
        }

        int id=businessService.findIdByName(user.getUsername()).intValue();
        UploadImage uploadImage=new UploadImage();
        String filename=uploadImage.uploadFile(sellerImgFile,datasource,id, Constant.OSS_BUSINESS_IMAGES+"/");
        String powerAttorneyName=uploadImage.uploadFile(powerAttorneyFile,datasource,id, Constant.OSS_BUSINESS_IMAGES+"/");
        String trademarkName=uploadImage.uploadFile(trademarkFile,datasource,id, Constant.OSS_BUSINESS_IMAGES+"/");

        seller.setBusinessInfoId(BigInteger.valueOf(id));
        seller.setSellerLogo(filename);
        seller.setPowerAttorney(powerAttorneyName);
        seller.setTrademark(trademarkName);
        sellerService.addSeller(seller);

        return "redirect:/qualifyBrand";
    }

    @RequestMapping("deleteSeller")
    @ResponseBody
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Secured({"ROLE_SELLER", "ROLE_ADMIN","ROLE_DECORATION"})
    public String deleteSeller(int id)
    {
        SaltedUser user=(SaltedUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if("3".equals(user.getGrade()) && !"认证成功".equals(user.getCertificationStatus()))
        {
            return "redirect:/qualifyUserInfo";
        }
        sellerService.deleteSeller(id,datasource);
        return "delete success";
    }

    @RequestMapping("updateSeller")
    @ResponseBody
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Token(remove = true)
    @Secured({"ROLE_SELLER", "ROLE_ADMIN","ROLE_DECORATION"})
    public String updateSeller(Seller seller,MultipartFile sellerImgFile)
    {
        SaltedUser user=(SaltedUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if("3".equals(user.getGrade()) && !"认证成功".equals(user.getCertificationStatus()))
        {
            return "redirect:/qualifyUserInfo";
        }
        sellerService.updateBrand(seller,datasource,sellerImgFile);
        return "update success";
    }
}
