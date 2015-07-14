<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="./js/jquery/jquery-2.1.0.js" type="text/javascript"></script>
  </head>
  
  <body>
    <form action="default/user_selectUserlist.do">
     <input type="text" name="age" >
     <br>
     <button>SUBMIT</button>
    </form>
    <hr>
    <a href="jsp/login/login.jsp">LOGIN</a>
    <a href="jsp/admin/index.jsp">ADMIN</a>
    <a href="jsp/user/index.jsp">USER</a>
    <a href="spring_logout">注销</a>
  </body>
  <script type="text/javascript">
   $(function(){
   

   });
  </script>
</html>
