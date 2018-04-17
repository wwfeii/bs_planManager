<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>计划管理系统</title>
    <link rel="stylesheet" href="/plan-manager/css/layui.css"/>
    <link rel="stylesheet" href="/plan-manager/css/modules/laydate/default/laydate.css">
    <link rel="stylesheet" href="/plan-manager/css/modules/layer/default/layer.css">
    <link rel="stylesheet" href="/plan-manager/css/modules/code.css">
    <link rel="stylesheet" href="/plan-manager/css/login.css">
    <link rel="stylesheet" href="/plan-manager/css/base.css">
    <script src="/plan-manager/js/login.js"></script>
	<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
	<script src="http://malsup.github.com/jquery.form.js"></script>

</head>
<body>
	<form id="mainForm" method="post">  
      <input type="text" name="userName" value="" />  
      <input type="text" name="age" value=""/>
       <input type="button" value="提交" id="agreementSub">  
	</form> 

<script type="text/javascript">
$("#agreementSub").click(function(){
	alert("zz");
	$('#mainForm').ajaxSubmit(      //ajax方式提交表单  
	         {  
	             url: 'http://localhost:8080/plan-manager/test2.action',  
	             type: 'post',  
	             dataType: 'json',  
	             beforeSubmit: function () {},  
	             success: function (data){
		             alert(data.code);  
		             alert(data.msg);
	                 if (data.code == 200) {  
	                 } else {  
	                     alert(data.msg);  
	                 }  
	             },
	             error:function(data){
		             alert(data.code);
		             },  
	             clearForm: false,//禁止清楚表单  
	             resetForm: false //禁止重置表单  
	         }); 
    return false;
})
</script>
</body>

</html>