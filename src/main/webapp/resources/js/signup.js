/**
 * Created by server1 on 2016/11/17.
 */
var isexist=false;
var iserror=false;
;(function ($) {
    $(function () {
        $("#register_btn").on("click",function () {
            $(".help-block").html("");
            var username=$("#inputUsername").val();
            var number=checkmobilenumber(username)|checkEmail(username);
            if(checkmobilenumber(username) && $("#inputMobileCode").val()=="")
            {
                iserror=true;
                helpblock("请输入验证码",1);
            }
            if(!number)
            {
                iserror=true;
                helpblock("请输入手机或者邮箱",0);
            }
            else
            {
                check_form=function ($) {
                    alert(1);
                    var password=$("#inputPassword").val()==$("#inputPasswordConfirm").val();
                    if(!password)
                    {
                        iserror=true;
                        helpblock("密码不一致",3);
                    }
                    if(!checkPassword($("#inputPassword").val()))
                    {
                        iserror=true;
                        helpblock("密码格式错误",3);
                    }
                    if($("#inputPassword").val().length < 6)
                    {
                        helpblock("密码长度不能少于6位",3);
                        iserror=true;
                    }
                    if($("#inputPassword").val().length > 44)
                    {
                        helpblock("密码长度不能超过44位",3);
                        iserror=true;
                    }
                    if($("#inputPassword").val() == "")
                    {
                        helpblock("密码不能为空！",3);
                        iserror=true;
                    }
                    if(!$("#consentclause")[0].checked)
                    {
                        iserror=true;
                        helpblock("请同意协议",4);
                    }
                    if(!iserror)
                    {
                        $("#register_btn")[0].disabled=true;
                        $("#registerForm").submit();
                    }
                };

                exist_username($);
            }
        });

        $("#sub_modify_pwd").on("click",function () {
            $("#modifyPassWordForm").submit();
        });

        $("#modifyPassWordForm").submit(function() {
            var pwd=$("#inputPassword").val();
            var password= pwd==$("#inputPasswordConfirm").val();
            var passwordFormat=checkPassword($("#inputPassword").val());
            var isError=false;
            if(!passwordFormat)
            {
                helpblock("密码格式错误",1);
                isError=true;
            }
            if(!password)
            {
                helpblock("密码不一致",1);
                isError=true;
            }
            if(pwd.length > 44)
            {
                helpblock("密码长度不能超过44位",1);
                isError=true;
            }
            if(!isError)
            {
                $("#sub_modify_pwd")[0].disabled=true;
                return true;
            }
            return false;
        });
    });
})(jQuery);

function exist_username($)
{
    $(".help-block").html("");
    var username=$("#inputUsername").val();
    var number=checkmobilenumber(username)|checkEmail(username);
    if(!number)
    {
        helpblock("请输入手机或者邮箱",0);
    }
    else
    {
        $.ajax({
            type:"get",
            url:hostUrl+"checkUserName",
            data:{"username":username},
            success:function(result){
                if(result == "exist")
                {
                    helpblock("账号已存在！",0);
                    isexist=true;
                    iserror=true;
                }
                else
                {
                    isexist=false;
                    iserror=false;
                }
                check_form($);
            }
        });
    }
}
