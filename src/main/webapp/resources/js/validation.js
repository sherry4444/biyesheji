/**
 * Created by server1 on 2017/1/19.
 */
var form_info_check={
    check_form_field:function (value) {
        if(value == "" || value == null || typeof(value) == 'undefined' )
        {
            return true;
        }
        return false;
    },
    check_form_field_length:function (value,length) {
        if(value.length > length)
        {
            return true;
        }
        return false;
    }
}

function testField(values) {
    var isError=false;
    var field;
    for(var i=0;i<values.length;i++)
    {
        field=values[i];
        if(field.msg != "" && form_info_check.check_form_field(field.value))
        {
            isError=true;
            helpblock(field.msg,field.index);
        }
        if(form_info_check.check_form_field_length(field.value,field.long))
        {
            isError=true;
            helpblock(field.msgLong,field.index);
        }
    }
    return isError;
}

function helpblock(msg,index) {
    $(".help-block")[index].innerHTML=msg;
    $(".help-block")[index].style.color="red";
}