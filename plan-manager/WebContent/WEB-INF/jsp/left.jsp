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


<div class=" index_content">
    <div class="  index_content_left">
    	
    	<div class="left_ul_box1 left_ul_box color-1">
            <div class="left_1 cursor mar_t20">
                <div class="plan_sx"  onselectstart="return false;">
                    <a href="${pageContext.request.contextPath }/task/queryTotalNum.action" target="right"  style="cursor:pointer">首页</a>
                </div>
            </div>
        </div>
    
        <div class="left_ul_box1 left_ul_box color-1">
            <div class="left_1 cursor mar_t20">
                <div class="plan_sx"  onselectstart="return false;">
                    项目计划管理
                </div>
            </div>
            <ul class="left_ul  mar_t10 cl-effect-1">
                <li onselectstart="return false;" ><a href="${pageContext.request.contextPath }/project/getAll.action" target="right">项目管理</a></li>
                <li onselectstart="return false;"><a href="${pageContext.request.contextPath }/plan/getAll.action" target="right">计划管理</a></li>
                <li onselectstart="return false;"><a href="${pageContext.request.contextPath }/plan/getAllPlan.action" target="right">任务管理</a></li>
                <li onselectstart="return false;"><a href="${pageContext.request.contextPath }/node/getAll.action" target="right">审核流程管理</a></li>
               
            </ul>
        </div>
        <div class="left_ul_box1 left_ul_box color-1">
            <div class="left_1 cursor mar_t20">
                <div class="plan_sx"  onselectstart="return false;">
                    用户管理
                </div>
            </div>
            <ul class="left_ul  mar_t10 cl-effect-1">
                <li id="userInfo" onselectstart="return false;" ><a href="${pageContext.request.contextPath }/user/getAll.action" target="right">信息管理</a></li>
                <li onselectstart="return false;"><a href="${pageContext.request.contextPath }/role/getAll.action" target="right">角色管理</a></li>
                <li onselectstart="return false;"><a href="box3.html" target="right">审核流程管理</a></li>
                <li onselectstart="return false;"><a href="box4.html" target="right">任务管理</a></li>
            </ul>
        </div>

    </div>
</div>


<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="/plan-manager/js/layui.all.js"></script>
<script src="/plan-manager/js/index.js"></script>
</body>
</html>

