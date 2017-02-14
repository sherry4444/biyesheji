/**
 * Created by server1 on 2016/11/22.
 */
;(function ($) {
    $(function () {
        btnUpload();
    });
})(jQuery);

function btnUpload() {
    $("#deleteImg").on("click",function () {
        $("#uploadImg").val(null);
        $("#previewImg").attr("src","");
    });

    $("#addImg").on("click",function () {
        $("#uploadImg").trigger("click");
    });

    $("#uploadImg").on("change",function () {
        checkImgFile(this);
        if(this.value != "")
        {
            var file=document.getElementById("uploadImg").files[0];
            var src=window.URL.createObjectURL(file);
            $("#previewImg").attr("src",src);
        }
    });
}