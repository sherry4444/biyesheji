/**
 * Created by server1 on 2016/11/15.
 */
var check_form;
;(function ($) {
    $(function(){
        $("#verificationCode").on("click",refreshCode);

        $(".role").on("click",function () {
            $(".role").attr("class","role");
            this.className="role choice";
            role=jQuery(this).data("role");
        });

        $("#choice").on("click",function () {
            window.location.href="/signup/"+role;
        });

        $("#pwdMobileCode").on("click",function () {
            var type=$("#pwdMobileCode").data("type");
            var value=$("#pwdMobileCode")[0].innerHTML;
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $("#pwdMobileCode")[0].disabled=true;
            var time_num=60;
            var element=$("#pwdMobileCode")[0];
            var sendTime=window.setInterval(function () {
                element.innerHTML=time_num+"s后重新获取";
                time_num--;
                if(time_num == 0)
                {
                    window.clearInterval(sendTime);
                    sendTime=null;
                    element.disabled=false;
                    element.innerHTML=value;
                }
            },1000);
            $.ajax({
                type:'GET',
                url:hostUrl+"get_verify",
                headers:{"token":token,"headerName":header},
                data:{
                    "mobile":$("#modifyUsername").val(),
                    "url":"users/get_verify_code_by_mobile",
                    "type":type
                },
                success: function(msg){
                    var result=eval("("+msg+")");
                    if(result.result.code==1010)
                    {
                        $(".help-block")[1].innerHTML="发送失败";
                        $(".help-block")[1].style.color="red";
                    }
                    else
                    {
                        $(".help-block")[1].innerHTML="";
                        $(".help-block")[1].style.color="#737373";
                    }
                }
            });
        });

        $("#MobileCode").on("click",function () {
            $.ajax({
                type: "get",
                url: hostUrl + "checkUserName",
                data: {"username": $("#inputUsername").val()},
                success: function (result) {
                    if (result == "exist") {
                        helpblock("账号已存在！", 0);
                        isexist = true;
                    }
                    else
                    {
                        check_form=function($){
                            var token = $("meta[name='_csrf']").attr("content");
                            var header = $("meta[name='_csrf_header']").attr("content");
                            var type=$("#MobileCode").data("type");
                            var value=$("#MobileCode")[0].innerHTML;
                            $("#MobileCode")[0].disabled=true;
                            var time_num=60;
                            var element=$("#MobileCode")[0];
                            var sendTime=window.setInterval(function () {
                                element.innerHTML=time_num+"s后重新获取";
                                time_num--;
                                if(time_num == 0)
                                {
                                    window.clearInterval(sendTime);
                                    sendTime=null;
                                    element.disabled=false;
                                    element.innerHTML=value;
                                }
                            },1000);
                            $.ajax({
                                type:'GET',
                                url:hostUrl+"get_verify",
                                headers:{"token":token,"headerName":header},
                                data:{
                                    "mobile":$("#inputUsername").val(),
                                    "url":"users/get_verify_code_by_mobile",
                                    "type":type
                                },
                                success: function(msg){
                                    var result=eval("("+msg+")");
                                    if(result.result.code==1010)
                                    {
                                        $(".help-block")[1].innerHTML="发送失败";
                                        $(".help-block").css("color","red");
                                    }
                                    else
                                    {
                                        $(".help-block")[1].innerHTML="";
                                        $(".help-block").css("color","#737373");
                                    }
                                }
                            });

                        };
                        exist_username($);

                    }
                }
            });
        });

        $("#inputUsername").on("blur",function () {
            var username=$("#inputUsername").val();
            exist_username($);
            if(checkmobilenumber(username))
            {
                $(".MobileCode-G").css("display","block");
            }
            else
            {
                $(".MobileCode-G").css("display","none");
            }
        });

        $("#modifyUsername").on("blur",function () {
            var username=this.value;
            if(checkmobilenumber(username))
            {
                $(".MobileCode-G").css("display","block");
            }
            else
            {
                $(".MobileCode-G").css("display","none");
            }
        });

        $(".toLogin button").on("click",function () {
            window.location.href=hostUrl+"users/login";
        });

        $("#retrieve-form").submit(function () {
            var username=$("#inputUsername").val();
            if(checkmobilenumber(username) && $("#inputMobileCode").val() == '')
            {
                $(".MobileCode-G").css("display","block");
                return false;
            }
            return true;
        });
    });

})(jQuery);

function refreshCode() {
    $("#verificationCode").attr("src",hostUrl+"users/verification?"+new Date());
}

function  checkmobilenumber(num) {
    if(/^[0-9]{11}$/.test(num))
    {
       return true;
    }
    else
    {
        return false;
    }
}

function checkEmail(username) {
    if(/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/.test(username))
    {
        return true;
    }
    else
    {
        return false;
    }
}

function checkPassword(password) {
    if(/^[a-z0-9_-]{6,44}$/.test(password))
    {
        return true;
    }
    return false;
}

function helpblock(msg,index) {
    $(".help-block")[index].innerHTML=msg;
    $(".help-block")[index].style.color="red";
}
