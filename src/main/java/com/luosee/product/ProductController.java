package com.luosee.product;

import com.luosee.business.SellerService;
import com.luosee.common.QualifyInfo;
import com.luosee.common.SaltedUser;
import com.luosee.common.ValidatePoJo;
import com.luosee.oss.Constant;
import com.luosee.oss.UploadImage;
import com.luosee.po.Page;
import com.luosee.product.style.StyleService;
import com.luosee.product.tag.TagsService;
import com.luosee.product.type.TypeService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by server1 on 2016/11/26.
 */
@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private StyleService styleService;

    final Logger logger= LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagsService tagsService;

    @Autowired
    private SellerService sellerService;

    @Value("${dataSource.url}")
    private  String datasource;


    @RequestMapping(value = "qualifyProduct", produces = "text/html;charset=UTF-8")
    @Secured({"ROLE_SELLER", "ROLE_ADMIN","ROLE_DECORATION"})
    public String qualifyProduct(Principal principal, Model model,QualifyInfo qualifyInfo,Page page,HttpServletRequest request
            ,@RequestParam (name = "time",defaultValue = "DESC") String time
            ,@RequestParam (name = "status",defaultValue = "all") String status
            ,@RequestParam (name = "type",defaultValue = "all") String type
            ,@RequestParam (name = "style",defaultValue = "all") String style)
    {
        SaltedUser user=(SaltedUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if("3".equals(user.getGrade()) && !"认证成功".equals(user.getCertificationStatus()))
        {
            return "redirect:/qualifyUserInfo";
        }
        qualifyInfo.setUsername(principal.getName());
        try {
            if(qualifyInfo.getQualifyContent() !=null && qualifyInfo.getQualifyContent().equals(new String(qualifyInfo.getQualifyContent().getBytes("ISO-8859-1"), "ISO-8859-1")))
            {
                qualifyInfo.setQualifyContent(new String(qualifyInfo.getQualifyContent().getBytes("ISO-8859-1"), "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map<String,Object> queryField=new HashMap<String,Object>();
        queryField.put("time",time);
        queryField.put("status",status);
        queryField.put("type",type);
        queryField.put("style",style);
        qualifyInfo.setQueryField(queryField);

        page.setQuerytool(qualifyInfo);
        Integer count=productService.queryProductCount(page) ;
        count=count==null?0:count;
        page.setTotalNumber(count);

        model.addAttribute("productList",productService.queryProduct(page));
        model.addAttribute("page",page);
        model.addAttribute("typeList",typeService.findType());
        model.addAttribute("styleList",styleService.findStyle());
        return  "product/productList";
    }

    @RequestMapping("addProduct")
    @Token(save = true)
    @Secured({"ROLE_SELLER", "ROLE_ADMIN","ROLE_DECORATION"})
    public String  toAddProduct(Principal principal,Model model,Page page)
    {
        SaltedUser user=(SaltedUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if("3".equals(user.getGrade()) && !"认证成功".equals(user.getCertificationStatus()))
        {
            return "redirect:/qualifyUserInfo";
        }
        page.setQuerytool(principal.getName());
        page.setTotalNumber(sellerService.sellerCount(page));

        model.addAttribute("typeList",typeService.findType());
        model.addAttribute("styleList",styleService.findStyle());
        model.addAttribute("tagList",tagsService.findTag());
        model.addAttribute("sellerList",sellerService.findSellerByName(page));
        return "product/addProduct";
    }

    @RequestMapping(value = "saveProduct",method = RequestMethod.POST)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Token(remove = true)
    @Secured({"ROLE_SELLER", "ROLE_ADMIN","ROLE_DECORATION"})
    public String saveProduct(Principal principal, @Valid Product product, ProductTagmap productTagmap, ProductItem productItem, BindingResult errors, ValidatePoJo validatePoJo)
    {
        SaltedUser user=(SaltedUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if("3".equals(user.getGrade()) && !"认证成功".equals(user.getCertificationStatus()))
        {
            return "redirect:/qualifyUserInfo";
        }
        if(validatePoJo.setValidPoJo(product,errors).hasErrors() && validatePoJo.setValidPoJo(productTagmap,errors).hasErrors())
        {
            return "redirect:/addProduct";
        }

        if(product.getSellerId().equals("") || product.getSellerId() == null)
        {
            return "redirect:/qualifyBrand";
        }

        StringBuilder stringBuilder=new StringBuilder();
        UploadImage uploadImage=new UploadImage();
        for(int i=0;productItem.getProductIntroduceImg()!=null && i<productItem.getProductIntroduceImg().size();i++)
        {
            stringBuilder.append(uploadImage.uploadFile(productItem.getProductIntroduceImg().get(i),datasource,"img",Constant.OSS_PRODUCT_IMAGE));
            stringBuilder.append(",");
        }
        product.setIntroduceImg(stringBuilder.toString());
        productService.addProduct(product);

        long productId=productService.queryLastProductId().longValue();

        for(int i=0;productItem.getProductStyleList()!=null && i<productItem.getProductStyleList().size();i++)
        {
            if(productItem.getProductStyleImg()!=null)
            {
                productItem.getProductStyleList().get(i).setNormalPic(uploadImage.uploadFile(productItem.getProductStyleImg().get(i),datasource,productId,Constant.OSS_PRODUCT_IMAGE));
            }
            productItem.getProductStyleList().get(i).setProductId(BigInteger.valueOf(productId));
        }
        productService.addProductStyle(productItem.getProductStyleList());
        productTagmap.setProductId(BigInteger.valueOf(productId));
        productService.addProductTag(productTagmap);
        return "redirect:/qualifyProduct";
    }

    //删除商品
    @RequestMapping("/delProduct")
    @ResponseBody
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Secured({"ROLE_SELLER", "ROLE_ADMIN","ROLE_DECORATION"})
    public String delProduct(ProductItem productItem)
    {
        SaltedUser user=(SaltedUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if("3".equals(user.getGrade()) && !"认证成功".equals(user.getCertificationStatus()))
        {
            return "redirect:/qualifyUserInfo";
        }
        productService.deleteProduct(productItem,datasource);
        return "delete product success";
    }

    /**
     * 跳到修改商品页面
     * @param principal
     * @param model
     * @param page
     * @param productId
     * @param session
     * @return
     */
    @RequestMapping("/toUpdateProduct")
    @Token(save = true)
    @Secured({"ROLE_SELLER", "ROLE_ADMIN","ROLE_DECORATION"})
    public String toUpdataProduct(Principal principal, Model model, Page page, @RequestParam(value = "productId",defaultValue = "1") long productId, HttpSession session)
    {
        SaltedUser user=(SaltedUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if("3".equals(user.getGrade()) && !"认证成功".equals(user.getCertificationStatus()))
        {
            return "redirect:/qualifyUserInfo";
        }
        session.setAttribute("productId",productId);

        page.setQuerytool(principal.getName());
        page.setTotalNumber(sellerService.sellerCount(page));

        Map<String,Object> infoMap=new HashMap<String,Object>();
        infoMap.put("username",principal.getName());
        infoMap.put("productId",productId);


        model.addAttribute("typeList",typeService.findType());
        model.addAttribute("styleList",styleService.findStyle());
        model.addAttribute("tagList",tagsService.findTag());
        model.addAttribute("sellerList",sellerService.findSellerByName(page));
        model.addAttribute("product",productService.queryProductInfoByName(infoMap));
        model.addAttribute("productStyleList",productService.queryProductStyleByName(infoMap));

        session.setAttribute("productStyleList",productService.queryProductStyleByName(infoMap));
        return "product/updateProduct";
    }

    /**
     *
     * @param principal 用户登录信息类
     * @param product 用户修改后的商品类
     * @param productTagmap 商品标签
     * @param productItem 商品信息封装类包括商品和款式的图片上传
     * @param txtIntroduceImg 修改前介绍套图信息
     * @param result   验证结果类
     * @param validatePoJo 验证类
     * @param session 会话信息
     * @return 修改成功返回商品列表
     */
    @RequestMapping(value = "updateProduct",method = RequestMethod.POST)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Token(remove = true)
    @Secured({"ROLE_SELLER", "ROLE_ADMIN","ROLE_DECORATION"})
    public String updateProduct(Principal principal,@Valid Product product, ProductTagmap productTagmap,@Valid ProductItem productItem,String[] txtIntroduceImg,BindingResult result,ValidatePoJo validatePoJo, HttpSession session)
    {
        SaltedUser user=(SaltedUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if("3".equals(user.getGrade()) && !"认证成功".equals(user.getCertificationStatus()))
        {
            return "redirect:/qualifyUserInfo";
        }
        product.setProductId(BigInteger.valueOf((long)session.getAttribute("productId")));
        //验证商品信息是否有错，有错则返回商品修改页
        if (validatePoJo.setValidPoJo(product,result).hasErrors() || productItem.getProductStyleList() == null && productItem.getProductStyleList().size() == 0)
        {
            return "redirect:/toUpdateProduct?productId="+product.getProductId();
        }

        StringBuilder stringBuilder=new StringBuilder();

        UploadImage uploadImage=new UploadImage();

        String[] introduceImgName=product.getIntroduceImg().split(",");

        //合并修改后剩余的介绍套图字段用户修改
        try{
            for(String img:txtIntroduceImg)
            {
                stringBuilder.append(img);
                stringBuilder.append(",");
            }

            //删除多余的介绍套图
            for (int i=0;i<introduceImgName.length;i++)
            {
                int r=0;
                for(r=0;r<txtIntroduceImg.length;r++)
                {
                    if(introduceImgName[i].equals(txtIntroduceImg[r]))
                    {
                        break;
                    }
                }

                if(r == txtIntroduceImg.length)
                {
                    uploadImage.deleteFile(datasource,introduceImgName[i]);
                }
            }
        }
        catch(NullPointerException e)
        {

        }


        //上传新的介绍套图，并合并字段
        for(int i=0;productItem.getProductIntroduceImg() != null && i < productItem.getProductIntroduceImg().size();i++)
        {
            stringBuilder.append(uploadImage.uploadFile(productItem.getProductIntroduceImg().get(i),datasource,"img",Constant.OSS_PRODUCT_IMAGE));
            stringBuilder.append(",");
        }

        product.setIntroduceImg(stringBuilder.toString());

        Map<String,Object> infoMap=new HashMap<String,Object>();
        infoMap.put("product",product);
        infoMap.put("productTag",productTagmap);
        productService.updateProductInfo(infoMap);// 修改商品信息

        List<ProductStyle> oldProductStyleList= (List<ProductStyle>) session.getAttribute("productStyleList");
        int styleListSize=oldProductStyleList != null ? oldProductStyleList.size():0;

        List<BigInteger> deleteStyleIdList=new ArrayList<BigInteger>();//需要删除款式的Id列表
        List<ProductStyle> oldProductStyle=new ArrayList<ProductStyle>();//需要修改的旧款式
        List<ProductStyle> newProductStyle=new ArrayList<ProductStyle>();//需要添加的新款式
        int i;
        for(i=0;i<productItem.getProductStyleList().size();i++)//遍历款式列表
        {
            if((productItem.getProductStyleList().get(i) == null || productItem.getProductStyleList().get(i).getStyleName() == null) && i<styleListSize)//删除旧的款式
            {
                deleteStyleIdList.add(oldProductStyleList.get(i).getStyleId());
                uploadImage.deleteFile(datasource,oldProductStyleList.get(i).getNormalPic());
            }

            else if(productItem.getProductStyleList().get(i) != null && i<styleListSize)
            {
                if(productItem.getProductStyleImg() != null && productItem.getProductStyleImg().get(i) !=null && !productItem.getProductStyleImg().get(i).getOriginalFilename().equals(""))//添加新的款式略缩图，删除旧的款式略缩图
                {
                        uploadImage.deleteFile(datasource,oldProductStyleList.get(i).getNormalPic());
                        productItem.getProductStyleList().get(i).setNormalPic(uploadImage.uploadFile(productItem.getProductStyleImg().get(i),datasource,product.getProductId(),Constant.OSS_PRODUCT_IMAGE));
                }
                oldProductStyle.add(productItem.getProductStyleList().get(i));//需要修改的旧款式
            }

            else if(productItem.getProductStyleList().get(i) != null && i>=styleListSize)
            {
                if(productItem.getProductStyleImg() != null && productItem.getProductStyleImg().get(i) !=null && !productItem.getProductStyleImg().get(i).getOriginalFilename().equals(""))//添加新的款式略缩图
                {
                    productItem.getProductStyleList().get(i).setProductId(product.getProductId());
                    productItem.getProductStyleList().get(i).setNormalPic(uploadImage.uploadFile(productItem.getProductStyleImg().get(i),datasource,product.getProductId(),Constant.OSS_PRODUCT_IMAGE));
                }
                newProductStyle.add(productItem.getProductStyleList().get(i));
            }

        }

        if(deleteStyleIdList !=null && deleteStyleIdList.size() != 0)
        {
            productService.deleteProductStyle(deleteStyleIdList);
        }

        if(oldProductStyle != null && oldProductStyle.size() != 0)
        {
            productService.updateProductStyle(oldProductStyle);
        }

        if(newProductStyle != null && newProductStyle.size() !=0)
        {
            productService.addProductStyle(newProductStyle);
        }

        session.removeAttribute("productStyleList");
        return "redirect:/qualifyProduct";
    }
}
