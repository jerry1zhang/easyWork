//初始化方法
$(function () {
    init();
});
function init() {
    $('#my-modal-loading').modal('open');
    initTableData();
    initSelectOption();
    initPage();
    $("input[name='producerRs9']").datepicker({format: 'yyyy-mm-dd'});
    initFrom();
    $('#my-modal-loading').modal('close');
}
//初始化表格数据
function initTableData() {
    $.ajax({
        url: "../man/info/1/10",
        dataType: 'json',
        type: 'get',
        async: false,
        error: function() {
            $("#alert-message").html("请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {
            drawTable(data);
        }
    });
    $.ajax({
        url: "../group/info",
        dataType: 'json',
        type: 'get',
        error: function() {
            $("#alert-message").html("初始化下拉框错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("初始化下拉框超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {

            var html = "";
            html+="<option value=\"\"></option>";
            for(var index in data){
                html+="<option value=\""+data[index].id+"\">"+data[index].name+"("+data[index].type+")"+"</option>";
            }
            $("#addGroup").html(html);
            $("#editGroup").html(html);
            $("#addGroup").selected();
            $("#editGroup").selected();
            if (!$.AMUI.support.mutationobserver) {
                $("#addGroup").trigger('changed.selected.amui');
                $("#editGroup").trigger('changed.selected.amui');
            }
            // $("#productProducer").trigger('chosen:updated');
            // $("#producer_chosen").css("width","atom");
        }
    });
}
//初始化下拉框
function initSelectOption(){
    $.ajax({
        url: "../common/selectOptionList/userList",
        dataType: 'json',
        type: 'get',
        async: false,
        error: function() {
            $("#alert-message").html("请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {
            var html="";
            for(var index in data){
                html+="<option value=\""+data[index].itemValue+"\">"+data[index].itemName+"</option>";
            }
            $("#selectO").html(html);
        }
    });
}
//初始化分页
function initPage() {
    var listSum=0;
    var showSum=10;
    $.ajax({
        url: "../man/page/"+showSum,
        dataType: 'json',
        type: 'get',
        async: false,
        error: function() {
            $("#alert-message").html("请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {
            listSum = data.listSum;
            $("#listSum").html(data.listSum);

        }
    });
    $("ul[class='am-pagination']").pagination({
        coping: true,
        homePage: '首页',
        endPage: '末页',
        prevContent: '上页',
        nextContent: '下页',
        totalData: listSum,
        showData: showSum,
        activeCls: 'am-active',
        keepShowPN: true,
        callback: function(api) {
            callback(api);
        }
    }, function(api) {
    });

}
//初始化表单验证
function initFrom(){
    $('#FORM').validator({
        validateOnSubmit: true,
        validClass: 'am-field-valid',
        inValidClass: 'am-field-error',
    });
}
//分页回掉
function callback(api){
    $.ajax({
        url: "../man/info/"+api.getCurrent()+"/10",
        dataType: 'json',
        type: 'get',
        error: function() {
            $("#alert-message").html("请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {
            drawTable(data);
        }
    });
}
function callback2(api,selectType,selectValue){
    var url = "../man/info/"+api.getCurrent()+"/10";
    if(typeof(selectValue)==undefined||selectValue==undefined||selectValue==""){

    }else{
        url+="/"+selectType+"/"+selectValue;
    }
    $.ajax({
        url: url,
        dataType: 'json',
        type: 'get',
        error: function() {
            $("#alert-message").html("分页回调请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("分页回调请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {
            drawTable(data);
        }
    });

}
//搜索
function selectAction() {
    $('#my-modal-loading').modal('open');
    var selectType = $("#selectO").val();
    var selectValue = $("#selectV").val();
    var url = "../man/info/1/10";
    if(typeof(selectValue)==undefined||selectValue==undefined||selectValue==""){

    }else{
        url+="/"+selectType+"/"+selectValue;
    }
    $.ajax({
        url: url,
        dataType: 'json',
        type: 'get',
        error: function() {
            $("#alert-message").html("搜索请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("搜索请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {

            //写表
            drawTable(data);
            //写分页
            var listSum=0;
            var showSum=10;
            var url = "../man/page/"+showSum;
            if(typeof(selectValue)==undefined||selectValue==undefined||selectValue==""){

            }else{
                url+="/"+selectType+"/"+selectValue;
            }
            $.ajax({
                url: url,
                dataType: 'json',
                type: 'get',
                async: false,
                error: function() {
                    $("#alert-message").html("请求错误");
                    $("#my-alert").modal('open');
                },
                timeout: function() {
                    $("#alert-message").html("请求超时");
                    $("#my-alert").modal('open');
                },
                success: function(data) {
                    listSum = data.listSum;
                    $("#listSum").html(data.listSum);

                }
            });
            $("ul[class='am-pagination']").pagination({
                coping: true,
                homePage: '首页',
                endPage: '末页',
                prevContent: '上页',
                nextContent: '下页',
                totalData: listSum,
                showData: showSum,
                activeCls: 'am-active',
                keepShowPN: true,
                callback: function(api) {
                    callback2(api,selectType,selectValue);
                }
            }, function(api) {
            });
            $("#selectV").val("");

        }
    });
    $('#my-modal-loading').modal('close');
}
//新增
function addPage() {
    $("#addprompt").modal('open');
}
function add() {
    if($("#addFORM").find(".am-form-error").length!=0){
        $("#alert-message").html("表单验证失败");
        $("#my-alert").modal('open');
        return false;
    }
    $("#addprompt").modal('close');
    $('#my-modal-loading').modal('open');

    //下拉框特殊处理
    // var other = "\""+$("#addGroup").attr("name")+"\":\""+$("#addGroup").val()+"\"";
    var formItme = $("#addFORM").find("input,select");
    var form = tojson(formItme);
    $.ajax({
        url: "../man/add/"+form,
        dataType: 'json',
        type: 'GET',
        async: false,
        error: function() {
            $('#my-modal-loading').modal('close');
            $("#alert-message").html("请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $('#my-modal-loading').modal('close');
            $("#alert-message").html("请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {

            $('#my-modal-loading').modal('close');
            $("#alert-message").html("新增成功！新增条数："+data.addSum);
            $("#my-alert").modal('open');

            flushPage('man/page',1);
        }
    });
}
function copy(obj){
    $("#addprompt").modal('open');
    $("#addFirstName").val($(obj).attr("firstName"));
    $("#addLastName").val($(obj).attr("lastName"));
    $("#addEmail").val($(obj).attr("email"));
    $("#addGroup").val($(obj).attr("groupId"));
    $("#addGroup").trigger('changed.selected.amui');
}
function editPage(obj){
    $("#editprompt").modal('open');
    $("#editName").val($(obj).attr("id"));
    $("#editFirstName").val($(obj).attr("firstName"));
    $("#editLastName").val($(obj).attr("lastName"));
    $("#editEmail").val($(obj).attr("email"));
    $("#editGroup").val($(obj).attr("groupId"));
    $("#editGroup").trigger('changed.selected.amui');
}
function edit() {
    if($("#editFORM").find(".am-form-error").length!=0){
        $("#alert-message").html("表单验证失败");
        $("#my-alert").modal('open');
        return false;
    }
    $("#editprompt").modal('close');
    $('#my-modal-loading').modal('open');

    //下拉框特殊处理
    // var other = "\""+$("#editGroup").attr("name")+"\":\""+$("#editGroup").val()+"\"";
    var formItme = $("#editFORM").find("input,select");
    var form = tojson(formItme);
    $.ajax({
        url: "../man/edit/"+form,
        dataType: 'json',
        type: 'GET',
        async: false,
        error: function() {
            $('#my-modal-loading').modal('close');
            $("#alert-message").html("请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $('#my-modal-loading').modal('close');
            $("#alert-message").html("请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {

            $('#my-modal-loading').modal('close');
            $("#alert-message").html("修改成功！修改条数："+data.changeSum);
            $("#my-alert").modal('open');

            flushPage('man/page',1);
        }
    });
}
//删除
function del() {
    var chenked=$("input[name='checkbox']:checked").val([]);
    var array = [];
    for(var i = 0;i<chenked.length;i++){
        array.push({"name":chenked[i].value});
    }
    $.ajax({
        url: "../man/del/"+JSON.stringify(array),
        dataType: 'json',
        type: 'get',
        async: false,
        error: function() {
            $("#alert-message").html("请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {
            $("#alert-message").html("操作成功！操作结果数："+data.delSum);
            $("#my-alert").modal('open');
            init();
        }
    });
}
function drawTable(data) {
    var html="";
    for(var index in data){
        var id = data[index].user.id==undefined?"":data[index].user.id;
        var firstName = data[index].user.firstName==undefined?"":data[index].user.firstName;
        var lastName = data[index].user.lastName==undefined?"":data[index].user.lastName;
        var email = data[index].user.email==undefined?"":data[index].user.email;
        var groupId = data[index].group.id==undefined?"":data[index].group.id;
        var groupName = data[index].group.name==undefined?"":data[index].group.name;
        var groupType = data[index].group.type==undefined?"":data[index].group.type;
        html+="<tr><td><input type=\"checkbox\" name='checkbox' " +
            "value='"+id+"' /></td>";
        html+="<td>"+id+"</td>";
        html+="<td>"+firstName+" "+lastName+"</td>";
        html+="<td>"+email+"</td>";
        html+="<td>"+groupName+"("+groupType+")"+"</td>";
        html+="<td>" +
            "<div class=\"am-btn-toolbar\">" +
            "<div class=\"am-btn-group am-btn-group-xs\"> " +
            "<button class=\"am-btn am-btn-default am-btn-xs am-text-secondary\" " +
            "onclick='editPage(this)' " +
            "id='"+id+"' " +
            "firstName='"+firstName+"' " +
            "lastName='"+lastName+"' " +
            "email='"+email+"' " +
            "groupId='"+groupId+"' " +
            "groupName='"+groupName+"' " +
            "groupType='"+groupType+"'>" +
            "<span class=\"am-icon-pencil-square-o\"></span> 编辑</button> " +
            "<button class=\"am-btn am-btn-default am-btn-xs am-hide-sm-only\" " +
            "onclick='copy(this);addPage();' " +
            "id='"+id+"' " +
            "firstName='"+firstName+"' " +
            "lastName='"+lastName+"' " +
            "email='"+email+"' " +
            "groupId='"+groupId+"' " +
            "groupName='"+groupName+"' " +
            "groupType='"+groupType+"'>" +
            "<span class=\"am-icon-copy\"></span> 复制</button> " +
            "</div> " +
            "</div> " +
            "</td>";
        html+="</tr>";
    }
    $("#Table").find("tbody").html(html);
}