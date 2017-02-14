/**
 * Created by server1 on 2017/1/13.
 */
;(function ($) {
    $(function () {
        $("#business_submit").on("click",function () {

            if (!ismobile($.trim($("input[name='contactMobile']").val()))) {
                $("span[name='contactMobile']").html("请输入正确的手机号码");
                return false;
            }
            else {
                $("span[name='contactMobile']").html("");
            }

            if (!isEmail($("input[name='contactEmail']").val())) {
                $("span[name='contactEmail']").html("邮箱格式错误");
                return false;
            }
            else {
                $("span[name='contactEmail']").html("");
            }

            if (!isPhone($("input[name='contactPhone']").val())) {
                $("span[name='contactPhone']").html("固话格式错误，正确格式：0757-65875423");
                return false;
            }
            else {
                $("span[name='contactPhone']").html("");
            }


          /*  if (!isWebsite($("input[name='website']").val())) {
                $("span[name='website']").html("网址格式错误");
                return false;
            }
            else {
                $("span[name='website']").html("");
            }*/

            if (!isLicenseNumber($("input[name='licenseNumber']").val())) {
                $("span[name='licenseNumber']").html("请填入18位信用代码");
                return false;
            }
            else {
                $("span[name='licenseNumber']").html("");
            }

            $(".help-block").html("");
            $(".help-block").css("color","");
            $(".help-block")[6].innerHTML="经营类型一经确定，无法修改";
            $(".help-block")[7].innerHTML="如三证合一，请填写统一社会信用代码";
            $(".help-block")[8].innerHTML="支持格式: gif, png, jpg. 文件最大 2Mb.";
            var isError=false;
            if(business_info.check_business_value($("input[name=contactName]").val()))
            {
                helpblock("联系人名不能为空！",0);
                isError=true;
            }
            if(business_info.check_business_value_length($("input[name=contactName]").val(),255))
            {
                helpblock("联系人名不能超过255位！",0);
                isError=true;
            }
            if(business_info.check_business_value($("input[name=contactMobile]").val()))
            {
                helpblock("联系手机不能为空！",1);
                isError=true;
            }
            if(business_info.check_business_value_length($("input[name=contactMobile]").val(),20))
            {
                helpblock("联系手机不能超过20位！",1);
                isError=true;
            }
            if(business_info.check_business_value($("input[name=contactEmail]").val()))
            {
                helpblock("联系邮箱不能为空！",2);
                isError=true;
            }
            if(business_info.check_business_value_length($("input[name=contactEmail]").val(),40))
            {
                helpblock("联系邮箱不能超过40位！",2);
                isError=true;
            }
            if(business_info.check_business_value($("input[name=contactPhone]").val()))
            {
                helpblock("联系固话不能为空！",3);
                isError=true;
            }
            if(business_info.check_business_value_length($("input[name=contactPhone]").val(),20))
            {
                helpblock("联系固话不能超过20位！",3);
                isError=true;
            }
            if(business_info.check_business_value($("input[name=companyName]").val()))
            {
                helpblock("公司名称不能为空！",4);
                isError=true;
            }
            if(business_info.check_business_value_length($("input[name=companyName]").val(),255))
            {
                helpblock("公司名称不能超过255位！",4);
                isError=true;
            }
            if(business_info.check_business_value($("input[name=companyAddress]").val()))
            {
                helpblock("地址不能为空！",5);
                isError=true;
            }
            if(business_info.check_business_value_length($("input[name=companyAddress]").val(),255))
            {
                helpblock("地址不能超过255位！",5);
                isError=true;
            }
            if(business_info.check_business_value($("select[name=companyType]").val()))
            {
                helpblock("经营类型不能为空！",6);
                isError=true;
            }
            if(business_info.check_business_value_length($("select[name=companyType]").val(),25))
            {
                helpblock("经营类型不能超过25位！",6);
                isError=true;
            }
            if(business_info.check_business_value($("input[name=licenseNumber]").val()))
            {
                helpblock("营业执照号不能为空！",7);
                isError=true;
            }
            if(business_info.check_business_value_length($("input[name=licenseNumber]").val(),255))
            {
                helpblock("营业执照号不能超过255位！",7);
                isError=true;
            }
            if(business_info.check_business_value($("input[name=uploadImg]")[0].value))
            {
                helpblock("营业执照副本不能为空！",9);
                isError=true;
            }
            if(!isError)
            {
                this.disabled=true;
                $("#business_f").submit();
            }
        });

        function ismobile(aMobile) {
            var bValidate = new RegExp(/^(0|86|17951)?(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$/).test(aMobile);
            if (bValidate) {
                return true;
            }
            else
                return false;
        }

        function isEmail(aEmail) {
            var bValidate = new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(aEmail);
            if (bValidate) {
                return true;
            }
            else
                return false;
        }

        function isPhone(aPhone) {
            var bValidate = new RegExp(/^0\d{2,3}-\d{7,8}(-\d{1,6})?$/).test(aPhone);
            if (bValidate) {
                return true;
            }
            else
                return false;
        }

        function isWebsite(aWebsite){
            //var reg = /^(((ht|f)tp(s?))\://)?(www.|[a-zA-Z].)[a-zA-Z0-9\-\.]+\.(com|edu|gov|mil|net|org|biz|info|name|museum|us|ca|uk)(\:[0-9]+)*(/($|[a-zA-Z0-9\.\,\;\?\\\\+&amp;%\$#\=~_\-]+))*$/;
            var bValidate = new RegExp("^(((ht|f)tp(s?))\://)?(www.|[a-zA-Z].)[a-zA-Z0-9\-\.]+\.(com|edu|gov|mil|net|org|biz|info|name|museum|us|ca|uk)(\:[0-9]+)*(/($|[a-zA-Z0-9\.\,\;\?\'\\\+&amp;%\$#\=~_\-]+))?$").test(aWebsite);
            if (bValidate) {
                return true;
            }
            else
                return false;
        }

        function isLicenseNumber(aLicenseNumber) {
            var bValidate = new RegExp(/[A-Z0-9]{18}/g).test(aLicenseNumber);
            if (bValidate) {
                return true;
            }
            else
                return false;
        }


    });
})(jQuery);

var business_info={
    check_business_value:function (value) {
        if(value == "" || value == null)
        {
            return true;
        }
        return false;
    },
    check_business_value_length:function (value,length) {
        if(value.length > length)
        {
            return true;
        }
        return false;
    }
}
