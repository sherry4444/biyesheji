/**
 * Created by server1 on 2016/11/28.
 */
var productType=0;
var productStyleDiv=0;
;(function ($) {
    $(function () {
        $(".choice-producttype").on("change",function () {
            productType=$(this).data("productType");
        });

        //增加介绍套图
        $(".introduceImg").on("click",function () {
            var imgdiv=$(["                                                <div class=\"col-sm-4 col-lg-2\">",
                "                                                    <div>",
                "                                                        <div class=\"bg-primary-800 demo-color model-i\">",
                "<input type=\"file\" accept=\"image/*\" name='productIntroduceImg' style=\"display:none\">",
                "                                                            <img/>",
                "                                                        <div class=\"up-btn del-model-btn\">",
                "                                                            <a href=\"javascript:void(0);\" class=\"del-p-introduce\">",
                "                                                                <i class=\"icon-bin\"></i>",
                "                                                            </a>",
                "                                                        </div>",
                "<div style='color:red'></div>",
                "                                                        </div>",
                "                                                        <div class=\"p-15\">",
                "                                                            <div class=\"media-body\">",
                "                                                                <span>",
                "                                                                    <strong></strong>",
                "                                                                </span>",
                "                                                            </div>",
                "                                                        </div>",
                "                                                    </div>",
                "                                                </div>"].join(""));
            imgdiv.find("input").trigger("click");

            var introduceBtn=this;



            imgdiv.find("input").on("change",function () {
                checkImgFile(this);
                if(this.value != "") {
                    var file = this.files[0];
                    var src = window.URL.createObjectURL(file);
                    $(this.parentElement).find("img").attr("src", src);
                    $(introduceBtn.parentElement).prepend(imgdiv);

                    product_stack.delete_introduce();
                }
            });
        });

        $(".addProductStyle").on("click",function () {
            var model=[""];

            if(productType==0)
            {
                model=["                                    <div class=\"row\">",
                    "                                        <div class=\"form-group r-info\">",
                    "                                            <label>模型:</label>",
                    "                                            <div class=\"row\">",
                    "                                                <div data-index='"+productStyleDiv+"' class=\"col-sm-4 col-lg-2 addproductModel"+productStyleDiv+"\">",
                    "                                                    <div>",
                    "                                                        <div class=\"bg-primary-800 demo-color\">",
                    "                                                            <img src=\""+hostUrl+"resources/images/brand.jpg\"/>",
                    "                                                        </div>",
                    "                                                        <div class=\"p-15\">",
                    "                                                            <div class=\"media-body\">",
                    "                                                                <span>",
                    "                                                                    <strong>添加模型</strong>",
                    "                                                                </span>",
                    "                                                            </div>",
                    "                                                        </div>",
                    "                                                    </div>",
                    "                                                </div>",
                    "                                            </div>",
                    "                                        </div>",
                    "                                    </div>"];
            }

            var height=$(".product-form").css("height");

            $(".product-form").append($(["<div style='height: "+height+"' class='product-style style-div"+productStyleDiv+"'>",
                "                                <fieldset class=\"step ui-formwizard-content\" id=\"step1\" style=\"display: block;\">",
                "                                    <h6 class=\"form-wizard-title text-semibold\">",
                "                                        款式信息",
                "                                        <small class=\"display-block\">请填写款式信息</small>",
                "                                    </h6>",
                "                                    <div class=\"row\">",
                "                                        <div class=\"col-md-6\">",
                "                                            <div class=\"form-group\">",
                "                                                <label>款式名:",
                "                                             <span class=\"text-danger\">*</span>",
                "                                                    <a data-index=\"0\" class=\"setDefault\" href=\"javascript:void(0);\">设置为默认款式</a>",
                "                                                </label>",
                "                                                <input type=\"text\" id='styleName"+productStyleDiv+"' name='productStyleList["+productStyleDiv+"].styleName' class=\"styleName form-control ui-wizard-content\">",
                "                                            </div>",
                "                                        </div>",
                "                                        <div class=\"col-md-6\">",
                "                                            <div class=\"form-group\">",
                "                                                <label>价格:</label>",
                "                                                <input type=\"text\" name='productStyleList["+productStyleDiv+"].price' class=\"form-control ui-wizard-content\">",
                "                                            </div>",
                "                                        </div>",
                "                                    </div>",
                "                                    <div class=\"row\">",
                "                                        <div class=\"col-md-6\">",
                "                                            <label>大小:</label>",
                "                                            <div class=\"row\">",
                "                                                <div class=\"col-md-4\">",
                "                                                    <div class=\"form-group\">",
                "                                                        <input type=\"text\" name='productStyleList["+productStyleDiv+"].length' class=\"form-control ui-wizard-content\" placeholder=\"长度\">",
                "                                                    </div>",
                "                                                </div>",
                "                                                <div class=\"col-md-4\">",
                "                                                    <div class=\"form-group\">",
                "                                                        <input type=\"text\" name='productStyleList["+productStyleDiv+"].width' class=\"form-control ui-wizard-content\"  placeholder=\"宽度\">",
                "                                                    </div>",
                "                                                </div>",
                "                                                <div class=\"col-md-4\">",
                "                                                    <div class=\"form-group\">",
                "                                                        <input type=\"text\" name='productStyleList["+productStyleDiv+"].height' class=\"form-control ui-wizard-content\" placeholder=\"高度\">",
                "                                                    </div>",
                "                                                </div>",
                "                                            </div>",
                "                                        </div>",
                "                                        <div class=\"col-md-6\">",
                "                                            <div class=\"form-group\">",
                "                                                <label>网址:</label>",
                "                                                <input type=\"text\" name='productStyleList["+productStyleDiv+"].shoppingUrl' class=\"form-control ui-wizard-content\" >",
                "                                            </div>",
                "                                        </div>",
                "                                    </div>",
                model.join(""),
                "                                    <div class=\"row thumbnail"+productStyleDiv+"\">",
                "                                        <div class=\"form-group r-info\">",
                "                                            <label>略缩图:</label>",
                "                                            <div class=\"row\">",
                "                                                <div class=\"col-sm-4 col-lg-2 addStyleImage"+productStyleDiv+"\">",
                "                                                    <div>",
                "                                                        <div class=\"bg-primary-800 demo-color model-i\">",
                "                                                            <img src=\""+hostUrl+"resources/images/brand.jpg\"/>",
                "                                                        </div>",
                "                                                        <div class=\"p-15\">",
                "                                                            <div class=\"media-body\">",
                "                                                                <span class='product-style-item"+productStyleDiv+"' data-index=\""+productStyleDiv+"\">",
                "                                                                    <strong>添加略缩图</strong>",
                "                                                                </span>",
                "                                                            </div>",
                "                                                        </div>",
                "                                                    </div>",
                "                                                </div>",
                "<input type='file' accept=\"image/*\" name='productStyleImg["+productStyleDiv+"]' style='display: none' />",
                "<div style='color:red'></div>",
                "                                            </div>",
                "                                        </div>",
                "                                    </div>",
                "                                </fieldset>",
                "            <div class=\"md-form-wizard-actions\">",
                "                <button type=\"button\" data-index='"+productStyleDiv+"' class=\"btn btn-info ui-wizard-content ui-formwizard-button style-sub"+productStyleDiv+"\">确定</button>",
                "            </div>",
                "        </div>"].join("")));

            product_stack.setDefault();

            product_stack.addStyleImg(productStyleDiv);

            product_stack.addmodel(productStyleDiv);

            $(".style-sub"+productStyleDiv).on("click",function () {

                var index=$(this).data("index");

                if(index==productStyleDiv)
                {
                    var addStyleImage=$(".addStyleImage"+index);
                    addStyleImage.find("img").after($(["<div class=\"up-btn del-model-btn\">",
                        "                                                            <a href=\"javascript:void(0);\" class=\"del-p-style\" data-index='"+index+"'>",
                        "                                                                <i class=\"icon-bin\"></i>",
                        "                                                            </a>",
                        "                                                        </div>"].join("")));
                    var stylename=$("#styleName"+index).val() =="" ? "款式名" :$("#styleName"+index).val();
                    addStyleImage.find("strong").html(stylename);

                    var p_style_item= $(["                                                <div class=\"col-sm-4 col-lg-2 product-style-p"+productStyleDiv+"\" data-index='"+productStyleDiv+"'>",
                        addStyleImage.html(),
                        "</div>"].join(""));
                    $(".productDiagram").prepend(p_style_item);

                    product_stack.productStyle_stack(productStyleDiv);
                    product_stack.delete_p_style();

                    productStyleDiv++;
                }

                $(".style-div"+index).css("display","none");
            });

        });


        $(".del-Product").on("click",function () {
            //var choice =$(".product-choice");
            var choice=$("input[name='product-choice']:checked");
            var choiceId={};
            if (choice.length == 0) {
                alert("请至少选择一项!");
                return false;
            }
            if (confirm("确定删除所选商品?")) {
                var r = 0;
                for (var i = 0; i < choice.length; i++) {
                    if (choice[i].checked) {
                        choiceId["productIds[" + r + "]"] = choice[i].value;
                        r++;
                    }
                }

                if (choice.length > 0) {
                    $.ajax({
                        type: "get",
                        url: hostUrl + "delProduct",
                        data: choiceId,
                        success: function (result) {
                            if (result == "delete product success") {
                                window.location.href = "/qualifyProduct";
                            }
                        }
                    });

                }
            }
        });

        productNodes($);

        $("#product_sub").on("click",function () {
            var isError=false;
            if(product_info_check.check_product_field($("input[name=productName]").val()))
            {
                isError=true;
                helpblock("商品名不能为空！",0);
            }
            if(product_info_check.check_product_field_length($("input[name=productName]").val(),50))
            {
                isError=true;
                helpblock("商品名不能超过50位！",0);
            }
            if($(".styleName").length ==0 || product_info_check.check_product_field($(".styleName").val(),50))
            {
                isError=true;
                $.alert("提示","<span>至少一种样式，且样式名不能为空！</span>");
                $(".btn-primary").on("click",function () {
                    $(".close").trigger("click");
                });
            }
            if(product_info_check.check_product_field_length($(".styleName").val()))
            {
                isError=true;
                $.alert("提示","<span>样式名不能超过50位！</span>");
                $(".btn-primary").on("click",function () {
                    $(".close").trigger("click");
                });
            }
            if(product_info_check.check_product_field($("select[name=sellerId]").val()))
            {
                isError=true;
                $.alert("提示","<span>你还没有设置品牌，请先设置品牌再添加商品！</span>");
                $(".btn-primary").on("click",function () {
                    window.location.href=hostUrl+"qualifyBrand";
                });
            }
            if(!isError)
            {
                this.disabled=true;
                $("form").submit();
            }
        });

        product_stack.delete_introduce();
    });

})(jQuery);
var product_stack
function productNodes($) {
    product_stack={
        productStyle_stack:function (id) {
            $(".product-style-item"+id).on("click",function () {
                var index=$(this).data("index");
                if($("input[name='productType']:checked").val() == 0 && $("input[name='productType']:checked").val()!=p_type)
                {
                    var model=["                                    <div class=\"row\">",
                        "                                        <div class=\"form-group r-info\">",
                        "                                            <label>模型:</label>",
                        "                                            <div class=\"row\">",
                        "                                                <div data-index='"+index+"' class=\"col-sm-4 col-lg-2 addproductModel"+index+"\">",
                        "                                                    <div>",
                        "                                                        <div class=\"bg-primary-800 demo-color\">",
                        "                                                            <img src=\""+hostUrl+"resources/images/brand.jpg\"/>",
                        "                                                        </div>",
                        "                                                        <div class=\"p-15\">",
                        "                                                            <div class=\"media-body\">",
                        "                                                                <span>",
                        "                                                                    <strong>添加模型</strong>",
                        "                                                                </span>",
                        "                                                            </div>",
                        "                                                        </div>",
                        "                                                    </div>",
                        "                                                </div>",
                        "                                            </div>",
                        "                                        </div>",
                        "                                    </div>"];
                    $(".thumbnail"+index).before($(model.join("")));
                    product_stack.addmodel(index);
                }
                var height=$(".product-form").css("height");
                $(".style-div"+index).css("display","block");
                $(".style-div"+index).css("height",height);
            });
        }
        ,
        addmodel:function (id) {
            $(".addproductModel"+id).on("click",function () {
                var token = $("meta[name='_csrf']").attr("content");
                var index=$(this).data("index");
                var models=[];
                $.ajax({
                    url:hostUrl+"qualifyModelToAjax",
                    type:"POST",
                    data:{"_csrf":token},
                    success:function (msg) {
                        var modelitem=eval("("+msg+")");
                        var size=modelitem["size"];
                        models.push("<div class=\"row\">");
                        for(var i=0;i<size;i++)
                        {
                            var modelImg=modelitem["model"+i].modelImages;
                            modelImg=modelImg.split(";")[0];
                            modelImg=modelImg.split(",")[1];
                            models.push("<div class=\"models\">");
                            models.push("<div class=\"col-sm-4 col-lg-2\" style='min-width: 250px;margin-right: 5px'>");
                            models.push("<div>");
                            models.push("<div class=\"bg-primary-800 demo-color\">");
                            models.push("<img src='http://cdn-test.dodojia.cn/"+modelImg+"?x-oss-process=style/product-normal'/>");
                            models.push("<label>"+modelitem["model"+i].modelName+"</label>");
                            models.push("<input type='hidden' value='"+modelitem["model"+i].modelId+"'/>");
                            models.push("</div>");
                            models.push("<div class=\"p-15\">");
                            models.push("<div class=\"media-body\">");
                            models.push("<span>");
                            models.push("<strong>"+modelitem["model"+i].modelName+"</strong>");
                            models.push("</span>");
                            models.push("</div>");
                            models.push("</div>");
                            models.push("</div>");
                            models.push("</div>");
                            models.push("</div>");
                        }
                        models.push("</div>");

                        var modelList=$(["<div>",
                            "<form class=\"navbar-form navbar-left\" >",
                            "               <span class='help-block'>双击图片添加模型</span>",
                            "                <div class=\"form-group\">",
                            "                    <input type=\"text\" class=\"form-control\" placeholder=\"搜索\"  id=\"qualifyContent\">",
                            "                </div>",
                            "                <button type=\"button\" id='btn_model'  class=\"btn btn-default\">搜索</button>",
                            "            </form>",
                            "<div class=\"collapse navbar-collapse\" id=\"example-navbar-collapse\">",
                            "                <ul class=\"nav navbar-nav\">",
                            "                    <li class=\"dropdown\">",
                            "                        <select>"+$(".style-item").html()+"</select>",
                            "                    </li>",
                            "                    <li class=\"dropdown\">",
                            "                        <select>"+$(".type-item").html()+"</select>",
                            "                    </li>",
                            "                </ul>",
                            "            </div>",
                            "<div   id='modelList'>",
                            models.join(""),
                            "</div>",
                            "</div>"].join(""));
                        $.alert("模型列表",modelList);
                        queryModel($);

                        $(".models").on("dblclick",function () {
                            $(this.firstElementChild.firstElementChild.firstElementChild.lastElementChild).attr("name","productStyleList["+index+"].modelId");
                            $(".addproductModel"+index).removeAttr("style");
                            $(".addproductModel"+index).html(this.innerHTML);
                            $(".close").trigger("click");
                        });

                        $(".btn-primary").on("click",function () {
                           $(".close").trigger("click");
                        });
                    }
                });

            });
        }
        ,
        styleSub:function (id) {
            $(".style-sub"+id).on("click",function () {
                $(".style-div" + id).css("display", "none");
            });
        }
        ,
        addStyleImg:function (id) {
            var index=id;
            $(".addStyleImage"+id).on("click",function () {
                var styleDiv=this;
                $(styleDiv.parentElement).find("input[type=file]").trigger("click");
                $(styleDiv.parentElement).find("input[type=file]").on("change",function () {
                    checkImgFile(this);
                    if(this.value != "") {
                        var file = this.files[0];
                        var src = window.URL.createObjectURL(file);
                        $(styleDiv.firstElementChild.firstElementChild).html("<img src='" + src + "'/>");
                        $(".product-style-p"+index).html($(styleDiv).html());
                        product_stack.productStyle_stack(index);
                    }
                })
            });
        }
        ,
        delete_p_style:function () {
            $(".del-p-style").on("click",function () {
                var index=$(this).attr("data-index");
                $(".style-div"+index).remove();
                $(this.parentElement.parentElement.parentElement.parentElement).remove();
            })
        }
        ,
        setDefault:function () {
            $(".setDefault").on("click",function () {
                $(".setDefault").html("设置为默认款式");
                var index=$(this).data("index");
                $("#default").attr("name","productStyleList["+index+"].Default");
                this.innerHTML="默认款式";
            });
        }
        ,
        delete_introduce:function () {
            $(".del-p-introduce").on("click",function () {
                $(this.parentElement.parentElement.parentElement.parentElement).remove();
            })
        }
    };
}

function queryModel($) {
    $("#btn_model").on("click",function () {
        var qualifyContent=$("#qualifyContent").val();
        var token = $("meta[name='_csrf']").attr("content");
        var models=[];
        $.ajax({
            url:hostUrl+"qualifyModelToAjax",
            type:"POST",
            data:{"_csrf":token,"qualifyContent":qualifyContent},
            success:function (msg) {
                var modelitem = eval("(" + msg + ")");
                var size = modelitem["size"];
                models.push("<div class=\"row\">");
                for (var i = 0; i < size; i++) {
                    var modelImg = modelitem["model" + i].modelImages;
                    modelImg = modelImg.split(";")[0];
                    modelImg = modelImg.split(",")[1];
                    models.push("<div class=\"models\">");
                    models.push("<div class=\"col-sm-4 col-lg-2\" style='min-width: 250px;margin-right: 5px'>");
                    models.push("<div>");
                    models.push("<div class=\"bg-primary-800 demo-color\">");
                    models.push("<img src='http://cdn-test.dodojia.cn/"+modelImg+"?x-oss-process=style/product-normal'/>");
                    models.push("<label>"+modelitem["model"+i].modelName+"</label>");
                    models.push("<input type='hidden' value='"+modelitem["model"+i].modelId+"'/>");
                    models.push("</div>");
                    models.push("<div class=\"p-15\">");
                    models.push("<div class=\"media-body\">");
                    models.push("<span>");
                    models.push("<strong>"+modelitem["model"+i].modelName+"</strong>");
                    models.push("</span>");
                    models.push("</div>");
                    models.push("</div>");
                    models.push("</div>");
                    models.push("</div>");
                    models.push("</div>");
                }
                models.push("</div>");
                $("#modelList").html(models.join(""));
            }
        });
    })
}

var product_info_check={
    check_product_field:function (value) {
        if(value == "" || value == null)
        {
            return true;
        }
        return false;
    },
    check_product_field_length:function (value,length) {
        if(value.length > length)
        {
            return true;
        }
        return false;
    }
}
