package com.luosee.oss;

/**
 * Created by server1 on 2016/11/25.
 */
public class Dataformal {
    private final static String TEST_PROJECT="test_project_daedalus";


    public static boolean IsFormal(String datasource) {
        if (datasource.contains(TEST_PROJECT))
        {
            return false;
        }
        return true;
    }
}
