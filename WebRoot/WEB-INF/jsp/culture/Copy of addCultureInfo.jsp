<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="/prev-tag" prefix="slp"%>
<!doctype html>
<html>
<head>
<%@include file="/WEB-INF/jsp/public/header.jspf"%>
</head>
<body>

	<%@include file="/WEB-INF/jsp/index/header.jsp"%>
	<%@include file="/WEB-INF/jsp/public/tools.jsp"%>

	<div class="am-cf admin-main">
		<%@include file="/WEB-INF/jsp/index/menu.jsp"%>

		<div class="admin-content">

			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<small>位置</small>：<small>公司管理中心</small>
					/<small>
					<a href="${pageContext.request.contextPath}/culture/cultureList">企业文化信息管理</a>
					</small>/<small>企业文化信息添加或修改</small>
				</div>
			</div>
			<div style="width: 60%;margin: 0 auto;">
				<form
					action="${pageContext.request.contextPath}/culture/addCultureInfoCommit"
					class="am-form" data-am-validator>
					<fieldset>
						
						<div class="am-form-group">
							<label for="title">名称：</label> <input type="text" id="cultureName" value="${info.cultureName }"
								name="cultureName" maxlength="20" placeholder="不超过20" required />
						</div>
						<div id="nameMessage" style="color: red;margin-bottom: 12px;"></div>

						<div class="am-form-group">
							<label for="content">内容：</label>
								<textarea id="cultureInfo" name="cultureInfo" rows="25"
								style="width: 100%; border: 1px">${info.cultureInfo}</textarea>
						</div>
						<div id="nameMessage" style="color: red;margin-bottom: 12px;"></div>

						<div class="am-form-group">
							<label for="positionSalary">图片：</label>
							<input type="hidden" name="culturePic" id="culturePic" value="${info.culturePic}"/>
						
							<c:if test="${info.culturePic == null}">
								<img id="cultureImage" src="${info.culturePic}" height="150px" width="150px">
							</c:if>
							<c:if test="${info.culturePic != null}">
								<img id="cultureImage" src="${pageContext.request.contextPath}/photo?imgName=${info.culturePic}" height="50px" width="50px">
							</c:if>  
 							<input type="file" id="uploadImage" name="picFile" onchange="ajaxFileUpload()"/>
 							<input type="file" id="upFile" name="upFile" onchange="UploadPic('cultureImage','upFile');"/>
						</div>
						<input type="file" id="uploadF" name="uploadF" onchange="upload1();"/>
						<input type="hidden" name="id" value="${info.id}" />
						<button class="am-btn am-btn-secondary" type="button" id="submit">提交</button>
					</fieldset>
				</form>
			</div>
		</div>
		<form action="${pageContext.request.contextPath}/culture/cultureList" id="sx" method="post">
			<input type="hidden" name="name" value="${cultureInfo.cultureName}" />
			<input type="hidden" name="currentPage" value="${pageQueryUtil.currentPage}" />
		</form>
	</div>
	<%@include file="/WEB-INF/jsp/index/foot.jsp"%>
	
	<script type="text/javascript">
	$(document).ready(function() {
		//初始化xhEditor编辑器插件  
		$('#cultureInfo').xheditor(
						{
							tools : 'full',
							skin : 'default',
							html5Upload : false,
							upImgUrl : "${pageContext.request.contextPath}/file/xheditorFileUpload2Map",
							upImgExt : "jpg,jpeg,gif,bmp,png",
							onUpload : insertUpload
						});
		//xbhEditor编辑器图片上传回调函数  
		function insertUpload(data) {
			var imgSrc=data.toString();
			console.log(data);
			console.log(imgSrc);
		}
	});
        function upload1(){  
        	$.ajaxFileUpload({
        		url:"${pageContext.request.contextPath}/image/uploadify2String",  
    	        secureuri:false,                              
    	        fileElementId:'uploadF',                 
    	        dataType:'json',                           
    	        type:'post',
    	        success:function(data, status){            
    	        	 data=data.imgName;
    		            $("#culturePic").val(data);
    		            $("img[id='cultureImage']").attr("src", "${pageContext.request.contextPath}/photo?imgName="+data);
    		            console.log(data);
                    if(data.status==1){
                    }
    	        },  
    	        error:function(data, status, e){
    	        }
        		
        	}); 
        }
	function UploadPic(src,name){
		
		var count = 0, i;
		
		for (var i = 1; i < 4; i++) {
			! function(_i) {
				setTimeout(function() {
					console.log(new Date());
					console.log(_i);
				}, _i * 10000);
			}(i)
		}
		
    	$.ajaxFileUpload({
    		url:"${pageContext.request.contextPath}/image/uploadify",  
	        secureuri:false,                              
	        fileElementId:'upFile',                 
	        dataType:'text',                           
	        type:'post',
	        success:function(data, status){            
	        	 data = data.replace(/<pre.*?>/g, ''); 
		            data = data.replace(/<PRE.*?>/g, '');  
		            data = data.replace("<PRE>", '');  
		            data = data.replace("</PRE>", '');  
		            data = data.replace("<pre>", '');  
		            data = data.replace("</pre>", '');
		            $("#culturePic").val(data);
		            $("img[id='cultureImage']").attr("src", "${pageContext.request.contextPath}/photo?imgName="+data);
		            console.log(data);
                if(data.status==1){
                }
	        },  
	        error:function(data, status, e){
	        }
    		
    	});
         
    }
	function ajaxFileUpload(){  
	    
	    $.ajaxFileUpload({  
	        url:"${pageContext.request.contextPath}/file/ajaxFileUpload",  
	        secureuri:false,                           //是否启用安全提交,默认为false   
	        fileElementId:'uploadImage',               //文件选择框的id属性  
	        dataType:'text',                           //服务器返回的格式,可以是json或xml等  
	        success:function(data, status){            //服务器响应成功时的处理函数  
	            data = data.replace(/<pre.*?>/g, '');  //ajaxFileUpload会对服务器响应回来的text内容加上<pre style="....">text</pre>前后缀  
	            data = data.replace(/<PRE.*?>/g, '');  
	            data = data.replace("<PRE>", '');  
	            data = data.replace("</PRE>", '');  
	            data = data.replace("<pre>", '');  
	            data = data.replace("</pre>", '');     //本例中设定上传文件完毕后,服务端会返回给前台[0`filepath]  
	            if(data.substring(0, 1) == 0){         //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
	                $("img[id='cultureImage']").attr("src", data.substring(2));
	            	var src=$("img[id='cultureImage']").attr("src");
	                $("#culturePic").val(data.substring(2));
	                $('#result').html("图片上传成功<br/>");  
	            }else{  
	                $('#result').html('图片上传失败，请重试！！');  
	            }  
	        },  
	        error:function(data, status, e){
	            $('#result').html('图片上传失败，请重试！！');  
	        }  
	    });  
	}
		$(function() {
			$("#submit")
					.click(
							function() {
								var id = "${info.id}";
								var name = $("#cultureName").val();
								var content = $("#cultureInfo").val();
								var pic=$("#culturePic").val();
								$
										.ajax({
											type : 'post',
											async : false,
											dataType : 'json',
											data : {
												id : id,
												cultureName : name,
												cultureInfo : content,
												culturePic : pic
												
											},
											url : "${pageContext.request.contextPath}/culture/addCultureInfoCommit",
											success : function(data) {
												if (data.errorFlags) {
													$("#nameMessage").html("");
													$("#nameMessage").html(data.nameMessage);
												} else {
													amazeAlertSuccess(data.message);
													window
															.setTimeout(
																	function() {
																		if(id != null && id != ""){
																			$("#sx").submit();
																		} else {
																			window.location.href = "${pageContext.request.contextPath}/culture/cultureList";
																		}
																		
																	}, 1000);
												}
											},
											error : function(data) {
												$("#update-fail-alert").modal();
											}
										});
							});
		});
	</script>
</body>
</html>