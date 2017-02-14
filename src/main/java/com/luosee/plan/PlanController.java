package com.luosee.plan;

import com.luosee.common.QualifyInfo;
import com.luosee.common.ValidatePoJo;
import com.luosee.po.Page;
import com.luosee.token.Token;
import com.luosee.user.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by server1 on 2016/11/22.
 */
@Controller
public class PlanController {

    @Autowired
    private PlanService planService;

    @Autowired
    private UserDao userDao;

    final Logger logger= LoggerFactory.getLogger(PlanController.class);


    @RequestMapping(value = "qualifyPlan")
    @Token(save = true)
    public String qualifyPlan(Principal principal, Model model, Page page,QualifyInfo qualifyInfo,PlanItem planItem)
    {
        qualifyInfo.setUsername(principal.getName());
        try {
            if(qualifyInfo.getQualifyContent() !=null && qualifyInfo.getQualifyContent().equals(new String(qualifyInfo.getQualifyContent().getBytes("ISO-8859-1"), "ISO-8859-1")))
            {
                qualifyInfo.setQualifyContent(new String(qualifyInfo.getQualifyContent().getBytes("ISO-8859-1"), "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        page.setQuerytool(qualifyInfo);



        Map<String,Object> planInfo=new HashMap<String,Object>();

        planInfo.put("page",page);

        if(planItem.getTags() !=null && planItem.getTags().size() != 0)
        {
            planInfo.put("tags",planItem.getTags());
            model.addAttribute("tags",planItem.getTags());
        }

        Integer count=planService.findUserPlanCount(planInfo);
        count=count==null?0:count;
        page.setTotalNumber(count);


        if(page.getPageNumber() == 10)
        {
            page.setPageNumber(12);
        }
        else if (page.getPageNumber() > 300)
        {
            page.setPageNumber(96);
        }

        planInfo.replace("page",page);

        model.addAttribute("planList",planService.findPlan(planInfo));
        model.addAttribute("planTagList",planService.fingUserPlanTag(principal.getName()));
        model.addAttribute("planTagGroup",planService.findUserPlanTagGroup(principal.getName()));
        model.addAttribute("page",page);
        return "plan/myplan";
    }

    @RequestMapping(value = "choiceCad")
    public String choiceCad()
    {
        return "plan/choiceCad";
    }

    @RequestMapping(value = "saveCad")
    public String saveCad()
    {
        return "plan/saveCad";
    }

    @RequestMapping(value = "copyUserPlan")
    @ResponseBody
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String copyUserPlan(Principal principal,PlanItem planIds)
    {
        planService.copyPlan(planIds,principal.getName());
        return "copy success";
    }

    @RequestMapping(value = "deletePlan")
    @ResponseBody
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String deletePlan(Principal principal,PlanItem planIds)
    {
        Map<String,Object> planInfo=new HashMap<String,Object>();
        planInfo.put("planId",planIds.getPlanIds());
        planInfo.put("username",principal.getName());
        planService.deletePlan(planInfo);
        return "delete success";
    }

    @RequestMapping("managementTag")
    @Token(save = true)
    public String managementTag(Principal principal,Model model)
    {
        model.addAttribute("planTagList",planService.fingUserPlanTag(principal.getName()));
        model.addAttribute("planTagGroup",planService.findUserPlanTagGroup(principal.getName()));
        return "plan/managementTag";
    }

    @RequestMapping(value = "addGroup",method = RequestMethod.POST)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Token(remove = true)
    @ResponseBody
    public String addGroup(Principal principal, TagGroup group, ValidatePoJo poJo, BindingResult result)
    {
        if(poJo.setValidPoJo(group,result).hasErrors())
        {
            return "redirect:/managementTag";
        }

        Map<String,Object> planInfo=new HashMap<String,Object>();
        planInfo.put("groupName",group.getGroupName());
        planInfo.put("username",principal.getName());

        if(planService.queryUserGroupCount(principal.getName()) < 6 && !planService.queryHasGroupName(planInfo))
        {
            Integer id=userDao.getUserIdByName(principal.getName());
            group.setUserId(id);
            planService.addGroup(group);
            return String.valueOf(group.getGroupId());
        }
        return "failure";
    }

    @RequestMapping(value = "addTag",method = RequestMethod.POST)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Token(remove = true)
    @ResponseBody
    public String addTag(Principal principal,PlanTag tag, ValidatePoJo poJo, BindingResult result)
    {
        if(poJo.setValidPoJo(tag,result).hasErrors())
        {
            return "redirect:/managementTag";
        }
        Map<String,Object> planInfo=new HashMap<String,Object>();
        planInfo.put("tagName",tag.getTagName());
        planInfo.put("username",principal.getName());

        if(!planService.queryHasTagName(planInfo))
        {
            planService.addTag(tag);
            return String.valueOf(tag.getTagId());
        }
        return "failure";
    }

    @RequestMapping(value = "updateGroupName",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String updateGroupName(Principal principal,TagGroup group)
    {

        Map<String,Object> planInfo=new HashMap<String,Object>();
        planInfo.put("groupName",group.getGroupName());
        planInfo.put("username",principal.getName());
        planInfo.put("groupId",group.getGroupId());

        if(!planService.queryHasGroupName(planInfo))
        {
            planService.updateGroupName(group);
            return "update success";
        }
        return "failure";
    }

    @RequestMapping(value = "updateTagName",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String updateTagName(Principal principal,PlanTag tag)
    {
        Map<String,Object> planInfo=new HashMap<String,Object>();
        planInfo.put("tagName",tag.getTagName());
        planInfo.put("username",principal.getName());
        planInfo.put("tagId",tag.getTagId());

        if(!planService.queryHasTagName(planInfo))
        {
            planService.updateTagName(tag);
            return "update success";
        }
        return "failure";
    }

    @RequestMapping(value = "deleteGroup",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String deleteGroup(TagGroup group)
    {
        planService.deleteGroup(group);
        return "delete success";
    }

    @RequestMapping(value = "deleteTag",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String deleteTag(PlanTag tag)
    {
        planService.deleteTag(tag);
        return "delete success";
    }

    @RequestMapping("queryOnePlan")
    @Token(save = true)
    public String queryOnePlan(Model model,int planId,Principal principal)
    {
        model.addAttribute("plan",planService.findOnePlanById(planId));
        model.addAttribute("planTagList",planService.fingUserPlanTag(principal.getName()));
        model.addAttribute("planTagGroup",planService.findUserPlanTagGroup(principal.getName()));
        model.addAttribute("relationList",planService.queryTagRelation(planId));
        model.addAttribute("groupList",planService.findOnePlanSubGroup(planId));
        model.addAttribute("tagList",planService.findOnePlanSubTag(planId));
        return "plan/planInfo";
    }

    @RequestMapping(value = "updatePlan",method = RequestMethod.POST)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Token(remove = true)
    public String updatePlan(Principal principal,Plan plan,PlanItem planItem)
    {
        Map<String,Object> planInfo = new HashMap<String,Object>();
        planInfo.put("username",principal.getName());
        planInfo.put("plan",plan);
        if(planItem.getRelation() != null)
        {
            for(int i=0;i<planItem.getRelation().size();i++)
            {
                if(planItem.getRelation().get(i) ==null || planItem.getRelation().get(i).getTagId() == null)
                {
                    planItem.getRelation().remove(i);
                    i--;
                }
            }
            planInfo.put("relations",planItem.getRelation());
        }
        planService.updatePlan(planInfo);

        for(int i=0;planItem.getNewRelation() != null && i<planItem.getNewRelation().size();i++)
        {
            if(planItem.getNewRelation().get(i)==null || planItem.getNewRelation().get(i).getTagId() == null)
            {
                planItem.getNewRelation().remove(i);
                i--;
            }
            else
            {
                planItem.getNewRelation().get(i).setPlanId(plan.getPlanId().intValue());
            }
        }
        if(planItem.getNewRelation() != null && planItem.getNewRelation().size()!=0)
        {
            planService.addRalation(planItem.getNewRelation());
        }
        return "redirect:queryOnePlan?planId="+plan.getPlanId();
    }
}
