//初始化方法
$(function () {
    init();
});
function init() {
    $('#my-modal-loading').modal('open');
    initTableData();
    initSelectOption();
    initPage();
    initFrom();
    $('#my-modal-loading').modal('close');
}
//初始化表格数据
function initTableData() {
    $.ajax({
        url: "../group/info/1/10",
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
}
//初始化下拉框
function initSelectOption(){
    $.ajax({
        url: "../common/selectOptionList/groupList",
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
        url: "../group/page/"+showSum,
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
        url: "../group/info/"+api.getCurrent()+"/10",
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
    var url = "../group/info/"+api.getCurrent()+"/10";
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
    var url = "../group/info/1/10";
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
            var url = "../group/page/"+showSum;
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
    // var producer = "\""+$("#productProducer").attr("name")+"\":\""+$("#productProducer").val()+"\"";
    // $("addId").val($("#addName").val());
    var formItme = $("#addFORM").find("input,select");
    var form = tojson(formItme);
    $.ajax({
        url: "../group/add/"+form,
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

            flushPage('group/page',1);
        }
    });
}
function copy(obj){
    $("#addprompt").modal('open');
    $("#addName").val($(obj).attr("groupName"));
    $("#addType").val($(obj).attr("groupType"));
}
function editPage(obj){
    $("#editprompt").modal('open');
    $("#editId").val($(obj).attr("groupId"));
    $("#editName").val($(obj).attr("groupName"));
    $("#editType").val($(obj).attr("groupType"));
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
    // var producer = "\""+$("#editProductProducer").attr("name")+"\":\""+$("#editProductProducer").val()+"\"";
    var formItme = $("#editFORM").find("input,select");
    var form = tojson(formItme);
    $.ajax({
        url: "../group/edit/"+form,
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

            flushPage('group/page',1);
        }
    });
}
//删除
function del() {
    var chenked=$("input[name='checkbox']:checked").val([]);
    var array = [];
    for(var i = 0;i<chenked.length;i++){
        array.push({"id":chenked[i].value});
    }
    $.ajax({
        url: "../group/del/"+JSON.stringify(array),
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
        html+="<tr><td><input type=\"checkbox\" name='checkbox' " +
            "value='"+data[index].id+"' /></td>";
        html+="<td>"+data[index].id+"</td>";
        html+="<td>"+data[index].name+"</td>";
        html+="<td>"+data[index].type+"</td>";
        html+="<td>" +
            "<div class=\"am-btn-toolbar\">" +
            "<div class=\"am-btn-group am-btn-group-xs\"> " +
            "<button class=\"am-btn am-btn-default am-btn-xs am-text-secondary\" " +
            "onclick='editPage(this)' " +
            "groupId='"+data[index].id+"' " +
            "groupName='"+data[index].name+"' " +
            "groupType='"+data[index].type+"'>" +
            "<span class=\"am-icon-pencil-square-o\"></span> 编辑</button> " +
            "<button class=\"am-btn am-btn-default am-btn-xs am-hide-sm-only\" " +
            "onclick='copy(this);addPage();' " +
            "groupId='"+data[index].id+"' " +
            "groupName='"+data[index].name+"' " +
            "groupType='"+data[index].type+"'>" +
            "<span class=\"am-icon-copy\"></span> 复制</button> " +
            "</div> " +
            "</div> " +
            "</td>";
        html+="</tr>";
    }
    $("#Table").find("tbody").html(html);
}