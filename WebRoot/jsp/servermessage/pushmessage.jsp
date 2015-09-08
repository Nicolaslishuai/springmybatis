<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>消息推送</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="消息推送">
    <link href="js/bootstrp3.3.5/css/bootstrap.css" rel="stylesheet" type="text/css" charset="UTF-8"/>
    <link href="js/bootstrp3.3.5/css/bootstrap-theme.css" rel="stylesheet" />
    <script src="js/bootstrp3.3.5/js/jquery-2.1.0.js" type="text/javascript"></script>
    <script src="js/bootstrp3.3.5/js/bootstrap.js" type="text/javascript"></script>
  </head>
  <body>
    <div class="container">
     <div class="row">
      <div id="resivemessage" class="col-*-*">
      
      </div>      
    </div>
  </div>
<!-- 模态框（Modal） -->
<div id="identifier" class="modal fade" id="myModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
               警告
            </h4>
         </div>
         <div class="modal-body">
            不支持EventSource
         </div>
      </div><!-- /.modal-content -->
</div><!-- /.modal -->
</body>
<script type="text/javascript">
$(function(){
 if(typeof(EventSource)!=="undefined"){
   var source=new EventSource("<%=basePath%>/message.ms?ID=123");
   source.addEventListener('update', function(e) {
      console.log(e.data+"update");
   });
  }
 else{
   $('#identifier').modal('show');
  }


});
</script>
</html>
