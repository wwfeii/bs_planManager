<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0 user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="/plan-manager/css/layui.css"/>
    <link rel="stylesheet" href="/plan-manager/css/modules/laydate/default/laydate.css">
    <link rel="stylesheet" href="/plan-manager/css/modules/layer/default/layer.css">
    <link rel="stylesheet" href="/plan-manager/css/modules/code.css">
    <link rel="stylesheet" href="/plan-manager/css/base.css">
    <link rel="stylesheet" href="/plan-manager/css/index.css">
    <title></title>
</head>
<body>

<!--ä¸é¢ççå­-->
<div class="index_top">
    <p class="font18 ar ">欢迎您 : <span class="font_red cursor">${sessionScope.loginName}</span> <span id="logout" class="tc cursor">注销</span></p>
    <div class="logo"><span id="show_name" class="font24"></span></div>
</div>

<script>
    var str="企业项目计划管理系统";
    var len=str.length;
    var i=1;
    function showword(){
        document.getElementById("show_name").innerHTML=str.substr(0,i);
        i++;
        if(i>len){
            clearInterval("done");
        }
    }
    var done=setInterval("showword()",500);
</script>

<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="/plan-manager/js/layui.all.js"></script>
<script src="/plan-manager/js/index.js"></script>
</body>
</html>

