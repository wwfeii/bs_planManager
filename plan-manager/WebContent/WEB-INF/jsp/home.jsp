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


<div class="  index_content">
    <div class="  index_content_right">
        <div class="cai_1 memu ul_li_box1">
            <div class="main_right_top">
                <div class="one fl">
                    <div class="index_one">
                        <a href="${pageContext.request.contextPath}/task/getMineTasks.action"><img src="/plan-manager/images/index_1img.png"  class="mar_t20" alt="我的任务"></a>
                        <p class="tc mar_t20">我的任务 <i class="font_red">${totalNum.taskNum }</i>个</p>
                    </div>
                </div>
                <div class="one fl">
                    <div class="index_two">
                        <a href="${pageContext.request.contextPath}/plan/getMinePlans.action"><img src="/plan-manager/images/wei_wanc.png" class="mar_t20"  alt="我的计划"></a>
                        <p class="tc mar_t20">我的计划 <i class="font_red">${totalNum.planNum}</i> 个</p>

                    </div>
                </div>
            </div>
            <div class="main_right_bottom">
                <div class="one fl">
                    <div class="index_three">
                       <a href="${pageContext.request.contextPath}/project/getMineProject.action"><img src="/plan-manager/images/shenhe.png" class="mar_t20"  alt="我的项目"></a> 
                        <p class="tc mar_t20">我的项目<i class="font_red">${totalNum.projectNum}</i>个</p>
                    </div>
                </div>
                <div class="one fl">
                    <div class="index_four">
                       <a href="${pageContext.request.contextPath}/processs/mineCheck.action"> <img src="/plan-manager/images/question.png" class="mar_t20"  alt="我的审核"></a>
                        <p class="tc mar_t20">我的审核 <i class="font_red">${totalNum.checkNum }</i> 个</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--åå°ç®¡çç»æ-->
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="/plan-manager/js/layui.all.js"></script>
<script src="/plan-manager/js/index.js"></script>
</body>
</html>

