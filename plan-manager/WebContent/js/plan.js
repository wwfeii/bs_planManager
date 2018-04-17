

$(function(){ 

	$("#searchBtn").click(function(){//搜索事件
		var radio = $('input[name="planStatus"]:checked').val();//状态
		var searchVal = $("#searchVal").val();
		$.ajax({
			type:"POST",
			async:false,
			data:{'planStatus':radio},
			dataType:"json",
			url:"/plan-manager/plan/getJson.action?searchVal="+searchVal,
			success:function(data){
				var json=eval(data);
    			var trVal = "";
    			$.each(json,function(index,plan){
    				var planId=json[index].planId;
    				var planName = json[index].planName;
    				var projectName = json[index].projectName;
    				var planLeaderName = json[index].planLeaderName;
    				var planStatus = json[index].planStatus;
    				var taskTotalNum = json[index].taskTotalNum;
    				var taskDisFinishNum = json[index].taskDisFinishNum;
    				var taskFinishNum = json[index].taskFinishNum;
    				var creatorName = json[index].creatorName;
    				
    				trVal +=  "<tr><td><input type='checkbox' value='"+planId+"'></td> <td>"+planName+"</td><td>"+planStatus+"</td><td>"+planLeaderName+"</td><td>"+projectName+"</td><td>"+taskTotalNum+"</td>" +
    						"<td>"+taskDisFinishNum+"</td><td>"+taskFinishNum+"</td><td>"+creatorName+"</td></tr>";
    			});
    			$("#planTbody").html("");
    			$("#planTbody").html(trVal);
				}
		})
	})
	
    
	//totalNum = ${requestScope.total};
	$.ajax({//得到总记录数
		type:"GET",
		async:false,
		data:{},
		dataType:"json",
		url:"/plan-manager/plan/getTotal.action",
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
		            			type:"POST",
		            			async:false,
		                		data:{},
		                		dataType:"json",
		                		url:"/plan-manager/plan/getJson.action?pageNum="+e.curr,
		                		success:function(data){
		                			var json=eval(data);
		                			var trVal = "";
		                			$.each(json,function(index,plan){
		                				var planId=json[index].planId;
		                				var planName = json[index].planName;
		                				var projectName = json[index].projectName;
		                				var planLeaderName = json[index].planLeaderName;
		                				var planStatus = json[index].planStatus;
		                				var taskTotalNum = json[index].taskTotalNum;
		                				var taskDisFinishNum = json[index].taskDisFinishNum;
		                				var taskFinishNum = json[index].taskFinishNum;
		                				var creatorName = json[index].creatorName;
		                				
		                				trVal +=  "<tr><td><input type='checkbox' value='"+planId+"'></td> <td>"+planName+"</td><td>"+planStatus+"</td><td>"+planLeaderName+"</td><td>"+projectName+"</td><td>"+taskTotalNum+"</td>" +
		                						"<td>"+taskDisFinishNum+"</td><td>"+taskFinishNum+"</td><td>"+creatorName+"</td></tr>";
		                			});
		                			$("#planTbody").html("");
		                			$("#planTbody").html(trVal);
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
    layui.use('form', function(){
        var form = layui.form;
        
      //给单选按钮添加点击事件
    	form.on('radio(ra)', function(data){
    		var searchVal = $("#searchVal").val();
    		var redioVal = data.value//被点击的radio的value值
    		$.ajax({
    			type:"POST",
    			async:false,
        		data:{'planStatus':redioVal},
        		dataType:"json",
        		url:"/plan-manager/plan/getJson.action?searchVal="+searchVal,
        		success:function(data){
        			var json=eval(data);
        			var trVal = "";
        			$.each(json,function(index,plan){
        				var planId=json[index].planId;
        				var planName = json[index].planName;
        				var projectName = json[index].projectName;
        				var planLeaderName = json[index].planLeaderName;
        				var planStatus = json[index].planStatus;
        				var taskTotalNum = json[index].taskTotalNum;
        				var taskDisFinishNum = json[index].taskDisFinishNum;
        				var taskFinishNum = json[index].taskFinishNum;
        				var creatorName = json[index].creatorName;
        				
        				trVal +=  "<tr><td><input type='checkbox' value='"+planId+"'></td> <td>"+planName+"</td><td>"+planStatus+"</td><td>"+planLeaderName+"</td><td>"+projectName+"</td><td>"+taskTotalNum+"</td>" +
        						"<td>"+taskDisFinishNum+"</td><td>"+taskFinishNum+"</td><td>"+creatorName+"</td></tr>";
        			});
        			$("#planTbody").html("");
        			$("#planTbody").html(trVal);
        		}
    		})
    		}); 
        
        form.on('submit(formDemo)', function(data){
        	$("#planForm").submit();
            return false;
        });
        //打回
        form.on('submit(callbackForm)',function(data){
        	$('#callBackForm').ajaxSubmit({
        		url: 'http://localhost:8080/plan-manager/processs/callback.action',  
  	             type: 'post',
  	             async:false,
  	             dataType: 'json',  
  	             beforeSubmit: function () {},  
  	             success: function (data){
  	            	var resData = eval(data)
   	            	var trVal = "";
   		             if(resData.code == 200){
   		            	 var json = resData.data;
   		            	$.each(json,function(index,plan){
   	        				var planId=json[index].planId;
   	        				var planName = json[index].planName;
   	        				var projectName = json[index].projectName;
   	        				var planLeaderName = json[index].planLeaderName;
   	        				var planStatus = json[index].planStatus;
   	        				var taskTotalNum = json[index].taskTotalNum;
   	        				var taskDisFinishNum = json[index].taskDisFinishNum;
   	        				var taskFinishNum = json[index].taskFinishNum;
   	        				var creatorName = json[index].creatorName;
   	        				
   	        				trVal +=  "<tr><td><input type='checkbox' value='"+planId+"'></td> <td>"+planName+"</td><td>"+planStatus+"</td><td>"+planLeaderName+"</td><td>"+projectName+"</td><td>"+taskTotalNum+"</td>" +
   	        						"<td>"+taskDisFinishNum+"</td><td>"+taskFinishNum+"</td><td>"+creatorName+"</td></tr>";
   	        			});
   	        			$("#planTbody").html("");
   	        			$("#planTbody").html(trVal);
   		            	layer.closeAll();
   		            	
   		             }else if(resData.code == 500){
   		            	$('#errorMsg').text(resData.msg);
   		             }
  	             }
        	})
        })
        
        //审核
        form.on('submit(checkForm)',function(data){
        	$('#checkFormId').ajaxSubmit(      //ajax方式提交表单  
       	         {  
       	             url: 'http://localhost:8080/plan-manager/processs/planCheck.action',  
       	             type: 'post',
       	             async:false,
       	             dataType: 'json',  
       	             beforeSubmit: function () {},  
       	             success: function (data){
       	            	var resData = eval(data)
       	            	var trVal = "";
       		             if(resData.code == 200){
       		            	 var json = resData.data;
       		            	$.each(json,function(index,plan){
       	        				var planId=json[index].planId;
       	        				var planName = json[index].planName;
       	        				var projectName = json[index].projectName;
       	        				var planLeaderName = json[index].planLeaderName;
       	        				var planStatus = json[index].planStatus;
       	        				var taskTotalNum = json[index].taskTotalNum;
       	        				var taskDisFinishNum = json[index].taskDisFinishNum;
       	        				var taskFinishNum = json[index].taskFinishNum;
       	        				var creatorName = json[index].creatorName;
       	        				
       	        				trVal +=  "<tr><td><input type='checkbox' value='"+planId+"'></td> <td>"+planName+"</td><td>"+planStatus+"</td><td>"+planLeaderName+"</td><td>"+projectName+"</td><td>"+taskTotalNum+"</td>" +
       	        						"<td>"+taskDisFinishNum+"</td><td>"+taskFinishNum+"</td><td>"+creatorName+"</td></tr>";
       	        			});
       	        			$("#planTbody").html("");
       	        			$("#planTbody").html(trVal);
       		            	layer.closeAll();
       		            	
       		             }else if(resData.code == 500){
       		            	$('#errorMsg').text(resData.msg);
       		             }  
       	             }, 
       	             clearForm: false,//禁止清楚表单  
       	             resetForm: false //禁止重置表单  
       	         }); 
        	return false;
        })
    });
    

    // 弹出层
    layui.use('layer', function() {
        var layer = layui.layer;
        
        //打回
        $('#callback').on('click',function(){
        	var ids = $("#planTbody input[type=checkbox]:checked").map(function(){
            	   return $(this).val();
               }).get().join(',');
               if(ids.split(',').length==0 || ids==''|| ids == null){
            	   alert("请选择需要审核的记录");
            	   return;
               }
               if(ids.split(',').length != 1){
            	   alert("一次只能审核一条");
            	   return;
               }
               var id = ids[0];
               layer.open({
                   type: 1,
                   title: '确认打回',
                   scrollbar: false, shade: 0.5,
                   area: ['400px', '250px'],
                   shadeClose: true, //点击遮罩关闭
                   content: '<form id="callBackForm" class="layui-form" action="">\n' +
                   '<input type="hidden" name="bussnessId" value="'+id+'"/>'+
                	   '<div class="layui-form-item layui-form-text">\n' +
   			    	'    <label class="layui-form-label w20">打回原因:</label>\n' +
   			    	'    <div class="layui-input-block">\n' +
			    	'      <textarea name="checkInfo" placeholder="请输入打回原因" style="width: 80%;" class="layui-textarea"></textarea>\n' +
			    	'    </div>\n' +
			    	' </div>'+
			    	'</form>'+
                       '<div class="mar_t40 tc">'+
       			    '		<button class="layui-btn" lay-submit lay-filter="callbackForm">确认打回</button>\n' +
                       '<a class="layui-btn layui-btn-primary cancer" style="margin-left: 20px;" onClick="layer.closeAll()">取消</a>'+
                     '</div>'
               });
               
        	
        })

        //审核
        $('#check').on('click',function(){
        	var ids = $("#planTbody input[type=checkbox]:checked").map(function(){
           	   return $(this).val();
              }).get().join(',');
              if(ids.split(',').length==0 || ids==''|| ids == null){
           	   alert("请选择需要审核的记录");
           	   return;
              }
              if(ids.split(',').length != 1){
           	   alert("一次只能审核一条");
           	   return;
              }
              var id = ids[0];
              $.ajax({
            	  type:"GET",
            	  async:false,
            	  url:"http://localhost:8080/plan-manager/processs/getCheckInfo.action?businessId="+id+"&nodeType=typePlan",
            	  data:{},
            	  dataType:"json",
            	  success:function(data){//需要的数据   当前流程名 -- 下一步流程名：审核人list  上一步意见     
            		  var checkInfo = eval(data);
            		  var currentProcessNodeName = checkInfo.currentProcessNodeName;
            		  var nextProcessNodeName = checkInfo.nextProcessNodeName;
            		  var isFinish = false;
            		  if(nextProcessNodeName == "结束"){//表示当前就已经是结束了
            			  isFinish = true;
            		  }
            		  var preCheckInfo = checkInfo.preCheckInfo;
            		  if(preCheckInfo == "" || preCheckInfo == null){
            			  preCheckInfo = "这是第一步，没有上一步的审核意见！"
            		  }
            		  var checkUsers = checkInfo.checkUsers;
            		  var trVal = "";
            		  $.each(checkUsers,function(index,user){
          				var userId=checkUsers[index].userId;
          				var userName = checkUsers[index].userName;
          				trVal+= "<option value='"+userId+"'>"+userName+"</option>";
          			});
            		  if(trVal == ""){
            			 trVal = "<option value=''>结束</option>";
            		  }
            		  layer.open({
            			    type: 1,
            			    title: '计划审核',
            			    scrollbar: false, shade: 0.5,
            			    area: ['550px', '500px'],
            			    shadeClose: true, //点击遮罩关闭
            			    content: '<form id="checkFormId" class="layui-form" action="">\n' +
            			    '<input type="hidden" name="bussnessId" value="'+id+'"/>'+
            			    '<input type="hidden" name="isFinish" value="'+isFinish+'"/>'+
            			    '<div class="layui-form-item">' +
            			    '      <label class="layui-form-label w20">当前步骤：</label>' +
            			    '      <div class="layui-form-mid layui-word-aux">'+currentProcessNodeName+'</div>' +
            			    '</div>'+
            			    '<div class="layui-form-item">' +
            			    '      <label class="layui-form-label w20">提下一步：</label>' +
            			    '      <div class="layui-form-mid layui-word-aux">'+nextProcessNodeName+'</div>' +
            			    '</div>'+
            			    '<div id="nextUserId" class="layui-form-item">\n' +
            			    '    <label class="layui-form-label w20">下一步审核人:</label>\n' +
            			    '    <div class="layui-input-block">\n' +
            			    '      <select name="checkId" lay-verify="required" style="display: inline-block;width: 80%;height: 36px;border-color:#e6e6e6">\n' +
            			    trVal +
            			    '      </select>\n' +
            			    '    </div>\n' +
            			    '</div>'+
            			    '<div class="layui-form-item layui-form-text">\n' +
            			    '    <label class="layui-form-label w20">上一步审核意见:</label>\n' +
            			    '    <div class="layui-input-block">\n' +
            			    '      <textarea name="desc" readonly placeholder="" style="width: 80%;color: #b7aeae;"  class="layui-textarea">'+preCheckInfo+'</textarea>\n' +
            			    '    </div>\n' +
            			    ' </div>'+
            			    '<div class="layui-form-item layui-form-text">\n' +
            			    '    <label class="layui-form-label w20">审核意见:</label>\n' +
            			    '    <div class="layui-input-block">\n' +
            			    '      <textarea name="checkInfo" placeholder="请输入审核意见" style="width: 80%;" class="layui-textarea"></textarea>\n' +
            			    '    </div>\n' +
            			    ' </div>'+
            			    '</form>'+
            			    '<span id="errorMsg"></span>'+
            			     '<div class="mar_t20 tc">' +
            			    '		<button class="layui-btn" lay-submit lay-filter="checkForm">提交审核</button>\n' +
            			    '      <a class="layui-btn layui-btn-primary cancer" style="margin-left: 20px;" onClick="layer.closeAll()">取消</a>' +
            			    ' </div>\''
            			});
            	  }
              })
        })
        
        // 新增
        $('#add_layer1').on('click', function () {
        	
        	$.ajax({//得到项目
        		type:"GET",
        		async:false,
        		url:"http://localhost:8080/plan-manager/project/getAllJson.action",
        		data:{},
        		dataType:"json",
        		success:function(data){
        			var json=eval(data);
        			var projectVal = "<option value=''>--请选择项目--</option>";
        			$.each(json,function(index,project){
        				var projectName=json[index].projectName;
        				var projectId = json[index].projectId;
        				projectVal += "<option value='"+projectId+"'>"+projectName+"</option>";
        			});
        			$.ajax({//发送ajax请求 获得所有的角色名
                		type:"GET",
                		async:false,
                		url:"http://localhost:8080/plan-manager/user/getUserByRoleId.action?roleId="+3,//计划负责人角色id为3
                		data:{},
                		dataType:"json",
                		success:function(data){
                			var json=eval(data);
                			var leaderVal = "<option value=''>--请选择负责人--</option>";
                			$.each(json,function(index,leader){
                				var userName=json[index].userName;
                				var userId = json[index].userId;
                				leaderVal += "<option value='"+userId+"'>"+userName+"</option>";
                			});
                			//设置layer的事件
                			layer.open({
                                type: 1,
                                scrollbar: false, shade: 0.5,
                                title: "新增计划",
                                area: ['600px', '360px'],
                                shadeClose: true, //点击遮罩关闭
                                content:'<form id="planForm" class="layui-form lay_form_padding20" method="POST" action="http://localhost:8080/plan-manager/plan/addPlan.action">\n' +
                                '  <div class="layui-form-item" style="margin-left: 90px;">\n' +
                                '    <label class="layui-form-label">计划名称</label>\n' +
                                '    <div class="layui-input-block">\n' +
                                '      <input type="text" name="planName" required  lay-verify="required" placeholder="请输入计划名称" autocomplete="off" class="layui-input"  style="width: 56%;">\n' +
                                '    </div>\n' +
                                '  </div>\n' +
                                ' \' <div class="layui-form-item" style="margin-left: 90px;">' +
                                '   <label class="layui-form-label">计划负责人</label>' +
                                '   <div class="layui-input-block" style="height: 36px">' +
                                '     <select name="planLeaderId" lay-verify="required" style="height: 100%;width: 56%;border: 1px solid #e6e6e6;display: block"\' +\n' +
                                leaderVal+
                                '     </select>' +
                                '   </div>' +
                                ' </div>'+
                                ' <div class="layui-form-item" style="margin-left: 90px;">\n' +
                                '    <label class="layui-form-label">对应的项目</label>\n' +
                                '    <div class="layui-input-block" style="height: 36px">\n' +
                                '      <select name="projectId" lay-verify="required" style="height: 100%;width: 56%;border: 1px solid #e6e6e6;display: block"' +
                                projectVal+
                                '      </select>\n' +
                                '    </div>\n' +
                                '  </div>'+
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
        		}
        		
        	})
        	
        	
            
        });
        
       
        

        // 修改
        $('#update_layer').on('click', function () {
        	
        	 var ids = $("#planTbody input[type=checkbox]:checked").map(function(){
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
             var id = ids[0];
             $.ajax({
          	   		type:"GET",
          	   		async:false,
          	   		url:"http://localhost:8080/plan-manager/plan/getPlanById.action?planId="+id,//通过id 获得详情
	           		data:{},
	           		dataType:"json",
	           		success:function(data){
	           			var plan=eval(data);
	           			var planName=plan.planName;
	    				var planStatus = plan.planStatus;
	    				var planLeaderId = plan.planLeaderId;
	    				var planId = plan.planId;
	    				var creatorName = plan.creatorName;
	    				var createdTime = plan.createdTime;
	    				var pprojectId = plan.projectId;
	    				
	    				//获得计划负责人列表
	    				$.ajax({
	    					type:"GET",
	    					async:false,
	              	   		url:"http://localhost:8080/plan-manager/user/getUserByRoleId.action?roleId="+3,//通过id 获得详情
	    	           		data:{},
	    	           		dataType:"json",
	    	           		success:function(data){
	    	           			var json=eval(data);
	    	        			var updateVal = "";
	    	        			$.each(json,function(index,project){
	    	        				var userName=json[index].userName;
	    	        				var userId = json[index].userId;
	    	        				if(planLeaderId == userId){
	    	        					updateVal += "<option selected='selected' value='"+userId+"'>"+userName+"</option>";
	    	        				}else{
	    	        					updateVal += "<option value='"+userId+"'>"+userName+"</option>";
	    	        				}
	    	        			});
	    	        			
	    	        			//获得项目列表
	    	        			$.ajax({
	    	    					type:"GET",
	    	    					async:false,
	    	              	   		url:"http://localhost:8080/plan-manager/project/getAllJson.action",//通过id 获得详情
	    	    	           		data:{},
	    	    	           		dataType:"json",
	    	    	           		success:function(data){
	    	    	           			var json=eval(data);
	    	    	        			var projectVal = "";
	    	    	        			$.each(json,function(index,project){
	    	    	        				var projectName=json[index].projectName;
	    	    	        				var projectId = json[index].projectId;
	    	    	        				if(pprojectId = projectId){
	    	    	        					projectVal += "<option selected='selected' value='"+projectId+"'>"+projectName+"</option>";
	    	    	        				}else{
	    	    	        					projectVal += "<option value='"+projectId+"'>"+projectName+"</option>";
	    	    	        				}
	    	    	        			});
	    	    	                    
	    	    	                    layer.open({
	    	    	                    	type: 1,
	    	    	                    	title: '修改计划',
	    	    	                    	scrollbar: false, shade: 0.5,
	    	    	                    	area: ['400px', '450px'],
	    	    	                    	shadeClose: true, //点击遮罩关闭
	    	    	                    	content: '<form id="updateForm" method="POST" class="layui-form" action="http://localhost:8080/plan-manager/plan/updatePlan.action">\n' +
	    	    	                    	' <input type="hidden" name="planId" value="'+planId+'"/>\n'+
	    	    	                    	'  <div class="layui-form-item">\n' +
	    	    	                    	'    <label class="layui-form-label">计划名称</label>\n' +
	    	    	                    	'    <div class="layui-input-block">\n' +
	    	    	                    	'      <input type="text" value="'+planName+'" name="planName" required  lay-verify="required" placeholder="请输入计划名称" style="width: 80%" autocomplete="off" class="layui-input mar_t10">\n' +
	    	    	                    	'    </div>\n' +
	    	    	                    	'  </div>\n' +
	    	    	                    	'  <div class="layui-form-item">\n' +
	    	    	                    	'    <label class="layui-form-label">状态</label>\n' +
	    	    	                    	'    <div class="layui-form-mid layui-word-aux">'+planStatus+'</div>\n' +
	    	    	                    	'  </div>'+
	    	    	                    	'<div class="layui-form-item">\n' +
	    	    	                    	'    <label class="layui-form-label">计划负责人</label>\n' +
	    	    	                    	'    <div class="layui-input-block">\n' +
	    	    	                    	'      <select name="planLeaderId" lay-verify="required" style="display: inline-block;width: 80%;height: 36px;border-color:#e6e6e6">\n' +
	    	    	                    	updateVal +
	    	    	                    	'      </select>\n' +
	    	    	                    	'    </div>\n' +
	    	    	                    	'  </div>'+
	    	    	                    	'<div class="layui-form-item">\n' +
	    	    	                    	'    <label class="layui-form-label">所属项目</label>\n' +
	    	    	                    	'    <div class="layui-input-block">\n' +
	    	    	                    	'      <select name="projectId" lay-verify="required" style="display: inline-block;width: 80%;height: 36px;border-color:#e6e6e6">\n' +
	    	    	                    	projectVal +
	    	    	                    	'      </select>\n' +
	    	    	                    	'    </div>\n' +
	    	    	                    	'  </div>'+
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
	           		}
             })
        });

        //选中删除
        $('.select_det').on('click', function () {
        	 var ids = $("#planTbody input[type=checkbox]:checked").map(function(){
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
           var ids = $("#planTbody input[type=checkbox]:checked").map(function(){
        	   return $(this).val();
           }).get().join(',');
           window.location.href="http://localhost:8080/plan-manager/plan/delete.action?ids="+ids;
        })
         $(document).on("click","#confirm",function () {
           $("#updateForm").submit();
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



});



function right_qiehuan(ele,showele){
    $(ele).click(function(){
            $(showele).eq($(this).index()).removeClass('hide').siblings().addClass('hide');
            $(showele).eq($(this).index()).removeClass('hide').parent().siblings(".memu").children('div').addClass('hide');
            $(showele).eq($(this).index()).parent(".memu").removeClass('hide').parent().siblings(".memu").addClass('hide');
        }
    )
}
