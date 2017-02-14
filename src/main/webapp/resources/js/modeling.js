var token = $("meta[name='_csrf']").attr("content");
var dialog;
;(function ($) {
    $(function () {
        $.alert = function (title, msg) {
            dialog = $(["<div class=\"modal fade\" id=\"myModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">",
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
            dialog.on("hidden.bs.modal", function () {
                dialog.remove();
                $(".modal-backdrop").remove();
            });
        };


        $('.taginput').change(function(){
            var t = $("#tagNames");
            var SPEARTER= ",";
            var name =$(this).children('option:selected').val();
            //$("#tagNames").attr('value', name);

            if (t.val() == "") {
                t.val(name);
            } else {
                t.val(t.val() + SPEARTER + name);
            }
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

        $(".introduceImg").on("click",function () {

            var imgurl = $(this).attr("imgurl");
            var imgpath = $(this).attr("imgpath");
            var token = $("meta[name='_csrf']").attr("content");
            var msg= ["      <img src=\""+imgurl+imgpath+"\" class=\"img-thumbnail col-md-12\"/>"
            ].join("");
            $.alert("浏览", msg);
        });


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
                $(this).attr("src", imgurl + ttt[1] + imgsufix);
                $(this).attr("alt", ttt[0]);
                $(this).attr("title", ttt[0]);
            });
        });

            imgpathurl.on({
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
                        html +=  "      <img src=\"" + imgurl + ttt[1] + "\"  class=\"img-thumbnail col-md-12\" /> <br> <p class=\"text-center \">"+ttt[0]+"</p> ";
                    }
                    var msg = $([html
                    ].join(""));
                    $.alert("浏览", msg);
                }
            });

        $("#select").change(function () {
            var num = $("#select").val();
            var flag = $(this).attr("flag");
            //alert(flag+num);
            location.href = "/modeling?flag=" + flag + "&num=" + num;
        });

        $(".veto").on("click", function () {
            var node = this;
            var modelId = $(this).attr("cn");
            //alert("companyName:"+companyName);
            var token = $("meta[name='_csrf']").attr("content");
            var msg = $([" <form class=\"form-horizontal vetoform\" role=\"form\" id=\"form1\" method=\"post\" action=\'"+hostUrl+"/modeling?_csrf=" + token + "\'>",
                "        <div class=\"form-group\">",
                "            <label  class=\"col-sm-2 control-label\">模型ID</label>",
                "            <div class=\"col-sm-10\">",
                "               <textarea type=\"text\" rows=\"1\" style=\"border:none;\" value=\"comany\" name=\"modelId\">" + modelId + "</textarea>",
                "            </div>",
                "        </div>",
                "        <div class=\"form-group\">",
                "            <label  class=\"col-sm-2 control-label\">打回原因</label>",
                "            <div class=\"col-sm-10\">",
                "                <select class=\"form-control\" name=\"cerCausepre\">",
                "                    <option value=\"工商信息有误\">信息有误</option>",
                "                    <option value=\"工商信息有\">信息有</option>",
                "                    <option value=\"工商信息\">信息</option>",
                "                    <option value=\"工商信\">信</option>",
                "                </select>",
                "            </div>",
                "        </div>",
                "        <div class=\"form-group\">",
                "            <div class=\"col-sm-offset-2 col-sm-10\">",
                "                <textarea class=\"form-control\" rows=\"4\" name=\"cerCause\"></textarea>",
                "            </div>",
                "        </div>",
                "    </form>"].join(""));
            $.alert("打回：" + modelId + "", msg);
            dialog.find(".btn-primary").on("click", function () {
                veto(node);
            })
        });


        $(".finishmaking").on("click", function () {
            var modelId = $(this).attr("modelId");
            var modelName = $(this).attr("modelName");
            var description = $(this).attr("description");

            $('#submodelId').text(modelId);
            $('#submodelName').attr("value",modelName);
            $('#subdescription').attr("value",description);

            $("#myModal").modal('show');
            //var styleList = $(this).attr("styleList");
            //alert(styleList);
            /*var token = $("meta[name='_csrf']").attr("content");
            var msg = $(["<div class=\"md-col\">",
                "				<dl>",
                "					<dt>",
                "						<span>风格</span>",
                "					</dt>",
                "					<dd>",
                "						<select  name=\"styleId\">",
                "							<option value=\"0\">全部</option>",
                "							<option data-th-each=\"style,styleIndex:" + styleList + "\" data-th-value=\"" + styleList.styleId + "\" data-th-text=\"" + styleList.styleName + "\"></option>",
                "						</select>",
                "					</dd>",
                "				</dl>",
                "				<dl>",
                "					<dt>",
                "						<span>种类</span>",
                "					</dt>",
                "					<dd>",
                "						<select name=\"typeId\">",
                "							<option value=\"0\">全部</option>",
                "							<option data-th-each=\"type,typeIndex:${typeList}\" data-th-value=\"${type.typeId}\" data-th-text=\"${type.typeName}\">地面</option>",
                "						</select>",
                "					</dd>",
                "				</dl>",
                "			</div>"].join(""));
            $.alert("打回：" + modelId + "", msg);
            dialog.find(".btn-primary").on("click", function () {
                veto(node);
            })*/
        });

        $(".edit").on("click",function () {
            cleanedit();
            var modelId = $(this).attr("modelId");
            var modelName = $(this).attr("modelName");
            var description = $(this).attr("description");
            var styleName = $(this).attr("styleName");
            var typeName = $(this).attr("typeName");
            var attachType = $(this).attr("attachType");
            var rotateType = $(this).attr("rotateType");
            var scaleX = $(this).attr("scaleX");
            var scaleY = $(this).attr("scaleY");
            var scaleZ = $(this).attr("scaleZ");
            var tagNames = $(this).attr("tagNames");
            //console.log(scaleX+"+"+scaleY+"+"+scaleZ+"+"+typeName+"+"+styleName+"+"+attachType+"+"+tagNames);
            $('#modelIdedit').text(modelId);
            $('#modelNameedit').attr("value",modelName);
            $('#descriptionedit').attr("value",description);
            $('#styleIdedit').find("option:contains("+styleName+")").attr("selected", true);
            $('#typeIdedit').find("option:contains("+typeName+")").attr("selected", true);
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
            $('#modelIdedit').text("");
            $('#modelNameedit').attr("value","");
            $('#descriptionedit').attr("value","");
            $('#styleIdedit').find('option:selected').attr('selected', false);
            $('#typeIdedit').find('option:selected').attr('selected', false);
            $('#attachTypeedit').find('option:selected').attr('selected', false);
            $('#rotateTypeedit').find('option:selected').attr('selected', false);
            $('#scaleXedit').find('option:selected').attr('selected', false);
            $('#scaleYedit').find('option:selected').attr('selected', false);
            $('#scaleZedit').find('option:selected').attr('selected', false);
            $('#tagsinputedit').attr("value","");
        };

        $("#tagsinput").select2({
            placeholder: "Click here!",
            tags: true,
            tokenSeparators: [',', ' '],
            maximumSelectionLength: 3
        });

        $("#tagsinputedit").select2({
            tags: true,
            tokenSeparators: [',', ' '],
            maximumSelectionLength: 3
        });

    });
})(jQuery);


var xmlHttp;

function createXMLHttpResquest() {
    if (window.ActiveXObject) {
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        xmlHttp = new XMLHttpRequest();
    }
}


function startmaking(node) {
    var r = confirm("确认开始制作？");
    if (r) {
        createXMLHttpResquest();
        var modelId = $(node).attr("modelId");
        //alert(certificationId);
        var url = hostUrl+"/modeling/startmaking?modelId=" + modelId;
        xmlHttp.open("GET", url, true);
        xmlHttp.onreadystatechange = handleStateChange;
        xmlHttp.send(null);
        function handleStateChange() {
            if (xmlHttp.readyState == 4) {
                $(node).attr({"onclick": "cancel(this)"});
                $(node).text('取消开始制作');
                alert(xmlHttp.responseText);
            }
        }

        return true;
    }
    else {
        return false;
    }

}


function veto(node) {
    var modelId = document.getElementsByName("modelId")[0].value;
    var cerCausepre = document.getElementsByName("cerCausepre")[0].value;
    var cerCause = document.getElementsByName("cerCause")[0].value;
    var token = $("meta[name='_csrf']").attr("content");
    //alert(companyName+"+"+cerCausepre+"+"+ cerCause);
    createXMLHttpResquest();
    $.ajax({
        cache: true,
        type: "POST",
        url: hostUrl+"/modeling/veto?_csrf=" + token,
        // data:$('#form1').serialize(),// 你的formid
        data: {modelId: modelId, cerCausepre: cerCausepre, cerCause: cerCause},
        async: false,
        error: function (request) {
            alert("Connection error");
        },
        success: function (data) {
            alert(data);
            $(node).attr({"onclick": "null"});//会全部都禁用，要改
            $(node).text('已打回');
        }
    });
}

function cancel(node) {
    var r = confirm("确认取消开始制作？");
    if (r) {
        createXMLHttpResquest();
        var modelId = $(node).attr("modelId");
        //alert(certificationId);
        var url = hostUrl+"modeling/cancel?modelId=" + modelId;
        xmlHttp.open("GET", url, true);
        xmlHttp.onreadystatechange = handleStateChange;
        xmlHttp.send(null);
        function handleStateChange() {
            if (xmlHttp.readyState == 4) {
                $(node).attr({"onclick": "startmaking(this)"});
                $(node).text('开始制作');
                alert(xmlHttp.responseText);
            }
        }

        return true;
    }
    else {
        return false;
    }
}


//模糊搜索
function titlesearch(node) {
    var title = document.getElementById("title").value;
    var flag = $(node).attr("flag");
    var num = $(node).attr("num");
    //alert(title+flag+num);
    location.href = "/modeling?flag=" + flag + "&title=" + title + "&num=" + num;
}

// function fillin() {
//     var form = new FormData(document.getElementById("fillin"));
//     $('#fillin').ajaxSubmit({
//         type: "POST",
//         url: hostUrl+"/modeling/finishmaking?_csrf=" + token,
//         data: form.formSerialize,
//         success: function (data) {
//             alert(data);
//             //setTimeout("location.reload()",1000);//页面刷新
//         },
//         error: function (data) {
//             alert(data);
//         }
//
//     });
// }


//全选 待制作
function selectAll() {
    if ($("#SelectAll").is(":checked")) {
        $(":checkbox").prop("checked", true);//所有选择框都选中
    } else {
        $(":checkbox").prop("checked", false);
    }
}

//全选 待制作
function selectAll2() {
    if ($("#SelectAll2").is(":checked")) {
        $(":checkbox").prop("checked", true);//所有选择框都选中
    } else {
        $(":checkbox").prop("checked", false);
    }
}

//全选 待制作
function selectAll3() {
    if ($("#SelectAll3").is(":checked")) {
        $(":checkbox").prop("checked", true);//所有选择框都选中
    } else {
        $(":checkbox").prop("checked", false);
    }
}

//单条下载
function batchDown(node) {
    if (confirm("确定下载该模型图片?")) {
        var filename = $(node).attr("filename");
        var checkedList = $(node).attr("imgpath");

        var temp = document.createElement("form");
        temp.action = hostUrl+"/modeling/zipDown?_csrf=" + token;
        temp.method = "post";
        temp.style.display = "none";
        var opt = document.createElement("input");
        opt.name = "modelIds";
        opt.value = checkedList;
        temp.appendChild(opt);
        // var file_name = document.createElement("input");
        // file_name.name = "filename";
        // file_name.value = filename;
        // temp.appendChild(file_name);
        document.body.appendChild(temp);
        temp.submit();
    }
}

//打包下载
function zipDown() {
    //判断至少写了一项
    var checkedNum = $("input[name='subcheck']:checked");
    var token = $("meta[name='_csrf']").attr("content");
    if (checkedNum.length == 0) {
        alert("请至少选择一项!");
        return false;
    }
    if (confirm("确定打包下载所选项目?")) {
        var checkedList = [];
        checkedNum.each(function () {
            checkedList.push($(this).val());
        });
        var temp = document.createElement("form");
        temp.action = hostUrl+"modeling/zipDown?_csrf=" + token;
        temp.method = "post";
        temp.style.display = "none";
        var opt = document.createElement("input");
        opt.name = "modelIds";
        opt.value = checkedList.toString();
        temp.appendChild(opt);
        document.body.appendChild(temp);
        temp.submit();

    }
}

function show(node){
    var modelid3 = $(node).attr("modelid3");
    $('#modelid3').text(modelid3);
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
    formData.append("modelId",document.getElementById("modelid3").innerText);
    formData.append("productStatus",statusid);
    $.ajax({
        url: hostUrl+"/modeling/ProductStatus?_csrf=" + token ,
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

function addtag(node) {
    var name = $(node).val();
    $('input').tagsinput('add', 'name');
}


function doUpload() {
    var formData = new FormData();
    formData.append("modelId",document.getElementById("submodelId").innerHTML);
    formData.append("modelName",document.getElementById("submodelName").value);
    formData.append("description",document.getElementById("subdescription").value);
    formData.append("styleId",document.getElementById("styleId").value);
    formData.append("typeId",document.getElementById("typeId").value);
    formData.append("attachType",document.getElementById("attachType").value);
    formData.append("rotateType",document.getElementById("rotateType").value);
    formData.append("scaleX",document.getElementById("scaleX").value);
    formData.append("scaleY",document.getElementById("scaleY").value);
    formData.append("scaleZ",document.getElementById("scaleZ").value);
    formData.append("tagNames",$('#tagsinput').val());
    formData.append("ImgFile",$('#ImgFile')[0].files[0]);
    formData.append("uploadfile",$('#uploadfile')[0].files[0]);
    //console.log(formData);
    $.ajax({
        url: hostUrl+"/modeling/finish?_csrf=" + token ,
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

function doEdit() {
    var formData = new FormData();
    formData.append("modelId",document.getElementById("modelIdedit").innerHTML);
    formData.append("modelName",document.getElementById("modelNameedit").value);
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
    formData.append("uploadfile",$('#uploadfileedit')[0].files[0]);
    //console.log(formData);
    $.ajax({
        url: hostUrl+"/modeling/edit?_csrf=" + token ,
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


