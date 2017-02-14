package com.luosee.plan;

import com.luosee.manager.user.plan.PlanListService;
import com.luosee.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Created by server1 on 2016/11/22.
 */
@Service
public class PlanService {

    @Resource(name = "planDao")
    private PlanDao planDao;

    @Resource(name = "userDao")
    private UserDao userDao;

    @Autowired
    private PlanListService planListService;

    public List<Plan> findPlan(Map<String,Object> planInfo)
    {
        return planDao.findPlan(planInfo);
    }

    public void copyPlan(PlanItem planIds,String username)
    {
        Integer userId=userDao.getUserIdByName(username);
        for (BigInteger id :planIds.getPlanIds())
        {
            planListService.copyaccount(username,userId.toString(),id.toString());
        }
    }

    public Integer findUserPlanCount(Map<String,Object> planInfo)
    {
        return  planDao.findUserPlanCount(planInfo);
    }

    public List<TagGroup> findUserPlanTagGroup(String username)
    {
        return planDao.findUserPlanTagGroup(username);
    }

    public List<PlanTag> fingUserPlanTag(String username)
    {
        return planDao.fingUserPlanTag(username);
    }

    public void addGroup(TagGroup tagGroup)
    {
        planDao.addGroup(tagGroup);
    }

    public void addTag(PlanTag planTag)
    {
        planDao.addTag(planTag);
    }

    public void updateGroupName(TagGroup group)
    {
        planDao.updateGroupName(group);
    }

    public void updateTagName(PlanTag planTag)
    {
        planDao.updateTagName(planTag);
    }


    public void deleteGroup(TagGroup group)
    {
        planDao.deleteGroup(group);
    }

    public void deleteTag (PlanTag planTag)
    {
        planDao.deleteTag(planTag);
    }

    public void deletePlan(Map<String,Object> planInfo)
    {
        planDao.deletePlan(planInfo);
    }

    public Plan findOnePlanById(int planId)
    {
        return planDao.findOnePlanById(planId);
    }

    public List<Integer> queryTagRelation(int planId)
    {
        return planDao.queryTagRelation(planId);
    }

    public void updatePlan(Map<String,Object> planInfo)
    {
        planDao.updatePlan(planInfo);
    }

    public List<Integer> findOnePlanSubGroup(int planId)
    {
        return  planDao.findOnePlanSubGroup(planId);
    }

    public List<Integer> findOnePlanSubTag(int planId)
    {
        return  planDao.findOnePlanSubTag(planId);
    }

    public void addRalation(List<PlanTagRelation> relations)
    {
        planDao.addRalation(relations);
    }

    public Integer queryUserGroupCount(String username)
    {
        return planDao.queryUserGroupCount(username);
    }

    public boolean queryHasGroupName(Map<String,Object> planInfo)
    {
        if(planDao.queryHasGroupName(planInfo) != null)
        {
            return true;
        }
        return false;
    }

    public boolean queryHasTagName(Map<String,Object> planInfo)
    {
        if(planDao.queryHasTagName(planInfo) != null)
        {
            return true;
        }
        return false;
    }
}
