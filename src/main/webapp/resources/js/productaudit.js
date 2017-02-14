

var dialog;
;(function ($) {
    $(function () {
        $.alert=function (title,msg) {
             dialog=$(["<div class=\"modal fade\" id=\"myModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">",
                "	<div class=\"modal-dialog\">",
                "		<div class=\"modal-content\">",
                "			<div class=\"modal-header\">",
                "				<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">×",
                "				</button>",
                "				<h4 class=\"modal-title\" id=\"myModalLabel\">",
                "                 ",
                "				</h4>",
                "			</div>",
                "			<div class=\"modal-body\">",
                "               ",
                "			</div>",
                "			<div class=\"modal-footer\">",
                "				<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">",
                "					关闭",
                "				</button>",
                "				<button type=\"button\" class=\"btn btn-primary\">",
                "					确定",
                "				</button>",
                "			</div>",
                "		</div><!-- /.modal-content -->",
                "	</div><!-- /.modal-dialog -->",
                "</div><!-- /.modal -->"].join(""));
            $("body").append(dialog);
            dialog.find(".modal-title").text(title);
            dialog.find(".modal-body").html(msg);
            dialog.modal("show");
            dialog.on("hidden.bs.modal",function(){
                dialog.remove();
                $(".modal-backdrop").remove();
            });
        }

        var imgpathurl = $(".imgpath");
        $(document).ready(function () {
            imgpathurl.each(function () {
                var imgurl = $(this).attr("imgurl");
                var imgpath1 = $(this).attr("imgpath");
                var imgsufix = $(this).attr("imgsufix");
                var imgpath = imgpath1;
                var kkk = [];
                kkk = new String(imgpath).split(";");
                var ttt = [];
                ttt = kkk[0].split(',');
                $(this).attr("src", imgurl + ttt[0] + imgsufix);
               /* $(this).attr("alt", ttt[0]);
                $(this).attr("title", ttt[0]);*/
            });
        });

       /* imgpathurl.on({
            "click": function () {
                var imgurl = $(this).attr("imgurl");
                var imgpath = $(this).attr("imgpath");
                //console.log(imgurl + "|" + imgpath + "|");
                var kkk = [];
                kkk = imgpath.split(";");
                var ttt = [];
                var html = "";
                for (var i = 0; i < kkk.length - 1; i++) {
                    ttt = kkk[i].split(",");
                    //console.log(ttt);
                    //$(this).attr("src", imgurl + ttt[1]);
                    html +=  "      <img src=\"" + imgurl + ttt[0] + "\"  class=\"img-thumbnail col-md-12\" /> <br> ";
                }
                var msg = $([html
                ].join(""));
                $.alert("浏览", msg);
            }
        });
*/

        imgpathurl.on("click",function () {

            var imgurl = $(this).attr("imgurl");
            var imgpath = $(this).attr("imgpath");
            var plantitle = $(this).attr("plantitle");

            var kkk = [];
            kkk = imgpath.split(",");
            //console.log(kkk);
            var ttt = [];
            var html = "";
            for (var i = 0; i < kkk.length; i++) {
                if(kkk[i] != "")
                {
                    ttt = kkk[i];
                    //console.log(ttt);
                    //$(this).attr("src", imgurl + ttt[1]);
                    html += "      <img src=\"" + imgurl + ttt + "\"  class=\"img-thumbnail col-md-12\" /> <br> ";
                }
            }
            var msg = $([html
            ].join(""));
            $.alert("浏览", msg);
        });

        $(".imgpath2").on("click",function () {

            var imgurl = $(this).attr("imgurl");
            var imgpath = $(this).attr("imgpath");
            var plantitle = $(this).attr("plantitle");

            //alert("imgpath :"+imgpath);
            var token = $("meta[name='_csrf']").attr("content");
            var msg= ["      <img src=\""+imgurl+imgpath+"\"  class=\"img-thumbnail\" />",
            ].join("");
            $.alert(plantitle,msg);
        });


        $(".veto").on("click",function () {

            var node = this;
            var productName = $(this).attr("cn");
            //alert("companyName:"+companyName);
            var token = $("meta[name='_csrf']").attr("content");
            var msg=$([" <form class=\"form-horizontal vetoform\" role=\"form\" id=\"form1\" method=\"post\" action=\'"+hostUrl+"/productaudit?_csrf="+token+"\'>",
                "        <div class=\"form-group\">",
                "            <label  class=\"col-sm-2 control-label\">商品名称</label>",
                "            <div class=\"col-sm-10\">",
                "               <textarea type=\"text\" rows=\"1\" style=\"border:none;\" value=\"comany\" name=\"productName\">"+productName+"</textarea>",
                "            </div>",
                "        </div>",
                "        <div class=\"form-group\">",
                "            <label  class=\"col-sm-2 control-label\">失败原因</label>",
                "            <div class=\"col-sm-10\">",
                "                <select class=\"form-control\" name=\"cerCausepre\">",
                "                    <option value=\"工商信息有误\">工商信息有误</option>",
                "                    <option value=\"工商信息有\">工商信息有</option>",
                "                    <option value=\"工商信息\">工商信息</option>",
                "                    <option value=\"工商信\">工商信</option>",
                "                </select>",
                "            </div>",
                "        </div>",
                "        <div class=\"form-group\">",
                "            <div class=\"col-sm-offset-2 col-sm-10\">",
                "                <textarea class=\"form-control\" rows=\"4\" name=\"cerCause\"></textarea>",
                "            </div>",
                "        </div>",
                "    </form>"].join(""));
            $.alert("否决："+productName+"",msg);
            dialog.find(".btn-primary").on("click",function () {
                veto(node);
            })
        });

        $('.taginputedit').change(function(){
            var t = $("#tagNamesedit");
            var SPEARTER= ",";
            var name =$(this).children('option:selected').val();
            //$("#tagNames").attr('value', name);

            if (t.val() == "") {
                t.val(name);
            } else {
                t.val(t.val() + SPEARTER + name);
            }
        });


        $(".edit").on("click",function () {
            cleanedit();
            var productId = $(this).attr("productId");
            var productName = $(this).attr("productName");
            var description = $(this).attr("description");
            var styleId = $(this).attr("styleId");
            var typeId = $(this).attr("typeId");
            var attachType = $(this).attr("attachType");
            var rotateType = $(this).attr("rotateType");
            var scaleX = $(this).attr("scaleX");
            var scaleY = $(this).attr("scaleY");
            var scaleZ = $(this).attr("scaleZ");
            var tagNames = $(this).attr("tagNames");
            //console.log(scaleX+"+"+scaleY+"+"+scaleZ+"+"+typeId+"+"+styleId+"+"+attachType+"+"+tagNames);
            $('#productIdedit').text(productId);
            $('#productNameedit').attr("value",productName);
            $('#descriptionedit').attr("value",description);
            $('#styleIdedit').val(styleId);
            $('#typeIdedit').val(typeId);
            $('#attachTypeedit').val(attachType);
            $('#rotateTypeedit').val(rotateType);
            $('#scaleXedit').find("option[value="+scaleX+"]").attr("selected", true);
            $('#scaleYedit').val(scaleY);
            $('#scaleZedit').val(scaleZ);
            $('#tagsinputedit').select2({
                tags: true,
                tokenSeparators: [',', ' '],
                maximumSelectionLength: 3
            }).val(tagNames.split(',')).trigger("change");

            $("#myModaledit").modal('show');
        });

        cleanedit=function () {
            $('#productIdedit').text("");
            $('#productNameedit').attr("value","");
            $('#descriptionedit').attr("value","");
            $('#styleIdedit').find('option:selected').attr('selected', false);
            $('#typeIdedit').find('option:selected').attr('selected', false);
            $('#attachTypeedit').find('option:selected').attr('selected', false);
            $('#rotateTypeedit').find('option:selected').attr('selected', false);
            $('#scaleXedit').find('option:selected').attr('selected', false);
            $('#scaleYedit').find('option:selected').attr('selected', false);
            $('#scaleZedit').find('option:selected').attr('selected', false);
            $('#tagNamesedit').attr("value","");
        };

        $("#tagsinputedit").select2({
            tags: true,
            tokenSeparators: [',', ' '],
            maximumSelectionLength: 3
        });

    });
})(jQuery);


var xmlHttp;

function createXMLHttpResquest() {
    if(window.ActiveXObject){
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }else if (window.XMLHttpRequest)
    {
        xmlHttp = new XMLHttpRequest();
    }
}


function pass(node) {
    var r=confirm("确认通过？");
    if (r)
    {
        createXMLHttpResquest();
        console.log();
        var productId = $(node).attr("productId");
        //alert(certificationId);
        var url = hostUrl+"productaudit/pass?productId="+productId;
        xmlHttp.open("GET",url,true);
        xmlHttp.onreadystatechange = handleStateChange;
        xmlHttp.send(null);
        function handleStateChange() {
            console.log(xmlHttp.responseText);
            if(xmlHttp.readyState == 4){
                //alert(xmlHttp.responseText);
                $(node).attr({"onclick" : "cancel(this)"});
                $(node).text('取消通过');
                $(node.parentElement.lastElementChild).attr({"onclick" : "null"});
                $(node.parentElement.lastElementChild).text('认证成功');
                alert(xmlHttp.responseText);
            }
        }
        return true;
    }
    else
    {
        return false;
    }

}


function veto(node) {
    var productName = document.getElementsByName("productName")[0].value;
    var cerCausepre = document.getElementsByName("cerCausepre")[0].value;
    var cerCause = document.getElementsByName("cerCause")[0].value;
    var token = $("meta[name='_csrf']").attr("content");
    //alert(companyName+"+"+cerCausepre+"+"+ cerCause);
    createXMLHttpResquest();
    $.ajax({
        cache: true,
        type: "POST",
        url:hostUrl+"productaudit/veto?_csrf="+token,
      // data:$('#form1').serialize(),// 你的formid
        data:{productName:productName,cerCausepre:cerCausepre,cerCause:cerCause},
        async: false,
        error: function(request) {
            alert("Connection error");
        },
        success: function(data) {
            $('#myModal').modal('hide');
            alert(data);
            $(node.parentElement.firstElementChild).attr({"onclick" : "null"});//会全部都禁用，要改
            $(node.parentElement.firstElementChild).text('已否决');
            $(node).attr({"onclick" : "null"});//会全部都禁用，要改
            $(node).text('认证失败');
        }
    });
}

function cancel(node) {
    var r=confirm("确认取消通过？");
    if (r)
    {
        createXMLHttpResquest();
        console.log();
        var productId = $(node).attr("productId");
        //alert(certificationId);
        var url = hostUrl+"productaudit/cancel?productId="+productId;
        xmlHttp.open("GET",url,true);
        xmlHttp.onreadystatechange = handleStateChange;
        xmlHttp.send(null);
        function handleStateChange() {
            console.log(xmlHttp.responseText);
            if(xmlHttp.readyState == 4){
                //alert(xmlHttp.responseText);
                $(node).attr({"onclick" : "pass(this)"});
                $(node).text('通过');
                $(node.parentElement.lastElementChild).attr({"onclick" : ""});
                $(node.parentElement.lastElementChild).text('否决');
                alert(xmlHttp.responseText);
                $('#myModal').modal('hide');
            }
        }
        return true;
    }
    else
    {
        return false;
    }

    
}


function show(node){
    var productId2 = $(node).attr("productId2");
    $('#productId2').text(productId2);
    $("#change").modal('show');
}

function putaway() {
    var status=document.getElementsByName("productstatus");//不能getElementById，ById又只会读数组第一个值
    var statusid;
    for(var i = 0; i < status.length; i++)
    {
        if(status[i].checked)
            statusid=i;
    }
    var formData = new FormData();
    formData.append("productId",document.getElementById("productId2").innerText);
    formData.append("productstatus",statusid);
    $.ajax({
        url: hostUrl+"productaudit/status?_csrf=" + token ,
        type: "post",
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (returndata) {
            alert(returndata);
            $("#change").modal('hide');
            setTimeout("location.reload()",100);//页面刷新
        },
        error: function (returndata) {
            alert("错误！！"+returndata);
        }
    });
}
// function search() {
//     var name = document.getElementById("name").value;
//     //alert(name);
//     window.location.href = '/audit?name='+name+'&flag='+2+'';
// }


function doEdit() {
    var formData = new FormData();
    formData.append("productId",document.getElementById("productIdedit").innerHTML);
    formData.append("productName",document.getElementById("productNameedit").value);
    formData.append("description",document.getElementById("descriptionedit").value);
    formData.append("styleId",document.getElementById("styleIdedit").value);
    formData.append("typeId",document.getElementById("typeIdedit").value);
    formData.append("attachType",document.getElementById("attachTypeedit").value);
    formData.append("rotateType",document.getElementById("rotateTypeedit").value);
    formData.append("scaleX",document.getElementById("scaleXedit").value);
    formData.append("scaleY",document.getElementById("scaleYedit").value);
    formData.append("scaleZ",document.getElementById("scaleZedit").value);
    formData.append("tagNames",$('#tagsinputedit').val());
    formData.append("ImgFile",$('#ImgFileedit')[0].files[0]);
    //console.log(formData);
    $.ajax({
        url: hostUrl+"/productaudit/edit?_csrf=" + token ,
        type: "post",
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (returndata) {
            alert(returndata);
            setTimeout("location.reload()",100);//页面刷新
        },
        error: function (returndata) {
            alert("错误！！"+returndata);
        }
    });
}

