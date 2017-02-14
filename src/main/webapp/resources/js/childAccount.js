/**
 * Created by server1 on 2016/12/16.
 */
;(function ($) {
    $(function () {
        $("#addAccount").on("click",function () {
            if($("tbody tr").length < 5)
            {
                var msg=$(["                                  <div class=\"panel-body\">",
                    "                                      <div class=\"form-group\">",
                    "											<label class=\"col-lg-3 control-label\">账号:</label>",
                    "											<div class=\"col-lg-9\">",
                    "												<input type=\"text\" class=\"form-control\" placeholder=\"请输入手机或邮箱\" id='inputUsername' >",
                    "                                               <span id='u-childValid' class='childValid help-block' style='color:red;'></span>",
                    "											</div>",
                    "										</div>",
                    "                        <div class=\"form-group has-feedback has-feedback-left MobileCode-G\" style=\"display:none\">",
                    "                            <div class=\"div-input-sign\">",
                    "                                <input type=\"text\" class=\"form-control\"  style=\"width:53.6%!important;float: left\" id=\"inputMobileCode\" placeholder=\"验证码\" name=\"verificationCode\" />",
                    "                                <div class=\"col-lgi\" >",
                    "                                    <button type=\"button\" class=\"btn btn-default\" id=\"MobileCode\" data-type=\"0\">点击获取验证码</button>",
                    "                                </div>",
                    "                            </div>",
                    "                            <span class=\"help-block\" th:text=\"${verifyCodeErrors != null?verifyCodeErrors:\'\'}\" th:style=\"\'color:\'+${verifyCodeErrors != null?\'red\':\'#737373\'}\"></span>",
                    "                        </div>",
                    "                                      <div class=\"form-group\">",
                    "											<label class=\"col-lg-3 control-label\">备注:</label>",
                    "											<div class=\"col-lg-9\">",
                    "												<input type=\"text\" class=\"form-control\" placeholder=\"请输入子账号备注\" id='remarks'>",
                    "											</div>",
                    "										</div>",
                    "										<div class=\"form-group\">",
                    "											<label class=\"col-lg-3 control-label\">密码:</label>",
                    "											<div class=\"col-lg-9\">",
                    "												<input type=\"password\" class=\"form-control\" placeholder=\"请输入密码\" id='inputPassword'>",
                    "                                               <span id='p-childValid' class='childValid' style='color:red;'></span>",
                    "											</div>",
                    "										</div>",
                    "										<div class=\"form-group\">",
                    "											<label class=\"col-lg-3 control-label\">确认密码:</label>",
                    "											<div class=\"col-lg-9\">",
                    "												<input id='inputPasswordConfirm' type=\"password\" class=\"form-control\" placeholder=\"请输入密码\">",
                    "											</div>",
                    "										</div>",
                    "                              </div>"].join(""));
                $.alert("添加账号",msg);
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
                msg.find(".form-group").css("margin-bottom","20px");
                msg.find(".form-group").css("height","36px");
                $(".btn-primary").on("click",function () {
                    var username=$("#inputUsername").val();
                    var password=$("#inputPassword").val();
                    var btn=this;
                    $(".childValid").html("");
                    check_form=function ($) {
                        if ((checkmobilenumber(username) || checkEmail(username)) && password == $("#inputPasswordConfirm").val() && checkPassword(password) && !isexist) {
                            var form_token = $("meta[name='token']").attr("content");
                            btn.disabled = true;
                            $.ajax({
                                type: "get",
                                url: hostUrl + "addChildUser",
                                data: {
                                    "username": username,
                                    "password": password,
                                    "remarks": $("#remarks").val(),
                                    "token": form_token
                                },
                                success: function (result) {
                                    if (result == "Add sub account success") {
                                        window.location.href = "/queryChildUser";
                                    }
                                    else if (result == "Add sub account not success") {
                                        $("#u-childValid").html("子账号添加不成功");
                                        window.location.href = "/queryChildUser";
                                    }
                                    else if (result == "Sub account password error") {
                                        $("#p-childValid").html("密码错误");
                                        window.location.href = "/queryChildUser";
                                    }
                                    else if (result == "Child account already add") {
                                        $("#u-childValid").html("该账号已经存在");
                                        window.location.href = "/queryChildUser";
                                    }
                                    else if (result.indexOf("FormSubmitException") != -1) {
                                        $(".modal-body").html("<span style='height:242px;display: block' class=\"text-semibold\">不能重复提交申请页面！</span>");
                                        window.location.href = "/queryChildUser";
                                    }
                                    else if (result == "more than 5 Child account") {
                                        $("#p-childValid").html("账号最多为5个!");
                                        window.location.href = "/queryChildUser";
                                    }
                                }
                            });
                        }
                        else if (username == "") {
                            $("#u-childValid").html("账号不能为空");
                        }
                        else if (!(checkmobilenumber(username) || checkEmail(username))) {
                            $("#u-childValid").html("账号格式不正确");
                        }
                        else if (password != $("#inputPasswordConfirm").val()) {
                            $("#p-childValid").html("密码不一致");
                        }
                        else if (!checkPassword(password)) {
                            $("#p-childValid").html("密码格式不正确");
                        }
                    }

                    exist_username($);

                });
            }
            else
            {
                $.alert("提示",$(["<span>子账号最多为5个</span>"].join("")));
                $(".btn-primary").on("click", function () {
                    $(".close").trigger("click");
                });
            }

        });

        $(".delChild").on("click",function () {
            var token = $("meta[name='_csrf']").attr("content");
            var child=$(".childId");
            var childIds={};
            var childPlate=new Array();
            var choice=false;
            for(var i=0,r=0;i<child.length;i++)
            {
            	if(child[i].checked)
            	{
                    choice=true;
            		childIds["childId["+r+"]"]=$(child[i]).data("childName");
                    childPlate[r]=child[i].parentElement.parentElement.parentElement.parentElement.parentElement.parentElement;
            		r++;
            	}
            }
            if(choice) {

                childIds["_csrf"] = token;

                $.ajax({
                    type: "post",
                    url: hostUrl + "delChildAccount",
                    data: childIds,
                    success: function (result) {
                        if (result == "delete success") {
                            for (var i = 0; i < childPlate.length; i++) {
                                $(childPlate[i]).remove();
                            }
                        }
                    }
                });
            }
            else
            {
                $.alert("重设密码", $("<span>请选择子账号</span>"));
                $(".btn-primary").on("click", function () {
                    $(".close").trigger("click");
                });
            }
        });

        $(".modifyAccount").on("click",function () {
            var childs=$(".childId");
            var choice=false;
            for(var i=0,r=0;i<childs.length;i++)
            {
                if(childs[i].checked)
                {
                    choice=true;
                }
            }

            if(choice) {
                var token = $("meta[name='_csrf']").attr("content");
                var form_token = $("meta[name='token']").attr("content");
                var child = $(".childId");
                var childInfo = {};
                var msg = [];
                for (var i = 0, r = 0; i < child.length; i++) {
                    if (child[i].checked) {
                        var id = $(child[i]).data("childName");
                        var childUserId = $(child[i]).data("childUser");
                        var childUserName = $(child[i]).data("childUsername");
                        childInfo["account[" + r + "].userId"] = childUserId;
                        childInfo["account[" + r + "].username"] = childUserName;
                        msg.push("<fieldset>");
                        msg.push("<div class=\"panel-body\">");
                        msg.push("<div class=\"form-group\">");
                        msg.push("<label class=\"col-lg-3 control-label\">账号:</label>");
                        msg.push("<div class=\"col-lg-9\">");
                        msg.push("<span class='help-block'>" + childUserName + "</span>");
                        msg.push("</div>");
                        msg.push("</div>");
                        msg.push("<div class=\"form-group\">");
                        msg.push("<label class=\"col-lg-3 control-label\">密码:</label>");
                        msg.push("<div class=\"col-lg-9\">");
                        msg.push("<input type=\"password\" class=\"form-control inputPassword\" placeholder=\"请输入密码\">");
                        msg.push("<span id='p-childValid' class='childValid' style='color:red;'></span>");
                        msg.push("</div>");
                        msg.push("</div>");
                        msg.push("<div class=\"form-group\">");
                        msg.push("<label class=\"col-lg-3 control-label\">确认密码:</label>");
                        msg.push("<div class=\"col-lg-9\">");
                        msg.push("<input type=\"password\" class=\"form-control inputPasswordConfirm\" placeholder=\"请输入密码\">");
                        msg.push("</div>");
                        msg.push("</div>");
                        msg.push("</div>");
                        msg.push("</fieldset>");
                        r++;
                    }
                }
                var punctuation = new RegExp("[,]", "g");
                var plate = msg.join().replace(punctuation, "");
                $.alert("重设密码", $(plate));

                $(".btn-primary").on("click", function () {
                    $(".childValid").html("");
                    var password = $(".inputPassword");
                    var passwordConfirm = $(".inputPasswordConfirm");
                    var error = 0;
                    for (var i = 0; i < password.length; i++) {

                        childInfo["account[" + i + "].password"] = password[i].value;
                        if (password[i].value != passwordConfirm[i].value) {
                            $(".childValid")[i].innerHTML = "密码不一致";
                            error++;
                        }
                        else if (!checkPassword(password[i].value)) {
                            $(".childValid")[i].innerHTML = "密码格式不正确";
                            error++;
                        }
                    }

                    if (error == 0) {
                        childInfo["token"] = form_token;
                        var token = $("meta[name='_csrf']").attr("content");
                        this.disabled = true;
                        $.ajax({
                            type: "post",
                            url: hostUrl + "updateChildPwd?_csrf=" + token,
                            data: childInfo,
                            success: function (result) {
                                if (result == "update success") {
                                    $(".modal-body").html("<span class=\"text-semibold\">密码修改成功！</span>");
                                    $(".close").trigger("click");
                                }
                                else if (result.indexOf("FormSubmitException") != -1) {
                                    $(".modal-body").html("<span style='height:242px;display: block' class=\"text-semibold\">不能重复提交申请页面！</span>");
                                }
                            }
                        });
                    }
                });
            }
            else
            {
                $.alert("重设密码", $("<span>请选择子账号</span>"));
                $(".btn-primary").on("click", function () {
                   $(".close").trigger("click");
                });
            }
        });

        $(".childId").on("click",function () {
            var childs=$(".childId");
            var choice=false;
            for(var i=0,r=0;i<childs.length;i++)
            {
                if(childs[i].checked)
                {
                    choice=true;
                }
            }

            if(choice)
            {
                $(".modifyAccount").css("display","block");
            }
            else
            {
                $(".modifyAccount").css("display","none");
            }
        })
    });
})(jQuery);