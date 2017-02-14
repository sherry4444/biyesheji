package com.luosee.token;

/**
 * Created by server1 on 2017/1/11.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token  {

     boolean save() default false;

     boolean remove() default false;
 }
