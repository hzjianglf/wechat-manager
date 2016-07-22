<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
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
					<small>位置</small>：<small>服务管理中心</small> /<small> <a
						href="${pageContext.request.contextPath}/service/serviceItemList">服务信息管理</a>
					</small>/<small>服务信息添加或修改</small>
				</div>
			</div>
			<div style="width: 60%; margin: 0 auto;">
				<form
					action="${pageContext.request.contextPath}/service/addServiceItemCommit"
					class="am-form" data-am-validator>
					<fieldset>

						<div class="am-form-group">
							<label for="title">服务名称：</label> <input type="text"
								id="serviceName" value="${item.serviceName }" name="serviceName"
								minlength="3" placeholder="输入名称（至少 3 个字符）" required />
						</div>
						<div id="serviceNameMessage" style="color: red; margin-bottom: 12px;"></div>
						<script type="text/javascript">
							$(document).ready(function(){
								console.log("${item.parentId}");
								$(".selector").val("${item.parentId}");
								//$(".selector").find("option[text='环境毒理实验']").attr("selected",true);
							});
						</script>
						<div class="am-form-group">
							<label for="title">服务类型：</label> <select class="selector" id="serviceType" required>
								<optgroup label="农药">
									<option value="0">环境毒理实验</option>
									<option value="1">毒理学检测GLP</option>
									<option value="2">毒代动力学试验</option>
								</optgroup>
								<optgroup label="新化学物质">
									<option value="3">毒理学检测</option>
									<option value="4">生态毒理学试验</option>
								</optgroup>
								<optgroup label="药品">
									<option value="5">药物安全性评价</option>
									<option value="6">药效学研究</option>
									<option value="7">药代动力学试验</option>
								</optgroup>
							</select>
						</div>
						<div id="serviceTypeMessage" style="color:red; margin-bottom:12px;"></div>
						<div class="am-form-group">
							<label for="content">内容：</label>
							<textarea id="serviceContent" name="serviceContent" rows="15"
								style="width: 100%; border: 1px">${item.serviceContent}</textarea>
						</div>
						<div id="serviceContentMessage" style="color: red; margin-bottom: 12px;"></div>
						<div class="am-form-group">
							<label for="content">优势：</label>
							<textarea id="serviceAdvantage" name="serviceAdvantage" rows="15"
								style="width: 100%; border: 1px" required="required">${item.serviceAdvantage}</textarea>
						</div>
						<div id="serviceAdvantageMessage" style="color: red; margin-bottom: 12px;"></div>
						<div class="am-form-group">
							<script type="text/javascript">
							jQuery(function(){
					            UploadPic("imageUrl_pic", "imageUrl_progress", "imageUrl_src", "servicePic");
					            			//上传按钮------进度条----------------显示照片--------传值(name属性)
							});
							</script>
							
							<label for="positionSalary">图片：</label>
							<div>
							<input type="hidden" name="servicePic" id="servicePic" value="${item.servicePic}"/>
						
							<c:if test="${(item.servicePic eq '') || (item.servicePic eq null)}">
								<img id="imageUrl_src" src="" height="150px" width="150px">
							</c:if>
							<c:if test="${(item.servicePic ne '') && (item.servicePic ne null)}">
								<img id="imageUrl_src" src="${pageContext.request.contextPath}/image/photo?imgName=${item.servicePic}" height="150px" width="150px">
							</c:if>  
 							
							<span><input type="file" id="imageUrl_pic" value="上传图片" class="search-button" />（大小不超过1M）</span>
			    			<div id="imageUrl_progress"></div>
			    			</div>
						</div>
						<div id="servicePicMessage" style="color: red; margin-bottom: 12px;"></div>
						
						<div class="am-form-group">
							<label for="createTime">发布时间：</label> <input type="text"
								id="createTime"
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								name="createTime"
								value="<fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>"
								required />
						</div>
						<div id="createTimeMessage"
							style="color: red; margin-bottom: 12px;"></div>

						<input type="hidden" name="id" value="${item.id}" />
						<button class="am-btn am-btn-secondary" type="button" id="submit">提交</button>
					</fieldset>
				</form>
			</div>
		</div>
		<!-- content end -->
		<form action="${pageContext.request.contextPath}/service/serviceItemList"
			id="sx" method="post">
			<input type="hidden" name="serviceName" value="${itemInfo.serviceName}" />
			<input type="hidden" name="currentPage" value="${pageQueryUtil.currentPage}" />
		</form>
	</div>
	<%@include file="/WEB-INF/jsp/index/foot.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
							//初始化xhEditor编辑器插件  
							$('#serviceContent').xheditor(
											{
												tools : 'full',
												skin : 'default',
												html5Upload : false,
												upImgUrl : "${pageContext.request.contextPath}/file/xheditorFileUpload2Map",
												upImgExt : "jpg,jpeg,gif,bmp,png",
												onUpload : insertUpload
											});
							function insertUpload(msg) {
								var _msg = msg.toString();
								console.log(_msg);
								$('#serviceContent').append(
										"<img src='"+_msg+"' id='img' width='"+100+"' height='"+200+"'/>");
							}
							function Substring(s) {
								return s.substring(s.substring(0,
										s.lastIndexOf("/")).lastIndexOf("/"),
										s.length);
							}
							$('#serviceAdvantage').xheditor(
									{
										tools : 'full',
										skin : 'default',
										html5Upload : false,
										upImgUrl : "${pageContext.request.contextPath}/file/xheditorFileUpload2Map",
										upImgExt : "jpg,jpeg,gif,bmp,png"
									});
						});
		
		$(function() {
			$("#submit")
					.click(
							function() {

								var id = "${item.id}";
								var name = $("#serviceName").val();
								var content = $("#serviceContent").val();
								var createTime = $("#createTime").val();
								var pic=$("#servicePic").val();
								console.log( $('#serviceType option:selected') .val());
								$
										.ajax({
											type : 'post',
											async : false,
											dataType : 'json',
											data : {
												id : id,
												serviceName : name,
												serviceContent : content,
												servicePic : pic,
												createTime : createTime,
												parentId: $('#serviceType option:selected') .val(),
												serviceAdvantage:$("#serviceAdvantage").val()

											},
											url : "${pageContext.request.contextPath}/service/addServiceItemCommit",
											success : function(data) {
												if (data.errorFlags) {
													$("#serviceNameMessage").html("");
													$("#parentIdMessage").html("");
													$("#serviceContentMessage").html("");
													$("#serviceAdvantageMessage").html("");
													$("#servicePicMessage").html("");
													$("#serviceNameMessage").html(data.serviceNameMessage);
													$("#serviceTypeMessage").html(data.serviceTypeMessage);
													$("#serviceContentMessage").html(data.serviceContentMessage);
													$("#serviceAdvantageMessage").html(data.serviceAdvantageMessage);
													$("#servicePicMessage").html(data.servicePicMessage);


												} else {
													amazeAlertSuccess(data.message);
													window
															.setTimeout(
																	function() {
																		if (id != null&& id != "") {
																			console.log("gt'");
																			$("#sx").submit();
																		} else {
																			console.log("we");
																			window.location.href = "${pageContext.request.contextPath}/service/serviceItemList";
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