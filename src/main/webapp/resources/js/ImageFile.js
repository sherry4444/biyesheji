/**
 * Created by server1 on 2016/12/7.
 */
var imgObj;
var imgFileSize;
var imgFileWidth;
var imgFileHeight;
var fileObj;
var allowSize=10240;
var allowWidth=1366;
var allowHeight=768;
var checkMsg;
function checkImgFile(obj) {
    fileObj=obj;
    imgObj=document.createElement("img");
    imgObj.src=obj.value;
    document.body.insertAdjacentElement("beforeend",imgObj);
    checkMsg=obj.parentElement.lastElementChild;
    imgFileSize=Math.round(fileObj.files[0].size/1024*100)/100;
    imgFileWidth=imgObj.offsetWidth;
    imgFileHeight=imgObj.offsetHeight;

    if(imgFileSize > allowSize)
    {
    	showMsg("文件大小不能超过"+allowSize+"KB");
    }
    else if (imgFileWidth > allowWidth && imgFileHeight > allowHeight) {
    	showMsg("文件尺寸不能超过"+allowWidth+"*"+allowHeight);
    }
    else
    {
        checkMsg.innerHTML="";
    }
}

function showMsg(msg)
{
	checkMsg.innerHTML=msg;
	fileObj.value="";
}

