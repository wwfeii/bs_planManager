
var planIdVal="";
$(function(){ 
	
	var hrVal = window.location.href;
	if(hrVal.indexOf("?") != -1){
		var postVal = hrVal.split("?")[1];
		var planId = postVal.split("=")[1];
		planIdVal = planId
	}

	$("#searchBtn").click(function(){//搜索事件
		var radio = $('input[name="taskStatus"]:checked').val();//状态
		var searchVal = $("#searchVal").val();
		if(searchVal == ""){
			return ;
		}
		$.ajax({
			type:"POST",
			data:{'taskStatus':radio},
			dataType:"json",
			url:"/plan-manager/plan/getTaskJson.action?planId="+planIdVal+"&searchVal="+searchVal,
			success:function(data){
				var json=eval(data);
    			var trVal = "";
    			var indexz = 0;
    			$.each(json,function(index,task){
    				var taskId = json[index].taskId;
    				var taskTitle = json[index].taskTitle;
    				var taskDescription = json[index].taskDescription;
    				var taskStatus = json[index].taskStatus;
    				var taskLeaderName = json[index].taskLeaderName;
    				var planName = json[index].planName;
    				var creatorName = json[index].creatorName;
    				
    				trVal +=  "<tr><td><input type='checkbox' value='"+taskId+"'></td><td>"+taskTitle+"</td><td>"+taskDescription+"</td><td>"+taskStatus+"</td><td>"+taskLeaderName+"</td>" +
    						"<td>"+planName+"</td><td>"+creatorName+"</td></tr>";
    			});
    			$("#taskTbody").html("");
    			$("#taskTbody").html(trVal);
				}
		})
	})
	
    
	//totalNum = ${requestScope.total};
	if(planIdVal != ""){
		$.ajax({//得到总记录数
			type:"GET",
			data:{},
			dataType:"json",
			url:"/plan-manager/task/getTotal.action?planId="+planIdVal,
			success:function(data){
				  //分页
			    layui.use('laypage', function(){//成功后再初始化分页
			        var laypage = layui.laypage;
			        laypage.render({
			            elem: 'fen_ye1',
			            count: data, //数据总数，从服务端得到
			            jump: function(e,first){//点击翻页按钮触发的事件
			            	if(!first){
			            		$.ajax({
			            			type:"GET",
			                		data:{},
			                		dataType:"json",
			                		url:"/plan-manager/plan/getTaskJson.action?pageNum="+e.curr,
			                		success:function(data){
			                			var json=eval(data);
			                			var trVal = "";
			                			var indexz = 0;
			                			$.each(json,function(index,task){
			                				var taskId = json[index].taskId;
			                				var taskTitle = json[index].taskTitle;
			                				var taskDescription = json[index].taskDescription;
			                				var taskStatus = json[index].taskStatus;
			                				var taskLeaderName = json[index].taskLeaderName;
			                				var planName = json[index].planName;
			                				var creatorName = json[index].creatorName;
			                				
			                				trVal +=  "<tr><td><input type='checkbox' value='"+taskId+"'></td> <td>"+taskTitle+"</td><td>"+taskDescription+"</td><td>"+taskStatus+"</td><td>"+taskLeaderName+"</td>" +
			                						"<td>"+planName+"</td><td>"+creatorName+"</td></tr>";
			                			});
			                			$("#taskTbody").html("");
			                			$("#taskTbody").html(trVal);
			                		},
			                		error:function(data){//这里 可能有两个原因导致 ajax成功后 还是进入了error  1.请求跨域  2.返回的数据中可能有特殊字符 
			                		}
			                	});
			            	}
			            	
			            }
			        });
			    });
			}
		});
	}
	
    layui.use('form', function(){
        var form = layui.form;
        
        //给select添加事件
        form.on('select(se)',function(data){
        	var val = data.value;
        	planIdVal = val;
        	$.ajax({
        		type:"POST",
        		data:{},
        		dataType:"json",
        		url:"/plan-manager/plan/getTaskJson.action?planId="+val,
        		success:function(data){
        			var json=eval(data);
        			//设置任务数
        			$("#taskNum").html(json.length);
        			var trVal = "";
        			var indexz = 0;
        			$.each(json,function(index,task){
        				var taskId = json[index].taskId;
        				var taskTitle = json[index].taskTitle;
        				var taskDescription = json[index].taskDescription;
        				var taskStatus = json[index].taskStatus;
        				var taskLeaderName = json[index].taskLeaderName;
        				var planName = json[index].planName;
        				var creatorName = json[index].creatorName;
        				
        				trVal +=  "<tr><td><input type='checkbox' value='"+taskId+"'></td> <td>"+taskTitle+"</td><td>"+taskDescription+"</td><td>"+taskStatus+"</td><td>"+taskLeaderName+"</td>" +
        						"<td>"+planName+"</td><td>"+creatorName+"</td></tr>";
        			});
        			$("#taskTbody").html("");
        			$("#taskTbody").html(trVal);
        		}
        	})
        });
        
      //给单选按钮添加点击事件
    	form.on('radio(ra)', function(data){
    		var searchVal = $("#searchVal").val();
    		var redioVal = data.value//被点击的radio的value值
    		$.ajax({
    			type:"POST",
        		data:{'taskStatus':redioVal},
        		dataType:"json",
        		url:"/plan-manager/plan/getTaskJson.action?planId="+planIdVal+"&searchVal="+searchVal,
        		success:function(data){
        			var json=eval(data);
        			var trVal = "";
        			var indexz = 0;
        			$.each(json,function(index,task){
        				var taskId = json[index].taskId;
        				var taskTitle = json[index].taskTitle;
        				var taskDescription = json[index].taskDescription;
        				var taskStatus = json[index].taskStatus;
        				var taskLeaderName = json[index].taskLeaderName;
        				var planName = json[index].planName;
        				var creatorName = json[index].creatorName;
        				
        				trVal +=  "<tr><td><input type='checkbox' value='"+taskId+"'></td> <td>"+taskTitle+"</td><td>"+taskDescription+"</td><td>"+taskStatus+"</td><td>"+taskLeaderName+"</td>" +
        						"<td>"+planName+"</td><td>"+creatorName+"</td></tr>";
        			});
        			$("#taskTbody").html("");
        			$("#taskTbody").html(trVal);
        		}
    		})
    		}); 
        
        form.on('submit(formDemo)', function(data){
        	$("#taskForm").submit();
            return false;
        });
    });

  

    // 弹出层
    layui.use('layer', function() {
        var layer = layui.layer;

        function close() {

        }
        // 新增
        $('#add_layer1').on('click', function () {
        	
        	if(planIdVal == ""){
        		alert("请先选择对应的计划");
        		return;
        	}
        			$.ajax({//发送ajax请求 获得所有该角色的用户
                		type:"GET",
                		url:"http://localhost:8080/plan-manager/user/getUserByRoleId.action?roleId="+4,//任务负责人角色id为4
                		data:{},
                		dataType:"json",
                		success:function(data){
                			var json=eval(data);
                			var leaderVal = "<option value='' selected='selected'>--请选择负责人--</option>";
                			$.each(json,function(index,leader){
                				var userName=json[index].userName;
                				var userId = json[index].userId;
                				leaderVal += "<option value='"+userId+"'>"+userName+"</option>";
                			});
                			//设置layer的事件
                			layer.open({
                                type: 1,
                                scrollbar: false, shade: 0.5,
                                title: "新增任务",
                                area: ['600px', '360px'],
                                shadeClose: true, //点击遮罩关闭
                                content:'<form id="taskForm" method="POST" class="layui-form lay_form_padding20" action="http://localhost:8080/plan-manager/task/addTask.action">\n' +
                                '<input type="hidden" name="planId" value="'+planIdVal+'"/>\n'+
                                '  <div class="layui-form-item">\n' +
                                '    <label class="layui-form-label">任务标题</label>\n' +
                                '    <div class="layui-input-block">\n' +
                                '      <input type="text" name="taskTitle" required  lay-verify="required" placeholder="请输入任务标题" autocomplete="off" class="layui-input">\n' +
                                '    </div>\n' +
                                '  </div>\n' +
                                ' <div class="layui-form-item layui-form-text">' +
                                '   <label class="layui-form-label">任务描述</label>' +
                                '   <div class="layui-input-block">' +
                                '     <textarea name="taskDescription" placeholder="请输入内容" class="layui-textarea"></textarea>' +
                                '   </div>' +
                                ' </div>'+
                                ' \' <div class="layui-form-item">' +
                                '   <label class="layui-form-label">任务负责人</label>' +
                                '   <div class="layui-input-block" style="height: 36px">' +
                                '     <select name="taskLeaderId" lay-verify="required" style="height: 100%;width:100%;border: 1px solid #e6e6e6;display: block"\' +\n' +
                                leaderVal+
                                '     </select>' +
                                '   </div>' +
                                ' </div>'+
                                '  <div class="layui-form-item mar_t40" style="margin-left: 90px;">\n' +
                                '    <div class="layui-input-block">\n' +
                                '      <button class="layui-btn" lay-submit lay-filter="formDemo">提交</button>\n' +
                                '      <a class="layui-btn layui-btn-primary" onClick="layer.closeAll()">取消</a>\n' +
                                '    </div>\n' +
                                '  </div>\n' +
                                '</form>'

                            });
                		}
                		
                	})
        		
        		
        	})
        	
        	
            
        });

        // 修改
        $('#update_layer').on('click', function () {
        	
        	var ids = $("#taskTbody input[type=checkbox]:checked").map(function(){
         	   return $(this).val();
            }).get().join(',');
            if(ids.split(',').length==0 || ids==''|| ids == null){
         	   alert("请选择需要修改的记录");
         	   return;
            }
            if(ids.split(',').length != 1){
         	   alert("只能选择一条记录修改");
         	   return;
            }
            var id = ids;
            alert(id)
            $.ajax({
           	   type:"GET",
 	           		url:"http://localhost:8080/plan-manager/task/getTaskById.action?taskId="+id,//通过id 获得详情
 	           		data:{},
 	           		dataType:"json",
 	           		success:function(data){
 	           			var task =eval(data);
 	           			var taskId=task.taskId;
 	    				var taskTitle = task.taskTitle;
 	    				var taskDescription = task.taskDescription;
 	    				var taskLeaderId = task.taskLeaderId;
 	    				var taskStatus = task.taskStatus;
 	    				var creatorName = task.creatorName;
 	    				var createdTime = task.createdTime;
 	    				var planId = task.planId;
 	    				
 	    				//查询任务负责人列表
 	    				$.ajax({//发送ajax请求 获得所有该角色的用户
 	                		type:"GET",
 	                		url:"http://localhost:8080/plan-manager/user/getUserByRoleId.action?roleId="+4,//任务负责人角色id为4
 	                		data:{},
 	                		dataType:"json",
 	                		success:function(data){
 	                			var json=eval(data);
 	                			var leaderVal = "";
 	                			$.each(json,function(index,leader){
 	                				var userName=json[index].userName;
 	                				var userId = json[index].userId;
 	                				if(userId == taskLeaderId){
 	                					leaderVal += "<option selected='selected' value='"+userId+"'>"+userName+"</option>";
 	                				}else{
 	                					leaderVal += "<option value='"+userId+"'>"+userName+"</option>";
 	                				}
 	                				
 	                			});
 	                           layer.open({
 	                           	type: 1,
 	                           	title: '任务',
 	                           	scrollbar: false, shade: 0.5,
 	                           	area: ['480px', '500px'],
 	                           	shadeClose: true, //点击遮罩关闭
 	                           	content: '<form id="updateForm" method="POST" class="layui-form" action="http://localhost:8080/plan-manager/task/updateTask.action">\n' +
 	                           ' <input type="hidden" name="taskId" value="'+taskId+'"/>\n'+
 	                          ' <input type="hidden" name="planId" value="'+planId+'"/>\n'+
 	                           	'  <div class="layui-form-item">\n' +
 	                           	'    <label class="layui-form-label">任务标题</label>\n' +
 	                           	'    <div class="layui-input-block">\n' +
 	                           	'      <input type="text" value="'+taskTitle+'" name="taskTitle" required  lay-verify="required" placeholder="请输入任务标题" style="width: 80%" autocomplete="off" class="layui-input mar_t10">\n' +
 	                           	'    </div>\n' +
 	                           	'  </div>\n' +
 	                           	    ' <div class="layui-form-item layui-form-text">\n' +
 	                           	'    <label class="layui-form-label">任务描述</label>\n' +
 	                           	'    <div class="layui-input-block">\n' +
 	                           	'      <textarea name="taskDescription" value="'+taskDescription+'" placeholder="请输入内容" style="width: 80%;" class="layui-textarea">'+taskDescription+'</textarea>\n' +
 	                           	'    </div>\n' +
 	                           	'  </div>'+
 	                           	'<div class="layui-form-item">\n' +
 	                           	'    <label class="layui-form-label">任务负责人</label>\n' +
 	                           	'    <div class="layui-input-block">\n' +
 	                           	'      <select name="taskLeaderId" lay-verify="required" style="display: inline-block;width: 80%;height: 36px;border-color:#e6e6e6">\n' +
 	                           leaderVal +
 	                           	'      </select>\n' +
 	                           	'    </div>\n' +
 	                           	'  </div>'+
 	                           	'<div class="layui-form-item">' +
 	                           	'      <label class="layui-form-label">任务状态</label>' +
 	                           	'      <div class="layui-form-mid layui-word-aux">'+taskStatus+'</div>' +
 	                           	'    </div>'+
 	                           	'<div class="layui-form-item">' +
 	                           	'      <label class="layui-form-label">创建人</label>' +
 	                           	'      <div class="layui-form-mid layui-word-aux">'+creatorName+'</div>' +
 	                           	'    </div>'+
 	                           	' <div class="layui-form-item">' +
 	                           	'       <label class="layui-form-label">创建时间</label>' +
 	                           	'       <div class="layui-form-mid layui-word-aux">'+createdTime+'</div>' +
 	                           	'</div>'+
 	                           	'</form>'+
 	                           	 '<div class="mar_t20 tc">' +
 	                           	'      <a class="layui-btn layui-btn-primary que_ren" id="confirm" style="margin-right: 20px;">确认</a>' +
 	                           	'      <a class="layui-btn layui-btn-primary cancer" style="margin-left: 20px;" onClick="layer.closeAll()">取消</a>' +
 	                           	' </div>\''

 	                           });
 	                		}
 	    				})
 	    				
 	           		}
              })

        });

        //选中删除
        $('.select_det').on('click', function () {
        	 var ids = $("#taskTbody input[type=checkbox]:checked").map(function(){
          	   return $(this).val();
             }).get().join(',');
             if(ids.split(',').length==0 || ids==''|| ids == null){
          	   alert("请选择需要删除的记录");
          	   return;
             }
            layer.open({
                type: 1,
                title: '确认删除',
                scrollbar: false, shade: 0.5,
                area: ['400px', '250px'],
                shadeClose: true, //点击遮罩关闭
                content: '<p class="tc mar_t40">确定要删除选中的数据吗？</p>'+
                    '<div class="mar_t40 tc">'+
                    '<a class="layui-btn layui-btn-primary que_ren" style="margin-right: 20px;">确认</a>'+
                    '<a class="layui-btn layui-btn-primary cancer" style="margin-left: 20px;" onClick="layer.closeAll()">取消</a>'+
                  '</div>'
            });
        });

        $(document).on("click",".que_ren",function () {
           var ids = $("#taskTbody input[type=checkbox]:checked").map(function(){
        	   return $(this).val();
           }).get().join(',');
           window.location.href="http://localhost:8080/plan-manager/task/delete.action?ids="+ids+"&planId="+planIdVal;
        })
         $(document).on("click","#confirm",function () {
           $("#updateForm").submit();
         })


    });

    $(".left_ul_box .left_1").click(function(){
        $(this).siblings('.left_ul').removeClass('hide').toggle();
    })
    $(" .cl-effect-1 li").click(function(){
        $(this).addClass('li_choose').siblings('li').removeClass("li_choose");
        $(this).parent().parent(".left_ul_box").siblings().find("li").removeClass("li_choose");
        // $(".index_content_right .left_box_ul_1 .ul_li_box1").removeClass("hide").siblings().addClass("hide");
    })



function right_qiehuan(ele,showele){
    $(ele).click(function(){
            $(showele).eq($(this).index()).removeClass('hide').siblings().addClass('hide');
            $(showele).eq($(this).index()).removeClass('hide').parent().siblings(".memu").children('div').addClass('hide');
            $(showele).eq($(this).index()).parent(".memu").removeClass('hide').parent().siblings(".memu").addClass('hide');
        }
    )
}
