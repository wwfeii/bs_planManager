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
<frameset rows="13%,*,7%">
    <frame name="top" src="${pageContext.request.contextPath }/page/top.action" noresize scrolling="yes"/>
    <frameset cols="15%,*">
	    <frame name="left" src="${pageContext.request.contextPath }/page/left.action" noresize/>
	    <frame name="right" src="${pageContext.request.contextPath }/task/queryTotalNum.action" noresize/>
    </frameset>
    <frame name="bottom" src="${pageContext.request.contextPath }/page/bottom.action" noresize scrolling="yes"/>
</frameset>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="/plan-manager/js/layui.all.js"></script>
<script src="/plan-manager/js/index.js"></script>
</html>