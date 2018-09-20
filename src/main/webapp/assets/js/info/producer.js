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
        url: "../producer/info/1/10",
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
                html+="<tr><td><input type=\"checkbox\" name='checkbox' value='"+data[index].producerId+"' /></td>";
                html+="<td>"+data[index].producerId+"</td>";
                html+="<td>"+data[index].producerName+"</td>";
                html+="<td>"+data[index].producerAddress+"</td>";
                html+="<td>"+data[index].producerAdmin+"</td>";
                html+="<td>"+data[index].producerTel+"</td>";
                html+="<td>"+data[index].producerRs9+"</td>";
                html+="<td>" +
                    "<div class=\"am-btn-toolbar\">" +
                    "<div class=\"am-btn-group am-btn-group-xs\"> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-text-secondary\" " +
                    "onclick='editPage(this)' " +
                    "producerId='"+data[index].producerId+"' " +
                    "producerName='"+data[index].producerName+"' " +
                    "producerAddress='"+data[index].producerAddress+"' " +
                    "producerAdmin='"+data[index].producerAdmin+"'" +
                    "producerTel='"+data[index].producerTel+"'" +
                    "producerRs9='"+data[index].producerRs9+"'>" +
                    "<span class=\"am-icon-pencil-square-o\"></span> 编辑</button> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-hide-sm-only\" " +
                    "onclick='copy(this);addPage();' " +
                    "producerId='"+data[index].producerId+"' " +
                    "producerName='"+data[index].producerName+"' " +
                    "producerAddress='"+data[index].producerAddress+"' " +
                    "producerAdmin='"+data[index].producerAdmin+"'" +
                    "producerTel='"+data[index].producerTel+"'" +
                    "producerRs9='"+data[index].producerRs9+"'>" +
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
        url: "../common/selectOptionList/producerList",
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
        url: "../producer/page/"+showSum,
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
        url: "../producer/info/"+api.getCurrent()+"/10",
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
                html+="<tr><td><input type=\"checkbox\" name='checkbox' value='"+data[index].producerId+"' /></td>";
                html+="<td>"+data[index].producerId+"</td>";
                html+="<td>"+data[index].producerName+"</td>";
                html+="<td>"+data[index].producerAddress+"</td>";
                html+="<td>"+data[index].producerAdmin+"</td>";
                html+="<td>"+data[index].producerTel+"</td>";
                html+="<td>"+data[index].producerRs9+"</td>";
                html+="<td>" +
                    "<div class=\"am-btn-toolbar\">" +
                    "<div class=\"am-btn-group am-btn-group-xs\"> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-text-secondary\" " +
                    "onclick='editPage(this)' " +
                    "producerId='"+data[index].producerId+"' " +
                    "producerName='"+data[index].producerName+"' " +
                    "producerAddress='"+data[index].producerAddress+"' " +
                    "producerAdmin='"+data[index].producerAdmin+"'" +
                    "producerTel='"+data[index].producerTel+"'" +
                    "producerRs9='"+data[index].producerRs9+"'>" +
                    "<span class=\"am-icon-pencil-square-o\"></span> 编辑</button> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-hide-sm-only\" " +
                    "onclick='copy(this);addPage();' " +
                    "producerId='"+data[index].producerId+"' " +
                    "producerName='"+data[index].producerName+"' " +
                    "producerAddress='"+data[index].producerAddress+"' " +
                    "producerAdmin='"+data[index].producerAdmin+"'" +
                    "producerTel='"+data[index].producerTel+"'" +
                    "producerRs9='"+data[index].producerRs9+"'>" +
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
    var url = "../producer/info/"+api.getCurrent()+"/10";
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
                html+="<tr><td><input type=\"checkbox\" name='checkbox' value='"+data[index].producerId+"' /></td>";
                html+="<td>"+data[index].producerId+"</td>";
                html+="<td>"+data[index].producerName+"</td>";
                html+="<td>"+data[index].producerAddress+"</td>";
                html+="<td>"+data[index].producerAdmin+"</td>";
                html+="<td>"+data[index].producerTel+"</td>";
                html+="<td>"+data[index].producerRs9+"</td>";
                html+="<td>" +
                    "<div class=\"am-btn-toolbar\">" +
                    "<div class=\"am-btn-group am-btn-group-xs\"> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-text-secondary\" " +
                    "onclick='editPage(this)' " +
                    "producerId='"+data[index].producerId+"' " +
                    "producerName='"+data[index].producerName+"' " +
                    "producerAddress='"+data[index].producerAddress+"' " +
                    "producerAdmin='"+data[index].producerAdmin+"'" +
                    "producerTel='"+data[index].producerTel+"'" +
                    "producerRs9='"+data[index].producerRs9+"'>" +
                    "<span class=\"am-icon-pencil-square-o\"></span> 编辑</button> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-hide-sm-only\" " +
                    "onclick='copy(this);addPage();' " +
                    "producerId='"+data[index].producerId+"' " +
                    "producerName='"+data[index].producerName+"' " +
                    "producerAddress='"+data[index].producerAddress+"' " +
                    "producerAdmin='"+data[index].producerAdmin+"'" +
                    "producerTel='"+data[index].producerTel+"'" +
                    "producerRs9='"+data[index].producerRs9+"'>" +
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
    var url = "../producer/info/1/10";
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
                html+="<tr><td><input type=\"checkbox\" name='checkbox' value='"+data[index].producerId+"' /></td>";
                html+="<td>"+data[index].producerId+"</td>";
                html+="<td>"+data[index].producerName+"</td>";
                html+="<td>"+data[index].producerAddress+"</td>";
                html+="<td>"+data[index].producerAdmin+"</td>";
                html+="<td>"+data[index].producerTel+"</td>";
                html+="<td>"+data[index].producerRs9+"</td>";
                html+="<td>" +
                    "<div class=\"am-btn-toolbar\">" +
                    "<div class=\"am-btn-group am-btn-group-xs\"> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-text-secondary\" " +
                    "onclick='editPage(this)'" +
                    "producerId='"+data[index].producerId+"' " +
                    "producerName='"+data[index].producerName+"' " +
                    "producerAddress='"+data[index].producerAddress+"' " +
                    "producerAdmin='"+data[index].producerAdmin+"'" +
                    "producerTel='"+data[index].producerTel+"'" +
                    "producerRs9='"+data[index].producerRs9+"'>" +
                    "<span class=\"am-icon-pencil-square-o\"></span> 编辑</button> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-hide-sm-only\" " +
                    "onclick='copy(this);addPage();' " +
                    "producerId='"+data[index].producerId+"' " +
                    "producerName='"+data[index].productCode+"' " +
                    "producerAddress='"+data[index].producerAddress+"' " +
                    "producerAdmin='"+data[index].producerAdmin+"'" +
                    "producerTel='"+data[index].producerTel+"'" +
                    "producerRs9='"+data[index].producerRs9+"'>" +
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
            var url = "../producer/page/"+showSum;
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
    var formItme = $("#addFORM").find("input,select");
    var form = tojson(formItme);
    $.ajax({
        url: "../producer/add/"+form,
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

            flushPage('producer/page',1);
        }
    });
}
function copy(obj){
    $("#addprompt").modal('open');
    $("#addProducerName").val($(obj).attr("producerName"));
    $("#addProducerAddress").val($(obj).attr("producerAddress"));
    $("#addProducerAdmin").val($(obj).attr("producerAdmin"));
    $("#addProducerTel").val($(obj).attr("producerTel"));
    $("#addProducerRs9").datepicker('setValue', $(obj).attr("producerRs9"));
}
function editPage(obj){
    $("#editprompt").modal('open');
    $("#editProducerId").val($(obj).attr("producerId"));
    $("#editProducerName").val($(obj).attr("producerName"));
    $("#editProducerAddress").val($(obj).attr("producerAddress"));
    $("#editProducerAdmin").val($(obj).attr("producerAdmin"));
    $("#editProducerTel").val($(obj).attr("producerTel"));
    $("#editProducerRs9").datepicker('setValue', $(obj).attr("producerRs9"));
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
        url: "../producer/edit/"+form,
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

            flushPage('producer/page',1);
        }
    });
}
//删除
function del() {
    var chenked=$("input[name='checkbox']:checked").val([]);
    var array = [];
    for(var i = 0;i<chenked.length;i++){
        array.push({"producerId":chenked[i].value});
    }
    $.ajax({
        url: "../producer/del/"+JSON.stringify(array),
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