package com.luosee.common;

import org.hibernate.validator.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.constraints.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;

/**
 * Created by server1 on 2016/12/15.
 */
public class ValidatePoJo {
    private Object targetObj;
    private BindingResult result;
    private Class cls;

    final Logger logger= LoggerFactory.getLogger(ValidatePoJo.class);
    public ValidatePoJo setValidPoJo(Object obj,BindingResult result)
    {
        this.targetObj=obj;
        this.result=result;
        validProperty();
        return this;
    }

    public BindingResult getResult()
    {
        return result;
    }

    public Boolean hasErrors()
    {
        return result.hasErrors();
    }

    public void validProperty()
    {
        cls=targetObj.getClass();
        Field[] fields=cls.getDeclaredFields();

        for (Field field:fields)
        {
            field.setAccessible(true);
            Annotation[] ans=field.getAnnotations();
            for (Annotation an:ans)
            {
                try {
                    validType(an,field);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public void validType(Annotation an,Field field) throws IllegalAccessException, ParseException {
        if(an instanceof Null)
        {
            if(field.get(targetObj) != null)
            {
                setErrorValue(field,((Null) an).message());
            }
        }
        else if (an instanceof NotNull)
        {
            if (field.get(targetObj) == null)
            {
                setErrorValue(field,((NotNull) an).message());
            }
        }
        else if (an instanceof AssertTrue)
        {
            if (!field.get(targetObj).equals(true))
            {
                setErrorValue(field,((AssertTrue) an).message());
            }
        }
        else if (an instanceof AssertFalse)
        {
            if (!field.get(targetObj).equals(false))
            {
                setErrorValue(field,((AssertTrue) an).message());
            }
        }
        else if(an instanceof Min)
        {
            if(numLength(( Integer.valueOf( String.valueOf(field.get(targetObj)) ) ).intValue()) < Integer.parseInt(String.valueOf(((Min) an).value())))
            {
                setErrorValue(field,((Min) an).message());
            }
        }
        else if(an instanceof Max)
        {
            if(numLength(( Integer.valueOf( String.valueOf(field.get(targetObj)) ) ).intValue()) > Integer.parseInt(String.valueOf(((Max) an).value())))
            {
                setErrorValue(field,((Max) an).message());
            }
        }
        else if (an instanceof DecimalMin)
        {
            if(numLength((BigDecimal.valueOf(Double.valueOf(String.valueOf(field.get(targetObj))))).intValue()) < Integer.parseInt(String.valueOf(((DecimalMin) an).value())))
            {
                setErrorValue(field,((DecimalMin) an).message());
            }
        }
        else if (an instanceof DecimalMax)
        {
            if(numLength((BigDecimal.valueOf(Double.valueOf(String.valueOf(field.get(targetObj))))).intValue()) > Integer.parseInt(String.valueOf(((DecimalMax) an).value())))
            {
                setErrorValue(field,((DecimalMax) an).message());
            }
        }
        else if (an instanceof Size)
        {
            int count=numLength(((Integer)field.get(targetObj)).intValue());
            if( count < Integer.parseInt(String.valueOf(((Size) an).min())) || count > Integer.parseInt(String.valueOf(((Size) an).max())))
            {
                setErrorValue(field,((Size) an).message());
            }
        }
        else if (an instanceof Digits)
        {
            Double num= (Double) field.get(targetObj);
            String[] nums=String.valueOf(num).split(".");
            if(num.isNaN() || numLength(Integer.parseInt(nums[0])) > ((Digits) an).integer() || numLength(Integer.parseInt(nums[0])) > ((Digits) an).fraction())
            {
                setErrorValue(field,((Digits) an).message());
            }
        }
        else if (an instanceof Past)
        {
            Date nowDate=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy.MM.dd");
            Date targetDate=dateFormat.parse(String.valueOf(field.get(targetObj)));
            if(targetDate.getTime() > nowDate.getTime())
            {
                setErrorValue(field,((Past) an).message());
            }
        }
        else if(an instanceof Future )
        {
            Date nowDate=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy.MM.dd");
            Date targetDate=dateFormat.parse(String.valueOf(field.get(targetObj)));
            if(targetDate.getTime() < nowDate.getTime())
            {
                setErrorValue(field,((Future) an).message());
            }
        }
        else if( an instanceof Pattern && field.get(targetObj) !=null)
        {
            java.util.regex.Pattern pattern= java.util.regex.Pattern.compile(((Pattern) an).regexp());
            Matcher matcher=pattern.matcher((CharSequence) field.get(targetObj));
            if(!matcher.matches())
            {
                setErrorValue(field,((Pattern) an).message());
            }
        }
        else if(an instanceof Email && field.get(targetObj) !=null)
        {
            java.util.regex.Pattern pattern= java.util.regex.Pattern.compile("^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$");
            Matcher matcher=pattern.matcher((CharSequence) field.get(targetObj));
            if(!matcher.matches())
            {
                setErrorValue(field,((Email) an).message());
            }
        }
        else if(an instanceof Length && field.get(targetObj) !=null)
        {
            String value= String.valueOf(field.get(targetObj));
            if(value.length() < ((Length) an).min() || value.length() > ((Length) an).max())
            {
                setErrorValue(field,((Length) an).message());
            }
        }
        else if(an instanceof NotEmpty)
        {
            String value= String.valueOf(field.get(targetObj));
            if(value == null || value.equals(""))
            {
                setErrorValue(field,((NotEmpty) an).message());
            }
        }
        else if (an instanceof Range)
        {
            if(Double.isNaN((Double) field.get(targetObj)) || !(Long.parseLong(String.valueOf(field.get(targetObj))) < ((Range) an).max() && Long.parseLong(String.valueOf(field.get(targetObj))) > ((Range) an).min()) )
            {
                setErrorValue(field,((Range) an).message());
            }
        }
        else if (an instanceof NotBlank)
        {
            String value= String.valueOf(field.get(targetObj));
            if(value == null || value.equals(""))
            {
                setErrorValue(field,((NotBlank) an).message());
            }
        }
    }

    public void setErrorValue(Field field,String message)
    {
        ObjectError objError=new ObjectError(field.getName(),message);
        result.addError(objError);
    }

    public int numLength(int i)
    {
        int count = 0;
        do
        {
            i/=10;
            count++;
        }while (i != 0);
        return count;
    }
}
