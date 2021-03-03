<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>头部</title>
		<script src="static/js/common-css.js"></script>
		<script type="text/javascript" src="static/js/common-js.js"></script>
		<script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
		<script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
		<script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>

		<style type="text/css">
			div.container {
				background: #0088ff;
				height: 60px;
				max-width: 100%;
			}
			div.container h3 {
				float: left;
				margin-top: 10px;
				margin-left: 10%;
			}
			div.container h3 a {
				color: #ffffff;
			}
			div.container h3 a:hover {
				text-decoration: none;
			}
			div.container .dropdown {
				float: right;
				margin-top: 5px;
				margin-right: 5%;
			}
			div.container span img {
				width: 50px;
				height: 50px;
				border-radius: 40px;
			}
		</style>
	</head>
	<body>
		<div class="container">
			<h3><a href="home">个人健康信息管理</a></h3>
			<div class="dropdown">
				<span class="dropdown-toggle" data-toggle="dropdown"><img src="${ user.headurl }" /></span>
				<div class="dropdown-menu">
					<a class="dropdown-item" href="#" onclick="ifram_info('userInfo')">个人信息</a>
					<a id="logout" class="dropdown-item" href="user/logout">退出登录</a>
				</div>
			</div>
		</div>
	</body>
</html>
