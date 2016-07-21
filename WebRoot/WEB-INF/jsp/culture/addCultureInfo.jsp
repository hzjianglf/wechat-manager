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
					<a href="${pageContext.request.contextPath}/about_us/culture/cultureList">企业文化信息管理</a>
					</small>/<small>企业文化信息添加或修改</small>
				</div>
			</div>
			<div style="width: 60%;margin: 0 auto;">
				<form
					action="${pageContext.request.contextPath}/about_us/culture/addCultureInfoCommit"
					class="am-form" data-am-validator>
					<fieldset>
						
						<div class="am-form-group">
							<label for="title">名称：</label> <input type="text" id="cultureName" value="${info.cultureName }"
								name="cultureName" minlength="3" placeholder="" required />
						</div>
						<div id="cultureNameMessage" style="color: red;margin-bottom: 12px;"></div>
						<div class="am-form-group">
							<label for="title">简单描述文字一条：</label> <input type="text" id="cultureDescription" value="${info.cultureDescription }"
								name="cultureDescription" maxlength="20" placeholder="不超过20" required />
						</div>
						<div id="cultureDescriptionMessage" style="color: red;margin-bottom: 12px;"></div>

						<div class="am-form-group">
							<label for="content">内容：</label>
								<textarea id="cultureInfo" name="cultureInfo" rows="25"
								style="width: 100%; border: 1px">${info.cultureInfo}</textarea>
						</div>
						<div id="cultureInfoMessage" style="color: red;margin-bottom: 12px;"></div>

						<div class="am-form-group">
							<script type="text/javascript">
							jQuery(function(){
					            UploadPic("imageUrl_pic", "imageUrl_progress", "imageUrl_src", "culturePic");
					            			//上传按钮------进度条----------------显示照片--------传值(name属性)
							});
							</script>
							<label for="positionSalary">图片：</label>
							<div>
							<input type="hidden" name="culturePic" id="culturePic" value="${info.culturePic}"/>
						
							<c:if test="${info.culturePic == '' || info.culturePic eq null}">
								<img id="imageUrl_src" src="" height="150px" width="150px">
							</c:if>
							<c:if test="${info.culturePic != '' && info.culturePic ne null}">
								<img id="imageUrl_src" src="${pageContext.request.contextPath}/image/photo?imgName=${info.culturePic}" height="150px" width="150px">
							</c:if>
							<span><input type="file" id="imageUrl_pic" value="上传图片" class="search-button" />（大小不超过1M）</span>
			    			<div id="imageUrl_progress"></div>
			    			</div>  
						</div>
						<div id="culturePicMessage" style="color:red;margin-bottom:12px"></div>
						<input type="hidden" name="id" value="${info.id}" />
						<button class="am-btn am-btn-secondary" type="button" id="submit">提交</button>
					</fieldset>
				</form>
			</div>
		</div>
		<form action="${pageContext.request.contextPath}/about_us/culture/cultureList" id="sx" method="post">
			<input type="hidden" name="cultureName" value="${cultureInfo.cultureName}" />
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
												culturePic : pic,
												cultureDescription:$("#cultureDescription").val()
												
											},
											url : "${pageContext.request.contextPath}/about_us/culture/addCultureInfoCommit",
											success : function(data) {
												if (data.errorFlags) {
													$("#cultureNameMessage").html("");
													$("#cultureNameMessage").html(data.cultureNameMessage);
													$("#cultureInfoMessage").html("");
													$("#cultureInfoMessage").html(data.cultureInfoMessage);
													$("#cultureDescriptionMessage").html("");
													$("#cultureDescriptionMessage").html(data.cultureDescriptionMessage);
													$("#culturePicMessage").html("");
													$("#culturePicMessage").html(data.culturePicMessage);
												} else {
													amazeAlertSuccess(data.message);
													window
															.setTimeout(
																	function() {
																		if(id != null && id != ""){
																			$("#sx").submit();
																		} else {
																			window.location.href = "${pageContext.request.contextPath}/about_us/culture/cultureList";
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