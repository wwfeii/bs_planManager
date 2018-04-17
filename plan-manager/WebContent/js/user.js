

$(function(){ 
	//totalNum = ${requestScope.total};
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


        // 新增
        $('#add_layer1').on('click', function () {
        	$.ajax({//发送ajax请求 获得所有的角色名
        		type:"GET",
        		url:"http://localhost:8080/plan-manager/role/getAllJson.action",
        		data:{},
        		dataType:"json",
        		success:function(data){
        			var json=eval(data);
        			var optionVal = "<option value=''>--请选择角色--</option>";
        			$.each(json,function(index,role){
        				var roleName=json[index].roleName;
        				var roleId = json[index].roleId;
        				optionVal += "<option value='"+roleId+"'>"+roleName+"</option>";
        			});
        			//设置layer的事件
        			layer.open({
                        type: 1,
                        scrollbar: false, shade: 0.5,
                        title: "新增用户",
                        area: ['600px', '360px'],
                        shadeClose: true, //点击遮罩关闭
                        content:'<form id="userForm" class="layui-form lay_form_padding20" method="POST" action="http://localhost:8080/plan-manager/user/addUser.action">\n' +
                        '  <div class="layui-form-item" style="margin-left: 90px;">\n' +
                        '    <label class="layui-form-label">用户名</label>\n' +
                        '    <div class="layui-input-block">\n' +
                        '      <input type="text" name="userName" required  lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input" style="width: 56%;">\n' +
                        '    </div>\n' +
                        '  </div>\n' +
                        '  <div class="layui-form-item" style="margin-left: 90px;">\n' +
                        '    <label class="layui-form-label">电话号码</label>\n' +
                        '    <div class="layui-input-inline">\n' +
                        '      <input type="tel" name="telPhone" required lay-verify="required" placeholder="请输入电话号码" autocomplete="off" class="layui-input" style="width: 106%;">\n' +
                        '    </div>\n' +
                        '  </div>\n' +
                        ' <div class="layui-form-item" style="margin-left: 90px;">\n' +
                        '    <label class="layui-form-label">角色名</label>\n' +
                        '    <div class="layui-input-block" style="height: 36px">\n' +
                        '      <select name="roleId" lay-verify="required" style="height: 100%;width: 56%;border: 1px solid #e6e6e6;display: block"' +
                        optionVal+
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
            
        });

        // 修改
        $('#update_layer').on('click', function () {
        	
        	 var ids = $("#userTbody input[type=checkbox]:checked").map(function(){
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
    	           		url:"http://localhost:8080/plan-manager/user/getUserById.action?userId="+id,//通过id 获得详情
    	           		data:{},
    	           		dataType:"json",
    	           		success:function(data){
    	           			var user=eval(data);
    	           			var userId=user.userId;
    	    				var userName = user.userName;
    	    				var uroleId = user.roleId;
    	    				var telPhone = user.telPhone;
    	    				var creatorName = user.creatorName;
    	    				var createdTime = user.createdTime;
    	    				
    	    				//得到角色列表
    	    				$.ajax({//发送ajax请求 获得所有的角色名
    	    	        		type:"GET",
    	    	        		url:"http://localhost:8080/plan-manager/role/getAllJson.action",
    	    	        		data:{},
    	    	        		dataType:"json",
    	    	        		success:function(data){
    	    	        			var json=eval(data);
    	    	        			var rolesVal = "";
    	    	        			$.each(json,function(index,role){
    	    	        				var roleName=json[index].roleName;
    	    	        				var roleId = json[index].roleId;
    	    	        				if(uroleId == roleId){
    	    	        					rolesVal += "<option selected='selected' value='"+roleId+"'>"+roleName+"</option>";
    	    	        				}else{
    	    	        					rolesVal += "<option value='"+roleId+"'>"+roleName+"</option>";
    	    	        				}
    	    	        				
    	    	        			});
    	    	                    layer.open({
    	    	                    	type: 1,
    	    	                    	title: '用户',
    	    	                    	scrollbar: false, shade: 0.5,
    	    	                    	area: ['400px', '450px'],
    	    	                    	shadeClose: true, //点击遮罩关闭
    	    	                    	content: '<form id="updateForm" method="POST" class="layui-form" action="http://localhost:8080/plan-manager/user/updateUser.action">\n' +
    	    	                    	' <input type="hidden" name="userId" value="'+userId+'"/>\n'+
    	    	                    	'  <div class="layui-form-item">\n' +
    	    	                    	'    <label class="layui-form-label">用户名称</label>\n' +
    	    	                    	'    <div class="layui-input-block">\n' +
    	    	                    	'      <input type="text" value="'+userName+'" name="userName" required  lay-verify="required" placeholder="请输入用户名称" style="width: 80%" autocomplete="off" class="layui-input mar_t10">\n' +
    	    	                    	'    </div>\n' +
    	    	                    	'  </div>\n' +
    	    	                    	'<div class="layui-form-item">\n' +
    	    	                    	'    <label class="layui-form-label">所属角色</label>\n' +
    	    	                    	'    <div class="layui-input-block">\n' +
    	    	                    	'      <select name="roleId" lay-verify="required" style="display: inline-block;width: 80%;height: 36px;border-color:#e6e6e6">\n' +
    	    	                    	rolesVal +
    	    	                    	'      </select>\n' +
    	    	                    	'    </div>\n' +
    	    	                    	'  </div>'+
    	    	                    	'<div class="layui-form-item">' +
    	    	                    	'      <label class="layui-form-label">电话号码</label>' +
    	    	                    	'    <div class="layui-input-block">\n' +
    	    	                    	'      <input type="text" value="'+telPhone+'" name="telPhone" required  lay-verify="required" value="15775890632" style="width: 80%" autocomplete="off" class="layui-input mar_t10">\n' +
    	    	                    	'    </div>\n' +
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

        //单独删除
        $('.cao_zuo .det').on('click', function () {
            layer.open({
                type: 1,
                title: '删除',
                scrollbar: false, shade: 0.5,
                area: ['400px', '250px'],
                shadeClose: true, //点击遮罩关闭
                content: '<p class="tc mar_t40">确定要删除这条数据吗？</p>'+
                    '<div class="mar_t40 tc">'+
                    '<a class="layui-btn layui-btn-primary que_ren" style="margin-right: 20px;" >确认</a>'+
                    '<a class="layui-btn layui-btn-primary cancer" style="margin-left: 20px;" onClick="layer.closeAll()">取消</a>'+
                  '</div>'

            });

        });
        //全部删除
        $('.all_det').on('click', function () {
            layer.open({
                type: 1,
                title: '全部删除',
                scrollbar: false, shade: 0.5,
                area: ['400px', '250px'],
                shadeClose: true, //点击遮罩关闭
                content: '<p class="tc mar_t40">确定要全部删除数据吗？</p>'+
                    '<div class="mar_t40 tc">'+
                    '<a class="layui-btn layui-btn-primary que_ren" style="margin-right: 20px;">确认</a>'+
                    '<a class="layui-btn layui-btn-primary" style="margin-left: 20px;" onClick="layer.closeAll()">取消</a>'+
                  '</div>'

            });

        });
        //选中删除
        $('.select_det').on('click', function () {
        	 var ids = $("#userTbody input[type=checkbox]:checked").map(function(){
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
