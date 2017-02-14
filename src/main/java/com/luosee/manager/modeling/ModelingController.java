package com.luosee.manager.modeling;

import com.luosee.common.HttpInfo;
import com.luosee.oss.Constant;
import com.luosee.po.Page;
import com.luosee.product.style.StyleService;
import com.luosee.product.tag.TagsService;
import com.luosee.product.type.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ModelingController {

    @Autowired
    private ModelingService modelingService;
    @Autowired
    private StyleService styleService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagsService tagsService;
    private int totalNumber;

    @Value("${dataSource.url}")
    private String datasource;

    private Integer ttt_length;

    private final Logger logger = LoggerFactory.getLogger(ModelingController.class);

    @RequestMapping(value = "modeling",produces = "text/html;charset=UTF-8")
    @Secured("ROLE_ADMIN")
    public String select(Model model,
                         @RequestParam(value = "currentPage",defaultValue = "1",required=false)int currentPage,
                         @RequestParam(value="flag",defaultValue = "1",required = false)Integer flag,
                         @RequestParam(value="flag1",defaultValue = "0",required = false)Integer flag1,
                         @RequestParam(value = "title",required = false)String title,
                         @RequestParam(value = "num",required = false)Integer num
    ) throws UnsupportedEncodingException {

        Map<String,Object> parameter = new HashMap<String, Object>();
        Modeling modeling = new Modeling();

//        title = URLDecoder.decode(title, "UTF-8");
        try {
            if(title !=null && title.equals(new String(title.getBytes("ISO-8859-1"), "ISO-8859-1")))
            {
                title = new String(title.getBytes("ISO-8859-1"), "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

       /* if(title != null) {
            title = URLDecoder.decode(title, "UTF-8");
            logger.debug(byteArrayToHex(title.getBytes()));
            logger.debug(byteArrayToHex(title.getBytes("ISO-8859-1")));
            title = new String(title.getBytes("ISO-8859-1"), "utf-8");
            logger.debug(byteArrayToHex(title.getBytes()));
            parameter.put("title",title);
        }*/
        parameter.put("title",title);
        Page page = new Page();
        if (num != null) {page.setPageNumber(num);} else {num=page.getPageNumber();}
        page.setCurrentPage(currentPage);
        parameter.put("page",page);


        //待制作
        if(flag == 1){
            modeling.setStatus(3);
            parameter.put("modeling",modeling);
            totalNumber = modelingService.count(parameter);
            page.setTotalNumber(totalNumber);
            page.setCurrentPage(currentPage);
            parameter.put("page",page);
            parameter.put("flag1",flag1);
            model.addAttribute("waitToBeDone",modelingService.selectAllByStatus(parameter));
        }
        if(flag == 2)
        {
            modeling.setStatus(4);
            parameter.put("modeling",modeling);
            totalNumber = modelingService.count(parameter);
            page.setTotalNumber(totalNumber);
            page.setCurrentPage(currentPage);
            parameter.put("page",page);
            parameter.put("flag1",flag1);
            model.addAttribute("inMaking",modelingService.selectAllByStatus(parameter));
        }
        if (flag == 3)
        {
            modeling.setStatus(7);
            parameter.put("modeling",modeling);
            totalNumber = modelingService.count(parameter);
            page.setTotalNumber(totalNumber);
            page.setCurrentPage(currentPage);
            parameter.put("page",page);
            parameter.put("flag1",flag1);
            model.addAttribute("toComplete",modelingService.selectAllByStatus(parameter));
        }

        System.out.println(page.toString());
        model.addAttribute("page",page);
        model.addAttribute("title",title);
        model.addAttribute("flag",flag);
        model.addAttribute("flag1",flag1);
        model.addAttribute("num",num);
        model.addAttribute("imgurl", new HttpInfo());
        model.addAttribute("url","modeling");

        model.addAttribute("typeList",typeService.findType());
        model.addAttribute("styleList",styleService.findStyle());
        model.addAttribute("tagsList",tagsService.findTag());
        return "/manager/modeling";
    }



    /* 打回*/
    @RequestMapping(value = "modeling/veto", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String veto(@RequestParam("modelId")String modelId,
                       @RequestParam("cerCausepre")String cerCausepre,
                       @RequestParam(value="cerCause",required=false)String cerCause) throws Exception{

        String certificationCause = cerCausepre+","+cerCause;
        Modeling modeling=new Modeling();
        modeling.setModelId(modelId);
        modeling.setStatus(6);
        modeling.setVetoReason(certificationCause);
        modelingService.veto(modeling);
        return "打回成功";
    }

    /* 开始制作*/
    @RequestMapping(value = "modeling/startmaking", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String veto(@RequestParam("modelId")String modelId) throws Exception{

        Modeling modeling=new Modeling();
        modeling.setModelId(modelId);
        modeling.setStatus(4);
        modelingService.changestatus(modeling);
        return "开始制作";
    }

    /* 取消开始制作*/
    @RequestMapping(value = "modeling/cancel", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String cancel(@RequestParam("modelId")String modelId) throws Exception{

        Modeling modeling=new Modeling();
        modeling.setModelId(modelId);
        modeling.setStatus(3);
        modelingService.changestatus(modeling);
        return "已取消开始制作";
    }

    /* 完成制作*/
    @RequestMapping(value = "modeling/finish", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String finish(
                       @RequestParam(value = "ImgFile",required = false)MultipartFile ImgFile,
                       @RequestParam(value = "uploadfile",required = false)MultipartFile uploadfile,
                       HttpServletRequest request, HttpServletResponse response,Modeling modeling) throws Exception{

        if ((uploadfile != null && uploadfile.isEmpty()) || (ImgFile != null && ImgFile.isEmpty())) {return "没有上传文件";}
        UploadFile uploadFile = new UploadFile();
        String imgpath = uploadFile.Uploadimg(ImgFile,Constant.OSS_PRODUCT_IMAGE,datasource);
        String filepath = uploadFile.Uploadfile(uploadfile,Constant.OSS_BUSINESS_modalFile,datasource);
        modeling.setIntroduceImg(imgpath);
        modeling.setFilepath(filepath);
        modeling.setStatus(7);
        modeling.setCertificationStatus("认证成功");
        modelingService.finish(modeling);
        return "上传成功";
    }

    /*打包下载*/
    @RequestMapping(value = "modeling/zipDown", method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public void zipdown(@RequestParam("modelIds")String modelIds,HttpServletResponse response) throws Exception{
        String[] strs = modelIds.split(";");
        response =  modelingService.zipDown(strs,response);
    }

    /*上架*/
    @RequestMapping(value = "modeling/ProductStatus", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String changeProductStatus(Modeling modeling) throws Exception{
        if (modeling.getProductStatus().equals("0")) {
            modeling.setProductStatus("试上架");
        } else if (modeling.getProductStatus().equals("1")) {
            modeling.setProductStatus("正式上架");
        }
        modelingService.changeProductStatus(modeling);
        return "操作成功";
    }


    /*编辑*/
    @RequestMapping(value = "modeling/edit", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public String edit(
            @RequestParam(value = "ImgFile",required = false)MultipartFile ImgFile,
            @RequestParam(value = "uploadfile",required = false)MultipartFile uploadfile,
            HttpServletRequest request, HttpServletResponse response,Modeling modeling) throws Exception{

        UploadFile uploadFile = new UploadFile();
        if (ImgFile != null && !ImgFile.isEmpty()) {
            try {
                String imgpath = uploadFile.Uploadimg(ImgFile, Constant.OSS_PRODUCT_IMAGE, datasource);
                modeling.setIntroduceImg(imgpath);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (uploadfile != null && !uploadfile.isEmpty()) {
            try{
            String filepath = uploadFile.Uploadfile(uploadfile,Constant.OSS_BUSINESS_modalFile,datasource);
            modeling.setFilepath(filepath);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        modelingService.edit(modeling);
        return "编辑成功";
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

}
