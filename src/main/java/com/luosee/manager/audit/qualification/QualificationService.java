package com.luosee.manager.audit.qualification;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by server2 on 2016/12/6.
 */
@Service
public class QualificationService {

    @Resource(name = "qualificationDao")
    private QualificationDao qualificationDao;

    public List<Qualification> selectAllunaudited(Map<String,Object> parameter){return qualificationDao.selectAllunaudited(parameter);}


    public List<Qualification> selectAllaudited(Map<String,Object> parameter){return qualificationDao.selectAllaudited(parameter);}

    public void veto(Qualification qualification){
        qualificationDao.veto(qualification);
    }

    public void pass(Qualification qualification){
        qualificationDao.pass(qualification);
    }

    public int countun(Map<String,Object> parameter){
        return qualificationDao.countun(parameter);
    }

    public int count(Map<String,Object> parameter){
        return qualificationDao.count(parameter);
    }

    public void cancel(Qualification qualification) {
         qualificationDao.cancel(qualification);
    }

    public void change(Qualification qualification) {
        qualificationDao.change(qualification);
    }
}

