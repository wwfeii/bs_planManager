

$(function(){ 
	
	
	
	
	
	$.ajax({//得到总记录数
		type:"GET",
		data:{},
		dataType:"json",
		url:"/plan-manager/user/getTotal.action",
		success:function(data){
			  //分页
		    layui.use('laypage', function(){//成功后再初始化分页
		        var laypage = layui.laypage;
		        laypage.render({
		            elem: 'fen_ye1',
		            count: data, //数据总数，从服务端得到
		            jump: function(e,first){//点击翻页按钮触发的事件
		            	if(!first){
		            		//getPageJson(e.curr);
		            		$.ajax({
		            			type:"GET",
		                		data:{},
		                		dataType:"json",
		                		url:"/plan-manager/user/getJson.action?pageNum="+e.curr,
		                		success:function(data){
		                			var json=eval(data);
		                			var trVal = "";
		                			$.each(json,function(index,user){
		                				var userId=json[index].userId;
		                				var userName = json[index].userName;
		                				var roleName = json[index].roleName;
		                				var telPhone = json[index].telPhone;
		                				var userName = json[index].userName;
		                				var creatorName = json[index].creatorName;
		                				var createdTime = json[index].createdTime;
		                				trVal +=  "<tr><td><input type='checkbox' value='"+userId+"'></td> <td>"+userId+"</td><td>"+userName+"</td><td>"+roleName+"</td><td>"+telPhone+"</td><td>"+creatorName+"</td><td>"+createdTime+"</td></tr>";
		                			});
		                			$("#userTbody").html("");
		                			$("#userTbody").html(trVal);
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
   	        				var planLeaderName = json[index].planLeaderName;
   	        				var planStatus = json[index].planStatus;
   	        				var creatorName = json[index].creatorName;
   	        				
   	        				trVal +=  "<tr><td><input type='checkbox' value='"+planId+"'></td> <td>"+planName+"</td><td>"+planLeaderName+"</td><td>"+planStatus+"</td>" +
   	        						"<td>"+creatorName+"</td></tr>";
   	        			});
   	        			$("#mineCheckTbody").html("");
   	        			$("#mineCheckTbody").html(trVal);
   		            	layer.closeAll();
   		            	
   		             }else if(resData.code == 500){
   		            	$('#errorMsg').text(resData.msg);
   		             }
  	             }
        	})
        })
        
        form.on('submit(formDemo)', function(data){
        	$("#userForm").submit();
            return false;
        });
        form.on('submit(taskCheckForm)',function(data){
        	$("#mineTaskForm").ajaxSubmit({
        		 url: 'http://localhost:8080/plan-manager/task/checkTask.action',  
	             type: 'post',  
	             dataType: 'json',  
	             beforeSubmit: function () {},  
	             success: function (data){
	            	 var resData = eval(data);
	            	 if(resData.code == 200){
	            			 var trVal = ""
	    	            		 var tasks = resData.data;
	    	            		 $.each(tasks,function(index,task){
	    	            			var taskId =  tasks[index].taskId;
	    	            			var taskTitle = tasks[index].taskTitle;
	    	            			var taskLeaderName = tasks[index].taskLeaderName;
	    	            			var taskStatus = tasks[index].taskStatus;
	    	            			var createdTime = tasks[index].createdTime;
	    	            			
	    	            			trVal +=  "<tr><td><input type='checkbox' value='"+taskId+"'></td> <td>"+taskTitle+"</td><td>"+taskLeaderName+"</td><td>"+taskStatus+"</td>" +
	        						"<td>"+createdTime+"</td></tr>";
	    	            		 })
	    	            		 $("#mineCheckTbody").html("");
	    	            		 $("#mineCheckTbody").html(trVal);
	            		 
	            	 }else{
	            		 alert(resData.msg);
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
       	        				var planLeaderName = json[index].planLeaderName;
       	        				var planStatus = json[index].planStatus;
       	        				var creatorName = json[index].creatorName;
       	        				
       	        				trVal +=  "<tr><td><input type='checkbox' value='"+planId+"'></td> <td>"+planName+"</td><td>"+planLeaderName+"</td><td>"+planStatus+"</td>" +
       	        						"<td>"+creatorName+"</td></tr>";
       	        			});
       	        			$("#mineCheckTbody").html("");
       	        			$("#mineCheckTbody").html(trVal);
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
    function getPageJson(pageNum){
    	$.getJSON("http://localhost:8080/plan-manager/user/getAll.action?pageNum="+pageNum,{},function(data){
    		var json=eval(data);
			var trVal = "<tr><td><input type='checkbox' value='"+userId+"'></td>";
			$.each(json,function(index,user){
				var userId=json[index].userId;
				var userName = json[index].userName;
				var roleName = json[index].roleName;
				var telPhone = json[index].telPhone;
				var userName = json[index].userName;
				var creatorName = json[index].creatorName;
				trVal += " <td>"+userId+"</td><td>"+userName+"</td><td>"+roleName+"</td><td>"+telPhone+"</td><td>"+creatorName+"</td><td>"+createdTime+"</td>";
			});
			trVal+="</tr>";
			$("#userTbody").html("");
			$("#userTbody").html(trVal);
    	});
    	
    }

  

    // 弹出层
    layui.use('layer', function() {
        var layer = layui.layer;
       
       
      //审核
        $('#checkBtn').on('click', function () {
            var ids = $("#mineCheckTbody input[type=checkbox]:checked").map(function(){
         	   return $(this).val();
            }).get().join(',');
            if(ids.split(',').length==0 || ids==''|| ids == null){
         	   alert("请选择需要提交的记录");
         	   return;
            }
            if(ids.split(',').length != 1){
         	   alert("只能选择一条记录提交");
         	   return;
            }
            var id = ids;
           var type =  $("#type").val();
           if(type == "plan"){//类型是计划，审核人角色
        	   
        	   var ids = $("#mineCheckTbody input[type=checkbox]:checked").map(function(){
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
                  var id = ids;
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
        	   
        	   
           }else{//类型是任务，负责人角色
        	   $.ajax({
           		type:"GET",
           		url:"http://localhost:8080/plan-manager/task/getTaskCheckInfoById.action?taskId="+id,
           		data:{},
           		dataType:"json",
           		success:function(data){
           			var json=eval(data);
           			var taskId = json.taskId;
           			var commitUserName = json.commitUserName;
           			var checkUserName = json.checkUserName;
           			//设置layer的事件
           			layer.open({
                           type: 1,
                           scrollbar: false, shade: 0.5,
                           title: "任务提交",
                           area: ['500px', '360px'],
                           shadeClose: true, //点击遮罩关闭
                           content:'<form id="mineTaskForm" class="layui-form lay_form_padding20" method="POST">\n' +
                           ' <input type="hidden" name="taskId" value="'+taskId+'"/>'+
                           '  <div class="layui-form-item" style="margin-left: 20px;">\n' +
                           '    <label class="layui-form-label">提交人</label>\n' +
                           '    <div class="layui-input-block">\n' +
                           '      <input type="text" disabled="disabled" value="'+commitUserName+'" name="commitUserName" required  lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input" style="width: 56%;">\n' +
                           '    </div>\n' +
                           '  </div>\n' +
                           '  <div class="layui-form-item" style="margin-left: 20px;">\n' +
                           '    <label class="layui-form-label">审核人</label>\n' +
                           '    <div class="layui-input-inline">\n' +
                           '      <input type="tel" disabled="disabled" value="'+checkUserName+'" name="checkUserName" required lay-verify="required" placeholder="请输入电话号码" autocomplete="off" class="layui-input" style="width: 106%;">\n' +
                           '    </div>\n' +
                           '  </div>\n' +
                           ' <div class="layui-form-item layui-form-text" style="margin-left: 20px;">' +
                           '   <label class="layui-form-label">提交说明</label>' +
                           '   <div class="layui-input-block">' +
                           '     <textarea name="checkDescription" placeholder="请输入内容" class="layui-textarea"></textarea>' +
                           '   </div>' +
                           ' </div>'+
                           '  <div class="layui-form-item mar_t40" style="margin-left: 90px;">\n' +
                           '    <div class="layui-input-block">\n' +
                           '      <button class="layui-btn" lay-submit lay-filter="taskCheckForm">提交</button>\n' +
                           '      <a class="layui-btn layui-btn-primary" onClick="layer.closeAll()">取消</a>\n' +
                           '    </div>\n' +
                           '  </div>\n' +
                           '</form>'

                       });
           		}
           	})
           }
            
        	
            
        });
        
        
        //打回 功能
        $('#callBack').on('click', function () {
        	var ids = $("#mineCheckTbody input[type=checkbox]:checked").map(function(){
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
            var id = ids;
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
        
        
        

        $(document).on("click",".que_ren",function () {
           var ids = $("#userTbody input[type=checkbox]:checked").map(function(){
        	   return $(this).val();
           }).get().join(',');
           window.location.href="http://localhost:8080/plan-manager/user/delete.action?ids="+ids;
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
