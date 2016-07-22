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
      				<small>位置</small>：<small>公司管理中心</small>/<small>组织机构管理</small>
      			</div>
			</div>
			<form action="${pageContext.request.contextPath}/about_us/organize/organizeList" method="post" id="form1" class="am-form">
    		<div class="am-g">
    			<slp:privilege module="OrganizeManager" oprator="add">
   				<div class="am-u-lg-2">
				    <div class="am-input-group am-input-group-secondary am-form-group">
				      <a href="${pageContext.request.contextPath}/about_us/organize/addOrganizeInfo"><span class="am-input-group-label">新增</span></a>
				    </div>
				 </div>
				 </slp:privilege>
   				<div class="am-u-lg-3">
				    <div class="am-input-group am-input-group-secondary am-form-group">
				      <span class="am-input-group-label">组织机构名称</span>
				      <input type="text" name="organizeName" placeholder="名称" value="${organizeInfo.organizeName}" class="am-form-field"/>
				    </div>
				 </div>
				 
				 <div class="am-u-lg-1">
				    <div class="am-input-group ">
				      <input class="am-btn am-btn-primary" type="submit" value="搜索"/>
				      <input type="hidden" id="currentPage" name="currentPage" value="1" />
				    </div>
				 </div>
			</div>
    		</form>
    		<form action="${pageContext.request.contextPath}/about_us/organize/organizeList" method="post" id="form2">
    			<input type="hidden" name="organizeName" value="${organizeInfo.organizeName}" />
    			<input type="hidden" id="currentPage2" name="currentPage" value="1" />
    		</form>
			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th>序号</th>
									<th>组织机构名称</th>
									<th>组织机构图片</th>
									<th>状态</th>
									<th class="table-set">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list }" var="p" varStatus="status">
									<tr>
										<td>${status.index + (page.currentPage - 1)*page.pageSize + 1}</td>
										<td>${p.organizeName}</td>
										<c:choose>
											<c:when test="${p.organizePic ne '' && p.organizePic ne null }">
												<td><img src="${pageContext.request.contextPath}/image/photo?imgName=${p.organizePic}" width="80px" height="60px"/></td>											
											</c:when>
											<c:otherwise>
												<td>无</td>
											</c:otherwise>
										</c:choose>
										<td>
											<c:if test="${p.isDel==0 }">在用</c:if>
											<c:if test="${p.isDel==1 }">删除</c:if>
											
										</td>
										<td>
											<div class="am-dropdown" data-am-dropdown>
								                <button class="am-btn am-btn-default am-btn-xs am-dropdown-toggle" data-am-dropdown-toggle><span class="am-icon-cog"></span> <span class="am-icon-caret-down"></span></button>
												
								                <ul class="am-dropdown-content">
								                
								                  <slp:privilege module="OrganizeManager" oprator="update">
								                  	<li><a href="javascript:void(0)" name="${p.id}" class="bj">编辑</a></li>
								                  </slp:privilege>
								                  <slp:privilege module="OrganizeManager" oprator="delete">
								                  	<li><a href="javascript:void(0)" class="del" name="${p.id}">删除</a></li>
								                  </slp:privilege>
								                </ul>
								              </div></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="am-cf">
							共 ${requestScope.page.totalSize } 条记录&nbsp;&nbsp;<span class="tooltip"></span>
							<div class="am-fr">
								<ul class="am-pagination ">
									${requestScope.pageString }
								</ul>
							</div>
						</div>
					</form>
				</div>

			</div>
			<br><br><br><br><br>
		</div>

	</div>
	<%@include file="/WEB-INF/jsp/index/foot.jsp"%>
	<input type="hidden" value="${requestScope.page.totalPage }" id="totalPage"/>
	<input type="hidden" value="${page.currentPage}" id="cp" />
	<script type="text/javascript">
		/**
		*跳转到首页
		*/
		function toStart(){
			if($('#cp').val() != 1){
				$('#form2').submit();
			}
		}
			
		/**
		*跳转到尾页
		*/
		function toEnd(){
			var totalPage = $('#totalPage').val();
			if($('#cp').val() != totalPage){
				$('#currentPage2').val(totalPage);
				$('#form2').submit();			
			}
		}
		
		function del(id){
			$.ajax({
				type : 'post',
				async : false,
				dataType : 'json',
				data : {
					id : id
				},
				url : "${pageContext.request.contextPath}/about_us/organize/deleteOrganizeInfo",
				success : function(data) {
					if (data.errorFlags) {
						alert(1);
						amazeAlertSuccess(data.message);
		        		window.setTimeout(function(){
		        			$('#currentPage2').val("${page.currentPage}");
		        			$("#form2").submit();
		        		}, 1000);						
					} else {
						alert(2);
						amazeAlertFail("删除失败！");
					}
				},
				error : function(data) {
					$("#update-fail-alert").modal();
				}
			});
		}
		
		$(document).ready(function(){
			$('.page_s').on('click',function(){
				var count = $(this).attr('lang');
				if($('#cp').val() != count){
					$('#currentPage2').val(count);
					if (count <= parseInt($('#totalPage').val()) && count >= 1){
						$('#form2').submit();
					}
				}
			});
			
			$(".del").click(function(){
				var id = $(this).attr("name");
				amazeConfirm("您确定要删除这条组织机构信息吗？");
				$('#amaze-confirm').modal({
			        relatedTarget: this,
			        onConfirm: function(options) {
			        	del(id);
			        },
			        onCancel: function(){
			          return;
			        }
      			});		
				
			});
			
			$(".bj").click(function(){
				var id = $(this).attr("name");
				var url = "${pageContext.request.contextPath}/about_us/organize/updateOrganizeInfo?id=" + id;
				var cname = "${honorInfo.companyName}";
				var ccp = "${page.currentPage}";
				
				var c = "";
				if(cname!=null && $.trim(cname) != ""){
					c = c + "&name=" + cname;
				}
				
				if(ccp==null){
					c = c + "&currentPage=" + 1;
				}else{
					c = c + "&currentPage=" + ccp;
				}
				window.location.href = url + c;
			});
		});
	</script>
</body>
</html>