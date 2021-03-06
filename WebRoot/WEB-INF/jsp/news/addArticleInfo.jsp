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
					<small>位置</small>：<small>新闻活动</small> /<small> <a
						href="${pageContext.request.contextPath}/article/articleList">新闻信息管理</a>
					</small>/<small>新闻信息添加或修改</small>
				</div>
			</div>
			<div style="width: 60%; margin: 0 auto;">
				<form
					action="${pageContext.request.contextPath}/article/addArticleCommit"
					class="am-form" data-am-validator>
					<fieldset>

						<div class="am-form-group">
							<label for="title">标题：</label> <input type="text"
								id="articleTitle" value="${article.title }" name="title"
								minlength="3" placeholder="输入标题（至少 3 个字符）" required />
						</div>
						<div id="articleTitleMessage" style="color: red; margin-bottom: 12px;"></div>
						
						<div class="am-form-group">
							<label for="gender">标签</label> <select id="tag"
								name="tag" required>
								<option value="新鲜事" <c:if test="${article.tag == '新鲜事'}">selected=selected</c:if>>新鲜事</option>
								<option value="最新动态" <c:if test="${article.tag == '最新动态'}">selected=selected</c:if>>最新动态</option>
							</select>
						</div>
						<div id="tagMessage" style="color: red;margin-bottom: 12px;"></div>
						<div class="am-form-group">
							<label for="gender">作者</label> 
							<input type="text" name="author" id="author" value="${article.author}">
						</div>
						<div id="authorMessage" style="color: red;margin-bottom: 12px;"></div>
						<script type="text/javascript">
							$(document).ready(function(){
								$("#newsType").val("${article.newsType}");
							});
						</script>
						<div class="am-form-group">
							<label for="gender">新闻类型：</label>
							<select id="newsType" required >
								<option value="1">公司新闻</option>
								<option value="2">学术新闻</option>
							</select>
						</div>
						<div id="newsTypeMessage" style="color: red;margin-bottom: 12px;"></div>
						<div class="am-form-group">
							<label for="gender">一句话描述：</label>
							<input type="text" id="description" name="description" value="${article.description}" 
							minlength="3" placeholder="描述 " required="required" />
						</div>
						<div id="newsDescriptionMessage" style="color:red;margin-bottom:12px;"></div>
						<div class="am-form-group">
							<label for="content">文章内容：</label>
							<textarea id="content_editor" name=content rows="25"
								style="width: 100%; border: 1px">${article.content}</textarea>
						</div>
						<div id="newsContentMessage" style="color:red;margin-bottom:12px"></div>
						<div class="am-form-group">
							<script type="text/javascript">
							jQuery(function(){
					            UploadPic("imageUrl_pic", "imageUrl_progress", "imageUrl_src", "pic");
					            			//上传按钮------进度条----------------显示照片--------传值(name属性)
							});
							</script>
							
							<label for="content">图片：(必选项)</label>
							<div>
							<input type="hidden" name="pic" id="pic" value="${article.pic}"/>
						
							<c:if test="${article.pic == '' || article.pic eq null}">
								<img id="imageUrl_src" src="" height="150px" width="150px">
							</c:if>
							<c:if test="${article.pic != '' && article.pic ne null}">
								<img id="imageUrl_src" src="${pageContext.request.contextPath}/image/photo?imgName=${article.pic}" height="150px" width="150px">
							</c:if>  
 						
							<span><input type="file" id="imageUrl_pic" value="上传图片" class="search-button" />（大小不超过1M）</span>
			    			<div id="imageUrl_progress"></div>
			    			</div>
						</div>
						<div id="picMessage" style="color:red;margin-bottom:12px;"></div>
						
						<div id="uploadList"></div>
						<select id="selectPicss">
							<option value="">请选择</option>
						</select>
						<div id="nameMessage" style="color: red; margin-bottom: 12px;"></div>

						<div class="am-form-group">
							<label for="createTime">发布时间：</label> <input type="text"
								id="createTime"
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								name="createTime"
								value="<fmt:formatDate value="${article.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>"
								required />
						</div>
						<div id="createTimeMessage" style="color:red;margin-bottom:12px"></div>
						<input type="hidden" name="id" value="${article.id}" />
						<button class="am-btn am-btn-secondary" type="button" id="submit">提交</button>
					</fieldset>
				</form>
			</div>
		</div>
		<!-- content end -->
		<form action="${pageContext.request.contextPath}/article/articleList"
			id="sx" method="post">
			<input type="hidden" name="title" value="${news.title}" />
			<input type="hidden" name="currentPage" value="${pageQueryUtil.currentPage}" />
		</form>
	</div>
	<%@include file="/WEB-INF/jsp/index/foot.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
							//初始化xhEditor编辑器插件  
							$('#content_editor')
									.xheditor(
											{
												tools : 'full',
												skin : 'default',
												//upMultiple:false,
												html5Upload : false,
												upImgUrl : "${pageContext.request.contextPath}/file/xheditorFileUpload2Map",
												upImgExt : "jpg,jpeg,gif,bmp,png",
												onUpload : insertUpload
											});
							//xbhEditor编辑器图片上传回调函数  
							function insertUpload(msg) {
								var _msg = msg.toString();
								var _picture_name = _msg.substring(_msg
										.lastIndexOf("/") + 1);
								var _picture_path = Substring(_msg);
								var _str = "<input type='checkbox' name='_pictures' id='pics' value='"+_picture_path+"' checked='checked' onclick='return false'/><label>"
										+ _picture_name + "</label><br/>";
								//$('#content_editor').append(_msg);  
								$('#content_editor').append(
										"<img src='"+_msg+"' id='img' width='300px' height='200px'/>");
								//$("img[id='img']").attr("src", _msg);
								$("#uploadList").append(_str);

								$("#selectPicss").append(
										"<option value='"+_msg+"'>"
												+ _picture_name + "</option>");

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

								var id = "${article.id}";
								var title = $("#articleTitle").val();
								var content = $("#content_editor").val();
								var createTime = $("#createTime").val();
								var pic=$("#pic").val();
								var tag=$("#tag").val();
								var author=$("#author").val();
								
								$
										.ajax({
											type : 'post',
											async : false,
											dataType : 'json',
											data : {
												id : id,
												title : title,
												content : content,
												pic : pic,
												createTime : createTime,
												tag:tag,
												author:author,
												newsType:$('#newsType option:selected') .val(),
												description:$("#description").val()

											},
											url : "${pageContext.request.contextPath}/article/addArticleCommit",
											success : function(data) {
												if (data.errorFlags) {
													$("#articleTitleMessage").html("");
													$("#tagMessage").html("");
													$("#authorMessage").html("");
													$("#newsTypeMessage").html("");
													$("#newsDescriptionMessage").html("");
													$("#contentMessage").html("");
													$("#picMessage").html("");
													$("#createTimeMessage").html("");
													
													$("#articleTitleMessage").html(data.titleMessage);
													$("#tagMessage").html(data.tagMessage);
													$("#authorMessage").html(data.authorMessage);
													$("#newsTypeMessage").html(data.newsTypeMessage);
													$("#newsDescriptionMessage").html(data.descriptionMessage);
													$("#contentMessage").html(data.contentMessage);
													$("#picMessage").html(data.picMessage);
													$("#createTimeMessage").html(data.createTimeMessage);


												} else {
													amazeAlertSuccess(data.message);
													window
															.setTimeout(
																	function() {
																		if (id != null
																				&& id != "") {
																			$(
																					"#sx")
																					.submit();
																		} else {
																			window.location.href = "${pageContext.request.contextPath}/article/articleList";
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