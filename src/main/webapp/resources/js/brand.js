
function changeBg(link)
{
    var alllinks=document.getElementById("pagination").getElementsByTagName("a");
    for(var i=0;i<alllinks.length;i++){
        if(alllinks[i].hasClass('active'))
            alllinks[i].removeClass('active');
        // alllinks[i].className="";//默认未点击时引用的样式
    }
    link.addClass('active');//点击切换样式
}


/**
 * Created by server1 on 2016/11/23.
 */

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

        $(".imgpath").on("click",function () {

            var imgurl = $(this).attr("imgurl");
            var imgpath = $(this).attr("imgpath");

            //alert("imgpath :"+imgpath);
            var token = $("meta[name='_csrf']").attr("content");
            var msg= ["      <img src=\""+imgurl+imgpath+"\" class=\"img-thumbnail\"/>"
            ].join("");
            $.alert("浏览", msg);
        });

        $(".veto").on("click",function () {

            var node = this;
            var sellerName = $(this).attr("cn");
            //alert("sellerName:"+sellerName);
            var token = $("meta[name='_csrf']").attr("content");
            var msg=$([" <form class=\"form-horizontal vetoform\" role=\"form\" id=\"form1\" method=\"post\" action=\'"+hostUrl+"/brand?_csrf="+token+"\'>",
                "        <div class=\"form-group\">",
                "            <label  class=\"col-sm-2 control-label\">品牌名称</label>",
                "            <div class=\"col-sm-10\">",
                "               <textarea type=\"text\" rows=\"1\" style=\"border:none;\" value=\"comany\" name=\"sellerName\">"+sellerName+"</textarea>",
                "            </div>",
                "        </div>",
                "        <div class=\"form-group\">",
                "            <label  class=\"col-sm-2 control-label\">失败原因</label>",
                "            <div class=\"col-sm-10\">",
                "                <select class=\"form-control\" name=\"cerCausepre\">",
                "                    <option value=\"信息有误\">信息有误</option>",
                "                    <option value=\"信息有\">信息有</option>",
                "                    <option value=\"信息\">信息</option>",
                "                    <option value=\"信\">信</option>",
                "                </select>",
                "            </div>",
                "        </div>",
                "        <div class=\"form-group\">",
                "            <div class=\"col-sm-offset-2 col-sm-10\">",
                "                <textarea class=\"form-control\" rows=\"4\" name=\"cerCause\"></textarea>",
                "            </div>",
                "        </div>",
                "    </form>"].join(""));
            $.alert("否决："+sellerName+"",msg);
            dialog.find(".btn-primary").on("click",function () {
                veto(node);
            })
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
    if (r){
        createXMLHttpResquest();
        console.log();
        var sellerId = $(node).attr("sellerId");
        //alert(sellerId);
        var url = "brand/pass?sellerId="+sellerId;
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
    }else{
        return false;
    }

}


function veto(node) {

    var sellerName = document.getElementsByName("sellerName")[0].value;
    var cerCausepre = document.getElementsByName("cerCausepre")[0].value;
    var cerCause = document.getElementsByName("cerCause")[0].value;
    var token = $("meta[name='_csrf']").attr("content");
    //alert(sellerName+"+"+cerCausepre+"+"+ cerCause);
    createXMLHttpResquest();
    $.ajax({
        cache: true,
        type: "POST",
        url:hostUrl+"/brand/veto?_csrf="+token,
      // data:$('#form1').serialize(),// 你的formid
        data:{sellerName:sellerName,cerCausepre:cerCausepre,cerCause:cerCause},
        async: false,
        error: function(request) {
            alert("Connection error");
        },
        success: function(data) {
            $('#myModal').modal('hide');
            alert(data);
            $(node.parentElement.firstElementChild).attr({"onclick" : "null"});//
            $(node.parentElement.firstElementChild).text('已否决');
            $(node).attr({"onclick" : "null"});//
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
        var sellerId = $(node).attr("sellerId");
        //alert(sellerId);
        var url = hostUrl+"/brand/cancel?sellerId="+sellerId;
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

/*function search() {
    var name = document.getElementById("name").value;
    //alert(name);
    window.location.href =hostUrl+'/brand?name='+name+'&flag='+2+'';
}*/

