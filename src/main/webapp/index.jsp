<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="WEB-INF/view/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>登录</title>
		<script src="static/js/common-css.js"></script>
		<link rel="stylesheet" href="static/css/login.css">
		<script type="text/javascript">
			// 防止退出登录后又页面回退带来的问题
			const referer = '<%=request.getHeader("Referer")%>';
			const target = '<%=request.getParameter("target")%>';
			function getreferer() {
				if ((!target || target == 'null') && referer && referer.indexOf("/home") != -1) {
					top.location.href = 'login';
				}
			}
		</script>
	</head>
	<body onload="getreferer()">
		<div class="page login-page">
			<div class="container d-flex align-items-center">
				<div class="form-holder has-shadow">
					<div class="row">
						<!-- Logo & Information Panel-->
						<div class="col-lg-6">
							<div class="info d-flex align-items-center">
								<div class="content">
									<div class="logo">
										<h1>欢迎登录</h1>
									</div>
									<p>个人健康信息管理系统</p>
								</div>
							</div>
						</div>
						<!-- Form Panel    -->
						<div class="col-lg-6 bg-white">
							<div class="form d-flex align-items-center">
								<div class="content">
									<form class="form-validate" id="loginFrom">
										<div class="form-group">
											<input id="login-username" type="text" name="username" required data-msg="请输入用户名" placeholder="用户名" value=""
											 class="input-material">
										</div>
										<div class="form-group">
											<input id="login-password" type="password" name="passWord" required data-msg="请输入密码" placeholder="密码" class="input-material">
										</div>
										<button id="login" class="btn btn-primary">登录</button>
										<div style="margin-top: -40px;">
											<!-- <input type="checkbox"  id="check1"/>&nbsp;<span>记住密码</span>
		                    	<input type="checkbox" id="check2"/>&nbsp;<span>自动登录</span> -->
											<div class="custom-control custom-checkbox " style="float: right;">
												<input type="checkbox" class="custom-control-input" id="check2"> <label class="custom-control-label" for="check2">自动登录</label>
											</div>
											<div class="custom-control custom-checkbox " style="float: right;">
												<input type="checkbox" class="custom-control-input" id="check1"> <label class="custom-control-label" for="check1">记住密码&nbsp;&nbsp;</label>
											</div>
										</div>
									</form>
									<br /> <small>没有账号?</small><a href="register" class="signup">&nbsp;注册</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- JavaScript files-->
		<script src="static/js/common-js.js"></script>
		<script src="static/js/util/jquery.validate.min.js"></script>
		<script src="static/js/login.js"></script>
		<!--表单验证-->
		<!-- Main File-->
		<script>
			$(window).load(function() {
				/*判断上次是否勾选记住密码和自动登录*/
				var check1s = localStorage.getItem("check1");
				var check2s = sessionStorage.getItem("check2");
				var oldName = localStorage.getItem("username");
				var oldPass = localStorage.getItem("passWord");
				if (check1s == "true") {
					$("#login-username").val(oldName);
					$("#login-password").val(oldPass);
					$("#check1").prop('checked', true);
				} else {
					$("#login-username").val('');
					$("#login-password").val('');
					$("#check1").prop('checked', false);
				}
				if (check2s == "true") {
					$("#check2").prop('checked', true);
					$("#loginFrom").submit();
				} else {
					$("#check2").prop('checked', false);
				}
			});
			$("#loginFrom").submit(function(e) {
				var username = $("#login-username").val();
				var passWord = $("#login-password").val();
				$.ajax({
					url: "${baseurl}/user/login",
					type: 'POST',
					data: {
						'username': username,
						'passWord': passWord
					},
					success: function(data) {
						if (data.result == true) {
							localStorage.setItem("user", JSON.stringify(data.data))
							localStorage.setItem("username", username);
							localStorage.setItem("passWord", passWord);
							var check1 = $("#check1").prop('checked');
							var check2 = $('#check2').prop('checked');
							localStorage.setItem("check1", check1);
							sessionStorage.setItem("check2", check2);
							window.location.href = 'home';
						} else {
							alert(data.message)
						}
					}
				});
				return false;
			});
		</script>
	</body>
</html>
