package com.luosee.manager.user.plan;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by server2 on 2016/11/22.
 */
public interface PlanListDao {

    List<PlanList> selectAll(Map<String, Object> parameter);

    public int count(@Param("title") String title);

    public Integer getUserIdByName(String copyusername);

    public void insertMyfloorplan(PlanList planList);

    public int getfloorIdByUrl(String floorPlanUrl);

    public void insertMyplan(PlanList planList);

    PlanList selectToCopy(Map<String, Object> parameter);
}
