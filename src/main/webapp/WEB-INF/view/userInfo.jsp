<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="base.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>用户信息</title>
		<script type="text/javascript" src="static/js/common-css.js"></script>
		<style type="text/css">
			#form_content {
				text-align: center;
				padding: 20px 0px 20px 35%;
			}
			.content {
				width: 100%;
				padding: 20px 0px;
			}
			.content .form-group span {
				display: inline-block;
				line-height: 30px;
				padding-right: 20px;
			}
			.content .form-group input {
				width: 250px;
				padding-left: 5px;
			}
			.content .form-group input#body-headurl {
				font-size: 10px;
				border-bottom: none;
			}
			.btn_clar {
				display: none;
			}
		</style>
	</head>
	<body>
		<div id="form_content">
			<div class="col-lg-6 bg-white">
				<div class="form d-flex align-items-center">
					<div class="content">
						<div class="form-group">
							<span>昵称</span> <input id="body-nickname" class="input-material" type="text" name="nickname" disabled="disabled"
							 value="${ user.nickname }">
							 <div class="invalid-feedback">昵称必须在2~10之间</div>
						</div>
						<div class="form-group">
							<span>账号</span> <input id="body-username" class="input-material" type="text" name="username" disabled="disabled"
							 value="${ user.username }">
						</div>
						<div class="form-group">
							<span>邮箱</span> <input id="body-email" class="input-material" type="email" name="email" disabled="disabled"
							 value="${ user.email }">
							 <div class="invalid-feedback">邮箱不能为空</div>
						</div>
						<div class="form-group">
							<span>头像</span> <input id="body-headurl" class="input-material" type="file" name="headurl" disabled="disabled"
							 value="${ user.email }">
							 <div class="invalid-feedback">请选择头像文件</div>
						</div>
						
						<div class="form-group">
							<button id="update" type="button" name="update" class="btn btn-primary">修改信息</button>
							<button id="submit" type="button" name="submit" class="btn btn-primary btn_clar">确认修改</button>
							<button id="reset"type="reset" name="reset" class="btn btn-primary btn_clar" style="margin-left: 40px">重置</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script src="static/js/common-js.js"></script>
		<script>
			$(function() {
				/*错误class  form-control is-invalid
				正确class  form-control is-valid*/
				var nickname = '${user.nickname}';
				var flagnickname = nickname;
				var flagheadurl = false;
				var headurl;
				$("#body-nickname").change(function() {
					nickname = $(this).val();
					console.log(nickname)
					if (nickname.length < 2 || nickname.length > 10) {
						$(this).removeClass("form-control is-valid").addClass("form-control is-invalid").css("display", "inline-block");
						flagnickname = false;
					} else {
						$(this).removeClass("form-control is-invalid").addClass("form-control is-valid").css("display", "inline-block");
						flagnickname = true;
					}
				})
				$("#body-headurl").change(function() {
					headurl = $(this).val();
					if (!headurl) {
						$(this).removeClass("form-control is-valid").addClass("form-control is-invalid").css("display", "inline-block");
						flagheadurl = false;
					} else {
						$(this).removeClass("form-control is-invalid").addClass("form-control is-valid").css("display", "inline-block");
						flagheadurl = true;
					}
				})

				$("#update").click(function() {
					$("#body-nickname").removeAttr("disabled", 'none');
					$("#body-email").removeAttr("disabled", 'none');
					$("#body-headurl").removeAttr("disabled", 'none');
					$("#submit").removeClass("btn_clar");
					$("#reset").removeClass("btn_clar");
					$(this).addClass("btn_clar");
				});
				
				$("#submit").click(function() {
					if (flagnickname && flagheadurl) {
						const oFiles = document.getElementById("body-headurl").files;
						var fromData = new FormData();
						fromData.append('nickname', nickname);
						fromData.append('headurl', oFiles[0]);
						fromData.append('email', $("#body-email").val());
						$.ajax({
							url: "${baseurl}/user/updateUserInfo",
							type: 'POST',
							data: fromData,
							contentType: false,
							processData: false,
							success: function(data) {
								alert(data.message)
								if (data.result == true) {
									localStorage.setItem("user", JSON.stringify(data.data));
									location.href='userInfo';
									window.parent.$(".dropdown-toggle img").attr('src', data.data.headurl);
								} else {
									if (data.path) {
										top.location.href=data.path;
									}
								}
							}
						});
					} else {
						if (!flagnickname) {
							$("#body-nickname").addClass("form-control is-invalid").css("display", "inline-block");
						}
						if (!flagheadurl) {
							$("#body-headurl").addClass("form-control is-invalid").css("display", "inline-block");
						}
					}
				})
				// 重置
				$("#reset").click(function() {
					$("input").each(function() {
						$(this).removeClass("form-control is-invalid").val("");
					});
					$("#body-nickname").val('${user.nickname}');
					$("#body-username").val('${user.username}');
					$("#body-email").val('${user.email}');
				})
			})
		</script>
	</body>
</html>
