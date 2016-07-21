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
					<small>位置</small>：<small>服务管理中心</small>
					/<small>
					<a href="${pageContext.request.contextPath}/case/caseList">服务案例信息管理</a>
					</small>/<small>案例信息添加或修改</small>
				</div>
			</div>
			<div style="width: 60%;margin: 0 auto;">
				<form
					action="${pageContext.request.contextPath}/case/addCaseInfoCommit"
					class="am-form" data-am-validator>
					<fieldset>
						
						<div class="am-form-group">
							<label for="title">名称：</label> <input type="text" id="caseName" value="${info.caseName }"
								name="caseName" minlength="3" placeholder="输入名称（至少 3 个字符）" required />
						</div>
						<div id="caseNameMessage" style="color: red;margin-bottom: 12px;"></div>

						<%-- <div class="am-form-group" style="width: 800px">
							<label for="content">介绍内容：</label> 
								<textarea id="caseInfo" name="caseInfo" rows="20" cols="40">${info.caseInfo}</textarea>
						</div>
						
						<div id="caseInfoMessage" style="color: red;margin-bottom: 12px;"></div> --%>

						<input type="hidden" name="id" value="${info.id}" />
						<button class="am-btn am-btn-secondary" type="button" id="submit">提交</button>
					</fieldset>
				</form>
			</div>
		</div>
		<form action="${pageContext.request.contextPath}/case/caseList" id="sx" method="post">
			<input type="hidden" name="caseName" value="${caseInfo.caseName}" />
			<input type="hidden" name="currentPage" value="${pageQueryUtil.currentPage}" />
		</form>
	</div>
	<%@include file="/WEB-INF/jsp/index/foot.jsp"%>
	
	<script type="text/javascript">
	
	$(document).ready(function() {
				//初始化xhEditor编辑器插件  
				$('#caseInfo')
						.xheditor(
								{
									tools : 'full',
									skin : 'default',
									html5Upload : false,
									upImgUrl : "${pageContext.request.contextPath}/file/xheditorFileUpload2Map",
									upImgExt : "jpg,jpeg,gif,bmp,png",
									onUpload : insertUpload
								});
				//xbhEditor编辑器图片上传回调函数  
				function insertUpload(msg) {
					console.log(msg.toString());
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
								var name = $("#caseName").val();
								$.ajax({
											type : 'post',
											async : false,
											dataType : 'json',
											data : {
												id_int : id,
												caseName : name
												
											},
											url : "${pageContext.request.contextPath}/case/addCaseInfoCommit",
											success : function(data) {
												if (data.errorFlags) {
													$("#caseNameMessage").html("");
													
													$("#caseNameMessage").html(data.caseNameMessage);
														
												} else {
													amazeAlertSuccess(data.message);
													window
															.setTimeout(
																	function() {
																		if(id != null && id != ""){
																			$("#sx").submit();
																		} else {
																			window.location.href = "${pageContext.request.contextPath}/case/caseList";
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