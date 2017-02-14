/* ------------------------------------------------------------------------------
*
*  # Login page
*
*  Specific JS code additions for login and registration pages
*
*  Version: 1.0
*  Latest update: Aug 1, 2015
*
* ---------------------------------------------------------------------------- */

$(function() {

	// Style checkboxes and radios
	$('.styled').uniform();

});

function checkCapsLock (event){
    event = event||window.event;
    //var keycode = event.charCode;
    var keycode = event.keyCode ? event.keyCode: (event.which ? event.which : event.charCode);
    var realkey = String.fromCharCode(keycode);
    if(/^[A-Z]+$/.test(realkey)){
        document.getElementById("capsLockTip").style.display = "inline";
    }else{
        document.getElementById("capsLockTip").style.display = "none";
    }
}