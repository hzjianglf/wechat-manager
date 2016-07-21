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
					<small>位置</small>：<small>功能实验室</small>
					/<small>
					<a href="${pageContext.request.contextPath}/laboratory/laboratoryList">功能实验室管理</a>
					</small>/<small>功能实验室信息添加或修改</small>
				</div>
			</div>
			<div style="width: 60%;margin: 0 auto;">
				<form
					action="${pageContext.request.contextPath}/laboratory/addLaboratoryInfoCommit"
					class="am-form" data-am-validator>
					<fieldset>
						
						<div class="am-form-group">
							<label for="title">名称：</label> <input type="text" id="laboratoryName" value="${info.laboratoryName }"
								name="laboratoryName" minlength="3" placeholder="" required />
						</div>
						<div id="laboratoryNameMessage" style="color: red;margin-bottom: 12px;"></div>
						<script type="text/javascript">
							$(document).ready(function(){
								$("#laboratoryType").val("${info.laboratoryType}");
							});
						</script>
						<div class="am-from-group">
							<label for="">实验室类型：</label>
							<select id="laboratoryType" required>
									<option value="1">实验动物房</option>
									<option value="2">组织病理室</option>
									<option value="3">临床检验室</option>
									<option value="4">一般毒理室</option>
									<option value="5">分析室</option>
									<option value="6">细胞生物室</option>
									<option value="7">供试品室</option>
									<option value="8">档案室</option>
									<option value="9">环境毒理室</option>
									<option value="10">信息中心</option>
									<option value="11">质量保证部</option>
							</select>
						</div>
						<div id="laboratoryTypeMessage" style="color:red;margin-bottom:12px"></div>
						<div class="am-form-group">
							<label for="content">内容：</label>
								<textarea id="laboratoryInfo" name="laboratoryInfo" rows="25"
								style="width: 100%; border: 1px">${info.laboratoryInfo}</textarea>
						</div>
						<div id="laboratoryInfoMessage" style="color: red;margin-bottom: 12px;"></div>

						<div class="am-form-group">
							<script type="text/javascript">
							jQuery(function(){
					            UploadPic("imageUrl_pic", "imageUrl_progress", "imageUrl_src", "laboratoryPic");
					            			//上传按钮------进度条----------------显示照片--------传值(name属性)
							});
							</script>
							
							<label for="positionSalary">图片：</label>
							<div>
							<input type="hidden" name="laboratoryPic" id="laboratoryPic" value="${info.laboratoryPic}"/>
						
							<c:if test="${info.laboratoryPic == ''|| info.laboratoryPic eq null}">
								<img id="imageUrl_src" src="" height="150px" width="150px">
							</c:if>
							<c:if test="${(info.laboratoryPic != '') && (info.laboratoryPic ne null)}">
								<img id="imageUrl_src" src="/image/photo?imgName=${info.laboratoryPic}" height="150px" width="150px">
							</c:if> 
							<span><input type="file" id="imageUrl_pic" value="上传图片" class="search-button" />（大小不超过1M）</span>
			    			<div id="imageUrl_progress"></div>
			    			</div>
						</div>
						<div id="laboratoryPicMessage" style="color:red;margin-bottom:12px"></div>
						<input type="hidden" name="id" value="${info.id}" />
						<button class="am-btn am-btn-secondary" type="button" id="submit">提交</button>
					</fieldset>
				</form>
			</div>
		</div>
		<form action="${pageContext.request.contextPath}/laboratory/laboratoryList" id="sx" method="post">
			<input type="hidden" name="laboratoryName" value="${laboratoryInfo.laboratoryName}" />
			<input type="hidden" name="currentPage" value="${pageQueryUtil.currentPage}" />
		</form>
	</div>
	<%@include file="/WEB-INF/jsp/index/foot.jsp"%>
	<script type="text/javascript">	
	
	$(document).ready(function() {
		//初始化xhEditor编辑器插件  
		$('#laboratoryInfo').xheditor(
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
								var name = $("#laboratoryName").val();
								var content = $("#laboratoryInfo").val();
								var pic=$("#laboratoryPic").val();
								$
										.ajax({
											type : 'post',
											async : false,
											dataType : 'json',
											data : {
												id : id,
												laboratoryName : name,
												laboratoryInfo : content,
												laboratoryPic : pic,
												laboratoryType:$('#laboratoryType option:selected') .val()
													
											},
											url : "${pageContext.request.contextPath}/laboratory/addLaboratoryInfoCommit",
											success : function(data) {
												if (data.errorFlags) {
													$("#laboratoryNameMessage").html("");
													$("#laboratoryNameMessage").html(data.laboratoryNameMessage);
													$("#laboratoryTypeMessage").html("");
													$("#laboratoryTypeMessage").html(data.laboratoryTypeMessage);
													$("#laboratoryInfoMessage").html("");
													$("#laboratoryInfoMessage").html(data.laboratoryInfoMessage);
													$("#laboratoryPicMessage").html("");
													$("#laboratoryPicMessage").html(data.laboratoryPicMessage);
												} else {
													amazeAlertSuccess(data.message);
													window
															.setTimeout(
																	function() {
																		if(id != null && id != ""){
																			$("#sx").submit();
																		} else {
																			window.location.href = "${pageContext.request.contextPath}/laboratory/laboratoryList";
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