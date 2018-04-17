

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
        form.on('submit(formDemo)', function(data){
        	$("#userForm").submit();
            return false;
        });
        form.on('submit(checkForm)',function(data){
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
	            			var taskStatus = tasks[index].taskStatus;
	            			var taskDescription = tasks[index].taskDescription;
	            			var taskLeaderName = tasks[index].taskLeaderName;
	            			var planName = tasks[index].planName;
	            			var creatorName = tasks[index].creatorName;
	            			var createdTime = tasks[index].createdTime;
	            			
	            			trVal +=  "<tr><td><input type='checkbox' value='"+taskId+"'></td> <td>"+taskTitle+"</td><td>"+taskStatus+"</td><td>"+taskDescription+"</td><td>"+taskLeaderName+"</td><td>"+planName+"</td>" +
    						"<td>"+creatorName+"</td><td>"+createdTime+"</td></tr>";
	            		 })
	            		 $("#mineTaskTbody").html("");
	            		 $("#mineTaskTbody").html(trVal);
	            	 }else{
	            		 alert(resData.msg);
	            	 }
	             }
        	})
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
       
       
      //任务审核
        $('#checkBtn').on('click', function () {
            var ids = $("#mineTaskTbody input[type=checkbox]:checked").map(function(){
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
            var id = ids[0];
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
                        '      <button class="layui-btn" lay-submit lay-filter="checkForm">提交</button>\n' +
                        '      <a class="layui-btn layui-btn-primary" onClick="layer.closeAll()">取消</a>\n' +
                        '    </div>\n' +
                        '  </div>\n' +
                        '</form>'

                    });
        		}
        	})
            
        });

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
