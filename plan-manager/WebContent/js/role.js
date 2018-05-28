

$(function(){
	
	
	$("#searchBtn").click(function(){//搜索事件
		var searchVal = $("#searchVal").val();
		if(searchVal == ""){
			return ;
		}
		$.ajax({
			type:"GET",
			data:{},
			dataType:"json",
			url:"/plan-manager/role/getJson.action?searchVal="+searchVal,
			success:function(data){
				var json=eval(data);
    			var trVal = "";
    			$.each(json,function(index,role){
    				var roleId=json[index].roleId;
    				var roleName = json[index].roleName;
    				var roleDescription = json[index].roleDescription;
    				var createdTime = json[index].createdTime;
    				var creatorName = json[index].creatorName;
    				
    				trVal +=  "<tr><td><input type='checkbox' value='"+roleId+"'></td> <td>"+roleId+"</td><td>"+roleName+"</td><td>"+roleDescription+"</td><td>"+creatorName+"</td><td>"+createdTime+"</td></tr>";
    			});
    			$("#roleTbody").html("");
    			$("#roleTbody").html(trVal);
				}
		})
	})
	
	$.ajax({//得到总记录数
		type:"GET",
		data:{},
		dataType:"json",
		url:"/plan-manager/role/getTotal.action",
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
		                		url:"/plan-manager/role/getJson.action?pageNum="+e.curr,
		                		success:function(data){
		                			var json=eval(data);
		                			var trVal = "";
		                			$.each(json,function(index,role){
		                				var roleId=json[index].roleId;
		                				var roleName = json[index].roleName;
		                				var roleDescription = json[index].roleDescription;
		                				var createdTime = json[index].createdTime;
		                				var creatorName = json[index].creatorName;
		                				
		                				trVal +=  "<tr><td><input type='checkbox' value='"+roleId+"'></td> <td>"+roleId+"</td><td>"+roleName+"</td><td>"+roleDescription+"</td><td>"+creatorName+"</td><td>"+createdTime+"</td></tr>";
		                			});
		                			$("#roleTbody").html("");
		                			$("#roleTbody").html(trVal);
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
           $("#roleForm").ajaxSubmit({
        	   	 url: 'http://localhost:8080/plan-manager/role/addRole.action',  
	             type: 'post',  
	             dataType: 'json',  
	             beforeSubmit: function () { },  
	             success: function (data){
	            	 var resData = eval(data)
	   	            	var trVal = "";
	   		             if(resData.code == 200){
	   		            	 var json = resData.data;
                			$.each(json,function(index,role){
                				var roleId=json[index].roleId;
                				var roleName = json[index].roleName;
                				var roleDescription = json[index].roleDescription;
                				var createdTime = json[index].createdTime;
                				var creatorName = json[index].creatorName;
                				
                				trVal +=  "<tr><td><input type='checkbox' value='"+roleId+"'></td> <td>"+roleId+"</td><td>"+roleName+"</td><td>"+roleDescription+"</td><td>"+creatorName+"</td><td>"+createdTime+"</td></tr>";
                			});
	   	        			$("#roleTbody").html("");
	   	        			$("#roleTbody").html(trVal);
	   		            	layer.closeAll();
	   		            	
	   		             }else if(resData.code == 500){
	   		            	$('#errorMsg').text(resData.msg);
	   		             }
	             }
           })
            return false;
        });
    });

    //分页
    layui.use('laypage', function(){
        var laypage = layui.laypage;
        laypage.render({
            elem: 'fen_ye1'
            ,count: 50 //数据总数，从服务端得到
        });
    });

    // 弹出层
    layui.use('layer', function() {
        var layer = layui.layer;
        // 新增
        $('#add_layer1').on('click', function () {
            layer.open({
                type: 1,
                scrollbar: false, shade: 0.5,
                title: "新增角色",
                area: ['600px', '360px'],
                shadeClose: true, //点击遮罩关闭
                content:'<form id="roleForm" method="POST" class="layui-form lay_form_padding20" action="">\n' +
                '<span id="errorMsg"></span>'+
                '  <div class="layui-form-item">\n' +
                '    <label class="layui-form-label">角色名</label>\n' +
                '    <div class="layui-input-block">\n' +
                '      <input type="text" name="roleName" required  lay-verify="required" placeholder="请输入角色名" autocomplete="off" class="layui-input">\n' +
                '    </div>\n' +
                '  </div>\n' +
              ' <div class="layui-form-item layui-form-text">\n' +
                '    <label class="layui-form-label">角色描述</label>\n' +
                '    <div class="layui-input-block">\n' +
                '      <textarea name="roleDescription" required placeholder="请输入内容" class="layui-textarea"></textarea>\n' +
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
        });

        // 修改
        $('#update_layer').on('click', function () {
        	var ids = $("#roleTbody input[type=checkbox]:checked").map(function(){
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
             $.ajax({
            	   type:"GET",
  	           		url:"http://localhost:8080/plan-manager/role/getRoleById.action?roleId="+id,//通过id 获得详情
  	           		data:{},
  	           		dataType:"json",
  	           		success:function(data){
  	           			var role=eval(data);
  	           			var roleName=role.roleName;
  	           			var roleId = role.roleId;
  	    				var roleDescription = role.roleDescription;
  	    				var creatorName = role.creatorName;
  	    				var createdTime = role.createdTime;
  	    				layer.open({
  	    	            	type: 1,
  	    	            	title: '修改角色',
  	    	            	scrollbar: false, shade: 0.5,
  	    	            	area: ['480px', '500px'],
  	    	            	shadeClose: true, //点击遮罩关闭
  	    	            	content: '<form id="updateForm" method="POST" class="layui-form" action="http://localhost:8080/plan-manager/role/updateRole.action">\n' +
  	    	            	' <input type="hidden" name="roleId" value="'+roleId+'"/>\n'+
  	    	            	'  <div class="layui-form-item">\n' +
  	    	            	'    <label class="layui-form-label">角色名</label>\n' +
  	    	            	'    <div class="layui-input-block">\n' +
  	    	            	'      <input type="text" value="'+roleName+'" name="roleName" required  lay-verify="required" placeholder="请输入角色名" style="width: 80%" autocomplete="off" class="layui-input mar_t10">\n' +
  	    	            	'    </div>\n' +
  	    	            	'  </div>\n' +
  	    	            	    ' <div class="layui-form-item layui-form-text">\n' +
  	    	            	'    <label class="layui-form-label">角色描述</label>\n' +
  	    	            	'    <div class="layui-input-block">\n' +
  	    	            	'      <textarea name="roleDescription" id="roleDesId"  placeholder="请输入内容" style="width: 80%;" class="layui-textarea">'+roleDescription+'</textarea>\n' +
  	    	            	'    </div>\n' +
  	    	            	'  </div>'+
  	    	            	'<div class="layui-form-item">' +
  	    	            	'      <label class="layui-form-label">创建人</label>' +
  	    	            	'      <div class="layui-form-mid layui-word-aux">'+creatorName+'</div>' +
  	    	            	'</div>'+
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
        //选中删除
        $('.select_det').on('click', function () {
        	var ids = $("#roleTbody input[type=checkbox]:checked").map(function(){
           	   return $(this).val();
              }).get().join(',');
              if(ids.split(',').length==0 || ids==''|| ids == null){
           	   alert("请选择需要删除的记录");
           	   return;
              }
            layer.open({
                type: 1,
                title: '删除角色',
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
        	var ids = $("#roleTbody input[type=checkbox]:checked").map(function(){
         	   return $(this).val();
            }).get().join(',');
            window.location.href="http://localhost:8080/plan-manager/role/delete.action?ids="+ids;
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
