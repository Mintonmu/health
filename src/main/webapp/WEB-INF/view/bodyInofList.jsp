<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>用户身体信息列表</title>
		<script src="static/js/common-css.js"></script>
		<link rel="stylesheet" type="text/css" href="static/css/util/bootstrap-datetimepicker.min.css">
		<script src="static/js/common-js.js"></script>
		<script type="text/javascript" src="static/js/util/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
		<script type="text/javascript" src="static/js/util/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>

		<style type="text/css">
			div.search {
				padding: 5px;
			}

			div.search label {
				padding-left: 10px;
				padding-right: 10px;
			}

			div.search .time {
				display: inline-block;
				width: 200px;
				margin-top: 3px;
			}

			div.search button {
				margin-top: -5px;
			}

			.body-list tr th {
				text-align: center;
			}

			.body-list tbody tr td {
				text-align: center;
				line-height: 40px;
				padding: 2px;
			}

			.body-list tfoot span {
				border: 1px solid #ececec;
				width: 15px;
				padding-left: 10px;
				padding-right: 16px;
				text-align: center;
				line-height: 25px;
			}
		</style>
	</head>
	<body>
		<div class="body-list">
			<div class="search">
				<label>打卡时间 </label>
				<input type="text" class="form-control time" id="min-expireTime"> ~
				<input type="text" class="form-control time" id="max-expireTime">
				<button type="button" onclick="search(this)" class="btn btn-primary">搜索</button>
				<button type="button" onclick="list(1, 10)" class="btn btn-info">刷新</button>
			</div>
			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th>序号</th>
						<th>打卡时间</th>
						<th>收缩压</th>
						<th>舒张压</th>
						<th>心率</th>
						<th>体温</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="body"></tbody>
				<tfoot>
					<tr>
						<td id="dataInfo" colspan="6" style="text-align: right; border-right: 0px; line-height: 28px;">1/2</td>
						<td id="pageInfo" style="text-align: center; border-left: 0px;">
						</td>
					</tr>
				</tfoot>
			</table>
		</div>

		<!-- JavaScript files-->
		<script>
			var pageSize = 10;
			$(function() {
				$(".time").datetimepicker({
					language: "zh-CN",
					format: "yyyy-mm-dd", //显示格式
					minView: "month", //设置只显示到月份
					initialDate: new Date(), //初始化当前日期
					autoclose: true, //选中自动关闭
					todayBtn: true, //显示今日按钮
					clearBtn: true,
					pickerPosition: "bottom-left"
				});

				$("#min-expireTime").on('change', function(ev) {
					var startDate = $('#min-expireTime').val();
					$("#max-expireTime").datetimepicker('setStartDate', startDate);
					$("#min-expireTime").datetimepicker('hide');
				});
				$("#max-expireTime").on('change', function(ev) {
					var startDate = $('#max-expireTime').val();
					$("#min-expireTime").datetimepicker('setReturnDate', startDate);
					$("#max-expireTime").datetimepicker('hide');
				});

				list(1, pageSize);
			})

			function list(page, pageSize, minDate, maxDate) {
				$.ajax({
					url: "${baseurl}/health/bodyInfoList?page=" + page + "&pageSize=" + pageSize + (minDate ? ("&minDate=" + minDate) :
						'') + (maxDate ? ("&maxDate=" + maxDate) : ''),
					type: 'GET',
					success: function(data) {
						if (data.result == false) {
							alert(data.message)
							return;
						}
						var content = "";
						const list = data.data.content;
						const page = data.data.page;
						const totalPage = data.data.totalPages;
						for (var i = 0; i < list.length; i++) {
							content += "<tr>" +
								"<td target=" + list[i].id + ">" + ((page - 1) * pageSize + i + 1) + "</td>" +
								"<td>" + dateFormat('YYYY-mm-dd HH:MM', new Date(list[i].createtime)) + "</td>" +
								"<td>" + list[i].lowbloodpressure + "</td>" +
								"<td>" + list[i].highbloodpressure + "</td>" +
								"<td>" + list[i].heartrate + "</td>" +
								"<td>" + list[i].temperature + "</td>" +
								"<td><button onclick='detailBodyInfo(" + list[i].id + ")' type='button' class='btn btn-info'>详情</button> " +
								"<button  onclick='deleteBodyInfo(" + list[i].id + ", " + page + ", " + data.data.numberofelements + ", " +
								totalPage + ")' type='button' class='btn btn-danger'>删除</button></td>" +
								"</tr>";
						}
						$("#body").empty().append(content);
						$("#dataInfo").text("共" + data.data.totalElements + "条  " + data.data.page + "/" + data.data.totalPages + "页");
						$("#pageInfo").html('<a href="#" onclick="pagelist(1, ' + totalPage + ', 0)"><span>|&laquo;</span></a>' +
							' <a href="#" onclick="pagelist(' + page + ', ' + totalPage + ', -1)"><span>&laquo;</span></a>' +
							' <a href="#" onclick="pagelist(' + page + ', ' + totalPage + ', 1)"><span>&raquo;</span></a>' +
							' <a href="#" onclick="pagelist(' + totalPage + ', ' + totalPage + ', 0)"><span>&raquo;|</span></a>');
					}
				});
			}

			function pagelist(page, totalPage, criticality) {
				const newPage = page + criticality;
				if (newPage < 1) {
					return;
				}
				if (newPage > totalPage) {
					return;
				}
				list(newPage, pageSize);
			}

			function deleteBodyInfo(id, page, numberOfElements, totalPage) {
				$.ajax({
					url: "${baseurl}/health/" + id,
					type: 'DELETE',
					success: function(data) {
						alert(data.message)
						// 当前页临界值处理
						if (numberOfElements == 1) {
							if (totalPage > 1 && page > 1) {
								page = page - 1;
							}
						}
						list(page, pageSize);
					}
				});
			}

			function search(e) {
				const minDate = $("#min-expireTime").val();
				const maxDate = $("#max-expireTime").val();
				list(1, pageSize, minDate, maxDate);
			}

			function detailBodyInfo(id) {
				const parent = window.parent;
				parent.$('#model_in').val(id);
				parent.$('#myModal').modal('show');

				$.ajax({
					url: "${baseurl}/health/" + id,
					type: 'GET',
					success: function(data) {
						if (data.result == true) {
							parent.$("#body-low-blood-pressure").val(data.data.lowbloodpressure);
							parent.$("#body-low-blood-pressure-desc").text(data.data.lowbloodpressuredesc);
							parent.$("#body-high-blood-pressure").val(data.data.highbloodpressure);
							parent.$("#body-high-blood-pressure-desc").text(data.data.highbloodpressuresesc);
							parent.$("#body-heart-rate").val(data.data.heartrate);
							parent.$("#body-heart-rate-desc").text(data.data.heartratedesc);
							parent.$("#body-temperature").val(data.data.temperature);
							parent.$("#body-temperature-desc").text(data.data.temperaturedesc);
							parent.$("#body-appetite").val(data.data.appetite);
							parent.$("#body-weight").val(data.data.weight);
							parent.$("#body-number-of-step").val(data.data.numberofstep);
							parent.$("#body-blog-pressure").text(data.data.bloodpressuredesc);
						} else {
							alert(data.message)
							if (data.path) {
								top.location.href = data.path;
							}
						}
					}
				});
			}
		</script>
	</body>
</html>
