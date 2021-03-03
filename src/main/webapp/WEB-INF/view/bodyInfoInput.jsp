<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="base.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>身体信息录入</title>
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
						<span>昵称</span> <input id="body-nickname" class="input-material" type="text" name="nickname" disabled="disabled"
						 value="${ user.nickname }">
					</div>
					<div class="form-group">
						<span>舒张压</span> <input id="body-low-blood-pressure" class="input-material" type="text" oninput="value=value.replace(/[^\d]/g,'')"
						 name="lowbloodpressure" placeholder="请输入舒张压">
						<div class="invalid-feedback">舒张压必须在0~200之间</div>
					</div>
					<div class="form-group">
						<span>收缩压</span> <input id="body-high-blood-pressure" class="input-material" type="text" oninput="value=value.replace(/[^\d]/g,'')"
						 name="highbloodpressure" placeholder="请输入收缩压">
						<div class="invalid-feedback">收缩压必须在0~200之间</div>
					</div>
					<div class="form-group">
						<span>心率</span> <input id="body-heart-rate" class="input-material" type="text" oninput="value=value.replace(/[^\d]/g,'')"
						 name="heartrate" placeholder="请输入心率">
						<div class="invalid-feedback">心率必须在40~200之间</div>
					</div>
					<div class="form-group">
						<span>体温</span> <input id="body-temperature" class="input-material" type="text" onkeyup="clearNoNum(this)"
						 oninput="value=value.replace(/[^\d.]/g,'')" name="temperature" placeholder="请输入体温">
						<div class="invalid-feedback">体温必须在33~45度之间</div>
					</div>
					<div class="form-group">
						<span>食欲</span> <select id="body-appetite" class="input-material form-control" name="appetite">
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
						<span>体重KG</span> <input id="body-weight" class="input-material" type="text" onkeyup="clearNoNum(this)" oninput="value=value.replace(/[^\d.]/g,'')"
						 name="weight" placeholder="请输入体重">
						<div class="invalid-feedback">体重必须在0~200之间</div>
					</div>
					<div class="form-group">
						<span>步数</span> <input id="body-number-of-step" class="input-material" type="text" oninput="value=value.replace(/[^\d]/g,'')"
						 name=numberofstep placeholder="请输入步数">
					</div>
					<div class="form-group">
						<button id="submit" type="button" name="submit" class="btn btn-primary">打卡</button>
						<button id="reset" type="reset" name="reset" class="btn btn-primary" style="margin-left: 40px">重置</button>
					</div>
				</div>
			</div>
		</div>
		<script src="static/js/common-js.js"></script>
		<script>
			$(function() {
				/*错误class  form-control is-invalid
				正确class  form-control is-valid*/
				var flaglowbloodpressure = false;
				var flaghighbloodpressure = false;
				var flagheartrate = false;
				var flagTemperature = false;
				var flagAppetite = false;
				var flagWeight = false;
				var lowbloodpressure, highbloodpressure, heartrate, temperature, appetite, weight;
				$("#body-low-blood-pressure").change(function() {
					lowbloodpressure = $(this).val();
					if (lowbloodpressure < 0 || lowbloodpressure > 200) {
						$(this).removeClass("form-control is-valid").addClass("form-control is-invalid");
						flaglowbloodpressure = false;
					} else {
						$(this).removeClass("form-control is-invalid").addClass("form-control is-valid");
						flaglowbloodpressure = true;
					}
				})
				$("#body-high-blood-pressure").change(function() {
					highbloodpressure = $(this).val();
					if (highbloodpressure < 0 || highbloodpressure > 200) {
						$(this).removeClass("form-control is-valid").addClass("form-control is-invalid");
						flaghighbloodpressure = false;
					} else {
						$(this).removeClass("form-control is-invalid").addClass("form-control is-valid");
						flaghighbloodpressure = true;
					}
				})
				$("#body-heart-rate").change(function() {
					heartrate = $(this).val();
					if (heartrate < 40 || heartrate > 200) {
						$(this).removeClass("form-control is-valid").addClass("form-control is-invalid");
						flagheartrate = false;
					} else {
						$(this).removeClass("form-control is-invalid").addClass("form-control is-valid");
						flagheartrate = true;
					}
				})
				$("#body-temperature").change(function() {
					temperature = $(this).val();
					if (temperature < 33 || temperature > 45) {
						$(this).removeClass("form-control is-valid").addClass("form-control is-invalid");
						flagTemperature = false;
					} else {
						$(this).removeClass("form-control is-invalid").addClass("form-control is-valid");
						flagTemperature = true;
					}
				})
				$("#body-appetite").change(function() {
					appetite = $(this).val();
					if (!appetite || appetite == '-请选择-') {
						$(this).removeClass("form-control is-valid").addClass("form-control is-invalid");
						flagAppetite = false;
					} else {
						$(this).removeClass("form-control is-invalid").addClass("form-control is-valid");
						flagAppetite = true;
					}
				})
				$("#body-weight").change(function() {
					weight = $(this).val();
					if (!weight || weight <0 || weight > 200) {
						$(this).removeClass("form-control is-valid").addClass("form-control is-invalid");
						flagWeight = false;
					} else {
						$(this).removeClass("form-control is-invalid").addClass("form-control is-valid");
						flagWeight = true;
					}
				})

				
				$("#submit").click(function() {
					if (flaglowbloodpressure && flaghighbloodpressure && flagheartrate && flagTemperature && flagAppetite && flagWeight) {
						$.ajax({
							url: "${baseurl}/health/saveBodyInfo",
							type: 'POST',
							dataType: "json",
							contentType: "application/json",
							data: JSON.stringify({
								'lowbloodpressure': lowbloodpressure,
								'highbloodpressure': highbloodpressure,
								'heartrate': heartrate,
								'temperature': temperature,
								'appetite': appetite,
								'weight': weight,
								'numberofstep': $("#body-number-of-step").val()
							}),
							success: function(data) {
								alert(data.message)
								if (data.result == true) {
									$("input").each(function() {
										$(this).removeClass("form-control is-invalid").val("");
									});
									$("#body-appetite").removeClass("is-valid is-invalid").val("-请选择-");
									$("#body-nickname").val('${user.nickname}');
								} else {
									if (data.path) {
										top.location.href=data.path;
									}
								}
							}
						});
					} else {
						if (!flaglowbloodpressure) {
							$("#body-low-blood-pressure").addClass("form-control is-invalid");
						}
						if (!flaghighbloodpressure) {
							$("#body-high-blood-pressure").addClass("form-control is-invalid");
						}
						if (!flagheartrate) {
							$("#body-heart-rate").addClass("form-control is-invalid");
						}
						if (!flagTemperature) {
							$("#body-temperature").addClass("form-control is-invalid");
						}
						if (!flagAppetite) {
							$("#body-appetite").addClass("form-control is-invalid");
						}
						if (!flagWeight) {
							$("#body-weight").addClass("form-control is-invalid");
						}
					}
				})
				// 重置
				$("#reset").click(function() {
					$("input").each(function() {
						$(this).removeClass("form-control is-invalid").val("");
					});
					$("#body-appetite").removeClass("is-valid is-invalid").val("-请选择-");
					$("#body-nickname").val('${user.nickname}');
				})
			})
		</script>
	</body>
</html>
