/**
 * Created by server1 on 2016/11/24.
 */
var model_num=0;
;(function ($) {
    $(function () {
        $(".del-model").on("click",function () {
           // var modelList=$(".model-choice");
            var modelList=$("input[name='sub_del']:checked");
            if (modelList.length == 0) {
                alert("请至少选择一项!");
                return false;
            }
            if (confirm("确定删除所选模型?")) {
                var modelIdList = {};
                var r = 0;
                for (var i = 0; i < modelList.length; i++) {
                    if (modelList[i].checked) {
                        modelIdList['modelIdList[' + r + ']'] = modelList[i].value;
                        r++;
                    }
                }

                $.ajax({
                    type: 'GET',
                    url: hostUrl + "del_model",
                    data: modelIdList,
                    success: function (msg) {
                        if (msg == "success delete model") {
                            window.location.href = "/qualifyModel";
                        }
                    }
                });
            }
        });

        $(".addModelImage").on("click",function () {
            var brandLength=$(".brand").length;
            if(brandLength<7)
            {
                var msg=["	<form class=\"form-horizontal form-attr\" role=\"form\" >",
                    "			<div class=\"form-group\">",
                    "				<label for=\"firstname\" class=\"col-sm-2 control-label\">参考图方位</label>",
                    "				<div class=\"col-sm-10\">",
                    "					<input type=\"text\" class=\"form-control\" id=\"position\" ",
                    "						   placeholder=\"填上、下、左、右、前、后\">",
                    "				</div>",
                    "			</div>",
                    "			<div class=\"form-group\">",
                    "				<label  class=\"col-sm-2 control-label\">参考图</label>",
                    "				<div class=\"col-sm-10\">",
                    "                   <div class='bg-primary-800 demo-color'  style='float: left'>",
                    "					<img id='previewImg'/>",
                    "                   </div>",
                    "<input type=\"file\" accept=\"image/*\" class='modelImgFile' style=\"display: none\"  id=\"uploadImg\">",
                    "					<button type=\"button\" id='addImg'>上传</button>",
                    "<div style='color:red'></div>",
                    "				</div>",
                    "			</div>",
                    "       </form>"].join("");
                $.alert("参考图信息",msg);
                btnUpload();
                $(".btn-primary").on("click",function () {
                    var brand=$(["                                                <div class=\"col-sm-4 col-lg-2\">",
                        "                                                    <div>",
                        "                                                        <div class=\"bg-primary-800 demo-color model-i\" name=\"modelImgFile\">",
                        "                                                        </div>",
                        "                                                        <div class=\"p-15\">",
                        "                                                            <div class=\"media-body model-t\">",
                        "                                                            </div>",
                        "                                                        </div>",
                        "                                                    </div>",
                        "                                                </div>"].join(""));
                    $("#uploadImg").attr("name","modelImgFile['"+model_num+"']");
                    $("#position").attr("name","positionName['"+model_num+"']");
                    model_num++;
                    var imgfile=$("#uploadImg");
                    var img=$("#previewImg");
                    var imgtext=$("#position");

                    imgfile.removeAttr("id");
                    img.removeAttr("id");
                    imgtext.removeAttr("id");

                    brand.find(".model-i").append(img);
                    brand.find(".model-t").append(imgtext);
                    brand.find(".model-i").append(imgfile);
                    brand.find(".model-i").append($(" <div class=\"del-model-btn\"> <a href=\"javascript:void(0);\" class=\"del-model\" > <i class=\"icon-bin\"></i> </a> </div>"));

                    $(".modelDiagram").prepend(brand);
                    $(".close").trigger("click");

                    $(".del-model").on("click",function () {
                       $(this.parentElement.parentElement.parentElement.parentElement) .remove();
                    });
                });
            }
            else
            {
                $.alert("提示","参考图已达到上限不能再添加");
            }
        });


        $("#model_sub").on("click",function () {
            var name=$("input[name=modelName]").val();
            var isError=false;
            if(model_info_check.check_model_field(name))
            {
                isError=true;
                helpblock("模型名不能为空!",0);
            }
            if(model_info_check.check_model_field_length(name,40))
            {
                isError=true;
                helpblock("模型名不能超过40位!",0);
            }
            if($(".modelImgFile").length == 0)
            {
                helpblock("请上传参考图!",1);
                isError=true;
            }
            if(!isError)
            {
                this.disabled=true;
                $("#modelform").submit();
            }
        });

    });
})(jQuery);