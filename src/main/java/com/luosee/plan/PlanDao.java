package com.luosee.plan;

import java.util.List;
import java.util.Map;

/**
 * Created by server1 on 2016/11/22.
 */
public interface PlanDao {
    List<Plan> findPlan(Map<String,Object> planInfo);

    Integer findUserPlanCount(Map<String,Object> planInfo);

    List<TagGroup> findUserPlanTagGroup(String username);

    List<PlanTag> fingUserPlanTag(String username);

    void addGroup(TagGroup tagGroup);

    void addTag(PlanTag planTag);

    void updateGroupName(TagGroup group);

    void updateTagName(PlanTag planTag);

    void deleteGroup(TagGroup group);

    void deleteTag (PlanTag planTag);

    void deletePlan(Map<String,Object> planInfo);

    Plan findOnePlanById(int planId);

    List<Integer> queryTagRelation(int planId);

    void updatePlan(Map<String,Object> planInfo);

    List<Integer> findOnePlanSubGroup(int planId);

    List<Integer> findOnePlanSubTag(int planId);

    void addRalation(List<PlanTagRelation> relations);

    Integer queryUserGroupCount(String username);

    Integer queryHasGroupName(Map<String,Object> planInfo);

    Integer queryHasTagName(Map<String,Object> planInfo);

    List<String> queryFloorPlanUrl(List<Integer> childInfo);
    List<Plan> queryPlanResources(List<Integer> childInfo);

    void deleteChildTag(List<Integer> childInfo);

    void deleteChildPlan(List<Integer> childInfo);
}
