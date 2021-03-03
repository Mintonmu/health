<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="base.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>修改密码</title>
		<script type="text/javascript" src="static/js/common-css.js"></script>
		<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,700">
		<style type="text/css">
			.bg-white {
				padding-left: 20px;
				padding-top: 20px;
			}

			.bg-white .input-material {
				padding-left: 10px;
			}
		</style>
	</head>
	<body>
		<div class="col-lg-6 bg-white">
			<div class="form d-flex align-items-center">
				<div class="content">
					<div class="form-group">
						<input id="register-username" class="input-material" type="text" name="username" placeholder="请输入用户名" disabled="disabled"
						 value="${ user.username }">
					</div>
					<div class="form-group">
						<input id="register-password" class="input-material" type="password" name="passWord" placeholder="请输入密码">
						<div class="invalid-feedback">
							密码必须在6~10位之间
						</div>
					</div>
					<div class="form-group">
						<input id="register-passwords" class="input-material" type="password" name="confirmPassWord" placeholder="确认密码">
						<div class="invalid-feedback">
							两次密码必须相同 且在6~10位之间
						</div>
					</div>
					<div class="form-group">
						<button id="regbtn" type="button" name="registerSubmit" class="btn btn-primary">确定</button>
					</div>
				</div>
			</div>
		</div>
		<script src="static/js/common-js.js"></script>
		<script>
			$(function() {
				/*错误class  form-control is-invalid
				正确class  form-control is-valid*/
				var flagPas = false;
				var flagPass = false;
				var passWord, passWords;
				/*验证密码*/
				$("#register-password").change(function() {
					passWord = $("#register-password").val();
					if (passWord.length < 6 || passWord.length > 18) {
						$("#register-password").removeClass("form-control is-valid")
						$("#register-password").addClass("form-control is-invalid");
						flagPas = false;
					} else {
						$("#register-password").removeClass("form-control is-invalid")
						$("#register-password").addClass("form-control is-valid");
						flagPas = true;
					}
				})
				/*验证确认密码*/
				$("#register-passwords").change(function() {
					passWords = $("#register-passwords").val();
					if ((passWord != passWords) || (passWords.length < 6 || passWords.length > 18)) {
						$("#register-passwords").removeClass("form-control is-valid")
						$("#register-passwords").addClass("form-control is-invalid");
						flagPass = false;
					} else {
						$("#register-passwords").removeClass("form-control is-invalid")
						$("#register-passwords").addClass("form-control is-valid");
						flagPass = true;
					}
				})

				$("#regbtn").click(function() {
					if (flagPas && flagPass) {
						$.ajax({
							url: "${baseurl}/user/updatePassword",
							type: 'PUT',
							dataType: "json",
							contentType: "application/json",
							data: JSON.stringify({
								'passWord': passWord,
								'confirmPassWord': passWords
							}),
							success: function(data) {
								alert(data.message)
								if (data.result == true) {
									localStorage.removeItem("passWord");
									localStorage.removeItem("check1");
									localStorage.removeItem("user");
									$("#register-password").val("");
									$("#register-password").removeClass("is-valid");
									$("#register-passwords").val("");
									$("#register-passwords").removeClass("is-valid");
									top.location.href = 'login';
								}
							}
						});
					} else {
						if (!flagPas) {
							$("#register-password").addClass("form-control is-invalid");
						}
						if (!flagPass) {
							$("#register-passwords").addClass("form-control is-invalid");
						}
					}
				})
			})
		</script>
	</body>
</html>
