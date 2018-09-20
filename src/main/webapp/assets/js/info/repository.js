//初始化方法
$(function () {
    init();
});
function init() {
    $('#my-modal-loading').modal('open');
    initTableData();
    initSelectOption();
    initPage();
    $("#addRepositoryRs9").datepicker({format: 'yyyy-mm-dd'});
    $("#editRepositoryRs9").datepicker({format: 'yyyy-mm-dd'});
    initFrom();
    initAddress();
    $('#my-modal-loading').modal('close');
}
//初始化表格数据
function initTableData() {
    $.ajax({
        url: "../repository/info/1/10",
        dataType: 'json',
        type: 'get',
        async: false,
        error: function() {
            $("#alert-message").html("初始化表格数据错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("初始化表格数据超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {
            var html="";
            for(var index in data){
                html+="<tr><td><input type=\"checkbox\" name='checkbox' " +
                    "value='"+data[index].repositoryId+"' /></td>";
                html+="<td>"+data[index].repositoryId+"</td>";
                html+="<td>"+data[index].repositoryName+"</td>";
                html+="<td>"+data[index].repositoryAddress+"</td>";
                html+="<td>"+data[index].repositoryRs2+"</td>";
                html+="<td>"+data[index].repositoryRs1+"</td>";
                html+="<td>" +
                    "<div class=\"am-btn-toolbar\">" +
                    "<div class=\"am-btn-group am-btn-group-xs\"> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-text-secondary\" " +
                    "onclick='editPage(this)' " +
                    "repositoryId='"+data[index].repositoryId+"' " +
                    "repositoryName='"+data[index].repositoryName+"' " +
                    "repositoryAddress='"+data[index].repositoryAddress+"' " +
                    "repositoryAdmin='"+data[index].repositoryAdmin+"'" +
                    "repositoryRs1='"+data[index].repositoryRs1+"'" +
                    "repositoryRs9='"+data[index].repositoryRs9+"'" +
                    "<span class=\"am-icon-pencil-square-o\"></span> 编辑</button> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-hide-sm-only\" " +
                    "onclick='copy(this);addPage();' " +
                    "repositoryId='"+data[index].repositoryId+"' " +
                    "repositoryName='"+data[index].repositoryName+"' " +
                    "repositoryAddress='"+data[index].repositoryAddress+"' " +
                    "repositoryAdmin='"+data[index].repositoryAdmin+"'" +
                    "repositoryRs1='"+data[index].repositoryRs1+"'" +
                    "repositoryRs9='"+data[index].repositoryRs9+"'" +
                    "<span class=\"am-icon-copy\"></span> 复制</button> " +
                    "</div> " +
                    "</div> " +
                    "</td>";
                html+="</tr>";
            }
            $("#Table").find("tbody").html(html);
        }
    });
}
//初始化下拉框
function initSelectOption(){
    $.ajax({
        url: "../common/selectOptionList/repositoryList",
        dataType: 'json',
        type: 'get',
        async: false,
        error: function() {
            $("#alert-message").html("初始化下拉框错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("初始化下拉框超时");
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
    $.ajax({
        url: "../apiuser/userList/库管",
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
                html+="<option value=\""+data[index].user.id+"\">"+data[index].user.firstName+data[index].user.lastName+"("+data[index].group.id+")"+"</option>";
            }
            $("#addRepositoryAdmin").html(html);
            $("#editRepositoryAdmin").html(html);
            $("#addRepositoryAdmin").selected();
            $("#editRepositoryAdmin").selected();
            if (!$.AMUI.support.mutationobserver) {
                $("#addRepositoryAdmin").trigger('changed.selected.amui');
                $("#editRepositoryAdmin").trigger('changed.selected.amui');
            }
            // $("#productProducer").trigger('chosen:updated');
            // $("#producer_chosen").css("width","atom");
        }
    });
}
//初始化分页
function initPage() {
    var listSum=0;
    var showSum=10;
    $.ajax({
        url: "../repository/page/"+showSum,
        dataType: 'json',
        type: 'get',
        async: false,
        error: function() {
            $("#alert-message").html("初始化分页错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("初始化分页超时");
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
function initAddress() {
    $('#addRepositoryAddress').address({
        scrollToCenter: true,
        footer: true,
        selectEnd: function(json) {
            $('#addRepositoryAddress').val(json.prov+json.city+json.district);
        }
    });
    $('#editRepositoryAddress').address({
        scrollToCenter: true,
        footer: true,
        selectEnd: function(json) {
            $('#editRepositoryAddress').val(json.prov+json.city+json.district);
        }
    });
    // add.on("provTap",function(event,activeli){
    //     $('#addRepositoryAddress').val(activeli.text());
    // });
    // add.on("cityTap",function(event,activeli){
    //     var str = $('#addRepositoryAddress').val();
    //     $('#addRepositoryAddress').val(str+activeli.text());
    // });
    // add.on("districtTap",function(event,activeli){
    //     var str = $('#addRepositoryAddress').val();
    //     $('#addRepositoryAddress').val(str+activeli.text());
    // });
    // edit.on("provTap",function(event,activeli){
    //     $('#editRepositoryAddress').val(activeli.text());
    // });
    // edit.on("cityTap",function(event,activeli){
    //     var str = $('#editRepositoryAddress').val();
    //     $('#editRepositoryAddress').val(str+activeli.text());
    // });
    // edit.on("districtTap",function(event,activeli){
    //     var str = $('#editRepositoryAddress').val();
    //     $('#editRepositoryAddress').val(str+activeli.text());
    // });
}
//分页回掉
function callback(api){
    $.ajax({
        url: "../repository/info/"+api.getCurrent()+"/10",
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
            var html="";
            for(var index in data){
                html+="<tr><td><input type=\"checkbox\" name='checkbox' " +
                    "value='"+data[index].repositoryId+"' /></td>";
                html+="<td>"+data[index].repositoryId+"</td>";
                html+="<td>"+data[index].repositoryName+"</td>";
                html+="<td>"+data[index].repositoryAddress+"</td>";
                html+="<td>"+data[index].repositoryRs2+"</td>";
                html+="<td>"+data[index].repositoryRs1+"</td>";
                html+="<td>" +
                    "<div class=\"am-btn-toolbar\">" +
                    "<div class=\"am-btn-group am-btn-group-xs\"> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-text-secondary\" " +
                    "onclick='editPage(this)' " +
                    "repositoryId='"+data[index].repositoryId+"' " +
                    "repositoryName='"+data[index].repositoryName+"' " +
                    "repositoryAddress='"+data[index].repositoryAddress+"' " +
                    "repositoryAdmin='"+data[index].repositoryAdmin+"'" +
                    "repositoryRs1='"+data[index].repositoryRs1+"'" +
                    "repositoryRs9='"+data[index].repositoryRs9+"'" +
                    "<span class=\"am-icon-pencil-square-o\"></span> 编辑</button> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-hide-sm-only\" " +
                    "onclick='copy(this);addPage();' " +
                    "repositoryId='"+data[index].repositoryId+"' " +
                    "repositoryName='"+data[index].repositoryName+"' " +
                    "repositoryAddress='"+data[index].repositoryAddress+"' " +
                    "repositoryAdmin='"+data[index].repositoryAdmin+"'" +
                    "repositoryRs1='"+data[index].repositoryRs1+"'" +
                    "repositoryRs9='"+data[index].repositoryRs9+"'" +
                    "<span class=\"am-icon-copy\"></span> 复制</button> " +
                    "</div> " +
                    "</div> " +
                    "</td>";
                html+="</tr>";
            }
            $("#Table").find("tbody").html(html);
        }
    });
}
function callback2(api,selectType,selectValue){
    var url = "../repository/info/"+api.getCurrent()+"/10";
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
            var html="";
            for(var index in data){
                html+="<tr><td><input type=\"checkbox\" name='checkbox' " +
                    "value='"+data[index].repositoryId+"' /></td>";
                html+="<td>"+data[index].repositoryId+"</td>";
                html+="<td>"+data[index].repositoryName+"</td>";
                html+="<td>"+data[index].repositoryAddress+"</td>";
                html+="<td>"+data[index].repositoryRs2+"</td>";
                html+="<td>"+data[index].repositoryRs1+"</td>";
                html+="<td>" +
                    "<div class=\"am-btn-toolbar\">" +
                    "<div class=\"am-btn-group am-btn-group-xs\"> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-text-secondary\" " +
                    "onclick='editPage(this)' " +
                    "repositoryId='"+data[index].repositoryId+"' " +
                    "repositoryName='"+data[index].repositoryName+"' " +
                    "repositoryAddress='"+data[index].repositoryAddress+"' " +
                    "repositoryAdmin='"+data[index].repositoryAdmin+"'" +
                    "repositoryRs1='"+data[index].repositoryRs1+"'" +
                    "repositoryRs9='"+data[index].repositoryRs9+"'" +
                    "<span class=\"am-icon-pencil-square-o\"></span> 编辑</button> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-hide-sm-only\" " +
                    "onclick='copy(this);addPage();' " +
                    "repositoryId='"+data[index].repositoryId+"' " +
                    "repositoryName='"+data[index].repositoryName+"' " +
                    "repositoryAddress='"+data[index].repositoryAddress+"' " +
                    "repositoryAdmin='"+data[index].repositoryAdmin+"'" +
                    "repositoryRs1='"+data[index].repositoryRs1+"'" +
                    "repositoryRs9='"+data[index].repositoryRs9+"'" +
                    "<span class=\"am-icon-copy\"></span> 复制</button> " +
                    "</div> " +
                    "</div> " +
                    "</td>";
                html+="</tr>";
            }
            $("#Table").find("tbody").html(html);
        }
    });

}
//搜索
function selectAction() {
    $('#my-modal-loading').modal('open');
    var selectType = $("#selectO").val();
    var selectValue = $("#selectV").val();
    var url = "../repository/info/1/10";
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
            var html="";
            for(var index in data){
                html+="<tr><td><input type=\"checkbox\" name='checkbox' " +
                    "value='"+data[index].repositoryId+"' /></td>";
                html+="<td>"+data[index].repositoryId+"</td>";
                html+="<td>"+data[index].repositoryName+"</td>";
                html+="<td>"+data[index].repositoryAddress+"</td>";
                html+="<td>"+data[index].repositoryRs2+"</td>";
                html+="<td>"+data[index].repositoryRs1+"</td>";
                html+="<td>" +
                    "<div class=\"am-btn-toolbar\">" +
                    "<div class=\"am-btn-group am-btn-group-xs\"> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-text-secondary\" " +
                    "onclick='editPage(this)'" +
                    "repositoryId='"+data[index].repositoryId+"' " +
                    "repositoryName='"+data[index].repositoryName+"' " +
                    "repositoryAddress='"+data[index].repositoryAddress+"' " +
                    "repositoryAdmin='"+data[index].repositoryAdmin+"'" +
                    "repositoryRs1='"+data[index].repositoryRs1+"'" +
                    "repositoryRs9='"+data[index].repositoryRs9+"'" +
                    "<span class=\"am-icon-pencil-square-o\"></span> 编辑</button> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-hide-sm-only\" " +
                    "onclick='copy(this);addPage();' " +
                    "repositoryId='"+data[index].repositoryId+"' " +
                    "repositoryName='"+data[index].repositoryName+"' " +
                    "repositoryAddress='"+data[index].repositoryAddress+"' " +
                    "repositoryAdmin='"+data[index].repositoryAdmin+"'" +
                    "repositoryRs1='"+data[index].repositoryRs1+"'" +
                    "repositoryRs9='"+data[index].repositoryRs9+"'" +
                    "<span class=\"am-icon-copy\"></span> 复制</button> " +
                    "</div> " +
                    "</div> " +
                    "</td>";
                html+="</tr>";
            }
            $("#Table").find("tbody").html(html);
            //写分页
            var listSum=0;
            var showSum=10;
            var url = "../repository/page/"+showSum;
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
    var formItme = $("#addFORM").find("input,select");
    var form = tojson(formItme);
    $.ajax({
        url: "../repository/add/"+form,
        dataType: 'json',
        type: 'GET',
        async: false,
        error: function() {
            $('#my-modal-loading').modal('close');
            $("#alert-message").html("新增请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $('#my-modal-loading').modal('close');
            $("#alert-message").html("新增请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {

            $('#my-modal-loading').modal('close');
            $("#alert-message").html("新增成功！新增条数："+data.addSum);
            $("#my-alert").modal('open');

            flushPage('repository/page',1);
        }
    });
}
function copy(obj){
    $("#addprompt").modal('open');
    $("#addRepositoryName").val($(obj).attr("repositoryName"));
    $("#addRepositoryAddress").val($(obj).attr("repositoryAddress"));
    $("#addRepositoryAdmin").val($(obj).attr("repositoryAdmin"));
    $("#addRepositoryAdmin").trigger('changed.selected.amui');
    $("#addRepositoryRs1").val($(obj).attr("repositoryRs1"));
    $("#addRepositoryRs9").datepicker('setValue', $(obj).attr("repositoryRs9"));
    // $("#addRepositoryRs9").datepicker({format: 'yyyy-mm-dd'});
}
function editPage(obj){
    $("#editprompt").modal('open');
    $("#editRepositoryId").val($(obj).attr("repositoryId"));
    $("#editRepositoryName").val($(obj).attr("repositoryName"));
    $("#editRepositoryAddress").val($(obj).attr("repositoryAddress"));
    $("#editRepositoryAdmin").val($(obj).attr("repositoryAdmin"));
    $("#editRepositoryAdmin").trigger('changed.selected.amui');
    $("#editRepositoryRs1").val($(obj).attr("repositoryRs1"));
    $("#editRepositoryRs9").datepicker('setValue', $(obj).attr("repositoryRs9"));
    // $("#editRepositoryRs9").datepicker({format: 'yyyy-mm-dd'});
}
//修改
function edit() {
    if($("#editFORM").find(".am-form-error").length!=0){
        $("#alert-message").html("表单验证失败");
        $("#my-alert").modal('open');
        return false;
    }
    $("#editprompt").modal('close');
    $('#my-modal-loading').modal('open');

    //下拉框特殊处理
    var formItme = $("#editFORM").find("input,select");
    var form = tojson(formItme);
    $.ajax({
        url: "../repository/edit/"+form,
        dataType: 'json',
        type: 'GET',
        async: false,
        error: function() {
            $('#my-modal-loading').modal('close');
            $("#alert-message").html("修改请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $('#my-modal-loading').modal('close');
            $("#alert-message").html("修改请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {

            $('#my-modal-loading').modal('close');
            $("#alert-message").html("修改成功！修改条数："+data.changeSum);
            $("#my-alert").modal('open');

            flushPage('repository/page',1);
        }
    });
}
//删除
function del() {
    var chenked=$("input[name='checkbox']:checked").val([]);
    var array = [];
    for(var i = 0;i<chenked.length;i++){
        array.push({"repositoryId":chenked[i].value});
    }
    $.ajax({
        url: "../repository/del/"+JSON.stringify(array),
        dataType: 'json',
        type: 'get',
        async: false,
        error: function() {
            $("#alert-message").html("删除请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("删除请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {
            $("#alert-message").html("删除成功！操作结果数："+data.delSum);
            $("#my-alert").modal('open');
            init();
        }
    });
}