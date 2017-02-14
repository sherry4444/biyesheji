/**
 * Created by server1 on 2016/11/23.
 */
;(function ($) {
    $(function () {
        $.alert=function (title,msg) {
            var dialog=$(["<div class=\"modal fade\" id=\"myModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">",
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

        $(".addbrand").on("click",function () {
            brandUpdatePlate($,"添加品牌","/addSeller");
        });
        $(".del-brand").on("click",function () {
            var Id=$(this).data("seller");
            var brand=this;
            $.alert("提示",$("<span><p>是否确定要删除该品牌</p><p>删除品牌后，品牌下的商品也会一并删除</p></span>"));
            $(".btn-primary").on("click",function(){
                $.ajax({
                    type:"get",
                    url:hostUrl+"deleteSeller",
                    data:{"id":Id},
                    success:function(result){
                        if(result == "delete success")
                        {
                            $(".close").trigger("click");
                            $(brand.parentElement.parentElement.parentElement.parentElement).remove();
                            setTimeout("location.reload()",100);
                        }
                    }
                });
            });
        });

        $(".up-brand").on("click",function () {

            var Id=$(this).data("seller");
            var name=$(this).data("name");
            brandUpdatePlate($,"修改品牌","/updateSeller",$(this.parentElement.parentElement.parentElement.parentElement.parentElement).find("img")[0],Id,name);
        });
    });
})(jQuery);

function brandUpdatePlate($,title,url,img,id,name) {
    var token = $("meta[name='_csrf']").attr("content");
    var form_token = $("meta[name='token']").attr("content");

    var src= typeof(img)!="undefined"?"src='"+img.src+"'":'';

    name=typeof(name)!="undefined"?name:'';
    var idIn=typeof(id)!="undefined" ? "<input type='hidden' value='"+id+"' name='sellerId'/>":'';
    var msg=["	<form class=\"form-horizontal form-attr\" id='sellerForm' role=\"form\" action='"+hostUrl+url+"?_csrf="+token+"'enctype='multipart/form-data' method='post'>",
        "<input     type=\"hidden\"     name=\"token\"     value=\""+form_token+"\"    />",
        idIn,
        "			<div class=\"form-group\">",
        "				<label for=\"firstname\" class=\"col-sm-2 control-label\">品牌名称</label>",
        "				<div class=\"col-sm-10\">",
        "					<input type=\"text\" class=\"form-control\" id=\"sellerName\" value='"+name+"'",
        "						   placeholder=\"\" name='sellerName'>",
        "                   <span class=\"help-block\"></span>",
        "				</div>",
        "			</div>",
        "			<div class=\"form-group\">",
        "				<label  class=\"col-sm-2 control-label\">品牌Logo</label>",
        "				<div class=\"col-sm-10\">",
        "					<img id='previewImg' style='max-width: 200px;max-height: 200px' "+src+"/>",
        "                   <input type=\"file\" accept=\"image/*\" style=\"display: none\" name=\"sellerImgFile\" id=\"uploadImg\">",
        "					<button type=\"button\" id='addImg'>上传</button>",
        "                   <div class=\"help-block\" style='color:red'></div>",
        "				</div>",
        "			</div>",
        "			<div class=\"form-group\">",
        "				<label  class=\"col-sm-2 control-label\">品牌授权书</label>",
        "				<div class=\"col-sm-10\">",
        "                   <input type=\"file\" accept=\"image/*\" name=\"powerAttorneyFile\" id=\"powerAttorneyFile\"/>",
        "                   <div class=\"help-block\" style='color:red'></div>",
        "				</div>",
        "			</div>",
        "			<div class=\"form-group\">",
        "				<label  class=\"col-sm-2 control-label\">商标注册书</label>",
        "				<div class=\"col-sm-10\">",
        "                   <input type=\"file\" accept=\"image/*\"  name=\"trademarkFile\"  id=\"trademarkFile\"/>",
        "                   <div class=\"help-block\" style='color:red'></div>",
        "				</div>",
        "			</div>",
        "			<div class=\"form-group\">",
        "				<label for=\"firstname\" class=\"col-sm-2 control-label\">可见</label>",
        "				<div class=\"col-sm-10\">",
        "					<label>",
        "						<input type=\"checkbox\"  name='visual'/>&nbsp;品牌及产品仅限本账号登录可见",
        "					</label>",
        "				</div>",
        "			</div>",
        "     	</form>"].join("");
    $.alert(title,msg);
    btnUpload();
    $(".btn-primary").on("click",function () {
        $(".help-block").html("");
        var isError=false;
        var sellerName=$("#sellerName").val();
        if(sellerName == '')
        {
            helpblock("请输入品牌名",0);
            isError=true;
        }if(sellerName.length > 50)
        {
            helpblock("品牌名不能超过50位",0);
            isError=true;
        }
        if ($("#uploadImg").val() == '' || $("#uploadImg").val() == null)
        {
            helpblock("请上传品牌Logo",1);
            isError=true;
        }
        if ($("#powerAttorneyFile").val() == '')
        {
            helpblock("请上传品牌授权书",2);
            isError=true;
        }
        if ($("#trademarkFile").val() == '' )
        {
            helpblock("请上传商标注册书",3);
            isError=true;
        }
        if(!isError)
        {
            $("#sellerForm").submit();
            this.disabled=true;
        }
    });
}

var model_info_check={
    check_model_field:function (value) {
        if(value == "" || value == null)
        {
            return true;
        }
        return false;
    },
    check_model_field_length:function (value,length) {
        if(value.length > length)
        {
            return true;
        }
        return false;
    }
}