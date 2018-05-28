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
    <script src="/plan-manager/js/echarts.js"></script>
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
	<script src="/plan-manager/js/layui.all.js"></script>
	<script src="/plan-manager/js/index.js"></script>
    <title>流程管理</title>
</head>
<body>
<select id="planSec" onchange="changOption(this.value)">
	<!-- 得到request中的plans 添加onclick事件-->
	<c:forEach items="${plans}" var="plan" varStatus="status">
		<c:choose>
			<c:when test="${status.index == 1}">
				<option value="${plan.planId}" selected="selected">${plan.planName}</option>
			</c:when>
			<c:otherwise>
				<option value="${plan.planId}">${plan.planName}</option>
			</c:otherwise>
		</c:choose>
			
	</c:forEach>
</select>
<div id="chartmain" style="width:800px; height: 600px;"></div>
</body>
</html>

<script type="text/javascript">
$(function(){
	//发送ajax请求 ，获得计划对应任务的数量

	var dataAry = [];
	var dataVal=[];
	
	var planId = $("#planSec").val();
	$.ajax({
		type:"GET",
		data:{},
		dataType:"json",
		async:false,
		url:"/plan-manager/plan/getStateTask.action?planId="+planId,
		success:function(res){
			var result = eval(res);
			$.each(result,function(index,edata){
					var taskStatusName = result[index].taskStatusName;
					var StatusNum = result[index].statusNum;
					 var obj = new Object();  
                     obj.name = taskStatusName;  
                     obj.value = StatusNum;  
                     dataAry.push(obj);  
					var task={
							'value':StatusNum,
							'name':taskStatusName
							}
					//dataAry.push(task); 
					dataVal.push(taskStatusName);
				});
		}
		
		});

		var option = {
	    title : {
	        text: '计划完成度统计',
	        subtext: '计划管理',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'left',
	        data: dataVal
	        //data:['新建','待执行','审核中','已完成']
	    },
	    series : [
	        {
	            name: '任务状态',
	            type: 'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            data:dataAry,
	            //data:[
	            //    {value:335, name:'新建'},
	            //    {value:310, name:'待执行'},
	            //    {value:234, name:'审核中'},
	            //    {value:135, name:'已完成'},
	            //],
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};

        //初始化echarts实例
        var myChart = echarts.init(document.getElementById('chartmain'));

        //使用制定的配置项和数据显示图表
        myChart.setOption(option);
	
	
});

function changOption(ppId){

	var dataAry = [];
	var dataVal=[];
	
	var planId = $("#planSec").val();
	$.ajax({
		type:"GET",
		data:{},
		dataType:"json",
		async:false,
		url:"/plan-manager/plan/getStateTask.action?planId="+planId,
		success:function(res){
			var result = eval(res);
			$.each(result,function(index,edata){
					var taskStatusName = result[index].taskStatusName;
					var StatusNum = result[index].statusNum;
					 var obj = new Object();  
                     obj.name = taskStatusName;  
                     obj.value = StatusNum;  
                     dataAry.push(obj);  
					dataVal.push(taskStatusName);
				});

		}
		
		});

	
	var option = {
		    title : {
		        text: '计划完成度统计',
		        subtext: '计划管理',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        left: 'left',
		        data: dataVal
		        //data:['新建','待执行','审核中','已完成']
		    },
		    series : [
		        {
		            name: '任务状态',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:dataAry,
		           // data:[
		           //     {value:335, name:'新建'},
		           //     {value:310, name:'待执行'},
		           //     {value:234, name:'审核中'},
		           //     {value:135, name:'已完成'},
		            //],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};

	        //初始化echarts实例
	        var myChart = echarts.init(document.getElementById('chartmain'));

	        //使用制定的配置项和数据显示图表
	        myChart.setOption(option);

	
}

    </script>