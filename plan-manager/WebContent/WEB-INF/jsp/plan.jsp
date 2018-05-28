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
 
    <title>计划管理</title>
</head>
<body>
<div class="  index_content">
    <div class="index_content_right">
        <div class="cai_1 memu ul_li_box2">
                <div class="layui-row">
                    <div class="layui-col-xs12 layui-col-md12">
                        <div class="layui-col-md4 layui-col-xs4">
                            <form id="searchForm" class="layui-form mar_t20 search_box" action="">
                                <div class="layui-form-item">
                                    <input id="searchVal" type="text" name="" placeholder="请输入计划名" autocomplete="off" class="layui-input fl">
                                    <a id="searchBtn" class="layui-btn fl">搜索</a>
                                </div>
                            </form>
                        </div>
                         <div class="layui-col-md8 layui-col-xs5">
                            <form class="layui-form mar_t20 search_box" action="">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">状态 :</label>
                                    <div class="layui-input-block">
	                                    <c:choose>
	                                    	<c:when test="${not empty flag}">
		                                    	<!-- <input  type="radio" name="planStatus"  value="全部" lay-filter="ra" title="全部" checked>
		                                        <input  type="radio" name="planStatus" value="新建" lay-filter="ra" title="新建">
		                                        <input type="radio" name="planStatus" value="审核中" lay-filter="ra" title="审核中">
		                                        <input type="radio" name="planStatus" value="进行中" lay-filter="ra" title="进行中"> 
		                                        <input type="radio" name="planStatus" value="未完成" lay-filter="ra" title="未完成" checked>
		                                        <input type="radio" name="planStatus" value="已完成" lay-filter="ra" title="已完成">-->
	                                    	</c:when>
	                                    	<c:otherwise>
	                                    		<input  type="radio" name="planStatus"  value="全部" lay-filter="ra" title="全部" checked>
		                                        <input  type="radio" name="planStatus" value="新建" lay-filter="ra" title="新建">
		                                        <input type="radio" name="planStatus" value="审核中" lay-filter="ra" title="审核中">
		                                        <input type="radio" name="planStatus" value="进行中" lay-filter="ra" title="进行中">
		                                        <input type="radio" name="planStatus" value="已完成" lay-filter="ra" title="已完成">
	                                    	</c:otherwise>
	                                    </c:choose>
                                    </div>
                                </div>
                            </form>

                        </div>
                    </div>
                    <div class="layui-col-xs12 layui-col-md12">
                        <p class="kf">
                            当前有 <i>${total}</i>个计划
                            <c:choose>
                            	<c:when test="${not empty flag}">
                            		<button class="layui-btn layui-btn-primary " id="check">审核</button>
                            	</c:when>
                            	<c:otherwise>
                            		<button class="layui-btn layui-btn-primary " id="add_layer1">新增</button>
		                            <button class="layui-btn layui-btn-primary xiu_gai" id="update_layer">修改</button>
		                            <button class="layui-btn layui-btn-primary select_det">删除</button>
		                            
		                            <button class="layui-btn layui-btn-primary " id="check">审核</button>
		                            <button class="layui-btn layui-btn-primary " id="callback">打回</button>
                            	</c:otherwise>
                            </c:choose>
                            
                        </p>
                    </div>
                    <div class="layui-col-xs12 layui-col-md12 padding_l_r20 table1">
                        <table class="layui-table ">
                            <colgroup>
                                <col width="60">
                                <col width="140">
                                <col width="120">
                                <col width="120">
                                <col width="120">
                                <col width="60">
                                <col width="60">
                                <col width="60">
                                <col width="100">
                            </colgroup>
                            <thead>
                            <tr>
                                <th>选择框</th>
                                <th>计划名称</th>
                                <th>状态</th>
                                <th>负责人</th>
                                <th>所属项目名称</th>
                                <th>总任务数</th>
                                <th>未完成数</th>
                                <th>已完成数</th>
                                <th>创建人</th>
                                
                            </tr>
                            </thead>
                            <tbody id="planTbody">
                            <c:forEach var="plan" items="${plans}">
                            <tr>
                                <td>
                                    <input type="checkbox" value="${plan.planId }" name="" >
                                </td>
                                <td>${plan.planName }</td>
                                <td>${plan.planStatus }</td>
                                <td>${plan.planLeaderName}</td>
                                <td>${plan.projectName}</td>
                                <td>${plan.taskTotalNum}</td>
                                <td>${plan.taskDisFinishNum }</td>
                                 <td>${plan.taskFinishNum }</td>
                                 <td>${plan.creatorName}</td>
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
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="/plan-manager/js/form.js"></script>
<script src="/plan-manager/js/layui.all.js"></script>
<script src="/plan-manager/js/plan.js"></script>
</body>
</html>

