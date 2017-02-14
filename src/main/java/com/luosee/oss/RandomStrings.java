package com.luosee.oss;

import java.util.Random;

/**
 * Created by server1 on 2016/11/25.
 */
public class RandomStrings extends Random {

    private final static String RANDOMCODE="abcdefghijklmnopqrstuvwsyz0123456789";
    private long speed;
    public RandomStrings(long speed)
    {
        this.speed=speed;
    }


    public String nextString() {
        StringBuilder random=new StringBuilder();
        char c;
        for(int i=0;i<speed;i++)
        {
            c=RANDOMCODE.charAt(super.nextInt(RANDOMCODE.length()));
            random.append(c);
        }
        return random.toString();
    }
}
