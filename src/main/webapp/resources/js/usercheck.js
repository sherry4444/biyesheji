/**
 * Created by server1 on 2017/1/19.
 */
;(function ($) {
    $(function () {
        $("#user_sub").on("click",function () {
            var values=[];
            var isError=false;
            values[values.length]={value:$("input[name=nickname]").val(),long:40,msg:"",msgLong:"呢称不能超过40位!",index:2};
            values[values.length]={value:$("input[name=mobile]").val(),long:20,msg:"",msgLong:"手机不能超过20位!",index:3};
            values[values.length]={value:$("input[name=email]").val(),long:40,msg:"",msgLong:"邮箱不能超过40位!",index:4};
            if($("select[name=province]").length !=0)
            {
                values[values.length]={value:$("select[name=province]").val(),long:255,msg:"",msgLong:"省不能超过255位!",index:5};
            }
            if($("select[name=city]").length !=0)
            {
                values[values.length]={value:$("select[name=city]").val(),long:255,msg:"",msgLong:"市不能超过255位!",index:5};
            }

            if (!isPhone($.trim($("input[name='mobile']").val()))) {
                $("span[name='mobile']").html("请输入正确的手机号码");
                return false;
            }
            else {
                $("span[name='mobile']").html("");
            }

            if (!isEmail($("input[name='email']").val())) {
                $("span[name='email']").html("邮箱格式错误");
                return false;
            }
            else {
                $("span[name='email']").html("");
            }

            isError=testField(values);
            if(!isError)
            {
                this.disabled=true;
                $("form").submit();
            }
        });

        function isEmail(aEmail) {
            var bValidate = new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(aEmail);
            if (bValidate) {
                return true;
            }
            else
                return false;
        }

        function isPhone(aPhone) {
            var bValidate = new RegExp(/^(0|86|17951)?(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$/).test(aPhone);
            if (bValidate) {
                return true;
            }
            else
                return false;
        }
    });
})(jQuery);
