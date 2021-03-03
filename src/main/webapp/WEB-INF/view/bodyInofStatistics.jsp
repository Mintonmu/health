<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用户身体信息统计</title>
    <script src="static/js/common-css.js"></script>
    <link rel="stylesheet" type="text/css" href="static/css/util/bootstrap-datetimepicker.min.css">
    <style type="text/css">
        .bar {
            padding-right: 40px;
            z-index: 1000;
        }

        .bar label {
            padding-left: 10px;
            padding-right: 10px;
        }

        .bar #min-expireTime {
            display: inline-block;
            width: 200px;
            margin-top: 3px;
        }

        .bar .btn-primary {
            margin-top: -5px;
        }

        .bar .btn-info {
            float: right;
        }

        #container {
            margin-top: 35px;
            padding: 5px;
            position: absolute;
            min-height: 100%;
            height: auto;
            width: 100%;
        }
    </style>
</head>
<body>
<div class="bar">
    <label>时间 </label>
    <input type="text" class="form-control time" id="min-expireTime">
    <button type="button" onclick="search(this)" class="btn btn-primary">搜索</button>
    <button type="button" onclick="init()" class="btn btn-info">刷新</button>
</div>
<div id="container"></div>
<script src="static/js/common-js.js"></script>
<script type="text/javascript" src="static/js/util/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="static/js/util/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="https://cdn.bootcdn.net/ajax/libs/echarts/5.0.1/echarts.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/echarts-gl/dist/echarts-gl.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/echarts-stat/dist/ecStat.min.js"></script>
<script type="text/javascript">
    $(".time").datetimepicker({
        language: "zh-CN",
        format: "yyyy-mm-dd", //显示格式
        minView: "month", //设置只显示到月份
        initialDate: new Date(), //初始化当前日期
        autoclose: true, //选中自动关闭
        todayBtn: true, //显示今日按钮
        clearBtn: true,
        pickerPosition: "bottom-left",
        endDate: new Date()
    });

    function search(e) {
        const minDate = $("#min-expireTime").val();
        init(minDate);
    }

    init();

    function init(date) {
        $.ajax({
            url: "${baseurl}/health/bodyInfoStatistics?" + (date ? ("date=" + date) : ''),
            type: 'GET',
            success: function (data) {
                console.log(data);
                if (data.result == true) {
                    console.log(data)
                    let option = {
                        title: {
                            text: '健康信息图'
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'cross',
                                label: {
                                    backgroundColor: '#6a7985'
                                }
                            }
                        },
                        legend: {
                            data: data.data.typeNames
                        },
                        toolbox: {
                            feature: {
                                saveAsImage: {}
                            }
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        xAxis: [{
                            type: 'category',
                            boundaryGap: false,
                            data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
                        }],
                        yAxis: [{
                            type: 'value'
                        }]
                    };

                    const bodyInfos = data.data.bodyInfoVos;

                    let series = [];
                    for (var i = 0; i < bodyInfos.length; i++) {
                        series.push({
                            name: bodyInfos[i].typeName,
                            type: 'line',
                            stack: '总量',
                            areaStyle: {},
                            data: bodyInfos[i].datas
                        });
                    }
                    option["series"] = series;

                    if (option && typeof option === "object") {
                        echarts.init(document.getElementById("container")).setOption(option);
                    }
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
