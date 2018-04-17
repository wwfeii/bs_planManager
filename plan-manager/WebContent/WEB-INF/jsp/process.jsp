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
    <title>流程管理</title>
    <style>
        /*box3*/
        .rz_lc{
            margin-top: 100px;
            width: 100%;
            display: flex;
        }
        .rz_line{
            display: inline-block;
            height: 2px;
            flex: 1;
            background: #CCCCCC;
            position: relative;
        }
        .rz_line2{
            margin-left: -5px;
        }
        .rz_line_icon_gray{
            position: absolute;
            top:-10px;
        }
        .choose_rz_line{
            background: #1284A5;
        }
        .rz_line i.blue_color{
            cursor: pointer;
            position: absolute;
            bottom:-30px;
            left:48%;
            font-size: 12px;
            color:gray;
        }
        .del_liu_c{

            cursor: pointer;
            position: absolute;
            top:-40px;
            left:57%;
        }
        .blue_color{
            color: #0ca4cb !important;
        }
    .shou_suo{
        margin-top: 150px;
    }
    </style>
</head>
<body>
<!--ä¸­é´ççå­-->
<div class="index_content">
    <div class="index_content_right layui-container">
    <div class="layui-col-xs12 layui-col-md12">
             <button class="layui-btn layui-btn-primary " id="node_add">新增</button>
     </div>
        <div class="cai_1 memu">
            <div class="mar_t40">
                <div class="rz_lc ac">
                <c:forEach items="${nodes}" var="node">
                	<span class="rz_line choose_rz_line rz1">
						<img src="/plan-manager/images/blue_icon.png" class="rz_line_icon_gray" alt="">
                        <i class="blue_color i1">${node.nodeName}</i>
                       <i class="hide idVal">${node.nodeId}</i>
                        <i class="layui-icon del_liu_c hide">&#x1006;</i>
                    </span>
                </c:forEach>
					
                   <!--  <span class="rz_line rz_line2 choose_rz_line  rz2">
                        <img src="images/blue_icon.png" class="rz_line_icon_gray" alt="">
                        <i class=" blue_color i2">验证邮箱</i>
                        <i class="layui-icon del_liu_c hide">&#x1006;</i>
                    </span>
                     -->
                </div>
            </div>
            <div class="mar_t40 shou_suo">
                <div class="layui-col-md12 layui-col-sm12">
                    <div class="layui-col-md6 layui-col-xs6">
                        <form class="layui-form search_box" action="">
                            <div class="layui-form-item">
                                <input type="text" name="" id="roleVal" placeholder="请输入角色名" autocomplete="off" class="layui-input fl" style="width: 70%">
                                <a class="layui-btn fl" onclick="searchRole()">搜索</a>
                            </div>
                        </form>
                    </div>
                    <div class="layui-col-md6 layui-col-xs6">
                        <p class="kf">如果您不清楚该角色，可以对其进行搜索查看详细介绍哦！</p>
                    </div>
                </div>
                <div class="layui-col-md12 layui-col-sm12">
                    <p id="detailName" class="mar_t40 font24"></p>
                    <p id="detailDes" class="mar_t40">
                    </p>
                </div>
             </div>
        </div>
    </div>
</div>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="/plan-manager/js/layui.all.js"></script>
<script src="/plan-manager/js/index.js"></script>
</body>
</html>

<script type="text/javascript">
    $('.rz_lc .rz_line i.blue_color,.rz_lc .rz_line img').hover(function () {
        $(this).siblings('.del_liu_c').removeClass("hide");
        $(this).parents(".rz_line").siblings().find(".del_liu_c").addClass("hide");
    });
    $(".rz_line").on('click',".del_liu_c",function (event) {
       var  nodeIdVal = $(this).siblings(".idVal").text();
       $(this).parents('.rz_line').remove();
       window.location.href = "http://localhost:8080/plan-manager/node/deleteNode.action?nodeId="+nodeIdVal;
     
    })
    
    //角色搜索
    function searchRole(){
		var roleValue = $("#roleVal").val();
			if(roleValue == ""){
					alert("请输入角色名！");
					return;
				}
			$.ajax({
				 type:"POST",
		     		url:"http://localhost:8080/plan-manager/role/getRoleDetail.action",
		     		data:{'roleName':roleValue},
		     		dataType:"json",
		     		success:function(data){
							if(data.code == 200){
									var roles = eval(data.data);
									var roleDetail = roles[0];
									$("#detailName").text(roleDetail.roleName);
									$("#detailDes").text(roleDetail.roleDescription);
								}else if(data.code == 500){
									var value = eval(data);
									alert(value.msg);
									}
			     		},
			     		error:function(request){
			     			var errorVal = request.responseText;
			     			var json=eval(errorVal);
			     			//var obj = errorVal.parseJSON()
							//alert(obj.msg);
							alert(json.msg);
				     		}
				})
        }
    
    //新增节点
     $('#node_add').on('click', function () {

         $.ajax({
        	 type:"GET",
     		url:"http://localhost:8080/plan-manager/role/getAllJson.action",
     		data:{},
     		dataType:"json",
     		success:function(data){
  			var json=eval(data);
  			var roleVal = "<option selected='selected' value=''>--请选择角色--</option>";
				$.each(json,function(index,role){
					var roleId=json[index].roleId;
					var roleName = json[index].roleName;
					roleVal += "<option value='"+roleId+"'>"+roleName+"</option>";
				});

				 layer.open({
			           type: 1,
			           scrollbar: false, shade: 0.5,
			           title: "新增节点",
			           area: ['500px', '360px'],
			           shadeClose: true, 
			           content:'<form method="POST" id="addNodeForm" class="layui-form lay_form_padding20" action="http://localhost:8080/plan-manager/node/addNode.action">\n' +
						'<input type="hidden" value="typePlan" name="nodeType"/>'+
			           '  <div class="layui-form-item">\n' +
			           '    <label class="layui-form-label">节点顺序</label>\n' +
			           '    <div class="layui-input-block">\n' +
			           '      <input type="text" name="nodeNo" required  lay-verify="required" placeholder="请输入节点顺序" autocomplete="off" class="layui-input">\n' +
			           '    </div>\n' +
			           '</div>'+
			           '  <div class="layui-form-item">\n' +
			           '    <label class="layui-form-label">节点名称</label>\n' +
			           '    <div class="layui-input-block">\n' +
			           '      <input type="text" name="nodeName" required  lay-verify="required" placeholder="请输入节点名称" autocomplete="off" class="layui-input">\n' +
			           '    </div>\n' +
			           '</div>'+
			           ' <div class="layui-form-item">\n' +
			           '    <label class="layui-form-label">负责角色</label>\n' +
			           '    <div class="layui-input-block" style="height: 36px">\n' +
			           '      <select name="roleId" lay-verify="required" style="height: 100%;width:100%;;border: 1px solid #e6e6e6;display: block"' +
			           roleVal +
			           '      </select>\n' +
			           '    </div>\n' +
			           '  </div>'+
			           '  <div class="layui-form-item mar_t40">\n' +
			           '    <div class="layui-input-block">\n' +
			           '      <button class="layui-btn" lay-submit lay-filter="addForm">新增</button>\n' +
			           '      <a class="layui-btn layui-btn-primary" onClick="layer.closeAll()">取消</a>\n' +
			           '    </div>\n' +
			           '  </div>\n' +
			           '</form>'

			       });
	     	}
         })
   });

    layui.use('form', function(){
        var form = layui.form;
        form.on('submit(addForm)', function(data){
        	$("#addNodeForm").submit();
            return false;
        });
		//修改 框
        form.on('submit(updateForm)', function(data){
        	$("#updateNodeForm").submit();
            return false;
        });
      })
    
    //修改结点
   $('.rz_lc .rz_line .blue_color').on('click', function () {
	  var nodeIdVal =  $(this).siblings(".idVal").text();
		$.ajax({
				type:"GET",
				url:"http://localhost:8080/plan-manager/node/getNodeById.action?nodeId="+nodeIdVal,
				data:{},
				dataType:"json",
				success:function(data){
					var node = eval(data);
					var nodeId = node.nodeId;
					var nodeName = node.nodeName;
					var nroleId = node.roleId;
					var roleName = node.roleName;
					var nodeNo = node.nodeNo;

					 $.ajax({
				      	 type:"GET",
				   		url:"http://localhost:8080/plan-manager/role/getAllJson.action",
				   		data:{},
				   		dataType:"json",
				   		success:function(data){
							var json=eval(data);
							var roleVal = "";
								$.each(json,function(index,role){
									var roleId=json[index].roleId;
									var roleName = json[index].roleName;
									if(roleId == nroleId){
											roleVal += "<option selected='selected' value='"+roleId+"'>"+roleName+"</option>";
										}else{
											roleVal += "<option  value='"+roleId+"'>"+roleName+"</option>";
										}
									
								});

								layer.open({
							           type: 1,
							           scrollbar: false, shade: 0.5,
							           title: "修改节点",
							           area: ['500px', '360px'],
							           shadeClose: true, 
							           content:'<form method="POST" id="updateNodeForm" class="layui-form lay_form_padding20" action="http://localhost:8080/plan-manager/node/updateNode.action">\n' +
										'<input type="hidden" name="nodeId" value="'+nroleId+'"'+
							           '  <div class="layui-form-item">\n' +
							           '    <label class="layui-form-label">节点顺序</label>\n' +
							           '    <div class="layui-input-block">\n' +
							           '      <input type="text" name="nodeName" value="'+nodeNo+'" required  lay-verify="required" placeholder="请输入节点顺序" autocomplete="off" class="layui-input">\n' +
							           '    </div>\n' +
							           '</div>'+
							           '  <div class="layui-form-item">\n' +
							           '    <label class="layui-form-label">节点名称</label>\n' +
							           '    <div class="layui-input-block">\n' +
							           '      <input type="text" name="nodeName" value="'+nodeName+'" required  lay-verify="required" placeholder="请输入节点名称" autocomplete="off" class="layui-input">\n' +
							           '    </div>\n' +
							           '</div>'+
							           ' <div class="layui-form-item">\n' +
							           '    <label class="layui-form-label">负责角色</label>\n' +
							           '    <div class="layui-input-block" style="height: 36px">\n' +
							           '      <select name="roleId" lay-verify="required" style="height: 100%;width:100%;;border: 1px solid #e6e6e6;display: block"' +
							           roleVal +
							           '      </select>\n' +
							           '    </div>\n' +
							           '  </div>'+
							           '  <div class="layui-form-item mar_t40">\n' +
							           '    <div class="layui-input-block">\n' +
							           '      <button class="layui-btn" lay-submit lay-filter="updateForm">修改</button>\n' +
							           '      <a class="layui-btn layui-btn-primary" onClick="layer.closeAll()">取消</a>\n' +
							           '    </div>\n' +
							           '  </div>\n' +
							           '</form>'

							       });
								
				   		}
					   })
				}
			})
	  
   });
</script>