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
					<a href="${pageContext.request.contextPath}/about_us/history/historyList">历史沿革管理</a>
					</small>/<small>历史沿革信息添加或修改</small>
				</div>
			</div>
			<div style="width: 60%;margin: 0 auto;">
				<form
					action="${pageContext.request.contextPath}/about_us/history/addHistoryInfoCommit"
					class="am-form" data-am-validator>
					<fieldset>
						
						<div class="am-form-group">
							<label for="title">名称：</label> <input type="text" id="historyName" value="${info.historyName }"
								name="historyName" minlength="3" placeholder="" required />
						</div>
						<div id="historyNameMessage" style="color: red;margin-bottom: 12px;"></div>
						<div class="am-form-group">
							<label for="createTime">年份：</label> <input type="text"
								id="historyYear" onClick="WdatePicker({dateFmt:'yyyy'})"
								name="historyYear" value="<fmt:formatDate value="${info.historyYear }" pattern="yyyy"/>" required />
						</div>
						<div id="historyYearMessage" style="color: red;margin-bottom: 12px;"></div>
						<div class="am-form-group">
							<label for="title">内容1：</label> <input type="text" id="historyInfo1" value="${info.historyInfo1 }"
								name="historyInfo1" maxlength="40" placeholder="不超过40" required />
						</div>
						<div class="am-form-group">
							<label for="title">内容2：</label> <input type="text" id="historyInfo2" value="${info.historyInfo2 }"
								name="historyInfo2" maxlength="40" placeholder="不超过40" required />
						</div>
						<div class="am-form-group">
							<label for="title">内容3：</label> <input type="text" id="historyInfo3" value="${info.historyInfo3 }"
								name="historyInfo3" maxlength="40" placeholder="不超过40" required />
						</div>
						<div class="am-form-group">
							<label for="title">内容4：</label> <input type="text" id="historyInfo" value="${info.historyInfo }"
								name="historyInfo" maxlength="40" placeholder="不超过40" required />
						</div>
						
						<div id="historyInfoMessage" style="color:red;margin-bottom:12px"></div>

						<div class="am-form-group">
							<script type="text/javascript">
							jQuery(function(){
					            UploadPic("imageUrl_pic", "imageUrl_progress", "imageUrl_src", "historyPic");
					            			//上传按钮------进度条----------------显示照片--------传值(name属性)
							});
							</script>
							
							<label for="positionSalary">图片：</label>
							<div>
							<input type="hidden" name="historyPic" id="historyPic" value="${info.historyPic}"/>
						
							<c:if test="${info.historyPic == '' || info.historyPic eq null}">
								<img id="imageUrl_ser" src="" height="150px" width="150px">
							</c:if>
							<c:if test="${info.historyPic != '' && info.historyPic ne null}">
								<img id="imageUrl_src" src="${pageContext.request.contextPath}/image/photo?imgName=${info.historyPic}" height="150px" width="150px">
							</c:if>  
							<span><input type="file" id="imageUrl_pic" value="上传图片" class="search-button" />（大小不超过1M）</span>
			    			<div id="imageUrl_progress"></div>
			    			</div>
						</div>
						<div id="historyPicMessage" style="color:red;margin-bottom:12px"></div>
						<input type="hidden" name="id" value="${info.id}" />
						<button class="am-btn am-btn-secondary" type="button" id="submit">提交</button>
					</fieldset>
				</form>
			</div>
		</div>
		<form action="${pageContext.request.contextPath}/about_us/history/historyList" id="sx" method="post">
			<input type="hidden" name="historyName" value="${historyInfo.historyName}" />
			<input type="hidden" name="currentPage" value="${pageQueryUtil.currentPage}" />
		</form>
	</div>
	<%@include file="/WEB-INF/jsp/index/foot.jsp"%>
	<script type="text/javascript">
		$(function() {
			$("#submit")
					.click(
							function() {
								var id = "${info.id}";
								var name = $("#historyName").val();
								var pic=$("#historyPic").val();
								$
										.ajax({
											type : 'post',
											async : false,
											dataType : 'json',
											data : {
												id : id,
												historyName : name,
												historyPic : pic,
												historyInfo1:$("#historyInfo1").val(),
												historyInfo2:$("#historyInfo2").val(),
												historyInfo3:$("#historyInfo3").val(),
												historyInfo:$("#historyInfo").val(),
												year:$("#historyYear").val()
													
												
											},
											url : "${pageContext.request.contextPath}/about_us/history/addHistoryInfoCommit",
											success : function(data) {
												if (data.errorFlags) {
													$("#historyNameMessage").html("");
													$("#historyNameMessage").html(data.historyNameMessage);
													$("#historyYearMessage").html("");
													$("#historyYearMessage").html(data.historyYearMessage);
													$("#historyInfoMessage").html("");
													$("#historyInfoMessage").html(data.historyInfoMessage);
												} else {
													amazeAlertSuccess(data.message);
													window
															.setTimeout(
																	function() {
																		if(id != null && id != ""){
																			$("#sx").submit();
																		} else {
																			window.location.href = "${pageContext.request.contextPath}/about_us/history/historyList";
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