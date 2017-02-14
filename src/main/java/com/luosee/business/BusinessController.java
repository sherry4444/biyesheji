package com.luosee.business;

import com.luosee.common.HttpInfo;
import com.luosee.common.QualifyInfo;
import com.luosee.common.SaltedUser;
import com.luosee.common.ValidatePoJo;
import com.luosee.oss.Constant;
import com.luosee.oss.UploadImage;
import com.luosee.po.Page;
import com.luosee.product.style.StyleService;
import com.luosee.product.type.TypeService;
import com.luosee.token.Token;
import com.luosee.user.QueryUserRole;
import com.luosee.user.TbUser;
import com.luosee.user.UserService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by server1 on 2016/11/21.
 */
@Controller
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @Resource(name = "businessDao")
    private BusinessDao businessDao;

    final Logger logger= LoggerFactory.getLogger(BusinessController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private StyleService styleService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private BusinessModelService businessModelService;


    @Value("${dataSource.url}")
    private  String datasource;

    //保存商家的认证信息
    @RequestMapping(value = "saveBusinessAttestation",method = RequestMethod.POST)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Token(remove = true)
    public String saveBusinessInfo(@Valid BusinessCertification businessCertification, Principal principal, MultipartFile uploadImg, BindingResult result, ValidatePoJo validatePoJo,HttpServletRequest request)
    {
        SaltedUser tbuser=(SaltedUser)SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if("3".equals(tbuser.getGrade()))
        {
            return "redirect:/qualifyUserInfo";
        }

        if (validatePoJo.setValidPoJo(businessCertification,result).hasErrors() || uploadImg == null || uploadImg.getOriginalFilename()=="")
        {
            return "user/attestation";
        }

        int idCount;
        //判断是否已经认证过
        if (businessService.findOneBusinessSub(principal.getName())==null)
        {
            idCount=businessService.businessInfoCount().intValue()+1;
            businessCertification.setBusinessInfoId(BigInteger.valueOf(idCount));
            businessService.saveBusinessInfo(businessCertification);

            TbUser user=userService.qualifyUserInfo(principal.getName());

            BusinessSubUser businessSubUser=new BusinessSubUser();
            businessSubUser.setSubUsername(user.getUsername());
            businessSubUser.setSubPassword(user.getPassword());
            businessSubUser.setBusinessUserID(idCount);
            businessSubUser.setSalt(user.getSalt());
            businessService.saveBusinessSubUser(businessSubUser);

            if (!"ROLE_ADMIN".equals(QueryUserRole.findUserRole(request)))
            {
                user.setRole(2);
                user.setGrade("1");
                userService.updateUserRole(user);
                QueryUserRole.updateRole(request,user,businessDao);
            }

            List<TbUser> userList=new ArrayList<TbUser>();
            userList.add(user);
            userService.updateSubUserInfo(userList);
        }
        else
        {
            idCount=businessService.findIdByName(principal.getName()).intValue();
            businessCertification.setBusinessInfoId(BigInteger.valueOf(idCount));

            businessService.updateBusinessInfo(businessCertification);
        }

        UploadImage image=new UploadImage();
        businessCertification.setLicenseImg(image.uploadFile(uploadImg,datasource,idCount,Constant.OSS_BUSINESS_IMAGES+"/"));
        businessCertification.setCertificationStatus("认证中");
        businessService.saveBusinessCertification(businessCertification);
        return "redirect:/qualifyUserInfo";
    }

    //查询模型信息
    @RequestMapping(value = "qualifyModel",produces = "text/html;charset=UTF-8")
    @Secured({"ROLE_SELLER", "ROLE_ADMIN","ROLE_DECORATION"})
    public String qualifyModel(Model model, QualifyInfo qualifyInfo, Page page
            ,@RequestParam (name = "time",defaultValue = "DESC") String time
            ,@RequestParam (name = "status",defaultValue = "0") String status
            ,@RequestParam (name = "type",defaultValue = "all") String type
            ,@RequestParam (name = "style",defaultValue = "all") String style)
    {
        SaltedUser user=(SaltedUser)SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if("3".equals(user.getGrade()) && !"认证成功".equals(user.getCertificationStatus()))
        {
            return "redirect:/qualifyUserInfo";
        }
        qualifyInfo.setUsername(user.getUsername());

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
        Integer count=businessModelService.queryModelCount(page);
        count=count==null?0:count;
        page.setTotalNumber(count);
        model.addAttribute("modelList",businessModelService.findModelByName(page));
        model.addAttribute("page",page);
        model.addAttribute("typeList",typeService.findType());
        model.addAttribute("styleList",styleService.findStyle());
        model.addAttribute("imgurl",new HttpInfo());
        return "business/modelList";
    }

    //查询模型信息
    @RequestMapping(value = "qualifyModelToAjax",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @Secured({"ROLE_SELLER", "ROLE_ADMIN","ROLE_DECORATION"})
    public String qualifyModelToAjax(QualifyInfo qualifyInfo, Page page
            ,@RequestParam (name = "time",defaultValue = "DESC") String time
            ,@RequestParam (name = "status",defaultValue = "0") String status
            ,@RequestParam (name = "type",defaultValue = "all") String type
            ,@RequestParam (name = "style",defaultValue = "all") String style)
    {
        SaltedUser user=(SaltedUser)SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if("3".equals(user.getGrade()) && !"认证成功".equals(user.getCertificationStatus()))
        {
            return "redirect:/qualifyUserInfo";
        }
        qualifyInfo.setUsername(user.getUsername());

        Map<String,Object> queryField=new HashMap<String,Object>();
        queryField.put("time",time);
        queryField.put("status",status);
        queryField.put("type",type);
        queryField.put("style",style);
        qualifyInfo.setQueryField(queryField);

        page.setQuerytool(qualifyInfo);
        Integer count=businessModelService.queryModelCount(page);
        count=count==null?0:count;
        page.setTotalNumber(count);
        Map<String,Object> map=new HashMap<String,Object>();
        List<BusinessModel> list=businessModelService.findModelByName(page);
        for(int i=0;list!=null&&i<list.size();i++)
        {
            BusinessModel businessModel=list.get(i);
            map.put("model"+i,businessModel);
        }

        if(list!=null)
        {
            map.put("size",list.size());
        }

        JSONObject jsonParam = new JSONObject();
        return jsonParam.fromObject(map).toString();
    }

    //查询商家认证信息
    @RequestMapping("qualifyCertification")
    @Secured({"ROLE_SELLER", "ROLE_ADMIN","ROLE_DECORATION"})
    public String qualifyCertification(Model model, HttpServletRequest request)
    {
        SaltedUser user=(SaltedUser)SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if("3".equals(user.getGrade()) && !"认证成功".equals(user.getCertificationStatus()))
        {
            return "redirect:/qualifyUserInfo";
        }
        model.addAttribute("CertificationList",businessService.findAttestation(user.getUsername()));
        return "user/certificationStatus";
    }

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping("queryBusinessInfo")
    @Secured({"ROLE_SELLER", "ROLE_ADMIN","ROLE_DECORATION"})
    public String queryBusinessInfo(Model model)
    {
        SaltedUser user=(SaltedUser)SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();
        if("3".equals(user.getGrade()) && !"认证成功".equals(user.getCertificationStatus()))
        {
            return "redirect:/qualifyUserInfo";
        }
        model.addAttribute("CertificationList",businessService.findOneCertification(user.getUsername()));
        return "user/businessInfo";
    }

    //跳到增加模型页面
    @RequestMapping("addModel")
    @Token(save = true)
    @Secured({"ROLE_SELLER", "ROLE_ADMIN","ROLE_DECORATION"})
    public String addModel(Model model)
    {
        SaltedUser user=(SaltedUser)SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if("3".equals(user.getGrade()) && !"认证成功".equals(user.getCertificationStatus()))
        {
            return "redirect:/qualifyUserInfo";
        }
        model.addAttribute("typeList",typeService.findType());
        model.addAttribute("styleList",styleService.findStyle());
        return "business/addModel";
    }


    //保存模型信息
    @RequestMapping(value = "addModel",method = RequestMethod.POST)
    @Token(remove = true)
    @Secured({"ROLE_SELLER", "ROLE_ADMIN","ROLE_DECORATION"})
    public String saveModel(@Valid BusinessModel businessModel,ModelItem modelItem,BindingResult result,ValidatePoJo validatePoJo)
    {
        SaltedUser user=(SaltedUser)SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if("3".equals(user.getGrade()) && !"认证成功".equals(user.getCertificationStatus()))
        {
            return "redirect:/qualifyUserInfo";
        }

        if(validatePoJo.setValidPoJo(businessModel,result).hasErrors() || modelItem.getModelImgFile() == null || modelItem.getModelImgFile().size()==0)
        {
            return "redirect:/addModel";
        }

        int id=businessService.findIdByName(user.getUsername()).intValue();

        UploadImage uploadImage=new UploadImage();
        StringBuilder modelImgSrc=new StringBuilder();

        //循环上传图片，最大为6张
        for(int i=0;i<modelItem.getModelImgFile().size()&&i<6;i++)
        {
            if(modelItem.getPositionName() != null && modelItem.getPositionName().get(i) !=null && !modelItem.getPositionName().get(i).equals(""))
            {
                modelImgSrc.append(modelItem.getPositionName().get(i));
            }
            else
            {
                modelImgSrc.append("参考图");
            }
            modelImgSrc.append(",");
            modelImgSrc.append(uploadImage.uploadFile(modelItem.getModelImgFile().get(i),datasource,id,Constant.OSS_BUSINESS_IMAGES+"/"));
            modelImgSrc.append(";");
        }
        businessModel.setBusinessUserId(BigInteger.valueOf(id));
        businessModel.setModelImages(modelImgSrc.toString());
        businessModelService.saveModel(businessModel);
        return "redirect:/qualifyModel";
    }

    //删除模型
    @RequestMapping("del_model")
    @ResponseBody
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Secured({"ROLE_SELLER", "ROLE_ADMIN","ROLE_DECORATION"})
    public String delModel(ModelItem modelItem)
    {
        SaltedUser user=(SaltedUser)SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if("3".equals(user.getGrade()) && !"认证成功".equals(user.getCertificationStatus()))
        {
            return "redirect:/qualifyUserInfo";
        }
        if(modelItem.getModelIdList()!=null && modelItem.getModelIdList().size()!=0)
        {
            List<BusinessModel> models=businessModelService.findModelImg(modelItem.getModelIdList());
            UploadImage uploadImage=new UploadImage();
            for(BusinessModel model:models)
            {
                String[] imgs=model.getModelImages().split(";");
                for(int i=0;i < imgs.length;i++)
                {
                    if(imgs[i] != "")
                    {
                        uploadImage.deleteFile(datasource,imgs[i].split(",")[1]);
                    }
                }
            }

            businessModelService.deleteModel(modelItem.getModelIdList());
        }
        return "success delete model";
    }
}
