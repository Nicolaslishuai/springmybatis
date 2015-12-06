<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>首页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="./js/angularjs/angular-csp.css" rel="stylesheet" type="text/css">
	<link href="./js/bootstrp3.3.5/css/bootstrap-theme.css" rel="stylesheet" type="text/css">
	<link href="./js/bootstrp3.3.5/css/bootstrap.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="./js/bootstrp3.3.5/js/jquery-2.1.0.js"></script>
    <script type="text/javascript" src="./js/bootstrp3.3.5/js/bootstrap.js"></script>	
    <script type="text/javascript" src="./js/angularjs/angular.js"></script>
    <script type="text/javascript" src="./js/angularjs/angular-resource.js"></script>
    <script type="text/javascript" src="./js/angularjs/angular-ui-router.js"></script>
    <script type="text/javascript" src="./js/angularjs/angular-sanitize.js"></script>
    <script type="text/javascript" src="./app/js/app.js"></script>
  </head>
  
  <body ng-app="myapp">
     <div class="continer" ui-view></div>
  </body>
</html>
