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

</head>
<body>
<div class="login_box center_absolute_xy">
    <p class="login_title font24 mar_t10 ac"><span class="font_red">企业项目</span>计划管理系统</p>
    <form id="infoForm" class="layui-form layui-form-pane login_form mar_t40" action="${pageContext.request.contextPath }/user/login.action" method="post">
        <!--用户名-->
        <div class="layui-form-item input_login" pane>
            <label class=" user_label">
                <img src="/plan-manager/images/user.png" class="img_password" alt="">
            </label>
            <div class="layui-input-block">
                <input type="text" name="userName" required   placeholder="用户名" autocomplete="off" class="layui-input">
            </div>
        </div>
        <!--用户名提示-->
        <div class="tips font_red">
		 ${userNameMsg}
        </div>
        <!--密码-->
        <div class="layui-form-item input_login mar_t10" pane>
            <label class=" user_label">
                <img src="/plan-manager/images/password.png" class="img_password" alt="">
            </label>
            <div class="layui-input-block">
                <input type="password" name="userPwd" required placeholder="密码" autocomplete="off" class="layui-input">
            </div>
        </div>
        <!--密码提示-->
        <div class="tips font_red">
		 ${userPwdMsg}
        </div>

        <!--验证码-->
        <div class="yzm mar_t10">
            <div class="layui-form-item input_login3" pane>
                <label class=" user_label">
                    <img src="/plan-manager/images/password.png" class="img_password" alt="">
                </label>
                <div class="layui-input-block">
                    <input type="text" name="validateCode" required placeholder="验证码" autocomplete="off" class="layui-input yzm_input">
                </div>
            </div>
            <div class="yzm_img">
                   <img id="codeImg" src="http://localhost:8080/plan-manager/user/getValidateImg.action" alt="" onclick="freshImg()">
               </div>
        </div>
        <!--验证码提示-->
        <div class="tips font_red mar_t20">
             ${message}
        </div>

        <!--记住密码/忘记密码-->
        <div class="remember_password al mar_t20">
            <div class="fl">
                <input type="checkbox" name="" value="" checked="checked" /> 记住密码
            </div>
            <div class="fr">
                忘记密码？
            </div>
        </div>

        <!--登录按钮-->
        <div class="login_button mar_t10">
            <button onclick="document.getElementById('infoForm').submit();" class="layui-btn btns">登    录</button>
        </div>

    </form>
</div>
</body>
<script src="/plan-manager/js/login.js"></script>
</html>