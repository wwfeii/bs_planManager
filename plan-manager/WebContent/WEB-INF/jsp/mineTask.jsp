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
    <title>用户管理</title>
</head>
<body>
<!--ä¸­é´ççå­-->
<div class="  index_content">
    <div class="index_content_right">
        <div class="cai_1 memu ul_li_box2">
                <div class="layui-row">
                    <div class="layui-col-xs12 layui-col-md12">
                        <div class="layui-col-md4 layui-col-xs4">
                            <form class="layui-form mar_t20 search_box" action="">
                                <div class="layui-form-item">
                                    <input type="text" name=""   placeholder="请输入任务标题" autocomplete="off" class="layui-input fl">
                                    <a class="layui-btn fl">搜索</a>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="layui-col-xs12 layui-col-md12">
                        <p class="kf">
                            当前有 <i>${total}</i>个任务
                            <button class="layui-btn layui-btn-primary " id="checkBtn">提交审核</button>
                        </p>
                    </div>
                    <div class="layui-col-xs12 layui-col-md12 padding_l_r20 table1">
                        <table class="layui-table ">
                            <colgroup>
                            	<col width="60">
                                <col width="120">
                                <col width="80">
                                <col width="220">
                                <col width="80">
                                <col width="100">
                                <col width="80">
                                <col width="150">
                            </colgroup>
                            <thead>
                            <tr>
                            	<th>选择框</th>
                                <th>任务标题</th>
                                <th>任务状态</th>
                                <th>任务描述</th>
                                <th>任务负责人</th>
                                <th>对应计划</th>
                                <th>创建人</th>
                                <th>创建时间</th>
                            </tr>
                            </thead>
                            <tbody id="mineTaskTbody">
                            <c:forEach var="task" items="${tasks}">
                            <tr>
                                <td>
                                    <input type="checkbox" value="${task.taskId }" name="" >
                                </td>
                                <td>${task.taskTitle }</td>
                                <td>${task.taskStatus }</td>
                                <td>${task.taskDescription}</td>
                                <td>${task.taskLeaderName}</td>
                                <td>${task.planName }</td>
                                <td>${task.creatorName}</td>
                                <td>${task.createdTime }</td>
                            </tr>
                            </c:forEach>
                            
                            </tbody>
                        </table>
                    </div>
                    <div class="layui-col-xs12 layui-col-md12">
                        <div id="fen_ye1" class="tc"></div>
                    </div>
                </div>
        </div>
    </div>
</div>
<!--åå°ç®¡çç»æ-->
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<script src="/plan-manager/js/layui.all.js"></script>
<script src="/plan-manager/js/mineTask.js"></script>
</body>
</html>

