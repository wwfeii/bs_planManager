

$(function(){
	
	$("#logout").click(function(){
		 window.location.href="http://localhost:8080/plan-manager/user/logout.action";
	})

    layui.use('form', function(){
        var form = layui.form;
        form.on('submit(formDemo)', function(data){
            layer.msg(JSON.stringify(data.field));
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

        function close() {

        }
        // 新增
        $('#add_layer1').on('click', function () {
            layer.open({
                type: 1,
                scrollbar: false, shade: 0.5,
                title: "新增项目",
                area: ['600px', '360px'],
                shadeClose: true, //点击遮罩关闭
                content:'<form class="layui-form lay_form_padding20" action="${pageContext.request.contextPath}/project/add.action">\n' +
                '  <div class="layui-form-item">\n' +
                '    <label class="layui-form-label">项目名</label>\n' +
                '    <div class="layui-input-block">\n' +
                '      <input type="text" name="projectName" required  lay-verify="required" placeholder="请输入项目名" autocomplete="off" class="layui-input">\n' +
                '    </div>\n' +
                '  </div>\n' +
                '<div class="layui-form-item">'+
                '<label class="layui-form-label">项目负责人:</label>\n'+
                '<div class="layui-input-block">\n'+
                   ' <select name="city" lay-verify="required">\n'+
                        '<option value="">请选择</option>\n'+
                       ' <option value="0">北京</option>\n'+
                       ' <option value="1">上海</option>\n'+
                       ' <option value="2">广州</option>\n'+
                       ' <option value="3">深圳</option>\n'+
                       ' <option value="4">杭州</option>\n'+
                    '</select>\n'+
               ' </div>\n'+
                '</div>\n'+
                '  <div class="layui-form-item">\n' +
                '    <div class="layui-input-block">\n' +
                '      <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>\n' +
                '      <button type="reset" class="layui-btn layui-btn-primary">重置</button>\n' +
                '    </div>\n' +
                '  </div>\n' +
                '</form>'

            });
        });

        // 修改
        $('.cao_zuo .xiu_gai').on('click', function () {
            layer.open({
                type: 1,
                scrollbar: false, shade: 0.5,
                title: "修改",
                area: ['600px', '360px'],
                shadeClose: true, //点击遮罩关闭
                content:'<form class="layui-form lay_form_padding20" action="">\n' +
                '  <div class="layui-form-item">\n' +
                '    <label class="layui-form-label">输入框</label>\n' +
                '    <div class="layui-input-block">\n' +
                '      <input type="text" name="title" required  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">\n' +
                '    </div>\n' +
                '  </div>\n' +
                '  <div class="layui-form-item">\n' +
                '    <label class="layui-form-label">密码框</label>\n' +
                '    <div class="layui-input-inline">\n' +
                '      <input type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">\n' +
                '    </div>\n' +
                '  </div>\n' +
                '  <div class="layui-form-item layui-form-text">\n' +
                '    <label class="layui-form-label">文本域</label>\n' +
                '    <div class="layui-input-block">\n' +
                '      <textarea name="desc" placeholder="请输入内容" class="layui-textarea"></textarea>\n' +
                '    </div>\n' +
                '  </div>\n' +
                '  <div class="layui-form-item">\n' +
                '    <div class="layui-input-block">\n' +
                '      <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>\n' +
                '      <button type="reset" class="layui-btn layui-btn-primary">重置</button>\n' +
                '    </div>\n' +
                '  </div>\n' +
                '</form>'

            });
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
            layer.open({
                type: 1,
                title: '全部删除',
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

            layer.closeAll();
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
