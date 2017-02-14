package com.luosee.manager.modeling;


import java.util.List;
import java.util.Map;

/**
 * Created by server2 on 2016/12/22.
 */
public interface ModelingDao {

    //Show
    List<Modeling> selectAllByStatus(Map<String, Object> parameter);

    //统计条数
    int count(Map<String, Object> parameter);

    //打回
    void veto(Modeling modeling);

    //改变status
    void changestatus(Modeling modeling);

    void downBatch(List<Integer> idList);

    void tomodal(Modeling modeling);

    void topruduct(Modeling modeling);

    void updatepruduct(Modeling modeling);

    void changeProductStatus(Modeling modeling);
}
