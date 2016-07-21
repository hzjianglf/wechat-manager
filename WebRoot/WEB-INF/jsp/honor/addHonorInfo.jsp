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
					<small>位置</small>：<small>荣誉管理中心</small>
					/<small>
					<a href="${pageContext.request.contextPath}/about_us/honor/honorList">荣誉信息管理</a>
					</small>/<small>荣誉信息添加或修改</small>
				</div>
			</div>
			<div style="width: 60%;margin: 0 auto;">
				<form
					action="${pageContext.request.contextPath}/about_us/honor/addHonorInfoCommit"
					class="am-form" data-am-validator>
					<fieldset>
						
						<!-- id为title和content这两个字段时候，  $("#title").val() 这样取值为空-->
						<div class="am-form-group">
							<label for="title">标题：</label> <input type="text" id="titles" value="${info.title }"
								name="title" minlength="3" placeholder="输入标题（至少 3 个字符）" required />
						</div>
						<div id="titleMessage" style="color: red;margin-bottom: 12px;"></div>

						<div class="am-form-group">
							<label for="content">内容：</label> <textarea id="contents" name=content rows="25"
								style="width: 100%; border: 1px">${info.content}</textarea>
						</div>
						<div id="contentMessage" style="color: red;margin-bottom: 12px;"></div>

						<div class="am-form-group">
							<script type="text/javascript">
							jQuery(function(){
					            UploadPic("imageUrl_pic", "imageUrl_progress", "imageUrl_src", "honorPic");
					            			//上传按钮------进度条----------------显示照片--------传值(name属性)
							});
							</script>
							
							<label for="positionSalary">图片：</label>
							<div>
							<input type="hidden" name="honorPic" id="pic" value="${info.pic}"/>
						
							<c:if test="${info.pic != '' && info.pic ne null}">
								<img id="imageUrl_src" src="/image/photo?imgName=${info.pic}" height="150px" width="150px">
							</c:if>  
							<c:if test="${info.pic == '' || info.pic eq null}">
								<img id="imageUrl_src" src="" height="150px" width="150px">
							</c:if>  
 							
							<span><input type="file" id="imageUrl_pic" value="上传图片" class="search-button" />（大小不超过1M）</span>
			    			<div id="imageUrl_progress"></div>
			    			</div>
						</div>
						<div id="honorPicMessage" style="color:red;margin-bottom:12px"></div>
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
		<form action="${pageContext.request.contextPath}/about_us/honor/honorList" id="sx" method="post">
			<input type="hidden" name="title" value="${honorInfo.title}" />
			<input type="hidden" name="currentPage" value="${pageQueryUtil.currentPage}" />
		</form>
	</div>
	<%@include file="/WEB-INF/jsp/index/foot.jsp"%>
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
	
		$(function() {
			$("#submit")
					.click(
							function() {
								var id = "${info.id}";
								var title = $("#titles").val();
								var content = $("#contents").val();
								var honorPic = $("#imageUrl_src").attr("src");
								var createTime = $("#createTime").val();
								var titleId = "${info.id}";
								var picStr=$("#pic").val();
								console.log(honorPic+"==="+picStr);
								$
										.ajax({
											type : 'post',
											async : false,
											dataType : 'json',
											data : {
												id : id,
												title : title,
												content : content,
												pic : picStr,
												createTime : createTime
												
											},
											url : "${pageContext.request.contextPath}/about_us/honor/addHonorInfoCommit",
											success : function(data) {
												if (data.errorFlags) {
													$("#titleMessage").html("");
													$("#contentMessage").html("");
													$("#honorPicMessage").html("");
													
													$("#titleMessage").html(data.titleMessage);
													$("#contentMessage").html(data.contentMessage);
													$("#honorPicMessage").html(data.picMessage);
															
												} else {
													amazeAlertSuccess(data.message);
													window
															.setTimeout(
																	function() {
																		if(id != null && id != ""){
																			$("#sx").submit();
																		} else {
																			window.location.href = "${pageContext.request.contextPath}/about_us/honor/honorList";
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