/**
 * Created with JetBrains WebStorm.
 * User: zhur
 * Date: 16-2-26
 * Time: 下午3:16
 * To change this template use File | Settings | File Templates.
 */
	window.sys = window.sys | "";
    function UploadPic(button,progress,src,name) {
    	var fileType = '*.jpg;*.gif;*.png;*.jpeg';
        UploadPicByFileType(button,progress,src,name,fileType);
    }
    
    function UploadPicByFileType(button,progress,src,name,fileType){
    	var buttonText="";
    	if($("#"+src).attr("src") == undefined || $("#"+src).attr("src") ==""){
    		buttonText="选择文件";
    	}else{
    		buttonText="修改文件";
    	}
    	console.log(window.sys);
    	console.log(window.sessionid);
    	
    	$("#"+button).uploadify({
            'swf':"../plugins/uploadify/uploadify.swf", //flash文件的相对路径
            'uploader':window.sys+'/image/uploadify2String;jsessionid='+window.sessionid,
            'fileObjName':'upFile', //设置上传文件名称,默认为Filedata
            'queueID':progress, //文件队列的ID，该ID与存放文件队列的div的ID一致
            'fileTypeDesc':fileType+'文件', //用来设置选择文件对话框中的提示文本
            'fileTypeExts':fileType, //设置可以选择的文件的类型
            'auto':true, //设置为true当选择文件后就直接上传了，为false需要点击上传按钮才上传
            'multi':false, //设置为true时可以上传多个文件
            'fileSizeLimit':'1MB',
            'queueSizeLimit':10, //上传文件的大小限制
            'buttonText':buttonText, //浏览按钮的文本，默认值：BROWSE
            'progressData':'percentage', //上传队列显示的数据类型，percentage是百分比，speed是上传速度
            //回调函数
            'onUploadError':function (errorObj, file, errorCode, errorMsg, errorString) {
                if (errorObj.type === "File Size") {
                    alert("文件最大为1M");
                    $("#"+button).uploadifyClearQueue();
                }
            },
            'onSelectError':function(file, errorCode, errorMsg){  //返回一个错误，选择文件的时候触发  
	           switch(errorCode) { 
	               case -100: 
	                   alert("上传的文件数量已经超出系统限制的"+$('#'+button).uploadify('settings','queueSizeLimit')+"个文件！"); 
	                   break; 
	               case -110: 
	                   alert("文件 ["+file.name+"] 大小超出系统限制的"+$('#'+button).uploadify('settings','fileSizeLimit')+"大小！"); 
	                   break; 
	               case -120: 
	                   alert("文件 ["+file.name+"] 大小异常！"); 
	                   break; 
	               case -130: 
	                   alert("文件 ["+file.name+"] 类型不正确！"); 
	                   break; 
	           } 
	        },
	         'onFallback':function(){             //检测FLASH失败调用  
	            alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。"); 
	        }, 
            'onUploadSuccess' : function(file, data, response) {
                var json = jQuery.parseJSON(data);
                console.log(json);
                if(json.status==1){
                    $("#"+src).attr("src", json.url);
                   // $(":input[name="+name+"]").val(json.imgName).keypress().blur();
                    $(":input[name="+name+"]").val(json.imgName);
                }else{
                    alert("上传失败，请重试");
                }
            }
        });
    }