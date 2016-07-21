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
					<a href="${pageContext.request.contextPath}/about_us/organize/organizeList">组织机构信息管理</a>
					</small>/<small>组织机构信息添加或修改</small>
				</div>
			</div>
			<div style="width: 60%;margin: 0 auto;">
				<form
					action="${pageContext.request.contextPath}/about_us/organize/addOrganizeInfoCommit"
					class="am-form" data-am-validator>
					<fieldset>
						
						<div class="am-form-group">
							<label for="title">组织机构名称：</label> <input type="text" id="organizeName" value="${info.organizeName }"
								name="organizeName" minlength="3" placeholder="" required />
						</div>
						<div id="organizeNameMessage" style="color: red;margin-bottom: 12px;"></div>

						<div class="am-form-group">
							<label for="content">组织机构内容：</label>
								<textarea id="organizeInfo" name="organizeInfo" rows="25"
								style="width: 100%; border: 1px">${info.organizeInfo}</textarea>
						</div>
						<div id="organizeInfoMessage" style="color: red;margin-bottom: 12px;"></div>

						<div class="am-form-group">
							<script type="text/javascript">
							jQuery(function(){
					            UploadPic("J_imageUrl_pic", "J_imageUrl_progress", "J_imageUrl_src", "imageUrl");
							});
							</script>
							<label for="positionSalary">机构图片：</label>
							<div>
							<input class="" type="hidden" id="J_imageUrl" name="imageUrl" value="${info.organizePic}"  />
						
							<c:if test="${info.organizePic != '' && info.organizePic ne null}">
			    			<img id="J_imageUrl_src" src="/image/photo?imgName=${info.organizePic}"  width="176" height="126" />
			    			</c:if>
							<c:if test="${info.organizePic == '' || info.organizePic eq null}">
			    			<img id="J_imageUrl_src" src=""  width="176" height="126" />
			    			</c:if>
			    			<span><input type="file" id="J_imageUrl_pic" value="上传图片" class="search-button" />（大小不超过1M）</span>
			    			<div id="J_imageUrl_progress"></div>
			    			</div>
						</div>
						<div id="organizePicMessage" style="color: red;margin-bottom: 12px;"></div>
						
						<input type="hidden" name="id" value="${info.id}" />	
						 
						<button class="am-btn am-btn-secondary" type="button" id="submit">提交</button>
					</fieldset>
				</form>
			</div>
		</div>
		<form action="${pageContext.request.contextPath}/about_us/organize/organizeList" id="sx" method="post">
			<input type="hidden" name="organizeName" value="${organizeInfo.organizeName}" />
			<input type="hidden" name="currentPage" value="${pageQueryUtil.currentPage}" />
		</form>
	</div>
	<%@include file="/WEB-INF/jsp/index/foot.jsp"%>
	<script type="text/javascript">
	$(document).ready(function() {
				//初始化xhEditor编辑器插件  
				$('#organizeInfo').xheditor(
								{
									tools : 'full',
									skin : 'default',
									html5Upload : false,
									upImgUrl : "${pageContext.request.contextPath}/file/xheditorFileUpload2String",
									upImgExt : "jpg,jpeg,gif,bmp,png",
									onUpload : insertUpload
								});
				//xbhEditor编辑器图片上传回调函数  
				function insertUpload(data) {
					var imgSrc=data.toString();
					console.log(data);
					console.log(imgSrc);
					//$('#organizeInfo').append("<img src='"+imgSrc+"' id='img' width='300' height='200'/>");
				}
		});
		$(function() {
			$("#submit")
					.click(
							function() {
								console.log($("#J_imageUrl").val());
								var id = "${info.id}";
								var name = $("#organizeName").val();
								var content = $("#organizeInfo").val();
								var pic=$("#J_imageUrl").val();
								$
										.ajax({
											type : 'post',
											async : false,
											dataType : 'json',
											data : {
												id : id,
												organizeName : name,
												organizeInfo : content,
												organizePic : pic
												
											},
											url : "${pageContext.request.contextPath}/about_us/organize/addOrganizeInfoCommit",
											success : function(data) {
												if (data.errorFlags) {
													$("#organizeNameMessage").html("");
													$("#organizeNameMessage").html(data.organizeNameMessage);
													$("#organizeInfoMessage").html("");
													$("#organizeInfoMessage").html(data.organizeInfoMessage);
													$("#organizePicMessage").html("");
													$("#organizePicMessage").html(data.organizePicMessage);
												} else {
													amazeAlertSuccess(data.message);
													window
															.setTimeout(
																	function() {
																		if(id != null && id != ""){
																			$("#sx").submit();
																		} else {
																			window.location.href = "${pageContext.request.contextPath}/about_us/organize/organizeList";
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