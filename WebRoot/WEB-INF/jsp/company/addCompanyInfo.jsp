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
					<small>位置</small>：<small>公司管理中心</small>
					/<small>
					<a href="${pageContext.request.contextPath}/about_us/company/companyList">公司信息管理</a>
					</small>/<small>公司信息添加或修改</small>
				</div>
			</div>
			<div style="width: 60%;margin: 0 auto;">
				<form
					action="${pageContext.request.contextPath}/about_us/company/addCompanyInfoCommit"
					class="am-form" data-am-validator>
					<fieldset>
						
						<div class="am-form-group">
							<label for="title">名称：</label> <input type="text" id="companyName" value="${info.companyName }"
								name="companyName" minlength="3" placeholder="输入名称（至少 3 个字符）" required />
						</div>
						<div id="companyNameMessage" style="color: red;margin-bottom: 12px;"></div>

						<div class="am-form-group" style="width: 800px">
							<label for="content">介绍内容：</label> 
								<textarea id="companyInfo" name="companyInfo" rows="20" cols="40">${info.companyInfo}</textarea>
						</div>
						
						<div id="companyInfoMessage" style="color: red;margin-bottom: 12px;"></div>

						<div class="am-form-group">
							<script type="text/javascript">
							jQuery(function(){
					            UploadPic("imageUrl_pic", "imageUrl_progress", "imageUrl_src", "companyPic");
					            			//上传按钮------进度条----------------显示照片--------传值(name属性)
							});
							</script>
							<label for="positionSalary">图片：</label>
							<div>
							<input type="hidden" name="companyPic" id="companyPic" value="${info.companyPic}"/>
						
							<c:if test="${info.companyPic == '' || info.companyPic eq null}">
								<img id="imageUrl_src" src="" height="50px" width="50px">
							</c:if>
							<c:if test="${info.companyPic != '' && info.companyPic !=null}">
								<img id="imageUrl_src" src="/image/photo?imgName=${info.companyPic}" height="150px" width="150px">
							</c:if>  
 							<span><input type="file" id="imageUrl_pic" value="上传图片" class="search-button" />（大小不超过1M）</span>
			    			<div id="imageUrl_progress"></div>
			    			</div>
						</div>
						<div id="companyPicMessage" style="color:red;margin-bottom:12px"></div>
						
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
		<form action="${pageContext.request.contextPath}/about_us/company/companyList" id="sx" method="post">
			<input type="hidden" name="companyName" value="${companyInfo.companyName}" />
			<input type="hidden" name="currentPage" value="${pageQueryUtil.currentPage}" />
		</form>
	</div>
	<%@include file="/WEB-INF/jsp/index/foot.jsp"%>
	
	<script type="text/javascript">
	
	$(document).ready(function() {
				//初始化xhEditor编辑器插件  
				$('#companyInfo')
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
								var name = $("#companyName").val();
								var content = $("#companyInfo").val();
								var createTime = $("#createTime").val();
								var titleId = "${info.id}";
								var picStr=$("#companyPic").val();
								$
										.ajax({
											type : 'post',
											async : false,
											dataType : 'json',
											data : {
												id : id,
												companyName : name,
												companyInfo : content,
												companyPic : picStr,
												createTime : createTime
												
											},
											url : "${pageContext.request.contextPath}/about_us/company/addCompanyInfoCommit",
											success : function(data) {
												if (data.errorFlags) {
													$("#companyNameMessage").html("");
													$("#companyInfoMessage").html("");
													
													$("#createTimeMessage").html("");
													$("#companyNameMessage").html(data.companyNameMessage);
													$("#companyInfoMessage").html(data.companyInfoMessage);
													
													$("#createTimeMessage").html(data.entryTimeMessage);
													
												} else {
													amazeAlertSuccess(data.message);
													window
															.setTimeout(
																	function() {
																		if(id != null && id != ""){
																			$("#sx").submit();
																		} else {
																			window.location.href = "${pageContext.request.contextPath}/about_us/company/companyList";
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