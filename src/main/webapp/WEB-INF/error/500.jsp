<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>错误页面</title>
</head>
<body>
<% System.out.print(request.getAttribute("message")); %>
</body>
</html>