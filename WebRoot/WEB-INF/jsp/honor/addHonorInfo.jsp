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
		<!-- sidebar start -->
		<%@include file="/WEB-INF/jsp/index/menu.jsp"%>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">

			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<small>位置</small>：<small>荣誉管理中心</small>
					/<small>
					<a href="${pageContext.request.contextPath}/honor/honorList">荣誉信息管理</a>
					</small>/<small>荣誉信息添加或修改</small>
				</div>
			</div>
			<div style="width: 60%;margin: 0 auto;">
				<form
					action="${pageContext.request.contextPath}/honor/addHonorInfoCommit"
					class="am-form" data-am-validator>
					<fieldset>
						<!-- <legend>JS 表单验证</legend> -->
						
						<!-- id为title和content这两个字段时候，  $("#title").val() 这样取值为空-->
						<div class="am-form-group">
							<label for="title">标题：</label> <input type="text" id="titles" value="${info.title }"
								name="title" minlength="2" placeholder="输入标题（至少 3 个字符）" required />
						</div>
						<div id="nameMessage" style="color: red;margin-bottom: 12px;"></div>

						<div class="am-form-group">
							<label for="content">内容：</label> <textarea id="contents" name=content rows="25"
								style="width: 100%; border: 1px">${info.content}</textarea>
						</div>
						<div id="nameMessage" style="color: red;margin-bottom: 12px;"></div>

						<div class="am-form-group">
							<label for="positionSalary">图片：</label>
							<input type="hidden" name="pic" id="pic" value="${info.pic}"/>
						
							<c:if test="${info.pic != null}">
								<img id="honorImage" src="${info.pic}" height="50px" width="50px">
							</c:if>  
							<c:if test="${info.pic == null}">
								<img id="honorImage" src="${info.pic}" height="50px" width="50px">
							</c:if>  
 							<input type="file" id="uploadImage" name="picFile" onchange="ajaxFileUpload()"/>
						</div>

						<div class="am-form-group">
							<label for="createTime">发布时间：</label> <input type="text"
								id="createTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								name="createTime" value="<fmt:formatDate value="${info.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" required />
						</div>
						<div id="createTimeMessage" style="color: red;margin-bottom: 12px;"></div>
						
						<input type="hidden" name="id" value="${info.id}" />
						<button class="am-btn am-btn-secondary" type="button" id="submit">提交</button>
					</fieldset>
				</form>
			</div>
		</div>
		<!-- content end -->
		<form action="${pageContext.request.contextPath}/honor/honorList" id="sx" method="post">
			<input type="hidden" name="name" value="${employeeInfo.name}" />
			<input type="hidden" name="gender" value="${employeeInfo.gender}" />
			<input type="hidden" name="currentPage" value="${pageQueryUtil.currentPage}" />
		</form>
	</div>
	<%@include file="/WEB-INF/jsp/index/foot.jsp"%>
	<script src="${pageContext.request.contextPath}/plugins/ajaxfileupload.js"></script>
	<script src="${pageContext.request.contextPath}/plugins/xheditor/xheditor-1.2.2.min.js"></script>
	<script type="text/javascript">
	
	$(document)
	.ready(
			function() {
				//初始化xhEditor编辑器插件  
				$('#contents')
						.xheditor(
								{
									tools : 'full',
									skin : 'default',
									html5Upload : false,
									upImgUrl : "${pageContext.request.contextPath}/file/xheditorFileUpload",
									upImgExt : "jpg,jpeg,gif,bmp,png",
									onUpload : insertUpload
								});
				//xbhEditor编辑器图片上传回调函数  
				function insertUpload(msg) {
					var _msg = msg.toString();					
					$('#contents').append(
							"<img src='"+_msg+"' id='img' width='300px' height='200px'/>");

				}
				//处理服务器返回到回调函数的字符串内容,格式是JSON的数据格式.  
				function Substring(s) {
					return s.substring(s.substring(0,
							s.lastIndexOf("/")).lastIndexOf("/"),
							s.length);
				}
			});
	function ajaxFileUpload(){  
	    
	    $.ajaxFileUpload({  
	        //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)  
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
	                $("img[id='honorImage']").attr("src", data.substring(2));
	               	var src=$("#honorImage").attr("src");
	                document.getElementById("pic").value=src;
	                $('#result').html("图片上传成功<br/>");  
	            }else{  
	                $('#result').html('图片上传失败，请重试！！');  
	            }  
	        },  
	        error:function(data, status, e){ //服务器响应失败时的处理函数  
	            $('#result').html('图片上传失败，请重试！！');  
	        }  
	    });  
	}
		$(function() {
			$("#submit")
					.click(
							function() {
								var id = "${info.id}";
								var title = $("#titles").val();
								var content = $("#contents").val();
								var picSrc = $("#honorImage").attr("src");
								var createTime = $("#createTime").val();
								var titleId = "${info.id}";
								var picStr=$("#pic").val();
								$
										.ajax({
											type : 'post',
											async : false,
											dataType : 'json',
											data : {
												id : id,
												title : title,
												content : content,
												pic : picSrc,
												createTime : createTime
												
											},
											url : "${pageContext.request.contextPath}/honor/addHonorInfoCommit",
											success : function(data) {
												if (data.errorFlags) {
													$("#nameMessage").html("");
													$("#createTimeMessage").html("");
													$("#nameMessage").html(
															data.nameMessage);
													
													$("#createTimeMessage")
															.html(
																	data.entryTimeMessage);
													
												} else {
													amazeAlertSuccess(data.message);
													window
															.setTimeout(
																	function() {
																		if(id != null && id != ""){
																			$("#sx").submit();
																		} else {
																			window.location.href = "${pageContext.request.contextPath}/honor/honorList";
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