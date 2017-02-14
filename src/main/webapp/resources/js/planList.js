/**
 * Created by server2 on 2016/11/28.
 */


/**
 * Created by server1 on 2016/11/23.
 */
var groupId;
;(function ($) {
    $(function () {
        $.alert=function (title,msg) {
            var dialog=$(["<div class=\"modal fade\" id=\"myModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">",
                "	<div class=\"modal-dialog modal-dialog modal-lg\">",
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
               /* "				<button type=\"button\" class=\"btn btn-primary\">",
                "					确定",
                "				</button>",*/
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

        $(".custom").on("click",function () {
            var node=this;
            var token = $("meta[name='_csrf']").attr("content");
            var msg=$([" <form class=\"form-horizontal \" role=\"form\" method=\"get\" >",
                "        <div class=\"form-group \">",
                "             <input type=\"text\" class=\"form-control  account\" id=\"account\" name=\"account\" placeholder=\"请输入你要复制的账号\">",
                "        </div>",
                "        <div class=\"form-group \">",
                "            <button type=\"button\" class=\"btn btn-default\"  data-dismiss=\"modal\">submit</button>",
                "        </div>",
                "   </form>"].join(""));
            $.alert("copy",msg);
            msg.find(".btn-default").on("click",function () {
                copyaccount(node);
            })
        });

        $('.dropdown-toggle').dropdown();

        $(window).scroll(function(){
            if($(window).scrollTop()>400){    //垂直滚动条钓offset 大于90时。
                $("#BacktoTop").css({
                    "visibility":"visible",       //修改相关div样式
                });
            }else{
                $("#BacktoTop").css({
                    "visibility":"hidden",     //修改相关div样式
                });
            }
        });


        window.onload=function(){

            window.onresize=function () {
                tagGroupResize();
                $(".group-body").css("width",$(".row-groups-i").width()-100+"px");
            };
            tagGroupResize();

            function tagGroupResize() {
                if($(window).width()>768)
                {
                    $(".col-md-4").css("width","49%");
                    $(".plan-img img").css("width","500px");
                    $(".plan-img img").css("height","300px");
                }
                else
                {
                    $(".col-md-4").removeAttr("style");
                    $(".plan-img img").css("width","100%");
                    $(".plan-img").css("width","100%");
                    $(".plan-l").css("width","100%");
                }
            }
            function adsorption(){
                var headerWrap=document.getElementById('header-wrap');
                var scrollTop=0;
                window.onscroll=function(){
                    scrollTop=document.body.scrollTop||document.documentElement.scrollTop;
                    if(headerWrap != null)
                    {
                        if(scrollTop>100){
                            headerWrap.className='fixed';
                            $("#look1").css({
                                "width":"100%",       //修改相关div样式
                            });

                        }else{
                            headerWrap.className='header-wrap';
                            $("#look1").css({
                                "width":"400px",       //修改相关div样式

                            });
                        }
                    }
                }
            }
            adsorption();
        }

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

/*复制到账号*/
function copyaccount(node) {
    createXMLHttpResquest();
    console.log(node);
    var planId = $(node.parentElement.parentElement).attr("planId");
    var userId = $(node.parentElement.parentElement).attr("userId");
    var account = $(node).attr("account");
    //alert(planId+"======"+userId+"====="+account);
    if(account == null){
         account = document.getElementsByName("account")[0].value;
    }
    //alert(planId+"======"+userId+"====="+account);
    var url = hostUrl+"/planList/copyaccount?account="+account+"&planId="+planId+"&userId="+userId;
    xmlHttp.open("GET",url,true);
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.send(null);
    function handleStateChange() {
        console.log(xmlHttp.responseText);
        if(xmlHttp.readyState == 4){
            alert(xmlHttp.responseText);
        }
    }
}

