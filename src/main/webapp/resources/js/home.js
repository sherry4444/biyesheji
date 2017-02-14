

var hostUrl=window.location.protocol+"//"+window.location.host+"/";
;(function ($) {
    $(function () {
    	$(".has-ul").on("click",function(){
    		if($(this.parentElement).attr("class") == "active")
    		{	    		
    			$(this.parentElement).attr("class","");
    		}
    		else
    		{
	    		$(this.parentElement).attr("class","active");
    		}
    		$(this.parentElement).find("ul").fadeToggle();
    	});
		$(".sidebar-content").css("min-height",window.outerHeight);
		var contentHeight=$(".page-header-default").height();
		$(".r-panel-content").css("min-height",window.outerHeight-20-contentHeight+"px");
		if(window.location.host == "testapi.dodojia.cn")
		{
			hostUrl=hostUrl+window.location.pathname.substr(1,31)+"/";
		}



		$("#plan-page-num").on("change",function () {
			var currentPage=$(this).data("currentPage");

			var qualifyContent=$(this).data("querytool");
			var url=$(this).data("url");
			currentPage=typeof(currentPage) !="undefined"? currentPage:1;
			qualifyContent=typeof(qualifyContent) !="undefined"? qualifyContent:'';
			window.location.href=hostUrl+url+"?currentPage="+currentPage+"&qualifyContent="+qualifyContent+"&pageNumber="+this.value;
		});


        //caches a jQuery object containing the header element
        var header = $(".navigation");
        $(window).scroll(function() {
            var scroll = $(window).scrollTop();

            if (scroll >= 500) {
                header.removeClass('navigation').addClass("scrollNavigation");
            } else {
                header.removeClass("scrollNavigation").addClass('navigation');
            }
        });

    });

})(jQuery);
