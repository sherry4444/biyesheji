var imgItems;
var imgPale;
var imgIndex;
var imgLong;
;(function ($) {
    $(function () {
    	imgItems=window.setInterval(paleImg,5000);
    	imgLong=$("#imgItem li").length;
    });
    
    function paleImg()
    {
    	
    	$(".show-banner-imgs img").fadeOut(250,changeImg);
    	$(".show-banner-imgs img").queue(function(){
    		$(this).dequeue();
    	})
    }
    function changeImg()
    {
    	imgIndex=$("#imgItem li").index($(".show-banner-imgs"));
    	
    	$("#imgItem li").get(imgIndex).className="";
    	$(this).fadeIn("fast");
    	if(imgIndex < length-1)
    	{
    		$("#imgItem li").get(imgIndex+1).className="show-banner-imgs";
    	}
    	else
    	{
    		$("#imgItem li").get(0).className="show-banner-imgs";
    	}
    }
  })(jQuery);