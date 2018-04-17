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
    <title>项目管理</title>
</head>
<body>
<div class="  index_content">
    <div class="index_content_right">
        <div class="cai_1 memu ul_li_box2">
                <div class="layui-row">
                    <div class="layui-col-xs12 layui-col-md12">
                        <div class="layui-col-md4 layui-col-xs4">
                            <form class="layui-form mar_t20 search_box" action="">
                                <div class="layui-form-item">
                                    <input id="searchVal" type="text" name="" placeholder="请输入项目名" autocomplete="off" class="layui-input fl">
                                    <a id="searchBtn" class="layui-btn fl">搜索</a>
                                </div>
                            </form>
                        </div>
                        <div class="layui-col-md8 layui-col-xs5">
                            <form class="layui-form mar_t20 search_box" action="">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">状态 :</label>
                                    <div class="layui-input-block">
                                    	<input type="radio" name="projectStatus" lay-filter="ra" value="全部" title="全部" checked>
                                        <input type="radio" name="projectStatus" lay-filter="ra" value="新建" title="新建">
                                        <input type="radio" name="projectStatus" lay-filter="ra" value="进行中" title="进行中">
                                        <input type="radio" name="projectStatus" lay-filter="ra" value="已完成" title="已完成">
                                    </div>
                                </div>
                            </form>

                        </div>
                    </div>
                    <div class="layui-col-xs12 layui-col-md12">
                        <p class="kf">
                            当前有 <i>${total}</i>个项目
                            <button class="layui-btn layui-btn-primary " id="add_layer1">新增</button>
                            <button class="layui-btn layui-btn-primary xiu_gai" id="update_layer">修改</button>
                            <button class="layui-btn layui-btn-primary select_det">删除</button>
                        </p>
                    </div>
                    <div class="layui-col-xs12 layui-col-md12 padding_l_r20 table1">
                        <table class="layui-table ">
                            <colgroup>
                                <col width="100">
                                <col width="100">
                                <col width="200">
                                <col width="150">
                                <col width="120">
                                <col width="120">
                                <col width="150">
                            </colgroup>
                            <thead>
                            <tr>
                                <th>选择框</th>
                                <th>序号</th>
                                <th>项目名</th>
                                <th>状态</th>
                                <th>负责人</th>
                                <th>创建人</th>
                                <th>创建时间</th>
                            </tr>
                            </thead>
                            <tbody id="projectTbody">
                            <c:forEach var="project" items="${projects}">
                            <tr>
                                <td>
                                    <input type="checkbox" value="${project.projectId }" name="" >
                                </td>
                                <td>${project.projectId }</td>
                                <td>${project.projectName }</td>
                                <td>${project.projectStatus}</td>
                                <td>${project.projectLeaderName}</td>
                                <td>${project.creatorName}</td>
                                <td>${project.createdTime }</td>
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
<script src="http://localhost:8080/plan-manager/js/layui.all.js"></script>
<script src="/plan-manager/js/project.js"></script>
</body>
</html>

