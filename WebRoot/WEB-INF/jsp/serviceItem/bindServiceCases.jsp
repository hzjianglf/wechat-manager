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
					</small>/<small>服务案例添加或修改</small>
				</div>
			</div>
			<div style="width: 60%; margin: 0 auto;">
				<form action="${pageContext.request.contextPath}/service/addServiceItemCommit"
					class="am-form" data-am-validator novalidate>
					<fieldset>
					<table>
						<tr>
							<td>可选案例</td>
							<td />
							<td>已选案例</td>
						</tr>
						
						<tr>
							<td><select id="selectold" multiple="multiple"style="height: 400px;width: 200px">
									<c:forEach items="${notBindCase}" var="p">
									<option value="${p[0] }">
									${p[1]}
									</option>
									</c:forEach>
							</select></td>
							<td>
								<div>
									<input id="right" type="button" onclick="toright()" value="&gt;"
										style="width: 50px;" /><br /> <input id="left" type="button"
										onclick="toleft()" value="&lt;" style="width: 50px;" /><br />
									<input id="allRight" type="button" onclick="all_Right()"
										value="&gt;&gt;" style="width: 50px;" /><br /> <input
										id="allLeft" type="button" onclick="all_Left()"
										value="&lt;&lt;" style="width: 50px;" /><br />
								</div>
							</td>
							<td><select id="selectnew" multiple="multiple" style="height: 400px;width: 200px">
								<c:forEach items="${bindCase }" var="pp">
									<option value="${pp[1] }">
										 ${pp[0]}</option>
								</c:forEach>
							</select></td>
						</tr>
					</table>
					<input type="hidden" id="serviceId" value="${serviceId}">
					<button class="am-btn am-btn-secondary" type="button" id="submit">提交</button>
					</fieldset>
				</form>
			</div>
		</div>
		<form action="${pageContext.request.contextPath}/service/serviceItemList" id="sx" method="post">
			<input type="hidden" name="serviceId" value="${serviceId}" />
			<input type="hidden" name="currentPage" value="${pageQueryUtil.currentPage}" />
		</form>
	</div>
	<%@include file="/WEB-INF/jsp/index/foot.jsp"%>
	<script type="text/javascript">
	function toright() {
		var firstElement = document.getElementById("selectold");
		var secondElement = document.getElementById("selectnew");
		var firstoptionElement = firstElement.getElementsByTagName("option");
		var len = firstoptionElement.length;
		for(var i=0;i<len;i++){
			if(firstElement.selectedIndex != -1){ //selectedIndex 是select 的属性
				secondElement.appendChild(firstoptionElement[firstElement.selectedIndex]);
			}
		}
	}
	
	function toleft(){
		var rightElement=document.getElementById("selectnew");
		var leftElement=document.getElementById("selectold");
		var rightOptionElement=rightElement.getElementsByTagName("option");
		var len=rightOptionElement.length;
		for(var i=0;i<len;i++){
			if(rightElement.selectedIndex!=-1){
				leftElement.appendChild(rightOptionElement[rightElement.selectedIndex]);
			}
		}
	}
	
	//全部移动到右边
	function all_Right(){
		console.log("开始移动");
		var firstElement = document.getElementById("selectold");
		var secondElement = document.getElementById("selectnew");
		var firstoptionElement = firstElement.getElementsByTagName("option");
		var len = firstoptionElement.length;
		for(var i=0;i<len;i++){
			secondElement.appendChild(firstoptionElement[0]);//option选项选中时候索引为0
		}
	}
	function all_Left(){
		var rightElement=document.getElementById("selectnew");
		var leftElement=document.getElementById("selectold");
		var rightOptionElement=rightElement.getElementsByTagName("option");
		var len=rightOptionElement.length;
		for(var i=0;i<len;i++){
			leftElement.appendChild(rightOptionElement[0]);
		}
	}
	</script>
	<script type="text/javascript">

		$(function() {
			$("#submit")
					.click(
							function() {

								var id = $("#serviceId").val();
								var array=$("#selectnew").val();
								
								var arrays=[];
								var options = $("#selectnew").find("option"); // select下所有的option
								for (var i=0; i<options.length; i++) {
									arrays.push(options.eq(i).val()); // 将所有的值赋给数组
								}
								console.log(id);
								console.log(arrays);
								console.log(array);
								console.log(document.getElementById("selectnew").options);
								console.log($('#selectnew option:selected').val());
								if(arrays.length==0){
									console.log("array长度为0");
									$("#my-alert-serviceCase").modal();
									return;
								}
								$
										.ajax({
											type : 'post',
											async : false,
											dataType : 'json',
											data : {
												id : id,
												arrays : arrays
											},
											url : "${pageContext.request.contextPath}/service/bindServiceCaseCommit/"+id,
											success : function(data) {
												if (data.errorFlags) {
													$("#serviceNameMessage").html("");
													$("#serviceNameMessage").html(data.serviceNameMessage);

												} else {
													amazeAlertSuccess(data.message);
													window
															.setTimeout(
																	function() {
																		if (id != null&& id != "") {
																			console.log("列表页进入");
																			$("#sx").submit();
																		} else {
																			console.log("edg");
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