/**
 * Created by server1 on 2017/1/4.
 */
var groupId;
var tag_plate;
var tag_elment;
var btn_elment;
;(function ($) {
    $(function () {
        $(".addGroup").on("click",function () {

            var msg;
            if($(".row-group").length >= 6)
            {
                msg=$(["<span>标签组不能多于6个</span>"].join(""));
            }
            else
            {
                var form_token = $("meta[name='token']").attr("content");

                msg=$(["                    <form method='post' id='groupform' >",
                    "<input     type=\"hidden\"     name=\"token\"     value=\""+form_token+"\"    />",
                    "                                  <div class=\"panel-body\">",
                    "                                      <div class=\"form-group\">",
                    "											<label class=\"col-lg-3 control-label\">标签组名:</label>",
                    "											<div class=\"col-lg-9\">",
                    "												<input type=\"text\" class=\"form-control\" placeholder=\"请输入标签组名\" name='groupName' >",
                    "                                         			<span class=\"help-block\">注意：标签组名最多为10位，并且标签组最多为6个</span>",
                    "											</div>",
                    "										</div>",
                    "                               </div>",
                    "                            </form>"].join(""));
            }
            $.alert("添加标签组",msg);

            $(".btn-primary").on("click",function(){
                if($(".modal-body").html() =="<span>标签组不能多于6个</span>")
                {
                    $(".close").trigger("click");
                }
                else
                {
                    $(".help-block").css("color","");
                    $(".help-block")[0].innerHTML="注意：标签组名最多为10位，并且标签组最多为6个";
                    if($("[name=groupName]").length > 0 && $("[name=groupName]").val().length > 10)
                    {
                        $(".help-block").css("color","red");
                        $(".help-block").html("注意：标签组名最多为10位，并且标签组最多为6个");
                    }
                    else if($("[name=groupName]").val() == "")
                    {
                        helpblock("请输入标签组名称",0);
                    }
                    else if($("[name=groupName]").length == 0)
                    {
                        $(".close").trigger("click");
                    }
                    else
                    {
                        this.disabled=true;
                        btn_elment=this;
                        $.ajax({
                            type:"POST",
                            url:hostUrl+"addGroup?_csrf="+$("meta[name='_csrf']").attr("content"),
                            data:{"groupName":$("input[name=groupName]").val(),"token":form_token},
                            success:function(result)
                            {
                                if(result == "failure")
                                {
                                    $(".close").trigger("click");
                                    btn_elment.disabled=false;
                                    alert("标签组名不能重复");
                                }

                                window.location.href=window.location.href;
                            }
                        });
                    }
                }
            });
        });

        $(".row-group").on("click",function () {
            $(".row-group").parent().attr("class","");
            this.parentElement.className="choice-tag";
            var plateTag=$(".tag-group");
            $(".tag-group").css("display","none");
            for(var i=0;i<plateTag.length;i++)
            {
                if($(plateTag[i]).attr("data-id") == $(this).attr("data-id"))
                {
                    tag_plate=$(plateTag[i]);
                    $(plateTag[i]).css("display","block");
                }
            }
        });

        $(".row-tag").on("click",function () {

            $(".row-tag").parent().attr("class","");
            this.parentElement.className="choice-tag";
        });

        $(".updateGroup").on("click",function(){
            var groups=$(".row-group");
            var hasChoiceGroup=false;
            var choice;
            var msg;
            var name;
            for(var i=0;i<groups.length;i++)
            {
                if($(groups[i].parentElement).hasClass("choice-tag"))
                {
                    hasChoiceGroup=true;
                    choice=groups[i];
                }
            }
            if(hasChoiceGroup)
            {
                name=$(choice).attr("data-name");
                msg=$(["                                  <div class=\"panel-body\">",
                    "                                      <div class=\"form-group\">",
                    "											<label class=\"col-lg-3 control-label\">标签组名:</label>",
                    "											<div class=\"col-lg-9\">",
                    "												<input type=\"text\" class=\"form-control\" placeholder=\"请输入标签组名\" id='groupName' value='"+name+"' >",
                    "                                         			<span class=\"help-block\">注意：标签组名最多为10位</span>",
                    "											</div>",
                    "										</div>",
                    "                               </div>"].join(""));
            }
            else
            {
                msg=$(["<span>请点击的标签组名选择标签组</span>"].join(""));
            }

            $.alert("修改标签组",msg);

            $(".btn-primary").on("click",function(){

                var groupName=$("#groupName").val();
                $(".help-block").css("color","");
                if($("#groupName").length > 0 && groupName.length > 10)
                {
                    $(".help-block").css("color","red");
                    $(".help-block").html("注意：标签组名最多为10位");
                }
                else if($("#groupName").length == 0)
                {
                    $(".close").trigger("click");
                }
                else
                {
                    groupName=groupName==""?name:groupName;
                    var form_token = $("meta[name='token']").attr("content");
                    this.disabled=true;
                    btn_elment=this;
                    $.ajax({
                        type:"post",
                        url:hostUrl+"updateGroupName?_csrf="+$("meta[name='_csrf']").attr("content"),
                        data:{"groupId":$(choice).attr("data-id"),"groupName":groupName,"token":form_token},
                        success:function(result){
                            if(result == "update success")
                            {
                                $(".close").trigger("click");
                                $(choice).html(groupName);
                                $(choice).attr("data-name",groupName);
                            }
                            else if(result.indexOf("FormSubmitException") != -1)
                            {
                                $(".modal-body").html("<span style='height:242px;display: block' class=\"text-semibold\">不能重复提交申请页面！</span>");
                                btn_elment.disabled=false;
                            }                    		
                            else if(result == "failure")
                    		{
                                $(".close").trigger("click");
                                btn_elment.disabled=false;
                                alert("标签组名不能重复");

                                window.location.href=window.location.href;
                    		}
                        }
                    });
                }
            });
        });

        $(".styled").on("click",function () {
            if($(".checked").length > 0)
            {
                $(".c-plan").css("display","block");
            }
            else
            {
                $(".c-plan").css("display","none");
            }
        });

        $(".updateTag").on("click",function(){
            var tags=$(".row-tag");
            var hasChoiceTag=false;
            var choice;
            var msg;
            var name;
            for(var i=0;i<tags.length;i++)
            {
                if($(tags[i].parentElement).hasClass("choice-tag"))
                {
                    hasChoiceTag=true;
                    choice=tags[i];
                }
            }
            if(hasChoiceTag)
            {
                name=$(choice).attr("data-name");
                msg=$(["                                  <div class=\"panel-body\">",
                    "                                      <div class=\"form-group\">",
                    "											<label class=\"col-lg-3 control-label\">标签名:</label>",
                    "											<div class=\"col-lg-9\">",
                    "												<input type=\"text\" class=\"form-control\" placeholder=\"请输入标签名\" id='tagName' value='"+name+"' >",
                    "                                         			<span class=\"help-block\">注意：标签名最多为10位</span>",
                    "											</div>",
                    "										</div>",
                    "                               </div>"].join(""));
            }
            else
            {
                msg=$(["<span>请点击的标签名选择标签</span>"].join(""));
            }

            $.alert("修改标签",msg);


            $(".btn-primary").on("click",function(){

                var tagName=$("#tagName").val();
                $(".help-block").css("color","");
                if($("#tagName").length > 0 && tagName.length > 10)
                {
                    $(".help-block").css("color","red");
                    $(".help-block").html("注意：标签名最多为10位");
                }
                else if($("#tagName").length == 0)
                {
                    $(".close").trigger("click");
                }
                else
                {
                    tagName=tagName==""?name:tagName;
                    var form_token = $("meta[name='token']").attr("content");
                    this.disabled=true;
                    btn_elment=this;
                    $.ajax({
                        type:"post",
                        url:hostUrl+"updateTagName?_csrf="+$("meta[name='_csrf']").attr("content"),
                        data:{"tagId":$(choice).attr("data-id"),"tagName":tagName,"token":form_token},
                        success:function(result){
                            if(result == "update success")
                            {
                                $(".close").trigger("click");
                                $(choice).html(tagName);
                                $(choice).attr("data-name",tagName);
                            }
                            else if(result.indexOf("FormSubmitException") != -1)
                            {
                                $(".modal-body").html("<span style='height:242px;display: block' class=\"text-semibold\">不能重复提交申请页面！</span>");
                            }                    		
                            else if(result == "failure")
                    		{
                                $(".close").trigger("click");
                                btn_elment.disabled=false;
                                alert("标签名不能重复");
                                window.location.href=window.location.href;
                    		}
                            
                        }
                    });
                }
            });
        });

        $(".addTag").on("click",function () {
            var groups=$(".row-group");
            var hasChoiceGroup=false;
            var choice;
            var msg;
            for(var i=0;i<groups.length;i++)
            {
                if($(groups[i].parentElement).hasClass("choice-tag"))
                {
                    hasChoiceGroup=true;
                    choice=groups[i];
                }
            }
            if(hasChoiceGroup)
            {
                var form_token = $("meta[name='token']").attr("content");
                msg=$(["                    <form method='post' id='tagform' >",
                    "<input     type=\"hidden\"     name=\"token\"     value=\""+form_token+"\"    />",
                    "                                  <div class=\"panel-body\">",
                    "                                      <div class=\"form-group\">",
                    "											<label class=\"col-lg-3 control-label\">标签名:</label>",
                    "											<div class=\"col-lg-9\">",
                    "												<input type=\"text\" class=\"form-control\" placeholder=\"请输入标签名\"name='tagName'id='tagName' >",
                    "                                        <input type='hidden' name='groupId' value='"+$(choice).attr("data-id")+"'/>",
                    "                                         			<span class=\"help-block\">注意：标签名最多为10位</span>",
                    "											</div>",
                    "										</div>",
                    "                               </div>",
                    "                            </form>"].join(""));
            }
            else
            {
                msg=$(["<span>请选择标签组</span>"].join(""));
            }

            $.alert("添加标签",msg);


            $("#tagform").submit(function () {
                if($("[name=tagName]").val() == "")
                {
                    helpblock("请输入标签名称",0);
                    return false;
                }
                return true;
            });

            $(".btn-primary").on("click",function(){
                $(".help-block").css("color","");
                if($("[name=tagName]").length > 0 && $("[name=tagName]").val().length > 10)
                {
                    $(".help-block").css("color","red");
                    $(".help-block").html("注意：标签名最多为10位");
                }
                else if($("[name=tagName]").length == 0)
                {
                    $(".close").trigger("click");
                }
                else
                {
                    this.disabled=true;
                    btn_elment=this;
                    var tagName=$("#tagName").val();
                    $.ajax({
                    	type:"POST",
                    	url:hostUrl+"addTag?_csrf="+$("meta[name='_csrf']").attr("content"),
                    	data:{"groupId":$(choice).attr("data-id"),"tagName":tagName,"token":form_token},
                    	success:function(result)
                    	{
                    		if(result == "failure")
                    		{
                                $(".close").trigger("click");
                                btn_elment.disabled=false;
                                alert("标签名不能重复");
                    		}

                            window.location.href=window.location.href;
                    	}
                    });
                }
            });
        });

        $(".del-group").on("click",function () {
            var groups=$(".row-group");
            var hasChoiceGroup=false;
            var choice;
            var msg;
            for(var i=0;i<groups.length;i++)
            {
                if($(groups[i].parentElement).hasClass("choice-tag"))
                {
                    hasChoiceGroup=true;
                    choice=groups[i];
                }
            }
            if(hasChoiceGroup)
            {
                $.alert("删除标签组",$(["<span>是否确定删除该标签组</span>"].join("")));
                $(".btn-primary").on("click",function () {
                    $.ajax({
                        type:"post",
                        url:hostUrl+"deleteGroup?_csrf="+$("meta[name='_csrf']").attr("content"),
                        data:{"groupId":$(choice).attr("data-id")},
                        success:function(result){
                            if(result == "delete success")
                            {
                                var plateTag=$(".tag-group");
                                for(var i=0;i<plateTag.length;i++)
                                {
                                    if($(plateTag[i]).attr("data-id") == $(choice).attr("data-id"))
                                    {
                                        $(plateTag[i]).remove();
                                    }
                                }

                                $(choice).parent().remove();
                            }

                            $(".close").trigger("click");
                        }
                    });
                });
            }
            else
            {
                msg=$(["<span>请点击的标签组名选择标签组</span>"].join(""));

                $.alert("删除标签组",msg);
            }
        });

        $(".del-tag").on("click",function () {
            var tags=$(".row-tag");
            var hasChoiceTag=false;
            var choice;
            var msg;
            for(var i=0;i<tags.length;i++)
            {
                if($(tags[i].parentElement).hasClass("choice-tag"))
                {
                    hasChoiceTag=true;
                    choice=tags[i];
                }
            }
            if(hasChoiceTag)
            {
                $.alert("删除标签",$(["<span>是否确定删除该标签</span>"].join("")));
                $(".btn-primary").on("click",function () {
                    $.ajax({
                        type: "post",
                        url: hostUrl+"deleteTag?_csrf=" + $("meta[name='_csrf']").attr("content"),
                        data: {"tagId": $(choice).attr("data-id")},
                        success: function (result) {
                            if (result == "delete success") {
                                $(choice).parent().remove();
                            }

                            $(".close").trigger("click");
                        }
                    });
                });
            }
            else
            {
                msg=$(["<span>请点击的标签名选择标签</span>"].join(""));

                $.alert("删除标签",msg);
            }
        });


        $(".copyPlan").on("click",function () {
            var plans=$(".plan");
            var data={};
            for(var i=0,r=0;i<plans.length;i++)
            {
                if($(".styled")[i].checked)
                {
                    data["planIds["+r+"]"]=$(plans[i]).attr("data-id");
                    r++;
                }
            }

            $.ajax({
                type:"post",
                url:hostUrl+"copyUserPlan?_csrf="+$("meta[name='_csrf']").attr("content"),
                data:data,
                success:function (result) {
                    if(result == "copy success")
                    {
                        $.alert("","<span>复制成功！</span>");
                        $(".btn-primary").on("click",function () {
                            $(".close").trigger("click");
                            window.location.href = window.location.href;
                        });
                    }
                }
            });
        });


        $(".delPlan").on("click",function () {
            var plans=$(".plan");
            var data={};
            var choice=new Array();
            for(var i=0,r=0;i<plans.length;i++)
            {
                if($(".styled")[i].checked)
                {
                    data["planIds["+r+"]"]=$(plans[i]).attr("data-id");
                    choice[choice.length]=plans[i];
                    r++;
                }
            }

            $.ajax({
                type:"post",
                url:hostUrl+"deletePlan?_csrf="+$("meta[name='_csrf']").attr("content"),
                data:data,
                success:function (result) {
                    if(result == "delete success")
                    {
                        for(var i=0;i<choice.length;i++)
                        {
                            $(choice[i]).remove();
                        }
                    }
                }
            });
        });

        $(".cancel-choice").on("click",function () {
            var check=$(".styled");
            for(var i=0;i<check.length;i++)
            {
                if(check[i].checked)
                {
                    $(check[i]).trigger("click");
                }
            }
        });

        $("#edit").on("click",function () {
            if(this.innerHTML == "编辑")
            {
                $("#planTitle").attr("disabled",false);
                $("select").attr("disabled",false);
                this.innerHTML="确认修改";
            }
            else
            {
                $.alert("提示",$(["<div class=\"form-group\">",
                    "                                 <span>你确定修改方案信息吗</span>",
                    "										</div>"].join("")));
                $(".btn-primary").on("click",function () {
                    var select=$("#planform").find("select");
                    for(var i=0;i<select.length;i++)
                    {
                        if(isNaN(select[i].value))
                        {
                            select[i].value="0";
                        }
                    }
                    $(".close").trigger("click");
                    $("#planform").submit();
                    this.disabled=true;
                })
            }
        });

        $("#cancel").on("click",function () {
            $("#planTitle").attr("disabled",true);
            $("select").attr("disabled",true);
            $("#edit").html("编辑");
        });

        $(".add-new-tag").on("click",function () {
            select.css("display","block");
            $.alert("添加新标签",$(select[0]));
            $(".add-tag").on("click",function () {
                $("#hasTag").append($(this.parentElement));
                $(this).remove();
            });
            $(".btn-primary").on("click",function(){
               $(".close").trigger("click");
            });
        });

        $(".group-tag").prev().on("click",function () {
            if($(this).siblings().css("display") == "none")
            {
                $(".group-tag").css("display","none");
                $(this).siblings().css("display","block");
                groupId=$(this).siblings().attr("data-group");
            }
            else
            {
                $(this).siblings().css("display","none");
            }
        });

        $(document).on("click",function () {
            $(".group-tag").css("display","none");
        });

        $(".imgpath").on("click",function () {

            var imgurl = $(this).attr("imgurl");
            var imgpath = $(this).attr("imgpath");
            var plantitle = $(this).attr("plantitle");

            var token = $("meta[name='_csrf']").attr("content");
            var msg= ["      <img src=\""+imgurl+imgpath+"\"  class=\"img-thumbnail\" />",
            ].join("");
            $.alert(plantitle,msg);
            btnUpload();
            $(".btn-primary").on("click",function () {
                $(".form-attr").submit();
            });
        });

        $(".group-body").css("width",$(".row-groups-i").width()-100+"px");

        $(".select").on("change",function () {
            if(this.value == "添加标签")
            {
                groupId=$(this).attr("data-groupId");
                msg=$([
                    "                                  <div class=\"panel-body\">",
                    "                                      <div class=\"form-group\">",
                    "											<label class=\"col-lg-3 control-label\">标签名:</label>",
                    "											<div class=\"col-lg-9\">",
                    "												<input type=\"text\" class=\"form-control\" placeholder=\"请输入标签名\"name='tagName' >",
                    "                                         			<span class=\"help-block\">注意：标签名最多为10位</span>",
                    "                                        <input type='hidden' name='groupId' value='"+groupId+"'/>",
                    "											</div>",
                    "										</div>",
                    "                               </div>",].join(""));

                if($(".modal-body").length !=0)
                {
                    $(".modal-body").html(msg);
                }
                else
                {
                    $.alert("添加标签",msg);
                }

                tag_elment=this;
                $(".btn-primary").on("click",function(){
                    this.disabled=true;
                    btn_elment=this;
                    var form_token = $("meta[name='token']").attr("content");
                    addTag_select(form_token);
                });
            }
        });
        window.onload=function() {

            window.onresize = function () {
                tagGroupResize();
                $(".group-body").css("width", $(".row-groups-i").width() - 100 + "px");
            };

        }

        tagGroupResize();
        function tagGroupResize() {
            if ($(window).width() > 768) {
                $(".col-md-4").css("width", "49%");
                $(".plan-img img").css("width", "500px");
                $(".plan-img img").css("height", "300px");
            }
            else {
                $(".col-md-4").removeAttr("style");
                $(".plan-img img").css("width", "100%");
                $(".plan-img").css("width", "100%");
                $(".plan-l").css("width", "100%");
            }
        }
        
        function addTag_select(form_token) {
            var value=$("input[name=tagName]").val();
            $.ajax({
                type:"POST",
                url:hostUrl+"addTag?_csrf="+$("meta[name='_csrf']").attr("content"),
                data:{"tagName":value,"token":form_token,"groupId":$("input[name=groupId]").val()},
                success:function(result)
                {
                    if(result == "failure")
                    {
                        $(".close").trigger("click");
                        btn_elment.disabled=false;
                        alert("标签名不能重复");
                    }
                    window.location.href=window.location.href;
                }
            });
        }
    });
})(jQuery);

