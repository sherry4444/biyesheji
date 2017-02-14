/**
 * Created by server1 on 2016/12/14.
 */
;(function ($) {
    $(function () {
        $(".updateBrand").on("click",function () {
            brandUpdatePlate($,"修改品牌","/updateSeller",$(this).find("img"));
        })
    });
});
