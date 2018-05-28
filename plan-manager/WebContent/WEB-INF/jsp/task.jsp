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
    <title>任务管理</title>
</head>
<body>
<div class="  index_content">
    <div class="index_content_right">
        <div class="cai_1 memu ul_li_box2">
                <div class="layui-row">
                    <div class="layui-col-xs12 layui-col-md12">
                    
                    <div class="layui-col-md3 layui-col-xs3">
                            <form class="layui-form mar_t20" action="">
                                <div class="layui-form-item">
                                    <label class="layui-form-label select_label_1">选计划</label>
                                    <div class="layui-input-block select1">
                                        <select lay-filter="se" name="city" lay-verify="required">
                                        <option value="" selected="selected">-请选择计划-</option>
                                        <c:if test="${not empty plans }">
                                        	<c:forEach items="${plans}" var="plan">
                                        	<c:choose>
                                        		<c:when test="${plan.planId eq newPlanId}">
                                        			<option selected="selected" value="${plan.planId}">${plan.planName}</option>
                                        		</c:when>
                                        		<c:otherwise>
                                        			<option  value="${plan.planId}">${plan.planName}</option>
                                        		</c:otherwise>
                                        	</c:choose>
                                        </c:forEach>
                                        </c:if>
                                        
                                        </select>
                                    </div>
                                </div>
                            </form>
                        </div>
                    
                        <div class="layui-col-md3 layui-col-xs3">
                            <form id="searchForm" class="layui-form mar_t20 search_box" action="">
                                <div class="layui-form-item">
                                    <input id="searchVal" type="text" name="" placeholder="请输入任务名" autocomplete="off" class="layui-input fl">
                                    <a id="searchBtn" class="layui-btn fl">搜索</a>
                                </div>
                            </form>
                        </div>
                         <div class="layui-col-md6 layui-col-xs6">
                            <form class="layui-form mar_t20 search_box" action="">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">状态 :</label>
                                    <div class="layui-input-block">
                                    	<input  type="radio" name="taskStatus"  value="全部" lay-filter="ra" title="全部" checked>
                                        <input  type="radio" name="taskStatus" value="新建" lay-filter="ra" title="新建">
                                        <input type="radio" name="taskStatus" value="审核中" lay-filter="ra" title="审核中">
                                        <input type="radio" name="taskStatus" value="已完成" lay-filter="ra" title="已完成">
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="layui-col-xs12 layui-col-md12">
                        <p class="kf">
                        <c:choose>
                        	<c:when test="${not empty taskTotaoNum }">
                        		当前有 <i id="taskNum">${taskTotaoNum}</i>个任务
                        	</c:when>
                        	<c:otherwise>
                        		当前有 <i id="taskNum">0</i>个任务
                        	</c:otherwise>
                        </c:choose>
                            
                            <button class="layui-btn layui-btn-primary " id="add_layer1">新增</button>
                            <button class="layui-btn layui-btn-primary xiu_gai" id="update_layer">修改</button>
                            <button class="layui-btn layui-btn-primary select_det">删除</button>
                        </p>
                    </div>
                    <div class="layui-col-xs12 layui-col-md12 padding_l_r20 table1">
                        <table class="layui-table ">
                            <colgroup>
                                <col width="60">
                                <col width="100">
                                <col width="200">
                                <col width="100">
                                <col width="100">
                                <col width="120">
                                <col width="80">
                            </colgroup>
                            <thead>
                            <tr>
                                <th>选择框</th>
                                <th>任务标题</th>
                                <th>任务描述</th>
                                <th>任务状态</th>
                                <th>任务负责人</th>
                                <th>计划名称</th>
                                <th>创建人</th>
                                
                            </tr>
                            </thead>
                            <tbody id="taskTbody">
                            <c:if test="${not empty tasks}">
                           		<c:forEach items="${tasks }" var="task" varStatus="status">
                           		<tr>
                           		<td>
                                    <input type="checkbox" value="${task.taskId }" name="" >
                                </td>
                                <td>${task.taskTitle }</td>
                                <td>${task.taskDescription}</td>
                                <td>${task.taskStatus}</td>
                                <td>${task.taskLeaderName}</td>
                                <td>${task.planName }</td>
                                <td>${task.createdTime }</td>
                                </tr>
                           		</c:forEach>
                           	</c:if>
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
<script src="/plan-manager/js/jquery.min.js"></script>
<script src="/plan-manager/js/layui.all.js"></script>
<script src="http://localhost:8080/plan-manager/js/task.js"></script>
</body>
</html>

