<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="base.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>首页</title>
		<script src="static/js/common-css.js"></script>
		<script type="text/javascript" src="static/js/common-js.js"></script>
		<style type="text/css">
			.content-top {}

			.content-left {
				position: absolute;
				min-height: 93.6%;
				height: auto;
				float: left;
				width: 10%;
				background: #0088ff;
				padding-top: 12px;
				border-top: 1px solid rgba(0, 0, 0, .125);
			}

			.content-left .list-group-item {
				text-align: center;
				padding-left: 10px;
				background: #0088ff;
				color: #ffffff;
			}

			.content-right {
				float: right;
				width: 90%;
				background: #f8f8f8;
				padding: 15px 15px 0px 15px;
				overflow: auto;
			}
			
			.body-desc {
				color: red;
				font-size: 12px;
				margin-left: 10px;
				line-height: 24px;
			}
		</style>
	</head>
	<body>
		<div style="width: 100%;">
			<!-- 头部栏 -->
			<div class="content-top">
				<jsp:include page="top.jsp" />
			</div>

			<!-- 菜单栏 -->
			<div class="content-left">
				<div class="list-group">
					<a href="#" url="bodyInfoInput" class="list-group-item">健康打卡</a>
					<a href="#" url="bodyInofList" class="list-group-item">历史记录</a>
					<a href="#" url="bodyInofStatistics" class="list-group-item">健康报告</a>
					<a href="#" url="updatePassword" class="list-group-item">修改密码</a>
				</div>
			</div>

			<!-- 内容栏 -->
			<div class="content-right">
				<div class="breadcrumbs" id="breadcrumbs">
					<!-- 面包屑导航 -->
					<ul class="breadcrumb">
						<li>
							<a href="home">Home</a>
						</li>
						<li class="active">/个人信息</li>
					</ul>
				</div>
				<!-- 内容展示页 -->
				<div>
					<iframe id="iframe-page-content" src="userInfo" width="100%" frameborder="no" border="0" marginwidth="0"
					 marginheight=" 0" allowtransparency="yes" scrolling="auto" onload="changeFrameHeight(this)"></iframe>
				</div>
			</div>
		</div>

		<!-- 模态框（Modal） -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="myModalLabel">
							健康信息详情
						</h4>
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<input type="hidden" id="model_in" />
					</div>
					<div class="modal-body">
						<div class="form-group">
							<span>舒张压</span><span class="body-desc" id="body-low-blood-pressure-desc"></span> <input id="body-low-blood-pressure" disabled class="input-material" type="text" oninput="value=value.replace(/[^\d]/g,'')"
							 name="lowbloodpressure" placeholder="请输入舒张压">
							<div class="invalid-feedback">舒张压必须在0~200之间</div>
						</div>
						<div class="form-group">
							<span>收缩压</span><span class="body-desc" id="body-high-blood-pressure-desc"></span> <input id="body-high-blood-pressure" disabled class="input-material" type="text" oninput="value=value.replace(/[^\d]/g,'')"
							 name="highbloodpressure" placeholder="请输入收缩压">
							<div class="invalid-feedback">收缩压必须在0~200之间</div>
						</div>
						<div class="form-group">
							<span>心率</span><span class="body-desc" id="body-heart-rate-desc"></span> <input id="body-heart-rate" disabled class="input-material" type="text" oninput="value=value.replace(/[^\d]/g,'')"
							 name="heartrate" placeholder="请输入心率">
							<div class="invalid-feedback">心率必须在40~200之间</div>
						</div>
						<div class="form-group">
							<span>体温</span><span class="body-desc" id="body-temperature-desc"></span> <input id="body-temperature" disabled class="input-material" type="text" onkeyup="clearNoNum(this)"
							 oninput="value=value.replace(/[^\d.]/g,'')" name="temperature" placeholder="请输入体温">
							<div class="invalid-feedback">体温必须在33~40度之间</div>
						</div>
						<div class="form-group">
							<span>食欲</span> <select id="body-appetite" disabled class="input-material form-control" name="appetite">
								<option>-请选择-</option>
								<option value="1">非常差</option>
								<option value="2">差</option>
								<option value="3">一般</option>
								<option value="4">良好</option>
								<option value="5">很多</option>
								<option value="6">非常多</option>
							</select>
							<div class="invalid-feedback">请选择食欲</div>
						</div>
						<div class="form-group">
							<span>体重KG</span> <input id="body-weight" disabled class="input-material" type="text" onkeyup="clearNoNum(this)" oninput="value=value.replace(/[^\d.]/g,'')"
							 name="weight" placeholder="请输入体重">
							<div class="invalid-feedback">体重必须在0~200之间</div>
						</div>
						<div class="form-group">
							<span>步数</span> <input id="body-number-of-step" disabled class="input-material" type="text" oninput="value=value.replace(/[^\d]/g,'')"
							 name=numberofstep placeholder="请输入步数">
						</div>
					</div>
					<div class="modal-footer">
						<span class="body-desc" id="body-blog-pressure" style="position: absolute; left: 0px; margin-left: 20px;"></span>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>

		<script type="text/javascript">
			$(function() {
				var height = document.documentElement.clientHeight;
				document.getElementsByName("content-right").style.height = height + 'px';
			});
			// 菜单按钮样式
			var oldBackground;
			$(".list-group-item").on('click', function() {
				$(this).css('background', '#add3f7').siblings().css('background', '#0088ff');
				oldBackground = $(this).css("background");
				$("#iframe-page-content").attr('src', $(this).attr("url"));
				$(".active").text('/' + $(this).text());
			});
			$(".list-group-item").hover(function() {
				oldBackground = $(this).css("background");
				$(this).css('background', '#add3f7');
			}, function() {
				$(this).css('background', oldBackground);
			});
			// 用户信息按钮
			function ifram_info(e) {
				$(".active").text('/个人信息');
				$("#iframe-page-content").attr('src', e);
				$(".list-group-item").css('background', '#0088ff');
			}
			// 自适应页面高度iframe
			function changeFrameHeight(that) {
				//电脑屏幕高度-iframe上面其他组件的高度
				$(that).height(document.documentElement.clientHeight - 146);
			}
		</script>
	</body>
</html>