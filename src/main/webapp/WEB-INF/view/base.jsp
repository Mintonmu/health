<!-- 静态导入和动态导入jsp，该jsp都后一步执行编译，导致全局设置jsp编码失效；引用界面使用变量，如果动态include，页面无法获取到变量 -->
<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="baseurl" value="${pageContext.request.contextPath}" />
<c:set var="user" value="${sessionScope.user}" />
